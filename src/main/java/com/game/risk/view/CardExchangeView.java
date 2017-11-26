package com.game.risk.view;

import java.awt.Color;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;

import com.game.risk.model.CardType;
import com.game.risk.model.Player;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JLabel;
import javax.swing.SwingConstants;

/**
 * Observer Class to update the Cards and exchange them for Armies.
 *
 * @author Sarthak
 * @author sohrab_singh
 */
public class CardExchangeView extends JFrame implements Observer {

	/** Serial Version UID. */
	private static final long serialVersionUID = 1L;

	/** The content pane. */
	private JPanel contentPane;

	/** The cards view panel. */
	private JPanel cardsViewPanel;

	/** The btn exchange. */
	private JButton btnExchange;

	/** The label no cards. */
	private JLabel labelNoCards;

	/** The current player. */
	private Player currentPlayer;

	/**
	 * Instantiates a new card exchange view.
	 */
	public CardExchangeView() {
		initializeView();
	}

	/**
	 * Initialize view.
	 */
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
		btnExchange.setVisible(false);
		btnExchange.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				currentPlayer.exchangeCardsWithArmies();
				currentPlayer.removeCardsFromDeck();
			}
		});

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

		if (arg1.getClass().equals(Boolean.class)) {
			if ((boolean) arg1) {
				btnExchange.setVisible(true);
			} else {
				btnExchange.setVisible(false);
			}
			updateCardListView();
		}
	}

	/**
	 * Update card list view.
	 */
	private void updateCardListView() {
		labelNoCards.setVisible(false);
		JPanel[] jpanels = new JPanel[currentPlayer.getCardList().size()];
		cardsViewPanel.removeAll();
		Border border = BorderFactory.createLineBorder(Color.WHITE, 4, true);
		for (int i = 0; i < currentPlayer.getCardList().size(); i++) {
			JLabel jlabel = new JLabel();
			if (currentPlayer.getCardList().get(i).equals(CardType.Infantry))
				jlabel.setText("I");
			else if (currentPlayer.getCardList().get(i).equals(CardType.Cavalry))
				jlabel.setText("C");
			else
				jlabel.setText("A");

			jlabel.setBorder(BorderFactory.createCompoundBorder(border, new EmptyBorder(20, 40, 20, 40)));
			jpanels[i] = new JPanel();
			jpanels[i].setBackground(Color.BLACK);
			jpanels[i].add(jlabel);
			cardsViewPanel.add(jpanels[i]);
		}
		cardsViewPanel.validate();
		cardsViewPanel.repaint();
	}

}
