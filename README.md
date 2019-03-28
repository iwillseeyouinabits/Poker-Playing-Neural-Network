# Java Neural Network

Java Neural Network for people to understand how gradient decent in a neural network is calculated and for people to use in AI and Machine Learning projects.

## How To Use This Code

The key training methods of this project are the LearnRecur and LearnRecurGPU methods of the Learn class. These methods will take in untrained weights of a neural network and train them on a given numerical data set.

The key methods to use for a trained neural network are the fire method of the NeuralNetwork class which will take the input of the neural network and compute the neural networks precieved correct output based on the input. This neural network could contain random weights to produce random outputs based on the inputs to the neural network or it could use trained weights to a dataset from a file or double[][][] array of weights. 

### Prerequisites

The computer running this code must already be running java and must jave the abuility to compile java code in an IDE or text editor

### Installing

This code should be put in a text editor or IDE and added to a java file with a main method to run the code.

an example main method may be to train a neural network to approximate the Math.cos() method in java

```java
import java.io.IOException;

public class Main {
	public static void main(String args[]) throws IOException, InterruptedException {
		double[][][] randomWeights = new NeuralNetwork(1, 5, 1, 2, new double[1]).getWeights();
		// define a Learn object with a learning rate of .1
		Learn learn = new Learn(randomWeights, 0.1);
		// training a set of 5 random weights of 5 neural network for either 10000000
		// iterations or until it achieves a 0.7% error rate on a data set provided by the
		// getDataOfCosFunction method
		learn.learnRecur(randomWeights, 1000000, .007, 5, getDataOfCosFunction(100));
		System.out.println();
		System.out.println("What do our 5 neural networks approximate the value of Math.cos(0.65)?");
		System.out.println("--> " + ((new NeuralNetwork("weights V0.txt", new double[] { 0.5 }).fire()[0]
				+ new NeuralNetwork("weights V1.txt", new double[] { 0.65 }).fire()[0]
				+ new NeuralNetwork("weights V2.txt", new double[] { 0.65 }).fire()[0]
				+ new NeuralNetwork("weights V3.txt", new double[] { 0.65 }).fire()[0]
				+ new NeuralNetwork("weights V4.txt", new double[] { 0.65 }).fire()[0]) / 5));
		System.out.println("What is the actual value of Math.cos(0.65)?");
		System.out.println("--> " + Math.cos(0.65));
	}

	/**
	 * gets numInput amount of inputs of a random number from 0 to 1 and the output
	 * of the Math.cos() of that number
	 * 
	 * @param numInputs
	 * @return - the data of the Math.cos() of a bunch of random numbers between 0
	 *         and 1
	 */
	public static double[][][] getDataOfCosFunction(int numInputs) {
		double[][][] data = new double[numInputs][2][];
		for (int i = 0; i < numInputs; i++) {
			double[] inputs = new double[] { Math.random() };
			double[] correctOutputs = new double[] { Math.cos(inputs[0]) };
			data[i][0] = inputs;
			data[i][1] = correctOutputs;
		}
		return data;
	}

}

```


when running this test I was able to achieve the output to the console of:

```

What do our 5 neural networks approximate the value of Math.cos(0.65)?
--> 0.8134524119730848
What is the actual value of Math.cos(0.65)?
--> 0.7960837985490559

```


## Running the tests
 
 You can run this test by downloading all of the files in this project and coppying the above code into your main java file.

## Deployment

This neural network can only output data in the range from 0 to 1 so all data should be normalized to that range for best results. This neural network is free to use with no legal restrictions.

## Authors

* **Isaac Singer**

See also the list of [contributors](https://github.com/iwillseeyouinabits/neural-network/graphs/contributors) who participated in this project.

