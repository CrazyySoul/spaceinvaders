package fr.unilim.iut.spaceinvaders;

import moteurJeu.Constante;
import moteurJeu.MoteurGraphique;

public class Main {
	public static void main(String[] args) {
		// creation du jeu particulier et de son afficheur
		SpaceInvaders jeu = new SpaceInvaders(Constante.ESPACEJEU_LONGUEUR, Constante.ESPACEJEU_HAUTEUR);
		jeu.initialiserJeu();
		DessinSpaceInvaders aff = new DessinSpaceInvaders(jeu);

		// classe qui lance le moteur de jeu generique
		MoteurGraphique moteur = new MoteurGraphique(jeu, aff);
		try {
			moteur.lancerJeu(Constante.ESPACEJEU_LONGUEUR, Constante.ESPACEJEU_HAUTEUR);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
