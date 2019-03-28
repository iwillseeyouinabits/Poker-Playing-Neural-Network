import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;

import javax.swing.*;

public class Main {

	static int[] layers = new int[] { 8 * 5, 50, 6 };

	public static void main(String[] args) throws IOException, InterruptedException {
		Data d = new Data();
		// train 1000 random poker neural networks through a genetic algorithm
		double[][][][] players = new double[64][][][];
		for (int i = 0; i < players.length; i++) {
			players[i] = randW();
		}
		players = d.geneticPokerGame(players, layers, 500, 30);
		for (int i = 0; i < players.length; i++) {
			new Learn(players[i], 0.1).weightsToFile(players[i], i);
		}

		// test best trained network
		while (players.length > 1) {
			double[][][][] tempPlayers = new double[players.length / 8][][][];
			int iter = 0;
			for (int j = 0; j + 7 < players.length; j += 8) {
				int[] games = new int[8];
				for (int i = 0; i < 100; i++) {
					games[d.runGameV2(new double[][][][] { players[j], players[j + 1], players[j + 2], players[j + 3],
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
		new Learn(randW(), 0.1).weightsToFile(players[0], 0);
		new GUI().gui(new NeuralNetwork("weights V0.txt", new double[layers[0]]).getWeights(), new double[] { 100, 100, 100, 100, 100, 100, 100, 100 });
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
