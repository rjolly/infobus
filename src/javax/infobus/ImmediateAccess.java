package javax.infobus;

import java.util.Locale;

// Referenced classes of package javax.infobus:
//			InvalidDataException

public interface ImmediateAccess {

	public abstract String getValueAsString();

	public abstract Object getValueAsObject();

	public abstract String getPresentationString(Locale locale);

	public abstract void setValue(Object obj) throws InvalidDataException;
}
