package menjacnica.sistemskeOperacije;

import java.io.IOException;
import java.util.LinkedList;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import menjacnica.Zemlja;
import menjacnica.util.URLConnectionUtil;

public class SOGetZemlje {
		
	public static String[] izvrsi(LinkedList<Zemlja> zemlje ) {
	try {
			String content = URLConnectionUtil.getContent("http://free.currencyconverterapi.com/api/v3/countries");
			Gson gson = new GsonBuilder().create();
			JsonParser jp = new JsonParser();
			JsonObject jo = jp.parse(content).getAsJsonObject().getAsJsonObject("results");
			for (Map.Entry<String, JsonElement> entry : jo.entrySet()) {
				Zemlja zemlja = gson.fromJson(entry.getValue(), Zemlja.class);
				zemlje.add(zemlja);
				
			}
			String[] naziviZemalja = new String[zemlje.size()];
			for (int i = 0; i < zemlje.size(); i++)
				naziviZemalja[i] = zemlje.get(i).getName();
			
			return naziviZemalja;
		} catch (IOException e) {
			e.printStackTrace();
		}
	return null;
		
	}
	
}
