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
		if(move.getEndPoint() != -1)
			System.out.println("Player " + state.getCurrentTurn() + " Played: " + move.getTile().getX() + "," + move.getTile().getY());
		else
			System.out.println("Player " + state.getCurrentTurn() + " Passed.");
		
		for(int i = 0; i < 4; i++)
			if(i != state.getCurrentTurn()){
				System.out.println("Player " + i + " possible tiles");
				for(int j = 0; j < 3; j++)
					for(Pair tiles : state.getTracker(i).getPossiblePlayerHand(j))
						System.out.println("["+ tiles.getX() + "," + tiles.getY() + "]");
			}

		return !state.hasGameEnded();
	}

	public void playToEnd(){
		while(this.playTurn());
		System.out.println("Game Over");
	}

	public void addPlayer(IPlayer player){
		if(!players.contains(player)){
			players.add(player);
		}
	}
}