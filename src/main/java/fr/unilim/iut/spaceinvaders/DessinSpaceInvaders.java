package fr.unilim.iut.spaceinvaders;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import moteurJeu.DessinJeu;

public class DessinSpaceInvaders implements DessinJeu {
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
		crayon.setColor(Color.green);
		crayon.fillRect(v.abscisseLaPlusAGauche(), v.ordonneeLaPlusBasse(), v.longueur(), v.hauteur());

	}

	/**
	 * methode dessiner redefinie de Afficheur retourne une image du jeu
	 */
	public void dessiner(BufferedImage im) {
		int s = this.jeu.getScore();
		this.dessinerUnScore(s, im);
		if (this.jeu.aUnVaisseau()) {
			Vaisseau v = this.jeu.recupererUnVaisseau();
			this.dessinerUnVaisseau(v, im);
		}
		for (int i = 0; i < jeu.missileVaisseau.size(); i++) {
			if (this.jeu.aUnMissileVaisseau(i)) {
				Missile m = this.jeu.recupererUnMissileVaisseau(i);
				this.dessinerUnMissileVaisseau(m, im);

			}
		}
		for(int i =0;i<jeu.envahisseur.size();i++){
			if (this.jeu.aUnEnvahisseur(i)) {
				Envahisseur e = this.jeu.recupererEnvahisseur(i);
				this.dessinerUnEnvahisseur(e, im);
			}
		}
		for(int i =0;i<jeu.missileEnvahisseur.size();i++){
			if (this.jeu.aUnMissileEnvahisseur(i)) {
				Missile n = this.jeu.recupererUnMissileEnvahisseur(i);
				this.dessinerUnMissileEnvahisseur(n, im);
			}
		}
	}

	private void dessinerUnScore(int s, BufferedImage im) {
		Graphics2D crayon = (Graphics2D) im.getGraphics();
		crayon.setColor(Color.white);
		Font f = new Font(Font.SANS_SERIF, Font.BOLD, 50);
		crayon.setFont(f);
		crayon.drawString(Integer.toString(s), this.jeu.longueur/2, this.jeu.hauteur/2);
		
	}

	private void dessinerUnMissileVaisseau(Missile m, BufferedImage im) {
		Graphics2D crayon = (Graphics2D) im.getGraphics();
		crayon.setColor(Color.green);
		crayon.fillRect(m.abscisseLaPlusAGauche(), m.ordonneeLaPlusBasse(), m.longueur(), m.hauteur());

	}
	
	private void dessinerUnMissileEnvahisseur(Missile m, BufferedImage im) {
		Graphics2D crayon = (Graphics2D) im.getGraphics();
		crayon.setColor(Color.white);
		crayon.fillRect(m.abscisseLaPlusAGauche(), m.ordonneeLaPlusBasse(), m.longueur(), m.hauteur());

	}

	private void dessinerUnEnvahisseur(Envahisseur e, BufferedImage im) {
		Graphics2D crayon = (Graphics2D) im.getGraphics();
		crayon.setColor(Color.gray);
		crayon.fillRect(e.abscisseLaPlusAGauche(), e.ordonneeLaPlusBasse(), e.longueur(), e.hauteur());

	}
}
