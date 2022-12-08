package prodcons.v6;

public class Producteur extends Thread{
	ProdConsBuffer pcbuffer;
	Message msg;
	int nplaces;

	public Producteur(ProdConsBuffer pcbuffer, Message msg, int nplaces) {
		this.pcbuffer = pcbuffer;
		this.msg = msg;
		this.nplaces = nplaces;
		start();
	}
	
	public void run() {
		try {
			pcbuffer.put(msg, nplaces);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
