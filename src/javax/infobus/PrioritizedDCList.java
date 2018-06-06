package javax.infobus;

import java.io.PrintStream;
import java.util.Enumeration;
import java.util.Vector;

// Referenced classes of package javax.infobus:
//			DefaultController, InfoBus, InfoBusDataController, InfoBusMembershipException

class PrioritizedDCList
	implements Cloneable {

	static final byte Copyright_1997_1998_Lotus_Development_Corporation_All_Rights_Reserved = 1;
	private Vector m_controllerList;
	private Vector m_priorityList;
	private InfoBus m_parentIB;
	private transient Object m_syncLock;

	PrioritizedDCList(int i, int j, InfoBus infobus) {
		m_parentIB = infobus;
		m_controllerList = new Vector(i, j);
		m_priorityList = new Vector(i, j);
		m_syncLock = new Object();
	}

	void addDataController(InfoBusDataController infobusdatacontroller, int i) throws InfoBusMembershipException {
		int j;
		if (i < 1 && !(infobusdatacontroller instanceof DefaultController))
			j = 1;
		else
		if (i > 5 && i != 6)
			j = 5;
		else
			j = i;
		if (m_controllerList.contains(infobusdatacontroller))
			throw new InfoBusMembershipException("Attempt to add DataController already present on InfoBus;  InfoBus:  " + m_parentIB.getName() + ", controller: " + infobusdatacontroller.toString());
		int k = m_priorityList.size();
		int l = 0;
		for (boolean flag = false; !flag && l < k;) {
			Integer integer = (Integer)m_priorityList.elementAt(l);
			if (integer.intValue() < i)
				flag = true;
			else
				l++;
		}

		m_controllerList.insertElementAt(infobusdatacontroller, l);
		m_priorityList.insertElementAt(new Integer(j), l);
	}

	void removeDataController(InfoBusDataController infobusdatacontroller) {
		int i = m_controllerList.indexOf(infobusdatacontroller);
		if (i < 0) {
			return;
		} else {
			m_controllerList.removeElementAt(i);
			m_priorityList.removeElementAt(i);
			return;
		}
	}

	Vector getDCclone() {
		synchronized (m_syncLock) {
			Vector vector = (Vector)m_controllerList.clone();
			return vector;
		}
	}

	public Object clone() {
		synchronized (m_syncLock) {
			PrioritizedDCList prioritizeddclist1 = null;
			try {
				prioritizeddclist1 = (PrioritizedDCList)super.clone();
			}
			catch (CloneNotSupportedException clonenotsupportedexception) {
				System.out.println("InfoBus internal error [PrioritizedDCList] : " + clonenotsupportedexception.toString());
			}
			prioritizeddclist1.m_controllerList = (Vector)m_controllerList.clone();
			prioritizeddclist1.m_priorityList = (Vector)m_priorityList.clone();
			PrioritizedDCList prioritizeddclist = prioritizeddclist1;
			return prioritizeddclist;
		}
	}

	int size() {
		return m_controllerList.size();
	}

	InfoBusDataController controllerAt(int i) {
		return (InfoBusDataController)m_controllerList.elementAt(i);
	}

	int priorityAt(int i) {
		Integer integer = (Integer)m_priorityList.elementAt(i);
		return integer.intValue();
	}

	Enumeration elements() {
		return m_controllerList.elements();
	}
}
