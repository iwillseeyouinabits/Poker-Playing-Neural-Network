import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class NeuralNetwork {

	double[][][] weights;
	double[] inputs;
	int size;
	Layer[] layers;

	/**
	 * 
	 * @param weights - the each layer of each neurons weights 
	 * @param inputs - the input to the network;
	 */
	public NeuralNetwork(double[][][] weights, double[] inputs) {
		this.weights = weights;
		this.inputs = inputs;
		size = this.weights.length;
		layers = new Layer[size];
		buildNetwork();
	}
	
	/**
	 * 
	 * @param file - the file associated with the weights to a neural network
	 * @param inputs - the inputs to the network
	 * @throws FileNotFoundException
	 */
	public NeuralNetwork(String file, double[] inputs) throws FileNotFoundException{
		this.weights = fileToWeights(file);
		this.inputs = inputs;
		size = this.weights.length;
		layers = new Layer[size];
		buildNetwork();
	}
	
	/**
	 * 
	 *creates random weighted neural network
	 *
	 * @param numInput - the number of inputs to the neural network
	 * @param numNeuronsInLayer - the number of neurons in each layer other than the output layer of a neural network
	 * @param numNeuronsInOutput - the number of neurons in the output layer of a neural network
	 * @param numLayers - the number of layers in the random weights of the neural network
	 */
	public NeuralNetwork(int numInput, int numNeuronsInLayer, int numNeuronsInOutput, int numLayers, double[] inputs){
		this.weights =  randomWeights(numInput, numNeuronsInLayer, numNeuronsInOutput, numLayers);
		this.inputs = inputs;
		size = this.weights.length;
		layers = new Layer[size];
		buildNetwork();
	}

	/**
	 * constructs a network build of layers
	 */
	private void buildNetwork() {
		for (int i = 0; i < size; i++) {
			if (i == 0) {
				layers[i] = new Layer(inputs, weights[i], weights[i].length);
			} else {
				layers[i] = new Layer(layers[i - 1].fire(), weights[i], weights[i].length);
			}
		}
	}

	/**
	 * gets the output of the neural network
	 * 
	 * @return - the output layer values
	 */
	public double[] fire() {
		double[] outputs = layers[size - 1].fire();
		return outputs;
	}

	/**
	 * gets the weights of each neuron in each layer of the neural network
	 * 
	 * @return - the weights of each neuron in each layer of the neural network
	 */
	public double[][][] getWeights() {
		return weights;
	}

	
	/**
	 * gets the inputed values to the neural network
	 * 
	 * @return - the inputed values to the neural network
	 */
	public double[] getInputs() {
		return inputs;
	}
	
	/**
	 * gets the numbber of layers in the neural network
	 * 
	 * @return - the numbber of layers in the neural network
	 */
	public int getSize() {
		return size;
	}

	
	/**
	 * gets the layers of the neural network
	 * 
	 * @return - gets the layers of the neural network
	 */
	public Layer[] getLayers() {
		return layers;
	}

	
	/**
	 * prints weights to console
	 */
	public void print() {
		for (int x = 0; x < size; x++) {
			for (int y = 0; y < weights[x].length; y++) {
				System.out.print("[ ");
				for (int z = 0; z < weights[x][y].length; z++) {
					System.out.print(weights[x][y][z] + " , ");
				}
				System.out.print("]");
			}
			System.out.println(" ");
		}
	}

	/**
	 *creates random weights to a neural network
	 *
	 * @param numInput - the number of inputs to the neural network
	 * @param numNeuronsInLayer - the number of neurons in each layer other than the output layer of a neural network
	 * @param numNeuronsInOutput - the number of neurons in the output layer of a neural network
	 * @param numLayers - the number of layers in the random weights of the neural network
	 * @return - random weights of a neural network
	 */
	public double[][][] randomWeights(int numInput, int numNeuronsInLayer, int numNeuronsInOutput, int numLayers) {
		double[][][] randW = new double[numLayers][][];
		for (int i = 0; i < numLayers; i++) {
			if (i < numLayers - 1) {
				randW[i] = new double[numNeuronsInLayer][];
			} else {
				randW[i] = new double[numNeuronsInOutput][];
			}
			for (int x = 0; x < randW[i].length; x++) {
				if (i == 0) {
					randW[i][x] = new double[numInput];
				} else {
					randW[i][x] = new double[randW[i-1].length];
				}
				for(int y = 0; y < randW[i][x].length; y++) {
					randW[i][x][y] = 2*(Math.random()-.5);
				}
			}
		}
		return randW;
	}

	
	/**
	 * takes a text file storing weights of a neural network in it and returns the neural networks weights
	 * 
	 * @return -  the neural networks weights that were stored in a file
	 */
	public double[][][] fileToWeights(String file) throws FileNotFoundException{
		double[][][] w;
		ArrayList<ArrayList<ArrayList<Double>>> tempW = new ArrayList<ArrayList<ArrayList<Double>>>();
		tempW.add(new ArrayList< ArrayList<Double>>());
		tempW.get(0).add(new ArrayList<Double>());
		int x = 0;
		int y = 0;
		Scanner sc = new Scanner(new File(file));
		while(sc.hasNext()){
			String curentS = sc.next();
			if(curentS.equals("?")){
				tempW.add(new ArrayList< ArrayList<Double>>());
				y = 0;
				x++;	
				tempW.get(x).add(new ArrayList<Double>());
			}else if(curentS.equals("*")){
				tempW.get(x).add(new ArrayList<Double>());
				y++;
			}else{
				double curentD = Double.parseDouble(curentS);
				tempW.get(x).get(y).add(curentD);
			}
		}
		
		w = new double[tempW.size()][][];
		for(int i = 0; i < tempW.size(); i++){
			w[i] = new double[tempW.get(i).size()][];
			for(int j = 0; j < tempW.get(i).size(); j++){
				w[i][j] = new double[tempW.get(i).get(j).size()];
				for(int k = 0; k < tempW.get(i).get(j).size(); k++){
					w[i][j][k] = tempW.get(i).get(j).get(k);
				}
			}
		}
		
		return w;
	}
	
	
	
}
