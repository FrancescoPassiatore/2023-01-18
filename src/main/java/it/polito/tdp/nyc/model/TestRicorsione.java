package it.polito.tdp.nyc.model;

public class TestRicorsione {

	public static void main(String[] args) {
		Model model = new Model();
		Provider p = model.allProviders().get(8);
		model.creaGrafo(0.5, p);
		model.analisiArchi();
		Location l = model.allLocation().get(4);
		model.calcolaPercorso(l, "Sud");
		
	}

}
