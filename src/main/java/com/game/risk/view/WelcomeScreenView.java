package com.game.risk.view;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.filechooser.FileSystemView;

import com.game.risk.core.parser.MapFileParser;

import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import java.awt.Font;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;

/**
 * View for the user to choose from loading a map file or creating a new map
 * 
 * @author Sarthak
 * @author sohrab_singh
 *
 */
public class WelcomeScreenView extends JFrame implements MouseListener {

	/**
	 * Serial Version UID
	 */
	private static final long serialVersionUID = -5774732089402932902L;

	/**
	 * Map File Parser
	 */
	private MapFileParser parser;

	/**
	 * Map Editor View to create a new Map or make changes to the existing map file
	 */
	private MapEditorView view;

	private WelcomeScreenInterface welcomeInterface;
	private JPanel contentPane;
	private JButton btnLoad;
	private JButton btnNewMap;

	/**
	 * Create the frame.
	 */
	public WelcomeScreenView() {
		initializeView();

		btnLoad.addMouseListener(this);
		btnNewMap.addMouseListener(this);
	}

	private void initializeView() {
		setBackground(Color.WHITE);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBackground(Color.BLACK);
		contentPane.setBorder(new EmptyBorder(20, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel lblWelcome = new JLabel("Risk Game");
		lblWelcome.setBounds(0, 13, 432, 33);
		lblWelcome.setVerticalAlignment(SwingConstants.TOP);
		lblWelcome.setHorizontalAlignment(SwingConstants.CENTER);
		lblWelcome.setFont(new Font("Tahoma", Font.BOLD, 26));
		lblWelcome.setForeground(Color.WHITE);
		contentPane.add(lblWelcome);

		btnLoad = new JButton("Load Map File");

		JLabel lblInfoText = new JLabel("Start with selecting a Map File, or Creating a new Map");
		lblInfoText.setHorizontalAlignment(SwingConstants.CENTER);
		lblInfoText.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblInfoText.setForeground(Color.LIGHT_GRAY);
		lblInfoText.setBackground(Color.BLACK);
		lblInfoText.setBounds(0, 46, 432, 33);
		contentPane.add(lblInfoText);
		btnLoad.setBounds(135, 109, 166, 47);
		contentPane.add(btnLoad);

		btnNewMap = new JButton("Create a New Map");
		btnNewMap.setBounds(135, 169, 166, 47);
		contentPane.add(btnNewMap);

	}

	@Override
	public void mouseClicked(MouseEvent event) {
		if (event.getComponent() == btnLoad) {
			setVisible(false);
			JFileChooser fileChooser = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
			fileChooser.setDialogTitle("Choose a Map File");
			fileChooser.setAcceptAllFileFilterUsed(false);
			FileNameExtensionFilter filter = new FileNameExtensionFilter("Map File Extensions", "map", "MAP");
			fileChooser.addChoosableFileFilter(filter);
			int returnValue = fileChooser.showOpenDialog(null);
			if (returnValue == JFileChooser.APPROVE_OPTION) {
				String filename = fileChooser.getSelectedFile().getAbsolutePath();
				System.out.println("Path: " + filename);
				try {
					parser = new MapFileParser(filename).readFile();
					view = new MapEditorView(parser);
					int playersCount = view.readMapEditor(false);
					startStartupPhase(playersCount);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		} else {
			setVisible(false);
			parser = new MapFileParser();
			view = new MapEditorView(parser);
			try {
				int playersCount = view.readMapEditor(true);
				startStartupPhase(playersCount);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	private void startStartupPhase(int playersCount) throws NumberFormatException, IOException {
		welcomeInterface.notifyRiskGameDriver(playersCount);

	}

	public void addListener(WelcomeScreenInterface welcomeInterface) {
		this.welcomeInterface = welcomeInterface;
	}

	/**
	 * Get Map File Parser
	 * 
	 * @return parser MapFileParser object
	 */
	public MapFileParser getParser() {
		return parser;
	}

	/**
	 * Set the MapFileParser object to the private class variable
	 * 
	 * @param parser
	 *            MapFileParser object
	 */
	public void setParser(MapFileParser parser) {
		this.parser = parser;
	}

	/**
	 * Get the Map Editor View object
	 * 
	 * @return view MapEditorView object
	 */
	public MapEditorView getView() {
		return view;
	}

	/**
	 * Set the MapEditorView object to the private class variable
	 * 
	 * @param view
	 */
	public void setView(MapEditorView view) {
		this.view = view;
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	public interface WelcomeScreenInterface {
		public void notifyRiskGameDriver(int numberOfPlayers) throws NumberFormatException, IOException;
	}
}
