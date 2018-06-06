package javax.infobus;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

// Referenced classes of package javax.infobus:
//			InfoBusDataProducer, InfoBusItemRequestedEvent

public class InfoBusDataProducerProxy
	implements InfoBusDataProducer {

	private InfoBusDataProducer m_parent;

	public InfoBusDataProducerProxy(InfoBusDataProducer infobusdataproducer) {
		m_parent = infobusdataproducer;
	}

	public void dataItemRequested(InfoBusItemRequestedEvent infobusitemrequestedevent) {
		m_parent.dataItemRequested(infobusitemrequestedevent);
	}

	public void propertyChange(PropertyChangeEvent propertychangeevent) {
		m_parent.propertyChange(propertychangeevent);
	}
}
