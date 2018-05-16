package menjacnica;

import java.util.LinkedList;

import menjacnica.sistemskeOperacije.SOGetZemlje;
import menjacnica.sistemskeOperacije.SOKonvertuj;
import menjacnica.sistemskeOperacije.SOUcitajLog;
import menjacnica.sistemskeOperacije.SOUpamtiLog;

public class Menjacnica {

	
	 
	public String[] preuzmiZemlje(LinkedList<Zemlja> zemlje) {
		return SOGetZemlje.izvrsi(zemlje);
	}
	
	public Valuta preuzmiValutu(String url,  String zahtevUrl) {
		return SOKonvertuj.izvrsi(url, zahtevUrl);
	}
	
	public void upamtiLog (String zahtevUrl, double valuta, String filepath) {
		SOUpamtiLog.izvrsi(zahtevUrl, valuta, filepath, SOUcitajLog.izvrsi(filepath));
	}
	
}
