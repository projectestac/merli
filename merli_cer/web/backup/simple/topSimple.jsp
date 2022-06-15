<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN"
		"http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">

<%@ page import="java.sql.*,simpple.xtec.web.util.Configuracio" %>
		
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="es" lang="es">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1" />
<title>Metacercador CAT365 - Panell de control</title>
<link REL="StyleSheet" HREF="../css/panellNew.css" TYPE="text/css"/>

</head>
<body>
<table width="100%" cellpadding="0" cellspacing="0">
  <tr class="panellCabecera" height="55">
    <td align="left" width="60%">&nbsp;&nbsp;Panell de Control Cercador</td>
    <td align="right" width="40%">&nbsp;</td>
  </tr>
  <tr>
    <td colspan="2">&nbsp;</td>
  </tr>  
</table>

<table width="96%" cellpadding="0" cellspacing="0" align="center" border="0">
  <tr> <td>
<div id="header">
<%
    if (Configuracio.isVoid()){
        Configuracio.carregaConfiguracio(); 
        } 
   
   String urlCercador = Configuracio.urlPanellCercador;
   String urlIndexador = Configuracio.urlPanellIndexador;   
   String urlLocal = "https://" + Configuracio.servidorWeb;   
   String contextWeb = Configuracio.contextWebAplicacio;   
%>
  <ul>
    <li><a href="<%=urlCercador%>/<%=contextWeb%>/general/puntuacions.jsp">General</a></li>
    <li><a href="<%=urlCercador%>/<%=contextWeb%>/edu365/fragments.jsp">edu365</a></li>
    <li id="current"><a href="<%=urlIndexador%>/<%=contextWeb%>/simple/fragments.jsp">Cercador simple professorat</a></li>
    <li><a href="<%=urlLocal%>/<%=contextWeb%>/complet/fragments.jsp">Cercador avan�at professorat</a></li>
    </ul>
    <img src="../img/pix_rojo.gif" width="100%" height="10" class="ImgBajoMenu" alt="" />
</div>
   </td></tr>
</table>


<table width="100%" border="0" height="450">
  <tr valign="top"> 
    <td>
		<table class="tableMenuEsq" cellpadding="0" border="0">
		  <tr>
		    <td rowspan="100" width="10" align="left">&nbsp;</td>
		    <td/>
		    <td rowspan="100" width="10" align="left">&nbsp;</td>
		  </tr>
		  <tr class="menuEsqNivell1">
		      <td>Cercador simple professorat</td>
		  </tr>
		  <tr class="blankStyle">
		      <td>&nbsp;</td>
		  </tr>
		  <tr class="menuEsqNivell2">
		      <td>Configuraci&oacute;</td>
		  </tr>
                  <tr class="menuEsqNivell3">
		      <td>&nbsp;&nbsp;&nbsp;<a href="<%=urlCercador%>/<%=contextWeb%>/simple/fragments.jsp">Configuraci� resultats</a></td>
		  </tr>
		  <tr class="menuEsqNivell3">
		      <td>&nbsp;&nbsp;&nbsp;<a href="<%=urlCercador%>/<%=contextWeb%>/simple/pesos.jsp">Pesos</a></td>
		  </tr>
		  <tr class="menuEsqNivell2">
		      <td>Logs</td>
		  </tr>
		  <tr class="menuEsqNivell3">
		      <td>&nbsp;&nbsp;&nbsp;<a href="<%=urlCercador%>/<%=contextWeb%>/simple/logs.jsp">Logs</a></td>
		  </tr>		  

		  <tr class="menuEsqNivell2">
		      <td>&nbsp;</td>
		  </tr>
		</table>
    </td>
    <td width="70%" height="100%">
    
