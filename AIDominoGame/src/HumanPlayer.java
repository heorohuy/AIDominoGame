import java.util.Scanner;


public class HumanPlayer implements IPlayer {

	private Scanner in;

	@Override
	public MoveData getMove(DominoesState state) {
		
		in = new Scanner(System.in);
		String a;
		Pair tile;
		MoveData move;
		
		System.out.println("Enter the tile: (Format: x,y)");
		a=in.next();
		
		tile = new Pair(((int)a.charAt(0)),((int)a.charAt(2)));
		
		System.out.println("Enter the Endpoint: (Format: x");
		a=in.next();
		
		move = new MoveData(tile,((int)a.charAt(0)));
		
		return move;
	}

}
