package main;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.ObservableList;

import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ListProperty;
import javafx.beans.property.ObjectProperty;


public class Økt {
	
	private final ListProperty<ØvelseIØkt> øvelseIØkt = new SimpleListProperty<ØvelseIØkt>(this, "øvelseIØkt");
	private final IntegerProperty form = new SimpleIntegerProperty(this, "form");
	private final StringProperty datoStarttid= new SimpleStringProperty(this, "datoStarttid");
	private final IntegerProperty prestasjon = new SimpleIntegerProperty(this, "prestasjon");
	private final ObjectProperty<LocalDate> dato = new SimpleObjectProperty<LocalDate>(this, "dato");
	private final ObjectProperty<Time> starttid = new SimpleObjectProperty<Time>(this, "starttid");
	private final ObjectProperty<Time> varighet = new SimpleObjectProperty<Time>(this, "varighet");
	private final StringProperty notat = new SimpleStringProperty(this, "notat");
	

	public Økt() {
	}
	
	public Økt(LocalDate dato, Time starttid, Time varighet, int form, int prestasjon, String notat) {
		
		this.setDato(dato);
		this.setStarttid(starttid);
		this.setVarighet(varighet);
		this.setForm(form);
		this.setPrestasjon(prestasjon);
		this.setNotat(notat);
		this.setDatoStarttid(dato.toString() + " " + starttid.toString());
	}
	
	

	public ListProperty<ØvelseIØkt> øvelseIØktProperty() {
		return øvelseIØkt;
	}
	public final ObservableList<ØvelseIØkt> getØvelseIØkt() {
		return øvelseIØktProperty().get();
	}
	public final void setØvelseIØkt(ObservableList<ØvelseIØkt> øvelseIØkt) {
		øvelseIØktProperty().set(øvelseIØkt);
	}

	
	
	public IntegerProperty formProperty() {
		return form;
	}
	public final int getForm() {
		return formProperty().get();
	}
	public final void setForm (int form) {
		formProperty().set(form);
	}
	
	
	public StringProperty datoStarttidProperty() {
		return datoStarttid;
	}
	public final String getDatoStarttid() {
		return datoStarttidProperty().get();
	}
	public final void setDatoStarttid (String datoStarttid) {
		datoStarttidProperty().set(datoStarttid);
	}
		
	
	
	
	public IntegerProperty prestasjonProperty() {
		return prestasjon;
	}
	public final int getPreastasjon() {
		return prestasjonProperty().get();
	}
	public final void setPrestasjon (int prestasjon) {
		prestasjonProperty().set(prestasjon);
	}
	
	
	 
	 public ObjectProperty<LocalDate> datoProperty() {
		 return dato ;
	 }
	 public final LocalDate getDato() {
	     return datoProperty().get();
	 }
	 public final void setDato(LocalDate dato) {
	     datoProperty().set(dato);
	 }
	 
		
	
	 public ObjectProperty<Time> starttidProperty() {
		 return starttid ;
	 }
	 public final Time getStarttid() {
	     return starttidProperty().get();
	 }
	 public final void setStarttid(Time starttid) {
	     starttidProperty().set(starttid);
	 }
	 
	 
	 
	 public ObjectProperty<Time> varighetProperty() {
		 return varighet ;
	 }
	 public final Time getVarighet() {
	     return varighetProperty().get();
	 }
	 public final void setVarighet(Time varighet) {
	     varighetProperty().set(varighet);
	 }
	 
	 
	 
	 public StringProperty notatProperty() {
		 return notat ;
	 }
	 public final String getNotat() {
	     return notatProperty().get();
	 }
	 public final void setNotat(String notat) {
	     notatProperty().set(notat);
	 }



	@Override
	public String toString() {
		return this.getDatoStarttid();
	}
	 
		

}
