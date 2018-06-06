package javax.infobus;


// Referenced classes of package javax.infobus:
//			InvalidDataException, InfoBusPropertyMap, RowsetAccess

public class RowsetValidationException extends InvalidDataException {

	static final byte Copyright_1997_1998_Lotus_Development_Corporation_All_Rights_Reserved = 1;
	RowsetAccess m_rowset;
	InfoBusPropertyMap m_map;

	public RowsetValidationException(String s, RowsetAccess rowsetaccess, InfoBusPropertyMap infobuspropertymap) {
		super(s);
		m_rowset = rowsetaccess;
		m_map = infobuspropertymap;
	}

	public RowsetAccess getRowset() {
		return m_rowset;
	}

	public Object getProperty(String s) {
		return m_map.get(s);
	}
}
