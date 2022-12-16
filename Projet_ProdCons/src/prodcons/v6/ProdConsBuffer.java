package prodcons.v6;

import java.util.concurrent.Semaphore;

public class ProdConsBuffer implements IProdConsBuffer{

	int bufferSz;
	Message buffer[];
	int nfull;
	int nempty;
	int prodTime;
	int consTime;
	int in = 0;
	int out = 0;
	
	public ProdConsBuffer(int bufferSz, int prodTime, int consTime) {
		this.bufferSz = bufferSz;
		this.buffer = new Message[bufferSz];
		this.consTime = consTime;
		this.prodTime = prodTime;
		this.nfull = bufferSz;
		this.nempty = 0;
	}

	
	public synchronized void put(Message msg) throws InterruptedException {
//		while (nfull == 0) {
//			wait();
//		}
//		buffer[in] = msg;
//		in = (in + 1) % bufferSz;
//		nempty++;
//		nfull--;
//		notifyAll();		
		put(msg,1);
	}
	
	public void put (Message m, int n) throws InterruptedException{
		int putCounter = 0;
		Rdv rdv = new Rdv(n);
		synchronized(this){
			while(putCounter < n){
				while(nfull == 0){
					wait();
				}
				Thread.sleep(prodTime);
				System.out.println("Message " + m.message + " being written " + n + " times");
				buffer[in] = m;
				buffer[in].setRdv(rdv);
				in = (in + 1) % bufferSz;
				nempty++;
				nfull--;
				putCounter++;
				notifyAll();
			}
		}
		m.getRdv().enter();
	}

	public Message get() throws InterruptedException {
		Message msg;
		Rdv rdv;
		synchronized(this) {
			while (nempty == 0) {
				wait();
			}
			Thread.sleep(consTime);
			msg = buffer[out];
			System.out.println("Message being read: " + msg.message);
			rdv = msg.getRdv();
			out = (out + 1) % bufferSz;
			nempty--;
			nfull++;
			notifyAll();
		}
		rdv.enter();
		return msg;
	}
	
//	public Message[] get(int k) throws InterruptedException{
//		int getCounter = 0;
//		Message[] msg = new Message[k];
//		Rdv rdv;
//		while (getCounter < k) {
//			synchronized(this) {
//				while (nempty == 0) {
//					wait();
//				}
//				Thread.sleep(consTime);
//				msg[getCounter] = buffer[out];
//				rdv = msg[getCounter].getRdv();
//				System.out.println("Message " + getCounter + "/" + k + ": " + msg[getCounter].message);
//				out = (out + 1) % bufferSz;
//				getCounter++;
//				nempty--;
//				nfull++;
//				notifyAll();
//			}
//			rdv.enter();
//			
//		}
//		
//		return msg;
//	}
	
	public int nmsg() {
		return nempty;
	}

	
	public int totmsg() {
		return 0;
	}
}
