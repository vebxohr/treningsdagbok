package main;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Apparat {
	
	private final StringProperty apparatnavn = new SimpleStringProperty(this, "apparatnavn");
	
	
	public Apparat(String apparatnavn) {
		this.setApparatnavn(apparatnavn);
	}

	
	public StringProperty apparatnavnProperty() {
		return apparatnavn;
	}
	public final String getApparatnavn() {
		return apparatnavnProperty().get();
	}
	public final void setApparatnavn (String apparatnavn) {
		apparatnavnProperty().set(apparatnavn);
	}
	
	private String apparatNavnFormat(String string) {
		return string.substring(0,1).toUpperCase() + string.substring(1).toLowerCase();
	}
	
	@Override
	public String toString() {
		return this.apparatNavnFormat(this.getApparatnavn());
	}
}
