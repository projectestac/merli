package simpple.xtec.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.apache.log4j.Logger;

import simpple.xtec.web.util.Configuracio;
import simpple.xtec.web.util.UtilsCercador;

/**
 * Oracle implementation for the UsuarisDAO interface
 * 
 * @author descuer
 *
 */

public class OracleTipusFitxersDAO implements TipusFitxersDAO {

	
	Connection myConnection = null;
	
	// logger
	static final Logger logger = Logger.getLogger(simpple.xtec.dao.OracleTipusFitxersDAO.class);
	
	/**
	 * Constructor
	 */
	
	public OracleTipusFitxersDAO () {
		try {
			logger.debug("constructor -> in");				
			if (Configuracio.isVoid()) {
				Configuracio.carregaConfiguracio();
			    }
		    // Get connection from pool					
			myConnection = UtilsCercador.getConnectionFromPool();
		} catch (Exception e) {
		logger.error(e);
		}
		logger.debug("constructor -> out");			
	}
	
	
	
	/**
	 * Adds an user to the database
	 */
	
	public void afegirTipusFitxers (
			String nomGrup,
			String mimeType
	) throws SQLException, Exception {
		
		String insertStatement = "";
		PreparedStatement prepStmt = null;
		
		try {
			logger.debug("afegirTipusFitxers -> in");			

			insertStatement = "INSERT INTO tipus_fitxers (id, nomGrup, mimeType) VALUES (s_tipus_fitxers.nextVal, ?, ?)";
			prepStmt = myConnection.prepareStatement (insertStatement);
			prepStmt.setString(1, nomGrup);
			prepStmt.setString(2, mimeType);			
			logger.info("SQL: " + prepStmt);
			prepStmt.executeUpdate(); 
		} catch (SQLException e) {
			throw e;	
		} catch (Exception e) {
			throw e;	
		} finally {
			prepStmt.close();
		}	
	  logger.debug("afegirTipusFitxers -> out");		
	}		
	

	/**
	 * Deletes an user from the database
	 */
	
	public void eliminarTipusFitxers (
			int idTipusFitxers
	) throws SQLException, Exception {
		
		String deleteStatement = "";
		PreparedStatement prepStmt = null;
		
		try {
			logger.debug("eliminarTipusFitxers -> in");			
			deleteStatement = "DELETE FROM tipus_fitxers WHERE id=?";
			prepStmt = myConnection.prepareStatement (deleteStatement);			
			prepStmt.setLong(1, idTipusFitxers);	
			logger.info("SQL: " + prepStmt);
			prepStmt.executeUpdate(); 
		} catch (SQLException e) {
			throw e;	
		} catch (Exception e) {
			throw e;	
		} finally {
			prepStmt.close();	       
		}	
	logger.debug("eliminarTipusFitxers -> out");		
	}		
	
	public void modificarTipusFitxers(int idTipusFitxers, String nouGrup, String mimeType) throws SQLException, Exception {
	 String insertStatement = "";
	 PreparedStatement prepStmt = null;
	
	 try {
		logger.debug("afegirTipusFitxers -> in");			

		insertStatement = "UPDATE tipus_fitxers SET nomGrup=?, mimeType=? WHERE id=?";
		prepStmt = myConnection.prepareStatement (insertStatement);
		prepStmt.setString(1, nouGrup);
		prepStmt.setString(2, mimeType);
		prepStmt.setInt(3, idTipusFitxers);
		logger.info("SQL: " + prepStmt);
		prepStmt.executeUpdate(); 
	 } catch (SQLException e) {
		throw e;	
	 } catch (Exception e) {
		throw e;	
	 } finally {
		prepStmt.close();
	 }	
  logger.debug("afegirTipusFitxers -> out");		
}		

	
	/**
     * Close the db connection
     */ 
	
	public void disconnect () {
       try {
     	   logger.debug("disconnect -> in");
		   myConnection.close();
	   	   logger.debug("disconnect -> out");		   
		  } catch (Exception e) {
		  logger.error(e); 	 
		  }
		}		
	
	
}