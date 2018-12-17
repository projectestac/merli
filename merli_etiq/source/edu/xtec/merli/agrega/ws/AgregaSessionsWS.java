package edu.xtec.merli.agrega.ws;


import java.net.URL;
import java.util.NoSuchElementException;

import javax.xml.soap.MessageFactory;
import javax.xml.soap.Name;
import javax.xml.soap.SOAPBody;
import javax.xml.soap.SOAPBodyElement;
import javax.xml.soap.SOAPElement;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPFactory;
import javax.xml.soap.SOAPMessage;

import org.apache.log4j.Logger;

import edu.xtec.merli.agrega.objects.AgregaPiF;
import edu.xtec.merli.agrega.objects.AgregaResource;
import edu.xtec.merli.agrega.objects.AgregaSession;
import edu.xtec.merli.agrega.objects.ObjectMerli;
import edu.xtec.merli.agrega.objects.SQIQuery;


/*
 * JAppletProva.java
 *
 * Created on 13 de abril de 2005, 13:55
 */

/**
 *
 * @author eda3s
 */
public class AgregaSessionsWS extends AgregaWS{      
	

	private static final Logger logger = Logger.getRootLogger();
    

	protected static SOAPMessage processCreateSession(AgregaSession resource, SOAPMessage smRequest, MessageFactory mf, SOAPFactory sf) {
	
	        try
	        {
	            smRequest = mf.createMessage();
	           
	            smRequest.getSOAPPart().getEnvelope().addNamespaceDeclaration("xsd", "http://www.w3.org/2001/XMLSchema");
	            smRequest.getSOAPPart().getEnvelope().addNamespaceDeclaration("xsi", "http://www.w3.org/2001/XMLSchemainstance");
	
	            SOAPBody sbRequest = smRequest.getSOAPPart().getEnvelope().getBody();
	            //Propi de cada operacio            
	//            Name n = sf.createName("createSession"); 
	//                    
	//            SOAPBodyElement sbeRequest = sbRequest.addBodyElement(n);
	//            sbeRequest.addAttribute(sf.createName("xmnls"), "http://Sesion.servicios.negocio.dri.pode.es");
	//            sbeRequest.addChildElement(resource.getUserIDXml());
	//            sbeRequest.addChildElement(resource.getPasswordXml());
	            
	            if (resource.getUserID() != null && resource.getPassword() != null &&
	            		!"".equals(resource.getUserID()) && !"".equals(resource.getPassword())){
		            //Propi de cada operacio            
		            Name n = sf.createName("createSession"); 
		                    
		            SOAPBodyElement sbeRequest = sbRequest.addBodyElement(n);
		            sbeRequest.addAttribute(sf.createName("xmnls"), "http://Sesion.servicios.negocio.dri.pode.es");
		            sbeRequest.addChildElement(resource.getUserIDXml());
		            sbeRequest.addChildElement(resource.getPasswordXml());
	            }else{
	            	 //Propi de cada operacio            
		            Name n = sf.createName("createAnonymousSession"); 
		                    
		            SOAPBodyElement sbeRequest = sbRequest.addBodyElement(n);
		            sbeRequest.addAttribute(sf.createName("xmnls"), "http://Sesion.servicios.negocio.dri.pode.es");
	            }
	
	           smRequest.saveChanges();
	        }
	        catch (Exception e)
	        {
	            System.out.println("Ha petat "+e.toString()+"*");
	        
	        }
	        return smRequest;
		}



	protected static SOAPMessage processCreateAnonymousSession(AgregaSession resource, SOAPMessage smRequest, MessageFactory mf, SOAPFactory sf) {

        try
        {
            smRequest = mf.createMessage();
           
            smRequest.getSOAPPart().getEnvelope().addNamespaceDeclaration("xsd", "http://www.w3.org/2001/XMLSchema");
            smRequest.getSOAPPart().getEnvelope().addNamespaceDeclaration("xsi", "http://www.w3.org/2001/XMLSchemainstance");

            SOAPBody sbRequest = smRequest.getSOAPPart().getEnvelope().getBody();
            Name n = sf.createName("createAnonymousSession"); 
	                    
            SOAPBodyElement sbeRequest = sbRequest.addBodyElement(n);
            sbeRequest.addAttribute(sf.createName("xmnls"), "http://Sesion.servicios.negocio.dri.pode.es");
           

           smRequest.saveChanges();
        }
        catch (Exception e)
        {
            System.out.println("Ha petat "+e.toString()+"*");
        
        }
        return smRequest;
	}



	protected static SOAPMessage processDestroySession(AgregaSession resource, SOAPMessage smRequest, MessageFactory mf, SOAPFactory sf) {

        try
        {
            smRequest = mf.createMessage();
           
            smRequest.getSOAPPart().getEnvelope().addNamespaceDeclaration("xsd", "http://www.w3.org/2001/XMLSchema");
            smRequest.getSOAPPart().getEnvelope().addNamespaceDeclaration("xsi", "http://www.w3.org/2001/XMLSchemainstance");

            SOAPBody sbRequest = smRequest.getSOAPPart().getEnvelope().getBody();
            //Propi de cada operacio            
            Name n = sf.createName("destroySession"); 
                    
            SOAPBodyElement sbeRequest = sbRequest.addBodyElement(n);
            sbeRequest.addAttribute(sf.createName("xmnls"), "http://Sesion.servicios.negocio.dri.pode.es");
            sbeRequest.addChildElement(resource.getSessionIDXml());
            
           smRequest.saveChanges();
        }
        catch (Exception e)
        {
            System.out.println("Ha petat "+e.toString()+"*");
        }

        return smRequest;
	}


	/**
	 * Processament d'una operaci�.
	 * 
	 * @param idOperation
	 *            identificador de la operaci� a realitzar.
	 * @param objm
	 *            Objecte SOAP que ser� enviat en l'execuci� de la operaci�.
	 * @param server
	 *            Servidor al que s'accedeix. {LOCAL, TEST, ACCEPT, PRODU}
	 * @return
	 */
	public SOAPElement processOperation(int idOperation, ObjectMerli objm,
			int server) {
		String response = null;
		SOAPMessage sm = null;
		SOAPElement se = null;

		int context = AGREGA_SESSIONS;
		objm.setOperation(idOperation);

		switch (idOperation) {
		case CREARSESION:
			smRequest = AgregaSessionsWS.processCreateSession((AgregaSession) objm,
					smRequest, mf, sf);
			if (((AgregaSession) objm).getUserID() != null && ((AgregaSession) objm).getPassword() != null &&
					!"".equals(((AgregaSession) objm).getUserID()) && !"".equals(((AgregaSession) objm).getPassword()))
				response = "createSessionResponse";
			else
				response = "createAnonymousSessionResponse";
			break;
		case CREARANONYMOUSSESION:
			smRequest = AgregaSessionsWS.processCreateAnonymousSession((AgregaSession) objm,
					smRequest, mf, sf);
			response = "createAnonymousSessionResponse";
			break;
		case DESTROYSESSION:
			smRequest = AgregaSessionsWS.processDestroySession((AgregaSession) objm,
					smRequest, mf, sf);
			response = "destroySessionResponse";
			break;
		}

		try {
			con = scf.createConnection();
	        sm =  sendMessage(context,server);
	        con.close();
			se = objm.getResponse(sm, response);
		} catch (SOAPException e) {
			e.printStackTrace();
		} catch (NoSuchElementException e) {
			try {
				if (sm != null)
				se = (((SOAPElement) sm.getSOAPBody().getFault()));
			} catch (SOAPException e1) {
				e1.printStackTrace();
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return se;
	}


	public String createSession(AgregaSession aSession, int servidor) {
		SOAPElement se = null;
		try{
			se = processOperation(AgregaWS.CREARSESION, aSession, servidor);
	
			aSession.setSessionID(se.getFirstChild().getFirstChild().getFirstChild().toString());
			
			return aSession.getSessionID();
		}catch(Exception e){
			logger.error("Error on createSession ->"+e.getMessage());
			if (se !=null)
				logger.equals("response: "+ se.toString());
			aSession.setSessionID("");
			return "";
		}
	}
	
	
	public SOAPElement destroySession(AgregaSession aSession, int servidor) {
		return processOperation(AgregaWS.DESTROYSESSION, aSession, servidor);
	}



	public String createAnonymousSession(AgregaSession aSession, int servidor) {
		SOAPElement se = null;
		try{
			se = processOperation(AgregaWS.CREARANONYMOUSSESION, aSession, servidor);
	
			aSession.setSessionID(se.getFirstChild().getFirstChild().getFirstChild().toString());
			
			return aSession.getSessionID();
		}catch(Exception e){
			logger.error("Error on createSession ->"+e.getMessage());
			if (se !=null)
				logger.equals("response: "+ se.toString());
			aSession.setSessionID("");
			return "";
		}
	}
}
