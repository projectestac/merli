﻿<%@ page import="java.sql.*,simpple.xtec.web.util.Configuracio, simpple.xtec.web.util.UtilsCercador, simpple.xtec.web.util.DucObject" %>
<%@ page import="org.apache.log4j.Logger, java.util.Locale, java.util.Hashtable, java.util.ArrayList, simpple.xtec.web.util.XMLCollection" %>
<%@ page pageEncoding="UTF-8" %>

<%
    Logger logger = Logger.getLogger("cercaSimple.jsp");
    String sLang = XMLCollection.getLang(request);
    session.setAttribute("lastUrl", UtilsCercador.getLastUrl(request));
    if (Configuracio.isVoid()) {
        Configuracio.carregaConfiguracio();
    }
    String urlLocal = "https://" + Configuracio.servidorWeb + "/" + Configuracio.contextWebAplicacio;
    String usuari = (String) session.getAttribute("nomUsuari");

    int comentarisSuspesos = 0;
    String usuariNomComplet = "";
    if (usuari == null) {
        //usuari = (String)request.getRemoteUser();
        usuari = (String) session.getAttribute("user");
    }
    logger.debug("Usuari -> " + usuari);

    String userGeneric = request.getParameter("userGeneric");
    if ((userGeneric == null) || (!userGeneric.equals("edu365") && !userGeneric.equals("XTEC"))) {
        userGeneric = "";
    }
    logger.debug("Setting userGeneric -> " + userGeneric);
    session.setAttribute("userGeneric", userGeneric);

    usuariNomComplet = (String) session.getAttribute("usuariNomComplet");
    comentarisSuspesos = UtilsCercador.getComentarisSuspesos(usuari);
    if (usuariNomComplet == null) {
        usuariNomComplet = UtilsCercador.getNomComplet(usuari);
        session.setAttribute("usuariNomComplet", usuariNomComplet);
    }

    Connection myConnection = UtilsCercador.getConnectionFromPool();
    ArrayList allLevels = (ArrayList) session.getAttribute("levels");
    Hashtable allCicles = (Hashtable) session.getAttribute("cicles");
    Hashtable allAreas = (Hashtable) session.getAttribute("areas");
    if (allLevels == null) {
        logger.debug("Loading DUC (Levels) ...");
        allLevels = UtilsCercador.getAllLevels(myConnection);
        logger.debug("Loading DUC (Cicles) ...");
        allCicles = UtilsCercador.getAllCicles(myConnection, allLevels);
        logger.debug("Loading DUC (Areas) ...");
        allAreas = UtilsCercador.getAllAreas(myConnection, allLevels);
        session.setAttribute("levels", allLevels);
        session.setAttribute("cicles", allCicles);
        session.setAttribute("areas", allAreas);
    } else {
        logger.debug("Cached!");
    }

    try {

%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN" "http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd">
<html lang="ca">
    <head>
        <title><%=XMLCollection.getProperty("cerca.cercaSimple.titol", sLang)%></title>
        <link rel="stylesheet" type="text/css" href="<%=urlLocal%>/css/merli.css" media="all"/>
        <link rel="shortcut icon" href="<%=urlLocal%>/imatges/merli.ico" />
        <script type="text/javascript">
            <% int i = 0;
                while (i < allLevels.size()) {
                    DucObject ducLevel = (DucObject) allLevels.get(i);
            %>
            var areas_<%=ducLevel.id%> = new Array("<%=XMLCollection.getProperty("cerca.select.nivell", sLang)%>", "-1"
            <%
                ArrayList allAreasLevel = (ArrayList) allAreas.get(new Integer(ducLevel.id));
                if (allAreasLevel != null) {
                    int j = 0;
                    while (j < allAreasLevel.size()) {
                        DucObject ducArea = (DucObject) allAreasLevel.get(j);
                        if (!ducArea.term.startsWith("Competències")) {
            %>
            , "<%=ducArea.getTerm(sLang)%>", "<%=ducArea.id%>"
            <%
                        }
                        j++;
                    }
                }
            %>
            );
            <%   i++;
                }%>

            function change_area() {

                var nivell_educatiu
                nivell_educatiu = document.cerca.nivell_educatiu[document.cerca.nivell_educatiu.selectedIndex].value

                if (nivell_educatiu > 0) {


                    mis_areas = eval("areas_" + nivell_educatiu)

                    num_areas = (mis_areas.length / 2)

                    document.cerca.area_curricular.length = num_areas

                    for (i = 0; i < (num_areas * 2); i = i + 2) {
                        document.cerca.area_curricular.options[i / 2].text = mis_areas[i]
                        document.cerca.area_curricular.options[i / 2].value = mis_areas[i + 1]
                    }
                } else {

                    document.cerca.area_curricular.length = 1

                    document.cerca.area_curricular.options[0].value = "-1"
                    document.cerca.area_curricular.options[0].text = "<%=XMLCollection.getProperty("cerca.select.area", sLang)%>"
                }

                document.cerca.area_curricular.options[0].selected = true
            }

            function save_area() {
                var area_curricular = document.cerca.area_curricular[document.cerca.area_curricular.selectedIndex].value
                setCookie("area_curricular", area_curricular);
            }


            function recoverValues() {
                var nivell_educatiu = document.cerca.nivell_educatiu[document.cerca.nivell_educatiu.selectedIndex].value
                if (nivell_educatiu > 0) {
                    change_area();
                    var area_curricular = getCookie("area_curricular");
                    for (var i = 0; i < document.cerca.area_curricular.length; i++)
                    {
                        if (document.cerca.area_curricular[i].value == area_curricular) {
                            document.cerca.area_curricular.selectedIndex = i;
                        }
                    }
                }
            }

            function doSubmit() {
                var value = document.cerca.textCerca.value;
                document.cerca.textCerca.value = value.toString();
                document.cerca.submit();
            }

        </script>
        <script type="text/javascript" src="<%=urlLocal%>/scripts/cookies.js"></script>	
    </head>	
    <body onLoad="recoverValues()">
        <div id="non-footer">
            <div id="login">
                <% if (usuari != null) {%>
                Hola, <%=usuariNomComplet%> 
                <% if (comentarisSuspesos == 1) {%>
                <div id="comentarisSuspesos">(Tens <a href="/<%=Configuracio.contextWebAplicacio%>/cerca/comentarisSuspesos.jsp"><%=comentarisSuspesos%> <%=XMLCollection.getProperty("cerca.comentari", sLang)%></a> <%=XMLCollection.getProperty("cerca.suspes", sLang)%>)</div>
                <% } %>
                <% if (comentarisSuspesos > 1) {%>
                <div id="comentarisSuspesos">(Tens <a href="/<%=Configuracio.contextWebAplicacio%>/cerca/comentarisSuspesos.jsp"><%=comentarisSuspesos%> <%=XMLCollection.getProperty("cerca.comentaris", sLang)%></a> <%=XMLCollection.getProperty("cerca.suspesos", sLang)%>)</div>
                <% }%>	    
                | <a href="/<%=Configuracio.contextWebAplicacio%>/administracio/logout.jsp"><%=XMLCollection.getProperty("cerca.directoriInicial.logout", sLang)%></a>  	    
                <%  } else {%>
                <a href="/<%=Configuracio.contextWebAplicacio%>/loginSSO.jsp?logOn=true"><%=XMLCollection.getProperty("cerca.directoriInicial.login", sLang)%></a>
                <%  }%>
            </div>

            <div id="header_formulari">
                <div id="header_formulari_left">
                    <a href="<%=urlLocal%>/cerca/directoriInicial.jsp"><img class="img_no_border" src="../imatges/logo_cercador.gif" alt="cercador"></a>
                </div>
                <div id="header_formulari_right">
                    <h1>
                        <a href="/<%=Configuracio.contextWebAplicacio%>/cerca/cercaCompleta.jsp"><%=XMLCollection.getProperty("cerca.cercaSimple.cercaCompleta", sLang)%></a> |		
                        <a href="/<%=Configuracio.contextWebAplicacio%>/cerca/directoriInicial.jsp"><%=XMLCollection.getProperty("cerca.cercaCompleta.catalegMerli", sLang)%></a> |		
                        <a target=\"_blank\" href="/<%=Configuracio.contextWebAplicacio%>/ajuda.jsp"><%=XMLCollection.getProperty("cerca.cercaSimpleTest.ajuda", sLang)%></a>
                    </h1>
                </div>
                <div class="clear"></div>
            </div> <!-- header_formulari -->

            <div id="barra_resultats">
                <div id="barra_resultats_left">
                    Cerca simple
                </div>
                <div id="barra_resultats_right">
                </div>
                <div class="clear"></div>
            </div>  <!-- barra_resultats -->





            <div id="cercador_complet">  
                <form name="cerca" action="/<%=Configuracio.contextWebAplicacio%>/ServletCerca" method="get">
                    <input type="hidden" name="tipus" value="simple"/>
                    <input type="hidden" name="nivell" value="0"/>
                    <input type="hidden" name="ordenacio" value=""/>
                    <input type="hidden" name="direccio" value=""/>
                    <input type="hidden" name="novaCerca" value="si"/>
                    <input type="hidden" name="userGeneric" value="<%=userGeneric%>"/>
                    <input type="hidden" name="nomUsuari" value="<%=usuari%>"/>                 
                    <fieldset>
                        <br/>
                        <label for="textCerca"><%=XMLCollection.getProperty("cerca.cercaSimpleTest.textLliure", sLang)%>: </label>						   
                        <input class="text_lliure" type="text" id="textCerca" name="textCerca" value="<%=XMLCollection.getProperty("cerca.cercaSimpleTest.textInicial", sLang)%>" onClick="javascript:document.cerca.textCerca.value = '';"/>
                        <br/>

                        <label for="nivell_educatiu"><%=XMLCollection.getProperty("cerca.cercaSimpleTest.nivellEducatiu", sLang)%>: </label>
                        <select id="nivell_educatiu" name="nivell_educatiu" onchange="change_area()">		   
                            <option value="-1" selected ><%=XMLCollection.getProperty("cerca.select.nivell", sLang)%></option>
                            <%

                                i = 0;
                                while (i < allLevels.size()) {
                                    DucObject ducObject = (DucObject) allLevels.get(i);
                                    // FIXME: Treure quan FP estigui disponible al DUC
                                    //if(ducObject.getTerm(sLang).indexOf("FP")<0){
%>   
                            <option value="<%=ducObject.id%>"><%=ducObject.getTerm(sLang)%></option>
                            <%
                                    //}
                                    i++;
                                }
                            %>
                        </select>
                        <br/>
                        <label for="area_curricular"><%=XMLCollection.getProperty("cerca.cercaSimpleTest.areaCurricular", sLang)%>: </label>
                        <select id="area_curricular" name="area_curricular" onchange="save_area()">		   
                            <option value="-1" selected ><%=XMLCollection.getProperty("cerca.select.area", sLang)%></option>
                        </select>
                        <br/>

                        <button class="submit" onClick="javascript:doSubmit();
                                return false;"><%=XMLCollection.getProperty("cerca.cercaSimpleTest.cerca", sLang)%></button>
                                                <!--<input class="submit" type="submit" name="cerca" value="<%=XMLCollection.getProperty("cerca.cercaSimpleTest.cerca", sLang)%>"/>-->
                        <br/>
                    </fieldset>
                </form>	

            </div>
        </div>
        <!--	<div class="footer_cercador">
                  <div>
                        <img style="float:right" src="../imatges/xtec_edu365.gif" alt="">
              </div>		
                </div>
        -->
        <div id="footer">
            <!-- <div> -->
            <img style="float:left;margin-left:10px;margin-bottom:20px" src="<%=urlLocal%>/imatges/gencat_logo.png"/>
            <a href="http://www.edu365.cat" target="_blank"><img style="float:right" src="<%=urlLocal%>/img/edu_logo.png" alt="Edu365" title="Edu365"></a>
            <img style="float:right" src="<%=urlLocal%>/img/footer_spacer.png" alt="">
                <a href="http://www.xtec.cat" target="_blank"><img style="float:right" src="<%=urlLocal%>/img/xtec_logo.png" alt="XTEC" title="XTEC"></a>		
                <!-- </div>		 -->
        </div>



        <%
            } catch (Exception e) {
                logger.error(e);
            } finally {
                try {
                    myConnection.close();
                } catch (Exception e) {
                    logger.error(e);
                }
            }
        %>   

    </body>
</html>
