package prodcons.v1;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.InvalidPropertiesFormatException;
import java.util.Properties;

public class TestProdCons {
	static int nProd, nCons, bufSz, prodTime, consTime, minProd, maxProd;
	
	static void loadData() throws InvalidPropertiesFormatException, IOException{
		Properties propreties = new Properties();
		propreties.loadFromXML(new FileInputStream("test.xml"));
		
		nProd = Integer.parseInt(propreties.getProperty("nProd"));
		nCons = Integer.parseInt(propreties.getProperty("nCons"));
		bufSz = Integer.parseInt(propreties.getProperty("bufSz"));
		prodTime = Integer.parseInt(propreties.getProperty("prodTime"));
		consTime = Integer.parseInt(propreties.getProperty("consTime"));
		minProd = Integer.parseInt(propreties.getProperty("minProd"));
		maxProd = Integer.parseInt(propreties.getProperty("maxProd"));
	}
	public static void main(String[] args) throws InvalidPropertiesFormatException, IOException{
		loadData();
		
		ProdConsBuffer pcbuffer = new ProdConsBuffer(bufSz);
		
		Message msg1 = new Message("test1");
		
		Consommateur c1 = new Consommateur(pcbuffer);
		Consommateur c2 = new Consommateur(pcbuffer);
		Producteur p1 = new Producteur(pcbuffer, msg1);
		Producteur p2 = new Producteur(pcbuffer, msg1);
		Producteur p3 = new Producteur(pcbuffer, msg1);
		
		 
	}

}
