package service.challenge;

import java.util.ArrayList;

import javax.ejb.Remote;

import domain.model.challenge.Challenge;
import domain.model.duel.Duel;

@Remote
public interface ChallengeServiceRemote {

	public void createChallenge(String nameTeam1, String nameTeam2,
			ArrayList<String> listTeam1, ArrayList<String> listTeam2);

	void rollFirstTeam(String player, int roll);

	void rollSecondTeam(String player, int roll);

	public int getScoreTeam1();

	public int getScoreTeam2();

	public String getWinnerChallenge();
	
	public String getWinnerOfDuel(int num);
	
	public void controlEndChallenge();
	
	public Duel getDuel(int num);

	public Challenge newChallenge(String name1, String name2);

	public Challenge saveChallenge();

	public Challenge loadChallenge(int id);

}
