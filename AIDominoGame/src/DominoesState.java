import java.util.ArrayList;


public class DominoesState {
	
	private Pair endPoints;
	private ArrayList<Pair> playedTiles = new ArrayList<Pair>();
	private ArrayList<ArrayList<Pair>> playerHands = new ArrayList<ArrayList<Pair>>();
	private int currentTurn = 0;
	private int passes = 0;

	public DominoesState(Pair endPoints, ArrayList<Pair> playedTiles,
			ArrayList<ArrayList<Pair>> playerHands, int currentTurn, 
			int passes){
		
		this.endPoints = endPoints;
		this.currentTurn = currentTurn;
		this.passes = passes;
		this.playedTiles = playedTiles;
		this.playerHands = playerHands;
	}
	
	public Pair getEndPoints(){
		return endPoints;
	}
	
	@SuppressWarnings("unchecked")
	public DominoesState makeMove(MoveData move){
		ArrayList<ArrayList<Pair>> nextPlayerHands = new ArrayList<ArrayList<Pair>>();
		ArrayList<Pair> nextPlayedTiles = new ArrayList<Pair>();
		Pair nextEndPoints;
		if(isMoveLegal(move)){
			if(move.getEndPoint() == -1){
				return new DominoesState(endPoints, playedTiles, 
						playerHands, currentTurn+1, passes+1);
			}
			
			if(move.getEndPoint() == endPoints.getX()){
				nextEndPoints = new Pair(endPoints.getY(),move.getTile().getOtherSide(move.getEndPoint()));
			}else{
				nextEndPoints = new Pair(endPoints.getX(),move.getTile().getOtherSide(move.getEndPoint()));
			}
			
			nextPlayedTiles = (ArrayList<Pair>)playedTiles.clone();
			nextPlayedTiles.add(move.getTile());
			
			nextPlayerHands = (ArrayList<ArrayList<Pair>>)playerHands.clone();;
			nextPlayerHands.set(currentTurn, (ArrayList<Pair>)(playerHands.get(currentTurn).clone()));
			nextPlayerHands.get(currentTurn).remove(move.getTile());
			
			return new DominoesState(nextEndPoints, nextPlayedTiles,
					nextPlayerHands, (currentTurn + 1)&3, 0);
		}
		throw new IllegalArgumentException("Not legal move.");
	}
	
	public boolean isMoveLegal(MoveData move){
		ArrayList<MoveData> legalMoves = getLegalMoves();
		if(move.getEndPoint() == -1){
			return legalMoves.isEmpty();
		}else{
			return legalMoves.contains(move);
		}
	}
	
	public ArrayList<MoveData> getLegalMoves(){
		
		ArrayList<MoveData> legalMoves = new ArrayList<MoveData>();
		
		for(Pair tile : playerHands.get(currentTurn)){
			if(tile.canConnect(endPoints.getX())){
				legalMoves.add(new MoveData(tile, endPoints.getX()));
			}
			if(tile.canConnect(endPoints.getY()) && 
					(endPoints.getX() != endPoints.getY())){
				legalMoves.add(new MoveData(tile, endPoints.getY()));
			}
		}
		return legalMoves;
	}
	
	public int getCurrentTurn(){
		return currentTurn;
	}
	
	public boolean hasGameEnded(){
		for(ArrayList<Pair> hand : playerHands)
			if(hand.isEmpty())
				return true;
		
		return passes >= 4;
	}
}