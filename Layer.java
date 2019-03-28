
public class Layer {
	double [] inputs;
	double [][] weights;
	int size;
	Neuron[] neurons;
	
	
	/**
	 * 
	 * @param inputs - the input to the layer of the neural network
	 * @param weights - each neurons weights for each input
	 * @param size - the number of neurons in the layer
	 */
	public Layer(double[] inputs, double[][] weights, int size){
		this.inputs = inputs;
		this.weights = weights;
		this.size = size;
		neurons = new Neuron[size];
		buildLayer();
	}
	
	/**
	 * builds the layer of neurons with the given weights and inputs
	 */
	private void buildLayer(){
		for(int i = 0; i < size; i++){
			neurons[i] = new Neuron(weights[i], inputs);
		}
	}
	
	/**
	 * Returns an array of the layers outputs of the layers neurons
	 * @return - the outputs of the layers neurons
	 */
	public double[] fire(){
		double[] outputs = new double[size];
		for(int i = 0; i < size; i++)
			outputs[i] = neurons[i].fire();
		return outputs;
	}
	
	 /**
	 * Returns an array of the inputs to this layer
	 * @return - an array of the inputs to this layer
	 */
	public double[] getInputs() {
		return inputs;
	}

	
	 /**
	 * Returns an array of weights of each neuron in this layer
	 * @return - an array of weights of each neuron in this layer
	 */
	public double[][] getWeights() {
		return weights;
	}
	
	 /**
	 * Returns the number of neurons in this layer of the neural network
	 * @return - the number of neurons in this layer of the neural network
	 */
	public int getSize() {
		return size;
	}

	
	 /**
	 * Returns the neurons in this layer of the neural network
	 * @return - the neurons in this layer of the neural network
	 */
	public Neuron[] getNeurons() {
		return neurons;
	}
	
	
	
}
