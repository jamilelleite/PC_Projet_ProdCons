package prodcons;

import java.util.concurrent.Semaphore;

public class ProdConsBuffer implements IProdConsBuffer{

	int bufferSz;
	Message buffer[];
	Semaphore notFull;
	Semaphore notEmpty;
	Semaphore mutex;
	int in = 0;
	int out = 0;
	
	public ProdConsBuffer(int bufferSz) {
		this.bufferSz = bufferSz;
		buffer = new Message[bufferSz];
		notFull = new Semaphore(bufferSz);
		notEmpty = new Semaphore(0);
		mutex = new Semaphore(1);
	}

	public void put(Message msg) throws InterruptedException {
		notFull.acquire();
		mutex.acquire();
		buffer[in] = msg;
		in = in + 1 % bufferSz;
		mutex.release();
		notEmpty.release();
	}

	public Message get() throws InterruptedException {
		notEmpty.acquire();
		mutex.acquire();
		Message msg = buffer[out];
		out = out + 1 % bufferSz;
		mutex.release();
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
