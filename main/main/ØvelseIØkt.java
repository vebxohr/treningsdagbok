package main;

import java.sql.Date;
import java.sql.Time;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ListProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.ObservableList;

public class ØvelseIØkt {
	
	private final StringProperty apparatnavn = new SimpleStringProperty(this, "apparatnavn");
	private final StringProperty beskrivelse = new SimpleStringProperty(this, "beskrivelse");
	
	public ØvelseIØkt() {
		
	}
	
	public ØvelseIØkt(Date dato, Time starttid, String øvelsenavn, int kg, int sett, int reps, Time tidsbruk, String apparatnavn, String beskrivelse) {
		
		this.setØvelsenavn(øvelsenavn);
		this.setKg(kg);
		this.setSett(sett);
		this.setReps(reps);
		this.setTidsbruk(tidsbruk);
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

	private final IntegerProperty øktID = new SimpleIntegerProperty(this, "øktID");
	public IntegerProperty øktIDProperty() {
		return øktID;
	}
	public final int getØktID() {
		return øktIDProperty().get();
	}
	public final void setØktID(int øktID) {
		øktIDProperty().set(øktID);
	}
	
	
	private final ObjectProperty<Time> tidsbruk= new SimpleObjectProperty<Time>(this, "tidsbruk");
	public ObjectProperty<Time> tidsbrukProperty() {
		return tidsbruk;
	}
	public final Time getTidsbruk() {
		return tidsbrukProperty().get();
	}
	public final void setTidsbruk (Time tidsbruk) {
		tidsbrukProperty().set(tidsbruk);
	}
	
	private final StringProperty øvelsenavn= new SimpleStringProperty(this, "øvelsenavn");
	public StringProperty øvelsenavnProperty() {
		return øvelsenavn;
	}
	public final String getøvelsenavn() {
		return øvelsenavnProperty().get();
	}
	public final void setØvelsenavn (String øvelsenavn) {
		øvelsenavnProperty().set(øvelsenavn);
	}
	

	private final IntegerProperty kg = new SimpleIntegerProperty(this, "kg");
	public IntegerProperty kgProperty() {
		return kg;
	}
	public final int getKg() {
		return kgProperty().get();
	}
	public final void setKg(int kg) {
		kgProperty().set(kg);
	}
	
	private final IntegerProperty sett = new SimpleIntegerProperty(this, "sett");
	public IntegerProperty settProperty() {
		return sett;
	}
	public final int getSett() {
		return settProperty().get();
	}
	public final void setSett(int sett) {
		settProperty().set(sett);
	}
	
	private final IntegerProperty reps = new SimpleIntegerProperty(this, "reps");
	public IntegerProperty repsProperty() {
		return reps;
	}
	public final int getReps() {
		return repsProperty().get();
	}
	public final void setReps(int reps) {
		repsProperty().set(reps);
	}
	
	@Override
	public String toString() {
		
		String øvelsenavnString = this.getøvelsenavn();
		
//		String beskrivelseString;
//		if (this.getBeskrivelse().trim().equals(""))
//			beskrivelseString = "";
//		else 
//			beskrivelseString = "\nBeskrivelse: " + this.getBeskrivelse();
		
		String kgString;
		if (this.getKg() == 0)
			kgString = "";
		else 
			kgString = "KG:  " + this.getKg();
		
//		String apparatnavnString;
//		if (this.getApparatnavn().trim().equals(""))
//			apparatnavnString = "";
//		else
//			apparatnavnString = "\nApparat: " + this.getApparatnavn();
		
		String settString;
		if (this.getSett() == 0)
			settString = "";
		else
			settString = "  Sett:  " + this.getSett();
		
		String repsString;
		if (this.getReps() == 0) {
			repsString = "";
		}
		else
			repsString = "  Reps:  " + this.getReps();
		
		String tidsbrukString;
		if (this.getTidsbruk().equals(Time.valueOf("00:00:00"))) {
			tidsbrukString = "";
		}
		else {
			tidsbrukString = "\nTidsbruk:  " + this.getTidsbruk();
		}
		String finalString = øvelsenavnString + "\n" + kgString + settString + repsString  + tidsbrukString;
		return finalString.trim();
//		return "hallo";
	}
		
	
	public static void main(String[] args) {
//		ØvelseIØkt øvelse = new ØvelseIØkt(1, "benkpress", 40, 3, 8, "");
//		System.out.println(øvelse);
		LocalTime time = LocalTime.parse("10:15:30");
		System.out.println(time);
	}
	
	
	
	

}
