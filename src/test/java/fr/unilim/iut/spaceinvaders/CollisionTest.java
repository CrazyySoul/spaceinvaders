package fr.unilim.iut.spaceinvaders;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class CollisionTest {
	@Test
	public void test_DetecterCollisionFrontale() {
		
		Envahisseur e = new Envahisseur(new Dimension(3, 2), new Position(12, 1), 3);
		Missile m = new Missile(new Dimension(3, 2), new Position(10, 2), 3);
		Collision collision = new Collision();

		assertEquals(true, collision.detecterCollision(e, m));
	}
	
	@Test
	public void test_DetecterPasCollisionFrontale() {
		
		Envahisseur e = new Envahisseur(new Dimension(3, 2), new Position(0, 1), 3);
		Missile m = new Missile(new Dimension(3, 2), new Position(0, 3), 3);
		Collision collision = new Collision();

		assertEquals(false, collision.detecterCollision(e, m));
	}
	
}
