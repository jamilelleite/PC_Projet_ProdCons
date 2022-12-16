package prodcons.v6;

public class Consommateur extends Thread{
	ProdConsBuffer pcbuffer;
	int minCons;
	int maxCons;
	Message[] msg;

	public Consommateur(ProdConsBuffer pcbuffer, int minCons, int maxCons) {
		this.pcbuffer = pcbuffer;
		this.minCons = minCons;
		this.maxCons = maxCons;
		start();
	}
	
	public void run() {
		try {
			int nmsgCons = (int) ((Math.random()*(maxCons-minCons))+minCons);
			msg = pcbuffer.get(nmsgCons);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
