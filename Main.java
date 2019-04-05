import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;
import java.util.concurrent.TimeUnit;

import javax.lang.model.util.Elements;
import javax.swing.*;

public class Main {

	static int[] layers = new int[] { 8*5, 50, 50, 50, 6 };

	public static void main(String[] args) throws IOException, InterruptedException {
		System.out.println("8");
		double[][][][] players = new double[8 * 8][][][];
		Data d = new Data();
		Thread.sleep(1000);
		// train 64 random poker neural networks through a genetic algorithm
		for (int i = 0; i < players.length; i++) {
			players[i] = randW();
		}
		players = d.geneticPokerGame(players, 150, 300);
		for (int i = 0; i < players.length; i++) {
			new Learn(players[i], 0.1).weightsToFile(players[i], i);
		}

		Thread.sleep(10000);
		// find best trained network
		for (int i = 0; i < players.length; i++) {
			players[i] = new NeuralNetwork("weights V" + i + ".txt", new double[layers[0]]).getWeights();
		}
		while (players.length > 1) {
			double[][][][] tempPlayers = new double[players.length / 8][][][];
			int iter = 0;
			for (int j = 0; j + 7 < players.length; j += 8) {
				int[] games = new int[8];
				for (int i = 0; i < 100; i++) {
					games[d.runGame(new double[][][][] { players[j], players[j + 1], players[j + 2], players[j + 3],
							players[j + 4], players[j + 5], players[j + 6], players[j + 7] })]++;
				}
				int bestPos = 0;
				int bestWinNum = 0;
				for (int i = 0; i < games.length; i++) {
					if (bestWinNum < games[i]) {
						bestPos = i;
						bestWinNum = games[i];
					}
				}
				tempPlayers[iter] = players[j + bestPos];
				iter++;
			}
			players = tempPlayers;
		}
		// write best trained network's weights to "weights V0.txt" file and runs the
		// GUI for user input to that best network
		new Learn(randW(), 0.1).weightsToFile(players[0], 0);
		new GUI().gui(new NeuralNetwork("weights V0.txt", new double[layers[0]]).getWeights(),
				new double[] { 100, 100, 100, 100, 100, 100, 100, 100 });
	}

	public static double[][][] randW() {
		double[][][] w = new double[layers.length - 1][][];
		for (int i = 1; i < layers.length; i++) {
			w[i - 1] = new double[layers[i]][layers[i - 1]];
			for (int j = 0; j < w[i - 1].length; j++) {
				for (int k = 0; k < w[i - 1][j].length; k++) {
					w[i - 1][j][k] = 2 * Math.random() - 1;
				}
			}
		}
		return w;
	}

	
}
