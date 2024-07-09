
public class TicketAVL {

	Ticket ticket;
	TicketAVL left, right;
	int height;
	
	TicketAVL(){
//		this.ticket = ticket;
		height = 1;
	}
	
	public String toString() {
		return "Ticket serial no: "+ticket.getSerialNo()+", Node height: "+height;
	}
}
