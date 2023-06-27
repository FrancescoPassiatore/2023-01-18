/**
 * Sample Skeleton for 'Scene.fxml' Controller Class
 */

package it.polito.tdp.nyc;

import java.net.URL;
import java.util.ResourceBundle;

import it.polito.tdp.nyc.model.Location;
import it.polito.tdp.nyc.model.Model;
import it.polito.tdp.nyc.model.Provider;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class FXMLController {

	private Model model;
	
    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="btnAnalisi"
    private Button btnAnalisi; // Value injected by FXMLLoader

    @FXML // fx:id="btnCreaGrafo"
    private Button btnCreaGrafo; // Value injected by FXMLLoader

    @FXML // fx:id="btnPercorso"
    private Button btnPercorso; // Value injected by FXMLLoader

    @FXML // fx:id="cmbProvider"
    private ComboBox<Provider> cmbProvider; // Value injected by FXMLLoader

    @FXML // fx:id="txtDistanza"
    private TextField txtDistanza; // Value injected by FXMLLoader

    @FXML // fx:id="txtResult"
    private TextArea txtResult; // Value injected by FXMLLoader

    @FXML // fx:id="txtStringa"
    private TextField txtStringa; // Value injected by FXMLLoader
    
    @FXML // fx:id="txtTarget"
    private ComboBox<Location> txtTarget; // Value injected by FXMLLoader

    @FXML
    void doAnalisiGrafo(ActionEvent event) {
    	this.txtResult.clear();
    	
    	for(String s :this.model.analisiArchi()) {
    		this.txtResult.appendText(s+"\n");
    	}
    	
    }

    @FXML
    void doCalcolaPercorso(ActionEvent event) {
    	this.txtResult.clear();
    	Location l = this.txtTarget.getValue();
    	String s = this.txtStringa.getText();
    	
    	this.txtResult.clear();
    
    	for(Location lo :this.model.calcolaPercorso(l, s)) {
    		this.txtResult.appendText(lo.getName()+"\n");
    	}
    	
    }

    @FXML
    void doCreaGrafo(ActionEvent event) {
    	this.txtResult.clear();
    	double x;
    	try {
    		x = Double.parseDouble(this.txtDistanza.getText());
    	}catch(	NumberFormatException e) {
    		this.txtResult.setText("Inserire dei valori numerici");
    		return;
    	}
    	
    	Provider p = this.cmbProvider.getValue();
    	if(x==0.0) {
    		this.txtResult.appendText("Inserire una distanza maggiore di 0");
    		
    	}
    	if(p== null) {
    		this.txtResult.appendText("Si prega di selezionare un Provider");
    	}
    	
    	this.model.creaGrafo(x, p);
    	this.txtResult.appendText("Grafo creato correttamente\n");
    	this.txtResult.appendText("Ci sono in tutto : "+ this.model.nNodes()+" vertici\n");
    	this.txtResult.appendText("Ci sono in tutto : "+ this.model.nEdges()+" archi\n");
    	this.txtTarget.getItems().addAll( this.model.allLocation());

    }

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert btnAnalisi != null : "fx:id=\"btnAnalisi\" was not injected: check your FXML file 'Scene.fxml'.";
        assert btnCreaGrafo != null : "fx:id=\"btnCreaGrafo\" was not injected: check your FXML file 'Scene.fxml'.";
        assert btnPercorso != null : "fx:id=\"btnPercorso\" was not injected: check your FXML file 'Scene.fxml'.";
        assert cmbProvider != null : "fx:id=\"cmbProvider\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtDistanza != null : "fx:id=\"txtDistanza\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtStringa != null : "fx:id=\"txtStringa\" was not injected: check your FXML file 'Scene.fxml'.";

    }

    public void setModel(Model model) {
    	this.model = model;
    	this.cmbProvider.getItems().addAll(this.model.allProviders());
    }
}
