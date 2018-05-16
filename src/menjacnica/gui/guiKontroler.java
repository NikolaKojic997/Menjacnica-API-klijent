package menjacnica.gui;

import java.awt.EventQueue;
import java.awt.TextField;
import java.util.LinkedList;

import javax.swing.JOptionPane;
import javax.swing.JTextField;

import menjacnica.Valuta;
import menjacnica.Zemlja;
import menjacnica.menjacnica;
import menjacnica.sistemskeOperacije.SOUcitajLog;
import menjacnica.sistemskeOperacije.SOUpamtiLog;

public class guiKontroler {
	
	public static menjacnicaGui glavniProzor ;
	public static menjacnica sistem;
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					sistem = new menjacnica();
					guiKontroler.glavniProzor = new menjacnicaGui();
					guiKontroler.glavniProzor.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	public static String[] preuzmiZemlje(LinkedList<Zemlja> zemlje) {
		return sistem.preuzmiZemlje(zemlje);
			
	}
	
	
	
	public static void Konvertuj() {
		
		String url = "http://free.currencyconverterapi.com/api/v3/convert?q=";
		String zahtevUrl = guiKontroler.getSkraceniNaziv(glavniProzor.getIzValuteComboBox().getSelectedItem().toString()) + "_"
				+ guiKontroler.getSkraceniNaziv(glavniProzor.getUValutuComboBox().getSelectedItem().toString());
		
		Valuta v = sistem.preuzmiValutu(url, zahtevUrl);
		
		if (v != null) {
			guiKontroler.izvrsiKonverziju(v.getVal(), glavniProzor.getIzValuteTextField(),
					glavniProzor.getUValutuTextField());
			sistem.upamtiLog(zahtevUrl, v.getVal(), "data/log.json");
		}
		else JOptionPane.showMessageDialog(glavniProzor.getContentPane(), "Nije pronadjena konverzija za: " + zahtevUrl,
				"ERROR", JOptionPane.ERROR_MESSAGE);
		
	}
	
	public static void izvrsiKonverziju(double val, JTextField jTextField, JTextField jTextField2 ) {
		try {
			double iznosIz = Double.parseDouble(jTextField.getText());
			jTextField2.setText(String.valueOf(iznosIz * val));
		} catch (NumberFormatException nfe) {
			JOptionPane.showMessageDialog(guiKontroler.glavniProzor.getContentPane(), "Morate uneti broj u polje za iznos!", "ERROR", JOptionPane.ERROR_MESSAGE);
		}
	}
	
	public static String getSkraceniNaziv(String ime) {
		for (int i = 0; i < glavniProzor.zemlje.size(); i++)
			if (glavniProzor.zemlje.get(i).getName().equals(ime))
				return glavniProzor.zemlje.get(i).getCurrencyId();
		return null;
	}
	

}
