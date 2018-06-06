package javax.infobus;

import java.awt.Component;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.beans.PropertyVetoException;
import java.beans.VetoableChangeListener;
import java.beans.VetoableChangeSupport;

// Referenced classes of package javax.infobus:
//			InfoBus, InfoBusMember, InfoBusMembershipException

public class InfoBusMemberSupport
	implements InfoBusMember {

	static final byte Copyright_1997_1998_Lotus_Development_Corporation_All_Rights_Reserved = 1;
	protected InfoBus m_infoBus;
	protected PropertyChangeSupport m_propSupport;
	protected VetoableChangeSupport m_vetoSupport;
	protected InfoBusMember m_sourceRef;
	protected Object m_syncLock;

	public InfoBusMemberSupport(InfoBusMember infobusmember) {
		if (infobusmember != null)
			m_sourceRef = infobusmember;
		else
			m_sourceRef = this;
		m_propSupport = new PropertyChangeSupport(m_sourceRef);
		m_vetoSupport = new VetoableChangeSupport(m_sourceRef);
		m_syncLock = new Object();
	}

	public InfoBusMemberSupport() {
		this(null);
	}

	public void setInfoBus(InfoBus infobus) throws PropertyVetoException {
		synchronized (m_syncLock) {
			m_vetoSupport.fireVetoableChange("InfoBus", m_infoBus, infobus);
			InfoBus infobus1 = m_infoBus;
			m_infoBus = infobus;
			m_propSupport.firePropertyChange("InfoBus", infobus1, infobus);
			if (m_infoBus != null)
				m_infoBus.register(m_sourceRef);
		}
	}

	public InfoBus getInfoBus() {
		return m_infoBus;
	}

	public void joinInfoBus(String s) throws InfoBusMembershipException, PropertyVetoException {
		synchronized (m_syncLock) {
			if (s == null)
				throw new NullPointerException("joinInfoBus needs a String or Component argument");
			if (getInfoBus() != null)
				throw new InfoBusMembershipException("InfoBus property already set");
			InfoBus infobus = InfoBus.get(s);
			try {
				infobus.join(m_sourceRef);
			}
			finally {
				infobus.release();
			}
		}
	}

	public void joinInfoBus(Component component) throws InfoBusMembershipException, PropertyVetoException {
		synchronized (m_syncLock) {
			if (component == null)
				throw new NullPointerException("joinInfoBus needs a String or Component argument");
			if (getInfoBus() != null)
				throw new InfoBusMembershipException("InfoBus property already set");
			InfoBus infobus = InfoBus.get(component);
			try {
				infobus.join(m_sourceRef);
			}
			finally {
				infobus.release();
			}
		}
	}

	public void leaveInfoBus() throws InfoBusMembershipException, PropertyVetoException {
		synchronized (m_syncLock) {
			if (getInfoBus() == null)
				throw new InfoBusMembershipException("InfoBus property already set to NULL");
			getInfoBus().leave(m_sourceRef);
		}
	}

	public void addInfoBusVetoableListener(VetoableChangeListener vetoablechangelistener) {
		m_vetoSupport.addVetoableChangeListener(vetoablechangelistener);
	}

	public void removeInfoBusVetoableListener(VetoableChangeListener vetoablechangelistener) {
		m_vetoSupport.removeVetoableChangeListener(vetoablechangelistener);
	}

	public void addInfoBusPropertyListener(PropertyChangeListener propertychangelistener) {
		m_propSupport.addPropertyChangeListener(propertychangelistener);
	}

	public void removeInfoBusPropertyListener(PropertyChangeListener propertychangelistener) {
		m_propSupport.removePropertyChangeListener(propertychangelistener);
	}
}
