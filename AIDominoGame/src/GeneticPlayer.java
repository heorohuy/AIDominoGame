import java.util.ArrayList;


public class GeneticPlayer implements IPlayer{

	@Override
	public MoveData getMove(DominoesState state) {
		int currentTurn = state.getCurrentTurn();
		Pair endPoints = state.getEndPoints();
		int passes = state.getPasses();
		PieceTracker tracker = state.getTracker(currentTurn);
		ArrayList<Pair> hand = state.getHand(currentTurn);
		ArrayList<Pair> playedTiles = state.getPlayedTiles();
		ArrayList<MoveData> legalMoves = state.getLegalMoves();
		
		
		// TODO Auto-generated method stub
		return null;
	}

}
