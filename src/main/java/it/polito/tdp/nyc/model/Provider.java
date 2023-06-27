package it.polito.tdp.nyc.model;

public class Provider {
	
	private String name;

	public Provider(String name) {
		super();
		this.name = name;
	}

	public String getName() {
		return name;
	}

	@Override
	public String toString() {
		return  name ;
	}
	
	

}
