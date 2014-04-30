import java.util.ArrayList;


public class GeneticPlayer implements IPlayer{
	private double[] weights;
	private int numOfWeights;

	public GeneticPlayer(double[] newWeights){
		this.numOfWeights = newWeights.length;
		weights = new double[numOfWeights];
		this.addWeights(newWeights);
	}

	@Override
	public MoveData getMove(DominoesState state) {
		double[] factors;
		//GameState Information
		int currentTurn = state.getCurrentTurn();
		Pair endPoints;
		PieceTracker tracker;
		ArrayList<Pair> hand;
		ArrayList<Pair> playedTiles;
		ArrayList<MoveData> legalMoves = state.getLegalMoves();
		double topValue = -999999999;
		DominoesState nextState; // = new DominoesState(state.getEndPoints(), state.getPlayedTiles(),
//				state.getPlayerHands(), state.getCurrentTurn(), state.getPasses(), state.getTrackers());
		MoveData bestMove = null;
		int numOfFactors = (int) ((-1+Math.sqrt(1+4*numOfWeights))/2);

		if(legalMoves.size() == 0){
			return new MoveData(null, -1);
		}

		//Equation Factors
		for(MoveData move : legalMoves){
			nextState = new DominoesState(state.getEndPoints(), state.getPlayedTiles(),
					state.getPlayerHands(), state.getCurrentTurn(), state.getPasses(), state.getTrackers());
			factors = new double[numOfFactors];
			nextState = nextState.makeMove(move);

			endPoints = nextState.getEndPoints();
			tracker = nextState.getTracker(currentTurn);
			hand = nextState.getHand(currentTurn);
			playedTiles = nextState.getPlayedTiles();

			factors[0] = ((double) playedTiles.size() / 28);
			int handSize = hand.size();
			factors[1] = ((double) handSize / 7);
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
			factors[2] = ((double)differentPieces / 7);

			for(Pair tile : playedTiles){
				totalScore = totalScore + tile.getX() + tile.getY();
			}
			factors[3] = ((double)totalScore / 168);

			probEndPoint1Enemy1 = this.probabilityOfNumber(tracker.getPossiblePlayerHand((currentTurn + 1) % 4), endPoints.getX());
			probEndPoint2Enemy1 = this.probabilityOfNumber(tracker.getPossiblePlayerHand((currentTurn + 1) % 4), endPoints.getY());

			factors[4] = (probEndPoint1Enemy1);
			factors[5] = (probEndPoint2Enemy1);

			probEndPoint1Ally = this.probabilityOfNumber(tracker.getPossiblePlayerHand((currentTurn + 2) % 4), endPoints.getX());
			probEndPoint2Ally = this.probabilityOfNumber(tracker.getPossiblePlayerHand((currentTurn + 2) % 4), endPoints.getY());

			factors[6] = (probEndPoint1Ally);
			factors[7] = (probEndPoint2Ally);

			probEndPoint1Enemy2 = this.probabilityOfNumber(tracker.getPossiblePlayerHand((currentTurn + 3) % 4), endPoints.getX());;
			probEndPoint2Enemy2 = this.probabilityOfNumber(tracker.getPossiblePlayerHand((currentTurn + 3) % 4), endPoints.getY());

			factors[8] = (probEndPoint1Enemy2);
			factors[9] = (probEndPoint2Enemy2);

			double temp = getStateValue(factors);

			if(topValue < temp){
				topValue = temp;
				bestMove = move;
			}
		}

		return bestMove;
	}

//		private double getStateValue(ArrayList<Double> factors){
//	
//			double value = 0;
//			String binary = "";
//	
//			for(int i = numOfWeights;i>0;i--){
//	
//				double multiplyFactors = 1;
//				binary = Integer.toBinaryString(i);
//				for(int j = binary.length()-1;j>=0;j--){
//					if(binary.charAt(j)=='1')
//						multiplyFactors *= factors.get(binary.length()-j-1);
//				}
//	
//				value += weights[i-1]*multiplyFactors;
//			}
//	
//			return value;
//		}

	private double getStateValue(double[] factors){

		double value = 0;
		int x = 0;
		
		for(int h = 0; h < factors.length; h++){
			value += weights[x]*factors[h];
			x++;
		}
		
		for(int i = 0; i < factors.length; i++)
			for(int j = 0; j < factors.length; j++){
				value += weights[x]*factors[i]*factors[j];
				x++;
			}

		return value;
	}

	private double probabilityOfNumber(ArrayList<Pair> hand, int endPoint){
		double s = 0;
		for(Pair tile : hand){
			if(tile.canConnect(endPoint)){
				s++;
			}
		}

		return (double)(s/28);
	}

	private void addWeights(double[] newWeights){

		if(newWeights.length != numOfWeights)
			return;

		for(int i = 0;i<numOfWeights;i++){
			weights[i] = newWeights[i];
		}
	}



}
