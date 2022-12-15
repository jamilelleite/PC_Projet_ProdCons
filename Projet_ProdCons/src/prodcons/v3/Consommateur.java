package prodcons.v3;

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
				if(pcbuffer.nmsg() == 0 && pcbuffer.nProd == 0)
					System.exit(0);
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
