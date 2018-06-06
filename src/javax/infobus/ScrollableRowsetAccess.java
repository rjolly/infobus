package javax.infobus;

import java.sql.SQLException;

// Referenced classes of package javax.infobus:
//			RowsetAccess, RowsetValidationException

public interface ScrollableRowsetAccess
	extends RowsetAccess {

	public abstract ScrollableRowsetAccess newCursor();

	public abstract void setBufferSize(int i);

	public abstract int getBufferSize();

	public abstract boolean previous() throws SQLException, RowsetValidationException;

	public abstract boolean first() throws SQLException, RowsetValidationException;

	public abstract boolean last() throws SQLException, RowsetValidationException;

	public abstract boolean relative(int i) throws SQLException, RowsetValidationException;

	public abstract int getRow();

	public abstract int getRowCount();

	public abstract boolean absolute(int i) throws SQLException, RowsetValidationException;
}
