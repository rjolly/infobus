package javax.infobus;

import java.util.EventObject;

// Referenced classes of package javax.infobus:
//			InfoBusEvent, InfoBusDataProducer

public final class InfoBusItemRevokedEvent extends InfoBusEvent {

	static final byte Copyright_1997_1998_Lotus_Development_Corporation_All_Rights_Reserved = 1;

	InfoBusItemRevokedEvent(String s, InfoBusDataProducer infobusdataproducer) {
		super(s, infobusdataproducer);
	}

	public InfoBusDataProducer getSourceAsProducer() {
		return (InfoBusDataProducer)getSource();
	}
}
