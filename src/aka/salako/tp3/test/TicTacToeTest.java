package aka.salako.tp3.test;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import fr.istic.l3miage.morpion.*;

public class TicTacToeTest {
	AbstractTicTacToe morpions;
	public static final int TAILLE = AbstractTicTacToe.BOARD_WIDTH;
	public static final int NB_CASES = AbstractTicTacToe.BOARD_WIDTH * AbstractTicTacToe.BOARD_HEIGHT;

	@BeforeEach
	public void setUp() {
		morpions = new TicTacToeV1();	//passe le test
//		morpions = new TicTacToeV2();	// passe le test
//		morpions = new TicTacToeV3();	// ne passe pas le test
//		morpions = new TicTacToeV4();	// passe le test
//		morpions = new TicTacToeV5();	// passe le test
//		morpions = new TicTacToeV6();	// ne passe pas le test
//		morpions = new TicTacToeV7();	// passe le test
	}

	@Test
	public void testInit() {
		assertEquals(morpions.getTurn(), Owner.FIRST, "Le premier doit jouer");
		testInvariant();
		
		// Vérifier que chaque carré est vide et donc que le plateau est vide.
	    for (int i =  0; i < TAILLE; i++) {
	        for (int j =  0; j < TAILLE; j++) {
	            assertEquals(Owner.NONE, morpions.getSquare(i, j), "La case (" + i + "," + j + ") doit être vide");
	        }
	    }
	    
	    // Vérifier qu'il n'y a pas de vainqueur au début du jeu (que le vainqueur est NONE)
	    assertEquals(Owner.NONE, morpions.getWinner(), "Le vainqueur doit être NONE");
	    
	    // Vérifier que le compteur de tours est à zéro
	    assertEquals(0, morpions.numberOfRounds(), "Le compteur de tours doit être à zéro");
	}

	/**
	 * Fonction à utiliser après chaque action, pour tester les conditions qui
	 * doivent toujours être vraies
	 */
	private void testInvariant() {
		assertTrue(morpions.numberOfRounds() >= 0, "Nombre de coups >= 0");
		assertTrue(morpions.numberOfRounds() <= NB_CASES, "Nombre de coups <= " + NB_CASES);
		// ----------------------
		// SÉQUENCE À COMPLÉTER
		// ----------------------
	}
	
	@Test 
	public void testGetJoueur() {
		morpions.restart();
		assertEquals(morpions.getTurn(), Owner.FIRST, "C'est au tour du joueur 1 de jouer");
		testInvariant();
		morpions.play(1,2);
		assertEquals(morpions.getTurn(), Owner.SECOND, "C'est au tour du joueur 2 de jouer");
		testInvariant();
	}
	
	@Test
	public void testGetVainqueur() {
		morpions.play(1, 1);
		morpions.play(0, 2);
		morpions.play(2, 2);
		morpions.play(0, 0);
		morpions.play(0, 1);
		morpions.play(2, 0);
		morpions.play(2, 1);
		testInvariant();
		
		assertEquals(morpions.getWinner(), Owner.FIRST, "Le joueur 1 à remporter la partie");
		assertTrue(morpions.gameOver());
		assertEquals(morpions.numberOfRounds(), 7, "Cette partie s'est faite en 7 tours.");
	}
	
	@Test
	public void testPremierCoup() {
		assertEquals(morpions.getTurn(), Owner.FIRST, "Le joueur 1 est le premier joueur.");
		morpions.play(1, 1);
		morpions.validSquare(1, 1);
		assertFalse(morpions.gameOver(), "La partie n'est pas terminée.");
		testInvariant();
	}
	
	@Test
	public void testPartiePasFinie() {
		testInit();
		morpions.play(0, 2);
		morpions.play(0, 1);
		morpions.play(1, 0);
		morpions.play(0, 0);
		morpions.play(1, 1);
		morpions.play(2, 0);
		morpions.play(2, 1);
		morpions.play(1, 2);
		morpions.play(2, 2);		
		assertTrue(morpions.gameOver(), "La partie n'est pas finie.");
		testInvariant();
	}
	
	@Test
	public void testControle() {
		testInit();
		morpions.restart();
		
		// Tentative de mouvement sur une case déjà occupée
        assertTrue(morpions.legalMove(0,  0));
		morpions.play(0, 0); // Joueur  1 joue en (0,  0)
        assertFalse(morpions.legalMove(0,  0), "Le joueur 2 ne peux pas jouer "
        		+ "sur cette case car déjà utilisé par le joueur 1."); // Joueur  2 tente de jouer en (0,  0)
        morpions.play(0, 0); // Joueur  2 joue en (0,  0)
        assertFalse(morpions.legalMove(0,  0), "Le carré (0, 0) est déjà occupé par le joueur 1");
        
        //On suppose la fin de la partie
        morpions.gameOver();
        assertFalse(morpions.gameOver(), "La partie est finie !");
        morpions.play(1, 2);
        assertFalse(morpions.legalMove(1, 2), "La partie est terminée, aucun "
        		+ "déplacement n'est plus autorisé. Relancer une autre partie !");
        testInvariant();
    }
	
	@Test
    public void testFinPartie() {
		
		//gagner verticalement premier joueur
		morpions.restart();
		morpions.play(1, 1);
		morpions.play(0, 2);
		morpions.play(2, 2);
		morpions.play(0, 0);
		morpions.play(0, 1);
		morpions.play(2, 0);
		morpions.play(2, 1);
		assertEquals(morpions.getSquare(2, 1), Owner.FIRST);
		assertTrue(morpions.gameOver());
		assertEquals(morpions.getWinner(), Owner.FIRST,"le premier joueur gagne");
		assertTrue(morpions.numberOfRounds() == 7);
		
		//gagner verticalement deuxieme joueur
		morpions.restart();
		morpions.play(0, 0);//1er
		morpions.play(0, 2);
		morpions.play(0, 1);//1er
		morpions.play(1, 2);
		assertFalse(morpions.gameOver());
		assertTrue(morpions.numberOfRounds() == 4);
		assertEquals(morpions.getTurn(), Owner.FIRST, "Le premier doit jouer");
		morpions.play(1, 1);
		assertTrue(morpions.numberOfRounds() == 5);
		morpions.play(2, 2);
		assertEquals(morpions.getSquare(0, 2), Owner.SECOND);
		assertTrue(morpions.numberOfRounds() == 6);
		assertTrue(morpions.gameOver());
		assertEquals(morpions.getWinner(), Owner.SECOND,"le deuxieme joueur gagne");
		testInvariant();
	
    	//gagner obliquement
		morpions.restart();
		morpions.play(0,0);
		morpions.play(0,1);
		morpions.play(1,1);
		morpions.play(1,0);
		assertFalse(morpions.gameOver());
		assertFalse(morpions.legalMove(1, 1));
		morpions.play(2, 2);
		assertTrue(morpions.gameOver());
		assertTrue(morpions.numberOfRounds() == 5);
		assertEquals(morpions.getWinner(), Owner.FIRST,"le premier joueur gagne");
    }

}

