package prodcons.v6;

public class Producteur extends Thread{
	ProdConsBuffer pcbuffer;
	Message msg;

	public Producteur(ProdConsBuffer pcbuffer, Message msg) {
		this.pcbuffer = pcbuffer;
		this.msg = msg;
		start();
	}
	
	public void run() {
		try {
			pcbuffer.put(msg,5);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
