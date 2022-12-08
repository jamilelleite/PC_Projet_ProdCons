package prodcons.v6;

public class Message {
    String message;
    Rdv rdv;
    public Message(String message){
        this.message = message;
    }
    
    public void setRdv(Rdv rdv) {
    	this.rdv = rdv;
    }
    
    public Rdv getRdv() {
    	return this.rdv;
    }
}
