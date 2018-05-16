package menjacnica.sistemskeOperacije;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.LinkedList;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;

import menjacnica.Log;

public class SOUcitajLog {
	
	public static LinkedList<Log> izvrsi(String filepath) {
		LinkedList<Log> novaLista = new LinkedList<Log>();
		try {
			FileReader reader = new FileReader(filepath);
			Gson gson = new GsonBuilder().setPrettyPrinting().create();
			JsonArray jsonArray = gson.fromJson(reader, JsonArray.class);
			if(jsonArray == null)
				return null;
			for (int i = 0; i < jsonArray.size(); i++)
				novaLista.add(gson.fromJson(jsonArray.get(i), Log.class));
		} catch (FileNotFoundException e) {
			
		}
		return novaLista;
	}
	
}
