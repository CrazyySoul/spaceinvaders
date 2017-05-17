package fr.unilim.iut.spaceinvaders;

public abstract class Sprite {

	public Position origine;
	public Dimension dimension;
	protected int vitesse;
	public Direction direction;

	public Sprite(Dimension dimension, Position origine, int vitesse) {
		super();
		this.origine = origine;
		this.dimension = dimension;
		this.vitesse = vitesse;
	}

	public Sprite() {
		super();
	}

	public boolean occupeLaPosition(int x, int y) {
		return (estAbscisseCouverte(x) && estOrdonneeCouverte(y));
	}

	private boolean estOrdonneeCouverte(int y) {
		return (ordonneeLaPlusBasse() <= y) && (y <= ordonneeLaPlusHaute());
	}

	private boolean estAbscisseCouverte(int x) {
		return (abscisseLaPlusAGauche() <= x) && (x <= abscisseLaPlusADroite());
	}

	public int ordonneeLaPlusHaute() {
		return this.origine.ordonnee();
	}

	public int ordonneeLaPlusBasse() {
		return ordonneeLaPlusHaute() - this.dimension.hauteur() + 1;
	}

	public int abscisseLaPlusADroite() {
		return abscisseLaPlusAGauche() + this.dimension.longueur() - 1;
	}

	public int abscisseLaPlusAGauche() {
		return this.origine.abscisse();
	}

	public void positionner(int x, int y) {
		this.origine.changerAbscisse(x);
		this.origine.changerOrdonnee(y);

	}

	public void deplacerVerticalementVers(Direction direction) {
			this.origine.changerOrdonnee(this.origine.ordonnee() + direction.valeur());
			
	}
	
	public void deplacerHorizontalementVers(Direction direction) {
		this.origine.changerAbscisse(this.origine.abscisse() + direction.valeur());
	}
	
	public Direction getDirection() {
		return direction;
	}

	public void setDirection(Direction direction) {
		this.direction = direction;
	}
	
	public boolean detecterCollision(Sprite sprite2) {
		if (verifierCollision(this, sprite2)) {
			return true;
		}
		return false;

	}

	private boolean verifierCollision(Sprite sprite1, Sprite sprite2) {
		return verifierCollisionAbscisse(sprite1, sprite2) && verifierCollisionOrdonne(sprite1, sprite2);
	}

	private boolean verifierCollisionOrdonne(Sprite sprite2, Sprite sprite1) {
		return (sprite2.ordonneeLaPlusHaute() >= sprite1.ordonneeLaPlusBasse()
				&& sprite2.ordonneeLaPlusHaute() <= sprite1.ordonneeLaPlusHaute())
				|| (sprite2.ordonneeLaPlusBasse() >= sprite1.ordonneeLaPlusBasse()
						&& sprite2.ordonneeLaPlusBasse() <= sprite1.ordonneeLaPlusHaute());
	}

	private boolean verifierCollisionAbscisse(Sprite sprite2, Sprite sprite1) {
		return (sprite2.abscisseLaPlusAGauche() >= sprite1.abscisseLaPlusAGauche()
				&& sprite2.abscisseLaPlusAGauche() <= sprite1.abscisseLaPlusADroite())
				|| (sprite2.abscisseLaPlusADroite() >= sprite1.abscisseLaPlusAGauche()
						&& sprite2.abscisseLaPlusADroite() <= sprite1.abscisseLaPlusADroite());
	}

}