package menjacnica.sistemskeOperacije;

import java.io.FileWriter;
import java.io.IOException;
import java.util.GregorianCalendar;
import java.util.LinkedList;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import menjacnica.Log;

public class SOUpamtiLog {
	
	public static void izvrsi(String zahtevUrl, double valuta, String filepath, LinkedList<Log> logList) {
		
		Log logData = new Log();
		logData.setIzValuta(zahtevUrl.split("_")[0]);
		logData.setuValuta(zahtevUrl.split("_")[1]);
		logData.setDatumVreme(new GregorianCalendar().getTime().toString());
		logData.setKurs(valuta);
		if (logList == null)
			logList = new LinkedList<Log>();
		logList.add(logData);
		try {
			FileWriter writer = new FileWriter(filepath);
			Gson gson = new GsonBuilder().setPrettyPrinting().create();
			gson.toJson(logList, writer);
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
