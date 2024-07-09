import java.time.*;

public class FunctionsAVL {

	TicketAVL root;
	TicketAVL t = new TicketAVL();
	
	// HEIGHT OF THE GIVEN NODE
	private int height(TicketAVL node) {
        if (node == null)
            return 0;
        return node.height;
    }

    private int max(int a, int b) {
        return (a > b) ? a : b;
    }

    // RIGHT ROTATION OF THE GIVEN NODE
    private TicketAVL rightRotate(TicketAVL y) {
    	TicketAVL x = y.left;
    	TicketAVL T2 = x.right;

        x.right = y;
        y.left = T2;

        y.height = max(height(y.left), height(y.right)) + 1;
        x.height = max(height(x.left), height(x.right)) + 1;

        return x;
    }

    // LEFT ROTATION OF THE GIVEN NODE
    private TicketAVL leftRotate(TicketAVL x) {
    	TicketAVL y = x.right;
    	TicketAVL T2 = y.left;

        y.left = x;
        x.right = T2;

        x.height = max(height(x.left), height(x.right)) + 1;
        y.height = max(height(y.left), height(y.right)) + 1;

        return y;
    }

    // BALANCE OF THE GIVEN NODE
    private int getBalance(TicketAVL node) {
        if (node == null)
            return 0;
        return height(node.left) - height(node.right);
    }
    
    // INSERTION OF THE RESOLVED TICKET
    public void insert(Ticket ticket) {
        root = insert(root, ticket);
        System.out.println("Root of AVL: "+root);
    }

    private TicketAVL insert(TicketAVL node, Ticket ticket) {  // [Node = 1st entry of AVL] [Ticket = resolved ticket.]
    	
    	if (node == null) {
        	TicketAVL t = new TicketAVL();  // making an object of the node class and assigning the given ticket to the 1st entry.
        	t.ticket = ticket;
        	return t;
        }

    	else {  // 1st entry is not null and hence a resolved ticket is already entered.
    		
    		// Checking if the newly entered ticket purchase time is earlier than that of the previously entered ticket purchase time:
    		if (ticket.getPriority().getValue() < node.ticket.getPriority().getValue()) { 
    			//if TRUE then place the newly arrived ticket on the left side of the previous one.
    			node.left = insert(node.left, ticket);
    		
    		}
    		
    		// ELSE placing the the newly arrived ticket on the right side of the previous one.
    		else if (ticket.getPriority().getValue() > node.ticket.getPriority().getValue())
    			node.right = insert(node.right, ticket);
    		
    		else
    			return node;  // Returning the same node as duplicate node is not allowed.  		

    		
    		// Calculating the height of the previous node after the new insertion.
	        node.height = 1 + max(height(node.left), height(node.right));  // HEight = 2
	
	        return balanceNode(node, ticket);
	        
    	}
    }
    
    // CHECKING AND BALANCING THE NODE IF NOT BALANCED: 
    public TicketAVL balanceNode(TicketAVL node, Ticket ticket) {
		
        int balance = getBalance(node);

        // Checks if the node is unbalanced: TRUE then apply rotations to balance the node
        if (balance>1||balance<-1) {
        	
        	// LEFT LEFT 
        	if (balance > 1 && (ticket.getPriority().getValue() < node.left.ticket.getPriority().getValue()))
        		return rightRotate(node);
        	
        	// RIGHT RIGHT
           	else if (balance < -1 && (ticket.getPriority().getValue() > node.right.ticket.getPriority().getValue()))	
        		return leftRotate(node);
        	
        	// LEFT RGHT     	
        	else if (balance > 1 && (ticket.getPriority().getValue() > node.left.ticket.getPriority().getValue())) {	
        		node.left = leftRotate(node.left);
        		return rightRotate(node);
        	}
        	
        	// RIGHT LEFT
        	else if (balance < -1 && (ticket.getPriority().getValue() < node.right.ticket.getPriority().getValue())) {
        		node.right = rightRotate(node.right);
        		return leftRotate(node);
        	}
        }
        return node;
	}
    	
    // UNDOING THE DELETED TICKET
    public Ticket undo(Ticket ticket) {
        root = delete(root, ticket);
        return ticket;
    }

    // DELETION OF THE GIVEN NODE
    public void delete(Ticket ticket) {
        root = delete(root, ticket);
    }
    
    private TicketAVL delete(TicketAVL root, Ticket ticket) {
        if (root == null)
            return root;

        if (ticket.getPriority().getValue() < root.ticket.getPriority().getValue()) {
            root.left = delete(root.left, ticket);
            System.out.println("in 1st if");
        }
        
        else if (ticket.getPriority().getValue() > root.ticket.getPriority().getValue()) {
            root.right = delete(root.right, ticket);
            System.out.println("in 2nd if");
        }
        
        else {
        	System.out.println("in else");
        	// Case 1: Node with only one child or no child
            if ((root.left == null) || (root.right == null)) {
            	TicketAVL temp = null;
            	
                if (temp == root.left)
                    temp = root.right;  // traversing the right side of AVL if left side is null
                else
                    temp = root.left;   // traversing the left side of AVL if left side is not null

                if (temp == null) {   // after traversing both sides, if temp is still null, there is only one node = root
                    temp = root;
                    root = null;
                } 
                else
                    root = temp;
            } 
            
         // Case 2: Node with two children
            else {
            	
            	TicketAVL temp = minValueNode(root.right);
                root.ticket = temp.ticket;
                root.right = delete(root.right, temp.ticket);
            }
        }

        return balanceNode(root, ticket);
        
    }

    private TicketAVL minValueNode(TicketAVL node) {
    	TicketAVL current = node;
        while (current.left != null)
            current = current.left;

        return current;
    }

    public void displayAVL(TicketAVL root) {
    	if (root!= null) {
    		System.out.println(root);
    		displayAVL(root.left);
    		displayAVL(root.right);
    	}
    	else {
    		System.out.println("Resolved Tickets tree is empty!");
    	}
    }
}
