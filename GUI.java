import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Scanner;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class GUI extends JFrame implements ActionListener {
	JFrame frame = new JFrame("Poker Helper");
	JPanel flopCards = new JPanel();
	JPanel suetCards = new JPanel();
	JPanel callBet = new JPanel();
	JTextField input = new JTextField("Player Number,Pot Amount;Player Number,Pot Amount");
	int tempCardNum = 0;
	int tempCardSuet = 0;
	int[][] hand = new int[7][2];
	int numCardsInHand = 0;
	JButton[] addCards = new JButton[8];
	JButton[] callBetBtns = new JButton[17];

	double[] vpipnum = new double[8];
	double[] pfrnum = new double[8];
	double[] aggnum = new double[8];
	double[] bankRole = new double[8];
	double[][][] brain = null;
	double gameCount = 1;

	public void gui(double[][][] brain, double[] bankRole) {
		this.bankRole = bankRole;
		this.brain = brain;
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(1000, 600);
//		frame.setLayout(new FlowLayout());
		JPanel titlePanel = new JPanel();
		JLabel title = new JLabel("Add Pocket Cards");
		titlePanel.add(title);
		frame.getContentPane().add(titlePanel);
		flopCards.add(new JLabel("Pick First Card"));
		addCards = new JButton[13 + 4];
		addCards[0] = (new JButton("2"));
		addCards[1] = (new JButton("3"));
		addCards[2] = (new JButton("4"));
		addCards[3] = (new JButton("5"));
		addCards[4] = (new JButton("6"));
		addCards[5] = (new JButton("7"));
		addCards[6] = (new JButton("8"));
		addCards[7] = (new JButton("9"));
		addCards[8] = (new JButton("10"));
		addCards[9] = (new JButton("Jack"));
		addCards[10] = (new JButton("Queen"));
		addCards[11] = (new JButton("King"));
		addCards[12] = (new JButton("Ace"));
		addCards[13] = (new JButton("Heart"));
		addCards[14] = (new JButton("Spade"));
		addCards[15] = (new JButton("Diamond"));
		addCards[16] = (new JButton("Club"));

		callBetBtns[0] = new JButton("Player 1 Called Bet");
		callBetBtns[1] = new JButton("Player 1 Bet or Raised");
		callBetBtns[2] = new JButton("Player 2 Called Bet");
		callBetBtns[3] = new JButton("Player 2 Bet or Raised");
		callBetBtns[4] = new JButton("Player 3 Called Bet");
		callBetBtns[5] = new JButton("Player 3 Bet or Raised");
		callBetBtns[6] = new JButton("Player 4 Called Bet");
		callBetBtns[7] = new JButton("Player 4 Bet or Raised");
		callBetBtns[8] = new JButton("Player 5 Called Bet");
		callBetBtns[9] = new JButton("Player 5 Bet or Raised");
		callBetBtns[10] = new JButton("Player 6 Called Bet");
		callBetBtns[11] = new JButton("Player 6 Bet or Raised");
		callBetBtns[12] = new JButton("Player 7 Called Bet");
		callBetBtns[13] = new JButton("Player 7 Bet or Raised");
		callBetBtns[14] = new JButton("Fin Hand");
		callBetBtns[15] = new JButton("Make a Good Move");
		callBetBtns[16] = new JButton("Called Back To You");

		for (int i = 0; i < 17; i++) {
			if (i < 17) {
				callBetBtns[i].addActionListener(this);
				callBet.add(callBetBtns[i]);
			}
			addCards[i].addActionListener(this);
			if (i < 13)
				flopCards.add(addCards[i]);
			else
				suetCards.add(addCards[i]);
		}
		frame.getContentPane().add(flopCards);
		frame.setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String cardNum = e.getActionCommand();
		System.out.println(cardNum);
		if (cardNum.equals("Ace")) {
			tempCardNum = 12;
			frame.remove(flopCards);
			frame.getContentPane().add(suetCards);
			frame.setVisible(true);
		} else if (cardNum.equals("King")) {
			tempCardNum = 11;
			frame.remove(flopCards);
			frame.getContentPane().add(suetCards);
			frame.setVisible(true);
		} else if (cardNum.equals("Queen")) {
			tempCardNum = 10;
			frame.remove(flopCards);
			frame.getContentPane().add(suetCards);
			frame.setVisible(true);
		} else if (cardNum.equals("Jack")) {
			tempCardNum = 9;
			frame.remove(flopCards);
			frame.getContentPane().add(suetCards);
			frame.setVisible(true);
		} else if (cardNum.contains("Call") && numCardsInHand == 2 && !cardNum.equals("Called Back To You")) {
			int player = 0;
			if (!cardNum.contains("you")) {
				Scanner sc = new Scanner(cardNum);
				while (sc.hasNext()) {
					String next = sc.next();
					try {
						player = Integer.parseInt(next);
					} catch (Exception ex) {

					}
				}
			}
			vpipnum[player]++;

			frame.remove(callBet);
			flopCards = new JPanel();
			suetCards = new JPanel();
			callBet = new JPanel();
			for (int i = 0; i < numCardsInHand; i++) {
				callBet.add(new JLabel("    " + hand[i][0] + "_" + hand[i][1] + "  "));
			}
			for (int i = 0; i < 17; i++) {
				if (i < 17)
					callBet.add(callBetBtns[i]);
				if (i < 13) {
					flopCards.add(addCards[i]);
				} else {
					suetCards.add(addCards[i]);
				}
			}
			for (int i = 0; i < numCardsInHand; i++) {
				flopCards.add(new JLabel("    " + hand[i][0] + "_" + hand[i][1] + "  "));
			}
			frame.getContentPane().add(callBet);
			frame.setVisible(true);
		} else if (cardNum.contains("Bet") && numCardsInHand == 2) {
			int player = 0;
			if (!cardNum.contains("you")) {
				Scanner sc = new Scanner(cardNum);
				while (sc.hasNext()) {
					String next = sc.next();
					try {
						player = Integer.parseInt(next);
					} catch (Exception ex) {

					}
				}
			}

			pfrnum[player]++;

			frame.remove(callBet);
			flopCards = new JPanel();
			suetCards = new JPanel();
			callBet = new JPanel();
			for (int i = 0; i < numCardsInHand; i++) {
				callBet.add(new JLabel("    " + hand[i][0] + "_" + hand[i][1] + "  "));
			}
			for (int i = 0; i < 17; i++) {
				if (i < 17)
					callBet.add(callBetBtns[i]);
				if (i < 13) {
					flopCards.add(addCards[i]);
				} else {
					suetCards.add(addCards[i]);
				}
			}
			for (int i = 0; i < numCardsInHand; i++) {
				flopCards.add(new JLabel("    " + hand[i][0] + "_" + hand[i][1] + "  "));
			}
			frame.getContentPane().add(callBet);
			frame.setVisible(true);
		} else if (cardNum.contains("Bet") && numCardsInHand > 2) {
			int player = 0;
			if (!cardNum.contains("you")) {
				Scanner sc = new Scanner(cardNum);
				while (sc.hasNext()) {
					String next = sc.next();
					try {
						player = Integer.parseInt(next);
					} catch (Exception ex) {

					}
				}
			}
			aggnum[player]++;

			frame.remove(callBet);
			flopCards = new JPanel();
			suetCards = new JPanel();
			callBet = new JPanel();
			for (int i = 0; i < numCardsInHand; i++) {
				callBet.add(new JLabel("    " + hand[i][0] + "_" + hand[i][1] + "  "));
			}
			for (int i = 0; i < 17; i++) {
				if (i < 17)
					callBet.add(callBetBtns[i]);
				if (i < 13) {
					flopCards.add(addCards[i]);
				} else {
					suetCards.add(addCards[i]);
				}
			}
			for (int i = 0; i < numCardsInHand; i++) {
				flopCards.add(new JLabel("    " + hand[i][0] + "_" + hand[i][1] + "  "));
			}
			frame.getContentPane().add(callBet);
			frame.setVisible(true);
		} else if (cardNum.contains("Make a Good Move")) {
			double[] inData = new double[8 * 5];
			for (int i = 0; i < 8; i++) {
				inData[i * 5] = vpipnum[i] / gameCount;
				inData[i * 5 + 1] = pfrnum[i] / gameCount;
				inData[i * 5 + 2] = aggnum[i] / gameCount;
				inData[i * 5 + 3] = (bankRole[i] - 100) / gameCount;
				if (i == 0) {
					int[][] tempHand = new int[numCardsInHand][];
					for (int k = 0; k < tempHand.length; k++) {
						tempHand[k] = hand[k];
					}
					inData[i * 5 + 4] = new Data().getOdds(tempHand);
				} else {
					int[][] allHand = new int[numCardsInHand - 2][];
					if (allHand.length <= 0) {
						inData[i * 5 + 4] = 0;
					} else {
						for (int k = 0; k < allHand.length; k++) {
							allHand[k] = hand[k + 2];
						}
						inData[i * 5 + 4] = new Data().getOdds(allHand);
					}
				}
			}
			for (double dat : inData) {
				System.out.println(dat);
			}
			double[] move = new NeuralNetwork(brain, inData).fire();
			System.out.println(
					move[0] + "   " + move[1] + "   " + move[2] + "   " + move[3] + "   " + move[4] + "   " + move[5]);
			String out = "";
			if (move[0] > move[1] && move[0] > move[2] && move[0] > move[3] && move[0] > move[4] && move[0] > move[5]) {
				out = "fold";
			} else if (move[1] > move[0] && move[1] > move[2] && move[1] > move[3] && move[1] > move[4]
					&& move[1] > move[5]) {
				out = "Bet/Call Pot";
			} else if (move[2] > move[0] && move[2] > move[1] && move[2] > move[3] && move[2] > move[4]
					&& move[2] > move[5]) {
				out = "Bet the Pot * 1.3 or go all in";
			} else if (move[3] > move[0] && move[3] > move[1] && move[3] > move[2] && move[3] > move[4]
					&& move[3] > move[5]) {
				out = "Bet the Pot * 1.7 or go all in";
			} else if (move[4] > move[0] && move[4] > move[1] && move[4] > move[2] && move[4] > move[3]
					&& move[4] > move[5]) {
				out = "Bet the Pot * 2.3 or go all in";
			} else if (move[5] > move[0] && move[5] > move[1] && move[5] > move[2] && move[5] > move[3]
					&& move[5] > move[4]) {
				out = "Go all in";
			}

			frame.remove(callBet);
			flopCards = new JPanel();
			suetCards = new JPanel();
			callBet = new JPanel();
			for (int i = 0; i < 17; i++) {
				if (i < 17)
					callBet.add(callBetBtns[i]);
				if (i < 13) {
					flopCards.add(addCards[i]);
				} else {
					suetCards.add(addCards[i]);
				}
			}
			callBet.add(new JLabel("     " + out + "       "));
			for (int i = 0; i < numCardsInHand; i++) {
				flopCards.add(new JLabel("    " + hand[i][0] + "_" + hand[i][1] + "  "));
			}
			frame.getContentPane().add(callBet);
			frame.setVisible(true);
		} else if (cardNum.contains("Submit")) {
			String[] bankChange = input.getText().split(";");
			for (String change : bankChange) {
				String[] data = change.split(",");
				bankRole[Integer.parseInt(data[0])] += Double.parseDouble(data[1]);
			}
			hand = new int[7][2];
			gameCount++;
			numCardsInHand = 0;
			frame.remove(callBet);
			flopCards = new JPanel();
			suetCards = new JPanel();
			callBet = new JPanel();
			for (int i = 0; i < 17; i++) {
				if (i < 17)
					callBet.add(callBetBtns[i]);
				if (i < 13) {
					flopCards.add(addCards[i]);
				} else {
					suetCards.add(addCards[i]);
				}
			}
			for (int i = 0; i < numCardsInHand; i++) {
				flopCards.add(new JLabel("    " + hand[i][0] + "_" + hand[i][1] + "  "));
			}
			frame.getContentPane().add(flopCards);
			frame.setVisible(true);
		} else if (cardNum.equals("Called Back To You")) {
			if (numCardsInHand < 7) {
				frame.remove(callBet);
				flopCards = new JPanel();
				suetCards = new JPanel();
				callBet = new JPanel();
				for (int i = 0; i < 17; i++) {
					if (i < 17)
						callBet.add(callBetBtns[i]);
					if (i < 13) {
						flopCards.add(addCards[i]);
					} else {
						suetCards.add(addCards[i]);
					}
				}
				for (int i = 0; i < numCardsInHand; i++) {
					flopCards.add(new JLabel("    " + hand[i][0] + "_" + hand[i][1] + "  "));
				}
				frame.getContentPane().add(flopCards);
				frame.setVisible(true);
			} else {
				frame.remove(callBet);
				flopCards = new JPanel();
				suetCards = new JPanel();
				callBet = new JPanel();
				callBet.add(input);
				JButton potWinner = new JButton("Submit");
				potWinner.addActionListener(this);
				callBet.add(potWinner);
				frame.getContentPane().add(callBet);
				frame.setVisible(true);
			}
		}else if (cardNum.equals("Fin Hand")) {
			frame.remove(callBet);
			flopCards = new JPanel();
			suetCards = new JPanel();
			callBet = new JPanel();
			callBet.add(input);
			JButton potWinner = new JButton("Submit");
			potWinner.addActionListener(this);
			callBet.add(potWinner);
			frame.getContentPane().add(callBet);
			frame.setVisible(true);
		} else {
			try {
				tempCardNum = Integer.parseInt(cardNum) - 2;
				frame.remove(flopCards);
				frame.getContentPane().add(suetCards);
				frame.setVisible(true);
			} catch (Exception ex) {
				if (cardNum.equals("Heart")) {
					hand[numCardsInHand][0] = tempCardNum;
					hand[numCardsInHand][1] = 0;
				} else if (cardNum.equals("Spade")) {
					hand[numCardsInHand][0] = tempCardNum;
					hand[numCardsInHand][1] = 1;
				} else if (cardNum.equals("Diamond")) {
					hand[numCardsInHand][0] = tempCardNum;
					hand[numCardsInHand][1] = 2;
				} else if (cardNum.equals("Club")) {
					hand[numCardsInHand][0] = tempCardNum;
					hand[numCardsInHand][1] = 3;
				}

				frame.remove(suetCards);
				flopCards = new JPanel();
				suetCards = new JPanel();
				callBet = new JPanel();
				for (int i = 0; i < numCardsInHand + 1; i++) {
					callBet.add(new JLabel("    " + hand[i][0] + "_" + hand[i][1] + "  "));
				}
				for (int i = 0; i < 17; i++) {
					if (i < 17)
						callBet.add(callBetBtns[i]);
					if (i < 13) {
						flopCards.add(addCards[i]);
					} else {
						suetCards.add(addCards[i]);
					}
				}
				for (int i = 0; i < numCardsInHand + 1; i++) {
					flopCards.add(new JLabel("    " + hand[i][0] + "_" + hand[i][1] + "  "));
				}
				numCardsInHand++;

				if (numCardsInHand == 2 || numCardsInHand == 5 || numCardsInHand == 6 || numCardsInHand == 7) {
					frame.getContentPane().add(callBet);
					frame.setVisible(true);
				} else {
					frame.getContentPane().add(flopCards);
					frame.setVisible(true);
				}

			}
		}
	}

}
