package domain.model.player;

public class GameFactoryForTest {

	public Game newGame()
	{
		Frame[] frames = new Frame[10];
		for (int i = 0; i < 9; i++) {
			
			frames[i] = new NormalFrame();
		}
		frames[9] = new LastFrame(); 
		Game g = new Game(frames);
		return g;
	}
	
}
