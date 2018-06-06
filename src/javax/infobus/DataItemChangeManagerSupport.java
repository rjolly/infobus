package javax.infobus;

import java.util.Enumeration;
import java.util.Vector;

// Referenced classes of package javax.infobus:
//			DataItemAddedEvent, DataItemChangeListener, DataItemDeletedEvent, DataItemRevokedEvent, 
//			DataItemShapeChangeListener, DataItemShapeChangedEvent, DataItemValueChangedEvent, RowsetCursorMovedEvent, 
//			InfoBusPropertyMap

public class DataItemChangeManagerSupport {

	static final byte Copyright_1997_1998_Lotus_Development_Corporation_All_Rights_Reserved = 1;
	protected Object m_source;
	protected Vector m_changeListeners;

	public DataItemChangeManagerSupport(Object obj) {
		m_source = obj;
	}

	public synchronized void addDataItemChangeListener(DataItemChangeListener dataitemchangelistener) {
		if (m_changeListeners == null)
			m_changeListeners = new Vector(5, 5);
		m_changeListeners.addElement(dataitemchangelistener);
	}

	public synchronized void removeDataItemChangeListener(DataItemChangeListener dataitemchangelistener) {
		if (m_changeListeners != null) {
			m_changeListeners.removeElement(dataitemchangelistener);
			if (m_changeListeners.isEmpty())
				m_changeListeners = null;
		}
	}

	public synchronized void removeAllListeners() {
		if (m_changeListeners != null) {
			m_changeListeners.removeAllElements();
			m_changeListeners = null;
		}
	}

	protected synchronized Enumeration enumerateListeners() {
		Enumeration enumeration = null;
		if (m_changeListeners != null)
			enumeration = ((Vector)m_changeListeners.clone()).elements();
		return enumeration;
	}

	public void fireItemValueChanged(Object obj, InfoBusPropertyMap infobuspropertymap) {
		Enumeration enumeration = enumerateListeners();
		if (enumeration != null) {
			DataItemValueChangedEvent dataitemvaluechangedevent = new DataItemValueChangedEvent(m_source, obj, infobuspropertymap);
			DataItemChangeListener dataitemchangelistener;
			for (; enumeration.hasMoreElements(); dataitemchangelistener.dataItemValueChanged(dataitemvaluechangedevent))
				dataitemchangelistener = (DataItemChangeListener)enumeration.nextElement();

		}
	}

	public void fireItemAdded(Object obj, Object obj1, InfoBusPropertyMap infobuspropertymap) {
		Enumeration enumeration = enumerateListeners();
		if (enumeration != null) {
			DataItemAddedEvent dataitemaddedevent = new DataItemAddedEvent(m_source, obj, obj1, infobuspropertymap);
			DataItemChangeListener dataitemchangelistener;
			for (; enumeration.hasMoreElements(); dataitemchangelistener.dataItemAdded(dataitemaddedevent))
				dataitemchangelistener = (DataItemChangeListener)enumeration.nextElement();

		}
	}

	public void fireItemDeleted(Object obj, Object obj1, InfoBusPropertyMap infobuspropertymap) {
		Enumeration enumeration = enumerateListeners();
		if (enumeration != null) {
			DataItemDeletedEvent dataitemdeletedevent = new DataItemDeletedEvent(m_source, obj, obj1, infobuspropertymap);
			DataItemChangeListener dataitemchangelistener;
			for (; enumeration.hasMoreElements(); dataitemchangelistener.dataItemDeleted(dataitemdeletedevent))
				dataitemchangelistener = (DataItemChangeListener)enumeration.nextElement();

		}
	}

	public void fireItemRevoked(Object obj, InfoBusPropertyMap infobuspropertymap) {
		Enumeration enumeration = enumerateListeners();
		if (enumeration != null) {
			DataItemRevokedEvent dataitemrevokedevent = new DataItemRevokedEvent(m_source, obj, infobuspropertymap);
			DataItemChangeListener dataitemchangelistener;
			for (; enumeration.hasMoreElements(); dataitemchangelistener.dataItemRevoked(dataitemrevokedevent))
				dataitemchangelistener = (DataItemChangeListener)enumeration.nextElement();

		}
	}

	public void fireRowsetCursorMoved(Object obj, InfoBusPropertyMap infobuspropertymap) {
		Enumeration enumeration = enumerateListeners();
		if (enumeration != null) {
			RowsetCursorMovedEvent rowsetcursormovedevent = new RowsetCursorMovedEvent(m_source, obj, infobuspropertymap);
			DataItemChangeListener dataitemchangelistener;
			for (; enumeration.hasMoreElements(); dataitemchangelistener.rowsetCursorMoved(rowsetcursormovedevent))
				dataitemchangelistener = (DataItemChangeListener)enumeration.nextElement();

		}
	}

	public void fireItemShapeChanged(Object obj, InfoBusPropertyMap infobuspropertymap) {
		Enumeration enumeration = enumerateListeners();
		if (enumeration != null) {
			DataItemShapeChangedEvent dataitemshapechangedevent = new DataItemShapeChangedEvent(m_source, obj, infobuspropertymap);
			while (enumeration.hasMoreElements())  {
				DataItemChangeListener dataitemchangelistener = (DataItemChangeListener)enumeration.nextElement();
				if (dataitemchangelistener instanceof DataItemShapeChangeListener)
					((DataItemShapeChangeListener)dataitemchangelistener).dataItemShapeChanged(dataitemshapechangedevent);
			}
		}
	}
}
