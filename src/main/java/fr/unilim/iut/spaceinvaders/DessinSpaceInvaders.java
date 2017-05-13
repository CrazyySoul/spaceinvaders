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
	private void dessinerUnVaisseau(Vaisseau v, BufferedImage im) {
		Graphics2D crayon = (Graphics2D) im.getGraphics();
			crayon.setColor(Color.gray);
			crayon.fillRect(v.abscisseLaPlusAGauche(), v.ordonneeLaPlusBasse(), v.longueur(), v.hauteur());

		}

	/**
	 * methode dessiner redefinie de Afficheur retourne une image du jeu
	 */
	public void dessiner(BufferedImage im) {
		if(this.jeu.aUnVaisseau()){
			Vaisseau v = this.jeu.recupererUnVaisseau();
			this.dessinerUnVaisseau(v, im);
		}
		
		if(this.jeu.aUnMissile()){
			Missile m = this.jeu.recupererUnMissile();
			this.dessinerUnMissile(m, im);
		}
		
		if(this.jeu.aUnEnvahisseur()){
			Envahisseur e = this.jeu.recupererEnvahisseur();
			this.dessinerUnEnvahisseur(e, im);
		}
		
	}

	private void dessinerUnMissile(Missile m, BufferedImage im) {
		Graphics2D crayon = (Graphics2D) im.getGraphics();
		crayon.setColor(Color.blue);
		crayon.fillRect(m.abscisseLaPlusAGauche(), m.ordonneeLaPlusBasse(), m.longueur(), m.hauteur());
		
	}
	
	private void dessinerUnEnvahisseur(Envahisseur e, BufferedImage im) {
		Graphics2D crayon = (Graphics2D) im.getGraphics();
		crayon.setColor(Color.red);
		crayon.fillRect(e.abscisseLaPlusAGauche(), e.ordonneeLaPlusBasse(), e.longueur(), e.hauteur());
		
	}
}
