package prodcons.v5;

public class ProdConsBuffer implements IProdConsBuffer{

	int bufferSz;
	Message buffer[];
	int nfull;
	int nempty;
	int in = 0;
	int out = 0;
	
	public ProdConsBuffer(int bufferSz) {
		this.bufferSz = bufferSz;
		this.buffer = new Message[bufferSz];
		this.nfull = bufferSz;
		this.nempty = 0;
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
	}
	
	public synchronized Message[] get(int k) throws InterruptedException{
		while (nempty == 0) {
			wait();
		}
		Message[] M = new Message[k];
		for(int i = 0; i<k; i++) {
			Message msg = buffer[out];
			M[i] = msg;
			out = (out + 1) % bufferSz;
		}
		nempty = nempty - k;
		nfull = nfull + k;
		notifyAll();
		return M;
	}
	
	public int nmsg() {
		return nempty;
	}

	
	public int totmsg() {
		return 0;
	}
}
