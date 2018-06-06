package javax.infobus;


// Referenced classes of package javax.infobus:
//			DataItemChangeEvent, InfoBusPropertyMap

public final class DataItemAddedEvent extends DataItemChangeEvent {

	static final byte Copyright_1997_1998_Lotus_Development_Corporation_All_Rights_Reserved = 1;
	private Object m_changedCollection;

	public DataItemAddedEvent(Object obj, Object obj1, Object obj2, InfoBusPropertyMap infobuspropertymap) {
		super(obj, obj1, infobuspropertymap);
		m_changedCollection = obj2;
	}

	public Object getChangedCollection() {
		return m_changedCollection;
	}
}
