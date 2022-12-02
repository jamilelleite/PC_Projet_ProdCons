package prodcons.v3;

import java.util.concurrent.Semaphore;

public class ProdConsBuffer implements IProdConsBuffer{

	int bufferSz;
	Message buffer[];
	int nfull;
	int nempty;
	Semaphore notEmpty;
	Semaphore notFull;
	Semaphore Mutex;
	int in = 0;
	int out = 0;
	
	public ProdConsBuffer(int bufferSz) {
		this.bufferSz = bufferSz;
		this.buffer = new Message[bufferSz];
		this.nfull = bufferSz;
		this.nempty = 0;
		notEmpty = new Semaphore(nempty);
		notFull = new Semaphore(nfull);
		Mutex = new Semaphore(1);
	}

	
	public void put(Message msg) throws InterruptedException {
		while (nfull == 0) {
			wait();
		}
		notFull.acquire();
		Mutex.acquire();
		buffer[in] = msg;
		in = (in + 1) % bufferSz;
		nempty++;
		nfull--;
		Mutex.release();
		notifyAll();
		notEmpty.release();
	}

	
	public Message get() throws InterruptedException {
		while (nempty == 0) {
			wait();
		}
		notEmpty.acquire();
		Mutex.acquire();
		Message msg = buffer[out];
		out = (out + 1) % bufferSz;
		nempty--;
		nfull++;
		Mutex.release();
		notifyAll();
		notFull.release();
		return msg;
	}

	
	public int nmsg() {
		return nempty;
	}

	
	public int totmsg() {
		return 0;
	}
}
