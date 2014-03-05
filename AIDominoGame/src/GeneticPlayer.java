import java.util.ArrayList;


public class GeneticPlayer implements IPlayer{

	@Override
	public MoveData getMove(DominoesState state) {
		//GameState Information
		int currentTurn = state.getCurrentTurn();
		Pair endPoints = state.getEndPoints();
		int passes = state.getPasses();
		PieceTracker tracker = state.getTracker(currentTurn);
		ArrayList<Pair> hand = state.getHand(currentTurn);
		ArrayList<Pair> playedTiles = state.getPlayedTiles();
		ArrayList<MoveData> legalMoves = state.getLegalMoves();

		//Equation Factors
		int gameTurn = playedTiles.size();
		int handSize = hand.size();
		int differentPieces = 0;
		int totalScore = 0;
		double probEndPoint1Enemy1 = 0;
		double probEndPoint2Enemy1 = 0;
		double probEndPoint1Enemy2 = 0;
		double probEndPoint2Enemy2 = 0;
		double probEndPoint1Ally = 0;
		double probEndPoint2Ally = 0;

		for(Pair tile : hand){
			for(int i = 0; i < 7 ; i++){
				if(tile.canConnect(i)){
					differentPieces++;
					break;
				}
			}
		}
		
		for(Pair tile : playedTiles){
			totalScore = totalScore + tile.getX() + tile.getY();
		}
		
		probEndPoint1Enemy1 = this.probabilityOfNumber(tracker.getPossiblePlayerHand(currentTurn + 1), endPoints.getX());
		probEndPoint2Enemy1 = this.probabilityOfNumber(tracker.getPossiblePlayerHand(currentTurn + 1), endPoints.getY());
		
		probEndPoint1Ally = this.probabilityOfNumber(tracker.getPossiblePlayerHand(currentTurn + 2), endPoints.getX());
		probEndPoint2Ally = this.probabilityOfNumber(tracker.getPossiblePlayerHand(currentTurn + 2), endPoints.getY());
		
		probEndPoint1Enemy2 = this.probabilityOfNumber(tracker.getPossiblePlayerHand(currentTurn + 3), endPoints.getX());;
		probEndPoint2Enemy2 = this.probabilityOfNumber(tracker.getPossiblePlayerHand(currentTurn + 3), endPoints.getY());

		// TODO Auto-generated method stub
		return null;
	}
	
	private double probabilityOfNumber(ArrayList<Pair> hand, int endPoint){
		int s = 0;
		for(Pair tile : hand){
			if(tile.canConnect(endPoint)){
				s++;
			}
		}
		
		return s/28;
	}
	
	private int numWeights(int n){
		int x = 0;
		for(int k = 1; k <= n; k++){
			 x += factorial(n)/(factorial(k)*factorial(n-k));
		}
		return x;
	}
	
	private int factorial(int x){
		if(x == 0 || x == 1){
			return 1;
		}else{
			return x*factorial(x-1);
		}
	}

}
