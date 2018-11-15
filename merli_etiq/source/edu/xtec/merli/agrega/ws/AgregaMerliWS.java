package edu.xtec.merli.agrega.ws;


import java.net.URL;

import javax.xml.soap.MessageFactory;
import javax.xml.soap.Name;
import javax.xml.soap.SOAPBody;
import javax.xml.soap.SOAPBodyElement;
import javax.xml.soap.SOAPElement;
import javax.xml.soap.SOAPFactory;
import javax.xml.soap.SOAPMessage;

import edu.xtec.merli.agrega.objects.AgregaResource;
import edu.xtec.merli.agrega.objects.IdResource;


/*
 * JAppletProva.java
 *
 * Created on 13 de abril de 2005, 13:55
 */

/**
 *
 * @author eda3s
 */
public class AgregaMerliWS extends AgregaWS{    
	
	/**
	 * Prepara el missatge a ser enviat. 
	 * 
	 * @param idResource id del recurs a recuperar.
	 * @return 
	 */   
	protected static SOAPMessage processGetResource(IdResource idResource, SOAPMessage smRequest, MessageFactory mf, SOAPFactory sf) {

         try
         {
             smRequest = mf.createMessage();
            SOAPBody sbRequest = smRequest.getSOAPPart().getEnvelope().getBody();

             //Propi de cada operacio            
             Name n = sf.createName("getResource"); 
                     
             SOAPBodyElement sbeRequest = sbRequest.addBodyElement(n);
             sbeRequest.addChildElement(idResource.toXml());
             

            smRequest.saveChanges();
         }
         catch (Exception e)
         {
             System.out.println("Ha petat "+e.toString()+"*");
         
         }
         return smRequest;
	}    


	public SOAPElement getResource(IdResource idResource, int servidor) {
		return processOperation(AgregaWS.GETRESOURCE, idResource, servidor);
	}
	

}
