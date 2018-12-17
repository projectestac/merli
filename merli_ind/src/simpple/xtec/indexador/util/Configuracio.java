package simpple.xtec.indexador.util;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.StringTokenizer;
import java.io.RandomAccessFile;

import org.apache.log4j.Logger;

import simpple.xtec.indexador.util.TipusFitxer;

/**
 * This class is used to load and store the configuration parameters for the
 * application. The valuea of all this parameters are extracted from a
 * configuration file.
 *
 * @author descuer
 *
 */
public class Configuracio {

    /** Scheme used to construct web URLs */
    public static String protocolWeb = "https";

    /** Scheme used to construct web service URLs */
    public static String protocolWSmerli = "http";

    // logger
    static final Logger logger = Logger.getLogger(simpple.xtec.indexador.util.Configuracio.class);

    // config parameters
    public static String contextWebAplicacio = "";
    public static String indexDir = "";
    public static String indexDir2 = "";

    public static String cadenaConnexioBDOracle = "";
    public static String userBDOracle = "";
    public static String passwordBDOracle = "";
    public static String nomDriverBDOracle = "";
    public static String servidorWSmerli = "";
    public static String portWSmerli = "";
    public static String nameHarvestingWS = "";
    public static String nameLomWS = "";

    public static String servidorOrganitzador = "";
    public static String nameRecomanacionsWS = "";

    public static String servidorWeb = "";
    public static String portWeb = "";

    public static String parseUrl = "";
    public static String refreshDUC = "";

    //Added Nadim
    public static String versionControl="v2.0.1";

    /**
     * Check if the values are already loaded
     *
     * @return true if there are loaded
     */
    public static boolean isVoid() {
        if (servidorWeb.equals("")) {
            return true;
        }
        return false;
    }


    public static void carregaConfiguracio() {

        Connection myConnection = null;
        Statement stmt = null;
        ResultSet rs = null;
        try {
            logger.debug("[carregaConfiguracio] -> init");
            myConnection = Utils.getConnectionFromPool();
            stmt = myConnection.createStatement();
            rs = stmt.executeQuery("SELECT * FROM configuracio");
            logger.debug("SELECT * FROM configuracio");

            while (rs.next()) {

                String name = rs.getString("clau");
                String value = rs.getString("valor");

                if (name.equalsIgnoreCase("protocolWeb")){
                    protocolWeb = value;
                    logger.debug("[Setting protocolWeb] " + value);
                }
                if (name.equalsIgnoreCase("servidorWeb")) {
                    servidorWeb = value;
                    logger.debug("[Setting servidorWeb] " + value);
                }
                if (name.equalsIgnoreCase("portWeb")) {
                    portWeb = value;
                    logger.debug("[Setting portWeb] " + value);
                }
                if (name.equalsIgnoreCase("contextWebAplicacio")) {
                    contextWebAplicacio = value;
                    logger.debug("[Setting contextWebAplicacio] " + value);
                }
                if (name.equalsIgnoreCase("indexDir")) {
                    indexDir = value;
                    logger.debug("[Setting indexDir] " + value);
                }
                if (name.equalsIgnoreCase("indexDir2")) {
                    indexDir2 = value;
                    logger.debug("[Setting indexDir2] " + value);
                }
                if (name.equalsIgnoreCase("userBDOracle")) {
                    userBDOracle = value;
                    logger.debug("[Setting userBDOracle] " + value);
                }
                if (name.equalsIgnoreCase("passwordBDOracle")) {
                    passwordBDOracle = value;
                    logger.debug("[Setting passwordBDOracle] " + value);
                }
                if (name.equalsIgnoreCase("nomDriverBDOracle")) {
                    nomDriverBDOracle = value;
                    logger.debug("[Setting nomDriverBDOracle] " + value);
                }
                if (name.equalsIgnoreCase("cadenaConnexioBDOracle")) {
                    cadenaConnexioBDOracle = value;
                    logger.debug("[Setting cadenaConnexioBDOracle] " + value);
                }
                if (name.equalsIgnoreCase("protocolWSmerli")){
                    protocolWSmerli = value;
                    logger.debug("[Setting protocolWSmerli] " + value);
                }
                if (name.equalsIgnoreCase("servidorWSmerli")) {
                    servidorWSmerli = value;
                    logger.debug("[Setting servidorWSmerli] " + value);
                }
                if (name.equalsIgnoreCase("portWSmerli")) {
                    portWSmerli = value;
                    logger.debug("[Setting portWSmerli] " + value);
                }
                if (name.equalsIgnoreCase("nameHarvestingWS")) {
                    nameHarvestingWS = value;
                    logger.debug("[Setting nameHarvestingWS] " + value);
                }
                if (name.equalsIgnoreCase("nameLomWS")) {
                    nameLomWS = value;
                    logger.debug("[Setting nameLomWS] " + value);
                }
                if (name.equalsIgnoreCase("servidorOrganitzador")) {
                    servidorOrganitzador = value;
                    logger.debug("[Setting servidorOrganitzador] " + value);
                }
                if (name.equalsIgnoreCase("nameRecomanacionsWS")) {
                    nameRecomanacionsWS = value;
                    logger.debug("[Setting nameRecomanacionsWS] " + value);
                }
                if (name.equalsIgnoreCase("parseUrl")) {
                    parseUrl = value;
                    logger.debug("[Setting parseUrl] " + value);
                }
                if (name.equalsIgnoreCase("refreshDUC")) {
                    refreshDUC = value;
                    logger.debug("[Setting refreshDUC] " + value);
                }

            }

            if (TipusFitxer.isVoid()) {

                TipusFitxer.carregaTipusFitxer();


            }
        } catch (Exception e) {
            logger.error(e);
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (stmt != null) {
                    stmt.close();
                }
                if (myConnection != null) {
                    Utils.commit(myConnection);
                    myConnection.close();
                }
            } catch (Exception e) {
                logger.error(e);
            }
        }
    }
    
    
    /**
     * Builds an absolute URL string for the current server configuration.
     *
     * The scheme, host and port are appended automatically to the
     * provided path parameter. If the path does not start with a / symbol,
     * it will be prefixed with it.
     *
     * Note: This is a work-around to build absolute URLs for the current.
     * code base, please, do not use this method on new code, use relative
     * URLs and see {@link java.net.URI}
     *
     * @param path    URL path string
     * @return        String representation
     */
    public static String getHostURL(String path) {
        StringBuffer sb = new StringBuffer();

        sb.append(protocolWeb);
        sb.append("://");
        sb.append(servidorWeb);

        if (portWeb != null && !portWeb.isEmpty()) {
            sb.append(':').append(portWeb);
        }

        sb.append(normalizePath(path));

        return sb.toString();
    }


    /**
     * Returns an absoulte URL for the current web application context.
     *
     * @param path    URL path string
     * @return        String representation
     */
    public static String getContextURL(String path) {
        StringBuffer sb = new StringBuffer();

        sb.append(contextWebAplicacio);
        sb.append(normalizePath(path));

        return getHostURL(sb.toString());
    }


    /**
     * Returns an absoulte URL for the current web service.
     *
     * @param path    URL path string
     * @return        String representation
     */
    public static String getServiceURL(String path) {
        StringBuffer sb = new StringBuffer();

        sb.append(protocolWSmerli);
        sb.append("://");
        sb.append(servidorWSmerli);
        
        if (portWSmerli != null && !portWSmerli.isEmpty()) {
            sb.append(':').append(portWSmerli);
        }

        sb.append(normalizePath(path));

        return sb.toString();
    }


    /**
     * Convenience method to return an absolute URL string for a
     * {@code null} URL path.
     *
     * @return        String representation
     */
    public static String getHostURL() {
        return getHostURL(null);
    }


    /**
     * Convenience method to return an absolute URL string for a
     * {@code null} URL path.
     *
     * @return        String representation
     */
    public static String getContextURL() {
        return getContextURL(null);
    }


    /**
     * Normalizes an URL path by prefixing it with a slash.
     *
     * @param path    URL path
     * @return        Normalized path
     */
    private static String normalizePath(String path) {
        StringBuffer sb = new StringBuffer();

        if (path != null && !path.isEmpty()) {
            if (path.charAt(0) != '/')
                sb.append('/');
            sb.append(path);
        }

        return sb.toString();
    }

}
