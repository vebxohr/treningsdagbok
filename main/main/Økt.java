package main;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.property.IntegerProperty;


public class Økt {
	
	public Økt() {
	}
	
	public Økt(String dato, String starttid, String varighet, int form, int prestasjon, String notat) {
		this.setDato(dato);
		this.setStarttid(starttid);
		this.setVarighet(varighet);
		this.setForm(form);
		this.setPrestasjon(prestasjon);
		this.setNotat(notat);
		this.setDatoStarttid(dato + " " + starttid);
	}
	
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
	
	
	 private final StringProperty dato = new SimpleStringProperty(this, "dato");
	 public StringProperty datoProperty() {
		 return dato ;
	 }
	 public final String getDato() {
	     return datoProperty().get();
	 }
	 public final void setDato(String dato) {
	     datoProperty().set(dato);
	 }
	 
		
	 private final StringProperty starttid = new SimpleStringProperty(this, "starttid");
	 public StringProperty starttidProperty() {
		 return starttid ;
	 }
	 public final String getStarttid() {
	     return starttidProperty().get();
	 }
	 public final void setStarttid(String starttid) {
	     starttidProperty().set(starttid);
	 }
	 
	 
	 private final StringProperty varighet = new SimpleStringProperty(this, "varighet");
	 public StringProperty varighetProperty() {
		 return varighet ;
	 }
	 public final String getVarighet() {
	     return varighetProperty().get();
	 }
	 public final void setVarighet(String varighet) {
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
	 
	 
	 
	 
	
	
	
	

}
