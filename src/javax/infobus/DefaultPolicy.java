package javax.infobus;

import java.applet.Applet;
import java.awt.Component;
import java.beans.PropertyChangeEvent;
import java.io.PrintStream;
import java.net.URL;

// Referenced classes of package javax.infobus:
//			InfoBusPolicyHelper, InfoBus, InfoBusMember, InfoBusDataController, 
//			InfoBusDataProducer, InfoBusDataConsumer

public class DefaultPolicy
	implements InfoBusPolicyHelper {

	static final byte Copyright_1997_1998_Lotus_Development_Corporation_All_Rights_Reserved = 1;

	public String generateDefaultName(Object obj) {
		if (obj instanceof Component)
			return retrieveID((Component)obj);
		else
			return "UniversalDefaultInfoBus";
	}

	private static String retrieveID(Component component) {
		String s = null;
		if (component instanceof Applet)
			s = ((Applet)component).getDocumentBase().toString();
		else
			try {
				s = retrieveID(((Component) (component.getParent())));
			}
			catch (NullPointerException _ex) {
				System.err.println("The Component  " + component.toString() + " passed to javax.infobus.get(Component)" + " must be an Applet or be contained in an Applet.");
			}
		return s;
	}

	public void canGet(String s) {
	}

	public void canJoin(InfoBus infobus, InfoBusMember infobusmember) {
	}

	public void canRegister(InfoBus infobus, InfoBusMember infobusmember) {
	}

	public void canPropertyChange(InfoBus infobus, PropertyChangeEvent propertychangeevent) {
	}

	public void canAddDataController(InfoBus infobus, InfoBusDataController infobusdatacontroller, int i) {
	}

	public void canAddDataProducer(InfoBus infobus, InfoBusDataProducer infobusdataproducer) {
	}

	public void canAddDataConsumer(InfoBus infobus, InfoBusDataConsumer infobusdataconsumer) {
	}

	public void canFireItemAvailable(InfoBus infobus, String s, InfoBusDataProducer infobusdataproducer) {
	}

	public void canFireItemRevoked(InfoBus infobus, String s, InfoBusDataProducer infobusdataproducer) {
	}

	public void canRequestItem(InfoBus infobus, String s, InfoBusDataConsumer infobusdataconsumer) {
	}

}
