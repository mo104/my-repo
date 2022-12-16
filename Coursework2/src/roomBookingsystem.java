import java.util.Scanner;
import java.util.regex.Pattern;
import java.util.ArrayList;
/*import java.io.FileReader;
import java.io.FileWriter;
import java.io.FileNotFoundException;*/
import java.util.InputMismatchException;

public class roomBookingsystem {

	static Scanner userInputscanner = new Scanner(System.in);
	static ArrayList<Hotelrooms> availableRooms = new ArrayList<Hotelrooms>();
	static ArrayList<Userpreferences> preference = new ArrayList<Userpreferences>();

	public static void main(String[] args) {
		String menuContent = "--Room Booking System--\n\n--MAIN MENU--\n1-Reserve Room\n2-Cancel Room\n3-View Room Reservations\nQ-Quit\nPick:";

		boolean loopFlag = false;
		while (loopFlag == false) {
			switch (mainMenu(menuContent)) {

			case "1":
				roomReserve();
				// mainMenu(menuContent);
				break;
			case "q":
				loopFlag = true;
			case "Q":
				loopFlag = true;
			}
		}

	}

	public static String mainMenu(String msg) {
		String ans;

		do {
			System.out.println(msg);
			ans = userInputscanner.nextLine();
		} while (!Pattern.matches("[1-4qQ]", ans)); // using REGex for input validation
		return ans;
	}

	public static void roomReserve() {

		Userpreferences preferences = new Userpreferences();

		do {
			System.out.println("Please select from the following options:");
			System.out.print("Room Type:\n1.Single\n2.Double\n3.Suite\n\nPick(1-3): "); // each do while loop exists to
																						// validate input
			preferences.setUserRoomtypes(userInputscanner.nextLine());
		} while (!Pattern.matches("[1-3]", preferences.getUserRoomtypes()));

		switch (preferences.getUserRoomtypes()) {
		case "1":
			preferences.setUserRoomtypes("single");

		case "2":
			preferences.setUserRoomtypes("double");

		case "3":
			preferences.setUserRoomtypes("suite");
		}

		do {
			try {
				System.out.print("Room Price(e.g. 30):");
				preferences.setUserRoomprices(userInputscanner.nextDouble());
				if (preferences.getUserRoomprices() <= 0) {
					throw new InputMismatchException();
				}
			} catch (InputMismatchException e) {
				System.out.println("Invalid Input! Please try again.\n");
				userInputscanner.nextLine();
			}
		} while (preferences.getUserRoomprices() <= 0);

		do {
			try {
				System.out.print("Balcony[true|false]:");
				preferences.setUserHasbalcony(userInputscanner.nextBoolean());
			} catch (InputMismatchException e) {
				System.out.println("Invalid Input! Please try again.\n");
				userInputscanner.nextLine();
			}
		} while (!preferences.isUserHasbalcony() == true && !preferences.isUserHasbalcony() == false);

		do {
			try {
				System.out.print("Lounge[true|false]:");
				preferences.setUserHaslounge(userInputscanner.nextBoolean());
			} catch (InputMismatchException e) {
				System.out.println("Invalid Input! Please try again.\n");
				userInputscanner.nextLine();
			}
		} while (!preferences.isUserHaslounge() == true && !preferences.isUserHaslounge() == false);

		do {
			userInputscanner.nextLine(); // moving onto nextLine because nextBoolean above does not digest newline
											// character
			System.out.print("Please enter your email: ");
			preferences.setUserEmail(userInputscanner.nextLine());
			System.out.println(preferences.getUserEmail());
		} while (!Pattern.matches(
				"^([\\w-]+(?:\\.[\\w-]+)*)@((?:[\\w-]+\\.)*\\w[\\w-]{0,66})\\.([a-z]{2,6}(?:\\.[a-z]{2})?)$",
				preferences.getUserEmail())); // regex expression validating common email format

		System.out.println(preferences.toString);

		preference.add(preferences);
		

		/*
		 * Look for room specified and display the exact or similar room then ask if
		 * they want to reserve it if they do then ask for their email and reserve the
		 * room if they do not want to reserve the room then return to main menu
		 */

	}

}
