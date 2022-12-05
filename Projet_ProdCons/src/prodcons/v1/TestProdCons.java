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
		//Avant de tout test, vérifie la valeur des producteurs et consommateur dans ton xml pour te rassurer 
		//que ce que tu cherche à prouver fonctionne bien
				
		//Pour celui-ci, il faut se rassurer que le nombre de producteur soit un multiple du nombre de consommateur
		//Se  multiple est trouvé par la valeur de K (consommateur = k * producteur)
		//La limite de se code c'est que, les lecture et les écriture ne se font pas de manière bouclé
		//donc le nombre de consommateur doit être égale au nombre de producteur sinon le code ne termine pas
		
		loadData();// data from xml file
		Random rand; //Used to generate a random number of messages
		int nmsg = (int) (Math.random() * (maxProd - minProd)); //the number of messages
		ProdConsBuffer pcbuffer = new ProdConsBuffer(bufSz, prodTime, consTime);
		Message[] msgs; //Table of all generated messages
		
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
