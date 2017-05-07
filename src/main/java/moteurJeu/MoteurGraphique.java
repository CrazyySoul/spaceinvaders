package moteurJeu;

import fr.unilim.iut.spaceinvaders.DessinSpaceInvaders;
import fr.unilim.iut.spaceinvaders.SpaceInvaders;

public class MoteurGraphique {
	/**
	 * le jeu a executer
	 */
	private SpaceInvaders jeu;

	/**
	 * l'interface graphique
	 */
	private InterfaceGraphique gui;

	/**
	 * l'afficheur a utiliser pour le rendu
	 */
	private DessinSpaceInvaders dessin;


	/**
	 * construit un moteur
	 * 
	 * @param pJeu
	 *            jeu a lancer
	 * @param pAffiche
	 *            afficheur a utiliser
	 */
	public MoteurGraphique(SpaceInvaders pJeu, DessinSpaceInvaders pAffiche) {
		// creation du jeu
		this.jeu = pJeu;
		this.dessin = pAffiche;
	}

	/**
	 * permet de lancer le jeu
	 */
	public void lancerJeu(int width, int height) throws InterruptedException {

		// creation de l'interface graphique
		this.gui = new InterfaceGraphique(this.dessin,width,height);
		Controleur controle = this.gui.getControleur();

		// boucle de jeu
		while (!this.jeu.etreFini()) {
			// demande controle utilisateur
			Commande c = controle.getCommande();
			// fait evoluer le jeu
			this.jeu.evoluer(c);
			// affiche le jeu
			this.gui.dessiner();
			// met en attente
			Thread.sleep(100);
		}
	}

}
