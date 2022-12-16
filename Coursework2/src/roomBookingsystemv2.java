import java.util.Scanner;
import java.util.regex.Pattern;
import java.util.ArrayList;
import java.io.FileReader;
import java.io.PrintWriter;
import java.io.FileNotFoundException;
import java.util.InputMismatchException;

public class roomBookingsystemv2 {

	static Scanner userInputscanner = new Scanner(System.in);
	static ArrayList<Hotelrooms> availableRooms = new ArrayList<Hotelrooms>();
	static ArrayList<Userpreferences> preference = new ArrayList<Userpreferences>();
	static ArrayList<Integer> roomScores = new ArrayList<Integer>();
	static int reserveCounter = 0; // A counter that counts how many times the reserve room option is selected

	public static void main(String[] args) throws FileNotFoundException {
		fileRead();
		final String menuContent = "--Room Booking System--\n\n--MAIN MENU--\n1-Reserve Room\n2-Cancel Room\n3-View Room Reservations\nQ-Quit\nPick:";

		boolean loopFlag = false;
		while (loopFlag == false) {
			switch (menuPrompt(menuContent)) {

			case "1":
				reservationPrompt();
				// mainMenu(menuContent);
				roomReserve();
				reserveCounter += 1;
				break;
			case "2":
				roomCancel();
				break;
			case "3":
				viewRooms();
				break;
			case "q":
				fileWrite();
				loopFlag = true;
				break;
			case "Q":
				fileWrite();
				loopFlag = true;
				break;
			}
		}

	}

	public static String menuPrompt(String msg) {
		String ans;

		do {
			System.out.println(msg);
			ans = userInputscanner.nextLine();
		} while (!Pattern.matches("[1-4qQ]", ans)); // using REGex for input validation
		return ans;
	}

	public static void reservationPrompt() {

		Userpreferences preferences = new Userpreferences();

		do {
			System.out.println("Please select from the following options:");
			System.out.print("\nRoom Type:\n1.Single\n2.Double\n3.Suite\n\nPick(1-3): "); // each do while loop exists
																							// to validate input
			preferences.setUserRoomtypes(userInputscanner.nextLine());
		} while (!Pattern.matches("[1-3]", preferences.getUserRoomtypes()));

		switch (preferences.getUserRoomtypes()) {
		case "1":
			preferences.setUserRoomtypes("Single");
			break;

		case "2":
			preferences.setUserRoomtypes("Double");
			break;

		case "3":
			preferences.setUserRoomtypes("Suite");
			break;
		}

		do {
			try {
				System.out.print("Room Price(e.g. 30):");
				preferences.setUserRoomprices(userInputscanner.nextDouble());
				userInputscanner.nextLine();
				if (preferences.getUserRoomprices() <= 0) {
					throw new InputMismatchException();
				}
			} catch (InputMismatchException e) {
				System.out.println("Invalid Input! Please try again.\n");
				userInputscanner.nextLine();
			}
		} while (preferences.getUserRoomprices() <= 0);

		String strInput;

		do {
			System.out.println("Balcony[Yes|No]: ");
			strInput = userInputscanner.nextLine();

			if (strInput.equalsIgnoreCase("yes") || strInput.equalsIgnoreCase("no")) {
				preferences.setUserHasbalcony((strInput.equalsIgnoreCase("yes")) ? true : false);
			} else {
				System.out.println("Invalid Input! Please try again.");
			}
		} while (!strInput.equalsIgnoreCase("yes") && !strInput.equalsIgnoreCase("no"));

		do {
			System.out.println("Lounge[Yes|No]: ");
			strInput = userInputscanner.nextLine();

			if (strInput.equalsIgnoreCase("yes") || strInput.equalsIgnoreCase("no")) {
				preferences.setUserHaslounge((strInput.equalsIgnoreCase("yes")) ? true : false);
			} else {
				System.out.println("Invalid Input! Please try again.");
			}
		} while (!strInput.equalsIgnoreCase("yes") && !strInput.equalsIgnoreCase("no"));

		do {
			System.out.print("Please enter your email: ");
			preferences.setUserEmail(userInputscanner.nextLine());
		} while (!Pattern.matches(
				"^([\\w-]+(?:\\.[\\w-]+)*)@((?:[\\w-]+\\.)*\\w[\\w-]{0,66})\\.([a-z]{2,6}(?:\\.[a-z]{2})?)$",
				preferences.getUserEmail())); // regex expression validating common email format

		preference.add(preferences);

		/*
		 * Look for room specified and display the exact or similar room then ask if
		 * they want to reserve it if they do then ask for their email and reserve the
		 * room if they do not want to reserve the room then return to main menu
		 */

	}

	public static void fileRead() throws FileNotFoundException {

		FileReader fileName = new FileReader("C:\\Users\\moham\\eclipse-workspace\\Coursework2\\src\\rooms2.txt");
		Scanner fileScanner = new Scanner(fileName);

		while (fileScanner.hasNextLine()) {
			Hotelrooms room = new Hotelrooms();
			room.setRoomID(Integer.parseInt(fileScanner.next()));
			room.setRoomType(fileScanner.next());
			room.setRoomPrice(Double.parseDouble(fileScanner.next()));
			room.setHasBalcony(Boolean.parseBoolean(fileScanner.next()));
			room.setHasLounge(Boolean.parseBoolean(fileScanner.next()));
			room.setOccupant(fileScanner.next());
			availableRooms.add(room);

			if (fileScanner.hasNextLine()) {
				fileScanner.nextLine();
			}
		}

		System.out.println(availableRooms.size());

		fileScanner.close();
		// System.out.println(availableRooms);
	}

	public static void roomReserve() throws FileNotFoundException {

		for (int index = 0; index < availableRooms.size(); index++) {
			int currentPoints = 0;

			if (availableRooms.get(index).getOccupant().equals("free")) {
				if (preference.get(reserveCounter).getUserRoomtypes().equals(availableRooms.get(index).getRoomType())) {
					currentPoints = currentPoints + 1;
				}

				if (preference.get(reserveCounter).getUserRoomprices() <= availableRooms.get(index).getRoomPrice()) {
					currentPoints = currentPoints + 1;
				}

				if (preference.get(reserveCounter).isUserHasbalcony() == availableRooms.get(index).isHasBalcony()) {
					currentPoints = currentPoints + 1;
				}

				if (preference.get(reserveCounter).isUserHaslounge() == availableRooms.get(index).isHasLounge()) {
					currentPoints = currentPoints + 1;
				}

				roomScores.add(currentPoints);
			}

		}

		int biggestScore = roomScores.get(0);

		for (int j = 0; j < roomScores.size(); j++) {
			if (roomScores.get(j) > biggestScore) {
				biggestScore = roomScores.get(j);
			}
		}

		String confirmChoice;

		if (biggestScore == 4) { // 4 is the maximum amount of points a room can score implying it meets full
									// user specifications
			System.out.println("The room you have specified is available!");
			do {
				System.out.println("Would you like to book it?[yes|no]: ");
				confirmChoice = userInputscanner.nextLine();

				if (confirmChoice.equalsIgnoreCase("yes")) {
					availableRooms.get(roomScores.indexOf(biggestScore))
							.setOccupant(preference.get(reserveCounter).getUserEmail());
				}
			} while (!confirmChoice.equalsIgnoreCase("yes") && !confirmChoice.equalsIgnoreCase("no"));
		} else {

			do {
				System.out
						.println("Unable to find your exact room! Here is a similar room would you like to book it?\n");
				System.out.println(availableRooms.get(roomScores.indexOf(biggestScore)));
				System.out.println("Pick [Yes|No]: ");
				confirmChoice = userInputscanner.nextLine();
				if (confirmChoice.equalsIgnoreCase("yes")) {
					availableRooms.get(roomScores.indexOf(biggestScore))
							.setOccupant(preference.get(reserveCounter).getUserEmail());
					System.out.println();
					System.out.println(availableRooms.get(roomScores.indexOf(biggestScore)));
				}

			} while (!confirmChoice.equalsIgnoreCase("yes") && !confirmChoice.equalsIgnoreCase("no"));

		}

	}

	public static void roomCancel() {

		String emailCheck;
		int cancelRoomID = 0;
		boolean roomIDfound = false;
		boolean emailFound = false;

		do {
			System.out.println("Please enter your email:");
			emailCheck = userInputscanner.nextLine();
		} while (!Pattern.matches(
				"^([\\w-]+(?:\\.[\\w-]+)*)@((?:[\\w-]+\\.)*\\w[\\w-]{0,66})\\.([a-z]{2,6}(?:\\.[a-z]{2})?)$",
				emailCheck));

		for (int i = 0; i < availableRooms.size(); i++) {
			if (availableRooms.get(i).getOccupant().equals(emailCheck)) { // Checking if provided email exists as a
																			// reservation.
				emailFound = true;
				System.out.println(availableRooms.get(i));
			}
		}

		if (emailFound = false) {
			System.out.println("There are no room reservations with the email provided.");
			System.out.println(emailCheck);

		}

		do {
			System.out.println("Please enter the room ID of the room you would like to cancel:"); // identifying which
																									// room to cancel by
																									// room ID
			cancelRoomID = userInputscanner.nextInt();

			for (int i = 0; i < availableRooms.size(); i++) {
				if (availableRooms.get(i).getRoomID() == cancelRoomID) {
					roomIDfound = true;
					availableRooms.get(i).setOccupant("free");
					System.out.println("Room ID " + cancelRoomID + " is canceled");
					userInputscanner.nextLine();
				}
			}

		} while (!roomIDfound);

	}

	public static void fileWrite() throws FileNotFoundException {
		PrintWriter roomWrite = new PrintWriter("C:\\Users\\moham\\eclipse-workspace\\Coursework2\\src\\rooms2.txt");

		for (int index = 0; index < availableRooms.size(); index++) {
			roomWrite.printf("%d %s %.2f %b %b %s\n", availableRooms.get(index).getRoomID(),
					availableRooms.get(index).getRoomType(), availableRooms.get(index).getRoomPrice(),
					availableRooms.get(index).isHasBalcony(), availableRooms.get(index).isHasLounge(),
					availableRooms.get(index).getOccupant());
		}

		roomWrite.close();
	}

	public static void viewRooms() {
		String emailCheck;
		boolean emailFound = false;

		do {
			System.out.println("Please enter your email:");
			emailCheck = userInputscanner.nextLine();
		} while (!Pattern.matches(
				"^([\\w-]+(?:\\.[\\w-]+)*)@((?:[\\w-]+\\.)*\\w[\\w-]{0,66})\\.([a-z]{2,6}(?:\\.[a-z]{2})?)$",
				emailCheck));

		for (int i = 0; i < availableRooms.size(); i++) {
			if (availableRooms.get(i).getOccupant().equals(emailCheck)) {
				emailFound = true;
				System.out.println(availableRooms.get(i));
			}
		}

		if (emailFound = false) {
			System.out.println("There are no room reservations with the email provided.");
			System.out.println(emailCheck);

		}

	}

}

//class diagram at https://ljmu-my.sharepoint.com/:u:/r/personal/oucmelma_ljmu_ac_uk/_layouts/15/doc2.aspx?sourcedoc=%7B4241c721-6b0b-4615-a272-2c1341f13256%7D&action=edit&or=PrevEdit&wdnewandopenct=0