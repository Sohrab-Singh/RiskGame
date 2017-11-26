package com.game.risk.view;

import java.awt.Color;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileNameExtensionFilter;

import com.game.risk.RiskGamePhases;
import com.game.risk.core.MapFileReader;
import com.game.risk.core.util.LoggingUtil;
import com.game.risk.core.util.PhaseStates;
import com.game.risk.model.Country;
import com.game.risk.model.Player;

import javax.swing.JLabel;
import java.awt.Font;

import javax.swing.SwingConstants;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFileChooser;

import java.awt.FlowLayout;

/**
 * (Observer) View for the user to view represent each Phase run time.
 *
 * @author Sarthak
 * @author sohrab_singh
 */
public class GamePhaseView extends JFrame implements Observer, MouseListener {

	/** Serial Version UID. */
	private static final long serialVersionUID = 1L;

	/** JPanel object. */
	private JPanel contentPane;

	/**
	 * MapFileReader object to read the stored data into Countries Graph Data
	 * Structure.
	 */
	private MapFileReader fileParser;

	/**
	 * HashMap to store the JLabel identifying the country name and its position in
	 * the JLabel Array.
	 */
	private HashMap<JPanel, Integer> panelHashMap;

	/**
	 * HashMap to store the JLabel indentify adjacent country and its position in
	 * the adjacent JLabel Array.
	 */
	private HashMap<JPanel, Integer> adjacentPanelHashMap;

	/** JPanel to contain the adjacent opponent countries;. */
	private JPanel panel_2;

	/** JPanel to contain the countries owned text label. */
	private JPanel panel_3;

	/** JPanel to contain the countries adjacent other than self owned. */
	private JPanel panel_4;

	/** JPanel to contain the player owned countries JLabel Array. */
	private JPanel panel_5;

	/** JPanel to contain the adjacent countries JLabel Array. */
	private JPanel panel_6;

	/** JLabel to show the Current Phase. */
	private JLabel lblCurrentPhase;

	/** Country Object to maintain the attacking country. */
	private Country attackingCountry;

	/** Country object to maintain the defending country. */
	private Country defendingCountry;

	/** Current Selection of the player owned country. */
	private int currentOwnedCountry;

	/** Current Selection of the adjacent country. */
	private int currentAdjacentCountry;

	/**
	 * Country object from where the fortification begins with.
	 */
	private Country startCountry;

	/**
	 * Country object from where the fortification ends.
	 */
	private Country moveCountry;

	/** JLabel Array object to store the player owned country labels. */
	private JPanel jpanels[];

	/** JButton object to perform attack phase init. */
	private JButton btnAttack;

	/** JButton object to perform fortification phase init. */
	private JButton btnFortify;

	/** JButton object to save the game */
	private JButton btnSave;

	/** JButton object to end the player's turn. */
	private JButton btnEndTurn;

	/**
	 * List to store the Adjacent Countries jlabels with player owned selected
	 * country.
	 */
	private List<JPanel> adjPanels;

	/** Current Player playing the game. */
	private Player currentPlayer;

	/** The lbl armies count. */
	private JLabel lblArmiesCount;

	/** The lbl player. */
	private JLabel lblPlayer;

	/** The current state. */
	private int currentState;

	/** File Output Stream to write the protobuf generated data structure to the file */
	private FileOutputStream output;

	/** Observable class for the Game Phase View Observer */
	private RiskGamePhases gamePhases;

	/**
	 * GamePhaseView Constructor.
	 * 
	 * @param gamePhases
	 *
	 * @param reader
	 *            MapFileReader object
	 * @param players
	 *            the players
	 */
	public GamePhaseView(RiskGamePhases gamePhases, MapFileReader reader) {
		this.fileParser = reader;
		initializeView();
		panelHashMap = new HashMap<>();
		adjacentPanelHashMap = new HashMap<>();
		this.gamePhases = gamePhases;

	}

	/**
	 * Method to initialize the layout for GamePhaseView JFrame class.
	 */
	private void initializeView() {
		setBackground(Color.WHITE);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		setBounds(100, 100, 850, 781);

		// Initialize JPanel contentPane to hold the JLabel and JButton elements
		contentPane = new JPanel();
		contentPane.setBackground(Color.WHITE);
		contentPane.setBorder(new EmptyBorder(20, 5, 5, 5));
		contentPane.setLayout(null);
		setContentPane(contentPane);

		JPanel panelMapDetails = new JPanel();
		panelMapDetails.setBounds(0, 33, 832, 444);
		contentPane.add(panelMapDetails);
		panelMapDetails.setLayout(null);

		JPanel panel_1 = new JPanel();
		panel_1.setBorder(new EmptyBorder(10, 10, 10, 10));
		panel_1.setBounds(0, 0, 833, 248);
		panelMapDetails.add(panel_1);
		panel_1.setLayout(null);

		panel_3 = new JPanel();
		panel_3.setBackground(Color.BLACK);
		panel_3.setBounds(0, 13, 162, 26);
		panel_1.add(panel_3);
		panel_3.setLayout(null);

		JLabel lblCountriesOwned = new JLabel("Countries Owned");
		lblCountriesOwned.setBounds(12, 0, 106, 26);
		panel_3.add(lblCountriesOwned);
		lblCountriesOwned.setBackground(Color.WHITE);
		lblCountriesOwned.setForeground(Color.WHITE);

		panel_5 = new JPanel();
		panel_5.setBorder(new EmptyBorder(5, 10, 10, 10));
		panel_5.setBounds(0, 52, 833, 245);
		panel_1.add(panel_5);
		panel_5.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));

		panel_2 = new JPanel();
		panel_2.setBackground(Color.LIGHT_GRAY);
		panel_2.setBounds(0, 297, 833, 147);
		panelMapDetails.add(panel_2);
		panel_2.setLayout(null);

		panel_4 = new JPanel();
		panel_4.setBackground(Color.BLACK);
		panel_4.setBounds(0, 13, 168, 27);
		panel_2.add(panel_4);
		panel_4.setLayout(null);

		JLabel lblCountriesAdjacent = new JLabel("Countries Adjacent");
		lblCountriesAdjacent.setBounds(12, 0, 108, 27);
		lblCountriesAdjacent.setForeground(Color.WHITE);
		panel_4.add(lblCountriesAdjacent);

		panel_6 = new JPanel();
		panel_6.setBorder(new EmptyBorder(5, 10, 10, 10));
		panel_6.setBackground(Color.LIGHT_GRAY);
		panel_6.setBounds(0, 39, 833, 109);
		panel_2.add(panel_6);

		JPanel panel = new JPanel();
		panel.setBackground(Color.BLACK);
		panel.setBounds(0, 477, 832, 126);
		contentPane.add(panel);

		lblArmiesCount = new JLabel();
		lblArmiesCount.setBounds(12, 13, 66, 52);
		lblArmiesCount.setForeground(Color.WHITE);
		lblArmiesCount.setHorizontalAlignment(SwingConstants.CENTER);
		lblArmiesCount.setFont(new Font("Tahoma", Font.BOLD, 40));

		JLabel lblArmies = new JLabel("armies");
		lblArmies.setForeground(Color.WHITE);
		lblArmies.setBounds(32, 63, 46, 20);
		panel.setLayout(null);
		panel.add(lblArmiesCount);
		panel.add(lblArmies);

		lblPlayer = new JLabel();
		lblPlayer.setFont(new Font("Tahoma", Font.BOLD, 24));
		lblPlayer.setForeground(Color.WHITE);
		lblPlayer.setBounds(106, 13, 142, 30);
		panel.add(lblPlayer);

		btnAttack = new JButton("Attack");
		btnAttack.setFont(new Font("Tahoma", Font.BOLD, 14));
		btnAttack.setBounds(356, 13, 97, 25);
		btnAttack.addMouseListener(this);
		panel.add(btnAttack);

		btnFortify = new JButton("Fortify");
		btnFortify.setFont(new Font("Tahoma", Font.BOLD, 14));
		btnFortify.setBounds(476, 13, 97, 25);
		btnFortify.addMouseListener(this);
		panel.add(btnFortify);

		btnEndTurn = new JButton("End Turn");
		btnEndTurn.setFont(new Font("Tahoma", Font.BOLD, 14));
		btnEndTurn.setBounds(598, 13, 106, 25);
		btnEndTurn.addMouseListener(this);
		panel.add(btnEndTurn);

		btnSave = new JButton("Save");
		btnSave.addMouseListener(this);
		btnSave.setFont(new Font("Tahoma", Font.BOLD, 15));
		btnSave.setBounds(598, 83, 106, 30);
		panel.add(btnSave);

		lblCurrentPhase = new JLabel("Reinforcement");
		lblCurrentPhase.setBounds(0, 0, 832, 34);
		contentPane.add(lblCurrentPhase);
		lblCurrentPhase.setHorizontalAlignment(SwingConstants.CENTER);
		lblCurrentPhase.setForeground(Color.BLACK);
		lblCurrentPhase.setFont(new Font("Tahoma", Font.BOLD, 18));

	}

	@Override
	public void update(Observable arg0, Object arg1) {
		setCurrentState(((RiskGamePhases) arg0).getCurrentState());
		if (((RiskGamePhases) arg0).getCurrentState() == PhaseStates.STATE_STARTUP) {
			this.setVisible(true);
			lblCurrentPhase.setText("StartUp Phase");
		} else if (((RiskGamePhases) arg0).getCurrentState() == PhaseStates.STATE_ACTIVE) {
			if (arg1.equals("attack")) {
				btnAttack.setVisible(false);
			}
			lblCurrentPhase.setText("What do you want to perform now?");
		} else if (((RiskGamePhases) arg0).getCurrentState() == PhaseStates.STATE_REINFORCEMENT) {
			lblCurrentPhase.setText("Reinforcement Phase");
		} else if (((RiskGamePhases) arg0).getCurrentState() == PhaseStates.STATE_FORTIFY) {
			lblCurrentPhase.setText("Fortification Phase");
		}
		updatePlayerCountries((RiskGamePhases) arg0);
	}

	/**
	 * Update the player owned countries in the JFrame.
	 *
	 * @param gamePhases
	 *            PhaseObservable type
	 */
	private void updatePlayerCountries(RiskGamePhases gamePhases) {
		currentPlayer = gamePhases.getPlayer();
		List<Country> countries = currentPlayer.getCountriesOwned();
		lblPlayer.setText(currentPlayer.getPlayerName());
		lblArmiesCount.setText(Integer.toString(currentPlayer.getNumberOfArmies()));
		jpanels = new JPanel[countries.size()];
		panel_5.removeAll();
		panel_6.removeAll();
		Border border = BorderFactory.createLineBorder(Color.WHITE, 2, true);
		for (int i = 0; i < jpanels.length; i++) {
			JLabel jlabel = new JLabel(countries.get(i).getCountryName());
			jpanels[i] = new JPanel();
			jlabel.setBorder(BorderFactory.createCompoundBorder(border, new EmptyBorder(5, 5, 5, 5)));
			jpanels[i].setBorder(new EmptyBorder(-2, -2, -1, -1));
			jpanels[i].add(jlabel);
			JPanel armyCount = new JPanel();
			JLabel label = new JLabel(Integer.toString(countries.get(i).getCurrentNumberOfArmies()));
			label.setForeground(Color.WHITE);
			armyCount.setBackground(Color.BLACK);
			armyCount.add(label);
			jpanels[i].add(armyCount);
			jpanels[i].addMouseListener(this);
			panelHashMap.put(jpanels[i], i);
			panel_5.add(jpanels[i]);
		}
		if (gamePhases.getCurrentState() == PhaseStates.STATE_STARTUP
				|| gamePhases.getCurrentState() == PhaseStates.STATE_REINFORCEMENT)
			panel_2.setVisible(false);
		else
			panel_2.setVisible(true);

		panel_5.validate();
		panel_5.repaint();
	}

	/**
	 * Update player adjacent countries.
	 *
	 * @param country
	 *            the country
	 */
	private void updatePlayerAdjacentCountries(Country country) {
		panel_6.removeAll();
		LinkedList<Country> adjCountries = fileParser.getCountriesGraph().getAdjListHashMap().get(country);
		Border border = BorderFactory.createLineBorder(Color.WHITE, 2, true);
		adjPanels = new ArrayList<>();
		int adjCount = 0;
		for (int i = 0; i < adjCountries.size(); i++) {

			// Ignoring the player owned countries and finding the opponnent
			if (!adjCountries.get(i).getPlayerName().equals(currentPlayer.getPlayerName())) {
				JLabel adjCountryName = new JLabel(adjCountries.get(i).getCountryName());
				JPanel adjPanel = new JPanel();
				adjPanel.setBackground(null);
				adjCountryName.setBorder(BorderFactory.createCompoundBorder(border, new EmptyBorder(5, 5, 5, 5)));
				adjPanel.setBorder(new EmptyBorder(-2, -2, -1, -1));
				adjPanel.add(adjCountryName);
				JPanel armyCount = new JPanel();
				JLabel adjArmyCountLabel = new JLabel(Integer.toString(adjCountries.get(i).getCurrentNumberOfArmies()));
				adjArmyCountLabel.setForeground(Color.WHITE);
				armyCount.setBackground(Color.BLACK);
				armyCount.add(adjArmyCountLabel);
				adjPanel.add(armyCount);
				adjPanel.addMouseListener(this);
				adjacentPanelHashMap.put(adjPanel, adjCount);
				adjPanels.add(adjCount++, adjPanel);
				panel_6.add(adjPanel);
			}

		}
		panel_6.validate();
		panel_6.repaint();
	}

	@Override
	public void mouseClicked(MouseEvent e) {

		if (e.getComponent() == btnSave) {
			JFileChooser fileChooser = new JFileChooser();
			FileNameExtensionFilter filter = new FileNameExtensionFilter("Risk Game Save Files", "rgs");
			fileChooser.setFileFilter(filter);

			if (fileChooser.showSaveDialog(getRootPane()) == JFileChooser.APPROVE_OPTION) {

				try {
					output = new FileOutputStream(fileChooser.getSelectedFile());
					
				} catch (IOException e1) {
					e1.printStackTrace();
				}

			}

		} else if (e.getComponent() == btnAttack) {
			if (attackingCountry.getCurrentNumberOfArmies() > defendingCountry.getCurrentNumberOfArmies()
					&& (defendingCountry.getCurrentNumberOfArmies() >= 1)) {
				gamePhases.startAttackPhase(attackingCountry, defendingCountry);
			} else {
				LoggingUtil.logMessage("Not Enough Armies to proceed attack.");
			}

		} else if (e.getComponent() == btnEndTurn) {
			gamePhases.updateCard();
			gamePhases.nextPlayer();
		} else if (e.getComponent() == btnFortify) {
			gamePhases.updateDominationPercentage();
			gamePhases.startFortificationPhase(startCountry, moveCountry);
		} else if (panelHashMap.containsKey((JPanel) e.getComponent())) {
			Integer value = panelHashMap.get((JPanel) e.getComponent());
			if (currentOwnedCountry != -1)
				jpanels[currentOwnedCountry].setBackground(null);
			currentOwnedCountry = value;
			jpanels[currentOwnedCountry].setBackground(Color.WHITE);

			// Find country name from the jlabel object wrapped in selected country jpanel
			String countryName = ((JLabel) jpanels[currentOwnedCountry].getComponent(0)).getText();
			Country country = fileParser.getCountriesHashMap().get(countryName);
			if (currentState == PhaseStates.STATE_STARTUP) {
				country.setCurrentNumberOfArmies(country.getCurrentNumberOfArmies() + 1);
				currentPlayer.setNumberOfArmies(currentPlayer.getNumberOfArmies() - 1);
				gamePhases.moveToNextPlayer();

			} else if (currentState == PhaseStates.STATE_REINFORCEMENT) {
				if (currentPlayer.getNumberOfArmies() > 1) {
					country.setCurrentNumberOfArmies(country.getCurrentNumberOfArmies() + 1);
					currentPlayer.setNumberOfArmies(currentPlayer.getNumberOfArmies() - 1);
					gamePhases.notifyStateChange(PhaseStates.STATE_REINFORCEMENT);
				} else if (currentPlayer.getNumberOfArmies() == 1) {
					country.setCurrentNumberOfArmies(country.getCurrentNumberOfArmies() + 1);
					currentPlayer.setNumberOfArmies(currentPlayer.getNumberOfArmies() - 1);
					gamePhases.notifyStateChange(PhaseStates.STATE_ACTIVE);
				}
			} else if (currentState == PhaseStates.STATE_ACTIVE) {
				updatePlayerAdjacentCountries(fileParser.getCountriesHashMap().get(countryName));
				setAttacker();
				btnAttack.setVisible(false);
			}

		} else if (adjacentPanelHashMap.containsKey((JPanel) e.getComponent())) {
			Integer value = adjacentPanelHashMap.get((JPanel) e.getComponent());
			if (currentAdjacentCountry != -1)
				adjPanels.get(currentAdjacentCountry).setBackground(null);
			currentAdjacentCountry = value;
			adjPanels.get(currentAdjacentCountry).setBackground(Color.WHITE);
			setDefender();
			btnAttack.setVisible(true);
		}
	}

	@Override
	public void mouseEntered(MouseEvent e) {
	}

	@Override
	public void mouseExited(MouseEvent e) {
	}

	@Override
	public void mousePressed(MouseEvent e) {
	}

	@Override
	public void mouseReleased(MouseEvent e) {
	}

	/**
	 * The main method.
	 *
	 * @param args
	 *            the arguments
	 */
	public static void main(String[] args) {
		java.awt.EventQueue.invokeLater(new Runnable() {
			public void run() {
				Player player = new Player();
				player.setPlayerName("Sohrab");
				player.setCurrentDominationPercentage(0.002f);
				Player player1 = new Player();
				player1.setPlayerName("Sarthak");
				player1.setCurrentDominationPercentage(0.5);
				Player player2 = new Player();
				player2.setPlayerName("Manjot");
				player2.setCurrentDominationPercentage(0.3);
				List<Player> playerss = new ArrayList<Player>();
				playerss.add(player);
				playerss.add(player1);
				playerss.add(player2);
			}
		});
	}

	/**
	 * Sets the attacker.
	 */
	public void setAttacker() {
		String countryName = ((JLabel) jpanels[currentOwnedCountry].getComponent(0)).getText();
		attackingCountry = fileParser.getCountriesHashMap().get(countryName);
	}

	/**
	 * Sets the defender.
	 */
	public void setDefender() {
		String countryName = ((JLabel) adjPanels.get(currentAdjacentCountry).getComponent(0)).getText();
		defendingCountry = fileParser.getCountriesHashMap().get(countryName);
	}

	/**
	 * Sets the current state.
	 *
	 * @param currentState
	 *            the new current state
	 */
	public void setCurrentState(int currentState) {
		this.currentState = currentState;
	}

	/**
	 * Gets the current state.
	 *
	 * @return the current state
	 */
	public int getCurrentState() {
		return currentState;
	}
}
