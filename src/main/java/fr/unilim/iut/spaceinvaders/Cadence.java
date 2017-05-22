package fr.unilim.iut.spaceinvaders;

public class Cadence {
	private long debut;
	
	public void Start(){
		debut = System.currentTimeMillis();
	}
	
	public long getDebut(){
		return debut;
	}
	
	public long getDuree(){
		return System.currentTimeMillis() - debut;
	}
}
