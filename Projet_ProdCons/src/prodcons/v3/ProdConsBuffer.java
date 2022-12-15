package prodcons.v3;

import java.util.concurrent.Semaphore;

public class ProdConsBuffer implements IProdConsBuffer{

	int bufferSz;
	Message buffer[];
	Semaphore notEmpty;
	Semaphore notFull;
	int in = 0;
	int out = 0;
	int prodTime; 
	int consTime; 
	int nProd;
	
	public ProdConsBuffer(int bufferSz, int prodTime, int consTime, int nProd) {
		this.bufferSz = bufferSz;
		this.buffer = new Message[bufferSz];
		notEmpty = new Semaphore(0);
		notFull = new Semaphore(bufferSz);
		this.prodTime = prodTime;
		this.consTime = consTime;
		this.nProd = nProd;
	}

	
	public void put(Message msg) throws InterruptedException {
		notFull.acquire();
		synchronized(this){
			System.out.println(msg.message + " is coming in and the number of active producers per messages is " + nProd);
			Thread.sleep(prodTime);
			buffer[in] = msg;
			in = (in + 1) % bufferSz;
			notifyAll();
		}
		notEmpty.release();
	}

	
	public Message get() throws InterruptedException {
		notEmpty.acquire();
		Message msg;
		synchronized(this){
			Thread.sleep(consTime);
			msg = buffer[out];
			System.out.println(msg.message + " is going out");
			out = (out + 1) % bufferSz;
			nProd--;
			notifyAll();
		}
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
