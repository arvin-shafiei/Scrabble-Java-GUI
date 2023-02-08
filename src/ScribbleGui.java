
/**
 * ConnectGui: 
 * 
 * @author Iain Martin 
 * @version 1.0
 * 
 * Notes to use ConnectGui
 *  ConnectGuu is intended as a replacement for a Menu class for the 
 *  Connect4 project.
 *  This is optional and would be given credit as an optional extra if
 *  you make a good job of using and adapting it.
 *  However, please feel free to implement your own GUI in any way you wish
 *  to. This intention of this example is not to restrict you in any way
 *  but to give you a head-start if you wish to play with GUI code and
 *  are not sure how to get started.
 *  
 *  Comments that start with ConnectGUI mark where you might 
 *  add your own code. Please do not attempt to use this GUI until
 *  you have already met the minimum requirements of the project.
 * 
 * Notes:
 *  Event handlers have been set up for Menu Options
 *  NewGame, LoadGame and Save Game.
 *  
 *  An Event handler has also been set up for a Mouse Click on
 *  the grid which calls clickSquare(row, col), setting it to a specific
 *  colour.
 *  
 *  To add functionality to this GUI add you code to these functions
 *  which are at the end of this file. 
 *  
 *  Potential additions: FileChoosers could be implemented and the grid characters
 *  could be replaced with graphics by loading gifs or jpgs into the grid which is
 *  created from JButtons.
 */

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.io.*;
import java.util.Scanner;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import com.fasterxml.jackson.databind.*;

public class ScribbleGui implements ActionListener {

	/**
	 * Enumerated type to allow us to refer to RED, YELLOW or BLANK
	 */
	private enum GRID_COLOUR {
		BLANK, YELLOW, RED
	}

	// Default filename to use for saving and loading files
	// Possible improvement: replace with a FileChooser
	private final static String DEFAULT_FILENAME = "Connectgui.txt";
	private int GRID_SIZE_X = 15, GRID_SIZE_Y = 15;
	private JButton[] buttonArray;
	private static boolean firstMoveMade = false;

	public JMenuBar createMenu() {
		JMenuBar menuBar = new JMenuBar();
		;
		JMenu menu = new JMenu("Action Menu");
		JMenuItem menuItem;

		menuBar.add(menu);

		// A group of JMenuItems. You can create other menu items here if desired
		menuItem = new JMenuItem("New Game");
		menuItem.addActionListener(this);
		menu.add(menuItem);

		menuItem = new JMenuItem("Load Game");
		menuItem.addActionListener(this);
		menu.add(menuItem);

		menuItem = new JMenuItem("Save Game");
		menuItem.addActionListener(this);
		menu.add(menuItem);
		
		menuItem = new JMenuItem("End Game");
		menuItem.addActionListener(this);
		menu.add(menuItem);
		
		menuItem = new JMenuItem("Show Player Scores");
		menuItem.addActionListener(this);
		menu.add(menuItem);

		// a submenu
		menu.addSeparator();
		return menuBar;
	}

	public Container createContentPane() {
		int numButtons = GRID_SIZE_X * GRID_SIZE_Y;
		JPanel grid = new JPanel(new GridLayout(GRID_SIZE_Y, GRID_SIZE_X));
		buttonArray = new JButton[numButtons];

		for (int i = 0; i < numButtons; i++) {
			buttonArray[i] = new JButton(" ");

			// This label is used to identify which button was clicked in the action
			// listener
			buttonArray[i].setActionCommand("" + i); // String "0", "1" etc.
			buttonArray[i].addActionListener(this);
			buttonArray[i].setBackground(Color.lightGray);
			grid.add(buttonArray[i]);
		}
		
		// Set middle tile red
		buttonArray[112].setBackground(Color.red);
		
		return grid;
	}

	/**
	 * This method handles events from the Menu and the board.
	 *
	 */
	public void actionPerformed(ActionEvent e) {
		String classname = getClassName(e.getSource());
		JComponent component = (JComponent) (e.getSource());

		if (classname.equals("JMenuItem")) {
			JMenuItem menusource = (JMenuItem) (e.getSource());
			String menutext = menusource.getText();

			// Determine which menu option was chosen
			if (menutext.equals("Load Game")) {
				/* ConnectGUI Add your code here to handle Load Game **********/
				LoadGame();
			} else if (menutext.equals("Save Game")) {
				/* ConnectGUI Add your code here to handle Save Game **********/
				SaveGame();
			} else if (menutext.equals("New Game")) {
				/* ConnectGUI Add your code here to handle Save Game **********/
				NewGame();
			} else if (menutext.equals("End Game")) {
				/* ConnectGUI Add your code here to handle Save Game **********/
				EndGame();
			} else if (menutext.equals("Show Player Scores")) {
				/* ConnectGUI Add your code here to handle Save Game **********/
				ShowPlayerScores();
			}
		}
		// Handle the event from the user clicking on a command button
		else if (classname.equals("JButton")) {
			JButton button = (JButton) (e.getSource());
			int bnum = Integer.parseInt(button.getActionCommand());
			int row = bnum % GRID_SIZE_X;
			int col = bnum / GRID_SIZE_X;

			System.out.println("bnum=" + bnum);

			/*
			 * ConnectGUI Add your code here to handle user clicking on the grid
			 ***********/
			clickSquare(row, col);
		}
	}

	/**
	 * Returns the class name
	 */
	protected String getClassName(Object o) {
		String classString = o.getClass().getName();
		int dotIndex = classString.lastIndexOf(".");
		return classString.substring(dotIndex + 1);
	}

	/**
	 * Create the GUI and show it. For thread safety, this method should be invoked
	 * from the event-dispatching thread.
	 */
	private static void createAndShowGUI() {
		// Create and set up the window.
		JFrame frame = new JFrame("ScribbleGui");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// Create and set up the content pane.
		ScribbleGui Connectgui = new ScribbleGui();
		frame.setJMenuBar(Connectgui.createMenu());
		frame.setContentPane(Connectgui.createContentPane());

		// Display the window, setting the size
		frame.setSize(900, 800);
		frame.setVisible(true);
	}

	/**
	 * Sets a Gui grid square at row, col to display a character
	 */
	public boolean setText(int row, int col, String buttonText) {
		int bnum = col * GRID_SIZE_X + row;
		if (bnum >= (GRID_SIZE_X * GRID_SIZE_Y)) {
			return false;
		} else {
			System.out.println("row: " + row + " col: " + col + " Text: " + buttonText + " bnum= " + bnum);
			buttonArray[bnum].setText(buttonText);
		}
		return true;
	}

	/**
	 * Sets a Gui grid square at row, col to display a character
	 */
	public boolean setGuiSquare(int row, int col, GRID_COLOUR colour) {
		int bnum = col * GRID_SIZE_X + row;
		if (bnum >= (GRID_SIZE_X * GRID_SIZE_Y)) {
			return false;
		} else {
			switch (colour) {
			case RED:
				buttonArray[bnum].setBackground(Color.red);
				break;
			case YELLOW:
				buttonArray[bnum].setBackground(Color.yellow);
				break;
			case BLANK:
				buttonArray[bnum].setBackground(Color.lightGray);
				break;
			default:
				buttonArray[bnum].setBackground(Color.lightGray);
				break;
			}
		}
		return true;
	}

	/**
	 * This is a standard main function for a Java GUI
	 */
	public static void main(String[] args) {
		// Schedule a job for the event-dispatching thread:
		// creating and showing this application's GUI.
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				createAndShowGUI();
			}
		});
	}

	// ************************************************************************
	// *** ConnectGUI: Modify the methods below to respond to Menu and Mouse click
	// events

	/**
	 * This method is called from the Menu event: New Game. ConnectGUI
	 */
	public void NewGame() {
		System.out.println("New game selected");

		// CLEAR BOARD
		// CLEAR PLAYER DATA
		// REQUEST USERNAMES
		// START TURNS

	}

	/**
	 * This method is called from the Menu event: Load Game. ConnectGUI
	 */
	public void LoadGame() {
		System.out.println("Load game selected");

		JFileChooser f = new JFileChooser();
		f.setCurrentDirectory(new java.io.File("."));
		f.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		f.showOpenDialog(null);
		f.setDialogTitle("select folder");

		String[] playerData = new String[4];
		int counter = 0;

		try {
			File myObj = new File(f.getSelectedFile() + "/playerData.txt");
			Scanner myReader = new Scanner(myObj);
			while (myReader.hasNextLine()) {
				String data = myReader.nextLine();
				System.out.println(data);
				playerData[counter] = data;
				counter++;
			}
			myReader.close();
		} catch (FileNotFoundException e) {
			System.out.println("An error occurred.");
			e.printStackTrace();
		} finally {
			GameSystem.loadGame(playerData, counter);
		}

		try {
			int currentRow = 0;
			int currentCol = 0;

			File myObj = new File(f.getSelectedFile() + "/gameBoard.txt");
			Scanner myReader = new Scanner(myObj);

			while (myReader.hasNextLine()) {
				String data = myReader.nextLine();

				if (currentRow == (GRID_SIZE_X - 1)) {
					currentRow = 0;
					currentCol++;
				} else {
					currentRow++;
				}

				if (!(data.equals("BLANK"))) {
					setText(currentRow, currentCol, data);

					setGuiSquare(currentRow, currentCol, GRID_COLOUR.YELLOW);
				}
			}
			myReader.close();
		} catch (FileNotFoundException e) {
			System.out.println("An error occurred.");
			e.printStackTrace();
		}
	}

	/**
	 * This method is called from the Menu event: Save Game. ConnectGUI
	 */
	public void SaveGame() {
		System.out.println("Save game selected");

		int gridSize = GRID_SIZE_X * GRID_SIZE_Y;

		String letterArray[] = new String[gridSize];

		for (int i = 0; i < gridSize; i++) {
			letterArray[i] = buttonArray[i].getText();

			if (letterArray[i].isBlank()) {
				letterArray[i] = "BLANK";
			}
		}

		GameSystem.SaveGame(letterArray);
	}
	
	public void EndGame() {
		System.out.println("REQUESTED END GAME");
		
		// show winner and scores
	}
	
	public void ShowPlayerScores() {
		System.out.println("REQUEST SHOW SCORES");
		
		// show scores in descending order
	}
	
	
	/**
	 * This method is called from the Mouse Click event. ConnectGUI
	 */

	public int calculatePoints(String Letter) {
		switch (Letter.toUpperCase()) {
		case "A":
		case "E":
		case "I":
		case "O":
		case "U":
		case "L":
		case "N":
		case "S":
		case "T":
		case "R":
			return 1;
		case "D":
		case "G":
			return 2;
		case "B":
		case "C":
		case "M":
		case "P":
			return 3;
		case "F":
		case "H":
		case "V":
		case "W":
		case "Y":
			return 4;
		case "K":
			return 5;
		case "J":
		case "X":
			return 8;
		case "Q":
		case "Z":
			return 10;
		default:
			return 0;
		}
	}

	public void calculatePoints(int row, int col, String movementDirection) {
		// TODO: REPLACE continueLoop loop with just true and use breaks

		int totalScore = 0;
		boolean continueLoop = true;
		int currentCol = col;
		int currentRow = row;
		String pointWord = "";

		while (continueLoop) {
			int bnum = currentCol * GRID_SIZE_X + currentRow;

			if (bnum >= (GRID_SIZE_X * GRID_SIZE_Y)) {
			} else {
				if (bnum <= 0) {
					continueLoop = false;
					break;
				}
				if (buttonArray[bnum] != null && bnum >= 1 && currentRow >= 0 && currentCol >= 0) {

					String currentLetter = buttonArray[bnum].getText();

					if (currentLetter != null || !(currentLetter.isBlank()) || !(currentLetter.isEmpty())) {
						totalScore += calculatePoints(currentLetter);
						pointWord = pointWord + currentLetter;
						if (movementDirection.equals("Vertical")) {
							currentCol -= 1;
						} else {
							currentRow -= 1;
						}
					} else {
						continueLoop = false;
						break;
					}
					System.out.println("Row: " + currentRow + " Col: " + currentCol);
				} else {
					continueLoop = false;
					break;
				}

			}
		}
		System.out.println(pointWord);
		System.out.println(totalScore);
		GameSystem.AddScore(totalScore);
	}

	public String findWord(int row, int col, String movementDirection) {
		// TODO: REPLACE continueLoop loop with just true and use breaks
		boolean continueLoop = true;
		int currentCol = col;
		int currentRow = row;
		String pointWord = "";

		while (continueLoop) {
			int bnum = currentCol * GRID_SIZE_X + currentRow;

			if (bnum >= (GRID_SIZE_X * GRID_SIZE_Y)) {
			} else {
				if (bnum <= 0) {
					continueLoop = false;
					break;
				}
				if (buttonArray[bnum] != null && bnum >= 1 && currentRow >= 0 && currentCol >= 0) {

					String currentLetter = buttonArray[bnum].getText();

					if (currentLetter != null) {
						if (!(currentLetter.equals(" "))) {
							pointWord = pointWord + currentLetter;
						}

						if (movementDirection.equals("Vertical")) {
							currentCol -= 1;
						} else {
							currentRow -= 1;
						}
					} else {
						continueLoop = false;
						break;
					}
					System.out.println("Row: " + currentRow + " Col: " + currentCol);
				} else {
					continueLoop = false;
					break;
				}

			}
		}

		String rPointsWord = "";
		char ch;

		for (int i = 0; i < pointWord.length(); i++) {
			ch = pointWord.charAt(i); // extracts each character
			rPointsWord = ch + rPointsWord; // adds each character in front of the existing string
		}
		System.out.println("Reversed word: " + rPointsWord);

		System.out.println(rPointsWord);

		return rPointsWord;
	}

	public void wipeTipes(int[] trackMovementsRow, int[] trackMovementsCol) {
		for (int i = 0; i < trackMovementsRow.length; i++) {
			setGuiSquare(trackMovementsRow[i], trackMovementsCol[i], GRID_COLOUR.BLANK);
			setText(trackMovementsRow[i], trackMovementsCol[i], "");
		}
	}

	public void clickSquare(int row, int col) {
		// BREAK IF ALREADY HAS TEXT
	
		System.out.println("Clicked square at (" + row + ", " + col + ")");

		setGuiSquare(row, col, GRID_COLOUR.YELLOW);

		// GAME SYSTEM METHOD
		int currentRow = row;
		int currentCol = col;

		boolean makingMovie = true;

		String nextLetter = JOptionPane.showInputDialog("Enter your letter (Type 1 to end/cancel turn)");
		if (nextLetter == null || nextLetter.equals("1")) {
			setGuiSquare(currentRow, currentCol, GRID_COLOUR.BLANK);
			makingMovie = false;
			return;
		}
		// SET LETTER ON BOARD
		setText(currentRow, currentCol, nextLetter);
		setGuiSquare(currentRow, currentCol, GRID_COLOUR.YELLOW);

		String movementDirection = "Undefined";

		int[] trackMovementsRow = new int[GRID_SIZE_X];
		int[] trackMovementsCol = new int[GRID_SIZE_Y];

		trackMovementsRow[0] = currentRow;
		trackMovementsCol[0] = currentCol;

		int counter = 1;

		// default icon, custom title
		if (JOptionPane.showConfirmDialog(null, "Would you like to move vertically?", "WARNING",
				JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
			movementDirection = "Vertical";
		} else {
			movementDirection = "Horizontal";
		}

		while (makingMovie) {
			if (movementDirection.equals("Horizontal")) {
				if (currentRow == GRID_SIZE_X - 1) {
					break;
				}
			}

			int bnum = currentCol * GRID_SIZE_X + currentRow;

			if (movementDirection.equals("Vertical")) {
				boolean checkingValue = true;

				while (checkingValue) {
					bnum = currentCol * GRID_SIZE_X + currentRow;

					if (bnum > buttonArray.length) {
						makingMovie = false;
						break;
					}

					String currentLetter = buttonArray[bnum].getText();

					if (!(currentLetter.isBlank())) {
						currentCol++;
					} else {
						checkingValue = false;
					}

					if (bnum > buttonArray.length) {
						makingMovie = false;
						break;
					}

				}
			} else {
				boolean checkingValue = true;

				while (checkingValue) {
					bnum = currentCol * GRID_SIZE_X + currentRow;
					String currentLetter = buttonArray[bnum].getText();

					if (!(currentLetter.isBlank())) {
						currentRow++;
					} else {
						checkingValue = false;
					}
				}
			}

			nextLetter = JOptionPane.showInputDialog("Enter your letter (Type 1 to end turn)");

			if (nextLetter == null || nextLetter.equals("1")) {
				makingMovie = false;
				break;
			}

			setText(currentRow, currentCol, nextLetter);

			setGuiSquare(currentRow, currentCol, GRID_COLOUR.YELLOW);

			trackMovementsCol[counter] = currentCol;
			trackMovementsRow[counter] = currentRow;
			counter++;

			if ((((currentCol + 1) * GRID_SIZE_X + currentRow) > buttonArray.length)
					&& movementDirection.equals("Vertical")) {
				break;
			}
		}

		// BIG MAN API THING THAT HAD INCORRECT DOCS
		try {
			URL url = new URL("https://api.api-ninjas.com/v1/dictionary?word="
					+ findWord(currentRow, currentCol, movementDirection));
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			connection.setRequestProperty("accept", "application/json");
			connection.setRequestProperty("X-Api-Key", "M4iNcbwEpqXWZObar6Dw2w==vmI1Fa3vRD7iC3l5");
			InputStream responseStream = connection.getInputStream();
			ObjectMapper mapper = new ObjectMapper();
			JsonNode root = mapper.readTree(responseStream);
			boolean isValid = Boolean.valueOf(root.path("valid").asText());

			System.out.println(isValid);
			if (isValid) {
				if (!(firstMoveMade)) {
					firstMoveMade = true;
					// CHECK IF MIDDLE
					// bnum = 112, (7,7)
					if (!((buttonArray[112].getText()).isBlank())) {
						calculatePoints(currentRow, currentCol, movementDirection);
					} else {
						JOptionPane.showMessageDialog(null,
								"Whoops, you left the middle tile empty!");
						wipeTipes(trackMovementsRow, trackMovementsCol);
					}
				} else {
					calculatePoints(currentRow, currentCol, movementDirection);
				}
				
			} else {
				JOptionPane.showMessageDialog(null,
						"Whoops, seems as if you didn't provide a valid word :( try again!");
				wipeTipes(trackMovementsRow, trackMovementsCol);
			}
		} catch (IOException e) {
			System.out.println(e);
			JOptionPane.showMessageDialog(null,
					"Whoops, seems as if I can't connect to the dictionary, maybe check your firewall settings!");
		}
	}
}
