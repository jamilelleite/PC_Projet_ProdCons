package prodcons.v3;

public class Producteur extends Thread{
	ProdConsBuffer pcbuffer;
	Message msg;
	int nmsgProd;
	
	public Producteur(ProdConsBuffer pcbuffer, int nmsgProd) {
		this.pcbuffer = pcbuffer;
		this.nmsgProd = nmsgProd;
		start();
	}
	
	public void run() {
		try {
			for(int i = 0; i< nmsgProd; i++) {
				this.msg = new Message("Thread " + this.getId() + " Message number " + i + " of " + (nmsgProd-1));
				pcbuffer.put(msg);
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
