import java.text.*;
import java.time.*;
import java.util.Date;

public class Ticket {
	
	public enum PriorityType {
		
		HIGH(1100),
	    MEDIUM(1050),
	    LOW(1000);
		
		private final int value;
		
		PriorityType(int value){
			this.value = value;
		}
		
		public int getValue() {
			return value;
		}
	}

	public enum TicketStatus {
        NEW,
        IN_PROGRESS,
        RESOLVED,
        CLOSED
    }


	private int serialNo;
	private Date date;
	private String description;
	private double price;
	private PriorityType priority;
	private TicketStatus status;
	private LocalTime expectedTime, purchaseTime;
	private String paymentStatus;

	Ticket(){
		this.status = TicketStatus.NEW;
	}
	
	// GETTER SETTER FOR SERIAL NO
	public void setSerialNo(int serialNo) {
        this.serialNo = serialNo;
    }

    public int getSerialNo() {
        return this.serialNo;
    }
    
    // GETTER SETTER FOR DATE
    public void setDate(String dateStr) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
		try {
			this.date = dateFormat.parse(dateStr);
		}
		catch (ParseException e) {
			throw new IllegalArgumentException("Invalid date format", e);
		}
    }

    public Date getDate() {
        return this.date;
    }
    
    // GETTER SETTER FOR DESCRIPTION
    public void setDescription(String description) {
        this.description = description;
    }

    public String getDescription() {
        return this.description;
    }
    
    // GETTER SETTER FOR PRICE
    public void setPrice(double price) {
        this.price = price;
    }

    public double getPrice() {
        return this.price;
    }
    
    // GETTER SETTER FOR STATUS
    public void setStatus(TicketStatus status) {
        this.status = status;
    }

    public TicketStatus getStatus() {
        return this.status;
    }
    
    // GETTER SETTER FOR PURCHASE TIME
    public void setPurchaseTime(LocalTime purchaseTime) {
        this.purchaseTime = purchaseTime;
    }

    public LocalTime getPurchaseTime() {
        return this.purchaseTime;
    }
    
    // GETTER SETTER FOR EXPECTED TIME
    public void setExpectedTime(LocalTime expectedTime) {
        this.expectedTime = expectedTime;
    }

    public LocalTime getExpectedTime() {
        return this.expectedTime;
    }
    
    // GETTER SETTER FOR PRIORITY
    public void setPriority(PriorityType priority) {
        this.priority = priority;
    }

    public PriorityType getPriority() {
        return this.priority;
    }
    
    // GETTER SETTER FOR PAYMENT STATUS
    public void setPaymentStatus(String paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public String getPaymentStatus() {
        return this.paymentStatus;
    }
	
	 public String toString() {
		return  "SerialNo: ["+serialNo+"] Priority: ["+priority+"] Price: ["+price+"] Purchase Time: ["+purchaseTime+"] Expected Time: ["+expectedTime
				+"] Ticket Status: ["+status+/*"] Payment Status: ["+type+*/"] Payment Status: ["+paymentStatus+"]";
//	        return "Ticket{date='" + date + "', description='" + description + "', type='" + type + "', priority=" + priority + ", price=" + price + "}";
	    }
	 

}
