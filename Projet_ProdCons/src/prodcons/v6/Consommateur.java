package prodcons.v6;

public class Consommateur extends Thread{
	ProdConsBuffer pcbuffer;
	int nplaces;
	Message[] msg;

	public Consommateur(ProdConsBuffer pcbuffer, int nplaces) {
		this.pcbuffer = pcbuffer;
		this.nplaces = nplaces;
		start();
	}
	
	public void run() {
		try {
			msg = pcbuffer.get(nplaces);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
