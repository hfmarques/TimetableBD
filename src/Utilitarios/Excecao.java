package Utilitarios;

public class Excecao extends Exception {
	private String msg;
	
	public Excecao(String msg) {
		super(msg);
		this.msg = msg;
	}
	
	public String getMensagem(){
		return msg;
	}
	

}
