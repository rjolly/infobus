package javax.infobus;

import java.awt.datatransfer.DataFlavor;
import java.io.PrintStream;
import java.util.Enumeration;
import java.util.Vector;

// Referenced classes of package javax.infobus:
//			InfoBus, InfoBusDataConsumer, InfoBusDataController, InfoBusDataProducer, 
//			InfoBusItemAvailableEvent, InfoBusItemRequestedEvent, InfoBusItemRevokedEvent

final class DefaultController
	implements InfoBusDataController {

	static final byte Copyright_1997_1998_Lotus_Development_Corporation_All_Rights_Reserved = 1;
	private InfoBus m_parent;
	private Vector m_producerList;
	private Vector m_consumerList;

	DefaultController(InfoBus infobus) {
		m_parent = infobus;
	}

	public boolean fireItemAvailable(String s, DataFlavor adataflavor[], InfoBusDataProducer infobusdataproducer) {
		InfoBusItemAvailableEvent infobusitemavailableevent = new InfoBusItemAvailableEvent(s, adataflavor, infobusdataproducer);
		Vector vector = (Vector)m_consumerList.clone();
		for (Enumeration enumeration = vector.elements(); enumeration.hasMoreElements(); ((InfoBusDataConsumer)enumeration.nextElement()).dataItemAvailable(infobusitemavailableevent));
		return true;
	}

	public boolean fireItemRevoked(String s, InfoBusDataProducer infobusdataproducer) {
		InfoBusItemRevokedEvent infobusitemrevokedevent = new InfoBusItemRevokedEvent(s, infobusdataproducer);
		Vector vector = (Vector)m_consumerList.clone();
		for (Enumeration enumeration = vector.elements(); enumeration.hasMoreElements(); ((InfoBusDataConsumer)enumeration.nextElement()).dataItemRevoked(infobusitemrevokedevent));
		return true;
	}

	public boolean findDataItem(String s, DataFlavor adataflavor[], InfoBusDataConsumer infobusdataconsumer, Vector vector) {
		InfoBusItemRequestedEvent infobusitemrequestedevent = new InfoBusItemRequestedEvent(s, adataflavor, infobusdataconsumer);
		Vector vector1 = (Vector)m_producerList.clone();
		for (Enumeration enumeration = vector1.elements(); enumeration.hasMoreElements();) {
			InfoBusDataProducer infobusdataproducer = (InfoBusDataProducer)enumeration.nextElement();
			infobusdataproducer.dataItemRequested(infobusitemrequestedevent);
			Object obj;
			if ((obj = infobusitemrequestedevent.getDataItem()) != null) {
				vector.insertElementAt(obj, 0);
				return true;
			}
		}

		return true;
	}

	public boolean findMultipleDataItems(String s, DataFlavor adataflavor[], InfoBusDataConsumer infobusdataconsumer, Vector vector) {
		InfoBusItemRequestedEvent infobusitemrequestedevent = new InfoBusItemRequestedEvent(s, adataflavor, infobusdataconsumer);
		Vector vector1 = (Vector)m_producerList.clone();
		for (Enumeration enumeration = vector1.elements(); enumeration.hasMoreElements();) {
			InfoBusDataProducer infobusdataproducer = (InfoBusDataProducer)enumeration.nextElement();
			infobusdataproducer.dataItemRequested(infobusitemrequestedevent);
			Object obj;
			if ((obj = infobusitemrequestedevent.getDataItem()) != null) {
				vector.addElement(obj);
				infobusitemrequestedevent.resetDataItem();
			}
		}

		return true;
	}

	public void setConsumerList(Vector vector) {
		m_consumerList = vector;
	}

	public void setProducerList(Vector vector) {
		m_producerList = vector;
	}

	public void addDataConsumer(InfoBusDataConsumer infobusdataconsumer) {
	}

	public void addDataProducer(InfoBusDataProducer infobusdataproducer) {
	}

	public void removeDataConsumer(InfoBusDataConsumer infobusdataconsumer) {
	}

	public void removeDataProducer(InfoBusDataProducer infobusdataproducer) {
	}
}
