package main;

import java.time.LocalDate;

public class Resultat {
	
	private int form;
	private int prestasjon;
	private String notat;
	private LocalDate date;
	
	public Resultat(int form, int prestasjon, String notat, LocalDate date) {
		this.form = form;
		this.prestasjon = prestasjon;
		this.notat = notat;
		this.date = date;
	}

	public LocalDate getDate() {
		return date;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}

	public int getForm() {
		return form;
	}

	public void setForm(int form) {
		this.form = form;
	}

	public int getPrestasjon() {
		return prestasjon;
	}

	public void setPrestasjon(int prestasjon) {
		this.prestasjon = prestasjon;
	}

	public String getNotat() {
		return notat;
	}

	public void setNotat(String notat) {
		this.notat = notat;
	}
	
	@Override
	public String toString() {
		String returnString = "Dato: " +this.getDate() + "\nForm: " + this.getForm() + "\nPrestasjon: " + this.getPrestasjon() + "\nNotat: " + this.getNotat();
		return returnString;
	}
	
	

}
