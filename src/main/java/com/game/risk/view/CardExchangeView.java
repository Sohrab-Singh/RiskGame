package com.game.risk.view;

import java.awt.Color;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Observable;
import java.util.Observer;
import java.util.Random;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;

import com.game.risk.PhaseObservable;
import com.game.risk.core.util.CardUtil;
import com.game.risk.core.util.PhaseStates;
import com.game.risk.model.Card;
import com.game.risk.model.Player;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import java.awt.Font;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

/**
 * Observer Class to update the Cards and exchange them for Armies
 * 
 * @author Sarthak
 *
 */
public class CardExchangeView extends JFrame implements Observer {

	/**
	 * Serial Version UID
	 */
	private static final long serialVersionUID = 1L;

	private JPanel contentPane;
	private JPanel cardsViewPanel;
	private JButton btnExchange;
	private JLabel labelNoCards;
	private Player currentPlayer;
	private HashMap<Player, List<Card>> cardHashMap;

	public CardExchangeView() {
		initializeView();
		cardHashMap = new HashMap<>();
	}

	private void initializeView() {
		setBackground(Color.WHITE);
		setUndecorated(true);
		setBounds(960, 590, 700, 300);

		// Initialize JPanel contentPane to hold the JLabel and JButton elements
		contentPane = new JPanel();
		contentPane.setBackground(Color.BLACK);
		contentPane.setBorder(new EmptyBorder(20, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		cardsViewPanel = new JPanel();
		cardsViewPanel.setBackground(Color.BLACK);
		cardsViewPanel.setBounds(12, 111, 640, 136);
		contentPane.add(cardsViewPanel);

		labelNoCards = new JLabel("< No Cards >");
		cardsViewPanel.add(labelNoCards);
		labelNoCards.setFont(new Font("Tahoma", Font.BOLD, 18));
		labelNoCards.setForeground(Color.WHITE);

		btnExchange = new JButton("Exchange");
		btnExchange.setFont(new Font("Segoe UI", Font.BOLD, 15));
		btnExchange.setBounds(529, 252, 159, 35);
		contentPane.add(btnExchange);

		JLabel lblInventory = new JLabel("I : Inventory");
		lblInventory.setHorizontalAlignment(SwingConstants.CENTER);
		lblInventory.setFont(new Font("Segoe UI", Font.BOLD, 18));
		lblInventory.setForeground(Color.WHITE);
		lblInventory.setBounds(12, 13, 130, 35);
		contentPane.add(lblInventory);

		JLabel lblCavalry = new JLabel("C : Cavalry");
		lblCavalry.setHorizontalAlignment(SwingConstants.CENTER);
		lblCavalry.setFont(new Font("Segoe UI", Font.BOLD, 18));
		lblCavalry.setForeground(Color.WHITE);
		lblCavalry.setBounds(174, 16, 148, 28);
		contentPane.add(lblCavalry);

		JLabel lblArtillary = new JLabel("A : Artillery");
		lblArtillary.setHorizontalAlignment(SwingConstants.CENTER);
		lblArtillary.setForeground(Color.WHITE);
		lblArtillary.setFont(new Font("Segoe UI", Font.BOLD, 18));
		lblArtillary.setBounds(367, 16, 148, 28);
		contentPane.add(lblArtillary);
	}

	@Override
	public void update(Observable arg0, Object arg1) {
		if (arg1 != null) {
			if (arg1.getClass() == Integer.class) {
				if (((int) arg1) == PhaseStates.STATE_UPDATE_CARD)
					addCard((PhaseObservable) arg0);
			}
		}
	}

	private void addCard(PhaseObservable observable) {
		currentPlayer = observable.getPlayer();
		Random random = new Random();
		int randomCard = random.nextInt(3);
		if (cardHashMap.containsKey(currentPlayer))
			cardHashMap.get(currentPlayer).add(new Card(randomCard));
		else {
			List<Card> cardList = new ArrayList<>();
			cardList.add(new Card(randomCard));
			cardHashMap.put(currentPlayer, cardList);
		}
		updateCardListView();
	}

	private void updateCardListView() {
		labelNoCards.setVisible(false);
		JPanel[] jpanels = new JPanel[cardHashMap.get(currentPlayer).size()];
		cardsViewPanel.removeAll();
		Border border = BorderFactory.createLineBorder(Color.WHITE, 4, true);
		for (int i = 0; i < cardHashMap.get(currentPlayer).size(); i++) {
			JLabel jlabel = new JLabel();
			if (cardHashMap.get(currentPlayer).get(i).getValue() == CardUtil.CARD_INFANTY)
				jlabel.setText("I");
			else if (cardHashMap.get(currentPlayer).get(i).getValue() == CardUtil.CARD_CAVALRY)
				jlabel.setText("C");
			else
				jlabel.setText("A");

			jlabel.setBorder(BorderFactory.createCompoundBorder(border, new EmptyBorder(5, 25, 5, 25)));
			jpanels[i] = new JPanel();
			jpanels[i].add(jlabel);
			cardsViewPanel.add(jpanels[i]);
		}
		cardsViewPanel.validate();
		cardsViewPanel.repaint();
	}

}
