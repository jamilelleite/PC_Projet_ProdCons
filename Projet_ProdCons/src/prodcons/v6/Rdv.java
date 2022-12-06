package prodcons.v6;

import java.util.concurrent.Semaphore;

public class Rdv {
	int nplaces;
	Semaphore p = new Semaphore(0);
	Semaphore mutex = new Semaphore(1);
	
	public Rdv(int nplaces) {
		this.nplaces = nplaces;
	}
	
	public void enter() throws InterruptedException {
		mutex.acquire();
		nplaces--;
		if(nplaces > 0) {
			mutex.release();
			p.acquire();
		} else {
			p.release(nplaces-1);
			mutex.release();
		}
	}
}
