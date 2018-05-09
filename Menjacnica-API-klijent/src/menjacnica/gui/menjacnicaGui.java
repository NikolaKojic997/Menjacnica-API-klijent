package menjacnica.gui;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import menjacnica.Valuta;
import menjacnica.Zemlja;
import menjacnica.util.URLConnectionUtil;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Map;

import javax.swing.JComboBox;
import javax.swing.JTextField;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class menjacnicaGui extends JFrame {

	private JPanel contentPane;
	private JLabel lblIzValuteZemlje;
	private JComboBox IzValuteComboBox;
	private JLabel lblUValutuZemlje;
	private JComboBox UValutuComboBox;
	private JLabel lblIznos;
	private JLabel label;
	private JTextField IzValuteTextField;
	private JTextField UValutuTextField;
	private JButton btnKonvertuj;
	
	LinkedList<Zemlja> zemlje = new LinkedList<Zemlja>();
	String[] nizZemalja; 
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					menjacnicaGui frame = new menjacnicaGui();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public menjacnicaGui() {
		
		
		nizZemalja = getZemlje();
		setResizable(false);
		setTitle("Menjacnica");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 426, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		contentPane.add(getLblIzValuteZemlje());
		contentPane.add(getIzValuteComboBox());
		contentPane.add(getLblUValutuZemlje());
		contentPane.add(getUValutuComboBox());
		contentPane.add(getLblIznos());
		contentPane.add(getLabel());
		contentPane.add(getIzValuteTextField());
		contentPane.add(getUValutuTextField());
		contentPane.add(getBtnKonvertuj());
		setLocationRelativeTo(null);
		
	
	}
	private JLabel getLblIzValuteZemlje() {
		if (lblIzValuteZemlje == null) {
			lblIzValuteZemlje = new JLabel("Iz valute zemlje:");
			lblIzValuteZemlje.setFont(new Font("Yu Gothic UI Semilight", Font.PLAIN, 18));
			lblIzValuteZemlje.setBounds(59, 32, 162, 36);
		}
		return lblIzValuteZemlje;
	}
	private JComboBox getIzValuteComboBox() {
		if (IzValuteComboBox == null) {
			
			IzValuteComboBox = new JComboBox();
			IzValuteComboBox.setBounds(59, 79, 122, 20);
			IzValuteComboBox.setModel(new DefaultComboBoxModel(nizZemalja));
		}
		return IzValuteComboBox;
	}
	private JLabel getLblUValutuZemlje() {
		if (lblUValutuZemlje == null) {
			lblUValutuZemlje = new JLabel("U valutu zemlje:");
			lblUValutuZemlje.setFont(new Font("Yu Gothic UI Semilight", Font.PLAIN, 18));
			lblUValutuZemlje.setBounds(231, 37, 162, 26);
			
		}
		return lblUValutuZemlje;
	}
	private JComboBox getUValutuComboBox() {
		if (UValutuComboBox == null) {
			UValutuComboBox = new JComboBox();
			UValutuComboBox.setBounds(231, 79, 122, 20);
			UValutuComboBox.setModel(new DefaultComboBoxModel(nizZemalja));
		}
		return UValutuComboBox;
	}
	private JLabel getLblIznos() {
		if (lblIznos == null) {
			lblIznos = new JLabel("Iznos:");
			lblIznos.setFont(new Font("Yu Gothic UI Semilight", Font.PLAIN, 18));
			lblIznos.setBounds(231, 110, 122, 26);
		}
		return lblIznos;
	}
	private JLabel getLabel() {
		if (label == null) {
			label = new JLabel("Iznos:");
			label.setFont(new Font("Yu Gothic UI Semilight", Font.PLAIN, 18));
			label.setBounds(59, 110, 122, 26);
		}
		return label;
	}
	private JTextField getIzValuteTextField() {
		if (IzValuteTextField == null) {
			IzValuteTextField = new JTextField();
			IzValuteTextField.setBounds(59, 147, 122, 20);
			IzValuteTextField.setColumns(10);
		}
		return IzValuteTextField;
	}
	private JTextField getUValutuTextField() {
		if (UValutuTextField == null) {
			UValutuTextField = new JTextField();
			UValutuTextField.setBounds(231, 147, 122, 20);
			UValutuTextField.setColumns(10);
		}
		return UValutuTextField;
	}
	
	
	
	private JButton getBtnKonvertuj() {
		if (btnKonvertuj == null) {
			btnKonvertuj = new JButton("Konvertuj");
			btnKonvertuj.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					String url = "http://free.currencyconverterapi.com/api/v3/convert?q=";
					String zahtevUrl = getSkraceniNaziv(IzValuteComboBox.getSelectedItem().toString()) + "_"
							+ getSkraceniNaziv(UValutuComboBox.getSelectedItem().toString());
					url = url + zahtevUrl;
					try {
						String content = URLConnectionUtil.getContent(url);
						JsonParser jsonPraser = new JsonParser();
						JsonObject jsonObj = jsonPraser.parse(content).getAsJsonObject().getAsJsonObject("results")
								.getAsJsonObject(zahtevUrl);
						Gson gson = new GsonBuilder().create();
						Valuta valuta = gson.fromJson(jsonObj, Valuta.class);

						if (valuta != null)
							izvrsiKonverziju(valuta.getVal());
						else
							JOptionPane.showMessageDialog(contentPane, "Nije pronadjena konverzija za: " + zahtevUrl,
									"ERROR", JOptionPane.ERROR_MESSAGE);
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
				
			});
			btnKonvertuj.setBounds(147, 205, 122, 23);
		}
		return btnKonvertuj;
	}

	private void izvrsiKonverziju(double val) {
	try {
		double iznosIz = Double.parseDouble(IzValuteTextField.getText());
		UValutuTextField.setText(String.valueOf(iznosIz * val));
	} catch (NumberFormatException nfe) {
		JOptionPane.showMessageDialog(contentPane, "Morate uneti broj u polje za iznos!", "ERROR", JOptionPane.ERROR_MESSAGE);
	}
}
	
	private String getSkraceniNaziv(String ime) {
		for (int i = 0; i < zemlje.size(); i++)
			if (zemlje.get(i).getName().equals(ime))
				return zemlje.get(i).getCurrencyId();
		return null;
	}
	
	private String[] getZemlje() {
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
