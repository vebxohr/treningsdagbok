package main;

import java.util.List;

import javafx.beans.property.ListProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Gruppe {
	
	private final StringProperty gruppenavn= new SimpleStringProperty(this, "gruppenavn");
	private final ListProperty<Øvelse> øvelser = new SimpleListProperty<Øvelse>(this, "øvelser");

	
	public Gruppe(String gruppenavn) {
		this.setGruppenavn(gruppenavn);
		if (this.getØvelser() == null)
			this.setØvelser(FXCollections.observableArrayList());
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
	
	public ListProperty<Øvelse> øvelserProperty() {
		return øvelser;
	}
	public final ObservableList<Øvelse> getØvelser() {
		return øvelserProperty().get();
	}
	public final void setØvelser (ObservableList<Øvelse> øvelser) {
		øvelserProperty().set(øvelser);
	}
	
	@Override
	public String toString() {
		return this.getGruppenavn();
	}


}
