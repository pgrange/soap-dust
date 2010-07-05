package soapdust;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Set;


public class ComposedValue {

	private HashMap<String, Object> children = new LinkedHashMap<String, Object>(); //ensure parameters are kept in addition order.

	public ComposedValue getComposedValue(String key) {
		Object value = getNonNullValue(key);
		if (value instanceof ComposedValue) {
			ComposedValue composedValue = (ComposedValue) value;
			return composedValue.children.isEmpty() ? null : composedValue; //empty map is null
		} else {
			throw new ClassCastException("key is not of type ComposedValue but of type String. Use getStringValue(" + key + ") instead.");
		}
	}

	public String getStringValue(String key) {
		Object value = getNonNullValue(key);
		if (value instanceof String) {
			return (String) value;
		} else {
			ComposedValue composedValue = (ComposedValue) value;
			if (composedValue.children.isEmpty()) {
				return null; //empty map is null
			} else {
				throw new ClassCastException("key is not of type String but of type ComposedValue. Use getComposedValue(" + key + ") instead.");
			}
		}
	}

	public Object getValue(String key) {
		return getNonNullValue(key);
	}
	
	public void put(String child, Object value) {
		if ((value instanceof String) || (value instanceof ComposedValue)) {
			if (children.get(child) == null) {
				children.put(child, value);
			} else {
				//TODO this sucks !!! Find a better way to represent object with several children having the same name.
				//     see also similar TODO in Client.
				int i = 1;
				for(; children.get(child + i) != null; i++);
				children.put(child + i, value);
			}
		} else {
			String message = "While adding child \"" + child + "\": " 
			+ "Only String and ComposedValue are allowed in ComposedValue, not: " ;
			if (value == null) {
				throw new IllegalArgumentException(message + null);
			} else {
				throw new IllegalArgumentException(message + value.getClass());
			}
		}
	}
	
	public Set<String> getChildrenKeys() {
		return children.keySet();
	}

	//---
	
	@Override
	public String toString() {
		return children.toString();
	}
	
//	@Override
//	public boolean equals(Object obj) {
//		if (obj instanceof ComposedValue) {
//			ComposedValue other = (ComposedValue) obj;
//			return this.children.equals(other.children);
//		} else {
//			return false;
//		}
//	}
//	
//	@Override
//	public int hashCode() {
//		return this.children.hashCode();
//	}
	
	private Object getNonNullValue(String key) {
		Object value = children.get(key);
		if (value == null) {
			StringBuilder msg = new StringBuilder();
			msg.append("unknown key: ");
			msg.append(key);
			msg.append(" known keys are: ");
			for (String knownKey : children.keySet()) {
				msg.append(knownKey);
				msg.append(" ");
			}
			throw new IllegalArgumentException(msg.toString());
		} else {
			return value;
		}
	}
}
