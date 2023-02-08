import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import javax.swing.JOptionPane;

public class GameSystem {
	static int numOfPlayers = RequestPlayerCount();

	public static int playerTurn = 0;

	static ScribbleGui gameBoard = new ScribbleGui();

	static Player players[] = new Player[numOfPlayers];

	public static void loadGame(String[] playerData, int newPlayerCount) {
		for (int i = 0; i < newPlayerCount; i++) {
			System.out.println(i);
			String[] playerDataSplit = playerData[i].split(" | ");
			players[i] = new Player();
			players[i].setUsername(playerDataSplit[0]);
			players[i].setScore(Integer.parseInt(playerDataSplit[2]));

			System.out.println(players[i].getUsername() + " " + players[i].getScore());
		}

		ChangeTurn(0); // STORE WHAT PLAYERS TURN IT WAS

		JOptionPane.showMessageDialog(null, "Player " + players[playerTurn].getUsername() + "'s turn");
	}

	public static void main(String[] args) {
		// ENSURE NUMEBR IS INPUTTED
		// Start gui code
		gameBoard.main(args);

		// Create 2 Players
		// TODO: CONVERT TO LOOP FOR MORE THAN 2 PLAYERS
		for (int i = 0; i < numOfPlayers; i++) {
			players[i] = new Player();
			players[i].setUsername(JOptionPane.showInputDialog("Enter player" + (i + 1) + "'s username"));
		}

		JOptionPane.showMessageDialog(null, "Player " + players[playerTurn].getUsername() + "'s turn");

		// String m = JOptionPane.showInputDialog("Anyone there?");
		// System.out.println(m);

		// JOptionPane.showMessageDialog(null, "ALERT MESSAGE");
	}

	public static void ChangeTurn() {
		if ((playerTurn + 1) > numOfPlayers - 1) {
			playerTurn = 0;
		} else {
			playerTurn += 1;
		}
		JOptionPane.showMessageDialog(null, "Player " + players[playerTurn].getUsername() + "'s turn");
	}

	public static void ChangeTurn(int forceChange) {
		playerTurn = forceChange;
		JOptionPane.showMessageDialog(null, "Player " + players[playerTurn].getUsername() + "'s turn");
	}

	public static void AddScore(int totalScore) {
		int currentScore = players[playerTurn].getScore();
		players[playerTurn].setScore(currentScore + totalScore);
		ChangeTurn();
	}

	public static String FolderNameGenerator() {
		String folderName = "GameSave";

		File theDir = new File(folderName);

		int counter = 0;

		while (true) {
			if (theDir.exists()) {
				counter += 1;
				folderName = "GameSave " + counter;
				theDir = new File(folderName);
			} else {
				break;
			}
		}

		return folderName;
	}

	public static void SaveGame(String[] textTiles) {
		String folderName = FolderNameGenerator();

		try {
			new File(folderName).mkdirs();

			File myObj = new File(folderName + "/" + "playerData.txt");
			myObj.createNewFile();

			System.out.println(folderName + "/" + "playerData.txt");
			FileWriter myWriter = new FileWriter(folderName + "/" + "playerData.txt");

			for (int i = 0; i < numOfPlayers; i++) {
				myWriter.write(players[i].getUsername() + " | " + players[i].getScore() + "\n");
			}

			myWriter.close();

			File myObj2 = new File(folderName + "/" + "gameBoard.txt");
			myObj2.createNewFile();

			System.out.println(folderName + "/" + "gameBoard.txt");
			FileWriter myWriter2 = new FileWriter(folderName + "/" + "gameBoard.txt");

			for (int i = 0; i < textTiles.length; i++) {
				myWriter2.write(textTiles[i] + "\n");
			}

			myWriter2.close();

			File myObj3 = new File(folderName + "/" + "SystemData.txt");
			myObj3.createNewFile();

			System.out.println(folderName + "/" + "gameBoard.txt");
			FileWriter myWriter3 = new FileWriter(folderName + "/" + "gameBoard.txt");

			// STORE ENVIROMENT VARIABLES
			// SUCH AS CURRENT PLAYERTURN AND THATS ABOUT IT
			for (int i = 0; i < textTiles.length; i++) {
				myWriter3.write(textTiles[i] + "\n");
			}

			myWriter3.close();
		} catch (IOException e) {
			System.out.println("An error occurred.");
			e.printStackTrace();
		}
	}

	public static int RequestPlayerCount() {
		int playerCount = 2;
		
		while (true) {
			try {
				playerCount = Integer.parseInt(JOptionPane.showInputDialog("Enter the number of players (Min 2, Max 4)"));
			} catch (NumberFormatException e){
				// DONT CARE DOESN'T CHANGE ANYTHING JUST NEEDA CATCH IT
			}
			
			if (playerCount >= 2 && playerCount <= 4) {
				break;
			}
		}

		System.out.println(playerCount);

		return playerCount;
	}
}
