package com.game.risk.view;

import java.awt.Color;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import com.game.risk.RiskGameDriver;
import com.game.risk.core.MapFileReader;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.SwingConstants;
import java.awt.Font;
import java.awt.event.MouseAdapter;

// TODO: Auto-generated Javadoc
/**
 * The Class PlayerSelectionView.
 */
public class PlayerSelectionView extends JFrame implements MouseListener {

	/** Serial Version UID. */
	private static final long serialVersionUID = 2992970223687943225L;

	/** The file reader. */
	private MapFileReader fileReader;

	/** The content pane. */
	private JPanel contentPane;

	/** The player names panel. */
	private JPanel playerNamesPanel;

	/** The player types panel. */
	private JPanel playerTypesPanel;

	/** The btn start game. */
	private JButton btnStartGame;

	/** The combo box for Selecting no of players. */
	@SuppressWarnings("rawtypes")
	private JComboBox comboBox1;

	/** The types. */
	private String[] types = { "Human", "Aggressive", "Benevolent", "Random", "Cheater" };

	/** The player count types. */
	private String[] playerCountTypes = { "2", "3", "4", "5", "6" };

	/** The player 1 text field. */
	private JTextField player1TextField;

	/** The player 2 text field. */
	private JTextField player2TextField;

	/** The player 3 text field. */
	private JTextField player3TextField;

	/** The player 4 text field. */
	private JTextField player4TextField;

	/** The player 5 text field. */
	private JTextField player5TextField;

	/** The player 6 text field. */
	private JTextField player6TextField;

	/** The combo box for Player 1 type. */
	@SuppressWarnings("rawtypes")
	private JComboBox comboBox2;

	/** The combo box for Player 2 type. */
	@SuppressWarnings("rawtypes")
	private JComboBox comboBox3;

	/** The combo box for Player 3 type. */
	@SuppressWarnings("rawtypes")
	private JComboBox comboBox4;

	/** The combo box for Player 4 type. */
	@SuppressWarnings("rawtypes")
	private JComboBox comboBox5;

	/** The combo box for Player 5 type. */
	@SuppressWarnings("rawtypes")
	private JComboBox comboBox6;

	/** The combo box for Player 6 type. */
	@SuppressWarnings("rawtypes")
	private JComboBox comboBox7;

	/**
	 * Instantiates a new player selection view.
	 */
	public PlayerSelectionView(MapFileReader fileReader) {
		this.fileReader = fileReader;
		initializeView();
	}

	/**
	 * Initialize view.
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private void initializeView() {
		setBackground(Color.WHITE);
		setBounds(400, 200, 468, 460);

		// Initialize JPanel contentPane to hold the JLabel and JButton elements
		contentPane = new JPanel();
		contentPane.setBackground(Color.WHITE);
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JPanel topPanel = new JPanel();
		topPanel.setBackground(Color.WHITE);
		topPanel.setBounds(0, 0, 450, 330);
		topPanel.setBorder(new EmptyBorder(5, 50, 0, 5));
		topPanel.setLayout(new GridLayout(1, 2, 5, 5));
		contentPane.add(topPanel);

		playerNamesPanel = new JPanel();
		playerNamesPanel.setBackground(Color.WHITE);
		playerNamesPanel.setLayout(new GridLayout(7, 1, 5, 5));

		JLabel label = new JLabel("Select the No of Players");
		label.setFont(new Font("Segoe UI", Font.BOLD, 15));
		label.setHorizontalAlignment(SwingConstants.CENTER);
		player1TextField = new JTextField();
		player1TextField.setFont(new Font("Segoe UI", Font.PLAIN, 14));
		player2TextField = new JTextField();
		player2TextField.setFont(new Font("Segoe UI", Font.PLAIN, 14));
		player3TextField = new JTextField();
		player3TextField.setFont(new Font("Segoe UI", Font.PLAIN, 14));
		player3TextField.setVisible(false);

		player4TextField = new JTextField();
		player4TextField.setFont(new Font("Segoe UI", Font.PLAIN, 14));
		player4TextField.setVisible(false);

		player5TextField = new JTextField();
		player5TextField.setFont(new Font("Segoe UI", Font.PLAIN, 14));
		player5TextField.setVisible(false);

		player6TextField = new JTextField();
		player6TextField.setFont(new Font("Segoe UI", Font.PLAIN, 14));
		player6TextField.setVisible(false);

		playerNamesPanel.add(label);
		playerNamesPanel.add(player1TextField);
		playerNamesPanel.add(player2TextField);
		playerNamesPanel.add(player3TextField);
		playerNamesPanel.add(player4TextField);
		playerNamesPanel.add(player5TextField);
		playerNamesPanel.add(player6TextField);

		playerTypesPanel = new JPanel();
		playerTypesPanel.setLayout(new GridLayout(7, 1, 5, 5));
		playerTypesPanel.setBorder(new EmptyBorder(0, 0, 0, 50));
		playerTypesPanel.setBackground(Color.WHITE);
		comboBox1 = new JComboBox(playerCountTypes);
		comboBox1.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				selectNewPlayerOptions();

			}
		});
		comboBox2 = new JComboBox(types);
		comboBox3 = new JComboBox(types);
		comboBox4 = new JComboBox(types);
		comboBox4.setVisible(false);
		comboBox5 = new JComboBox(types);
		comboBox5.setVisible(false);
		comboBox6 = new JComboBox(types);
		comboBox6.setVisible(false);
		comboBox7 = new JComboBox(types);
		comboBox7.setVisible(false);
		playerTypesPanel.add(comboBox1);
		playerTypesPanel.add(comboBox2);
		playerTypesPanel.add(comboBox3);
		playerTypesPanel.add(comboBox4);
		playerTypesPanel.add(comboBox5);
		playerTypesPanel.add(comboBox6);
		playerTypesPanel.add(comboBox7);
		topPanel.add(playerNamesPanel);
		topPanel.add(playerTypesPanel);

		btnStartGame = new JButton("Start Game");
		btnStartGame.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				onStartButtonPressed();
			}
		});
		btnStartGame.setBounds(156, 367, 111, 33);
		contentPane.add(btnStartGame);
	}

	protected void onStartButtonPressed() {
		List<String> playerNames = new ArrayList<>();
		int playersCount = Integer.parseInt(playerCountTypes[comboBox1.getSelectedIndex()]);
		playerNames.add(player1TextField.getText());
		playerNames.add(player2TextField.getText());
		if (playersCount == 3)
			playerNames.add(player3TextField.getText());
		else if (playersCount == 4)
			playerNames.add(player4TextField.getText());
		else if (playersCount == 5)
			playerNames.add(player5TextField.getText());
		else if (playersCount == 6)
			playerNames.add(player6TextField.getText());

		try {
			RiskGameDriver.startGame(fileReader, playerNames);
			dispose();
			setVisible(false);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Select new player options.
	 */
	protected void selectNewPlayerOptions() {
		if (comboBox1.getSelectedItem().equals(playerCountTypes[1])) {
			player3TextField.setVisible(true);
			comboBox4.setVisible(true);
		} else if (comboBox1.getSelectedItem().equals(playerCountTypes[2])) {
			player3TextField.setVisible(true);
			comboBox4.setVisible(true);
			comboBox5.setVisible(true);
			player4TextField.setVisible(true);
		} else if (comboBox1.getSelectedItem().equals(playerCountTypes[3])) {
			player3TextField.setVisible(true);
			comboBox4.setVisible(true);
			comboBox5.setVisible(true);
			comboBox6.setVisible(true);
			player4TextField.setVisible(true);
			player5TextField.setVisible(true);
		} else if (comboBox1.getSelectedItem().equals(playerCountTypes[4])) {
			player3TextField.setVisible(true);
			comboBox4.setVisible(true);
			comboBox5.setVisible(true);
			comboBox6.setVisible(true);
			comboBox7.setVisible(true);
			player4TextField.setVisible(true);
			player5TextField.setVisible(true);
			player6TextField.setVisible(true);
		} else {
			comboBox4.setVisible(false);
			comboBox5.setVisible(false);
			comboBox6.setVisible(false);
			comboBox7.setVisible(false);
			player3TextField.setVisible(false);
			player4TextField.setVisible(false);
			player5TextField.setVisible(false);
			player6TextField.setVisible(false);
		}
	}

	/**
	 * The main method.
	 *
	 * @param args
	 *            the arguments
	 */
	public static void main(String[] args) {

	}

	@Override
	public void mouseClicked(MouseEvent e) {

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
}
