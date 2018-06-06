package javax.infobus;

import java.beans.PropertyChangeEvent;

// Referenced classes of package javax.infobus:
//			InfoBus, InfoBusMember, InfoBusDataController, InfoBusDataProducer, 
//			InfoBusDataConsumer

public interface InfoBusPolicyHelper {

	public abstract String generateDefaultName(Object obj);

	public abstract void canGet(String s);

	public abstract void canJoin(InfoBus infobus, InfoBusMember infobusmember);

	public abstract void canRegister(InfoBus infobus, InfoBusMember infobusmember);

	public abstract void canPropertyChange(InfoBus infobus, PropertyChangeEvent propertychangeevent);

	public abstract void canAddDataController(InfoBus infobus, InfoBusDataController infobusdatacontroller, int i);

	public abstract void canAddDataProducer(InfoBus infobus, InfoBusDataProducer infobusdataproducer);

	public abstract void canAddDataConsumer(InfoBus infobus, InfoBusDataConsumer infobusdataconsumer);

	public abstract void canFireItemAvailable(InfoBus infobus, String s, InfoBusDataProducer infobusdataproducer);

	public abstract void canFireItemRevoked(InfoBus infobus, String s, InfoBusDataProducer infobusdataproducer);

	public abstract void canRequestItem(InfoBus infobus, String s, InfoBusDataConsumer infobusdataconsumer);
}
