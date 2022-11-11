import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Scanner;

public class uniCoursework {
	public static void main(String[] args)throws FileNotFoundException {
		System.out.println("--Room Tax System--");
		Scanner userScanner = new Scanner(System.in);
		double taxRate = 20; //default tax rate
		String customChoice;
		

		do {
			System.out.println("Specify custom tax rate [Y|N]: ");
			customChoice = userScanner.nextLine();
		}while(!customChoice.equals("Y") && !customChoice.equals("N"));
		
		if(customChoice.equals("Y")) {
			do {
				System.out.println("Specify tax rate(%): ");
				taxRate = userScanner.nextDouble();
			}while(taxRate <= 0 || taxRate >= 100);
			
			System.out.printf("Tax rate applied: %%%.2f\n\n", taxRate);
		} else {
			System.out.printf("Assuming tax rate of: %%%.2f\n\n", taxRate);
		}

		
		userScanner.close(); //closing user scanner as its no longer needed
		
		FileReader filename = new FileReader("C:\\Users\\moham\\OneDrive\\Desktop\\rooms.txt"); //The directory for a file that the program would read *NOTE* the txt file should not have a empty line as its last line
		Scanner file_Scan = new Scanner(filename);
		
		String room_Type;
		int room_Bookings;
		double room_Price;
		
		double total_Income = 0; //declaring variables as 0 so that they can later be added onto
		double total_Tax = 0;
		
		while(file_Scan.hasNext()) { //while loop that is true as long as the scanner has not reached the end of the file
			room_Type = file_Scan.nextLine();
			room_Bookings = file_Scan.nextInt(); 
			room_Price = file_Scan.nextDouble();
			
			double income = room_Price * room_Bookings;
			double per_Room_tax = (taxRate/100) * income;
			
			total_Income += income; //accumulating the income and tax for every loop the program does
			total_Tax += per_Room_tax;
			
			System.out.printf("Room Type: %s, Bookings: %d, Room Price: £%.2f, Income: £%.2f, Tax: £%.2f\n", room_Type, room_Bookings, room_Price, income, per_Room_tax);

			if(file_Scan.hasNextLine()) {
				file_Scan.nextLine(); 	//skipping empty line and moving onto the next readable text in the file
				file_Scan.nextLine(); 
			}
			
		}
		
		System.out.printf("\nTotal Income: £%.2f\nTotal Tax: £%.2f", total_Income, total_Tax);
		file_Scan.close();
		

	}

}