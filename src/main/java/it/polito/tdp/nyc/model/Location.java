package it.polito.tdp.nyc.model;

import java.util.Objects;

import com.javadocmd.simplelatlng.LatLng;

public class Location {

	private String name;
	private LatLng latLng;
	
	
	public Location(String name, Double latitude, Double longitude) {
		this.name = name;
		this.latLng = new LatLng(latitude,longitude);
		
	}

	public String getName() {
		return name;
	}
	
	
	public LatLng getLatLng() {
		return latLng;
	}


	@Override
	public int hashCode() {
		return Objects.hash(name);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Location other = (Location) obj;
		return Objects.equals(name, other.name);
	}

	@Override
	public String toString() {
		return  name ;
	}
	
	
	
	
}
