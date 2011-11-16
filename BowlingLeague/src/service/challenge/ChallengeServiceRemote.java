package service.challenge;

import java.util.ArrayList;

import javax.ejb.Remote;

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

}
