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
	
	public ProdConsBuffer(int bufferSz) {
		this.bufferSz = bufferSz;
		this.buffer = new Message[bufferSz];
		notEmpty = new Semaphore(bufferSz);
		notFull = new Semaphore(0);
		Mutex1 = new Semaphore(1);
		Mutex2 = new Semaphore(1);
	}

	
	public void put(Message msg) throws InterruptedException {
		notFull.acquire();
		Mutex1.acquire();
		buffer[in] = msg;
		in = (in + 1) % bufferSz;
		Mutex1.release();
		notifyAll();
		notEmpty.release();
	}

	
	public Message get() throws InterruptedException {
		notEmpty.acquire();
		Mutex2.acquire();
		Message msg = buffer[out];
		out = (out + 1) % bufferSz;
		Mutex2.release();
		notifyAll();
		notFull.release();
		return msg;
	}

	
	public int nmsg() {
		return 0;
	}

	
	public int totmsg() {
		return 0;
	}
}
