package fr.unilim.iut.spaceinvaders;

import moteurJeu.Commande;

public class Vaisseau {
	Position origine;
	Dimension dimension;

	public Vaisseau(int longueur, int hauteur) {
		this(longueur, hauteur, 0, 0);
	}

	public Vaisseau(int longueur, int hauteur, int x, int y) {
		this(new Dimension(longueur, hauteur), new Position(x, y));
	}

	public Vaisseau(Dimension dimension, Position positionOrigine) {
		this.dimension = dimension;
		this.origine = positionOrigine;
	}

	public boolean occupeLaPosition(int x, int y) {
		return (estAbscisseCouverte(x) && estOrdonneeCouverte(y));
	}

	private boolean estOrdonneeCouverte(int y) {
		return (ordonneeLaPlusHaute() <= y) && (y <= ordonneeLaPlusBasse());
	}

	private boolean estAbscisseCouverte(int x) {
		return (abscisseLaPlusAGauche() <= x) && (x <= abscisseLaPlusADroite());
	}

	public int ordonneeLaPlusBasse() {
		return this.origine.ordonnee();
	}

	public int ordonneeLaPlusHaute() {
		return ordonneeLaPlusBasse() - this.dimension.hauteur() + 1;
	}

	public int abscisseLaPlusADroite() {
		return abscisseLaPlusAGauche() + this.dimension.longueur() - 1;
	}

	public void seDeplacerVersLaDroite() {
		this.origine.changerAbscisse(this.abscisseLaPlusAGauche() + 1);

	}

	public int abscisseLaPlusAGauche() {
		return this.origine.abscisse();
	}

	public void seDeplacerVersLaGauche() {
		this.origine.changerAbscisse(this.abscisseLaPlusAGauche() - 1);

	}

	public void positionner(int x, int y) {
		this.origine.changerAbscisse(x);
		this.origine.changerOrdonnee(y);

	}
}
