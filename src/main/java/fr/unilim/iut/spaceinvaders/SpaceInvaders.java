package fr.unilim.iut.spaceinvaders;

import java.util.ArrayList;
import java.util.List;

import javax.sound.midi.MidiSystem;

import fr.unilim.iut.spaceinvaders.utils.*;
import moteurJeu.Commande;
import moteurJeu.Constante;
import moteurJeu.Jeu;

public class SpaceInvaders implements Jeu {

	int longueur;
	int hauteur;
	Vaisseau vaisseau;
	List<Missile> missileVaisseau = new ArrayList<Missile>();
	List<Missile> missileEnvahisseur = new ArrayList<Missile>();
	List<Envahisseur> envahisseur = new ArrayList<Envahisseur>();
	Cadence cadVaisseau = new Cadence();
	Cadence cadEnvahisseur = new Cadence();
	private boolean etreFini;
	private int score = Constante.SCORE_AU_DEBUT_DU_JEU;

	public void initialiserJeu() {
		Position positionVaisseau = new Position(this.longueur / 2, this.hauteur - 1);
		Dimension dimensionVaisseau = new Dimension(Constante.VAISSEAU_LONGUEUR, Constante.VAISSEAU_HAUTEUR);
		positionnerUnNouveauVaisseau(dimensionVaisseau, positionVaisseau, Constante.VAISSEAU_VITESSE);

		Dimension dimensionEnvahisseur = new Dimension(Constante.ENVAHISSEUR_LONGUEUR, Constante.ENVAHISSEUR_HAUTEUR);

		for (int i = 0; i < Constante.NOMBRE_ENVAHISSEUR; i++) {
			positionnerUnNouveauEnvahisseur(dimensionEnvahisseur,
					new Position(2 * i * Constante.ENVAHISSEUR_LONGUEUR, Constante.ENVAHISSEUR_HAUTEUR),
					Constante.ENVAHISSEUR_VITESSE);
		}
	}

	public SpaceInvaders(int longueur, int hauteur) {
		this.longueur = longueur;
		this.hauteur = hauteur;
	}

	public String recupererEspaceJeuDansChaineASCII() {
		StringBuilder espaceDeJeu = new StringBuilder();
		for (int y = 0; y < hauteur; y++) {
			for (int x = 0; x < longueur; x++) {
				espaceDeJeu.append(recupererMarqueDeLaPosition(x, y));
			}
			espaceDeJeu.append(Constante.MARQUE_FIN_LIGNE);
		}
		return espaceDeJeu.toString();
	}

	private char recupererMarqueDeLaPosition(int x, int y) {
		char marque = Constante.MARQUE_VIDE;
		if (this.aUnVaisseauQuiOccupeLaPosition(x, y)) {
			marque = Constante.MARQUE_VAISSEAU;
		}
		if (!missileVaisseau.isEmpty()) {
			for (int numMissile = 0; numMissile < getNombreMissileVaisseau(); numMissile++) {
				if (this.aUnMissileVaisseauQuiOccupeLaPosition(x, y, numMissile)) {
					marque = Constante.MARQUE_MISSILE_VAISSEAU;
				}

			}
		}
		if (!missileEnvahisseur.isEmpty()) {
			for (int numMissile = 0; numMissile < getNombreMissileEnvahisseur(); numMissile++) {
				if (this.aUnMissileEnvahisseurQuiOccupeLaPosition(x, y, numMissile)) {
					marque = Constante.MARQUE_MISSILE_ENVAHISSEUR;
				}
			}

		}
		if (!envahisseur.isEmpty()) {
			for (int numEnvahisseur = 0; numEnvahisseur < getNombreEnvahisseur(); numEnvahisseur++) {
				if (this.aUnEnvahisseurQuiOccupeLaPosition(x, y, numEnvahisseur)) {
					marque = Constante.MARQUE_ENVAHISSEUR;
				}
			}

		}

		return marque;
	}

	private int getNombreMissileEnvahisseur() {
		return this.missileEnvahisseur.size();
	}

	private boolean aUnEnvahisseurQuiOccupeLaPosition(int x, int y, int index) {
		return this.aUnEnvahisseur(index) && envahisseur.get(index).occupeLaPosition(x, y);
	}

	public boolean aUnEnvahisseur(int index) {
		if (envahisseur.isEmpty())
			return false;

		return envahisseur.get(index) != null;
	}

	private boolean aUnMissileVaisseauQuiOccupeLaPosition(int x, int y, int index) {
		return (this.aUnMissileVaisseau(index) && missileVaisseau.get(index).occupeLaPosition(x, y));
	}

	private boolean aUnMissileEnvahisseurQuiOccupeLaPosition(int x, int y, int index) {
		return (this.aUnMissileEnvahisseur(index) && missileEnvahisseur.get(index).occupeLaPosition(x, y));
	}

	public boolean aUnMissileVaisseau(int index) {
		if (missileVaisseau.isEmpty())
			return false;

		return missileVaisseau.get(index) != null;
	}

	public boolean aUnMissileEnvahisseur(int index) {
		if (missileEnvahisseur.isEmpty())
			return false;

		return missileEnvahisseur.get(index) != null;
	}

	private boolean aUnVaisseauQuiOccupeLaPosition(int x, int y) {
		return this.aUnVaisseau() && vaisseau.occupeLaPosition(x, y);
	}

	public boolean aUnVaisseau() {
		return vaisseau != null;
	}

	public boolean estDansEspaceJeu(int x, int y) {
		return ((x >= 0) && (x < longueur)) && ((y >= 0) && (y < hauteur));
	}

	public void positionnerUnNouveauVaisseau(Dimension dimension, Position position, int vitesse) {

		int x = position.abscisse();
		int y = position.ordonnee();

		if (!estDansEspaceJeu(x, y))
			throw new HorsEspaceJeuException("La position du vaisseau est en dehors de l'espace jeu");

		int longueurVaisseau = dimension.longueur();
		int hauteurVaisseau = dimension.hauteur();

		if (!estDansEspaceJeu(x + longueurVaisseau - 1, y))
			throw new DebordementEspaceJeuException(
					"Le vaisseau déborde de l'espace jeu vers la droite à cause de sa longueur");
		if (!estDansEspaceJeu(x, y - hauteurVaisseau + 1))
			throw new DebordementEspaceJeuException(
					"Le vaisseau déborde de l'espace jeu vers le bas à cause de sa hauteur");

		vaisseau = new Vaisseau(dimension, position, vitesse);
	}

	public Vaisseau recupererUnVaisseau() {
		return this.vaisseau;
	}

	public Missile recupererUnMissileVaisseau(int index) {
		return this.missileVaisseau.get(index);
	}
	
	public Missile recupererUnMissileEnvahisseur(int index) {
		return this.missileEnvahisseur.get(index);
	}


	@Override
	public void evoluer(Commande c) {
		for (int numMissile = 0; numMissile < getNombreMissileVaisseau(); numMissile++) {
			if (this.aUnMissileVaisseau(numMissile)) {
				deplacerMissileVaisseau();
			}
		}
		
		for (int numMissile = 0; numMissile < getNombreMissileEnvahisseur(); numMissile++) {
			if (this.aUnMissileEnvahisseur(numMissile)) {
				deplacerMissileEnvahisseur();
			}
		}

		for (int numEnvahisseur = 0; numEnvahisseur < this.envahisseur.size(); numEnvahisseur++) {
			if (this.aUnEnvahisseur(numEnvahisseur)) {
				deplacerEnvahisseur();
			}
		}
		if (c.gauche) {
			deplacerSpriteVersLaGauche(recupererUnVaisseau());
		}

		if (c.droite) {
			deplacerSpriteVersLaDroite(recupererUnVaisseau());
		}

		if (c.tir && cadVaisseau.getDuree() > Constante.CADENCE_TIR) {
			tirerUnMissileVaisseau(new Dimension(Constante.MISSILE_LONGUEUR, Constante.MISSILE_HAUTEUR),
					Constante.MISSILE_VITESSE);
		}

	}

	@Override
	public boolean etreFini() {
		return etreFini;
	}

	public void tirerUnMissileVaisseau(Dimension dimensionMissile, int vitesseMissile) {

		if ((vaisseau.dimension.hauteur() + dimensionMissile.hauteur()) > this.hauteur)
			throw new MissileException(
					"Pas assez de hauteur libre entre le vaisseau et le haut de l'espace jeu pour tirer le missile");

		Missile nouveauMissile = this.vaisseau.tirerUnMissile(dimensionMissile, vitesseMissile);
		nouveauMissile = nouveauMissileVaisseauPeutEtreTirer(nouveauMissile);
		if (nouveauMissile != null) {
			tireUnNouveauMissileVaisseau(nouveauMissile);
		}

	}

	public void tirerUnMissileEnvahisseur(Dimension dimensionMissile, int vitesseMissile, Envahisseur tireur) {

		if ((tireur.dimension.hauteur() + dimensionMissile.hauteur()) > this.hauteur)
			throw new MissileException(
					"Pas assez de hauteur libre entre l'envahisseur et le haut de l'espace jeu pour tirer le missile");

		Missile nouveauMissile = tireur.tirerUnMissile(dimensionMissile, vitesseMissile);
		nouveauMissile = nouveauMissileEnvahisseurPeutEtreTirer(nouveauMissile);
		if (nouveauMissile != null) {
			tireUnNouveauMissileEnvahisseur(nouveauMissile);
		}

	}

	private void tireUnNouveauMissileVaisseau(Missile nouveauMissile) {
		cadVaisseau.Start();
		this.missileVaisseau.add(nouveauMissile);
	}

	private void tireUnNouveauMissileEnvahisseur(Missile nouveauMissile) {
		cadEnvahisseur.Start();
		this.missileEnvahisseur.add(nouveauMissile);
	}

	private Missile nouveauMissileVaisseauPeutEtreTirer(Missile nouveauMissile) {
		for (int i = 0; i < getNombreMissileVaisseau(); i++) {
			if (this.missileVaisseau.get(i).detecterCollision(nouveauMissile)
					|| cadVaisseau.getDuree() < Constante.CADENCE_TIR) {
				nouveauMissile = null;
			}
		}
		return nouveauMissile;
	}

	private Missile nouveauMissileEnvahisseurPeutEtreTirer(Missile nouveauMissile) {
		for (int i = 0; i < getNombreMissileEnvahisseur(); i++) {
			if (cadEnvahisseur.getDuree() < Constante.CADENCE_TIR) {
				nouveauMissile = null;
			}
		}
		return nouveauMissile;
	}

	private int getNombreMissileVaisseau() {
		return missileVaisseau.size();
	}

	public void deplacerMissileVaisseau() {
		int index = 0;
		for (index = 0; index < getNombreMissileVaisseau(); index++) {
			int x = this.missileVaisseau.get(index).abscisseLaPlusAGauche();
			int y = this.missileVaisseau.get(index).ordonneeLaPlusHaute();

			if (!estDansEspaceJeu(x, y)) {
				this.missileVaisseau.remove(index);
			}

			if (this.aUnMissileVaisseau(index)) {
				for (int i = 0; i < this.missileVaisseau.get(index).vitesse; i++) {
					this.missileVaisseau.get(index).deplacerVerticalementVers(Direction.HAUT_ECRAN);
					for (int numEnvahisseur = 0; numEnvahisseur < getNombreEnvahisseur(); numEnvahisseur++) {
						if (this.aUnEnvahisseur(numEnvahisseur) && this.missileVaisseau.get(index)
								.detecterCollision(this.envahisseur.get(numEnvahisseur))) {
							this.envahisseur.remove(numEnvahisseur);
							score += Constante.SCORE_ENVAHISSEUR;
							if (this.envahisseur.isEmpty()) {
								score += Constante.SCORE_DESTRUCTION_LIGNE_ENVAHISSEUR;
								etreFini = true;
							}
						}
					}

				}
			}
		}
	}

	public void deplacerMissileEnvahisseur() {
		int index = 0;
		for (index = 0; index < getNombreMissileEnvahisseur(); index++) {
			int x = this.missileEnvahisseur.get(index).abscisseLaPlusAGauche();
			int y = this.missileEnvahisseur.get(index).ordonneeLaPlusHaute();

			if (!estDansEspaceJeu(x, y)) {
				this.missileEnvahisseur.remove(index);
			}

			if (this.aUnMissileEnvahisseur(index)) {
				for (int i = 0; i < this.missileEnvahisseur.get(index).vitesse; i++) {
					this.missileEnvahisseur.get(index).deplacerVerticalementVers(Direction.BAS_ECRAN);

					if (this.aUnVaisseau() && this.missileEnvahisseur.get(index).detecterCollision(this.vaisseau)) {
						this.vaisseau = null;
						etreFini = true;
					}
				}
			}
		}
	}

	private int getNombreEnvahisseur() {
		return envahisseur.size();
	}

	public void deplacerEnvahisseur() {
		for (int numEnvahisseur = 0; numEnvahisseur < getNombreEnvahisseur(); numEnvahisseur++) {
			if (envahisseur.get(0).abscisseLaPlusAGauche() <= 0) {
				envahisseur.get(numEnvahisseur).setDirection(Direction.DROITE);
			}

			if (envahisseur.get(getNombreEnvahisseur() - 1).abscisseLaPlusADroite() >= longueur) {
				envahisseur.get(numEnvahisseur).setDirection(Direction.GAUCHE);
			}
		}

		for (int numEnvahisseur = 0; numEnvahisseur < getNombreEnvahisseur(); numEnvahisseur++) {

			deplaceUnSpriteDeSaVitessePixelParPixel(envahisseur.get(numEnvahisseur),
					envahisseur.get(numEnvahisseur).getDirection());
			tirAleatoirement(envahisseur.get(numEnvahisseur));
		}

	}

	private void tirAleatoirement(Envahisseur envahisseur) {
		if(Math.random()*5 < 1){
			tirerUnMissileEnvahisseur(new Dimension(Constante.MISSILE_LONGUEUR, Constante.MISSILE_HAUTEUR), Constante.MISSILE_VITESSE, this.envahisseur.get((int) (Math.random()*getNombreEnvahisseur())));
		}
		
	}

	public void positionnerUnNouveauEnvahisseur(Dimension dimension, Position position, int vitesse) {
		int x = position.abscisse();
		int y = position.ordonnee();

		if (!estDansEspaceJeu(x, y))
			throw new HorsEspaceJeuException("La position de l'envahisseur est en dehors de l'espace jeu");

		int longueurVaisseau = dimension.longueur();
		int hauteurVaisseau = dimension.hauteur();

		if (!estDansEspaceJeu(x + longueurVaisseau - 1, y))
			throw new DebordementEspaceJeuException(
					"L'envahisseur déborde de l'espace jeu vers la droite à cause de sa longueur");
		if (!estDansEspaceJeu(x, y - hauteurVaisseau + 1))
			throw new DebordementEspaceJeuException(
					"L'envahisseur déborde de l'espace jeu vers le bas à cause de sa hauteur");

		envahisseur.add(new Envahisseur(dimension, position, vitesse));

	}

	public Envahisseur recupererEnvahisseur(int index) {
		return this.envahisseur.get(index);
	}

	public void deplacerSpriteVersLaDroite(Sprite aDeplacer) {
		if (aDeplacer == null)
 			throw new SpriteInexistantException("Le Sprite n'existe pas");
		if (aDeplacer.abscisseLaPlusADroite() < (longueur - 1))
			deplaceUnSpriteDeSaVitessePixelParPixel(aDeplacer, Direction.DROITE);
		if (!estDansEspaceJeu(aDeplacer.abscisseLaPlusADroite(), aDeplacer.ordonneeLaPlusBasse())) {
			aDeplacer.positionner(longueur - aDeplacer.dimension.longueur(), aDeplacer.ordonneeLaPlusHaute());
		}
	}

	private void deplaceUnSpriteDeSaVitessePixelParPixel(Sprite aDeplacer, Direction direction) {
		for (int compteurDeVitesse = 0; compteurDeVitesse < aDeplacer.vitesse; compteurDeVitesse++) {
			aDeplacer.deplacerHorizontalementVers(direction);
		}
	}

	public void deplacerSpriteVersLaGauche(Sprite aDeplacer) {
		if (aDeplacer == null)
			throw new SpriteInexistantException("Le Sprite n'existe pas");
		if (0 < aDeplacer.abscisseLaPlusAGauche())
			deplaceUnSpriteDeSaVitessePixelParPixel(aDeplacer, Direction.GAUCHE);
		if (!estDansEspaceJeu(aDeplacer.abscisseLaPlusAGauche(), aDeplacer.ordonneeLaPlusHaute())) {
			aDeplacer.positionner(0, aDeplacer.ordonneeLaPlusHaute());
		}
	}

	public int getScore() {
		return score;
	}
}
