import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Learn {

	double[][][] weights;
	NeuralNetwork net;
	double[][] errorNeurons;
	double learningRate;
	double errornum = 0;
	double errorden = 0;
	int num = 0;
	double prog = 100;
	boolean good = true;

	/**
	 * @param weights      - the weights to a neural network
	 * @param learningRate - the rate at which the network should make learning
	 *                     adjustments
	 */
	public Learn(double[][][] weights, double learningRate) {
		this.weights = weights;
		errorNeurons = new double[weights.length][];
		this.learningRate = learningRate;
	}

	/**
	 * adjusts a neural networks weights to better based on the error in the output
	 * of the neural network
	 * 
	 * @param weights     - weights that need to be adjusted
	 * @param inputs      - the inputs to the neural network that may need to be
	 *                    optimized
	 * @param rightOutput - the output that the neural network should have
	 *                    calculated and should be adjusted to calculate.
	 * @return - the adjusted weights to the neural network
	 */
	public double[][][] learn(double[][][] weights, double[] inputs, double[] rightOutput) {
		net = new NeuralNetwork(weights, inputs);
		double[] out = net.fire();

		for (int i = 0; i < inputs.length; i++) {
			System.out.print(inputs[i] + "   ");
		}
		System.out.println("   #########");
		for (int i = 0; i < out.length; i++) {
			System.out.print(out[i] + "     " + rightOutput[i] + "      ");
		}
		System.out.println("   ????????????");

		for (int x = weights.length - 1; x >= 0; x--) {
			errorNeurons[x] = new double[weights[x].length];
			for (int y = 0; y < weights[x].length; y++) {
				if (x == weights.length - 1) {
					errorNeurons[x][y] = (out[y] - rightOutput[y])
							* derivative(net.getLayers()[x].getNeurons()[y].getTotal());
					errornum += Math.abs(out[y] - rightOutput[y]);
					errorden += Math.abs(rightOutput[y]);

				} else {
					errorNeurons[x][y] = 0;
					for (int i = 0; i < errorNeurons[x + 1].length; i++) {
						errorNeurons[x][y] += errorNeurons[x + 1][i] * weights[x + 1][i][y];
					}
					errorNeurons[x][y] *= derivative(net.getLayers()[x].getNeurons()[y].getTotal());
				}

				// revises weights
				if (x != 0)
					for (int z = 0; z < weights[x][y].length; z++) {
						weights[x][y][z] -= learningRate * errorNeurons[x][y]
								* net.getLayers()[x - 1].getNeurons()[z].fire();
					}
				else
					for (int z = 0; z < weights[x][y].length; z++) {
						weights[x][y][z] -= learningRate * errorNeurons[x][y] * inputs[z];
					}
			}
		}
		return weights;
	}

	/**
	 * Corrects the weights in a neural network to better perform on a dataset
	 * 
	 * @param w        - the weights that need to be optimized
	 * @param it       - the number of samples for the neural network to practice
	 *                 gradient decent on
	 * @param stop     - the error ratio that would be enough for the network to
	 *                 stop training
	 * @param num      - the number of networks to train
	 * @param testData - a list of lists of input and corresponding output data
	 * @return - a list of weights optimized to better perform in the dataset;
	 * @throws IOException
	 */
	public double[][][][] learnRecur(double[][][] w, int it, double stop, int num, double[][][] testData)
			throws IOException {
		boolean run = true;
		int count = 0;
		double[][][][] allW = new double[num][][][];
		for (int x = 0; x < num; x++) {
			int subcount = 0;
			loop: while (count < it) {
				System.out.println((errornum / errorden) + "     " + x + "  !!!!!!!!!!!!!");
				// System.out.println(" ");
				double[][] data = testData(testData);
				w = learn(weights, data[0], data[1]);
				if (errornum / errorden <= stop && subcount >= 300) {
					errornum = 0;
					errorden = 0;
					run = false;
					break loop;
				}
				if (count == it - 1) {
					run = false;
					System.out.println("fired");
				}
				if (count % 10000 == 0) {
					System.out.println("hi there");
					if (prog < errornum / errorden) {
						prog = 100;
						run = true;
						errornum = 0;
						errorden = 0;
						good = false;
						x--;
						break loop;
					} else {
						good = true;
						prog = errornum / errorden;
						subcount = 0;
						errornum = 0;
						errorden = 0;
					}
				}
				subcount++;
				count++;
			}
			if (run) {
				return learnRecur(randW(), it, stop, num, testData);
			}

			if (good) {
				allW[x] = w;
				weightsToFile(w, x);
			}
		}
		return allW;
	}

	/**
	 * picks a singular set of inputs and their outputs from a data set
	 * 
	 * @param d - the dataset to pick from
	 * @return - the set of inputs and their corisponding outputs
	 */
	public static double[][] testData(double[][][] d) {
		int rand = (int) (Math.random() * d.length);
		return d[rand];

	}

	
	/**
	 * calculates of derivative of the squashing function of the neuron at a value x
	 * @param x - the x position to find the derivative of.
	 * @return
	 */
	public double derivative(double x) {
		return (Math.pow(Math.E, x)) / Math.pow(1.0 + Math.pow(Math.E, x), 2);
	}

	
	/**
	 * gets a file and outputs the weights that are stored in the file
	 * @param file - the file that this program stored weights in
	 * @return - the weights of that file
	 */
	public double[][][] fileToWeights(String file) {
		double[][][] w;
		ArrayList<ArrayList<ArrayList<Double>>> tempW = new ArrayList<ArrayList<ArrayList<Double>>>();
		tempW.add(new ArrayList<ArrayList<Double>>());
		tempW.get(0).add(new ArrayList<Double>());
		int x = 0;
		int y = 0;
		int z = 0;
		Scanner sc = new Scanner(file);
		while (sc.hasNext()) {
			if (sc.next() == "?") {
				tempW.add(new ArrayList<ArrayList<Double>>());
				y = 0;
				x++;
			} else if (sc.next() == "*") {
				tempW.get(0).add(new ArrayList<Double>());
				y++;
			} else {
				tempW.get(x).get(y).add(sc.nextDouble());
			}
		}

		w = new double[tempW.size()][][];
		for (int i = 0; i < tempW.size(); i++) {
			w[i] = new double[tempW.get(i).size()][];
			for (int j = 0; j < tempW.get(i).size(); j++) {
				w[i][j] = new double[tempW.get(i).get(j).size()];
				for (int k = 0; k < tempW.get(i).get(j).size(); k++) {
					w[i][j][k] = tempW.get(i).get(j).get(k);
				}
			}
		}

		return w;
	}
	
	public void weightsToFile(double[][][] w, int x) throws IOException {
			BufferedWriter bw = new BufferedWriter(new FileWriter("weights V" + Integer.toString(x) + ".txt"));
			double[][][] matrix = w;
			for (int i = 0; i < matrix.length; i++) {
				for (int j = 0; j < matrix[i].length; j++) {
					for (int z = 0; z < matrix[i][j].length; z++) {
						if (z != matrix[i][j].length - 1) {
							bw.write(Double.toString(matrix[i][j][z]));
							bw.newLine();
						} else {
							bw.write(Double.toString(matrix[i][j][z]));
							bw.newLine();
						}
					}
					if (j != weights[i].length - 1) {
						bw.write("*");
						bw.newLine();
					}
				}
				if (i != weights.length - 1) {
					bw.write("?");
					bw.newLine();
				}
			}

			bw.close();
	}

	/**
	 * Creates a random set of weights
	 * @return - a random set of weights
	 */
	public double[][][] randW() {
		double[][][] w = new double[weights.length][][];
		for (int x = 0; x < weights.length; x++) {
			w[x] = new double[weights[x].length][];
			for (int y = 0; y < weights[x].length; y++) {
				w[x][y] = new double[weights[x][y].length];
				for (int z = 0; z < weights[x][y].length; z++) {
					w[x][y][z] = Math.random() * 8.0 - 4.0;
				}
			}
		}
		return w;
	}

}