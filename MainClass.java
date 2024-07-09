import java.util.InputMismatchException;
import java.util.Scanner;

public class MainClass {

	public static void main(String[] args) {
		Scanner s = new Scanner(System.in);
	
		TcktFunctions f = new TcktFunctions();
		FunctionsAVL resolvedTickets = new FunctionsAVL();
		Ticket dequeuedTicket = new Ticket();
			
		int input;
		

		System.out.println("TICKET MANAGMENT SYSTEM\n");
		System.out.println("_______________________\n");
		do {
			System.out.println();
			System.out.println("{FUNCTIONALITIES:}\n");
			System.out.println("Enter 1 to CREATE A TICKET: ");  // WORKING
			System.out.println("Enter 2 to SEE TICKET DETAILS: ");  // WORKING
			System.out.println("Enter 3 to CHECK PAYMENT STATUS: ");  // WORKING
			System.out.println("Enter 4 to SEARCH A TICKET: ");  // WORKING
			System.out.println("Enter 5 to UPDATE INFORMATION OF A TICKET: ");  // WORKING
			System.out.println("Enter 6 to RESOLVE {DEQUEUE} A TICKET: ");  // WORKING
			System.out.println("Enter 7 to UNDO A RESOLVED TICKET: ");  // WORKING 
			System.out.println("Enter 0 to exit: ");
			System.out.println("_______________________\n");
				input = s.nextInt();
				switch (input) {
				case 1:
					f.generateTicket();
					System.out.println();
					break;
					
				case 2:
					f.display();
					break;
					
				case 3:
					System.out.println("Enter the serial number of the ticket you want to check: ");
					int serialNo = s.nextInt();
					Ticket found = f.search(serialNo);
					System.out.println("Founded ticket: "+found);
					if (found != null) {
						boolean status = f.checkPaymentStatus(found);
						if (status == true)
							f.markPaymentStatus("PAID", found);
						else
							f.markPaymentStatus("UNPAID", found);		
					}
					else {
						System.out.println("Ticket not found.");
					}
					break;
					
				case 4:
					System.out.println("Enter the serial number of the ticket you want to search: ");
					int serialN = s.nextInt();
					Ticket founded = f.search(serialN);
					if (founded != null)
						System.out.println("Founded ticket = {"+founded+"}");
					break;
					
				case 5:
					f.updateTicket();
					break;
					
				case 6:
					dequeuedTicket = f.dequeueTicket();
	                try {
	                	if (dequeuedTicket != null) {
	                		resolvedTickets.insert(dequeuedTicket);
	                    	System.out.println("Dequeued ticket: " + dequeuedTicket);
	                	}
	                }
	                catch (NullPointerException n) {
	                	System.out.println("Payment status is null. Update the payment status of the ticket first.");
	                }
					break;
					
				case 7:
					f.undoTicket(dequeuedTicket);
					break;
					
				case 0:
					System.out.println("You've exited the program!");
					break;
				}
		}
		while (input<9&&input>=0);
	}

}

