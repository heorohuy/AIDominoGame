import java.util.ArrayList;


public class PieceTracker {

	private ArrayList<ArrayList<Pair>> candidates = new ArrayList<ArrayList<Pair>>();

	public PieceTracker(){
		for(int i = 0; i < 4; i++)
			candidates.add(new ArrayList<Pair>());
		for(int i = 0; i < 7; i++)
			for(int j = i; j < 7; j++)
				for(int k = 0; k < 4 ; k++)
					candidates.get(k).add(new Pair(i,j));
	}

	private PieceTracker(ArrayList<ArrayList<Pair>> candidates){
		this.candidates = candidates;
	}

	@SuppressWarnings("unchecked")
	public PieceTracker clone(){
		ArrayList<ArrayList<Pair>> copyCandidates = new ArrayList<ArrayList<Pair>>();
		for(int i = 0; i < candidates.size(); i++)
			copyCandidates.add((ArrayList<Pair>) candidates.get(i).clone());

		return new PieceTracker(copyCandidates);
	}

	@SuppressWarnings("unchecked")
	public PieceTracker removeHand(ArrayList<Pair> hand, int player){
		PieceTracker nextCandidates = this.clone();
		for(int i = 0; i < 4; i++){
			if(i != player){
				for(Pair tile : hand){
					nextCandidates.candidates.get(i).remove(tile);
				}
			}
		}
		nextCandidates.candidates.set(player, (ArrayList<Pair>) hand.clone());
		return nextCandidates;
	}

	public PieceTracker tilePlayed(Pair tile, ArrayList<ArrayList<Pair>> hands){
		PieceTracker nextCandidates = this.clone();
		for(int i = 0; i < 4; i++)
			nextCandidates.candidates.get(i).remove(tile);
		this.updateHands(hands);

		return nextCandidates;
	}

	public PieceTracker playerPassed(int player, Pair endPoints, ArrayList<ArrayList<Pair>> hands){
		PieceTracker nextCandidates = this.clone();
		for(Pair tile : this.candidates.get(player)){
			if(tile.canConnect(endPoints.getX()) || tile.canConnect(endPoints.getY())){
				nextCandidates.candidates.get(player).remove(tile);
			}
		}
		this.updateHands(hands);

		return nextCandidates;
	}

	@SuppressWarnings("unchecked")
	public ArrayList<Pair> getPossiblePlayerHand(int player){
		return (ArrayList<Pair>) candidates.get(player).clone();
	}

	private void updateHands(ArrayList<ArrayList<Pair>> hands){
		for(int i = 0; i < 4; i++){
			if(candidates.get(i).size() == hands.get(i).size()){
				for(int j = 0; j < 4; j++){
					if(i != j){
						for(Pair tile : candidates.get(i)){
							candidates.get(j).remove(tile);
						}
					}
				}
			}
		}
	}

}
