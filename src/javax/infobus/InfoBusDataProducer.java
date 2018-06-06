package javax.infobus;


// Referenced classes of package javax.infobus:
//			InfoBusEventListener, InfoBusItemRequestedEvent

public interface InfoBusDataProducer
	extends InfoBusEventListener {

	public abstract void dataItemRequested(InfoBusItemRequestedEvent infobusitemrequestedevent);
}
