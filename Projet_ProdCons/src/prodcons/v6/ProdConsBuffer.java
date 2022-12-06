package prodcons.v6;

import java.util.concurrent.Semaphore;

public class ProdConsBuffer implements IProdConsBuffer{

	int bufferSz;
	Message buffer[];
	int nfull;
	int nempty;
	int in = 0;
	int out = 0;
	Semaphore fifoP;
	Semaphore fifoC;
	//Semaphore rdv;
	
	public ProdConsBuffer(int bufferSz) {
		this.bufferSz = bufferSz;
		this.buffer = new Message[bufferSz];
		this.nfull = bufferSz;
		this.nempty = 0;
		this.fifoP = new Semaphore(1);
		this.fifoC = new Semaphore(1);
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
		fifoP.acquire();
		synchronized(this){
			while(putCounter < n){
				while(nfull == 0){
					wait();
				}
				buffer[in] = m;
				in = (in + 1) % bufferSz;
				nempty++;
				nfull--;
				putCounter++;
			}
		}
		notifyAll();
		fifoP.release();
	}

	public synchronized Message get() throws InterruptedException {
		return null;
	}
	
	public synchronized Message[] get(int k) throws InterruptedException{
		int getCounter = 0;
		Message msg;
		Message[] M = new Message[k];
		fifoC.acquire();
		synchronized(this) {
			while(getCounter < k) {
				while (nempty == 0) {
					wait();
				}
				msg = buffer[out];
				M[getCounter] = msg;
				out = (out + 1) % bufferSz;
				getCounter++;
				nempty--;
				nfull++;
			}
		}
		notifyAll();
		fifoC.release();
		return M;
	}
	
	public int nmsg() {
		return nempty;
	}

	
	public int totmsg() {
		return 0;
	}
}
