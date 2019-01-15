
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
	

	public double[] getInputs() {
		return inputs;
	}

	public double[][] getWeights() {
		return weights;
	}

	public int getSize() {
		return size;
	}

	public Neuron[] getNeurons() {
		return neurons;
	}
	
	
	
}
