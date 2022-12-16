package prodcons.v6;

public class Consommateur extends Thread{
	ProdConsBuffer pcbuffer;
	Message msg;

	public Consommateur(ProdConsBuffer pcbuffer) {
		this.pcbuffer = pcbuffer;
		start();
	}
	
	public void run() {
		try {
			while(true) {
				msg = pcbuffer.get();
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
