import java.util.ArrayList;
import java.util.Random;

public class DominoesState {

	private Pair endPoints;
	private ArrayList<Pair> playedTiles = new ArrayList<Pair>();
	private ArrayList<ArrayList<Pair>> playerHands = new ArrayList<ArrayList<Pair>>();
	private int currentTurn = 0;
	private int passes = 0;
	private ArrayList<PieceTracker> trackers = new ArrayList<PieceTracker>();
	
	public DominoesState(Random rand){
		playerHands = initHands(rand);
		for(int i = 0; i < 4; i++){
			trackers.add(new PieceTracker());
			trackers.get(i).removeHand(playerHands.get(i),i);//Possible Bug
		}
	}

	public DominoesState(Pair endPoints, ArrayList<Pair> playedTiles,
			ArrayList<ArrayList<Pair>> playerHands, int currentTurn, 
			int passes, ArrayList<PieceTracker> trackers){

		this.endPoints = endPoints;
		this.currentTurn = currentTurn;
		this.passes = passes;
		this.playedTiles = playedTiles;
		this.playerHands = playerHands;
		this.trackers = trackers;
	}

	private static ArrayList<ArrayList<Pair>> initHands(Random rand){
		
		ArrayList<ArrayList<Pair>> firstHands = new ArrayList<ArrayList<Pair>>();
		ArrayList<Pair> tileSet = new ArrayList<Pair>();
		int num;
		Pair temp;
		for(int i = 0; i < 7; i++)
			for(int j = i; j < 7; j++)
				tileSet.add(new Pair(i,j));

		for(int i = tileSet.size() - 1; i > 1; i--){
			num = rand.nextInt(i + 1);
			temp = tileSet.get(i);
			tileSet.set(i, tileSet.get(num));
			tileSet.set(num, temp);
		}

		for(int k = 0, h = 0; k < 4; k++) {
			for(int i = 0; i < 7; i++, h++){
				firstHands.get(k).add(tileSet.get(h));
			}
		}
		return firstHands;
	}

	public ArrayList<Pair> getHand(int player){
		return playerHands.get(player);
	}

	public Pair getEndPoints(){
		return endPoints;
	}

	@SuppressWarnings("unchecked")
	public DominoesState makeMove(MoveData move){
		ArrayList<ArrayList<Pair>> nextPlayerHands = new ArrayList<ArrayList<Pair>>();
		ArrayList<Pair> nextPlayedTiles = new ArrayList<Pair>();
		Pair nextEndPoints;
		ArrayList<PieceTracker> nextTrackers = new ArrayList<PieceTracker>();
		
		if(isMoveLegal(move)){
			if(move.getEndPoint() == -1){
				for(int i = 0; i < 4; i++)
					if(i != currentTurn)
						nextTrackers.add(trackers.get(i).playerPassed(currentTurn, endPoints, playerHands));
				return new DominoesState(endPoints, playedTiles, 
						playerHands, currentTurn+1, passes+1, nextTrackers);
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

			for(int i = 0; i < 4; i++)
				nextTrackers.add(trackers.get(i).tilePlayed(move.getTile(),playerHands));

			return new DominoesState(nextEndPoints, nextPlayedTiles,
					nextPlayerHands, (currentTurn + 1)&3, 0, nextTrackers);
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

	public int getPasses() {
		return passes;
	}

	public PieceTracker getTracker(int player) {
		return trackers.get(player);
	}

	public ArrayList<Pair> getPlayedTiles() {
		return playedTiles;
	}
}
