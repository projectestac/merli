package simpple.xtec.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import org.apache.log4j.Logger;
import simpple.xtec.web.util.Configuracio;
import simpple.xtec.web.util.ConfiguracioFragments;
import simpple.xtec.web.util.UtilsCercador;

public class OracleConfiguracioDAO
  implements ConfiguracioDAO
{
  Connection myConnection = null;
  static final Logger logger = Logger.getLogger(OracleConfiguracioDAO.class);
  
  public OracleConfiguracioDAO()
  {
    try
    {
      logger.debug("constructor -> in");
      if (Configuracio.isVoid()) {
        Configuracio.carregaConfiguracio();
      }
      myConnection = UtilsCercador.getConnectionFromPool();
      logger.debug("constructor -> out");
    }
    catch (Exception localException)
    {
      logger.error(localException);
    }
  }
  
  public void actualitzaPes(String paramString1, String paramString2, float paramFloat)
    throws SQLException, Exception
  {
    String str = "";
    PreparedStatement localPreparedStatement = null;
    try
    {
      logger.debug("actualitzaPes -> in");
      logger.debug("tipusCercador: " + paramString1);
      logger.debug("nomPes: " + paramString2);
      logger.debug("valorPes: " + paramFloat);
      str = "UPDATE config_pesos_index SET ";
      str = str + "valor=? WHERE cercador_id=? and nom_camp=?";
      localPreparedStatement = myConnection.prepareStatement(str);
      localPreparedStatement.setFloat(1, paramFloat);
      if (paramString1.equals("edu365")) {
        localPreparedStatement.setInt(2, 1);
      } else {
        localPreparedStatement.setInt(2, 2);
      }
      localPreparedStatement.setString(3, paramString2);
      logger.debug("SQL: " + localPreparedStatement);
      localPreparedStatement.executeUpdate();
    }
    catch (SQLException localSQLException)
    {
      throw localSQLException;
    }
    catch (Exception localException)
    {
      throw localException;
    }
    finally
    {
      localPreparedStatement.close();
    }
    logger.debug("actualitzaPes -> out");
  }
  
  public void actualitzaFragments(String paramString, int paramInt1, int paramInt2, int paramInt3, int paramInt4)
    throws SQLException, Exception
  {
    String str = "";
    PreparedStatement localPreparedStatement = null;
    try
    {
      logger.debug("actualitzaFragments -> in");
      logger.debug("tipusCercador: " + paramString);
      logger.debug("longitudDescripcio: " + paramInt1);
      logger.debug("resultatsPagina: " + paramInt2);
      logger.debug("nombreNovetats: " + paramInt3);
      logger.debug("tempsVidaNovetat: " + paramInt4);
      str = "UPDATE config_cerca SET long_desc=?";
      str = str + ",max_resultats_pag=?";
      str = str + ",nombre_novetats=?";
      str = str + ",temps_vida_novetat=?";
      str = str + " WHERE cercador_id=?";
      logger.debug("[actualitzaDades] " + str);
      localPreparedStatement = myConnection.prepareStatement(str);
      localPreparedStatement.setInt(1, paramInt1);
      localPreparedStatement.setInt(2, paramInt2);
      localPreparedStatement.setInt(3, paramInt3);
      localPreparedStatement.setInt(4, paramInt4);
      if (paramString.equals("edu365")) {
        localPreparedStatement.setInt(5, 1);
      } else {
        localPreparedStatement.setInt(5, 2);
      }
      logger.info("SQL: " + localPreparedStatement);
      localPreparedStatement.executeUpdate();
    }
    catch (SQLException localSQLException)
    {
      throw localSQLException;
    }
    catch (Exception localException)
    {
      throw localException;
    }
    finally
    {
      localPreparedStatement.close();
    }
    logger.debug("actualitzaFragments -> out");
  }
  
  public void reload(String paramString)
  {
    try
    {
      logger.debug("reload -> in");
      ConfiguracioFragments.loadConfiguracio(myConnection, paramString);
    }
    catch (Exception localException)
    {
      logger.error(localException);
    }
    logger.debug("reload -> out");
  }
  
  public void disconnect()
  {
    try
    {
      logger.debug("disconnect -> in");
      myConnection.close();
      logger.debug("disconnect -> out");
    }
    catch (Exception localException)
    {
      logger.error(localException);
    }
  }
}