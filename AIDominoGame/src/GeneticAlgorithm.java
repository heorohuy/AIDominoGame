import java.util.ArrayList;
import java.util.Random;



public class GeneticAlgorithm {
	private Random rand;
	DominoesState state;

	public GeneticAlgorithm(Random rand){
		this.rand = rand;
		this.state = new DominoesState(rand);
	}

	public ArrayList<ArrayList<Double>> inicialPopulation(int weights, int populationSize){
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

	public ArrayList<ArrayList<Double>> crossOver(ArrayList<ArrayList<Double>> parents){
		ArrayList<ArrayList<Double>> children = parents;
		ArrayList<Double> individualMale = new ArrayList<Double>();
		ArrayList<Double> individualFemale = new ArrayList<Double>();
		int size = children.get(0).size();
		int maleIndex = 0, femaleIndex = 0;

		for(int j = 0; j < ((children.size()/2) + rand.nextInt(children.size())); j++){
			if(j < children.size()/2){
				maleIndex = j;
			}else{
				maleIndex = rand.nextInt(children.size());
			}
			femaleIndex = rand.nextInt(children.size());
			ArrayList<Double> male = children.get(maleIndex);
			ArrayList<Double> female = children.get(femaleIndex);
			for(int k = 0; k < size; k++){
				for(int i = 0; i < rand.nextInt(size); i++){
					int crossovers = rand.nextInt(size);
					if(crossovers == k){
						individualMale.add(female.get(k));
						individualFemale.add(male.get(k));
					}else{
						individualMale.add(male.get(k));
						individualFemale.add(female.get(k));
					}
				}
			}
			children.set(maleIndex, individualMale);
			children.set(femaleIndex, individualFemale);
		}

		return children;
	}

	public ArrayList<ArrayList<Double>> mutate(ArrayList<ArrayList<Double>> population){
		ArrayList<ArrayList<Double>> mutatedPopulation = population;
		ArrayList<Double> mutatedIndividual;

		for(int i = 0; i < mutatedPopulation.size(); i++){
			if(i < mutatedPopulation.size()/2){
				if(rand.nextBoolean() && rand.nextBoolean()){
					mutatedIndividual = mutatedPopulation.get(i);
					if(rand.nextBoolean()){
						mutatedIndividual.set(rand.nextInt(mutatedIndividual.size()), rand.nextDouble());
					}else {
						mutatedIndividual.set(rand.nextInt(mutatedIndividual.size()), -1*rand.nextDouble());
					}
					mutatedPopulation.set(i,mutatedIndividual);
				}
			}else{
				if(rand.nextBoolean()){
					mutatedIndividual = mutatedPopulation.get(i);
					if(rand.nextBoolean()){
						mutatedIndividual.set(rand.nextInt(mutatedIndividual.size()), rand.nextDouble());
					}else {
						mutatedIndividual.set(rand.nextInt(mutatedIndividual.size()), -1*rand.nextDouble());
					}
					mutatedPopulation.set(i,mutatedIndividual);
				}
			}
		}

		return mutatedPopulation;
	}

	public void displayPlayer(ArrayList<Double> player){
		for(int i = 0; i < player.size(); i++){
			if(i == 0){
				System.out.print("The player's values are: ");
			}
			System.out.print(player.get(i) + " ");
		}
	}

}
