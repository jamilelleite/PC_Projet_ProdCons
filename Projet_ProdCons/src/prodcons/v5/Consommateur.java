package prodcons.v5;

public class Consommateur extends Thread{
	ProdConsBuffer pcbuffer;
	Message[] msg;

	public Consommateur(ProdConsBuffer pcbuffer) {
		this.pcbuffer = pcbuffer;
		start();
	}
	
	public void run() {
		try {
			while(true) {
				msg = pcbuffer.get(3);
				if(pcbuffer.nmsg() == 0)
					System.exit(0);
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
