package moteurJeu;

public class Commande {
	/**
	 * boolean representant la commande de l'utilisateur
	 */
	public boolean gauche;
	public boolean droite;
	public boolean tir;

	public Commande()
	{
		
	}
	
	/**
	 * constructeur par copie
	 * copie la commande pour en creer une nouvelle
	 * @param commandeACopier
	 */
	public Commande(Commande commandeACopier)
	{
		this.gauche=commandeACopier.gauche;
		this.droite=commandeACopier.droite;
		this.tir=commandeACopier.tir;
	}
	
}
