package javax.infobus;

import java.awt.datatransfer.DataFlavor;
import java.util.Vector;

// Referenced classes of package javax.infobus:
//			InfoBusDataConsumer, InfoBusDataProducer

public interface InfoBusDataController {

	public abstract void setConsumerList(Vector vector);

	public abstract void setProducerList(Vector vector);

	public abstract void addDataConsumer(InfoBusDataConsumer infobusdataconsumer);

	public abstract void addDataProducer(InfoBusDataProducer infobusdataproducer);

	public abstract void removeDataConsumer(InfoBusDataConsumer infobusdataconsumer);

	public abstract void removeDataProducer(InfoBusDataProducer infobusdataproducer);

	public abstract boolean fireItemAvailable(String s, DataFlavor adataflavor[], InfoBusDataProducer infobusdataproducer);

	public abstract boolean fireItemRevoked(String s, InfoBusDataProducer infobusdataproducer);

	public abstract boolean findDataItem(String s, DataFlavor adataflavor[], InfoBusDataConsumer infobusdataconsumer, Vector vector);

	public abstract boolean findMultipleDataItems(String s, DataFlavor adataflavor[], InfoBusDataConsumer infobusdataconsumer, Vector vector);
}
