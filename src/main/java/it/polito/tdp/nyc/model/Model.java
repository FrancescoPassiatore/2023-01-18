package it.polito.tdp.nyc.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;
import org.jgrapht.traverse.DepthFirstIterator;

import com.javadocmd.simplelatlng.LatLng;
import com.javadocmd.simplelatlng.util.LengthUnit;
import com.javadocmd.simplelatlng.LatLngTool;
import it.polito.tdp.nyc.db.NYCDao;

public class Model {
	
	private List<Provider> providers;
	private NYCDao dao;
	private Graph<Location,DefaultWeightedEdge> grafo;
	private List<Location> nodes ;
	private Map <Location,LatLng> mapLocations;
	private Map<Location ,Integer> vicini;
	private List<Location> listaVicini ;
	
	//Per ricorsione
	private List<Location> best;
	private int maxLoc;
	
	public Model() {
		dao = new NYCDao();
		providers = new ArrayList<>(this.dao.getAllProviders());
	}
	
	
	public List<Location> calcolaPercorso(Location target , String s){
		
		List<Location> parziale = new ArrayList<>();
		List<Location> locationPossibili = new ArrayList<>(this.nodes);
		Location l = listaVicini.get((int)Math.random()* listaVicini.size());
		parziale.add(l);
		locationPossibili.remove(l);
		maxLoc=1;
		best = new ArrayList<>(parziale);
		
		recursionPercorso(parziale,1,locationPossibili,s,target);
		
		return best;
		
		
	}
	
	private void recursionPercorso(List<Location> parziale,int livello,  List<Location> locationPossibili, String s ,Location target ) {
		
		if(parziale.size()>this.maxLoc && parziale.get(parziale.size()-1).equals(target)) {
			maxLoc = parziale.size();
			this.best = new ArrayList<>(parziale);
		}
		
		for(Location l : locationPossibili) {
			if(!parziale.contains(l) && !l.getName().contains(s)) {
				System.out.println(livello);
				parziale.add(l);
				recursionPercorso(parziale,livello++,locationPossibili,s,target);
				parziale.remove(parziale.size()-1);
			}
			
		}
		
	}
	
	
	
	public void creaGrafo(double x,Provider p) {
		
		this.grafo = new SimpleWeightedGraph<Location,DefaultWeightedEdge>(DefaultWeightedEdge.class);
		this.loadNodes(p);
		Graphs.addAllVertices(this.grafo, nodes);
		
		for(Location l1 : this.grafo.vertexSet()) {
			for(Location l2 : this.grafo.vertexSet()) {
				if(!l1.equals(l2)) {
					double distBetweenL = LatLngTool.distance(l1.getLatLng(),l2.getLatLng(),LengthUnit.KILOMETER);
					if(distBetweenL <= x) {
						Graphs.addEdge(this.grafo, l1, l2,distBetweenL);
					}	}
			}	
		}
		
		
		
		
	}
	
	public List<String> analisiArchi() {

		vicini = new HashMap<>();
		listaVicini = new ArrayList<>();
		
		for(Location l : this.grafo.vertexSet()) {
			vicini.put(l,Graphs.neighborListOf(this.grafo, l).size());
		}
		
		int numMaxVicini=0;
		
		for(Integer i : vicini.values()) {
			if(i>numMaxVicini) {
				numMaxVicini=i;}
		}
		
		List <String> lista = new ArrayList<>();
		
		for(Location l : vicini.keySet()) {
			if(vicini.get(l)==numMaxVicini) {
			 lista.add(""+l.getName()+ " con numero vicini pari a : "+ vicini.get(l));
			 this.listaVicini.add(l);}
		}
		return lista;
		
	}
	
	public void loadNodes(Provider p) {
		this.nodes = new ArrayList<>(this.dao.getLocationsByProvider(p));
		this.mapLocations = new HashMap<>();
		for(Location l: nodes) {
			this.mapLocations.put(l, l.getLatLng());
		}
	}
	
	public List<Provider> allProviders(){
		return providers;
	}
	
	public List<Location> allLocation(){
		return nodes;
	}
	
	public int nNodes() {
		return this.grafo.vertexSet().size();
	}
	
	public int nEdges() {
		return this.grafo.edgeSet().size();
	}
	
	
	
	
}
