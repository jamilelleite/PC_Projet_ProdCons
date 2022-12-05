package prodcons.v3;

import java.util.concurrent.Semaphore;

public class ProdConsBuffer implements IProdConsBuffer{

	int bufferSz;
	Message buffer[];
	Semaphore notEmpty;
	Semaphore notFull;
	Semaphore Mutex1, Mutex2;
	int in = 0;
	int out = 0;
	int nempty = 0;
	int totmsg = 0;
	int prodDelay;
	int consDelay;
	
	public ProdConsBuffer(int bufferSz, int prodDelay, int consDelay) {
		this.bufferSz = bufferSz;
		this.buffer = new Message[bufferSz];
		notEmpty = new Semaphore(bufferSz, true);
		notFull = new Semaphore(0, true);
		Mutex1 = new Semaphore(1, true);
		Mutex2 = new Semaphore(1, true);
		this.prodDelay = prodDelay;
		this.consDelay = consDelay;
	}

	
	public void put(Message msg) throws InterruptedException {
		notEmpty.acquire();
		Mutex1.acquire();
		Thread.sleep(prodDelay);
		buffer[in] = msg;
		in = (in + 1) % bufferSz;
		nempty++;
		totmsg++;
		Mutex1.release();
		notFull.release();
	}

	
	public Message get() throws InterruptedException {
		notFull.acquire();
		Mutex2.acquire();
		Thread.sleep(consDelay);
		Message msg = buffer[out];
		buffer[out] = null;
		out = (out + 1) % bufferSz;
		nempty--;
		Mutex2.release();
		notEmpty.release();
		return msg;
	}

	
	public int nmsg() {
		return nempty;
	}

	
	public int totmsg() {
		return totmsg;
	}
}
