package javax.infobus;


// Referenced classes of package javax.infobus:
//			DataItemValueChangedEvent, DataItemAddedEvent, DataItemDeletedEvent, DataItemRevokedEvent, 
//			RowsetCursorMovedEvent

public interface DataItemChangeListener {

	public abstract void dataItemValueChanged(DataItemValueChangedEvent dataitemvaluechangedevent);

	public abstract void dataItemAdded(DataItemAddedEvent dataitemaddedevent);

	public abstract void dataItemDeleted(DataItemDeletedEvent dataitemdeletedevent);

	public abstract void dataItemRevoked(DataItemRevokedEvent dataitemrevokedevent);

	public abstract void rowsetCursorMoved(RowsetCursorMovedEvent rowsetcursormovedevent);
}
