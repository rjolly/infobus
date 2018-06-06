package javax.infobus;


// Referenced classes of package javax.infobus:
//			InvalidDataException

public interface ArrayAccess {

	public abstract Object getItemByCoordinates(int ai[]);

	public abstract void setItemByCoordinates(int ai[], Object obj) throws InvalidDataException;

	public abstract ArrayAccess subdivide(int ai[], int ai1[]);

	public abstract int[] getDimensions();
}
