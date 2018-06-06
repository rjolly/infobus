package javax.infobus;

import java.awt.datatransfer.DataFlavor;
import java.util.EventObject;

// Referenced classes of package javax.infobus:
//			InfoBusEvent, InfoBusDataProducer, InfoBusItemRequestedEvent, InfoBusDataConsumer

public final class InfoBusItemAvailableEvent extends InfoBusEvent {

	static final byte Copyright_1997_1998_Lotus_Development_Corporation_All_Rights_Reserved = 1;
	private DataFlavor m_flavors[];

	InfoBusItemAvailableEvent(String s, DataFlavor adataflavor[], InfoBusDataProducer infobusdataproducer) {
		super(s, infobusdataproducer);
		m_flavors = adataflavor;
	}

	public DataFlavor[] getDataFlavors() {
		return m_flavors;
	}

	public Object requestDataItem(InfoBusDataConsumer infobusdataconsumer, DataFlavor adataflavor[]) {
		InfoBusDataProducer infobusdataproducer = (InfoBusDataProducer)super.source;
		InfoBusItemRequestedEvent infobusitemrequestedevent = new InfoBusItemRequestedEvent(getDataItemName(), adataflavor, infobusdataconsumer);
		infobusdataproducer.dataItemRequested(infobusitemrequestedevent);
		return infobusitemrequestedevent.getDataItem();
	}

	public InfoBusDataProducer getSourceAsProducer() {
		return (InfoBusDataProducer)getSource();
	}
}
