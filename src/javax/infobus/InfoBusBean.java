package javax.infobus;


// Referenced classes of package javax.infobus:
//			InfoBusMember, InfoBusMembershipException

public interface InfoBusBean
	extends InfoBusMember {

	public static final String USE_DEFAULT_INFOBUS = "-default";

	public abstract void setInfoBusName(String s) throws InfoBusMembershipException;

	public abstract String getInfoBusName();
}
