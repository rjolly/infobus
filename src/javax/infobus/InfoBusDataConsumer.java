package javax.infobus;


// Referenced classes of package javax.infobus:
//			InfoBusEventListener, InfoBusItemAvailableEvent, InfoBusItemRevokedEvent

public interface InfoBusDataConsumer
	extends InfoBusEventListener {

	public abstract void dataItemAvailable(InfoBusItemAvailableEvent infobusitemavailableevent);

	public abstract void dataItemRevoked(InfoBusItemRevokedEvent infobusitemrevokedevent);
}
