package soapdust.wsdl;

import java.util.ArrayList;
import java.util.Collection;

public class DelegateType extends Type {

	private final Schema schema;
	private final String delegateNs;
	private final String delegateName;

	public DelegateType(Schema schema, String ns, String name, 
			String delegateNs, String delegateName) {
		super(ns, name);
		this.schema = schema;
		this.delegateNs = delegateNs;
		this.delegateName = delegateName;
	}
	
	@Override
	public Collection<Type> getElements() {
		Type delegate = schema.findType(delegateNs, delegateName);
		return delegate == null ? new ArrayList<Type>() : delegate.getElements();
	}
	
	@Override
	public Type getElement(String name) {
		Type delegate = schema.findType(delegateNs, delegateName);
		return delegate == null ? null : delegate.getElement(name);
	}

	@Override
	public void addElement(String name, Type type) {
		throw new UnsupportedOperationException("you can not add elements to a DelegateType");
	}
}
