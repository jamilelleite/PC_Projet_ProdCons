package prodcons.v6;


public class ProdConsBuffer implements IProdConsBuffer{

	int bufferSz;
	Message buffer[];
	int nfull;
	int nempty;
	int in = 0;
	int out = 0;
	int consDelay;
	int prodDelay;
	int totmsg = 0;
	
	public ProdConsBuffer(int bufferSz, int consDelay, int prodDelay) {
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
		totmsg++;
		nempty++;
		nfull--;
		notifyAll();		
	}
	
	public synchronized void put (Message m, int n) throws InterruptedException{
		//TO DO
		while (nfull == 0) {
			wait();
		}
		//Putting the message m, n times in the buffer
		for(int i = 0; i<n; i++) {
			buffer[in] = m;
			totmsg++;
			Thread.sleep(prodDelay);
			in = (in + 1) % bufferSz;
			nempty++;
			nfull--;
		}
		//The producer waiting as long as all the iterations of his message isn't out completely
		for(int i = 0; i<bufferSz; i++) {
			if(buffer[i] == m)
				wait();
		}
		notifyAll();
	}
	//Modifying get method so that if a consumer takes a message, he can finish only if all it's iterations
	//are out of the buffer
	public synchronized Message get() throws InterruptedException {
		while (nempty == 0) {
			wait();
		}
		Thread.sleep(consDelay);
		Message msg = buffer[out];
		buffer[out] = null; //freeing the spot of the taken message
		out = (out + 1) % bufferSz;
		nempty--;
		nfull++;
		//Waiting as long as there is the same message in the buffer
		for(int i = 0; i<bufferSz; i++) {
			if(buffer[i] == msg) {
				wait();
			}
		}
		notifyAll();
		return msg;
	}
	
	public synchronized Message[] get(int k) throws InterruptedException{
		while (nempty == 0) {
			wait();
		}
		Message[] M = new Message[k];
		int counter = 0;
		for(int i = 0; i<k; i++) {
			if(buffer[i%bufferSz] != null) {
				Thread.sleep(consDelay);
				Message msg = buffer[out];
				buffer[out]=null;
				M[i] = msg;
				out = (out + 1) % bufferSz;
			}
		}
		nempty = nempty - counter;
		nfull = nfull + counter;
		notifyAll();
		return M;
	}
	
	public int nmsg() {
		return nempty;
	}

	
	public int totmsg() {
		return totmsg;
	}
}
