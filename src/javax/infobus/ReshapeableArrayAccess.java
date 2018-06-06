package javax.infobus;


// Referenced classes of package javax.infobus:
//			ArrayAccess

public interface ReshapeableArrayAccess
	extends ArrayAccess {

	public abstract void setDimensions(int ai[]) throws IllegalArgumentException;

	public abstract void insert(int i, int j, int k) throws IllegalArgumentException;

	public abstract void delete(int i, int j, int k) throws IllegalArgumentException;
}
