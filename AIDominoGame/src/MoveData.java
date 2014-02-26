
public class MoveData {
	
	private Pair tile;
	private int endPoint;
	
	public MoveData(Pair tile, int endPoint){
		this.tile = tile;
		this. endPoint = endPoint;
		
	}
	
	public Pair getTile(){
		return tile;		
	}
	
	public int getEndPoint(){
		return endPoint;		
	}
	
	@Override
	public boolean equals(Object pair){
		MoveData other = (MoveData)pair;
		
		if((this.tile.equals(other.tile) && this.endPoint == other.endPoint)){
			return true;
		}
		
		return false;
	}

}
