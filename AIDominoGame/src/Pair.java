
public class Pair {
	
	private int x;
	private int y;
	
	public Pair(int x, int y){
		this.x = x;
		this.y = y;
	}
	
	public int getX(){
		return x;
	}
	
	public int getY(){
		return y;
	}
	
	public boolean canConnect(int endPoint){
		return (this.x == endPoint || this.y == endPoint);
	}
	
	@Override
	public boolean equals(Object pair){
		Pair other = (Pair)pair;
		
		if((this.x == other.x && this.y == other.y) || 
				(this.x == other.y && this.y == other.x)){
			return true;
		}
		
		return false;
	}
	
	public int getOtherSide(int endPoint){
		if(this.x == endPoint){
			return this.y;
		}else{
			return this.x;
		}
	}

}
