package javax.infobus;

import java.util.EventObject;

// Referenced classes of package javax.infobus:
//			InfoBusEventListener

public class InfoBusEvent extends EventObject {

	static final byte Copyright_1997_1998_Lotus_Development_Corporation_All_Rights_Reserved = 1;
	private String m_dataItemName;

	InfoBusEvent(String s, InfoBusEventListener infobuseventlistener) {
		super(infobuseventlistener);
		m_dataItemName = s;
	}

	public String getDataItemName() {
		return m_dataItemName;
	}
}
