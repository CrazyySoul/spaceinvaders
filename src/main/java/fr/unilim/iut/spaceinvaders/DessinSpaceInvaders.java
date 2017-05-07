package fr.unilim.iut.spaceinvaders;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import moteurJeu.DessinJeu;

public class DessinSpaceInvaders implements DessinJeu{
	/**
	 * lien vers le jeu a afficher
	 */
	private SpaceInvaders jeu;

	public DessinSpaceInvaders(SpaceInvaders j) {
		this.jeu = j;
	}

	/**
	 * dessiner un objet consiste a dessiner sur l'image suivante methode
	 * redefinie de Afficheur
	 */
	private void dessinerObjet(String s, BufferedImage im) {
		Graphics2D crayon = (Graphics2D) im.getGraphics();
		switch (s) {
		case "vaisseau":
			crayon.setColor(Color.gray);
			crayon.fillRect(jeu.getVaisseau().abscisseLaPlusAGauche(), jeu.getVaisseau().ordonneeLaPlusHaute(), jeu.getVaisseau().dimension.longueur, jeu.getVaisseau().dimension.hauteur);
			break;
		default:
			throw new AssertionError("objet inexistant");
		}
	}

	/**
	 * methode dessiner redefinie de Afficheur retourne une image du jeu
	 */
	public void dessiner(BufferedImage im) {
		// no sait que c'est un jeuTest
		SpaceInvaders j = (SpaceInvaders) jeu;
		Vaisseau pj = j.getVaisseau();
		this.dessinerObjet("vaisseau", im);
	}
}
