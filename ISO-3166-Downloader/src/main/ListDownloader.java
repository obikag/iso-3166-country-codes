package main;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Properties;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 * <p>This program parses the public available ISO3166 XML document and creates a CSV, YAML and JSON documents, as
 * well as SQL Script for inserting the contents into a database.</p>
 * 
 *<p><strong>Ant build file: </strong><em>build.xml</em></p>
 *<p><strong>Usage: </strong><em>java -jar iso3166dl.jar&nbsp;&nbsp;</em><strong>&nbsp;OR&nbsp;</strong>
 *<em>&nbsp;&nbsp;java -cp iso3166dl.jar main.ListDownloader</em></p>
 *
 * @author Obika Gellineau
 * @since 2013-06-05
 */
public class ListDownloader {
	
	/**
	 * Main Method
	 * @param args Command Line Arguments
	 */
	public static void main(String[] args) {
		URL url;
		InputStream inStream = null;
		Properties siteurl = new Properties();
		List<CountryCodes> clist = new LinkedList<CountryCodes>();
		
		try{
			//Load Properties file
			siteurl.load(new FileInputStream("siteurl.properties"));
			url = new URL(siteurl.getProperty("xmlsite"));
			inStream = url.openStream();
			//Build a Document Factory Object
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder;
			dBuilder = dbFactory.newDocumentBuilder();
			//Parse XML File
			Document doc = dBuilder.parse(inStream);
			doc.getDocumentElement().normalize();
			//Collect the Node List for root tags
			NodeList nList = doc.getElementsByTagName(siteurl.getProperty("entry"));
			for (int i = 0; i < nList.getLength(); i++) {
				//Get Node
				Node nNode = nList.item(i);
				if (nNode.getNodeType() == Node.ELEMENT_NODE) {//True if Node is an Element
					Element eElement = (Element) nNode;
					clist.add(new CountryCodes(getTagValue(siteurl.getProperty("countryname"), eElement),
										getTagValue(siteurl.getProperty("alpha2element"), eElement)));
				}
			}
			String path = siteurl.getProperty("outpath");
			//place Output files in app directory if outpath property is null
			if(path.equals("null")) path = "."+File.separator;
			//Write to SQL File
			toSQL(clist,path);
			//Write to JSON File
			toJSON(clist,path);
			//Write to CSV File
			toCSV(clist,path);
			//Write to YAML File
			toYAML(clist,path,siteurl.getProperty("xmlsite"));
		} catch (MalformedURLException mue) {
	         mue.printStackTrace();
	    } catch (IOException ioe) {
	         ioe.printStackTrace();
	    } catch (SAXException se) {
	    	se.printStackTrace();
	    } catch (ParserConfigurationException pce) {
	    	pce.printStackTrace();
	    } finally {
	        try {
	            inStream.close();
	        } catch (IOException ioe) {}
	        System.out.println("Completed");
	    }


	}
	
	/**
	 * Get Tag Value from an Element
	 * @param sTag Tag Name Element from XML File
	 * @param eElement Element Object from XML DOM
	 * @return String value of Element Object, returns "UNKNOWN" id not found
	 */
	private static String getTagValue(String sTag, Element eElement) {
		try{
			NodeList nlList = eElement.getElementsByTagName(sTag).item(0).getChildNodes();
			Node nValue = (Node) nlList.item(0);
			return nValue.getNodeValue();
		}catch(Exception e){
			return "UNKNOWN";
		}
	}
	
	/**
	 * Method used to create SQL File
	 * @param list Country code Linked List
	 * @param path Output path of the file
	 * @throws IOException Input/Output File Exception thrown
	 */
	private static void toSQL(List<CountryCodes> list, String path) throws IOException{
		FileOutputStream sql_fos = new FileOutputStream(new File(path+"iso3166.sql"));
		BufferedWriter sql_bw = new BufferedWriter(new OutputStreamWriter(sql_fos));
		sql_bw.write("CREATE TABLE iso_codes(\r\n");
		sql_bw.write("\tid INT NOT NULL AUTO_INCREMENT,\r\n");
		sql_bw.write("\tname VARCHAR(75) NOT NULL,\r\n");
		sql_bw.write("\talpha2 VARCHAR(2) NOT NULL,\r\n");
		sql_bw.write("\tCONSTRAINT pk_ISOCODES PRIMARY KEY(id)\r\n");
		sql_bw.write(");\r\n\r\n");
		for(CountryCodes c: list){
			sql_bw.write("INSERT INTO iso_codes(name,alpha2) VALUES ("+"'"+c.getName().toUpperCase().trim().replace("'", "\\'")+"','"+
					c.getAlpha2().trim()+"');\r\n");
		}
		sql_bw.close();
	}
	
	/**
	 * Method used to create JSON File. 
	 * Written as a JSON Array for easy insertion into MongoDB
	 * @param list Country Code Linked List
	 * @param path Output path of the file 
	 * @throws IOException Input/Output File Exception thrown
	 */
	private static void toJSON(List<CountryCodes> list, String path) throws IOException{
		FileOutputStream jsonmin_fos = new FileOutputStream(new File(path+"iso3166.json"));
		BufferedWriter jsonmin_bw = new BufferedWriter(new OutputStreamWriter(jsonmin_fos));
		jsonmin_bw.write("[");
		StringBuffer sb = new StringBuffer();
		for(CountryCodes c: list){
			sb.append("{\"name\":\""+c.getName().toUpperCase().trim()+"\"," +
					"\"alpha2\":\""+c.getAlpha2().trim()+"\"},");
		}
		jsonmin_bw.write(sb.toString().substring(0,sb.toString().lastIndexOf(",")));
		jsonmin_bw.write("]");
		jsonmin_bw.close();
	}
	
	/**
	 * Method used to create CSV File
	 * @param list Country code Linked List
	 * @param path Output path of the file
	 * @throws IOException Input/Output File Exception thrown
	 */
	private static void toCSV(List<CountryCodes> list, String path) throws IOException{
		FileOutputStream csv_fos = new FileOutputStream(new File(path+"iso3166.csv"));
		BufferedWriter csv_bw = new BufferedWriter(new OutputStreamWriter(csv_fos));
		csv_bw.write("COUNTRY,ALPHA-2\r\n");
		for(CountryCodes c: list){
			csv_bw.write("\""+c.getName().toUpperCase().trim()+"\","+c.getAlpha2().trim()+"\r\n");
		}
		csv_bw.close();
	}
	
	/**
	 * Method used to create YAML File
	 * @param list
	 * @param path Output path of the file
	 * @throws IOException Input/Output File Exception thrown
	 */
	private static void toYAML(List<CountryCodes> list, String path, String header) throws IOException {
		FileOutputStream yaml_fos = new FileOutputStream(new File(path+"iso3166.yml"));
		BufferedWriter yaml_bw = new BufferedWriter(new OutputStreamWriter(yaml_fos));
		Date now = new Date();
		yaml_bw.write("--- "+header+"\r\nlistname: ISO 3166 Country Code List\r\ncreated: "+(new SimpleDateFormat("yyyy-MM-dd")).format(now)+"\r\niso-codes: \r\n");
		for(CountryCodes c: list){
			yaml_bw.write("  - name: "+c.getName()+"\r\n");
			yaml_bw.write("    alpha2: "+c.getAlpha2()+"\r\n");
		}
		yaml_bw.close();
	}
}
