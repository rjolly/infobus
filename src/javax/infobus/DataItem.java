package javax.infobus;


// Referenced classes of package javax.infobus:
//			InfoBusEventListener

public interface DataItem {

	public abstract Object getProperty(String s);

	public abstract InfoBusEventListener getSource();

	public abstract void release();
}
