package prodcons.v3;

import java.io.IOException;
import java.util.InvalidPropertiesFormatException;
import java.util.Properties;

public class TestProdCons {

	static void loadData() throws InvalidPropertiesFormatException, IOException{
		Properties propreties = new Properties();
		propreties.loadFromXML(TestProdCons.class.getClassLoader().getResourceAsStream("test.xml"));
		
		int nProd = Integer.parseInt(propreties.getProperty("nProd"));
		int nCons = Integer.parseInt(propreties.getProperty("nCons"));
		int bufSz = Integer.parseInt(propreties.getProperty("bufSz"));
		int prodTime = Integer.parseInt(propreties.getProperty("prodTime"));
		int consTime = Integer.parseInt(propreties.getProperty("consTime"));
		int minProd = Integer.parseInt(propreties.getProperty("minProd"));
		int maxProd = Integer.parseInt(propreties.getProperty("maxProd"));
	}
	public static void main(String[] args) throws InvalidPropertiesFormatException, IOException{
		ProdConsBuffer pcbuffer = new ProdConsBuffer(2);
		
		 loadData();
	}

}
