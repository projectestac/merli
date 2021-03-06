package edu.xtec.merli.ws.objects;

import java.util.Iterator;

import javax.xml.soap.Name;
import javax.xml.soap.SOAPElement;
import javax.xml.soap.SOAPException;

public class LangString extends ObjectMerli {

	private String string;
	private String lang;	
	

	public LangString() {
		super();
		// TODO Auto-generated constructor stub
		this.string = "";
		this.lang = "";
	}
	public LangString(String string, String lang) {
		super();
		// TODO Auto-generated constructor stub
		this.string = string;
		this.lang = lang;
	}




	public LangString(SOAPElement seLangString) throws SOAPException {
		// TODO Auto-generated constructor stub
		SOAPElement seString;
		
		Iterator It=seLangString.getChildElements(soapFactory.createName("string"));
		if (It.hasNext()){
			seString = ((SOAPElement)It.next());
			string= seString.getValue();		
			lang = seString.getAttributeValue(soapFactory.createName("language"));
		}
	}
	public String getLang() {
		return lang;
	}
	



	public void setLang(String lang) {
		this.lang = lang;
	}
	



	public String getString() {
		return string;
	}
	



	public void setString(String string) {
		this.string = string;
	}
	



	public SOAPElement toXml() throws SOAPException {
		SOAPElement langString;		
		
		//Crea l'element string
		langString = soapFactory.createElement(soapFactory.createName("string"));
		//Posa l'atribut lang
		langString.addAttribute(soapFactory.createName("language"),lang);
		//Afageix el text
		if (string == null) string = " ";
		langString.addTextNode(string);
		
		return langString;
	}
	public String toUrl2(String prefix) {
		if (string == null) string = " ";
		return prefix+lang+"="+string+"&";
	}
	public String toUrl(String prefix) {
		String params="";
		if (string == null) string = " ";
		if(prefix.equals("titol") || prefix.equals("descripcio") || prefix.equals("descDrets") || prefix.equals("descRel"))
		{
			if(lang.equals("ca")) params=prefix+"="+string+"&";
			else if (lang.equals("es")) params=prefix+"Es="+string+"&";
			else if(lang.equals("en")) params=prefix+"En="+string+"&";
		}
		if(prefix.equals("context2")||prefix.equals("versio")) params=prefix+"="+string+"&";
		return params;
	}

}
