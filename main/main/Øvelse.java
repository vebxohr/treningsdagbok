package main;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Øvelse {
	
	private final StringProperty øvelsenavn= new SimpleStringProperty(this, "øvelsenavn");
	private final StringProperty gruppenavn= new SimpleStringProperty(this, "gruppenavn");
	
	public Øvelse() {
		
	}
	
	public Øvelse(String øvelsenavn, String gruppenavn) {
		this.setGruppenavn(gruppenavn);
		this.setØvelsenavn(øvelsenavn);
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
	

	public StringProperty gruppenavnProperty() {
		return gruppenavn;
	}
	public final String getGruppenavn() {
		return gruppenavnProperty().get();
	}
	public final void setGruppenavn (String gruppenavn) {
		gruppenavnProperty().set(gruppenavn);
	}
	
	private String øvelseNavnFormat(String string) {
		return string.substring(0,1).toUpperCase() + string.substring(1).toLowerCase();
	}
	
	@Override
	public String toString() {
		return øvelseNavnFormat(this.getØvelsenavn());
	}


}
