package client.challenge;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;

import javax.ejb.EJBException;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.junit.Before;
import org.junit.Test;

import service.challenge.ChallengeServiceRemote;

public class TestChallengeClient {

	private ChallengeServiceRemote challengeRemote;
	private ArrayList<String> listTeam1;
	private ArrayList<String> listTeam2;
	private String nameTeam1;
	private String nameTeam2;

	@Before
	public void setUp() {

		try {

			Context context = new InitialContext();
			challengeRemote = (ChallengeServiceRemote) context
					.lookup("ChallengeService/remote");

		} catch (NamingException e) {
			e.printStackTrace();
		}

		listTeam1 = new ArrayList<String>(5);
		listTeam2 = new ArrayList<String>(5);

		listTeam1.add("Jack");
		listTeam2.add("Marc");
		listTeam1.add("Cathy");
		listTeam2.add("Mick");
		listTeam1.add("Mona");
		listTeam2.add("Mary");
		listTeam1.add("Franck");
		listTeam2.add("Faby");
		listTeam1.add("Billy");
		listTeam2.add("Meriyam");

		nameTeam1 = "Cotagers";
		nameTeam2 = "Maggpies";

		challengeRemote.createChallenge(nameTeam1, nameTeam2, listTeam1,
				listTeam2);
	}

	@Test
	public void testChallengeFirstDuel() {

		play("Jack", "Marc", 3, 5, 8, 1);

		String expected = "Marc";
		assertEquals(challengeRemote.getWinnerOfDuel(0), expected);
	}

	@Test
	public void testChallenge() {

		play("Jack", "Marc", 3, 5, 8, 1);
		play("Cathy", "Mick", 3, 5, 8, 1);
		play("Mona", "Mary", 3, 5, 8, 1);
		play("Franck", "Faby", 3, 5, 8, 1);
		play("Billy", "Meriyam", 3, 5, 8, 1);

		String expected = nameTeam2;
		assertEquals(challengeRemote.getWinnerChallenge(), expected);
	}

	@Test(expected = EJBException.class)
	public void testReplayGamePlayer() {

		play("Jack", "Marc", 3, 5, 8, 1);
		play("Jack", "Mick", 3, 5, 8, 1);
		play("Mona", "Mary", 3, 5, 8, 1);
		play("Franck", "Faby", 3, 5, 8, 1);
		play("Billy", "Meriyam", 3, 5, 8, 1);
	}

	@Test(expected = EJBException.class)
	public void testUnknownPlayer() {

		play("Jack", "FERNANDO", 3, 5, 8, 1); // Unknown FERNANDO!
		play("Cathy", "Mick", 3, 5, 8, 1);
		play("Mona", "Mary", 3, 5, 8, 1);
		play("Franck", "Faby", 3, 5, 8, 1);
		play("Billy", "Meriyam", 3, 5, 8, 1);
	}

	@Test(expected = EJBException.class)
	public void testRollAfterOverChallenge() {

		play("Jack", "Marc", 3, 5, 8, 1);
		play("Cathy", "Mick", 3, 5, 8, 1);
		play("Mona", "Mary", 3, 5, 8, 1);
		play("Franck", "Faby", 3, 5, 8, 1);
		play("Billy", "Meriyam", 3, 5, 8, 1);
		play("Jack", "Mick", 3, 5, 8, 1); // Can't replay ;)
	}

	private void play(String name1, String name2, int roll1, int roll2,
			int roll3, int roll4) {

		for (int i = 0; i < 10; i++) {
			challengeRemote.rollFirstTeam(name1, roll1);
			challengeRemote.rollFirstTeam(name1, roll2);
			challengeRemote.rollSecondTeam(name2, roll3);
			challengeRemote.rollSecondTeam(name2, roll4);
		}
	}
}
