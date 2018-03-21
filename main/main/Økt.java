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
//	private List<String> øvelseIØkt;
//	
//	public void setØvelseIØkt(List<String> øvelseIØkt) {
//		this.øvelseIØkt = øvelseIØkt;
//	}
//	
//	public List<String> getØvelseIØkt() {
//		return this.øvelseIØkt;
//	}
	
	private final ListProperty<ØvelseIØkt> øvelseIØkt = new SimpleListProperty<ØvelseIØkt>(this, "øvelseIØkt");
	public ListProperty<ØvelseIØkt> øvelseIØktProperty() {
		return øvelseIØkt;
	}
	public final ObservableList<ØvelseIØkt> getØvelseIØkt() {
		return øvelseIØktProperty().get();
	}
	public final void setØvelseIØkt(ObservableList<ØvelseIØkt> øvelseIØkt) {
		øvelseIØktProperty().set(øvelseIØkt);
	}
	
//	private final StringProperty øvelseIØkt= new SimpleStringProperty(this, "øvelseIØkt");
//	public StringProperty øvelseIØktProperty() {
//		return øvelseIØkt;
//	}
//	public final String getØvelseIØkt() {
//		return øvelseIØktProperty().get();
//	}
//	public final void setØvelseIØkt (String øvelseIØkt) {
//		øvelseIØktProperty().set(øvelseIØkt);
//	}
	
//	private final IntegerProperty øktID = new SimpleIntegerProperty(this, "øktID");
//	public IntegerProperty øktIDProperty() {
//		return øktID;
//	}
//	public final int getØktID() {
//		return øktIDProperty().get();
//	}
//	public final void setØktID(int øktID) {
//		øktIDProperty().set(øktID);
//	}
	
	private final IntegerProperty form = new SimpleIntegerProperty(this, "form");
	public IntegerProperty formProperty() {
		return form;
	}
	public final int getForm() {
		return formProperty().get();
	}
	public final void setForm (int form) {
		formProperty().set(form);
	}
	
	private final StringProperty datoStarttid= new SimpleStringProperty(this, "datoStarttid");
	public StringProperty datoStarttidProperty() {
		return datoStarttid;
	}
	public final String getDatoStarttid() {
		return datoStarttidProperty().get();
	}
	public final void setDatoStarttid (String datoStarttid) {
		datoStarttidProperty().set(datoStarttid);
	}
	
	
	
	
	private final IntegerProperty prestasjon = new SimpleIntegerProperty(this, "prestasjon");
	public IntegerProperty prestasjonProperty() {
		return prestasjon;
	}
	public final int getPreastasjon() {
		return prestasjonProperty().get();
	}
	public final void setPrestasjon (int prestasjon) {
		prestasjonProperty().set(prestasjon);
	}
	
	
	 private final ObjectProperty<LocalDate> dato = new SimpleObjectProperty<LocalDate>(this, "dato");
	 public ObjectProperty<LocalDate> datoProperty() {
		 return dato ;
	 }
	 public final LocalDate getDato() {
	     return datoProperty().get();
	 }
	 public final void setDato(LocalDate dato) {
	     datoProperty().set(dato);
	 }
	 
		
	 private final ObjectProperty<Time> starttid = new SimpleObjectProperty<Time>(this, "starttid");
	 public ObjectProperty<Time> starttidProperty() {
		 return starttid ;
	 }
	 public final Time getStarttid() {
	     return starttidProperty().get();
	 }
	 public final void setStarttid(Time starttid) {
	     starttidProperty().set(starttid);
	 }
	 
	 
	 private final ObjectProperty<Time> varighet = new SimpleObjectProperty<Time>(this, "varighet");
	 public ObjectProperty<Time> varighetProperty() {
		 return varighet ;
	 }
	 public final Time getVarighet() {
	     return varighetProperty().get();
	 }
	 public final void setVarighet(Time varighet) {
	     varighetProperty().set(varighet);
	 }
	 
	 
	 private final StringProperty notat = new SimpleStringProperty(this, "notat");
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
