package edu.xtec.merli.ws.objects;

import java.util.ArrayList;
import java.util.Iterator;

import javax.xml.soap.SOAPElement;
import javax.xml.soap.SOAPException;



public class IdResource extends ObjectMerli{

	private String identifier;
	private String type;
	

	public IdResource() {
		super();
		// TODO Auto-generated constructor stub
		this.identifier = "";
		this.type = "";
	}

	public IdResource(String identifier, String type) {
		super();
		// TODO Auto-generated constructor stub
		this.identifier = identifier;
		this.type = type;
	}



	public IdResource(SOAPElement seIdResource) throws SOAPException {
		
		Iterator it = seIdResource.getChildElements(soapFactory.createName("identifier"));
		identifier = ((SOAPElement) it.next()).getValue();
		
		it = seIdResource.getChildElements(soapFactory.createName("type"));
		type = ((SOAPElement) it.next()).getValue();
	}

	public String getIdentifier() {
		return identifier;
	}
	

	public void setIdentifier(String identifier) {
		this.identifier = identifier;
	}
	

	public String getType() {
		return type;
	}
	

	public void setType(String type) {
		this.type = type;
	}
	

	public SOAPElement toXml() throws SOAPException {
		// TODO Auto-generated method stub
		SOAPElement seIdResult=null;
		SOAPElement seIdentifier;
		SOAPElement seType;
	        
		//Crea l'element IdResult
		seIdResult = soapFactory.createElement(soapFactory.createName("idResource"));
		
		//Crea l'element identifier
		if (identifier == null) identifier = "";
		seIdentifier = soapFactory.createElement(soapFactory.createName("identifier"));
		seIdentifier.addTextNode(this.identifier);
		//Afageix l'element Identifier al IdResult
		seIdResult.addChildElement(seIdentifier);

		//Crea l'element type
		if (type == null) type = "";
		seType = soapFactory.createElement(soapFactory.createName("type"));
		seType.addTextNode(this.type);
		//Afageix l'element type al IdResult
		seIdResult.addChildElement(seType);

		return seIdResult;
	}
	
	public String toString(){
		return "IdResource[id="+getIdentifier()+",type="+getType()+"]";
	}
	
	
}
