package com.game.risk.view;

import javax.swing.JFrame;

import com.game.risk.core.MapFileReader;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import java.awt.Color;
import javax.swing.JLabel;
import java.awt.Font;
import java.util.Observable;
import java.util.Observer;

import javax.swing.SwingConstants;

public class AttackPhaseView extends JFrame implements Observer {

	/**
	 * Serial Version UID
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * JPanel object
	 */
	private JPanel contentPane;

	/**
	 * MapFileReader object to fetch graph data
	 */
	private MapFileReader fileReader;

	/**
	 * Attack Phase View Constructor
	 * 
	 * @param reader
	 *            MapFileReader type
	 */
	public AttackPhaseView(MapFileReader reader) {
		initializeView();
	}

	/**
	 * Initialize view of the Attack Phase view with jframe, jpanel and jlabel
	 */
	private void initializeView() {
		setBackground(Color.BLACK);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		contentPane = new JPanel();
		contentPane.setBackground(Color.BLACK);
		contentPane.setBorder(new EmptyBorder(20, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel lblCountry1 = new JLabel("Country 1");
		lblCountry1.setFont(new Font("Segoe UI", Font.BOLD, 20));
		lblCountry1.setForeground(Color.WHITE);
		lblCountry1.setBounds(12, 13, 191, 40);
		contentPane.add(lblCountry1);

		JLabel lblCountry2 = new JLabel("Country 2");
		lblCountry2.setHorizontalAlignment(SwingConstants.TRAILING);
		lblCountry2.setForeground(Color.WHITE);
		lblCountry2.setFont(new Font("Segoe UI", Font.BOLD, 20));
		lblCountry2.setBounds(389, 13, 191, 40);
		contentPane.add(lblCountry2);

		JLabel country1Armies = new JLabel("3");
		country1Armies.setHorizontalAlignment(SwingConstants.CENTER);
		country1Armies.setForeground(Color.WHITE);
		country1Armies.setFont(new Font("Segoe UI", Font.BOLD, 20));
		country1Armies.setBounds(12, 53, 109, 40);
		contentPane.add(country1Armies);

		JLabel country2Armies = new JLabel("2");
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
		
		JLabel lblNewLabel = new JLabel("How many dice roll do you want?");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setForeground(Color.WHITE);
		lblNewLabel.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		lblNewLabel.setBounds(162, 65, 266, 40);
		contentPane.add(lblNewLabel);
	}

	@Override
	public void update(Observable arg0, Object arg1) {

	}
}
