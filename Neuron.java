public class Neuron {
	double[] weights;
	double[] inputs;
	double total;
	double bias;
	

	/**
	 * 
	 * @param weights - an array for all of the weighting of the neurons inputs
	 * @param inputs - the neurons inputs
	 * @param bias - the integer constant for a bias
	 */
	public Neuron(double[] weights, double[] inputs) {
		this.weights = weights;
		this.inputs = inputs;
		this.bias = 1;
		setTotal();
	}

	/**
	 * gets the inputs multiplies them by the weights and adds all of the
	 * weights multiplied by the inputs together. Finally it squashes the total
	 * and returns it.
	 * 
	 * @return - the output of the neuron
	 */
	public double fire() {
		return  squash(total);
	}

	private void setTotal() {
		for (int i = 0; i < weights.length; i++)
			total += weights[i] * inputs[i];
		total += bias;
	}

	/**
	 * takes a total of the values gathered by the neuron and squashes it to
	 * make the network non linear in higher dimensions.
	 * 
	 * @param total
	 *            - the total of the inputs multiplied by their corresponding
	 *            weights
	 * @return - the squashed total
	 */
	public double squash(double total) {
		return (1.0/(1.0 + Math.pow(Math.E, -total)));	
	}

	
	/**
	 * returns all of the inputs to the neuron
	 * @return - the input values to the neuron
	 */
	public double[] getInputs() {
		return this.inputs;
	}

	
	/**
	 * returns the weights for every input value
	 * @return
	 */
	public double[] getWeights() {
		return this.weights;
	}

	
	/**
	 * returns the total of all of the inputs to the neuron;
	 * @return
	 */
	public double getTotal() {
		return this.total;
	}

}