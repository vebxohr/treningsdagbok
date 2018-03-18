package main;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Øvelse {
	
	private boolean apparatØvelse;
	
	private final StringProperty øvelsenavn= new SimpleStringProperty(this, "øvelsenavn");
	private final ObjectProperty<Gruppe> gruppenavn= new SimpleObjectProperty<Gruppe>(this, "gruppenavn");
	private final StringProperty apparatnavn = new SimpleStringProperty(this, "apparatnavn");
	private final StringProperty beskrivelse = new SimpleStringProperty(this, "apparatnavn");
	
	public Øvelse() {
		
	}
	
	public Øvelse(String øvelsenavn, Gruppe gruppenavn, String apparatnavn, String beskrivelse) {
		this.setGruppenavn(gruppenavn);
		this.setØvelsenavn(øvelsenavn);
		this.setApparatnavn(apparatnavn);
		this.setBeskrivelse(beskrivelse);
	}
	
	
	public StringProperty øvelsenavnProperty() {
		return øvelsenavn;
	}
	public final String getØvelsenavn() {
		return øvelsenavnProperty().get();
	}
	public final void setØvelsenavn (String øvelsenavn) {
		øvelsenavnProperty().set(øvelsenavn);
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
	
	public StringProperty beskrivelseProperty() {
		return beskrivelse;
	}
	public final String getBeskrivelse() {
		return beskrivelseProperty().get();
	}
	public final void setBeskrivelse (String beskrivelse) {
		beskrivelseProperty().set(beskrivelse);
	}
	

	public ObjectProperty<Gruppe> gruppenavnProperty() {
		return gruppenavn;
	}
	public final Gruppe getGruppenavn() {
		return gruppenavnProperty().get();
	}
	public final void setGruppenavn (Gruppe gruppenavn) {
		gruppenavnProperty().set(gruppenavn);
	}
	
	private String øvelseNavnFormat(String string) {
		return string.substring(0,1).toUpperCase() + string.substring(1).toLowerCase();
	}
	
	public String øvelseInfoString() {
		String beskrivelseString;
		if (this.getBeskrivelse().trim().equals(""))
			beskrivelseString = "";
		else 
			beskrivelseString = "Beskrivelse:\n\n" + this.getBeskrivelse();
		
		String apparatnavnString;
		if (this.getApparatnavn().trim().equals(""))
			apparatnavnString = "";
		else
			apparatnavnString = "\n\n\n\nApparat: " + this.getApparatnavn();
		
		return beskrivelseString;
	}
	
	@Override
	public String toString() {

		
		
		
		return øvelseNavnFormat(this.getØvelsenavn());
	}


}
