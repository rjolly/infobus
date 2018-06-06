package javax.infobus;

import java.sql.DriverPropertyInfo;
import java.sql.SQLException;
import java.util.Properties;

// Referenced classes of package javax.infobus:
//			RowsetValidationException

public interface DbAccess {

	public abstract void connect() throws SQLException;

	public abstract void connect(String s, String s1, String s2) throws SQLException;

	public abstract void connect(String s, Properties properties) throws SQLException;

	public abstract void disconnect() throws SQLException;

	public abstract DriverPropertyInfo[] getPropertyInfo(String s, Properties properties);

	public abstract Object executeRetrieval(String s, String s1, String s2) throws SQLException;

	public abstract int executeCommand(String s, String s1) throws SQLException;

	public abstract void beginTransaction();

	public abstract void commitTransaction() throws SQLException, RowsetValidationException;

	public abstract void rollbackTransaction() throws SQLException, RowsetValidationException;

	public abstract void validate() throws SQLException, RowsetValidationException;

	public abstract void flush() throws SQLException, RowsetValidationException;
}
