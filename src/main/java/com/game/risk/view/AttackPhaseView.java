package com.game.risk.view;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JFrame;

import com.game.risk.PhaseObservable;
import com.game.risk.RiskGameDriver;
import com.game.risk.core.util.PhaseStates;
import com.game.risk.model.Country;

import javax.swing.JPanel;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;

import java.awt.Color;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Observable;
import java.util.Observer;

import javax.swing.SwingConstants;
import javax.swing.JButton;

/**
 * Observer class to implement the Attack Phase dice roll
 * 
 * @author Sarthak
 * @author sohrab_singh
 *
 */
public class AttackPhaseView extends JFrame implements Observer {

	/**
	 * Serial Version UID
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Main JFrame Object
	 */
	private JFrame mainFrame;

	/**
	 * JPanel object
	 */
	private JPanel contentPane;

	/**
	 * Country object to store the attacker
	 */
	private Country attackerCountry;

	/**
	 * Coutry object to store the defenders
	 */
	private Country defenderCountry;

	/**
	 * Country object to store the current country
	 */
	private Country currentCountry;

	/**
	 * JLabel object to show image icon indicating attacker
	 */
	private JLabel labelImage1;

	/**
	 * JLabel object to show image icon indicating defender
	 */
	private JLabel labelImage2;

	/** label Dice 1 */
	private JLabel lblDice1;

	/** Label Dice 2 */
	private JLabel lblDice2;

	/** Label Dice 3 */
	private JLabel lblDice3;

	/**
	 * Max Armies allowed to move after capturing defender
	 */
	private int maxArmies;

	/**
	 * Min armies allowed to move after capturing defender
	 */
	private int minArmies;

	/**
	 * No of dice selected by attacker
	 */
	private int diceAttacker;

	/**
	 * No of dice selected by defender
	 */
	private int diceDefender;

	/** Defender Armies */
	private int defenderArmies;

	/** Attacker Armies */
	private int attackerArmies;

	/**
	 * Label Message to describe update the control from attack to moving armies
	 */
	private JLabel lblMessage;

	/** Label minus 1 */
	private JLabel labelMinus1;

	/** Label Plus 1 */
	private JLabel labelPlus1;

	/** Country 1 armies */
	private JLabel country1Armies;

	/** Country 2 armies */
	private JLabel country2Armies;

	/** Label Attack Dice. */
	private JLabel lblAttackDice;

	/** Label Defend Dice */
	private JLabel lblDefendDice;

	/** Between Move Armies */
	private JButton btnMoveArmies;

	/**
	 * Attack Phase View Constructor
	 * 
	 * @param attacker
	 *            the attacker
	 * @param defender
	 *            the defender
	 * 
	 * @param reader
	 *            MapFileReader type
	 */
	public AttackPhaseView(Country attacker, Country defender) {
		this.attackerCountry = attacker;
		this.defenderCountry = defender;
		initializeView();
		defenderArmies = defenderCountry.getCurrentNumberOfArmies();
		attackerArmies = attackerCountry.getCurrentNumberOfArmies();
		currentCountry = attackerCountry;
		initializeControlToAttacker();
	}

	/**
	 * Initialize control to attacker.
	 */
	private void initializeControlToAttacker() {
		if (attackerCountry.getCurrentNumberOfArmies() > 3) {
			lblDice3.setVisible(true);
			lblDice2.setVisible(true);
		} else if (attackerCountry.getCurrentNumberOfArmies() == 3) {
			lblDice3.setVisible(false);
			lblDice2.setVisible(true);
		} else if (attackerCountry.getCurrentNumberOfArmies() == 2) {
			lblDice2.setVisible(false);
			lblDice3.setVisible(false);
		}
		lblDice1.setVisible(true);
		lblAttackDice.setVisible(false);
		lblDefendDice.setVisible(false);
		labelImage1.setVisible(true);
		labelImage2.setVisible(false);
	}

	/**
	 * Initialize view of the Attack Phase view with jframe, jpanel and jlabel
	 */
	private void initializeView() {
		setBackground(Color.BLACK);
		setUndecorated(true);
		setBounds(960, 200, 610, 380);
		mainFrame = this;
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		contentPane = new JPanel();
		contentPane.setBackground(Color.BLACK);
		contentPane.setBorder(new EmptyBorder(20, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel lblCountry1 = new JLabel(attackerCountry.getCountryName());
		lblCountry1.setFont(new Font("Segoe UI", Font.BOLD, 20));
		lblCountry1.setForeground(Color.WHITE);
		lblCountry1.setBounds(12, 13, 191, 40);
		contentPane.add(lblCountry1);

		JLabel lblCountry2 = new JLabel(defenderCountry.getCountryName());
		lblCountry2.setHorizontalAlignment(SwingConstants.TRAILING);
		lblCountry2.setForeground(Color.WHITE);
		lblCountry2.setFont(new Font("Segoe UI", Font.BOLD, 20));
		lblCountry2.setBounds(389, 13, 191, 40);
		contentPane.add(lblCountry2);

		country1Armies = new JLabel(Integer.toString(attackerCountry.getCurrentNumberOfArmies()));
		country1Armies.setHorizontalAlignment(SwingConstants.CENTER);
		country1Armies.setForeground(Color.WHITE);
		country1Armies.setFont(new Font("Segoe UI", Font.BOLD, 20));
		country1Armies.setBounds(12, 53, 109, 40);
		contentPane.add(country1Armies);

		country2Armies = new JLabel(Integer.toString(defenderCountry.getCurrentNumberOfArmies()));
		country2Armies.setHorizontalAlignment(SwingConstants.CENTER);
		country2Armies.setForeground(Color.WHITE);
		country2Armies.setFont(new Font("Segoe UI", Font.BOLD, 20));
		country2Armies.setBounds(471, 53, 109, 40);
		contentPane.add(country2Armies);

		JLabel lblAttacks = new JLabel("attacks");
		lblAttacks.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblAttacks.setHorizontalAlignment(SwingConstants.CENTER);
		lblAttacks.setForeground(Color.WHITE);
		lblAttacks.setBounds(260, 24, 64, 24);
		contentPane.add(lblAttacks);

		lblMessage = new JLabel("How many dice roll do you want?");
		lblMessage.setHorizontalAlignment(SwingConstants.CENTER);
		lblMessage.setForeground(Color.WHITE);
		lblMessage.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		lblMessage.setBounds(162, 65, 266, 40);
		contentPane.add(lblMessage);

		labelImage1 = new JLabel("");
		labelImage1.setBounds(12, 199, 121, 97);
		Image image = new ImageIcon(this.getClass().getResource("/icons8_Hand_Cursor_96px.png")).getImage();
		labelImage1.setIcon(new ImageIcon(image));
		contentPane.add(labelImage1);

		labelImage2 = new JLabel("");
		labelImage2.setHorizontalAlignment(SwingConstants.TRAILING);
		labelImage2.setBounds(459, 199, 121, 97);
		labelImage2.setIcon(new ImageIcon(image));
		contentPane.add(labelImage2);
		Border border = BorderFactory.createLineBorder(Color.WHITE, 2, true);

		Border yellowBorder = BorderFactory.createLineBorder(Color.YELLOW, 2, true);
		lblAttackDice = new JLabel("1");
		lblAttackDice.setVisible(false);
		lblAttackDice.setHorizontalAlignment(SwingConstants.CENTER);
		lblAttackDice.setForeground(Color.WHITE);
		lblAttackDice.setFont(new Font("Segoe UI", Font.BOLD, 18));
		lblAttackDice.setBorder(yellowBorder);
		lblAttackDice.setBounds(43, 106, 43, 40);
		contentPane.add(lblAttackDice);

		lblDefendDice = new JLabel("1");
		lblDefendDice.setVisible(false);
		lblDefendDice.setHorizontalAlignment(SwingConstants.CENTER);
		lblDefendDice.setForeground(Color.WHITE);
		lblDefendDice.setFont(new Font("Segoe UI", Font.BOLD, 18));
		lblDefendDice.setBorder(yellowBorder);
		lblDefendDice.setBounds(503, 106, 43, 40);
		contentPane.add(lblDefendDice);

		JPanel panel = new JPanel();
		panel.setBackground(Color.BLACK);
		panel.setBounds(141, 118, 306, 79);
		contentPane.add(panel);

		lblDice1 = new JLabel("1");
		panel.add(lblDice1);

		lblDice1.setFont(new Font("Segoe UI", Font.BOLD, 18));
		lblDice1.setForeground(Color.WHITE);
		lblDice1.setHorizontalAlignment(SwingConstants.CENTER);
		lblDice1.setBorder(BorderFactory.createCompoundBorder(border, new EmptyBorder(10, 20, 10, 20)));

		labelMinus1 = new JLabel("-");
		panel.add(labelMinus1);
		labelMinus1.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				changeTransferArmies(false);
			}
		});
		labelMinus1.setHorizontalAlignment(SwingConstants.CENTER);
		labelMinus1.setFont(new Font("Segoe UI", Font.PLAIN, 34));
		labelMinus1.setBorder(new EmptyBorder(5, 10, 5, 10));
		labelMinus1.setBackground(Color.WHITE);
		labelMinus1.setForeground(Color.WHITE);
		labelMinus1.setVisible(false);

		lblDice2 = new JLabel("2");
		panel.add(lblDice2);
		lblDice2.setFont(new Font("Segoe UI", Font.BOLD, 18));
		lblDice2.setHorizontalAlignment(SwingConstants.CENTER);
		lblDice2.setForeground(Color.WHITE);
		lblDice2.setBorder(BorderFactory.createCompoundBorder(border, new EmptyBorder(10, 20, 10, 20)));

		labelPlus1 = new JLabel("+");
		panel.add(labelPlus1);
		labelPlus1.setHorizontalAlignment(SwingConstants.CENTER);
		labelPlus1.setBorder(new EmptyBorder(5, 10, 5, 10));
		labelPlus1.setForeground(Color.WHITE);
		labelPlus1.setFont(new Font("Segoe UI", Font.PLAIN, 34));
		labelPlus1.setBackground(Color.WHITE);
		labelPlus1.setVisible(false);
		labelPlus1.addMouseListener(new java.awt.event.MouseAdapter() {
			@Override
			public void mouseClicked(java.awt.event.MouseEvent evt) {
				changeTransferArmies(true);
			}
		});
		lblDice3 = new JLabel("3");
		panel.add(lblDice3);
		lblDice3.setForeground(Color.WHITE);
		lblDice3.setHorizontalAlignment(SwingConstants.CENTER);
		lblDice3.setFont(new Font("Segoe UI", Font.BOLD, 18));
		lblDice3.setBorder(BorderFactory.createCompoundBorder(border, new EmptyBorder(10, 20, 10, 20)));

		btnMoveArmies = new JButton("Move");
		btnMoveArmies.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				moveArmies();
			}
		});
		btnMoveArmies.setFont(new Font("Segoe UI", Font.BOLD, 18));
		btnMoveArmies.setBounds(235, 272, 131, 48);
		contentPane.add(btnMoveArmies);
		btnMoveArmies.setVisible(false);
		lblDice3.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mouseClicked(java.awt.event.MouseEvent evt) {
				mouseClickDice3(evt);
			}
		});

		lblDice2.addMouseListener(new MouseAdapter() {
			public void mouseClicked(java.awt.event.MouseEvent evt) {
				mouseClickDice2(evt);
			}
		});
		lblDice1.addMouseListener(new MouseAdapter() {
			public void mouseClicked(java.awt.event.MouseEvent evt) {
				mouseClickDice1(evt);
			}
		});
	}

	/***
	 * Change the current Transfer Armies.
	 * 
	 * @param isIncrement
	 */
	private void changeTransferArmies(boolean isIncrement) {
		int moveArmies = Integer.parseInt(lblDice2.getText());
		if (isIncrement) {
			if (moveArmies < maxArmies)
				lblDice2.setText(Integer.toString(moveArmies + 1));
			else
				System.out.println("Cannot increment more Armies than the Maximum");

		} else {
			if (moveArmies > minArmies)
				lblDice2.setText(Integer.toString(moveArmies - 1));
			else
				System.out.println("Cannot decrement more Armies than the Minimum");
		}
	}

	/** Move Armies after the winner is declared */
	private void moveArmies() {
		int moveArmies = Integer.parseInt(lblDice2.getText());
		attackerCountry.setCurrentNumberOfArmies(attackerCountry.getCurrentNumberOfArmies() - moveArmies);
		defenderCountry.setCurrentNumberOfArmies(moveArmies);
		mainFrame.setVisible(false);
		RiskGameDriver.initiatePostAttackUpdate();
	}

	/**
	 * Mouse Clicked function when user selects 3 dice.
	 * 
	 * @param evt
	 *            the event
	 */
	private void mouseClickDice3(MouseEvent evt) {
		if (currentCountry == attackerCountry) {
			diceAttacker = 3;
			setCurrentCountry(defenderCountry);
			updateControlToDefender();
			lblAttackDice.setVisible(true);
			lblAttackDice.setText(Integer.toString(diceAttacker));
		}
	}

	/**
	 * Mouse Clicked function when user selects 2 dice.
	 * 
	 * @param evt
	 *            the event
	 */
	private void mouseClickDice2(MouseEvent evt) {
		if (currentCountry == attackerCountry) {
			diceAttacker = 2;
			setCurrentCountry(defenderCountry);
			lblAttackDice.setVisible(true);
			lblAttackDice.setText(Integer.toString(diceAttacker));
			updateControlToDefender();
		} else {
			diceDefender = 2;
			lblDefendDice.setVisible(true);
			lblDefendDice.setText(Integer.toString(diceDefender));
			RiskGameDriver.startBattle(attackerCountry, defenderCountry, diceAttacker, diceDefender);
		}
	}

	/**
	 * Mouse Clicked function when user selects 1 dice.
	 * 
	 * @param evt
	 *            the event
	 */
	private void mouseClickDice1(MouseEvent evt) {
		if (currentCountry == attackerCountry) {
			diceAttacker = 1;
			lblAttackDice.setText(Integer.toString(diceAttacker));
			lblAttackDice.setVisible(true);
			setCurrentCountry(defenderCountry);
			updateControlToDefender();
		} else {
			diceDefender = 1;
			lblDefendDice.setVisible(true);
			lblDefendDice.setText(Integer.toString(diceDefender));
			RiskGameDriver.startBattle(attackerCountry, defenderCountry, diceAttacker, diceDefender);
		}
	}

	/***
	 * Give control to defender.
	 */
	private void updateControlToDefender() {
		lblDice3.setVisible(false);
		if (diceAttacker == 1 || defenderCountry.getCurrentNumberOfArmies() == 1)
			lblDice2.setVisible(false);

		labelImage1.setVisible(false);
		labelImage2.setVisible(true);
	}

	/**
	 * Get the current country
	 *
	 * @return Country
	 */
	public Country getCurrentCountry() {
		return currentCountry;
	}

	/**
	 * Set the current country.
	 * 
	 * @param currentCountry
	 *            current Country
	 */
	public void setCurrentCountry(Country currentCountry) {
		this.currentCountry = currentCountry;
	}

	@Override
	public void update(Observable arg0, Object arg1) {
		if (arg1 != null) {
			if (arg1.getClass() == Boolean.class) {
				if (((boolean) arg1) == true)
					updateAfterBattle((PhaseObservable) arg0);
			}
		}

		if (((PhaseObservable) arg0).getCurrentState() == PhaseStates.STATE_CAPTURE) {
			captureDefender();
		} else if (((PhaseObservable) arg0).getCurrentState() == PhaseStates.STATE_ATTACK) {
			// Reinitialize Control to Attacker
			initializeControlToAttacker();
		}
	}

	/**
	 * Capture Defender country after attacker won the battle.
	 */
	private void captureDefender() {
		lblDice1.setVisible(false);
		lblDice2.setVisible(true);
		lblDice3.setVisible(false);
		lblMessage.setText("Select number of armies to move to " + defenderCountry.getCountryName());
		labelMinus1.setVisible(true);
		lblDefendDice.setVisible(false);
		lblAttackDice.setVisible(false);
		labelImage1.setVisible(false);
		labelImage2.setVisible(false);
		labelPlus1.setVisible(true);
		maxArmies = attackerCountry.getCurrentNumberOfArmies() - 1;
		minArmies = 1;
		btnMoveArmies.setVisible(true);
		lblDice2.setText(Integer.toString(maxArmies));
	}

	/**
	 * Update armies after the battle.
	 * 
	 * @param observable
	 *            the phase observable
	 */
	private void updateAfterBattle(PhaseObservable observable) {
		int diffDefenderArmies = defenderArmies - defenderCountry.getCurrentNumberOfArmies();
		int diffAttackerArmies = attackerArmies - attackerCountry.getCurrentNumberOfArmies();

		if (diffDefenderArmies > 0)
			System.out.println(attackerCountry.getCountryName() + " (" + attackerCountry.getPlayerName()
					+ ") has destroyed " + ((diffDefenderArmies > 1) ? diffDefenderArmies + " armies" : "an army"));
		else if (diffAttackerArmies > 0)
			System.out.println(attackerCountry.getCountryName() + " (" + attackerCountry.getPlayerName() + ") has lost "
					+ ((diffAttackerArmies > 1) ? diffAttackerArmies + " armies" : "an army"));

		System.out.println(":: Update after Battle ::");
		// Updating attacker and defender armies
		attackerArmies = attackerCountry.getCurrentNumberOfArmies();
		defenderArmies = defenderCountry.getCurrentNumberOfArmies();
		System.out.println("Attacker Armies: " + attackerArmies);
		System.out.println("Defender Armies: " + defenderArmies);
		country1Armies.setText(Integer.toString(attackerCountry.getCurrentNumberOfArmies()));
		country2Armies.setText(Integer.toString(defenderCountry.getCurrentNumberOfArmies()));

		System.out.println("State: " + observable.getCurrentState());

		if (attackerCountry.getCurrentNumberOfArmies() == 1) {
			observable.setCurrentState(PhaseStates.STATE_ACTIVE);
			System.out.println(attackerCountry.getCountryName() + " (" + attackerCountry.getPlayerName()
					+ " ) has lost the battle");
			diceAttacker = 0;
			diceDefender = 0;
			attackerCountry = null;
			defenderCountry = null;
		} else {
			setCurrentCountry(attackerCountry);
		}

	}
}
