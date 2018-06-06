package javax.infobus;

import java.beans.PropertyChangeListener;
import java.beans.PropertyVetoException;
import java.beans.VetoableChangeListener;

// Referenced classes of package javax.infobus:
//			InfoBus

public interface InfoBusMember {

	public abstract void setInfoBus(InfoBus infobus) throws PropertyVetoException;

	public abstract InfoBus getInfoBus();

	public abstract void addInfoBusVetoableListener(VetoableChangeListener vetoablechangelistener);

	public abstract void removeInfoBusVetoableListener(VetoableChangeListener vetoablechangelistener);

	public abstract void addInfoBusPropertyListener(PropertyChangeListener propertychangelistener);

	public abstract void removeInfoBusPropertyListener(PropertyChangeListener propertychangelistener);
}
