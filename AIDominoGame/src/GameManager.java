import java.util.ArrayList;


public class GameManager {

	private ArrayList<IPlayer> players = new ArrayList<IPlayer>();
	private DominoesState state;
	
	public GameManager(DominoesState state){
		this.state = state;
	}
	
	public boolean playTurn(){
		IPlayer player = players.get(state.getCurrentTurn());
		MoveData move = player.getMove(state);
		state = state.makeMove(move);
		
		return state.hasGameEnded();
	}
	
	public void playToEnd(){
		while(this.playTurn());
	}
}