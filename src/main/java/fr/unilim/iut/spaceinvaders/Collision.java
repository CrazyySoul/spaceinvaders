package fr.unilim.iut.spaceinvaders;

public class Collision {

	public boolean detecterCollision(Sprite sprite1, Sprite sprite2) {
		if (verifierCollision(sprite1, sprite2)) {
			return true;
		}
		return false;

	}

	private boolean verifierCollision(Sprite sprite1, Sprite sprite2) {
		return verifierCollisionAbscisse(sprite1, sprite2) && verifierCollisionOrdonne(sprite1, sprite2);
	}

	private boolean verifierCollisionOrdonne(Sprite sprite1, Sprite sprite2) {
		return (sprite2.ordonneeLaPlusHaute() >= sprite1.ordonneeLaPlusBasse()
				&& sprite2.ordonneeLaPlusHaute() <= sprite1.ordonneeLaPlusHaute())
				|| (sprite2.ordonneeLaPlusBasse() >= sprite1.ordonneeLaPlusBasse()
						&& sprite2.ordonneeLaPlusBasse() <= sprite1.ordonneeLaPlusHaute());
	}

	private boolean verifierCollisionAbscisse(Sprite sprite1, Sprite sprite2) {
		return (sprite2.abscisseLaPlusAGauche() >= sprite1.abscisseLaPlusAGauche()
				&& sprite2.abscisseLaPlusAGauche() <= sprite1.abscisseLaPlusADroite())
				|| (sprite2.abscisseLaPlusADroite() >= sprite1.abscisseLaPlusAGauche()
						&& sprite2.abscisseLaPlusADroite() <= sprite1.abscisseLaPlusADroite());
	}

}
