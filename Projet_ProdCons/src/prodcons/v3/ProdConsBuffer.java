package prodcons.v3;

import java.util.concurrent.Semaphore;

public class ProdConsBuffer implements IProdConsBuffer{

	int bufferSz;
	Message buffer[];
	Semaphore notEmpty;
	Semaphore notFull;
	int in = 0;
	int out = 0;
	
	public ProdConsBuffer(int bufferSz) {
		this.bufferSz = bufferSz;
		this.buffer = new Message[bufferSz];
		notEmpty = new Semaphore(bufferSz);
		notFull = new Semaphore(0);
	}

	
	public void put(Message msg) throws InterruptedException {
		notFull.acquire();
		synchronized(this){
			buffer[in] = msg;
			in = (in + 1) % bufferSz;
		}
		notifyAll();
		notEmpty.release();
	}

	
	public Message get() throws InterruptedException {
		notEmpty.acquire();
		Message msg;
		synchronized(this){
			msg = buffer[out];
			out = (out + 1) % bufferSz;
		}
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
