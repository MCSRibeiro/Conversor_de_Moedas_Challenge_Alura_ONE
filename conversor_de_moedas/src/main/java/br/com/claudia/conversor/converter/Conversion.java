package br.com.claudia.conversor.converter;

public class Conversion {
	String ask;
	
	public Conversion(String ask) {
		this.ask = ask;
	}
	
	public String getAsk() {
		return ask;
	}
	
	@Override
	public String toString() {
		return ask;
	}
	
	public String getString() {
		return toString();
	}
}
