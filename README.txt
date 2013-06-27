#Project Name: Digital ISO 3166 Country Code List
###Author: Obika Gellineau
###Created: 2013-05-23


###Description

The purpose of this GIT project is to create JSON, CSV, YAML files
and an SQL Script containing the following ISO-3166 country code data:

	1) ISO 3166-1 Alpha2
	2) ISO 3166-1 Alpha3
	3) ISO 3166-1 Numeric

The aforementioned files have been placed in the 'iso-country-code-files'
directory. The descrpition of each file is as follows:

	1)iso3166_full.xml - Comprehensive XML file of ISO 3166 List, where
						each node in the file has muliple elements
	2)iso3166_min.xml - Minimal XML file of ISO 3166 List, where each
						node in the file has multiple attributes
	3)iso3166_full.xml - Comprehensive JSON file of ISO 3166 List, where
						details such as the list name and date created are
						given
	4)iso3166_min.xml - Minimal JSON file of ISO 3166 List, where
						it has been formatted as a JSON Array for
						easy import into MongoDB
	5)iso3166.csv - CSV file of ISO 3166 List
	6)iso3166.yaml - YAML file of ISO 3166 List
	7)iso3166.sql - SQL script to create an iso_codes table in a
					database.

A JAVA program called 'ISO-3166-Downloader' has also been
packaged with this project. This program downloads the freely
available ISO 3166 list located at www.iso.org.

The program and associated files are located in the 
'ISO-3166-Downloader/dist' folder. To build from source, 
download Ant 1.8 and execute build.xml located in the 'ISO-3166-Downloader'
folder.

To execute the jar file, run the following command:

	java -cp iso3166dl.jar main.ListDownloader
	

Program Requirments:

	1) Java 1.6 or higher
	2) Internet connection
	

###Updates

2013-06-26 - Initial files were built from the information given from the following sites:

	1) http://en.wikipedia.org/wiki/ISO_3166
	2) http://www.iso.org/iso/home/standards/country_codes/country_names_and_code_elements_xml.htm
	
