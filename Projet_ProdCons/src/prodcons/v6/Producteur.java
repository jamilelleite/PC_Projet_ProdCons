package prodcons.v6;

public class Producteur extends Thread{
	ProdConsBuffer pcbuffer;
	Message msg;
	int minProd;
	int maxProd;

	public Producteur(ProdConsBuffer pcbuffer, int minProd, int maxProd) {
		this.pcbuffer = pcbuffer;
		this.minProd = minProd;
		this.maxProd = maxProd;
		start();
	}
	
	public void run() {
		try {
			int nmsgProd = (int) ((Math.random()*(maxProd-minProd))+minProd);
			for(int i = 0; i< nmsgProd; i++) {
				this.msg = new Message("Producer id: " + this.getId() + "  message number " + i);
				pcbuffer.put(msg);
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
