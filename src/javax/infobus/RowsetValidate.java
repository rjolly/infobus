package javax.infobus;


// Referenced classes of package javax.infobus:
//			RowsetValidationException

public interface RowsetValidate {

	public abstract void validateCurrentRow() throws RowsetValidationException;

	public abstract void validateRowset() throws RowsetValidationException;
}
