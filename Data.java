import java.io.IOException;
import java.util.ArrayList;

public class Data {

	/**
	 * Gets a rough magnitude approximation of the strength of a set of cards in a game of poker
	 * @param cards - the cards that will be approximated the strength of
	 * @return - the rough magnitude of the strength of a set of cards where 0 is very poor and 649740 is among the very best
	 */
	public double getOdds(int[][] cards) {
		double odds = 0;
		// checkForRoyal
		int[][] allRoyal = new int[4][5];
		for (int i = 0; i < cards.length; i++) {
			if (cards[i][1] > 9)
				allRoyal[cards[i][0]][cards[i][1] - 10] = 1;
		}
		int minLeft = 5;
		for (int[] set : allRoyal) {
			int count = 0;
			for (int card : set) {
				if (card == 0) {
					count++;
				}
			}
			if (minLeft > count)
				minLeft = count;
		}
		if (minLeft <= 7 - cards.length) {
			double tempOdds = 1;
			for (int i = 0; i < minLeft; i++) {
				tempOdds /= 52;
			}
			odds += tempOdds * 649740;
		}
		// checkForStrFlush
		int[][] allStFl = new int[4][13];
		for (int i = 0; i < cards.length; i++) {
			allStFl[cards[i][1]][cards[i][0]] = 1;
		}
		int minAway = 5;
		int pos = 0;
		for (int[] suet : allStFl) {
			int iter = 0;
			while (true) {
				int tempMin = 0;
				for (int i = iter; i - iter < 5; i++) {
					if (suet[i] == 0)
						tempMin++;
				}
				if (minAway > tempMin) {
					minAway = tempMin;
					pos = iter;
				}
				iter++;
				if (iter + 5 >= 13) {
					break;
				}
			}
		}
		if (minAway <= 7 - cards.length) {
			double tempOdds = 1;
			for (int i = 0; i < minAway; i++) {
				tempOdds /= 52;
			}
			odds += tempOdds * 72192 * ((1 + pos) / 10.0);
		}
		// checkFourPair
		int[] nums = new int[13];
		for (int[] card : cards) {
			nums[card[0]] += 1;
		}
		minAway = 4;
		pos = 0;
		for (int i = 0; i < nums.length; i++) {
			if (4 - nums[i] < minAway && 4 - nums[i] >= 0) {
				minAway = 4 - nums[i];
				pos = i;
			}
		}
		if (minAway <= 7 - cards.length) {
			double tempOdds = 1;
			for (int i = 0; i < minAway; i++) {
				tempOdds *= (minAway - i) / 52;
			}
			odds += tempOdds * 4165 * ((1 + pos) / 13.0);
		}
		// checkFullHouseint[]
		nums = new int[13];
		for (int[] card : cards) {
			nums[card[0]] += 1;
		}
		minAway = 5;
		pos = 0;
		for (int i = 0; i < 12; i++) {
			for (int j = i + 1; j < 13; j++) {
				if (5 - nums[i] + nums[j] < minAway && 5 - nums[i] + nums[j] >= 0 && nums[i] < 4 && nums[j] < 4) {
					minAway = 5 - nums[i] - nums[j];
					if (nums[i] > nums[j])
						pos = i;
					else
						pos = j;
				}
			}
		}
		if (minAway <= 7 - cards.length) {
			double tempOdds = 1;
			for (int i = 0; i < minAway; i++) {
				tempOdds *= (minAway - i) / 52;
			}
			odds += tempOdds * 693 * ((1 + pos) / 13.0);
		}
		// checkFlush
		int[] suets = new int[4];
		for (int[] card : cards) {
			suets[card[1]]++;
		}
		minAway = 5;
		for (int suet : suets) {
			if (minAway > 5 - suet)
				minAway = 5 - suet;
		}
		if (minAway <= 7 - cards.length) {
			double tempOdds = 1;
			for (int i = 0; i < minAway; i++) {
				tempOdds /= 4;
			}
			odds += tempOdds * 508;
		}
		// checkFlush
		minAway = 5;
		pos = 0;
		nums = new int[13];
		for (int[] card : cards) {
			nums[card[0]] = 1;
		}
		int iter = 0;
		while (true) {
			int tempMin = 0;
			for (int i = iter; i - iter < 5; i++) {
				if (nums[i] == 0)
					tempMin++;
			}
			if (minAway > tempMin) {
				minAway = tempMin;
				pos = iter;
			}
			iter++;
			if (iter + 5 >= 13) {
				break;
			}
		}
		if (minAway <= 7 - cards.length) {
			double tempOdds = 1;
			for (int i = 0; i < minAway; i++) {
				tempOdds *= (4 / 52);
			}
			odds += tempOdds * 254 * ((1 + pos) / 10.0);
		}
		// checkThreeOfAKind
		nums = new int[13];
		for (int[] card : cards) {
			nums[card[0]] += 1;
		}
		minAway = 3;
		pos = 0;
		for (int i = 0; i < nums.length; i++) {
			if (3 - nums[i] < minAway && 3 - nums[i] >= 0) {
				minAway = 3 - nums[i];
				pos = i;
			}
		}
		if (minAway <= 7 - cards.length) {
			double tempOdds = 1;
			for (int i = 0; i < minAway; i++) {
				tempOdds *= (minAway - i) / 52;
			}
			odds += tempOdds * 46.3 * ((1 + pos) / 13.0);
		}
		// check two pair
		nums = new int[13];
		for (int[] card : cards) {
			nums[card[0]] += 1;
		}
		minAway = 4;
		pos = 0;
		for (int i = 0; i < 12; i++) {
			for (int j = i + 1; j < 13; j++) {
				if (4 - nums[i] + nums[j] < minAway && 4 - nums[i] + nums[j] >= 0 && nums[i] < 3 && nums[j] < 3) {
					minAway = 4 - nums[i] - nums[j];
					if (nums[i] > nums[j])
						pos = i;
					else
						pos = j;
				}
			}
		}
		if (minAway <= 7 - cards.length) {
			double tempOdds = 1;
			for (int i = 0; i < minAway; i++) {
				tempOdds *= (minAway - i) / 52;
			}
			odds += tempOdds * 21.0 * ((1 + pos) / 13.0);
		}
		// checkPair
		nums = new int[13];
		for (int[] card : cards) {
			nums[card[0]] += 1;
		}
		minAway = 2;
		pos = 0;
		for (int i = 0; i < nums.length; i++) {
			if (2 - nums[i] < minAway && 2 - nums[i] >= 0) {
				minAway = 2 - nums[i];
				pos = i;
			}
		}
		if (minAway <= 7 - cards.length) {
			double tempOdds = 1;
			for (int i = 0; i < minAway; i++) {
				tempOdds *= (minAway - i) / 52;
			}
			odds += tempOdds * 46.3 * ((1 + pos) / 13.0);
		}
		return odds;
	}

	
	/**
	 * gets random cards dealt in a 8 player poker game
	 * @return - the cards in a random game of poker
	 */
	public int[][][] game() {
		int[][] deck = new int[][] { { 0, 0 }, { 1, 0 }, { 2, 0 }, { 3, 0 }, { 4, 0 }, { 5, 0 }, { 6, 0 }, { 7, 0 },
				{ 8, 0 }, { 9, 0 }, { 10, 0 }, { 11, 0 }, { 12, 0 }, { 0, 1 }, { 1, 1 }, { 2, 1 }, { 3, 1 }, { 4, 1 },
				{ 5, 1 }, { 6, 1 }, { 7, 1 }, { 8, 1 }, { 9, 1 }, { 10, 1 }, { 11, 1 }, { 12, 1 }, { 0, 2 }, { 1, 2 },
				{ 2, 2 }, { 3, 2 }, { 4, 2 }, { 5, 2 }, { 6, 2 }, { 7, 2 }, { 8, 2 }, { 9, 2 }, { 10, 2 }, { 11, 2 },
				{ 12, 2 }, { 0, 3 }, { 1, 3 }, { 2, 3 }, { 3, 3 }, { 4, 3 }, { 5, 3 }, { 6, 3 }, { 7, 3 }, { 8, 3 },
				{ 9, 3 }, { 10, 3 }, { 11, 3 }, { 12, 3 }, };
		int[][][] game = new int[4][][];
		int[] all = new int[16 + 5];
		for (int i = 0; i < all.length; i++) {
			all[i] = -1;
		}
		int pos = (int) (Math.random() * deck.length);
		int iter = 0;
		int[][] pocket = new int[16][];
		while (true) {
			boolean run = true;
			for (int card : all) {
				if (card == pos)
					run = false;
			}
			if (run) {
				all[iter] = pos;
				pocket[iter] = deck[pos];
				iter++;
			} else {
				pos = (int) (Math.random() * deck.length);
			}
			if (iter == pocket.length) {
				game[0] = pocket;
				break;
			}
		}
		pos = (int) (Math.random() * deck.length);
		iter = 0;
		int[][] flop = new int[3][];
		while (true) {
			boolean run = true;
			for (int card : all) {
				if (card == pos)
					run = false;
			}
			if (run) {
				all[2 + iter] = pos;
				flop[iter] = deck[pos];
				iter++;
			} else {
				pos = (int) (Math.random() * deck.length);
			}
			if (iter == flop.length) {
				game[1] = flop;
				break;
			}
		}

		pos = (int) (Math.random() * deck.length);
		iter = 0;
		int[][] river = new int[1][];
		while (true) {
			boolean run = true;
			for (int card : all) {
				if (card == pos)
					run = false;
			}
			if (run) {
				all[5 + iter] = pos;
				river[iter] = deck[pos];
				iter++;
			} else {
				pos = (int) (Math.random() * deck.length);
			}
			if (iter == river.length) {
				game[2] = river;
				break;
			}
		}

		pos = (int) (Math.random() * deck.length);
		iter = 0;
		int[][] gm = new int[1][];
		while (true) {
			boolean run = true;
			for (int card : all) {
				if (card == pos)
					run = false;
			}
			if (run) {
				all[6 + iter] = pos;
				gm[iter] = deck[pos];
				iter++;
			} else {
				pos = (int) (Math.random() * deck.length);
			}
			if (iter == gm.length) {
				game[3] = gm;
				break;
			}
		}
		return game;
	}

	
	/**
	 * gets the winning player of all the players playing in a hand of poker based on the playing players cards
	 * @param game - the cards that were dealt in a game of poker
	 * @param outPlayers - the current playing players in a hand of poker who have not busted or folded
	 * @return - the winning player in a hand of poker based on the current players best hands
	 */
	public ArrayList<Integer> winner(int[][][][] game, int[] outPlayers) {
		ArrayList<Integer> winners = new ArrayList<Integer>();
		int[][] streightPos = new int[8][9];
		int[][] setsPos = new int[8][13];
		int[][] flush = new int[8][2];
		int[][][] hands = new int[8][][];
		for (int i = 0; i < 8; i++) {
			hands[i] = new int[][] { game[i][0][0], game[i][0][1], game[i][1][0], game[i][1][1], game[i][1][2],
					game[i][2][0], game[i][3][0] };
		}

		for (int i = 0; i < 8; i++) {
			for (int j = 9; j >= 0; j--) {
				int[] str = new int[5];
				int numStr = 0;
				for (int k = 0; k < 7; k++) {
					if (hands[i][k][0] - j >= 0 && hands[i][k][0] - j < 5 && str[hands[i][k][0] - j] != 1) {
						str[hands[i][k][0] - j] = 1;
						numStr++;
					}
				}
				if (numStr == 5) {
					streightPos[i][j] = 1;
					break;
				}
			}
			for (int k = 0; k < 7; k++) {
				setsPos[i][hands[i][k][0]]++;
			}
			int[] suets = new int[4];
			for (int j = 0; j < 7; j++) {
				suets[hands[i][j][1]]++;
			}
			int j = 0;
			for (int suet : suets) {
				if (suet >= 5) {
					flush[i][0] = 1;
					flush[i][1] = j;
				}
				j++;
			}
		}

		boolean run = false;
		int maxPos = -1;
		// roayl flush or streight flush
		for (int i = 0; i < 8; i++) {
			for (int j = 8; j >= 0; j--) {
				if (streightPos[i][j] == 1 && flush[i][0] == 1 && maxPos <= j && outPlayers[i] != 1) {
					if (maxPos < j) {
						winners.clear();
					}
					winners.add(i);
					maxPos = j;
					run = true;
					break;
				}
			}
		}
		if (run) {
			return winners;
		}

		// four of a kind
		for (int i = 0; i < 8; i++) {
			for (int j = 12; j >= 0; j--) {
				if (setsPos[i][j] == 4 && maxPos <= j && outPlayers[i] != 1) {
					if (maxPos < j) {
						winners.clear();
					}
					winners.add(i);
					maxPos = j;
					run = true;
					break;
				}
			}
		}
		if (run) {
			return winners;
		}

		// full house
		int[] threeSetPos = new int[8];
		int[] twoSetPos = new int[8];
		for (int i = 0; i < 8; i++) {
			for (int j = 13; j > 0; j--) {
				if (setsPos[i][j - 1] >= 3) {
					threeSetPos[i] = j;
					break;
				}
			}
		}
		for (int i = 0; i < 8; i++) {
			for (int j = 13; j > 0; j--) {
				if (setsPos[i][j - 1] >= 2 && j != threeSetPos[i]) {
					twoSetPos[i] = j;
					break;
				}
			}
		}
		for (int i = 0; i < 8; i++) {
			if (threeSetPos[i] > 0 && twoSetPos[i] > 0 && threeSetPos[i] >= maxPos && outPlayers[i] != 1) {
				if (maxPos < threeSetPos[i]) {
					winners.clear();
				}
				winners.add(i);
				maxPos = threeSetPos[i];
				run = true;
				break;
			}
		}
		if (run) {
			return winners;
		}
		// flush
		for (int i = 0; i < 8; i++) {
			if (flush[i][0] > 0) {
				int tempMaxPos = -1;
				for (int j = 0; j < 7; j++) {
					if (hands[i][j][0] > tempMaxPos && hands[i][j][1] == flush[i][1]) {
						tempMaxPos = hands[i][j][0];
					}
				}
				if (tempMaxPos > maxPos && outPlayers[i] != 1) {
					if (maxPos < tempMaxPos) {
						winners.clear();
					}
					winners.add(i);
					maxPos = tempMaxPos;
					run = true;
					break;
				}
			}
		}
		if (run) {
			return winners;
		}
		for (int j = 8; j >= 0; j--) {
			for (int i = 0; i < 8; i++) {
				if (streightPos[i][j] == 1 && maxPos <= j && outPlayers[i] != 1) {
					if (maxPos < j) {
						winners.clear();
					}
					winners.add(i);
					maxPos = j;
					run = true;
					break;
				}
			}
		}
		if (run) {
			return winners;
		}

		// twoPair
		int[] twoSet1 = new int[8];
		int[] twoSet2 = new int[8];
		for (int i = 0; i < 8; i++) {
			for (int j = 13; j > 0; j--) {
				if (setsPos[i][j - 1] >= 2) {
					twoSet1[i] = j;
					break;
				}
			}
		}
		for (int i = 0; i < 8; i++) {
			for (int j = 13; j > 0; j--) {
				if (setsPos[i][j - 1] >= 2 && j != twoSet1[i]) {
					twoSet2[i] = j;
					break;
				}
			}
		}
		for (int i = 0; i < 8; i++) {
			if (twoSet1[i] > 0 && twoSetPos[i] > 0 && twoSet1[i] >= maxPos && outPlayers[i] != 1) {
				if (maxPos < twoSet2[i]) {
					winners.clear();
				}
				winners.add(i);
				maxPos = twoSet1[i];
				run = true;
				break;
			}
		}
		if (run) {
			return winners;
		}

		// onePair
		int[] pair = new int[8];
		for (int i = 0; i < 8; i++) {
			for (int j = 13; j > 0; j--) {
				if (setsPos[i][j - 1] >= 2) {
					pair[i] = j;
					break;
				}
			}
		}
		for (int i = 0; i < 8; i++) {
			if (pair[i] > 0 && pair[i] >= maxPos && outPlayers[i] != 1) {
				if (maxPos < pair[i]) {
					winners.clear();
				}
				winners.add(i);
				maxPos = pair[i];
				run = true;
				break;
			}
		}
		if (run) {
			return winners;
		}

		// high card
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 7; j++)
				if (hands[i][j][0] >= maxPos && outPlayers[i] != 1) {
					if (maxPos < hands[i][j][0]) {
						winners.clear();
					}
					winners.add(i);
					maxPos = hands[i][j][0];
					run = true;
					break;
				}
		}
		if (run) {
			return winners;
		}

		return winners;
	}

	/**
	 * This method will take a set of poker players, let them compete with each other, and let the best survive and breed and the worst slowly die off 
	 * @param players - The weights of the neural networks for the players to begin the genetic algorithm population with
	 * @param numGen - the number of generations that the players will compete and breed in
	 * @param numGamesInGen - the number of games played in each generation to calculate the best and worst poker playing players
	 * @return - the final population of poker playing neural networks after all the generations
	 * @throws IOException
	 */
	public double[][][][] geneticPokerGame(double[][][][] players, int numGen, int numGamesInGen)
			throws IOException {
		for (int x = 0; x < numGen; x++) {
			ArrayList<double[][][]> winners = new ArrayList<double[][][]>();
			int iter = 0;
			ArrayList<Integer> poses = new ArrayList<Integer>();
			for (int i = 0; i < players.length; i++) {
				poses.add(i);
			}
			double[][][][] tempPlayers = new double[players.length][][][];
			while (poses.size() > 0) {
				int pos = (int) (Math.random() * poses.size());
				tempPlayers[iter] = players[poses.get(pos)];
				poses.remove(pos);
				iter++;
			}
			players = tempPlayers;
			for (int i = 0; i + 7 < players.length; i += 8) {
				int[] numWin = new int[8];
				for (int j = 0; j < numGamesInGen; j++) {
					numWin[runGame(new double[][][][] { players[i], players[i + 1], players[i + 2], players[i + 3],
							players[i + 4], players[i + 5], players[i + 6], players[i + 7] })]++;
				}
				double[][][] winner = null;
				int maxWin = 0;
				for (int j = 0; j < numWin.length; j++) {
					if (numWin[j] > maxWin) {
						maxWin = numWin[j];
						winner = players[i + j];
					}
				}
				winners.add(winner);
			}
			for (int j = 0; j < players.length * 0.1; j++) {
				winners.add(players[(int) (players.length * Math.random())]);
			}
			for (int j = 0; j < winners.size(); j++) {
				players[j] = winners.get(j);
			}
			for (int i = winners.size(); i < players.length; i++) {
				double[][][] p1 = winners.get((int) (winners.size() * Math.random()));
				double[][][] child = new double[p1.length][][];
				for (int j = 0; j < p1.length; j++) {
					child[j] = new double[p1[j].length][];
					for (int k = 0; k < p1[j].length; k++) {
						double rand = Math.random();
						if (rand < 0.95) {
							child[j][k] = p1[j][k];
						} else {
							child[j][k] = new Learn(p1, 0.1).randW()[j][k];
						}
					}
					players[i] = child;
				}
			}
			if (x % (numGen * .1) == 0) {
				for (int i = 0; i < players.length; i++) {
					new Learn(players[i], 0.1).weightsToFile(players[i], i);
				}
			}
		}
		return players;
	}

	public static double[][][] randW(int[] layers) {
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

	/**
	 * takes the neural network weights of 8 poker players and plays them against each other for one game then returns index of the winning player
	 * @param players - neural network weights of 8 poker players
	 * @return - the index of the winning player
	 */
	public int runGame(double[][][][] players) {
		int inGame = 8;
		int[] playersInGame = new int[8];
		double[] bankRole = new double[] { 100, 100, 100, 100, 100, 100, 100, 100 };

		double[] vpipnum = new double[8];
		double[] pfrnum = new double[8];
		double[] aggnum = new double[8];
		double gameCount = (int) (Math.random() * 64);
		for (int j = 0; j < gameCount; j++) {
			int tempplayersInGame = playersInGame[0];
			double tempbankRole = bankRole[0];
			double tempvpipnum = vpipnum[0];
			double temppfrnum = pfrnum[0];
			double tempaggnum = aggnum[0];
			double[][][] tempPlayers = players[0];
			for (int i = 0; i < 8 - 1; i++) {
				playersInGame[i] = playersInGame[i + 1];
				bankRole[i] = bankRole[i + 1];
				vpipnum[i] = vpipnum[i + 1];
				pfrnum[i] = pfrnum[i + 1];
				aggnum[i] = aggnum[i + 1];
				players[i] = players[i+1];
			}
			playersInGame[7] = tempplayersInGame;
			bankRole[7] = tempbankRole;
			vpipnum[7] = tempvpipnum;
			pfrnum[7] = temppfrnum;
			aggnum[7] = tempaggnum;
			players[7] = tempPlayers;
		}

		while (inGame > 1) {
			gameCount++;
			// move Players;

			int tempplayersInGame = playersInGame[0];
			double tempbankRole = bankRole[0];
			double tempvpipnum = vpipnum[0];
			double temppfrnum = pfrnum[0];
			double tempaggnum = aggnum[0];
			double[][][] tempPlayers = players[0];
			for (int i = 0; i < 8 - 1; i++) {
				playersInGame[i] = playersInGame[i + 1];
				bankRole[i] = bankRole[i + 1];
				vpipnum[i] = vpipnum[i + 1];
				pfrnum[i] = pfrnum[i + 1];
				aggnum[i] = aggnum[i + 1];
				players[i] = players[i+1];
			}
			playersInGame[7] = tempplayersInGame;
			bankRole[7] = tempbankRole;
			vpipnum[7] = tempvpipnum;
			pfrnum[7] = temppfrnum;
			aggnum[7] = tempaggnum;
			players[7] = tempPlayers;
			System.out.println("GAME " + gameCount + " -- " + inGame + "  Players");
			double pot = 0;
			int[][][] game = game();
			int[] playersInHand = new int[] { playersInGame[0], playersInGame[1], playersInGame[2], playersInGame[3],
					playersInGame[4], playersInGame[5], playersInGame[6], playersInGame[7] };
			int inHand = inGame;
			loop: for (int round = 0; round < 4; round++) {
				// getHand
				int[][][] hand = null;
				if (round == 0) {
					hand = new int[8][2][];
					for (int i = 0; i < 8; i++) {
						hand[i][0] = game[0][i * 2];
						hand[i][1] = game[0][i * 2 + 1];
					}
				} else if (round == 1) {
					hand = new int[8][5][];
					for (int i = 0; i < 8; i++) {
						hand[i][0] = game[0][i * 2];
						hand[i][1] = game[0][i * 2 + 1];
						hand[i][2] = game[1][0];
						hand[i][3] = game[1][1];
						hand[i][4] = game[1][2];
					}
				} else if (round == 2) {
					hand = new int[8][6][];
					for (int i = 0; i < 8; i++) {
						hand[i][0] = game[0][i * 2];
						hand[i][1] = game[0][i * 2 + 1];
						hand[i][2] = game[1][0];
						hand[i][3] = game[1][1];
						hand[i][4] = game[1][2];
						hand[i][5] = game[2][0];
					}
				} else {
					hand = new int[8][7][];
					for (int i = 0; i < 8; i++) {
						hand[i][0] = game[0][i * 2];
						hand[i][1] = game[0][i * 2 + 1];
						hand[i][2] = game[1][0];
						hand[i][3] = game[1][1];
						hand[i][4] = game[1][2];
						hand[i][5] = game[2][0];
						hand[i][6] = game[3][0];
					}
				}

				double minBet = 0;
				double tempMinBet = 1;
				// playGame
				while (tempMinBet != minBet) {
					minBet = tempMinBet;
					for (int i = 0; i < 8; i++) {
						System.out.println();
						if (i + (gameCount % 8) < 8)
							System.out.println("Player " + (int) ((i + (gameCount % 8))));
						else
							System.out.println("Player " + (int) ((int) ((i + (gameCount % 8)) - 8)));
						System.out.println();
						for (int j = 0; j < hand[i].length; j++) {
							System.out.print(hand[i][j][0] + "_" + hand[i][j][1] + ", ");
						}
						System.out.println();
						if (playersInHand[i] == 0 && bankRole[i] > 0) {
							// get data
							double[] inData = new double[5 * 8];
							for (int j = 0; j < 8; j++) {
								if (j - i >= 0) {
									inData[j * 5] = vpipnum[j - i] / gameCount;
									inData[j * 5 + 1] = pfrnum[j - i] / gameCount;
									inData[j * 5 + 2] = aggnum[j - i] / gameCount;
									inData[j * 5 + 3] = (bankRole[j - i] - 100) / gameCount;
									if (j == 0) {
										inData[j * 5 + 4] = getOdds(hand[j]);
									} else {
										int[][] allHand = new int[hand[0].length - 2][];
										if (allHand.length == 0) {
											inData[j * 5 + 4] = 0;
										} else {
											for (int k = 0; k < allHand.length; k++) {
												allHand[k] = hand[j][k + 2];
											}
											inData[j * 5 + 4] = getOdds(allHand);
										}
									}

								} else {
									inData[j * 5] = vpipnum[8 + (j - i)] / gameCount;
									inData[j * 5 + 1] = pfrnum[8 + (j - i)] / gameCount;
									inData[j * 5 + 2] = aggnum[8 + (j - i)] / gameCount;
									inData[j * 5 + 3] = (bankRole[8 + (j - i)] - 100) / gameCount;
									if (j == 0) {
										inData[j * 5 + 4] = getOdds(hand[8 + (j - i)]);
									} else {
										int[][] allHand = new int[hand[0].length - 2][];
										if (allHand.length == 0) {
											inData[j * 5 + 4] = 0;
										} else {
											for (int k = 0; k < allHand.length; k++) {
												allHand[k] = hand[8 + (j - i)][k + 2];
											}
											inData[j * 5 + 4] = getOdds(allHand);
										}
									}
								}
							}

							// make moves

							double[] move = new NeuralNetwork(players[i], inData).fire();

							if (move[0] > move[1] && move[0] > move[2] && move[0] > move[3] && move[0] > move[4]
									&& move[0] > move[5]) {
								System.out.println();
								System.out.println("fold");
								playersInHand[i] = 1;
								inHand--;
								if (inHand == 1) {
									int wIter = 0;
									for (int w : playersInHand) {
										if (w == 0) {
											if (wIter + (gameCount % 8) < 8)
												System.out.println("Player " + (int) ((wIter + (gameCount % 8))) + " wins " + pot + " with id " + players[wIter][0][0][0]);
											else
												System.out.println("Player " + (int) ((int) ((wIter + (gameCount % 8)) - 8)) + " wins " + pot + " with id " + players[wIter][0][0][0]);
											bankRole[wIter] += pot;
										}
										wIter++;
									}
									minBet = tempMinBet;
									break loop;
								}
							} else if (move[1] > move[0] && move[1] > move[2] && move[1] > move[3] && move[1] > move[4]
									&& move[1] > move[5]) {
								System.out.println();
								System.out.println("call");
								if (bankRole[i] > tempMinBet) {
									bankRole[i] -= tempMinBet;
									pot += tempMinBet;
								} else {
									bankRole[i] = 0;
									pot += tempMinBet;
								}
								if (round == 0) {
									vpipnum[i]++;
								} else {
									aggnum[i]++;
								}
							} else if (move[2] > move[0] && move[2] > move[1] && move[2] > move[3] && move[2] > move[4]
									&& move[2] > move[5]) {
								System.out.println();
								System.out.println("bet small");
								if (bankRole[i] > tempMinBet * 1.3) {
									tempMinBet *= 1.3;
									bankRole[i] -= tempMinBet;
									pot += tempMinBet;
								} else {
									bankRole[i] = 0;
									pot += tempMinBet;
								}
								if (round == 0) {
									pfrnum[i]++;
								} else {
									aggnum[i]++;
								}
							} else if (move[3] > move[0] && move[3] > move[1] && move[3] > move[2] && move[3] > move[4]
									&& move[3] > move[5]) {
								System.out.println();
								System.out.println("bet med");
								if (bankRole[i] > tempMinBet * 1.7) {
									tempMinBet *= 1.7;
									bankRole[i] -= tempMinBet;
									pot += tempMinBet;
								} else {
									bankRole[i] = 0;
									pot += tempMinBet;
								}
								if (round == 0) {
									pfrnum[i]++;
								} else {
									aggnum[i]++;
								}
							} else if (move[4] > move[0] && move[4] > move[1] && move[4] > move[2] && move[4] > move[3]
									&& move[4] > move[5]) {
								System.out.println();
								System.out.println("bet big");
								if (bankRole[i] > tempMinBet * 2.3) {
									tempMinBet *= 2.3;
									bankRole[i] -= tempMinBet;
									pot += tempMinBet;
								} else {
									bankRole[i] = 0;
									pot += tempMinBet;
								}
								if (round == 0) {
									pfrnum[i]++;
								} else {
									aggnum[i]++;
								}
							} else {
								System.out.println();
								System.out.println("all in");
								if (tempMinBet < bankRole[i]) {
									tempMinBet = bankRole[i];
								}
								if (bankRole[i] > tempMinBet) {
									bankRole[i] -= tempMinBet;
									pot += tempMinBet;
								} else {
									bankRole[i] = 0;
									pot += tempMinBet;
								}
								if (round == 0) {
									pfrnum[i]++;
								} else {
									aggnum[i]++;
								}
							}
						}
					}
				}
			}
			int[][][][] winnerGame = new int[8][][][];
			for (int k = 0; k < 8; k++) {
				winnerGame[k] = new int[][][] { new int[][] { game[0][k * 2], game[0][k * 2 + 1] },
						new int[][] { game[1][0], game[1][1], game[1][2] }, new int[][] { game[2][0] },
						new int[][] { game[3][0] } };
			}
			ArrayList<Integer> winners = winner(winnerGame, playersInHand);
			for (int w : winners) {
				if (w + (gameCount % 8) < 8)
					System.out.println("Player " + (int) ((w + (gameCount % 8))) + " wins " + pot / winners.size() + " with id " + players[w][0][0][0]);
				else
					System.out.println("Player " + (int) ((int) ((w + (gameCount % 8)) - 8)) + " wins " + pot / winners.size() + " with id " + players[w][0][0][0]);
				bankRole[w] += pot / winners.size();
			}
			for (int i = 0; i < 8; i++) {
				if (bankRole[i] <= 0 && playersInGame[i] == 0) {
					playersInGame[i] = 1;
					inGame--;
				}
			}
		}

		int winnerPos = 0;
		double winnerStack = 0;
		for (int i = 0; i < 8; i++) {
			System.out.println("Player " + i + " bank Role = " + bankRole[i]);
			if (bankRole[i] > winnerStack) {
				winnerStack = bankRole[i];
				winnerPos = i;
			}
		}
		if (winnerPos + (gameCount % 8) < 8)
			return (int) (winnerPos + (gameCount % 8));
		else
			return (int) ((winnerPos + (gameCount % 8)) - 8);
	}

}
