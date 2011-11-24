package domain.model.player;

public class PlayerFactoryForTest implements PlayerFactoryLocal {
	
@Override
public Player newPlayer(String name) {
		
		Player p = new Player(name);
		Frame[] frames = new Frame[10];
		for (int i = 0; i < 9; i++) {
			
			frames[i] = new NormalFrame();
		}
		frames[9] = new LastFrame(); 
		Game g = new Game(frames);
		p.setGame(g);
		return p;
	}

}
