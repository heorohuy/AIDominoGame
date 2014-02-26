import java.util.ArrayList;
import java.util.Random;


public class RandomPlayer implements IPlayer {

	private Random rand = new Random();
	
	@Override
	public MoveData getMove(DominoesState state) {
		ArrayList<MoveData> moves = state.getLegalMoves();
		if (!moves.isEmpty())
			return moves.get(rand.nextInt());
		else 
			return new MoveData(null, -1);
	}
}
