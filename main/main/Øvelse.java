package main;

import javafx.beans.property.ListProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Øvelse {
	
	private final StringProperty øvelsenavn= new SimpleStringProperty(this, "øvelsenavn");
	private final ObjectProperty<Apparat> apparat = new SimpleObjectProperty<Apparat>(this, "apparatnavn");
	private final StringProperty beskrivelse = new SimpleStringProperty(this, "apparatnavn");
	private final ListProperty<Resultat> resultater = new SimpleListProperty<Resultat>(this, "resultater");
	private final ListProperty<Gruppe> grupper = new SimpleListProperty<Gruppe>(this, "grupper");
	
	public Øvelse() {
		
	}
	
	public Øvelse(String øvelsenavn, Apparat apparat, String beskrivelse) {
		this.setØvelsenavn(øvelsenavn);
		this.setApparat(apparat);
		this.setBeskrivelse(beskrivelse);
		if (this.getGrupper() == null)
			this.setGrupper(FXCollections.observableArrayList());
	}
	
	public ListProperty<Resultat> resultaterProperty(){
		return resultater;
	}
	public final ObservableList<Resultat> getResultater(){
		return resultaterProperty().get();
	}
	public final void setResultater(ObservableList<Resultat> resultater) {
		resultaterProperty().set(resultater);
	}
	
	public ListProperty<Gruppe> grupperProperty(){
		return grupper;
	}
	public final ObservableList<Gruppe> getGrupper(){
		return grupperProperty().get();
	}
	public final void setGrupper(ObservableList<Gruppe> grupper) {
		grupperProperty().set(grupper);
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
	
	public ObjectProperty<Apparat> apparatProperty() {
		return apparat;
	}
	public final Apparat getApparat() {
		return apparatProperty().get();
	}
	public final void setApparat (Apparat apparat) {
		apparatProperty().set(apparat);
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
	
	
	private String øvelseNavnFormat(String string) {
		return string.substring(0,1).toUpperCase() + string.substring(1).toLowerCase();
	}
	
	public String øvelseInfoString() {
		String beskrivelseString;
		String instruksjonsString;
		if (this.getBeskrivelse().trim().equals(""))
			beskrivelseString = "";
		else 
			beskrivelseString = "Beskrivelse:\n\n" + this.getBeskrivelse();
		if (this.getApparat().getInstruksjoner().trim().equals(""))
			instruksjonsString = "";
		else
			instruksjonsString = this.getApparat().getInstruksjoner();
		
		String apparatnavnString;
		if (this.getApparat().getApparatnavn().trim().equals(""))
			apparatnavnString = "";
		else
			apparatnavnString = "\n\n\n\nApparat: " + this.getApparat().getApparatnavn();
		
		return beskrivelseString;
	}
	
	@Override
	public String toString() {
		return øvelseNavnFormat(this.getØvelsenavn());
	}

	


}
