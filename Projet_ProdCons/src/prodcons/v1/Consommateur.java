package prodcons.v1;

public class Consommateur extends Thread{
	ProdConsBuffer pcbuffer;
	Message msg;
	int delay;
	int nProd;

	public Consommateur(ProdConsBuffer pcbuffer) {
		this.pcbuffer = pcbuffer;
		this.delay = delay;
		this.nProd = pcbuffer.nProd;
		start();
	}
	
	public void run() {
		try {
			while(true) {
				msg = pcbuffer.get(); //makes a consummer not die during it's execution. We'll have to figure out the stopping condition
				nProd--;
				if(pcbuffer.nmsg() == 0 && nProd == 0) //When there's no message in the buffer
					System.exit(0);  // kill all hahaha
			}	
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
