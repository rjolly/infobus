package javax.infobus;

import java.sql.SQLException;

// Referenced classes of package javax.infobus:
//			ColumnNotFoundException, DuplicateColumnException, RowsetValidationException, DbAccess

public interface RowsetAccess {

	public abstract int getColumnCount();

	public abstract String getColumnName(int i) throws IndexOutOfBoundsException;

	public abstract int getColumnDatatypeNumber(int i) throws IndexOutOfBoundsException;

	public abstract String getColumnDatatypeName(int i) throws IndexOutOfBoundsException;

	public abstract boolean next() throws SQLException, RowsetValidationException;

	public abstract int getHighWaterMark();

	public abstract boolean hasMoreRows();

	public abstract Object getColumnItem(int i) throws IndexOutOfBoundsException, SQLException;

	public abstract Object getColumnItem(String s) throws ColumnNotFoundException, DuplicateColumnException, SQLException;

	public abstract void newRow() throws SQLException, RowsetValidationException;

	public abstract void setColumnValue(int i, Object obj) throws IndexOutOfBoundsException, SQLException, RowsetValidationException;

	public abstract void setColumnValue(String s, Object obj) throws ColumnNotFoundException, DuplicateColumnException, SQLException, RowsetValidationException;

	public abstract void deleteRow() throws SQLException, RowsetValidationException;

	public abstract void flush() throws SQLException, RowsetValidationException;

	public abstract void lockRow() throws SQLException, RowsetValidationException;

	public abstract boolean canInsert();

	public abstract boolean canUpdate();

	public abstract boolean canUpdate(String s) throws ColumnNotFoundException, DuplicateColumnException;

	public abstract boolean canUpdate(int i) throws IndexOutOfBoundsException;

	public abstract boolean canDelete();

	public abstract DbAccess getDb();
}
