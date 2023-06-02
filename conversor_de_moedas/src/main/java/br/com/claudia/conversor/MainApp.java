package br.com.claudia.conversor;

import javax.swing.JOptionPane;

import br.com.claudia.conversor.converter.Converter;

public class MainApp {

	public static void main(String[] args) throws Exception {
		String inputValue;
		//Determina as opções de conversores a serem exibidas
		String choices[] = {"Conversor de Moedas", "Conversor de Temperaturas", 
				"Conversor de Peso Espacial"};
		
		String firstChoice = (String)JOptionPane.showInputDialog(null, 
				"Escolha uma opção:", "Conversor de Valores", JOptionPane.PLAIN_MESSAGE, 
				null, choices, choices[0]);
		
		if(firstChoice == null) {
			System.exit(0);
		} else {
			//Exibe tela para inserção de valor e verifica se o valor é válido
			//Se o valor for inválido, mostra uma mensagem
			//Se o valor for válido chama o conversor correspondente
			do {
				inputValue = JOptionPane.showInputDialog(null, "Insira um valor:", 
						"Conversor de Valores", JOptionPane.QUESTION_MESSAGE);
				if(inputValue == null) {
					break;
				} else {
					if(inputValue.matches("^[0-9.]+$")) {
						switch(firstChoice) {
						case "Conversor de Moedas":
							Converter.currencyConverter(inputValue);
							break;
						case "Conversor de Temperaturas":
							Converter.temperatureConverter(inputValue);
							break;
						case "Conversor de Peso Espacial":
							Converter.spaceWeightConverter(inputValue);
							break;
						}
						break;
					} else {
						JOptionPane.showMessageDialog(null, "Insira um valor válido");
					}
				}
			} while(!inputValue.matches("^[0-9.]+$") || inputValue.isEmpty());
		}
		
		//Verifica se o usuário deseja continuar a usar a aplicação
		int saida = JOptionPane.showConfirmDialog(null, "Deseja continuar?");
		
		if(saida == JOptionPane.YES_OPTION) {
			main(args);
		} else if(saida == JOptionPane.NO_OPTION) {
			JOptionPane.showMessageDialog(null, "Programa finalizado");
		} else if (saida == JOptionPane.CANCEL_OPTION) {
			JOptionPane.showMessageDialog(null, "Programa concluído");
		}

	}

}
