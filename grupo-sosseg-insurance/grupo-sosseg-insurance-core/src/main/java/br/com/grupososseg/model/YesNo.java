package br.com.grupososseg.model;

public enum YesNo {

	Y("Sim", true), 
	N("Não", false);

	private String name;
	private boolean status;

	private YesNo(String name, boolean status) {
		this.name = name;
		this.status = status;
	}

	public String getName() {
		return name;
	}

	public boolean isStatus() {
		return status;
	}

}
