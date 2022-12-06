package prodcons.v6;

import java.util.concurrent.Semaphore;

public class ProdConsBuffer implements IProdConsBuffer{

	int bufferSz;
	Message buffer[];
	int nfull;
	int nempty;
	int in = 0;
	int out = 0;
	Semaphore fifo;
	//Semaphore rdv;
	
	public ProdConsBuffer(int bufferSz) {
		this.bufferSz = bufferSz;
		this.buffer = new Message[bufferSz];
		this.nfull = bufferSz;
		this.nempty = 0;
		Semaphore fifo = new Semaphore(1);
	}

	
	public synchronized void put(Message msg) throws InterruptedException {
		while (nfull == 0) {
			wait();
		}
		buffer[in] = msg;
		in = (in + 1) % bufferSz;
		nempty++;
		nfull--;
		notifyAll();		
	}
	
	public void put (Message m, int n) throws InterruptedException{
		int putCounter = 0;
		//rdv = new Semaphore(n);
		fifo.acquire();
		synchronized(this){
			while(putCounter < n){
				while(nfull == 0){
					wait();
				}
				buffer[in] = msg;
				in = (in + 1) % bufferSz;
				nempty++;
				nfull--;
				putCounter++;
			}
		}
		notifyAll;
		fifo.release();
	}

	public synchronized Message get() throws InterruptedException {

	}
	
	public synchronized Message[] get(int k) throws InterruptedException{

	}
	
	public int nmsg() {
		return nempty;
	}

	
	public int totmsg() {
		return 0;
	}
}
