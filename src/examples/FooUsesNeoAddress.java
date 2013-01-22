package examples;

import com.google.inject.AbstractModule;

public class FooUsesNeoAddress extends AbstractModule {
	
	private final String neoAddress;
	
	
	/**
	 * @param address of Neo4j db
	 */
	public FooUsesNeoAddress(String address){
		this.neoAddress = address;
	}

	@Override
	protected void configure() {
		bindConstant().annotatedWith(NeoAddress.class).to(neoAddress);
	}

}
