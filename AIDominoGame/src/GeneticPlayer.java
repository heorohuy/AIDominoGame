import java.util.ArrayList;
import java.util.Random;


public class GeneticPlayer implements IPlayer{
	private Random rand;
	private ArrayList<Double> weights = new ArrayList<Double>();
	private int numOfWeights;
	private int numOfFactors = 10;

	public GeneticPlayer(Random rand){
		this.rand = rand;
	}

	@Override
	public MoveData getMove(DominoesState state) {
		ArrayList<Double> factors = new ArrayList<Double>();
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
		factors.add((double) gameTurn);
		int handSize = hand.size();
		factors.add((double) handSize);
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
		factors.add((double)differentPieces);

		for(Pair tile : playedTiles){
			totalScore = totalScore + tile.getX() + tile.getY();
		}
		factors.add((double)totalScore);

		probEndPoint1Enemy1 = this.probabilityOfNumber(tracker.getPossiblePlayerHand(currentTurn + 1), endPoints.getX());
		probEndPoint2Enemy1 = this.probabilityOfNumber(tracker.getPossiblePlayerHand(currentTurn + 1), endPoints.getY());

		factors.add(probEndPoint1Enemy1);
		factors.add(probEndPoint2Enemy1);

		probEndPoint1Ally = this.probabilityOfNumber(tracker.getPossiblePlayerHand(currentTurn + 2), endPoints.getX());
		probEndPoint2Ally = this.probabilityOfNumber(tracker.getPossiblePlayerHand(currentTurn + 2), endPoints.getY());

		factors.add(probEndPoint1Ally);
		factors.add(probEndPoint2Ally);

		probEndPoint1Enemy2 = this.probabilityOfNumber(tracker.getPossiblePlayerHand(currentTurn + 3), endPoints.getX());;
		probEndPoint2Enemy2 = this.probabilityOfNumber(tracker.getPossiblePlayerHand(currentTurn + 3), endPoints.getY());

		factors.add(probEndPoint1Enemy2);
		factors.add(probEndPoint2Enemy2);



		// TODO Auto-generated method stub
		return null;
	}

	private double getStateValue(ArrayList<Double> factors){

		if(factors.size() != numOfFactors){
			return -1;
		}

		double value = 0;
		String binary = "";

		for(int i = numOfWeights;i>0;i--){

			double multiplyFactors = 1;
			binary = Integer.toBinaryString(i);
			for(int j = binary.length()-1;j>=0;j--){
				if(binary.charAt(j)=='1')
					multiplyFactors *= factors.get(binary.length()-j-1);
			}

			value += weights.get(i-1)*multiplyFactors;
		}

		return value;
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

	private int factorial(int x){
		int temp = 1;
		for(int i=x;i>0;i--){
			temp *= i;
		}
		return temp;
	}

	private void setWeights(ArrayList<Double> newWeights){

		if(newWeights.size() != numOfWeights)
			return;

		for(int i = 0;i<numOfWeights;i++){
			weights.set(i, newWeights.get(i));
		}
	}

}
