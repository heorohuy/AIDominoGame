import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.AbstractMap.SimpleEntry;
import java.util.PriorityQueue;
import java.util.Random;



public class GeneticAlgorithm {
	File file = new File("filename.txt");
	FileWriter fw;
	BufferedWriter bw;
	private Random rand;
	private Random rand2;
	double[][] population;
	int gamesPerEval;
	int keepers;

	public GeneticAlgorithm(Random rand, int weights, int populationSize, int gamesPerEval, int keepers){
		this.rand = rand;
		//		this.population = initialPopulation(weights,populationSize);
		this.gamesPerEval = gamesPerEval;
		this.keepers = keepers;
		this.rand2 = new Random();

		if(!file.exists()){
			try {
				file.createNewFile();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		try {
			fw = new FileWriter(file.getAbsoluteFile());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		bw = new BufferedWriter(fw);

		this.population = initialPopulation(weights,populationSize);
	}

	public double[][] initialPopulation(int weights, int populationSize){
		double[][] individuals = new double[populationSize][weights];
		for(int i = 0; i < populationSize; i++){
			rand2 = new Random();
			for(int j = 0; j < weights; j++){
				if(rand2.nextBoolean())
					individuals[i][j] = rand2.nextDouble();
				else
					individuals[i][j] = -1*rand2.nextDouble();
			}
		}

		//		String playerInText = "{";
		//		
		//		for(double[] player : individuals){
		//			playerInText = "{";
		//			for(int i = 0; i < player.length; i++){
		//				playerInText += player[i];
		//				playerInText += " ";
		//			}
		//			try {
		//				playerInText += "}\n\n";
		//				bw.write(playerInText);
		//			} catch (IOException e) {
		//				// TODO Auto-generated catch block
		//				e.printStackTrace();
		//			}
		//		}

		return individuals;
	}

	public double[] beginEvolution(double goal, int generationLimit){

		PriorityQueue<SimpleEntry<Double,double[]>> pq;
		SimpleEntry<Double,double[]> topPlayer = new SimpleEntry<Double,double[]>(0.0,null);
		int currentGeneration = 0;
		double mutationRate = 0;

		do{
			long elapsed = System.currentTimeMillis();
			pq = new PriorityQueue<SimpleEntry<Double,double[]>>(100,new EntryComparator());

			//			String playerInText = "{";
			//			
			//			for(double[] player : population){
			//				for(int i = 0; i < player.length; i++){
			//					playerInText += player[i];
			//					playerInText += " ";
			//				}
			//				try {
			//					playerInText += "}\n";
			//					playerInText += "\n";
			//					bw.write(playerInText);
			//				} catch (IOException e) {
			//					// TODO Auto-generated catch block
			//					e.printStackTrace();
			//				}
			//			}

			for(int i = 0;i<population.length;i++){
				pq.add(new SimpleEntry<Double,double[]>(evaluate(new GeneticPlayer(population[i]), gamesPerEval), population[i]));
			}

			double[][] orderedList = new double[population.length][population[0].length];

			SimpleEntry<Double,double[]> aux = null;

			int i = 0;
			while(pq.size()>0){
				aux = pq.remove();
				orderedList[i] = aux.getValue();
				//				System.out.println(aux.getKey());
				i++;
			}

			currentGeneration++;

			if(aux.getKey() > topPlayer.getKey()){
				topPlayer = new SimpleEntry<Double,double[]>(aux.getKey(),aux.getValue());
				System.out.println("Latest Top Player has dominance of:" + topPlayer.getKey());
				System.out.println("At generation: " + currentGeneration);
				this.displayPlayer(topPlayer.getValue());
				System.out.println();
				mutationRate = 0;
			}else{
				mutationRate += 0.05;
			}

			if(mutationRate > 1){
				System.out.println("Latest Top Player has dominance of:" + topPlayer.getKey());
				return topPlayer.getValue();
			}

			population = crossOver(orderedList);
			population = mutate(population,mutationRate);

			elapsed = System.currentTimeMillis() - elapsed;
			System.out.printf("%d ms passed\n", elapsed);
			System.out.println(currentGeneration);



		}while((goal > topPlayer.getKey()) && (generationLimit > currentGeneration));

		return topPlayer.getValue();


	}

	private Double evaluate(GeneticPlayer player, int games){

		GameManager gm;
		double wins = 0;

		RandomPlayer randomPlayer1 = new RandomPlayer(rand);
		RandomPlayer randomPlayer2 = new RandomPlayer(rand);
		RandomPlayer randomPlayer3 = new RandomPlayer(rand);

		for(int i=0; i<games; i++){
			gm = new GameManager(new Random());

			gm.addPlayer(player);
			gm.addPlayer(randomPlayer1);
			gm.addPlayer(randomPlayer2);
			gm.addPlayer(randomPlayer3);

			if(gm.playToEnd())
				wins++;
		}

		return wins/(games);

	}

	public double[][] crossOver(double[][] parents){
		double[][] children = parents.clone();
		double[] individualMale = new double[parents[0].length];
		double[] individualFemale = new double[parents[0].length];
		int size = children[0].length;
		int maleIndex = 0, femaleIndex = 0;
		int ran = rand.nextInt(children.length);

		for(int j = 0; j < ((children.length/2) + ran); j++){
			if(j < children.length/2){
				maleIndex = j;
			}else{
				maleIndex = rand.nextInt(children.length-keepers);
			}
			femaleIndex = rand.nextInt(children.length-keepers);
			double[] male = children[maleIndex];
			double[] female = children[femaleIndex];
			for(int k = 0; k < size; k++){
				boolean ran2 = rand.nextBoolean();
				if(ran2){
					individualMale[k] = female[k];
					individualFemale[k] = male[k];
				}else{
					individualMale[k] = male[k];
					individualFemale[k] = female[k];
				}
			}
			children[maleIndex+keepers] = individualMale.clone();
			children[femaleIndex+keepers] = individualFemale.clone();
		}

		return children;
	}

	public double[][] mutate(double[][] population, double mutationRate){
		double[][] mutatedPopulation = population;

		for(int i = 0; i < mutatedPopulation.length; i++){
			if(rand.nextDouble() <= mutationRate){
				if(rand.nextBoolean()){
					mutatedPopulation[i][rand.nextInt(mutatedPopulation[i].length)] = rand.nextDouble();
				}else {
					mutatedPopulation[i][rand.nextInt(mutatedPopulation[i].length)] = -1*rand.nextDouble();
				}
			}
		}

		//		for(int i = 0; i < mutatedPopulation.length; i++){
		//			if(i < mutatedPopulation.length/2){
		//				if(rand.nextBoolean() && rand.nextBoolean()){
		//					mutatedIndividual = mutatedPopulation[i];
		//					if(rand.nextBoolean()){
		//						mutatedIndividual[rand.nextInt(mutatedIndividual.length)] = rand.nextDouble();
		//					}else {
		//						mutatedIndividual[rand.nextInt(mutatedIndividual.length)] = -1*rand.nextDouble();
		//					}
		//					mutatedPopulation[i] = mutatedIndividual.clone();
		//				}
		//			}else{
		//				if(rand.nextBoolean()){
		//					mutatedIndividual = mutatedPopulation[i];
		//					if(rand.nextBoolean()){
		//						mutatedIndividual[rand.nextInt(mutatedIndividual.length)] = rand.nextDouble();
		//					}else {
		//						mutatedIndividual[rand.nextInt(mutatedIndividual.length)] = -1*rand.nextDouble();
		//					}
		//					mutatedPopulation[i] = mutatedIndividual.clone();
		//				}
		//			}
		//		}

		return mutatedPopulation;
	}

	public void displayPlayer(double[] player){
		for(int i = 0; i < player.length; i++){
			if(i == 0){
				System.out.print("The player's values are: ");
			}
			System.out.print(player[i] + " ");
		}
	}

}
