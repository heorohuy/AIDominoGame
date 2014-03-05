import java.util.ArrayList;
import java.util.Random;



public class GeneticAlgorithm {
	private Random rand = new Random();
	DominoesState state = new DominoesState(rand);
	
	public GeneticAlgorithm(){
		
	}
	
	public ArrayList<ArrayList<Double>> InicialPopulation(int weights, int populationSize){
		ArrayList<ArrayList<Double>> individuals = new ArrayList<ArrayList<Double>>();
		ArrayList<Double> player = new ArrayList<Double>();
		for(int i = 0; i < populationSize; i++){
			for(int j = 0; j < weights; j++){
				if(rand.nextBoolean())
					player.add(rand.nextDouble());
				else
					player.add(-1*rand.nextDouble());
			}
			individuals.add(player);
			player.clear();
		}
		
		return individuals;
	}
	
}
