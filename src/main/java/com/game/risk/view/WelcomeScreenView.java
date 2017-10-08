package com.game.risk.view;

import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.filechooser.FileSystemView;

import com.game.risk.core.parser.MapFileParser;
import com.game.risk.core.parser.MapFileWriter;

import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import java.awt.Font;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;

/**
 * View for the user to choose from loading a map file or creating a new map
 * 
 * @author Sarthak
 *
 */
public class WelcomeScreenView extends JFrame {

	private JPanel contentPane;
	private JButton btnLoad;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					WelcomeScreenView frame = new WelcomeScreenView();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public WelcomeScreenView() {
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
		btnLoad.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
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
						MapFileParser parser = new MapFileParser(filename).readFile();
						MapFileWriter fileWriter = new MapFileWriter(filename, parser);
						MapEditorView view = new MapEditorView(parser, fileWriter);
						view.readMapEditor();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		});

		JLabel lblInfoText = new JLabel("Start with selecting a Map File, or Creating a new Map");
		lblInfoText.setHorizontalAlignment(SwingConstants.CENTER);
		lblInfoText.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblInfoText.setForeground(Color.LIGHT_GRAY);
		lblInfoText.setBackground(Color.BLACK);
		lblInfoText.setBounds(0, 46, 432, 33);
		contentPane.add(lblInfoText);
		btnLoad.setBounds(135, 109, 166, 47);
		contentPane.add(btnLoad);

		JButton btnNewMap = new JButton("Create a New Map");
		btnNewMap.setBounds(135, 169, 166, 47);
		contentPane.add(btnNewMap);
	}
}
