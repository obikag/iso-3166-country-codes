package main;

public class CountryCodes {
	private String name;
	private String alpha2;
	
	CountryCodes(String name, String alpha2){
		this.setName(name);
		this.setAlpha2(alpha2);
	}

	public String getName() {
		return name;
	}

	private void setName(String name) {
		this.name = name;
	}

	public String getAlpha2() {
		return alpha2;
	}

	private void setAlpha2(String alpha2) {
		this.alpha2 = alpha2;
	}
}
