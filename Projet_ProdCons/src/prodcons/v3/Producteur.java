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
				this.msg = new Message(this.getId() + " Message number " + i);
				pcbuffer.put(msg);
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
