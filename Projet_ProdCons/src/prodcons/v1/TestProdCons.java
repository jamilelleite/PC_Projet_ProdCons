package prodcons.v1;

public class TestProdCons {

	public static void main(String[] args) {
		ProdConsBuffer pcbuffer = new ProdConsBuffer(2);
		
		Message msg1 = new Message("test1");
		
		Consommateur c1 = new Consommateur(pcbuffer);
		Consommateur c2 = new Consommateur(pcbuffer);
		Producteur p1 = new Producteur(pcbuffer, msg1);
		Producteur p2 = new Producteur(pcbuffer, msg1);
		Producteur p3 = new Producteur(pcbuffer, msg1);
	}

}
