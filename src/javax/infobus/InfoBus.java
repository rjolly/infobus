package javax.infobus;

import java.awt.Component;
import java.awt.datatransfer.DataFlavor;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyVetoException;
import java.io.PrintStream;
import java.util.Enumeration;
import java.util.EventObject;
import java.util.MissingResourceException;
import java.util.Vector;

// Referenced classes of package javax.infobus:
//			DefaultController, InfoBusDataConsumer, InfoBusDataController, InfoBusDataProducer, 
//			InfoBusItemAvailableEvent, InfoBusItemRequestedEvent, InfoBusItemRevokedEvent, InfoBusMember, 
//			InfoBusMembershipException, InfoBusPolicyHelper, PrioritizedDCList, StaleInfoBusException

public final class InfoBus
	implements PropertyChangeListener {

	static final byte Copyright_1997_1998_Lotus_Development_Corporation_All_Rights_Reserved = 1;
	private static Vector sm_infoBusList = new Vector();
	private static InfoBusPolicyHelper sm_IBPolicy;
	private static Object sm_syncLock = new Object();
	private Object m_syncLock;
	static final boolean DEBUG_OUTPUT = false;
	public static final String MIME_TYPE_IMMEDIATE_ACCESS = "application/x-java-infobus;class=javax.infobus.ImmediateAccess";
	public static final String MIME_TYPE_ARRAY_ACCESS = "application/x-java-infobus;class=javax.infobus.ArrayAccess";
	public static final String MIME_TYPE_RESHAPEABLEARRAY_ACCESS = "application/x-java-infobus;class=javax.infobus.ReshapeableArrayAccess";
	public static final String MIME_TYPE_ROWSET_ACCESS = "application/x-java-infobus;class=javax.infobus.RowsetAccess";
	public static final String MIME_TYPE_SCROLLABLEROWSET_ACCESS = "application/x-java-infobus;class=javax.infobus.ScrollableRowsetAccess";
	public static final String MIME_TYPE_DB_ACCESS = "application/x-java-infobus;class=javax.infobus.DbAccess";
	public static final String MIME_TYPE_COLLECTION = "application/x-java-infobus;class=java.util.Collection";
	public static final String MIME_TYPE_MAP = "application/x-java-infobus;class=java.util.Map";
	public static final String MIME_TYPE_SET = "application/x-java-infobus;class=java.util.Set";
	public static final String MIME_TYPE_LIST = "application/x-java-infobus;class=java.util.List";
	public static final String MIME_TYPE_ANY_ACCESS = "application/x-java-infobus;class=javax.infobus.DataItem";
	public static final int MONITOR_PRIORITY = 6;
	public static final int VERY_HIGH_PRIORITY = 5;
	public static final int HIGH_PRIORITY = 4;
	public static final int MEDIUM_PRIORITY = 3;
	public static final int LOW_PRIORITY = 2;
	public static final int VERY_LOW_PRIORITY = 1;
	static final int DEFAULT_CONTROLLER_PRIORITY = 0;
	private String m_busID;
	private Vector m_memberList;
	private Vector m_producerList;
	private Vector m_consumerList;
	private int m_openCount;
	private PrioritizedDCList m_controllerList;
	private boolean m_addedControllers;
	private InfoBusDataController m_defaultControl;

	private InfoBus(String s) {
		checkPolicy();
		m_busID = s;
		m_memberList = new Vector();
		m_producerList = new Vector();
		m_consumerList = new Vector();
		m_syncLock = new Object();
		m_controllerList = new PrioritizedDCList(1, 1, this);
		m_defaultControl = new DefaultController(this);
		m_defaultControl.setProducerList(m_producerList);
		m_defaultControl.setConsumerList(m_consumerList);
		try {
			m_controllerList.addDataController(m_defaultControl, 0);
		}
		catch (InfoBusMembershipException _ex) { }
		m_addedControllers = false;
	}

	private static void checkPolicy() {
		synchronized (sm_syncLock) {
			if (sm_IBPolicy == null) {
				String s = "javax.infobus.DefaultPolicy";
				try {
					s = System.getProperty("javax.infobus.InfoBusPolicy", "javax.infobus.DefaultPolicy");
				}
				catch (SecurityException _ex) { }
				try {
					Class class1 = Class.forName(s);
					sm_IBPolicy = (InfoBusPolicyHelper)class1.newInstance();
				}
				catch (Exception exception) {
					System.err.println("Fatal Error Initializing InfoBusPolicy  in javax.infobus.InfoBus:" + exception);
					throw new MissingResourceException("Fatal Error Initializing javax.infobus.InfoBus", s, "javax.infobus.InfoBusPolicy");
				}
			}
		}
	}

	void checkStale() {
		synchronized (m_syncLock) {
			if (!sm_infoBusList.contains(this))
				throw new StaleInfoBusException(" Attempt to use stale  InfoBus, busName = " + getName());
		}
	}

	public String getName() {
		return m_busID;
	}

	public static InfoBus get(Component component) {
		checkPolicy();
		String s = sm_IBPolicy.generateDefaultName(component);
		sm_IBPolicy.canGet(s);
		InfoBus infobus = get(s);
		return infobus;
	}

	public static InfoBus get(String s) {
		synchronized (sm_syncLock) {
			try {
				if (s.charAt(0) == '-')
					throw new IllegalArgumentException("InfoBus names must not begin with the dash ( - ) character");
			}
			catch (StringIndexOutOfBoundsException _ex) {
				throw new IllegalArgumentException("The parameter to InfoBus.get(String) must not be the empty string.");
			}
			if (s.indexOf('*') != -1)
				throw new IllegalArgumentException("InfoBus names must not contain the asterisk ( * ) character");
			checkPolicy();
			sm_IBPolicy.canGet(s);
			for (int i = 0; i < sm_infoBusList.size(); i++) {
				InfoBus infobus2 = (InfoBus)sm_infoBusList.elementAt(i);
				if (infobus2.getName().equals(s)) {
					infobus2.incrementOpenCount();
					InfoBus infobus = infobus2;
					return infobus;
				}
			}

			InfoBus infobus3 = new InfoBus(s);
			sm_infoBusList.addElement(infobus3);
			infobus3.incrementOpenCount();
			InfoBus infobus1 = infobus3;
			return infobus1;
		}
	}

	private void incrementOpenCount() {
		m_openCount++;
	}

	private void decrementOpenCount() {
		m_openCount--;
		if (m_openCount < 0)
			m_openCount = 0;
	}

	public void release() {
		synchronized (m_syncLock) {
			decrementOpenCount();
			freeUnused();
		}
	}

	public void join(InfoBusMember infobusmember) throws PropertyVetoException {
		synchronized (m_syncLock) {
			checkStale();
			sm_IBPolicy.canJoin(this, infobusmember);
			try {
				infobusmember.setInfoBus(this);
			}
			catch (PropertyVetoException propertyvetoexception) {
				freeUnused();
				throw propertyvetoexception;
			}
		}
	}

	public void register(InfoBusMember infobusmember) {
		synchronized (m_syncLock) {
			checkStale();
			sm_IBPolicy.canRegister(this, infobusmember);
			if (!m_memberList.contains(infobusmember)) {
				infobusmember.addInfoBusPropertyListener(this);
				m_memberList.addElement(infobusmember);
			}
		}
	}

	public void propertyChange(PropertyChangeEvent propertychangeevent) {
		String s = propertychangeevent.getPropertyName();
		if (s != null && s.equals("InfoBus")) {
			sm_IBPolicy.canPropertyChange(this, propertychangeevent);
			Object obj = propertychangeevent.getOldValue();
			Object obj1 = propertychangeevent.getNewValue();
			if (obj == obj1)
				return;
			if (obj == this) {
				InfoBusMember infobusmember = (InfoBusMember)propertychangeevent.getSource();
				infobusmember.removeInfoBusPropertyListener(this);
				m_memberList.removeElement(infobusmember);
				freeUnused();
			}
		}
	}

	private void freeUnused() {
		synchronized (m_syncLock) {
			if (m_openCount > 0)
				return;
			if (m_memberList.isEmpty() && m_producerList.isEmpty() && m_consumerList.isEmpty() && !m_addedControllers)
				synchronized (sm_syncLock) {
					sm_infoBusList.removeElement(this);
				}
		}
	}

	public void leave(InfoBusMember infobusmember) throws PropertyVetoException {
		synchronized (m_syncLock) {
			if (!m_memberList.removeElement(infobusmember))
				return;
			infobusmember.removeInfoBusPropertyListener(this);
			try {
				infobusmember.setInfoBus(null);
			}
			catch (PropertyVetoException propertyvetoexception) {
				infobusmember.addInfoBusPropertyListener(this);
				m_memberList.addElement(infobusmember);
				throw propertyvetoexception;
			}
			freeUnused();
		}
	}

	public void addDataController(InfoBusDataController infobusdatacontroller, int i) throws InfoBusMembershipException {
		synchronized (m_syncLock) {
			checkStale();
			if (infobusdatacontroller == null)
				throw new NullPointerException("InfoBus.addDataController cannot accept null as parameter");
			sm_IBPolicy.canAddDataController(this, infobusdatacontroller, i);
			m_controllerList.addDataController(infobusdatacontroller, i);
			Vector vector = (Vector)m_producerList.clone();
			Vector vector1 = (Vector)m_consumerList.clone();
			infobusdatacontroller.setProducerList(vector);
			infobusdatacontroller.setConsumerList(vector1);
			m_addedControllers = true;
		}
	}

	public void removeDataController(InfoBusDataController infobusdatacontroller) {
		synchronized (m_syncLock) {
			m_controllerList.removeDataController(infobusdatacontroller);
			freeUnused();
		}
	}

	public void addDataProducer(InfoBusDataProducer infobusdataproducer) {
		synchronized (m_syncLock) {
			if (infobusdataproducer == null)
				throw new NullPointerException("InfoBus.addDataProducer cannot accept null as parameter");
			checkStale();
			sm_IBPolicy.canAddDataProducer(this, infobusdataproducer);
			if (!m_producerList.contains(infobusdataproducer)) {
				m_producerList.addElement(infobusdataproducer);
				if (m_addedControllers) {
					InfoBusDataController infobusdatacontroller;
					for (Enumeration enumeration = m_controllerList.elements(); enumeration.hasMoreElements(); infobusdatacontroller.addDataProducer(infobusdataproducer))
						infobusdatacontroller = (InfoBusDataController)enumeration.nextElement();

				}
			}
		}
	}

	public void removeDataProducer(InfoBusDataProducer infobusdataproducer) {
		synchronized (m_syncLock) {
			m_producerList.removeElement(infobusdataproducer);
			if (m_addedControllers) {
				InfoBusDataController infobusdatacontroller;
				for (Enumeration enumeration = m_controllerList.elements(); enumeration.hasMoreElements(); infobusdatacontroller.removeDataProducer(infobusdataproducer))
					infobusdatacontroller = (InfoBusDataController)enumeration.nextElement();

			}
			freeUnused();
		}
	}

	public void addDataConsumer(InfoBusDataConsumer infobusdataconsumer) {
		synchronized (m_syncLock) {
			if (infobusdataconsumer == null)
				throw new NullPointerException("InfoBus.addDataConsumer cannot accept null as parameter");
			checkStale();
			sm_IBPolicy.canAddDataConsumer(this, infobusdataconsumer);
			if (!m_consumerList.contains(infobusdataconsumer)) {
				m_consumerList.addElement(infobusdataconsumer);
				if (m_addedControllers) {
					InfoBusDataController infobusdatacontroller;
					for (Enumeration enumeration = m_controllerList.elements(); enumeration.hasMoreElements(); infobusdatacontroller.addDataConsumer(infobusdataconsumer))
						infobusdatacontroller = (InfoBusDataController)enumeration.nextElement();

				}
			}
		}
	}

	public void removeDataConsumer(InfoBusDataConsumer infobusdataconsumer) {
		synchronized (m_syncLock) {
			m_consumerList.removeElement(infobusdataconsumer);
			if (m_addedControllers) {
				InfoBusDataController infobusdatacontroller;
				for (Enumeration enumeration = m_controllerList.elements(); enumeration.hasMoreElements(); infobusdatacontroller.removeDataConsumer(infobusdataconsumer))
					infobusdatacontroller = (InfoBusDataController)enumeration.nextElement();

			}
			freeUnused();
		}
	}

	public void fireItemAvailable(String s, DataFlavor adataflavor[], InfoBusDataProducer infobusdataproducer) {
		sm_IBPolicy.canFireItemAvailable(this, s, infobusdataproducer);
		if (m_addedControllers) {
			boolean flag = false;
			Vector vector = m_controllerList.getDCclone();
			int i = vector.size();
			for (int j = 0; j < i && !flag; j++) {
				InfoBusDataController infobusdatacontroller = (InfoBusDataController)vector.elementAt(j);
				flag = infobusdatacontroller.fireItemAvailable(s, adataflavor, infobusdataproducer);
			}

			return;
		} else {
			m_defaultControl.fireItemAvailable(s, adataflavor, infobusdataproducer);
			return;
		}
	}

	public void fireItemRevoked(String s, InfoBusDataProducer infobusdataproducer) {
		sm_IBPolicy.canFireItemRevoked(this, s, infobusdataproducer);
		if (m_addedControllers) {
			boolean flag = false;
			Vector vector = m_controllerList.getDCclone();
			int i = vector.size();
			for (int j = 0; j < i && !flag; j++) {
				InfoBusDataController infobusdatacontroller = (InfoBusDataController)vector.elementAt(j);
				flag = infobusdatacontroller.fireItemRevoked(s, infobusdataproducer);
			}

			return;
		} else {
			m_defaultControl.fireItemRevoked(s, infobusdataproducer);
			return;
		}
	}

	public Object findDataItem(String s, DataFlavor adataflavor[], InfoBusDataConsumer infobusdataconsumer) {
		sm_IBPolicy.canRequestItem(this, s, infobusdataconsumer);
		Vector vector = new Vector(1, 1);
		if (m_addedControllers) {
			boolean flag = false;
			PrioritizedDCList prioritizeddclist = (PrioritizedDCList)m_controllerList.clone();
			int i = prioritizeddclist.size();
			for (int j = 0; !flag && j < i; j++) {
				InfoBusDataController infobusdatacontroller = prioritizeddclist.controllerAt(j);
				int k = prioritizeddclist.priorityAt(j);
				flag = infobusdatacontroller.findDataItem(s, adataflavor, infobusdataconsumer, vector);
				if (k == 6) {
					vector.removeAllElements();
					flag = false;
				} else {
					flag = flag || !vector.isEmpty();
				}
			}

		} else {
			m_defaultControl.findDataItem(s, adataflavor, infobusdataconsumer, vector);
		}
		if (vector.isEmpty())
			return null;
		else
			return vector.elementAt(0);
	}

	public Object[] findMultipleDataItems(String s, DataFlavor adataflavor[], InfoBusDataConsumer infobusdataconsumer) {
		sm_IBPolicy.canRequestItem(this, s, infobusdataconsumer);
		Vector vector = new Vector();
		Vector vector1 = new Vector();
		if (m_addedControllers) {
			boolean flag = false;
			PrioritizedDCList prioritizeddclist = (PrioritizedDCList)m_controllerList.clone();
			int j = prioritizeddclist.size();
			for (int k = 0; !flag && k < j; k++) {
				InfoBusDataController infobusdatacontroller = prioritizeddclist.controllerAt(k);
				int l = prioritizeddclist.priorityAt(k);
				flag = infobusdatacontroller.findMultipleDataItems(s, adataflavor, infobusdataconsumer, vector1);
				if (l != 6)
					catNoDups(vector, vector1);
				else
					flag = false;
				vector1.removeAllElements();
			}

		} else {
			m_defaultControl.findMultipleDataItems(s, adataflavor, infobusdataconsumer, vector1);
			catNoDups(vector, vector1);
		}
		if (vector.isEmpty()) {
			return null;
		} else {
			int i = vector.size();
			Object aobj[] = new Object[i];
			vector.copyInto(aobj);
			return aobj;
		}
	}

	private void catNoDups(Vector vector, Vector vector1) {
		for (Enumeration enumeration = vector1.elements(); enumeration.hasMoreElements();) {
			Object obj = enumeration.nextElement();
			if (!vector.contains(obj))
				vector.addElement(obj);
		}

	}

	public Object findDataItem(String s, DataFlavor adataflavor[], InfoBusDataConsumer infobusdataconsumer, InfoBusDataProducer infobusdataproducer) {
		sm_IBPolicy.canRequestItem(this, s, infobusdataconsumer);
		InfoBusItemRequestedEvent infobusitemrequestedevent = new InfoBusItemRequestedEvent(s, adataflavor, infobusdataconsumer);
		if (infobusdataproducer != null)
			infobusdataproducer.dataItemRequested(infobusitemrequestedevent);
		Object obj = infobusitemrequestedevent.getDataItem();
		return obj;
	}

	public Object findDataItem(String s, DataFlavor adataflavor[], InfoBusDataConsumer infobusdataconsumer, Vector vector) {
		sm_IBPolicy.canRequestItem(this, s, infobusdataconsumer);
		Vector vector1 = (Vector)vector.clone();
		InfoBusItemRequestedEvent infobusitemrequestedevent = new InfoBusItemRequestedEvent(s, adataflavor, infobusdataconsumer);
		Object obj = null;
		for (Enumeration enumeration = vector1.elements(); enumeration.hasMoreElements() && obj == null;) {
			Object obj1 = enumeration.nextElement();
			if (obj1 != null) {
				((InfoBusDataProducer)obj1).dataItemRequested(infobusitemrequestedevent);
				obj = infobusitemrequestedevent.getDataItem();
			}
		}

		return obj;
	}

	public void fireItemAvailable(String s, DataFlavor adataflavor[], InfoBusDataProducer infobusdataproducer, InfoBusDataConsumer infobusdataconsumer) {
		sm_IBPolicy.canFireItemAvailable(this, s, infobusdataproducer);
		InfoBusItemAvailableEvent infobusitemavailableevent = new InfoBusItemAvailableEvent(s, adataflavor, infobusdataproducer);
		if (infobusdataconsumer != null)
			infobusdataconsumer.dataItemAvailable(infobusitemavailableevent);
	}

	public void fireItemAvailable(String s, DataFlavor adataflavor[], InfoBusDataProducer infobusdataproducer, Vector vector) {
		sm_IBPolicy.canFireItemAvailable(this, s, infobusdataproducer);
		Vector vector1 = (Vector)vector.clone();
		InfoBusItemAvailableEvent infobusitemavailableevent = new InfoBusItemAvailableEvent(s, adataflavor, infobusdataproducer);
		for (Enumeration enumeration = vector1.elements(); enumeration.hasMoreElements();) {
			Object obj = enumeration.nextElement();
			if (obj != null)
				((InfoBusDataConsumer)obj).dataItemAvailable(infobusitemavailableevent);
		}

	}

	public void fireItemRevoked(String s, InfoBusDataProducer infobusdataproducer, InfoBusDataConsumer infobusdataconsumer) {
		sm_IBPolicy.canFireItemRevoked(this, s, infobusdataproducer);
		InfoBusItemRevokedEvent infobusitemrevokedevent = new InfoBusItemRevokedEvent(s, infobusdataproducer);
		if (infobusdataconsumer != null)
			infobusdataconsumer.dataItemRevoked(infobusitemrevokedevent);
	}

	public void fireItemRevoked(String s, InfoBusDataProducer infobusdataproducer, Vector vector) {
		sm_IBPolicy.canFireItemRevoked(this, s, infobusdataproducer);
		Vector vector1 = (Vector)vector.clone();
		InfoBusItemRevokedEvent infobusitemrevokedevent = new InfoBusItemRevokedEvent(s, infobusdataproducer);
		for (Enumeration enumeration = vector1.elements(); enumeration.hasMoreElements();) {
			Object obj = enumeration.nextElement();
			if (obj != null)
				((InfoBusDataConsumer)obj).dataItemRevoked(infobusitemrevokedevent);
		}

	}

}
