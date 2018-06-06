package javax.infobus;

import java.awt.datatransfer.DataFlavor;
import java.util.EventObject;

// Referenced classes of package javax.infobus:
//			InfoBusEvent, InfoBusDataConsumer

public final class InfoBusItemRequestedEvent extends InfoBusEvent {

	static final byte Copyright_1997_1998_Lotus_Development_Corporation_All_Rights_Reserved = 1;
	private Object m_dataItem;
	private DataFlavor m_flavors[];

	InfoBusItemRequestedEvent(String s, DataFlavor adataflavor[], InfoBusDataConsumer infobusdataconsumer) {
		super(s, infobusdataconsumer);
		m_flavors = adataflavor;
	}

	public DataFlavor[] getDataFlavors() {
		return m_flavors;
	}

	public void setDataItem(Object obj) {
		if (m_dataItem == null)
			m_dataItem = obj;
	}

	public Object getDataItem() {
		return m_dataItem;
	}

	void resetDataItem() {
		m_dataItem = null;
	}

	public InfoBusDataConsumer getSourceAsConsumer() {
		return (InfoBusDataConsumer)getSource();
	}
}
