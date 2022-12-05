package prodcons.v1;

public class ProdConsBuffer implements IProdConsBuffer{

	int bufferSz;
	Message buffer[];
	int nfull;
	int nempty;
	int in = 0;
	int out = 0;
	int prodDelay;
	int consDelay;
	int totmsgs = 0;
	
	public ProdConsBuffer(int bufferSz, int prodDelay, int consDelay) {
		this.bufferSz = bufferSz;
		this.buffer = new Message[bufferSz];
		this.nfull = bufferSz;
		this.nempty = 0;
		this.prodDelay = prodDelay;
		this.consDelay = consDelay;
	}

	
	public synchronized void put(Message msg) throws InterruptedException {
		while (nfull == 0) {
			wait();
		}
		Thread.sleep(prodDelay);
		buffer[in] = msg;
		in = (in + 1) % bufferSz;
		nempty++;
		nfull--;
		totmsgs++;
		notifyAll();		
	}

	
	public synchronized Message get() throws InterruptedException {
		while (nempty == 0) {
			wait();
		}
		Thread.sleep(consDelay);
		Message msg = buffer[out];
		buffer[out] = null;
		out = (out + 1) % bufferSz;
		nempty--;
		nfull++;
		notifyAll();
		return msg;
	}

	
	public int nmsg() {
		return nempty;
	}

	
	public int totmsg() {
		return totmsgs;
	}
}
