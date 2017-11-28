package com.game.risk.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import com.game.risk.model.Player;

/**
 * @author sohrab_singh
 *
 */
public class PlayerDominationView extends JFrame implements Observer, MouseListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2150623152505948331L;

	/** The color panel. */
	private JPanel colorPanel;

	/** The players. */
	private List<Player> players;

	private JPanel statusPanel;

	/**
	 * @param players
	 * 
	 */
	public PlayerDominationView(List<Player> players) {
		this.players = players;
		initializeView();
	}

	private void initializeView() {
		setBackground(Color.WHITE);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setUndecorated(true);
		setBounds(100, 755, 850, 180);

		JPanel dominationPanel = new JPanel();
		dominationPanel.setBackground(Color.BLACK);
		dominationPanel.setBorder(new EmptyBorder(20, 5, 5, 5));
		dominationPanel.setLayout(null);
		setContentPane(dominationPanel);

		JPanel playerInitPanel = new JPanel();
		playerInitPanel.setBackground(Color.BLACK);
		playerInitPanel.setBounds(12, 26, 197, 130);
		playerInitPanel.setLayout(new BoxLayout(playerInitPanel, BoxLayout.Y_AXIS));

		statusPanel = new JPanel();
		statusPanel.setBackground(Color.BLACK);
		statusPanel.setBounds(254, 44, 500, 29);
		statusPanel.setLayout(new FlowLayout(FlowLayout.LEADING, 0, 0));

		int i = 0;
		for (Player player : players) {
			JPanel playerColorPanel = new JPanel();
			playerColorPanel.setLayout(new FlowLayout());
			playerColorPanel.setBackground(Color.BLACK);

			JLabel labelPlayer = new JLabel(player.getPlayerName());
			Font font = new Font("Tahoma", Font.BOLD, 13);
			labelPlayer.setFont(font);
			labelPlayer.setForeground(Color.WHITE);
			playerColorPanel.add(labelPlayer);

			JPanel color = new JPanel();
			color.setBackground(getPlayerColor(i));
			playerColorPanel.add(color);

			playerInitPanel.add(playerColorPanel);

			colorPanel = new JPanel();
			colorPanel.setBackground(getPlayerColor(i));
			colorPanel.setPreferredSize(new Dimension((int) (player.getCurrentDominationPercentage() * 500), 29));

			statusPanel.add(colorPanel);
			i++;
		}
		dominationPanel.add(playerInitPanel);
		dominationPanel.add(statusPanel);

	}

	@Override
	public void update(Observable arg0, Object arg1) {
		this.setVisible(true);
		if (arg1.getClass().equals(String.class) && (String) arg1 != null) {
			if (arg1.equals("domination")) {
				updatePlayerDominationPanel();
			}
		}
	}

	private void updatePlayerDominationPanel() {
		statusPanel.removeAll();
		int i = 0;
		for (Player player : players) {

			colorPanel = new JPanel();
			colorPanel.setBackground(getPlayerColor(i));
			colorPanel.setPreferredSize(new Dimension((int) (player.getCurrentDominationPercentage() * 500), 29));

			statusPanel.add(colorPanel);
			i++;
		}
		statusPanel.validate();
		statusPanel.repaint();
	}

	/**
	 * Gets the player color.
	 *
	 * @param i
	 *            int
	 * @return the player color
	 */
	private Color getPlayerColor(int i) {

		Color color = null;
		// Assign the player color

		switch (i) {
		case 0:
			color = Color.RED;
			break;
		case 1:
			color = Color.BLUE;
			break;
		case 2:
			color = Color.YELLOW;
			break;
		case 3:
			color = Color.GREEN;
			break;
		case 4:
			color = Color.PINK;
			break;
		case 5:
			color = Color.ORANGE;
			break;
		default:
			break;
		}
		return color;
	}

	@Override
	public void mouseClicked(MouseEvent e) {

	}

	@Override
	public void mousePressed(MouseEvent e) {

	}

	@Override
	public void mouseReleased(MouseEvent e) {

	}

	@Override
	public void mouseEntered(MouseEvent e) {

	}

	@Override
	public void mouseExited(MouseEvent e) {

	}

}
