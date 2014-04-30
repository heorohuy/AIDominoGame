import java.util.ArrayList;
import java.util.Scanner;


public class HumanPlayer implements IPlayer {

	private Scanner in;

	@Override
	public MoveData getMove(DominoesState state) {

		in = new Scanner(System.in);
		String a;
		Pair tile;
		MoveData move;
		ArrayList<Pair> hand = state.getHand(state.getCurrentTurn());

		System.out.println("Your hand: ");
		for(int i = 0; i < hand.size() ; i++)
			System.out.println("[" + hand.get(i).getX() + "," + hand.get(i).getY() + "] ");

		System.out.println("End Points are: " + state.getEndPoints().getX() + " and " + state.getEndPoints().getY());

		System.out.println("Enter the tile: (Format: x,y)");
		a=in.next();

		tile = new Pair(Integer.parseInt(a.substring(0,1)), Integer.parseInt(a.substring(2,3)));

		System.out.println("Enter the Endpoint: (Format: x)");
		a=in.next();

		move = new MoveData(tile, Integer.parseInt(a));

		return move;
	}

}
