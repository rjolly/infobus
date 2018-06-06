package javax.infobus;

import java.util.EventObject;

// Referenced classes of package javax.infobus:
//			InfoBusPropertyMap

public class DataItemChangeEvent extends EventObject {

	static final byte Copyright_1997_1998_Lotus_Development_Corporation_All_Rights_Reserved = 1;
	private Object m_changedItem;
	private InfoBusPropertyMap m_propMap;

	DataItemChangeEvent(Object obj, Object obj1, InfoBusPropertyMap infobuspropertymap) {
		super(obj);
		m_changedItem = obj1;
		m_propMap = infobuspropertymap;
	}

	public Object getSource() {
		return super.getSource();
	}

	public Object getChangedItem() {
		return m_changedItem;
	}

	public Object getProperty(String s) {
		if (m_propMap != null)
			return m_propMap.get(s);
		else
			return null;
	}
}
