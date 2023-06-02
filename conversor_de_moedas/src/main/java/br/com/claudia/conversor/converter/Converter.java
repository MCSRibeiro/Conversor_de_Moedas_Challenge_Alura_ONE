package br.com.claudia.conversor.converter;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;

import javax.swing.JOptionPane;

import com.google.gson.Gson;

public class Converter {
	private static DecimalFormatSymbols formatSymbols = new DecimalFormatSymbols(Locale.US);
	private static DecimalFormat formatter = new DecimalFormat("#,###.##", formatSymbols);
	
	public Converter() {
	}
	
	public static void currencyConverter(String inputValue) throws Exception {
		BigDecimal value = new BigDecimal(inputValue);
		//Cria um LinkedHashMap e adiciona valores a ele
		//As chaves são as opções que serão exibidas no menu dropdown
		//Os valores são usados na URL da API para buscar a cotação selecionada
		LinkedHashMap<String, String> currencyMap = new LinkedHashMap<String, String>();
		currencyMap.put("Reais para Dólares", "BRL-USD");
		currencyMap.put("Reais para Euros", "BRL-EUR");
		currencyMap.put("Reais para Libras Esterlinas", "BRL-GBP");
		currencyMap.put("Reais para Pesos Argentinos", "BRL-ARS");
		currencyMap.put("Reais para Pesos Chilenos", "BRL-CLP");
		currencyMap.put("Dólares para Reais", "USD-BRL");
		currencyMap.put("Euros para Reais", "EUR-BRL");
		currencyMap.put("Libras Esterlinas para Reais", "GBP-BRL");
		currencyMap.put("Pesos Argentinos para Reais",  "ARS-BRL");
		currencyMap.put("Pesos Chilenos para Reais", "CLP-BRL");
		
		//Array que receberá as chaves do map para exibição no menu dropdown
		String[] currencyOptions = new String[currencyMap.size()];
		int counter = 0;
		
		for(Map.Entry mapElement : currencyMap.entrySet()) {
			currencyOptions[counter] = (String)mapElement.getKey();
			counter++;
		}
		
		String currencyChoice = (String)JOptionPane.showInputDialog(null, "Escolha uma opção:", 
				"Conversor de Valores", JOptionPane.PLAIN_MESSAGE, null, currencyOptions, currencyOptions[0]);
		
		if(currencyChoice == null) {
			return;
		} else {
			String currencyTo = currencyChoice.substring(currencyChoice.lastIndexOf("para") + 5);
			
			//Tenta a conexão com a API para obter a cotação
			//Sendo a conexão bem sucedida, mostra o resultado da conversão com base na cotação obtida
			try {
				URL url = new URL("https://economia.awesomeapi.com.br/json/" + currencyMap.get(currencyChoice));
				HttpURLConnection con = (HttpURLConnection) url.openConnection();
				
				if(con.getResponseCode() != 200) {
					throw new RuntimeException("Erro: " + con.getResponseCode());
				}
				
				BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(con.getInputStream()));
				String jsonToString = bufferedReader.readLine();
				
				Gson gson = new Gson();
				Conversion conversion = gson.fromJson(jsonToString.toString().replaceAll("[\\[\\]]", ""), Conversion.class);
				BigDecimal conversionValue = new BigDecimal(conversion.getString());
				BigDecimal result = value.multiply(conversionValue);
				
				JOptionPane.showMessageDialog(null, "Você tem $ " + (formatter.format(result)) + " " + currencyTo);
			} catch (Exception e) {
				throw new Exception("Erro: " + e);
			}
		}
	}
	
	public static void temperatureConverter(String inputValue) {
		Double value = Double.parseDouble(inputValue);
		double result = 0d;
		
		//Array com as opções de temperaturas exibidas no menu dropdown
		String tempOptions[] = {"Celsius para Fahrenheit", "Celsius para Kelvin", "Fahrenheit para Celsius", 
				"Fahrenheit para Kelvin", "Kelvin para Celsius", "Kelvin para Fahrenheit"};
		
		String tempChoice = (String)JOptionPane.showInputDialog(null, "Escolha uma opção:", 
				"Conversor de Valores", JOptionPane.PLAIN_MESSAGE, null, tempOptions, tempOptions[0]);
		
		if(tempChoice == null) {
			return;
		} else {
			String tempFrom = tempChoice.substring(0, tempChoice.indexOf("para"));
			String tempTo = tempChoice.substring(tempChoice.lastIndexOf("para") + 5);
			
			//Efetua a conversão de temperatura com base no valor digitado e nas temperaturas escolhidas
			switch(tempChoice) {
			case "Celsius para Fahrenheit":
				result = value * 1.8 + 32;
				break;
			case "Celsius para Kelvin":
				result = value + 273.15;
				break;
			case "Fahrenheit para Celsius":
				result = (value - 32) / 1.8;
				break;
			case "Fahrenheit para Kelvin":
				result = (value - 32) * 5/9 + 273.15;
				break;
			case "Kelvin para Celsius":
				result = value - 273.15;
				break;
			case "Kelvin para Fahrenheit":
				result = (value - 273.15) * 1.8 + 32;
				break;
			}
			JOptionPane.showMessageDialog(null, value + " " + tempFrom + " equivalem a " + formatter.format(result) + " " + tempTo);
		}
	}
	
	public static void spaceWeightConverter(String inputValue) {
		Double value = Double.parseDouble(inputValue);
		double result = 0d;
		
		//Array com a lista de planetas que serão exibidos no menu dropdown
		String planetOptions[] = {"Mercúrio", "Vênus", "Marte", "Júpiter", "Saturno", "Urano", "Netuno"};
		
		String planetChoice = (String)JOptionPane.showInputDialog(null, "Escolha uma opção:", 
				"Conversor de Valores", JOptionPane.PLAIN_MESSAGE, null, planetOptions, planetOptions[0]);
		
		if(planetChoice == null) {
			return;
		} else {
			//Calcula o resultado baseado no valor inserido e no planeta escolhido
			switch(planetChoice) {
			case "Mercúrio":
				result = value * 0.37;
				break;
			case "Vênus":
				result = value * 0.88;
				break;
			case "Marte":
				result = value * 0.38;
				break;
			case "Júpiter":
				result = value * 2.64;
				break;
			case "Saturno":
				result = value * 1.15;
				break;
			case "Urano":
				result = value * 1.17;
				break;
			case "Netuno":
				result = value * 1.18;
				break;
			}
			JOptionPane.showMessageDialog(null, value + " kg seriam " + (formatter.format(result)) + " kg em " + planetChoice);
		}
	}
}
