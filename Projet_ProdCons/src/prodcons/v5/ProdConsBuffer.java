package prodcons.v5;

import java.util.concurrent.Semaphore;

public class ProdConsBuffer implements IProdConsBuffer{

	int bufferSz;
	Message buffer[];
	int nfull;
	int nempty;
	int in = 0;
	int out = 0;
	Semaphore fifo;
	
	public ProdConsBuffer(int bufferSz) {
		this.bufferSz = bufferSz;
		this.buffer = new Message[bufferSz];
		this.nfull = bufferSz;
		this.nempty = 0;
		this.fifo = new Semaphore(1);
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

	
	public synchronized Message get() throws InterruptedException {
		while (nempty == 0) {
			wait();
		}
		Message msg = buffer[out];
		out = (out + 1) % bufferSz;
		nempty--;
		nfull++;
		notifyAll();
		return msg;	
		// return this.get(1)[0]
	}
	
	public Message[] get(int k) throws InterruptedException{
		int getCounter = 0;
		Message msg;
		Message[] M = new Message[k];
		fifo.acquire();
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
			notifyAll();
		}
		fifo.release();
		return M;
	}
	
	public int nmsg() {
		return nempty;
	}

	
	public int totmsg() {
		return 0;
	}
}
