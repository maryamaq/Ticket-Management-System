import java.util.*;
import java.text.*;
import java.time.temporal.ChronoUnit;
import java.time.*;


public class TcktFunctions {

	Scanner s = new Scanner(System.in);
	
	Ticket[] tickets = new Ticket[500];
	FunctionsAVL resolvedTickets = new FunctionsAVL();
	
	int count = 0, gCount = 0, sCount = 0, rCount = 0, delIdx = 0;
	boolean isPaid = false;
	
	int[] indexes = new int[tickets.length];
	boolean canUndo, canRedo;
	
	private static final int HIGH_EXPECTED_TIME = 5;
    private static final int MEDIUM_EXPECTED_TIME = 8;
    private static final int LOW_EXPECTED_TIME = 12;
    
	
	TcktFunctions() {
		
		for (int i=0; i<tickets.length; i++)
			tickets[i] = new Ticket();
	}
	
	// HELPER DATE INPUT METHOD:
	public void dateInput(Ticket ticket) {
		boolean validDate = false;
		while(!validDate) {
			try {
				System.out.println("Enter date: [It should be in the format of MM/dd/yyyy]");
				String dateStr = s.next();
				ticket.setDate(dateStr);
				validDate = true;
			}
			catch (IllegalArgumentException e) {
				System.out.println(e.getMessage());
			}
		}
	}
	
	// HELPER MEMBERSHIP TYPE INPUT METHOD:
	public void priorityTypeInput(Ticket ticket) {
		boolean validType = false;
		while(!validType) {
			try{
				System.out.println("Enter ticket priority type: [High, Medium or Low]");
				Ticket.PriorityType t = Ticket.PriorityType.valueOf(s.next().toUpperCase());
				ticket.setPriority(t);
				validType = true;
			}
			catch (IllegalArgumentException e) {
				System.out.println("Invalid type.");
			}
		}
	}
	
	// GENERATION OF A TICKET:
	public void generateTicket() {
		
		Ticket ticket = new Ticket();
		
		dateInput(ticket);
		
		System.out.println("Enter description: ");
		String desc = s.next();
		ticket.setDescription(desc);
		s.nextLine();
		
		priorityTypeInput(ticket);
		
		ticket.setPurchaseTime(LocalTime.now().truncatedTo(ChronoUnit.SECONDS));
		
		switch(ticket.getPriority()) {
		case HIGH:  // HIGHEST PRIORITY
			ticket.setPrice(500.0);
			ticket.setExpectedTime(LocalTime.now().truncatedTo(ChronoUnit.SECONDS).plusHours(HIGH_EXPECTED_TIME)); 
			gCount ++;
			break;
		case MEDIUM:  // MEDIUM PRIORITY
			ticket.setPrice(1000.0);
			ticket.setExpectedTime(LocalTime.now().truncatedTo(ChronoUnit.SECONDS).plusHours(MEDIUM_EXPECTED_TIME));
			sCount ++;
			break;
		case LOW:  // LOWEST PRIORITY
			ticket.setPrice(3000.0);
			ticket.setExpectedTime(LocalTime.now().truncatedTo(ChronoUnit.SECONDS).plusHours(LOW_EXPECTED_TIME));
			rCount ++;
			break;	
		default:
			System.out.println("Invalid ticket type!");
		}
		
		enqueueTicket(ticket);
		System.out.println("\n");
	}
	
	// SORTING THE QUEUE ACCORDING TO THE PRIORITY OF TICKET'S MEMBERSHIP TYPE AND PURCHASE TIME:
	public void prioritizeSwap() {
		if (count>=2) 
			for (int i=count-1; i>=0; i--) 
				for (int j=i-1; j>=0; j--) 
					if ((tickets[i].getPriority().ordinal() == tickets[j].getPriority().ordinal()) && (tickets[i].getPurchaseTime().isBefore(tickets[j].getPurchaseTime()))) {
						Ticket t = tickets[i];
						tickets[i] = tickets[j];
						tickets[j] = t;
					}
					else if (tickets[i].getPriority().ordinal() < tickets[j].getPriority().ordinal()) {
						Ticket t = tickets[i];
						tickets[i] = tickets[j];
						tickets[j] = t;
					}
	}
				
	// ENQUEUING THE GENERATED TICKET:
	public void enqueueTicket(Ticket ticket) {
		
		if (count == tickets.length)
			System.out.println("Tickets are sold out.");
		else {
			if (ticket!=null) {	
				ticket.setStatus(Ticket.TicketStatus.IN_PROGRESS);
				tickets[count] = ticket;
				count++;
				prioritizeSwap();
			}
		}
		serializingTicket();
	}

	// CHECK PAYEMENT STATUS OF THE SPECIFIED TICKET:
	public boolean checkPaymentStatus(Ticket ticket) {
		boolean flag = false;
		
		if (ticket.getPaymentStatus()==null) 
			System.out.println("NO PAYMENT STATUS");
		else if (ticket.getPaymentStatus().equalsIgnoreCase("UNPAID")) { 
			System.out.println(ticket.getPaymentStatus());
      		flag = false;
		}
		else if (ticket.getPaymentStatus().equalsIgnoreCase("PAID")) {
			System.out.println(ticket.getPaymentStatus());
			flag = true;
		}
		
		return flag;
		
	}
	
	// MARK PAYMENT STATUS TO THE SPECIFIED TICKET:
	public void markPaymentStatus(String mark, Ticket ticket) {
		ticket.setPaymentStatus(mark);
	}
	
	// SEARCH TICKET BY SERIAL NUMBER:
	public Ticket search(long number) {
		int p = 0;
		boolean exists = false;
		for (int i=0; i<count; i++)
			if (tickets[i].getSerialNo() == number) {
				exists = true;
				p = i;
				break;
			}
		if (exists == true)
			return tickets[p];
		else
			return null;
	}
	
	// UPDATE TICKET DETAILS:
	public void updateTicket() {
		System.out.println("Enter the serial number of the ticket you want to update: ");
		int seriaN = s.nextInt();
		
		Ticket ticket = search(seriaN);
		s.nextLine();
		
		System.out.println("Specify what you want to update: [Description, Date, Price, Payment Status] ");
		String choice = s.nextLine();
		
		if (ticket!=null) {
			switch(choice.toLowerCase()) {
			case "description":
				System.out.println("Enter the updated description: ");
				String des = s.next();
				ticket.setDescription(des);
				System.out.println("Description is updated.");
				break;
			case "price":
				System.out.println("Enter the updated price: ");
				double p = s.nextDouble();
				ticket.setPrice(p);
				System.out.println("Price is updated.");
				break;
			case "payment status":
				System.out.println("Enter the updated payment status: [PAID or UNPAID]");
				String t = s.next();
				ticket.setPaymentStatus(t.toUpperCase());
				System.out.println("Payment status is updated.");
				break;
				
			}					
		}
		else {
			System.out.println("Ticket to be updated not found.");
		}
		System.out.println("\n");
	}
	
	// SIMPLY DEQUEUING THE TICKET:
	public Ticket dequeueTicket() {
		
		Ticket first = tickets[0];
		
		if (count == 0)
			System.out.println("Queue is empty. Nothing to delete!");
		
		else if (first.getPaymentStatus().equals("UNPAID")) {
			System.out.println("Ticket is not paid. Cannot dequeue.");
			first = null;
		}
		else {
			System.out.println("Ticket is paid. Dequeuing 1st ticket.");
			first.setStatus(Ticket.TicketStatus.RESOLVED); 
			for (int i=0; i<count-1; i++) {
				tickets[i] = tickets[i+1];
			}
			tickets[count] = new Ticket();
			count--;	
		}
		return first;	
	}
	
	// UNDOING THE DEQUEUED TICKET SAVED IN AVL:
    public void undoTicket(Ticket ticket) {
    	
//    	System.out.println("Input ticket: "+ticket);
        Ticket restoredTicket = resolvedTickets.undo(ticket);
        System.out.println("Restored ticket: "+restoredTicket);
        
        if (restoredTicket != null) {
            enqueueTicket(restoredTicket);
            System.out.println("Ticket successfully restored and re-enqueued.");
        } 
        else {
            System.out.println("Ticket not found in the closed tickets.");
        }
    }
	
	// SERIALIZING THE TICKETS RANDOMLY:
	public void serializingTicket() {
		Random random = new Random();
		for (int i=0; i<count; i++) {
//			int serialNumber = random.nextInt(500);
//			String serialNumberStr = String.format("%03d", serialNumber);
			tickets[i].setSerialNo(i+1);
		}
	}
	
	// DISPLAYING ENQUEUED TICKETS:
	public void display() {
		
		System.out.println("\nCompiling Tickets.............");
		
		if (count==0)
			System.out.println("There are no tickets available!");
		else {
			for (int i=0; i<count; i++) {
				System.out.println("SerialNo: ["+tickets[i].getSerialNo()+"] Priority: ["+tickets[i].getPriority()+"] Price: ["+tickets[i].getPrice()
						+"] Purchase Time: ["+tickets[i].getPurchaseTime()+"] Expected Time: ["+tickets[i].getExpectedTime()+"] Status: ["+tickets[i].getStatus()
						+"] Payment Status: ["+tickets[i].getPaymentStatus()+"]");
			}
			System.out.println();
		}
		System.out.println("\n");
		
	}
	
}














	

	


	
	

