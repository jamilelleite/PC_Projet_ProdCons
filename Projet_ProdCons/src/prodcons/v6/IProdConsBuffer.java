package prodcons.v6;


public interface IProdConsBuffer {
	
	public void put(Message msg) throws InterruptedException;
	
	public Message get() throws InterruptedException;
	
	public void put (Message m, int n) throws InterruptedException;

	public Message[] get(int k) throws InterruptedException;
	
	public int nmsg();
	
	public int totmsg();
}
