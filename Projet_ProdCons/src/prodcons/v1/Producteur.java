package prodcons.v1;

public class Producteur extends Thread{
	ProdConsBuffer pcbuffer;
	Message msg;
	int nmsgProd;

	public Producteur(ProdConsBuffer pcbuffer, Message msg, int nmsgProd) {
		this.pcbuffer = pcbuffer;
		this.msg = msg;
		this.nmsgProd = nmsgProd;
		start();
	}
	
	public void run() {
		try {
			for(int i = 0; i< nmsgProd; i++)
				pcbuffer.put(msg);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
