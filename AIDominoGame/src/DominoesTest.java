import java.util.Random;


public class DominoesTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Random rand = new Random();
		DominoesState ds = new DominoesState(rand);
		GameManager game = new GameManager(ds);
		HumanPlayer me = new HumanPlayer();
		RandomPlayer player2 = new RandomPlayer(rand);
		RandomPlayer player3 = new RandomPlayer(rand);
		RandomPlayer player4 = new RandomPlayer(rand);
		
		game.addPlayer(me);
		game.addPlayer(player2);
		game.addPlayer(player3);
		game.addPlayer(player4);
		game.playToEnd();
	}

}
