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
	public static void main(String[] args) throws InvalidPropertiesFormatException, IOException, InterruptedException{
		loadData();
		
		int nmsg = (int) ((Math.random()*(maxProd-minProd))+minProd);
		
		int totmsgs = nmsg*nProd;
		
		ProdConsBuffer pcbuffer = new ProdConsBuffer(bufSz, prodTime, consTime, totmsgs);
		
		//Message[] msgs = new Message[nmsg*nProd];		
		
		Producteur[] prods = new Producteur[nProd];
		Consommateur[] cons = new Consommateur[nCons];
		
		//for(int i = 0; i<(nmsg*nProd); i++)
		//	msgs[i] = new Message("This is message number "+i);
		for(int i = 0; i<nProd; i++)
				prods[i] = new Producteur(pcbuffer, nmsg);
		
		for(int i = 0; i<nCons; i++)
			cons[i] = new Consommateur(pcbuffer);
		
		for(int i = 0; i<prods.length; i++)
			if(prods[i] != null)
				prods[i].join();
		for(int i = 0; i<cons.length; i++)
			if(cons[i] != null)
				cons[i].join();
	}

}
