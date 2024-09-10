package labs.lab9;

import java.util.HashMap;
import java.util.Map;

public class Customer implements Comparable {
	private String name;
	private String email;
	private Boolean[] pets;
	private double totalAmountSpent;
	private String homeStoreLocation;
	private String notes;
	
	public Customer(String name, String email) {
		this.name = name;
		this.email = email;
		// if pet buttons are clicked change to true **CHANGE LATER
		pets = new Boolean[5];
		pets[0] = false;
		pets[1] = false;
		pets[2] = false;
		pets[3] = false;
		pets[4] = false;
		
		totalAmountSpent = 0.0;
		homeStoreLocation = "Irvine"; // CHANGE LATER??
		notes = "";
		
	}
	
	@Override
	public String toString() {
		return name;
	}
	
	// mutators
	public void setName(String name) {
		this.name = name;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public void setTotalAmountSpent(double amount) {
		totalAmountSpent = amount;
	}
	public void setHomeStoreLocation(String location) {
		homeStoreLocation = location;
	}
	public void setNotes(String notes) {
		this.notes = notes;
	}
	
	// accessors
	
	public String getName() {
		return name;
	}
	public String getEmail() {
		return email;
	}
	
	public String getTotalAmountSpent() {
		return String.valueOf(totalAmountSpent);
	}
	
	public String getHomeStoreLocation() {
		return homeStoreLocation;
	}
	
	public String getNotes() {
		return notes;
	}
	public Boolean[] getPets() {
		return pets;
	}

	@Override
	public int compareTo(Object o) {
		Customer c = (Customer) o;
		
		return name.compareTo(c.name);
		
	}
}
