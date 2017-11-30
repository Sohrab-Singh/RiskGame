package com.game.risk.core.util;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

/**
 * Logging utility class for showing logging view of the actions performed in
 * the game.
 * 
 * @author sohrab_singh
 */
public class LoggingUtil {

	/** Text area for displaying logs. */
	private static JTextArea textArea;

	/** Frame window */
	private JFrame frame;

	/** Scroll pane */
	private JScrollPane scroll;

	/**
	 * Method to show logging window.
	 */
	public void showLoggingWindow() {
		frame = new JFrame("Logging window");
		textArea = new JTextArea();
		scroll = new JScrollPane(textArea);
		scroll = new JScrollPane(textArea, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

		// We add the scroll, since the scroll already contains the textArea
		frame.getContentPane().add(scroll);
		frame.pack();
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	/**
	 * Method to append log message to textArea.
	 * 
	 * @param message
	 *            message to be appended.
	 */
	public static void logMessage(String message) {
		textArea.append(message);
		textArea.append("\n");
	}

}
