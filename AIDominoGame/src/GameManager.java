import java.util.ArrayList;
import java.util.Random;


public class GameManager {

	private ArrayList<IPlayer> players = new ArrayList<IPlayer>();
	private IPlayer temp;
	private DominoesState state;
	private int score1 = 0;
	private int score2 = 0;
	private Random rand;
	private int whoWon;

	public GameManager(Random rand){
		this.rand = rand;
		this.state = new DominoesState(rand);
	}

	public boolean playTurn(){
		IPlayer player = players.get(state.getCurrentTurn());
		MoveData move = player.getMove(state);
		state = state.makeMove(move);

		return !state.hasGameEnded();
	}

	private void playGame(){
		do{
			if(state.hasGameEnded()){
				if(state.whoWon() == 0 || state.whoWon() == 2){
					score1 += state.endScore();
				}else{
					score2 += state.endScore();
				}

				this.state = new DominoesState(rand);
			}			
		}while(this.playTurn());
	}

	public boolean playToEnd(){
		boolean win;
		
		organizePlayers();
		
		while(score1 < 200 && score2 < 200){
			whoWon = state.whoWon();
			if(whoWon != -1){
				setFirst(whoWon);
			}

			

			playGame();
		}
		if(score1 > score2){
			win = true;
		}else{
			win = false;
		}
		return win;
	}

	public void addPlayer(IPlayer player){
		if(!players.contains(player)){
			players.add(player);
		}
	}

	private void organizePlayers(){
		for(int i = 0; i < 4; i++)
			for(Pair tile : state.getPlayerHands().get(i)){
				if(tile.getX() == 6 && tile.getY() == 6){
					setFirst(i);
				}
			}
	}

	private void setFirst(int first){
		temp = players.get(first);
		for(int j = 0; j < first ; j++)
			for(int i = first-j; i < first-j+3; i++)
				players.set(i%4, players.get((i+1)%4));
		players.set(0,temp);
	}
}