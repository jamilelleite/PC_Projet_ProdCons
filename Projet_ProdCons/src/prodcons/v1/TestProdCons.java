package prodcons.v1;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.InvalidPropertiesFormatException;
import java.util.Properties;
import java.util.Random;
import java.util.Scanner;

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
		
		loadData();// data from xml file
		Random rand; //Used to generate a random number of messages
		int nmsg = (int) (Math.random() * (maxProd - minProd)); //the number of messages
		ProdConsBuffer pcbuffer = new ProdConsBuffer(bufSz, prodTime, consTime, nProd);
		Message[] msgs; //Table of all generated messages
		System.out.println(bufSz);
		Producteur[] prods = new Producteur[nProd]; //Table of all producers
		Consommateur[] cons = new Consommateur[nCons]; ////Table of all consumers 
		
		 msgs = new Message[nmsg];
		
		for(int i = 0; i<nmsg; i++)
			msgs[i] = new Message("This is message number " + i);
		
		System.out.println("the total number of messages in the buffer currently "+pcbuffer.nmsg());
		System.out.println("the total number of messages that has ever entered this buffer "+pcbuffer.totmsg());
		
		for(int i = 0, j = 0; i<nProd; i++, j++)
			if(j < nmsg) {
				prods[i] = new Producteur(pcbuffer, msgs[i]);
			}
		
		System.out.println("the total number of messages in the buffer currently "+pcbuffer.nmsg());
		System.out.println("the total number of messages that has ever entered this buffer "+pcbuffer.totmsg());
		
		for(int i = 0; i<nCons; i++)
			cons[i] = new Consommateur(pcbuffer);
		
		System.out.println("the total number of messages in the buffer currently "+pcbuffer.nmsg());
		System.out.println("the total number of messages that has ever entered this buffer "+pcbuffer.totmsg());
		
		for(int i = 0; i<prods.length; i++)
			if(prods[i] != null)
				System.out.println("Producer :" + prods[i].msg);
		
		for(int i = 0; i<cons.length; i++)
			if(cons[i] != null)
				System.out.println("Consummer :" + cons[i].getName());
		
		for(int i = 0; i<prods.length; i++)
			if(prods[i] != null)
				prods[i].join();
		for(int i = 0; i<cons.length; i++)
			if(cons[i] != null)
				cons[i].join();
		
	}

}
