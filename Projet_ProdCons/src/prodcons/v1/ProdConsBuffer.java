package prodcons.v1;

public class ProdConsBuffer implements IProdConsBuffer{

	int bufferSz;
	Message buffer[];
	int nfull;
	int nempty;
	int prodTime;
	int consTime;
	int nProd;
	int in = 0;
	int out = 0;
	int totmsg = 0;
	
	public ProdConsBuffer(int bufferSz, int prodTime, int consTime, int nProd) {
		this.bufferSz = bufferSz;
		this.buffer = new Message[bufferSz];
		this.consTime = consTime;
		this.prodTime = prodTime;
		this.nProd = nProd;
		this.nfull = bufferSz;
		this.nempty = 0;
	}

	
	public synchronized void put(Message msg) throws InterruptedException {
		while (nfull == 0) {
			wait();
		}
		Thread.sleep(prodTime);
		buffer[in] = msg;
		in = (in + 1) % bufferSz;
		nempty++;
		nfull--;
		totmsg++;
		nProd--;
		notifyAll();		
	}

	
	public synchronized Message get() throws InterruptedException {
		while (nempty == 0) {
			wait();
		}
		Thread.sleep(consTime);
		Message msg = buffer[out];
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
		return totmsg;
	}
}
