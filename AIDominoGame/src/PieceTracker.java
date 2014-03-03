import java.util.ArrayList;


public class PieceTracker {

	private ArrayList<ArrayList<Pair>> candidates = new ArrayList<ArrayList<Pair>>();
	private DominoesState state;
	private int myTurn;

	public PieceTracker(DominoesState state, int myTurn){
		this.state = state;
		this.myTurn = myTurn;

		for(int i = 0; i < 7; i++)
			for(int j = 0; j < 7; j++)
				for(int k = 0; k < 4 ; k++)
					if(k != myTurn && !candidates.get(k).contains(new Pair(i,j)))
							candidates.get(k).add(new Pair(i,j));
		this.removeHand();
	}

	private void removeHand(){
		for(Pair tile : state.getHand(myTurn)){
			for(int i = 0; i < 4; i++)
				if(i != myTurn && candidates.get(i).contains(tile))
						candidates.get(i).remove(tile);
		}
	}
	
	public void tilePlayed(Pair tile){
		for(int i = 0; i < 4; i++)
			if(i != myTurn)
				candidates.get(i).remove(tile);
	}

	public void playerPassed(){
		for(Pair tile : candidates.get(state.getCurrentTurn())){
			if(tile.canConnect(state.getEndPoints().getX()) || tile.canConnect(state.getEndPoints().getY())){
				candidates.get(state.getCurrentTurn()).remove(tile);
			}
		}
	}
	
	public ArrayList<Pair> getPossiblePlayerHand(int player){
		return candidates.get(player);
	}

}
