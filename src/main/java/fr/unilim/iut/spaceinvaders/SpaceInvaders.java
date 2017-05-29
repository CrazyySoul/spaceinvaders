package fr.unilim.iut.spaceinvaders;

import java.util.ArrayList;
import java.util.List;

import fr.unilim.iut.spaceinvaders.utils.*;
import moteurJeu.Commande;
import moteurJeu.Constante;
import moteurJeu.Jeu;

public class SpaceInvaders implements Jeu {

	int longueur;
	int hauteur;
	Vaisseau vaisseau;
	List<Missile> missile = new ArrayList<Missile>();
	List<Envahisseur> envahisseur = new ArrayList<Envahisseur>();
	Cadence cad = new Cadence();
	private boolean etreFini;

	public void initialiserJeu() {
		Position positionVaisseau = new Position(this.longueur / 2, this.hauteur - 1);
		Position positionEnvahisseur = new Position(0, Constante.ENVAHISSEUR_HAUTEUR);
		Dimension dimensionVaisseau = new Dimension(Constante.VAISSEAU_LONGUEUR, Constante.VAISSEAU_HAUTEUR);
		Dimension dimensionEnvahisseur = new Dimension(Constante.ENVAHISSEUR_LONGUEUR, Constante.ENVAHISSEUR_HAUTEUR);
		positionnerUnNouveauVaisseau(dimensionVaisseau, positionVaisseau, Constante.VAISSEAU_VITESSE);
		for(int i =0; i<8;i=i+2){
			positionnerUnNouveauEnvahisseur(dimensionEnvahisseur, new Position(i*Constante.ENVAHISSEUR_LONGUEUR, Constante.ENVAHISSEUR_HAUTEUR), Constante.ENVAHISSEUR_VITESSE);
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
		if (this.aUnVaisseauQuiOccupeLaPosition(x, y))
			marque = Constante.MARQUE_VAISSEAU;
		else if (!missile.isEmpty()) {
			for (int numMissile = 0; numMissile < this.missile.size(); numMissile++) {
				if (this.aUnMissileQuiOccupeLaPosition(x, y, numMissile))
					marque = Constante.MARQUE_MISSILE;
			}
		}

		else if (!envahisseur.isEmpty()) {
			for (int numEnvahisseur = 0; numEnvahisseur < this.envahisseur.size(); numEnvahisseur++) {
				if (this.aUnEnvahisseurQuiOccupeLaPosition(x, y, numEnvahisseur))
					marque = Constante.MARQUE_ENVAHISSEUR;
			}
		} else
			marque = Constante.MARQUE_VIDE;
		return marque;
	}

	private boolean aUnEnvahisseurQuiOccupeLaPosition(int x, int y, int index) {
		return this.aUnEnvahisseur(index) && envahisseur.get(index).occupeLaPosition(x, y);
	}

	public boolean aUnEnvahisseur(int index) {
		if (envahisseur.isEmpty())
			return false;

		return envahisseur.get(index) != null;
	}

	private boolean aUnMissileQuiOccupeLaPosition(int x, int y, int index) {
		return this.aUnMissile(index) && missile.get(index).occupeLaPosition(x, y);
	}

	public boolean aUnMissile(int index) {
		if (missile.isEmpty())
			return false;

		return missile.get(index) != null;
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

	public Missile recupererUnMissile(int index) {
		return this.missile.get(index);
	}

	@Override
	public void evoluer(Commande c) {
		for (int numMissile = 0; numMissile < this.missile.size(); numMissile++) {
			if (this.aUnMissile(numMissile)) {
				deplacerMissile();
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

		if (c.tir && cad.getDuree() > Constante.CADENCE_TIR) {
			tirerUnMissile(new Dimension(Constante.MISSILE_LONGUEUR, Constante.MISSILE_HAUTEUR),
					Constante.MISSILE_VITESSE);
		}

	}

	@Override
	public boolean etreFini() {
		// jeu infini
		return etreFini;
	}

	public void tirerUnMissile(Dimension dimensionMissile, int vitesseMissile) {

		if ((vaisseau.dimension.hauteur() + dimensionMissile.hauteur()) > this.hauteur)
			throw new MissileException(
					"Pas assez de hauteur libre entre le vaisseau et le haut de l'espace jeu pour tirer le missile");

		Missile nouveauMissile = this.vaisseau.tirerUnMissile(dimensionMissile, vitesseMissile);
		for (int i = 0; i < missile.size(); i++) {
			if (this.missile.get(i).detecterCollision(nouveauMissile) || cad.getDuree() < Constante.CADENCE_TIR) {
				nouveauMissile = null;
			}
		}
		if (nouveauMissile != null) {
			cad.Start();
			this.missile.add(nouveauMissile);
		}

	}

	public void deplacerMissile() {
		int index = 0;
		for (index = 0; index < missile.size(); index++) {
			int x = this.missile.get(index).abscisseLaPlusAGauche();
			int y = this.missile.get(index).ordonneeLaPlusHaute();

			if (!estDansEspaceJeu(x, y)) {
				this.missile.remove(index);
			}

			if (this.aUnMissile(index)) {
				for (int i = 0; i < this.missile.get(index).vitesse; i++) {
					this.missile.get(index).deplacerVerticalementVers(Direction.HAUT_ECRAN);
					for (int numEnvahisseur = 0; numEnvahisseur < envahisseur.size(); numEnvahisseur++) {
						if (this.aUnEnvahisseur(numEnvahisseur)
								&& this.missile.get(index).detecterCollision(this.envahisseur.get(numEnvahisseur))) {
							this.envahisseur.remove(numEnvahisseur);
							if (this.envahisseur.isEmpty()) {
								etreFini = true;
							}
						}
					}

				}
			}
		}
	}

	public void deplacerEnvahisseur() {
		for (int numEnvahisseur = 0; numEnvahisseur < envahisseur.size(); numEnvahisseur++) {
			if (envahisseur.get(0).abscisseLaPlusAGauche() <= 0) {
				envahisseur.get(numEnvahisseur).setDirection(Direction.DROITE);
			}

			if (envahisseur.get(envahisseur.size() - 1).abscisseLaPlusADroite() >= longueur) {
				envahisseur.get(numEnvahisseur).setDirection(Direction.GAUCHE);
			}
		}

		for (int numEnvahisseur = 0; numEnvahisseur < envahisseur.size(); numEnvahisseur++) {

			deplaceUnSpriteDeSaVitessePixelParPixel(envahisseur.get(numEnvahisseur),
					envahisseur.get(numEnvahisseur).getDirection());
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
}
