package main;

/**
 * 
 * @author Obika Gellineau
 * @since 2013-06-05
 */
public class CountryCodes {
	private String name;
	private String alpha2;
	
	/**
	 * 
	 * @param name
	 * @param alpha2
	 */
	CountryCodes(String name, String alpha2){
		this.setName(name);
		this.setAlpha2(alpha2);
	}

	/**
	 * 
	 * @return
	 */
	public String getName() {
		return name;
	}

	/**
	 * 
	 * @param name
	 */
	private void setName(String name) {
		this.name = name;
	}

	/**
	 * 
	 * @return
	 */
	public String getAlpha2() {
		return alpha2;
	}

	/**
	 * 
	 * @param alpha2
	 */
	private void setAlpha2(String alpha2) {
		this.alpha2 = alpha2;
	}
}
