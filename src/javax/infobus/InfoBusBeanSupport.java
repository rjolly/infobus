package javax.infobus;

import java.awt.Component;
import java.beans.PropertyChangeSupport;
import java.beans.PropertyVetoException;
import java.beans.VetoableChangeSupport;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

// Referenced classes of package javax.infobus:
//			InfoBusMemberSupport, InfoBus, InfoBusBean, InfoBusMember, 
//			InfoBusMembershipException

public class InfoBusBeanSupport extends InfoBusMemberSupport
	implements InfoBusBean, Serializable {

	static final byte Copyright_1997_1998_Lotus_Development_Corporation_All_Rights_Reserved = 1;
	protected String m_infoBusName;

	public InfoBusBeanSupport(InfoBusMember infobusmember) {
		super(infobusmember);
	}

	public InfoBusBeanSupport(InfoBusMember infobusmember, String s) {
		this(infobusmember);
		m_infoBusName = s;
	}

	public InfoBusBeanSupport() {
		this(null);
	}

	private void writeObject(ObjectOutputStream objectoutputstream) throws IOException {
		objectoutputstream.defaultWriteObject();
		objectoutputstream.writeObject(super.m_sourceRef);
	}

	private void readObject(ObjectInputStream objectinputstream) throws IOException, ClassNotFoundException {
		objectinputstream.defaultReadObject();
		super.m_sourceRef = (InfoBusMember)objectinputstream.readObject();
		super.m_propSupport = new PropertyChangeSupport(super.m_sourceRef);
		super.m_vetoSupport = new VetoableChangeSupport(super.m_sourceRef);
	}

	public void setInfoBus(InfoBus infobus) throws PropertyVetoException {
		synchronized (super.m_syncLock) {
			super.m_vetoSupport.fireVetoableChange("InfoBus", super.m_infoBus, infobus);
			InfoBus infobus1 = super.m_infoBus;
			super.m_infoBus = infobus;
			if (super.m_infoBus != null) {
				super.m_infoBus.register(super.m_sourceRef);
				m_infoBusName = super.m_infoBus.getName();
			} else {
				m_infoBusName = null;
			}
			super.m_propSupport.firePropertyChange("InfoBus", infobus1, infobus);
		}
	}

	public InfoBus getInfoBus() {
		synchronized (super.m_syncLock) {
			InfoBus infobus = super.m_infoBus;
			return infobus;
		}
	}

	public void setInfoBusName(String s) throws InfoBusMembershipException {
		synchronized (super.m_syncLock) {
			if (s == null || s.equals("")) {
				try {
					setInfoBus(null);
				}
				catch (PropertyVetoException _ex) {
					String s1 = super.m_infoBus.getName();
					throw new InfoBusMembershipException("VETO ERROR:  InfoBus change from " + s1 + " to NULL was vetoed.");
				}
			} else {
				InfoBus infobus;
				if (s.equals("-default")) {
					if (super.m_sourceRef instanceof Component)
						infobus = InfoBus.get((Component)super.m_sourceRef);
					else
						throw new InfoBusMembershipException("DEFAULT ERROR: InfoBusBean's parent = " + super.m_sourceRef.toString() + " is not instanceof java.awt.Component.");
				} else {
					infobus = InfoBus.get(s);
				}
				try {
					setInfoBus(infobus);
					if (s.equals("-default"))
						m_infoBusName = s;
				}
				catch (PropertyVetoException _ex) {
					String s2 = super.m_infoBus.getName();
					throw new InfoBusMembershipException("VETO ERROR:  InfoBus change from " + s2 + " to " + s + " was vetoed.");
				}
				finally {
					infobus.release();
				}
			}
		}
	}

	public String getInfoBusName() {
		synchronized (super.m_syncLock) {
			if (m_infoBusName == null) {
				String s = new String("");
				return s;
			}
			String s1 = m_infoBusName;
			return s1;
		}
	}

	public void leaveInfoBus() throws InfoBusMembershipException, PropertyVetoException {
		synchronized (super.m_syncLock) {
			if (getInfoBus() == null)
				throw new InfoBusMembershipException("InfoBus property already set to NULL");
			String s = m_infoBusName;
			getInfoBus().leave(super.m_sourceRef);
			m_infoBusName = s;
		}
	}

	public void rejoinInfoBus() throws InfoBusMembershipException {
		if (super.m_infoBus != null || m_infoBusName == null) {
			return;
		} else {
			setInfoBusName(m_infoBusName);
			return;
		}
	}
}
