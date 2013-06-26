package main;

/**
 * POJO for storing the Country Codes extracted from the XML Document
 * @author Obika Gellineau
 * @since 2013-06-05
 */
public class CountryCodes {
	private String name;
	private String alpha2;
	
	/**
	 * Single Constructor for initialization 
	 * @param name Country Name
	 * @param alpha2 Country Alpha-2 code
	 */
	CountryCodes(String name, String alpha2){
		this.setName(name);
		this.setAlpha2(alpha2);
	}

	/**
	 * Gets Country Name
	 * @return String value of Country's Name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Sets Country Name
	 * @param name Country's Name
	 */
	private void setName(String name) {
		this.name = name;
	}

	/**
	 * Gets Country's Alpha-2 code
	 * @return String value of Country's Alpha-2 Code
	 */
	public String getAlpha2() {
		return alpha2;
	}

	/**
	 * Sets Country's Alpha-2 code
	 * @param alpha2 Alpha-2 code
	 */
	private void setAlpha2(String alpha2) {
		this.alpha2 = alpha2;
	}
}
