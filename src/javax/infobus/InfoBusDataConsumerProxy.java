package javax.infobus;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

// Referenced classes of package javax.infobus:
//			InfoBusDataConsumer, InfoBusItemAvailableEvent, InfoBusItemRevokedEvent

public class InfoBusDataConsumerProxy
	implements InfoBusDataConsumer {

	private InfoBusDataConsumer m_parent;

	public InfoBusDataConsumerProxy(InfoBusDataConsumer infobusdataconsumer) {
		m_parent = infobusdataconsumer;
	}

	public void dataItemAvailable(InfoBusItemAvailableEvent infobusitemavailableevent) {
		m_parent.dataItemAvailable(infobusitemavailableevent);
	}

	public void dataItemRevoked(InfoBusItemRevokedEvent infobusitemrevokedevent) {
		m_parent.dataItemRevoked(infobusitemrevokedevent);
	}

	public void propertyChange(PropertyChangeEvent propertychangeevent) {
		m_parent.propertyChange(propertychangeevent);
	}
}
