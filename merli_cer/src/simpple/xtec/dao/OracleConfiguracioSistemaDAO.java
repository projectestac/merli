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

public class OracleConfiguracioSistemaDAO implements ConfiguracioSistemaDAO {

	
	Connection myConnection = null;
	
	// logger
	static final Logger logger = Logger.getLogger(simpple.xtec.dao.OracleConfiguracioSistemaDAO.class);
	
	/**
	 * Constructor
	 */
	
	public OracleConfiguracioSistemaDAO () {
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
	
	public void afegirDadaConfiguracio (
			String novaClau,
			String valor
	) throws SQLException, Exception {
		
		String insertStatement = "";
		PreparedStatement prepStmt = null;
		
		try {
			logger.debug("afegirDadaConfiguracio -> in");			

			insertStatement = "INSERT INTO configuracio (id, clau, valor) VALUES (s_configuracio.nextVal, ?, ?)";
			prepStmt = myConnection.prepareStatement (insertStatement);
			prepStmt.setString(1, novaClau);
			prepStmt.setString(2, valor);			
			logger.info("SQL: " + prepStmt);
			prepStmt.executeUpdate();
			Configuracio.carregaConfiguracio();
		} catch (SQLException e) {
			throw e;	
		} catch (Exception e) {
			throw e;	
		} finally {
			prepStmt.close();
		}	
	  logger.debug("afegirDadaConfiguracio -> out");		
	}		
	

	public void modificarDadaConfiguracio (
			int idConfiguracio,
			String novaClau,
			String valor
	) throws SQLException, Exception {
		
		String insertStatement = "";
		PreparedStatement prepStmt = null;
		
		try {
			logger.debug("modificarDadaConfiguracio -> in");			
			insertStatement = "UPDATE configuracio SET clau=?, valor=? WHERE id=?";
			prepStmt = myConnection.prepareStatement (insertStatement);
			prepStmt.setString(1, novaClau);
			prepStmt.setString(2, valor);
			prepStmt.setInt(3, idConfiguracio);
			logger.info("SQL: " + prepStmt);
			prepStmt.executeUpdate(); 
			Configuracio.carregaConfiguracio();
		} catch (SQLException e) {
			throw e;	
		} catch (Exception e) {
			throw e;	
		} finally {
			prepStmt.close();
		}	
	  logger.debug("modificarDadaConfiguracio -> out");		
	}		
	
	
	
	/**
	 * Deletes an user from the database
	 */
	
	public void eliminarDadaConfiguracio (
			int idConfiguracio
	) throws SQLException, Exception {
		
		String deleteStatement = "";
		PreparedStatement prepStmt = null;
		
		try {
			logger.debug("eliminarTipusFitxers -> in");			
			deleteStatement = "DELETE FROM configuracio WHERE id=?";
			prepStmt = myConnection.prepareStatement (deleteStatement);			
			prepStmt.setLong(1, idConfiguracio);	
			logger.info("SQL: " + prepStmt);
			prepStmt.executeUpdate();
			Configuracio.carregaConfiguracio();
		} catch (SQLException e) {
			throw e;	
		} catch (Exception e) {
			throw e;	
		} finally {
			prepStmt.close();	       
		}	
	logger.debug("eliminarTipusFitxers -> out");		
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