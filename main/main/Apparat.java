package main;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Apparat {
	
	private final StringProperty apparatnavn = new SimpleStringProperty(this, "apparatnavn");
	private final StringProperty instruksjoner = new SimpleStringProperty(this,"instruksjoner");
	
	public Apparat() {
		this.setApparatnavn("");
		this.setInstruksjoner("");
	}
	
	public Apparat(String apparatnavn, String instruksjoner) {
		this.setApparatnavn(apparatnavn);
		this.setInstruksjoner(instruksjoner);
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
	
	public StringProperty instruksjonerProperty() {
		return instruksjoner;
	}
	public final String getInstruksjoner() {
		return instruksjonerProperty().get();
	}
	public final void setInstruksjoner (String instruksjoner) {
		instruksjonerProperty().set(instruksjoner);
	} 
	
	
	private String apparatNavnFormat(String string) {
		return string.substring(0,1).toUpperCase() + string.substring(1).toLowerCase();
	}
	
	@Override
	public String toString() {
		return this.apparatNavnFormat(this.getApparatnavn());
	}
}
