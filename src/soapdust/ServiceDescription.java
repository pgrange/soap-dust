package soapdust;

import java.util.LinkedHashMap;
import java.util.Map;

public class ServiceDescription {

	public Map<String, WsdlOperation> operations = new DefaultToStarMap<WsdlOperation>();
	public Map<String, WsdlElement> messages = new DefaultToStarMap<WsdlElement>();

}

class DefaultToStarMap<V> extends LinkedHashMap<String, V> {
	@Override
	public V get(Object key) {
		V value = super.get(key);
		if (value == null) {
			return super.get("*");
		} else {
			return value;
		}
	}
}
