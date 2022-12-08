package prodcons.v6;

import java.util.concurrent.Semaphore;

public class Rdv {
	int nplaces;
	int counter;
	Semaphore p = new Semaphore(0);
	Semaphore mutex = new Semaphore(1);
	
	public Rdv(int nplaces) {
		this.nplaces = nplaces;
		this.counter = nplaces;
	}
	
	public void enter() throws InterruptedException {
		mutex.acquire();
		counter--;
		if(counter + 1 > 0) {
			mutex.release();
			p.acquire();
		} else {
			// p.release(nplaces-1);
			p.release(nplaces); // 1 producteur + (n-1) consommateurs
			mutex.release();
		}
	}
}
