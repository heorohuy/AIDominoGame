import java.util.ArrayList;


public class PieceTracker {
	
	private ArrayList<Pair> candidates = new ArrayList<Pair>();
	
	public PieceTracker(DominoesState state){
		//TODO
		for(int i = 0; i < 7; i++)
			for(int j = 0; j < 7; j++)
				candidates.add(new Pair(i,j));
		
	}
	
	

}
