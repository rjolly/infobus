package javax.infobus;


// Referenced classes of package javax.infobus:
//			ArrayAccess

public interface DataItemView {

	public abstract void setViewStart(int i);

	public abstract int getViewStart();

	public abstract void scrollView(int i);

	public abstract ArrayAccess getView(int i);
}
