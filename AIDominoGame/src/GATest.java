import java.util.Random;


public class GATest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Random rand = new Random();
		double[] KICKASSPLAYER = new double[110];
		GeneticAlgorithm ga = new GeneticAlgorithm(rand, 110, 100, 100, 0);
		
		KICKASSPLAYER = ga.beginEvolution(0.80,1000);

		ga.displayPlayer(KICKASSPLAYER);
	}

}
