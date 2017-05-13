package fr.unilim.iut.spaceinvaders;

public class Envahisseur extends Sprite{

	public Envahisseur(Dimension dimension, Position positionOrigine, int vitesse) {
		super(dimension, positionOrigine, vitesse);
	}
	
	public int hauteur(){
		return this.dimension.hauteur();
	}
	
	public int longueur(){
		return this.dimension.longueur();
	}
}
