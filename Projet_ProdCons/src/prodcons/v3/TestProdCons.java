package prodcons.v3;

import java.io.IOException;
import java.util.InvalidPropertiesFormatException;
import java.util.Properties;

import prodcons.v1.Consommateur;
import prodcons.v1.Message;
import prodcons.v1.ProdConsBuffer;
import prodcons.v1.Producteur;

public class TestProdCons {
	static int nProd, nCons, bufSz, prodTime, consTime, minProd, maxProd;

	static void loadData() throws InvalidPropertiesFormatException, IOException{
		Properties propreties = new Properties();
		propreties.loadFromXML(TestProdCons.class.getClassLoader().getResourceAsStream("test.xml"));
		
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
		
		
		
		//La limite de se code c'est que, les lecture et les écriture ne se font pas de manière bouclé
		//donc le nombre de consommateur doit être égale au nombre de producteur sinon le code ne termine pas
		loadData();
		ProdConsBuffer pcbuffer = new ProdConsBuffer(bufSz);
		Message msg1 = new Message("test1");
		
		Producteur[] prods = new Producteur[nProd];
		Consommateur[] cons = new Consommateur[nCons];
		
		for(int i = 0; i<nProd; i++)
			prods[i] = new Producteur(pcbuffer, msg1);
		
		for(int i = 0; i<nCons; i++)
			cons[i] = new Consommateur(pcbuffer);
		
		for(int i = 0; i<prods.length; i++)
				prods[i].join();
		for(int i = 0; i<cons.length; i++)
			cons[i].join();
		}
	}
