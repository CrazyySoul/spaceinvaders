package fr.unilim.iut.spaceinvaders;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import org.junit.Before;
import org.junit.Test;

import fr.unilim.iut.spaceinvaders.utils.DebordementEspaceJeuException;
import fr.unilim.iut.spaceinvaders.utils.HorsEspaceJeuException;
import fr.unilim.iut.spaceinvaders.utils.MissileException;
import fr.unilim.iut.spaceinvaders.utils.SpriteInexistantException;

public class SpaceInvadersTest {

	private SpaceInvaders spaceinvaders;

	@Before
	public void initialisation() {
		spaceinvaders = new SpaceInvaders(15, 10);
	}

	@Test
	public void test_AuDebut_JeuSpaceInvaderEstVide() {
		assertEquals("" + "...............\n" 
						+ "...............\n" 
						+ "...............\n" 
						+ "...............\n"
						+ "...............\n" 
						+ "...............\n" 
						+ "...............\n" 
						+ "...............\n"
						+ "...............\n" 
						+ "...............\n", spaceinvaders.recupererEspaceJeuDansChaineASCII());
	}

	@Test
	public void test_unNouveauVaisseauEstCorrectementPositionneDansEspaceJeu() {
		spaceinvaders.positionnerUnNouveauVaisseau(new Dimension(1, 1), new Position(7, 9), 1);
		assertEquals("" + "...............\n" 
						+ "...............\n" 
						+ "...............\n" 
						+ "...............\n"
						+ "...............\n" 
						+ "...............\n" 
						+ "...............\n" 
						+ "...............\n"
						+ "...............\n" 
						+ ".......V.......\n", spaceinvaders.recupererEspaceJeuDansChaineASCII());
	}

	@Test
	public void test_UnNouveauVaisseauPositionneHorsEspaceJeu_DoitLeverUneException() {

		try {
			spaceinvaders.positionnerUnNouveauVaisseau(new Dimension(1, 1), new Position(15, 9), 1);
			fail("Position trop à droite : devrait déclencher une exception HorsEspaceJeuException");
		} catch (final HorsEspaceJeuException e) {
		}

		try {
			spaceinvaders.positionnerUnNouveauVaisseau(new Dimension(1, 1), new Position(-1, 9), 1);
			fail("Position trop à gauche : devrait déclencher une exception HorsEspaceJeuException");
		} catch (final HorsEspaceJeuException e) {
		}

		try {
			spaceinvaders.positionnerUnNouveauVaisseau(new Dimension(1, 1), new Position(14, 10), 1);
			fail("Position trop en bas : devrait déclencher une exception HorsEspaceJeuException");
		} catch (final HorsEspaceJeuException e) {
		}

		try {
			spaceinvaders.positionnerUnNouveauVaisseau(new Dimension(1, 1), new Position(14, -1), 1);
			fail("Position trop à haut : devrait déclencher une exception HorsEspaceJeuException");
		} catch (final HorsEspaceJeuException e) {
		}

	}

	@Test
	public void test_unNouveauVaisseauAvecDimensionEstCorrectementPositionneDansEspaceJeu() {
		spaceinvaders.positionnerUnNouveauVaisseau(new Dimension(3, 2), new Position(7, 9), 1);
		assertEquals("" + "...............\n" 
						+ "...............\n" 
						+ "...............\n" 
						+ "...............\n"
						+ "...............\n" 
						+ "...............\n" 
						+ "...............\n" 
						+ "...............\n"
						+ ".......VVV.....\n" 
						+ ".......VVV.....\n", spaceinvaders.recupererEspaceJeuDansChaineASCII());
	}

	@Test
	public void test_UnNouveauVaisseauPositionneDansEspaceJeuMaisAvecDimensionTropGrande_DoitLeverUneExceptionDeDebordement() {

		try {
			spaceinvaders.positionnerUnNouveauVaisseau(new Dimension(9, 2), new Position(7, 9), 1);
			fail("Dépassement du vaisseau à droite en raison de sa longueur trop importante : devrait déclencher une exception DebordementEspaceJeuException");
		} catch (final DebordementEspaceJeuException e) {
		}

		try {
			spaceinvaders.positionnerUnNouveauVaisseau(new Dimension(3, 4), new Position(7, 1), 1);
			fail("Dépassement du vaisseau vers le haut en raison de sa hauteur trop importante : devrait déclencher une exception DebordementEspaceJeuException");
		} catch (final DebordementEspaceJeuException e) {
		}

	}

	@Test
	public void test_VaisseauImmobile_DeplacerVaisseauVersLaDroite() {

		spaceinvaders.positionnerUnNouveauVaisseau(new Dimension(3, 2), new Position(12, 9), 3);
		spaceinvaders.deplacerSpriteVersLaDroite(spaceinvaders.recupererUnVaisseau());
		assertEquals("" + "...............\n" 
						+ "...............\n" 
						+ "...............\n" 
						+ "...............\n"
						+ "...............\n" 
						+ "...............\n" 
						+ "...............\n" 
						+ "...............\n"
						+ "............VVV\n" 
						+ "............VVV\n", spaceinvaders.recupererEspaceJeuDansChaineASCII());
	}

	@Test
	public void test_VaisseauAvance_DeplacerVaisseauVersLaGauche_AvecVitesse() {

		spaceinvaders.positionnerUnNouveauVaisseau(new Dimension(3, 2), new Position(7, 9), 3);
		spaceinvaders.deplacerSpriteVersLaGauche(spaceinvaders.recupererUnVaisseau());

		assertEquals("" + "...............\n" 
						+ "...............\n" 
						+ "...............\n" 
						+ "...............\n"
						+ "...............\n" 
						+ "...............\n" 
						+ "...............\n" 
						+ "...............\n"
						+ "....VVV........\n" 
						+ "....VVV........\n", spaceinvaders.recupererEspaceJeuDansChaineASCII());
	}

	@Test
	public void test_VaisseauImmobile_DeplacerVaisseauVersLaGauche() {

		spaceinvaders.positionnerUnNouveauVaisseau(new Dimension(3, 2), new Position(0, 9), 3);
		spaceinvaders.deplacerSpriteVersLaGauche(spaceinvaders.recupererUnVaisseau());

		assertEquals("" + "...............\n" 
						+ "...............\n" 
						+ "...............\n" 
						+ "...............\n"
						+ "...............\n" 
						+ "...............\n" 
						+ "...............\n" 
						+ "...............\n"
						+ "VVV............\n" 
						+ "VVV............\n", spaceinvaders.recupererEspaceJeuDansChaineASCII());
	}

	@Test
	public void test_VaisseauAvance_DeplacerVaisseauVersLaDroite_AvecVitesse() {

		spaceinvaders.positionnerUnNouveauVaisseau(new Dimension(3, 2), new Position(7, 9), 3);
		spaceinvaders.deplacerSpriteVersLaDroite(spaceinvaders.recupererUnVaisseau());
		assertEquals("" + "...............\n" 
						+ "...............\n" 
						+ "...............\n" 
						+ "...............\n"
						+ "...............\n" 
						+ "...............\n" 
						+ "...............\n" 
						+ "...............\n"
						+ "..........VVV..\n" 
						+ "..........VVV..\n", spaceinvaders.recupererEspaceJeuDansChaineASCII());
	}

	@Test
	public void test_VaisseauAvancePartiellement_DeplacerVaisseauVersLaDroite() {

		spaceinvaders.positionnerUnNouveauVaisseau(new Dimension(3, 2), new Position(10, 9), 3);
		spaceinvaders.deplacerSpriteVersLaDroite(spaceinvaders.recupererUnVaisseau());
		assertEquals("" + "...............\n" 
						+ "...............\n" 
						+ "...............\n" 
						+ "...............\n"
						+ "...............\n" 
						+ "...............\n" 
						+ "...............\n" 
						+ "...............\n"
						+ "............VVV\n" 
						+ "............VVV\n", spaceinvaders.recupererEspaceJeuDansChaineASCII());
	}

	@Test
	public void test_VaisseauAvancePartiellement_DeplacerVaisseauVersLaGauche() {

		spaceinvaders.positionnerUnNouveauVaisseau(new Dimension(3, 2), new Position(1, 9), 3);
		spaceinvaders.deplacerSpriteVersLaGauche(spaceinvaders.recupererUnVaisseau());

		assertEquals("" + "...............\n" 
						+ "...............\n" 
						+ "...............\n" 
						+ "...............\n"
						+ "...............\n" 
						+ "...............\n" 
						+ "...............\n" 
						+ "...............\n"
						+ "VVV............\n" 
						+ "VVV............\n", spaceinvaders.recupererEspaceJeuDansChaineASCII());
	}

	@Test
	public void test_MissileBienTireDepuisVaisseau_VaisseauLongueurImpaireMissileLongueurImpaire() {

		spaceinvaders.positionnerUnNouveauVaisseau(new Dimension(7, 2), new Position(5, 9), 2);
		spaceinvaders.tirerUnMissile(new Dimension(3, 2), 2);

		assertEquals("" + "...............\n" 
						+ "...............\n" 
						+ "...............\n" 
						+ "...............\n"
						+ "...............\n" 
						+ "...............\n" 
						+ ".......MMM.....\n" 
						+ ".......MMM.....\n"
						+ ".....VVVVVVV...\n" 
						+ ".....VVVVVVV...\n", spaceinvaders.recupererEspaceJeuDansChaineASCII());
	}

	@Test(expected = MissileException.class)
	public void test_PasAssezDePlacePourTirerUnMissile_UneExceptionEstLevee() throws Exception {
		spaceinvaders.positionnerUnNouveauVaisseau(new Dimension(7, 2), new Position(5, 9), 1);
		spaceinvaders.tirerUnMissile(new Dimension(7, 9), 1);
	}
	
	@Test
    public void test_MissileAvanceAutomatiquement_ApresTirDepuisLeVaisseau() {

	   spaceinvaders.positionnerUnNouveauVaisseau(new Dimension(7,2),new Position(5,9), 2);
	   spaceinvaders.tirerUnMissile(new Dimension(3,2),2);

	   spaceinvaders.deplacerMissile();
	   
       assertEquals("" + 
       "...............\n" + 
       "...............\n" +
       "...............\n" + 
       "...............\n" + 
       ".......MMM.....\n" + 
       ".......MMM.....\n" + 
       "...............\n" + 
       "...............\n" + 
       ".....VVVVVVV...\n" + 
       ".....VVVVVVV...\n" , spaceinvaders.recupererEspaceJeuDansChaineASCII());
   }
	
	@Test
	public void test_MissileDisparait_QuandIlCommenceASortirDeEspaceJeu() {

		   spaceinvaders.positionnerUnNouveauVaisseau(new Dimension(7,2),new Position(5,9), 1);
		   spaceinvaders.tirerUnMissile(new Dimension(3,2),1);
		   for (int i = 1; i <=7 ; i++) {
			   spaceinvaders.deplacerMissile();
		   }
		   
		   spaceinvaders.deplacerMissile();
		   
		assertEquals("" + "...............\n" 
						+ "...............\n" 
						+ "...............\n" 
						+ "...............\n"
						+ "...............\n" 
						+ "...............\n" 
						+ "...............\n" 
						+ "...............\n"
						+ ".....VVVVVVV...\n" 
						+ ".....VVVVVVV...\n", spaceinvaders.recupererEspaceJeuDansChaineASCII());
	}
	
	@Test
	public void test_unNouvelEnvahisseurAvecDimensionEstCorrectementPositionneDansEspaceJeu(){
		spaceinvaders.positionnerUnNouveauEnvahisseur(new Dimension(3,2),new Position(7,1), 1);
		   
		assertEquals("" + ".......EEE.....\n" 
						+ ".......EEE.....\n" 
						+ "...............\n" 
						+ "...............\n"
						+ "...............\n" 
						+ "...............\n" 
						+ "...............\n" 
						+ "...............\n"
						+ "...............\n" 
						+ "...............\n", spaceinvaders.recupererEspaceJeuDansChaineASCII());
	}
	
	@Test
	public void test_UnNouveauEnvahisseurPositionneHorsEspaceJeu_DoitLeverUneException() {

		try {
			spaceinvaders.positionnerUnNouveauEnvahisseur(new Dimension(1, 1), new Position(15, 9), 1);
			fail("Position trop à droite : devrait déclencher une exception HorsEspaceJeuException");
		} catch (final HorsEspaceJeuException e) {
		}

		try {
			spaceinvaders.positionnerUnNouveauEnvahisseur(new Dimension(1, 1), new Position(-1, 9), 1);
			fail("Position trop à gauche : devrait déclencher une exception HorsEspaceJeuException");
		} catch (final HorsEspaceJeuException e) {
		}

		try {
			spaceinvaders.positionnerUnNouveauEnvahisseur(new Dimension(1, 1), new Position(14, 10), 1);
			fail("Position trop en bas : devrait déclencher une exception HorsEspaceJeuException");
		} catch (final HorsEspaceJeuException e) {
		}

		try {
			spaceinvaders.positionnerUnNouveauEnvahisseur(new Dimension(1, 1), new Position(14, -1), 1);
			fail("Position trop à haut : devrait déclencher une exception HorsEspaceJeuException");
		} catch (final HorsEspaceJeuException e) {
		}

	}

	@Test
	public void test_UnNouveauEnvahisseurPositionneDansEspaceJeuMaisAvecDimensionTropGrande_DoitLeverUneExceptionDeDebordement() {

		try {
			spaceinvaders.positionnerUnNouveauEnvahisseur(new Dimension(9, 2), new Position(7, 9), 1);
			fail("Dépassement du vaisseau à droite en raison de sa longueur trop importante : devrait déclencher une exception DebordementEspaceJeuException");
		} catch (final DebordementEspaceJeuException e) {
		}

		try {
			spaceinvaders.positionnerUnNouveauEnvahisseur(new Dimension(3, 4), new Position(7, 1), 1);
			fail("Dépassement du vaisseau vers le haut en raison de sa hauteur trop importante : devrait déclencher une exception DebordementEspaceJeuException");
		} catch (final DebordementEspaceJeuException e) {
		}

	}

	@Test
	public void test_EnvahisseurAvance_DeplacerEnvahisseurVersLaDroite_AvecVitesse() {

		spaceinvaders.positionnerUnNouveauEnvahisseur(new Dimension(3, 2), new Position(7, 1), 3);
		spaceinvaders.deplacerSpriteVersLaDroite(spaceinvaders.recupererEnvahisseur());
		assertEquals("" + "..........EEE..\n" 
						+ "..........EEE..\n"
						+ "...............\n" 
						+ "...............\n"
						+ "...............\n" 
						+ "...............\n" 
						+ "...............\n" 
						+ "...............\n"
						+ "...............\n" 
						+ "...............\n", spaceinvaders.recupererEspaceJeuDansChaineASCII());
	}
	
	@Test
	public void test_DeplacerSpriteSiSpriteEstNullDoitLeveException(){
		try {
			spaceinvaders.deplacerSpriteVersLaDroite(spaceinvaders.recupererUnVaisseau());
			fail("Le Sprite n'existe pas : devrait déclencher une exception SpriteInexistantException");
		} catch (final SpriteInexistantException e) {
		}
		try {
			spaceinvaders.deplacerSpriteVersLaDroite(spaceinvaders.recupererUnVaisseau());
			fail("Le Sprite n'existe pas : devrait déclencher une exception SpriteInexistantException");
		} catch (final SpriteInexistantException e) {
		}
		
	}

	@Test
	public void test_EnvahisseurImmobile_DeplacerEnvahisseurVersLaDroite() {

		spaceinvaders.positionnerUnNouveauEnvahisseur(new Dimension(3, 2), new Position(12, 1), 3);
		spaceinvaders.deplacerSpriteVersLaDroite(spaceinvaders.recupererEnvahisseur());
		assertEquals("" + "............EEE\n" 
						+ "............EEE\n" 
						+ "...............\n" 
						+ "...............\n"
						+ "...............\n" 
						+ "...............\n" 
						+ "...............\n" 
						+ "...............\n"
						+ "...............\n" 
						+ "...............\n", spaceinvaders.recupererEspaceJeuDansChaineASCII());
	}
	
	@Test
	public void test_EnvahisseurAvancePartiellement_DeplacerEnvahisseurVersLaDroite() {

		spaceinvaders.positionnerUnNouveauEnvahisseur(new Dimension(3, 2), new Position(10, 1), 3);
		spaceinvaders.deplacerSpriteVersLaDroite(spaceinvaders.recupererEnvahisseur());
		assertEquals("" + "............EEE\n" 
						+ "............EEE\n" 
						+ "...............\n" 
						+ "...............\n"
						+ "...............\n" 
						+ "...............\n" 
						+ "...............\n" 
						+ "...............\n"
						+ "...............\n" 
						+ "...............\n", spaceinvaders.recupererEspaceJeuDansChaineASCII());
	}
	
	@Test
	public void test_EnvahisseurAvance_DeplacerEnvahisseurVersLaGauche_AvecVitesse() {

		spaceinvaders.positionnerUnNouveauEnvahisseur(new Dimension(3, 2), new Position(7, 1), 3);
		
		spaceinvaders.deplacerSpriteVersLaGauche(spaceinvaders.recupererEnvahisseur());
		assertEquals("" + "....EEE........\n" 
						+ "....EEE........\n"
						+ "...............\n" 
						+ "...............\n"
						+ "...............\n" 
						+ "...............\n" 
						+ "...............\n" 
						+ "...............\n"
						+ "...............\n" 
						+ "...............\n", spaceinvaders.recupererEspaceJeuDansChaineASCII());
	}

	@Test
	public void test_EnvahisseurImmobile_DeplacerEnvahisseurVersLaGauche() {

		spaceinvaders.positionnerUnNouveauEnvahisseur(new Dimension(3, 2), new Position(0, 1), 3);
		spaceinvaders.deplacerSpriteVersLaGauche(spaceinvaders.recupererEnvahisseur());
		assertEquals("" + "EEE............\n" 
						+ "EEE............\n" 
						+ "...............\n" 
						+ "...............\n"
						+ "...............\n" 
						+ "...............\n" 
						+ "...............\n" 
						+ "...............\n"
						+ "...............\n" 
						+ "...............\n", spaceinvaders.recupererEspaceJeuDansChaineASCII());
	}
	
	@Test
	public void test_EnvahisseurAvancePartiellement_DeplacerEnvahisseurVersLaGauche() {

		spaceinvaders.positionnerUnNouveauEnvahisseur(new Dimension(3, 2), new Position(2, 1), 3);
		spaceinvaders.deplacerSpriteVersLaGauche(spaceinvaders.recupererEnvahisseur());
		assertEquals("" + "EEE............\n" 
						+ "EEE............\n" 
						+ "...............\n" 
						+ "...............\n"
						+ "...............\n" 
						+ "...............\n" 
						+ "...............\n" 
						+ "...............\n"
						+ "...............\n" 
						+ "...............\n", spaceinvaders.recupererEspaceJeuDansChaineASCII());
	}

	@Test
    public void test_EnvahisseurSeDaplaceAutomatiquement_ApresPositionnement() {

	   spaceinvaders.positionnerUnNouveauEnvahisseur(new Dimension(3,2),new Position(0,1), 3);
	   spaceinvaders.deplacerEnvahisseur();
	   
       assertEquals("" + 
       "...EEE.........\n" + 
       "...EEE.........\n" +
       "...............\n" + 
       "...............\n" + 
       "...............\n" + 
       "...............\n" + 
       "...............\n" + 
       "...............\n" + 
       "...............\n" + 
       "...............\n" , spaceinvaders.recupererEspaceJeuDansChaineASCII());
   }
	
	@Test
    public void test_EnvahisseurSeDaplaceAutomatiquement_ChangeDeDirection_ApresAtteintBordureDroite() {

	   spaceinvaders.positionnerUnNouveauEnvahisseur(new Dimension(3,2),new Position(0,1), 3);
	   for (int i = 0; i < 7; i++){
		   spaceinvaders.deplacerEnvahisseur();
	   }
	   
       assertEquals("" + 
       ".........EEE...\n" + 
       ".........EEE...\n" +
       "...............\n" + 
       "...............\n" + 
       "...............\n" + 
       "...............\n" + 
       "...............\n" + 
       "...............\n" + 
       "...............\n" + 
       "...............\n" , spaceinvaders.recupererEspaceJeuDansChaineASCII());
   }
	
	@Test
    public void test_EnvahisseurSeDaplaceAutomatiquement_ChangeDeDirection_ApresAtteintBordureGauche() {

	   spaceinvaders.positionnerUnNouveauEnvahisseur(new Dimension(3,2),new Position(0,1), 3);
	   for (int i = 0; i < 9; i++){
		spaceinvaders.deplacerEnvahisseur();
	   }
	   
       assertEquals("" + 
       "...EEE.........\n" + 
       "...EEE.........\n" +
       "...............\n" + 
       "...............\n" + 
       "...............\n" + 
       "...............\n" + 
       "...............\n" + 
       "...............\n" + 
       "...............\n" + 
       "...............\n" , spaceinvaders.recupererEspaceJeuDansChaineASCII());
   }

	@Test
	public void test_PartieTermine(){
		spaceinvaders.positionnerUnNouveauEnvahisseur(new Dimension(3, 2), new Position(0, 1), 2);
		spaceinvaders.positionnerUnNouveauVaisseau(new Dimension(7, 2), new Position(5, 9), 3);
		
		spaceinvaders.deplacerSpriteVersLaGauche(spaceinvaders.recupererUnVaisseau());
		spaceinvaders.deplacerSpriteVersLaGauche(spaceinvaders.recupererUnVaisseau());
		spaceinvaders.tirerUnMissile(new Dimension(3, 2), 3);
		spaceinvaders.deplacerMissile();
		spaceinvaders.deplacerMissile();
		assertEquals("" + 
			       "..MMM..........\n" + 
			       "..MMM..........\n" +
			       "...............\n" + 
			       "...............\n" + 
			       "...............\n" + 
			       "...............\n" + 
			       "...............\n" + 
			       "...............\n" + 
			       "VVVVVVV........\n" + 
			       "VVVVVVV........\n" , spaceinvaders.recupererEspaceJeuDansChaineASCII());
	}
	
	@Test
	public void test_EnvahisseurDetruit(){
		spaceinvaders.positionnerUnNouveauEnvahisseur(new Dimension(3, 2), new Position(0, 1), 2);
		spaceinvaders.positionnerUnNouveauVaisseau(new Dimension(7, 2), new Position(5, 9), 3);
		
		spaceinvaders.deplacerSpriteVersLaGauche(spaceinvaders.recupererUnVaisseau());
		spaceinvaders.deplacerSpriteVersLaGauche(spaceinvaders.recupererUnVaisseau());
		spaceinvaders.tirerUnMissile(new Dimension(3, 2), 40);
		spaceinvaders.deplacerMissile();
		assertEquals("" + 
			       "...............\n" + 
			       "...............\n" +
			       "...............\n" + 
			       "...............\n" + 
			       "...............\n" + 
			       "...............\n" + 
			       "...............\n" + 
			       "...............\n" + 
			       "VVVVVVV........\n" + 
			       "VVVVVVV........\n" , spaceinvaders.recupererEspaceJeuDansChaineASCII());
	}
	
	@Test
	public void test_VaisseauTirePlusieursMissile(){
		spaceinvaders.positionnerUnNouveauVaisseau(new Dimension(7, 2), new Position(5, 9), 3);
		
		spaceinvaders.tirerUnMissile(new Dimension(3, 2), 5);
		spaceinvaders.deplacerMissile();
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		spaceinvaders.tirerUnMissile(new Dimension(3, 2), 5);
		assertEquals("" + 
			       "...............\n" + 
			       ".......MMM.....\n" +
			       ".......MMM.....\n" + 
			       "...............\n" + 
			       "...............\n" + 
			       "...............\n" + 
			       ".......MMM.....\n" + 
			       ".......MMM.....\n" + 
			       ".....VVVVVVV...\n" + 
			       ".....VVVVVVV...\n" , spaceinvaders.recupererEspaceJeuDansChaineASCII());
	}
	
	@Test
	public void test_VaisseauTirePlusieursMissileQuiNeSeChevauchePas(){
		spaceinvaders.positionnerUnNouveauVaisseau(new Dimension(7, 2), new Position(5, 9), 3);
		
		spaceinvaders.tirerUnMissile(new Dimension(3, 2), 1);
		spaceinvaders.deplacerMissile();
		spaceinvaders.tirerUnMissile(new Dimension(3, 2), 2);
		assertEquals("" + 
			       "...............\n" + 
			       "...............\n" +
			       "...............\n" + 
			       "...............\n" + 
			       "...............\n" + 
			       ".......MMM.....\n" + 
			       ".......MMM.....\n" + 
			       "...............\n" + 
			       ".....VVVVVVV...\n" + 
			       ".....VVVVVVV...\n" , spaceinvaders.recupererEspaceJeuDansChaineASCII());
	}
	
	@Test
	public void test_VaisseauTirePlusieursMissileQuiNeSeChevauchePasEtQuiSeDeplaceEnMemeTemps(){
		spaceinvaders.positionnerUnNouveauVaisseau(new Dimension(7, 2), new Position(5, 9), 3);

		spaceinvaders.tirerUnMissile(new Dimension(3, 2), 1);
		spaceinvaders.deplacerMissile();
		spaceinvaders.tirerUnMissile(new Dimension(3, 2), 2);
		spaceinvaders.deplacerSpriteVersLaGauche(spaceinvaders.recupererUnVaisseau());
		spaceinvaders.deplacerMissile();
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		spaceinvaders.tirerUnMissile(new Dimension(3, 2), 2);
		spaceinvaders.deplacerMissile();
		assertEquals("" + 
			       "...............\n" + 
			       "...............\n" +
			       "...............\n" + 
			       ".......MMM.....\n" + 
			       "....MMMMMM.....\n" + 
			       "....MMM........\n" + 
			       "...............\n" + 
			       "...............\n" + 
			       "..VVVVVVV......\n" + 
			       "..VVVVVVV......\n" , spaceinvaders.recupererEspaceJeuDansChaineASCII());
	}
	
	@Test
	public void test_VaisseauTirePlusieursMissileAvecCadence(){
		spaceinvaders.positionnerUnNouveauVaisseau(new Dimension(7, 2), new Position(5, 9), 3);
		
		spaceinvaders.tirerUnMissile(new Dimension(3, 2), 2);
		spaceinvaders.deplacerMissile();
		spaceinvaders.tirerUnMissile(new Dimension(3, 2), 2);
		assertEquals("" + 
			       "...............\n" + 
			       "...............\n" +
			       "...............\n" + 
			       "...............\n" + 
			       ".......MMM.....\n" + 
			       ".......MMM.....\n" + 
			       "...............\n" + 
			       "...............\n" + 
			       ".....VVVVVVV...\n" + 
			       ".....VVVVVVV...\n" , spaceinvaders.recupererEspaceJeuDansChaineASCII());
	}
}
