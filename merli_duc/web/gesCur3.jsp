<?xml version="1.0" encoding="ISO-8859-1" ?>
<%@ include file="/web/comu/taglibs.jsp"%>
<html:html locale="true">
<meta http-equiv="content-type" content="text/html;charset=ISO-8859-1"/>
<link rel="shortcut icon" href="web/images/favicon_duc.ico" type="image/x-icon">
<link rel="icon" href="web/images/favicon_duc.ico" type="image/x-icon">
	<title><bean:message key="application.title"/></title>
<head>
<%@ include file="/web/comu/header.jsp"%>
</head>
<jsp:useBean id="semanticnet" scope="session" class="edu.xtec.gescurriculum.semanticnet.SemanticInterface"/>

<body onload="init();">
<html:form action="gesNodes.do">
<div class="title"><bean:message key="application.title"/>
<span onclick="modeC()">C</span> <span onclick="modeD()">D</span>
<span>Benvingut <%
	if (request.getSession() != null && request.getSession().getAttribute("user") != null){
		out.println(((edu.xtec.merli.segur.User)request.getSession().getAttribute("user")).getUser());	
	}else{
		%><jsp:getProperty name="NodeForm" property="user"/><%
	}
%> !</span>
</div>
 
<div class="curriculum" id="divlevel">
	<div class="title"><bean:message key="application.level"/></div>
	<div class="content">
		<PHTM:ul id="level" name="NodeForm" property="idlevel"></PHTM:ul>
	</div>
	<div class="operacions">
		<a href="#" id="levelAmunt" onclick="amunt(this,'level')"><img class="operacio" src="web/images/amunt.png" alt="Amunt"/></a>
		<a href="#" id="levelAvall" onclick="avall(this,'level')"><img class="operacio" src="web/images/avall.png" alt="Avall"/></a>
		<a href="#" id="levelAdd" onclick="insert(this,'level');"><img class="operacio" src="web/images/add.png" alt="Add"/></a>
		<a href="#" id="levelDel" onclick="delNode(this,'level');"><img class="operacio" src="web/images/del.png" alt="Del"/></a>
		<a href="#" id="levelFill" onclick="insertFill(this,'level');">F</a>
		<%
		if (request.getParameter("nodeType") != null && request.getParameter("nodeType").compareTo("level")==0){
			%><html:errors property="operation"/>
			<html:errors property="delnode"/><%
		}
		%>		
	</div>
</div>
<div class="curriculum" id="divarea">
	<div class="title"><bean:message key="application.area"/></div>
	<div class="content">
	<PHTM:ul id="area" name="NodeForm" property="idarea"  ></PHTM:ul>
	</div>	
	<div class="operacions">
		<a href="#" id="areaAmunt" onclick="amunt(this,'area')"><img class="operacio" src="web/images/amunt.png" alt="Amunt"/></a>
		<a href="#" id="areaAvall" onclick="avall(this,'area')"><img class="operacio" src="web/images/avall.png" alt="Avall"/></a>
		<a href="#" id="areaAdd" onclick="insert(this,'area');"><img class="operacio" src="web/images/add.png" alt="Add"/></a>
		<a href="#" id="areaDel" onclick="delNode(this,'area');"><img class="operacio" src="web/images/del.png" alt="Del"/></a>
		<%
		if (request.getParameter("nodeType") != null && request.getParameter("nodeType").compareTo("area")==0){
			%><html:errors property="operation"/>
			<html:errors property="delnode"/><%
		}
		%>		
	</div>
</div>
<div class="curriculum" id="divcontent">
	<div class="title"><bean:message key="application.content"/></div>
	<div class="pestanya">
		<table>
			<tr>
				<td id="pos1" onclick="pestanya('contCC','contCP', 'contCA')"><bean:message key="application.content.cc"/></td>
				<td id="pos2" onclick="pestanya('contCP','contCC', 'contCA')"><bean:message key="application.content.cp"/></td>
				<td id="pos3" onclick="pestanya('contCA','contCP', 'contCC')"><bean:message key="application.content.ca"/></td>
			</tr>
		</table>
	</div>
	<div class="content">
		<PHTM:ul id="content" name="NodeForm" property="idcontent"  ></PHTM:ul>
	</div>
	<div class="operacions">
		<a href="#" id="contentAmunt" onclick="amunt(this,'content')"><img class="operacio" src="web/images/amunt.png" alt="Amunt"/></a>
		<a href="#" id="contentAvall" onclick="avall(this,'content')"><img class="operacio" src="web/images/avall.png" alt="Avall"/></a>
		<a href="#" id="contentAdd" onclick="insert(this,'content')"><img class="operacio" src="web/images/add.png" alt="Add"/></a>
		<a href="#" id="contentDel" onclick="delNode(this,'content');"><img class="operacio" src="web/images/del.png" alt="Del"/></a>
		<a href="#" id="contentFill" onclick="insertFill(this,'content');">F</a>
		<%
		if (request.getParameter("nodeType") != null && request.getParameter("nodeType").compareTo("content")==0){
			%><html:errors property="operation"/>
			<html:errors property="delnode"/><%
		}
		%>		
	</div>
</div>
<div class="curriculum" id="divobjective">
	<div class="title"><bean:message key="application.objective"/></div>
	<div class="pestanya">
			<table>
				<tr>
					<td id="pos2" onclick="pestanya('objOP','objOT')"><bean:message key="application.objective.principal"/></td>
					<td id="pos3" onclick="pestanya('objOT','objOP')"><bean:message key="application.objective.terminal"/></td>
				</tr>
			</table>
	</div>
	<div class="content">
			<PHTM:ul id="objective" name="NodeForm" property="idobjective"  ></PHTM:ul>			
	</div>
	<div class="operacions">
		<a href="#" id="objectiveAmunt" onclick="amunt(this,'objective')"><img class="operacio" src="web/images/amunt.png" alt="Amunt"/></a>
		<a href="#" id="objectiveAvall" onclick="avall(this,'objective')"><img class="operacio" src="web/images/avall.png" alt="Avall"/></a>
		<a href="#" id="objectiveAdd" onclick="insert(this,'objective');alert(document.NodeForm.idContent.value);"><img class="operacio" src="web/images/add.png" alt="Add"/></a>
		<a href="#" id="objectiveDel" onclick="delNode(this,'objective');"><img class="operacio" src="web/images/del.png" alt="Del"/></a>
		<%
		if (request.getParameter("nodeType") != null && request.getParameter("nodeType").compareTo("objective")==0){
			%><html:errors property="operation"/>
			<html:errors property="delnode"/><%
		}
		%>		
	</div>
</div>

<div class="formulari">
	<div class="title"><bean:message key="application.nodeform.title"/></div>
	<div class="content">		
			<html:hidden property="idLevel" ></html:hidden>
			<html:hidden property="idArea" ></html:hidden>
			<html:hidden property="idContent" ></html:hidden>
			<html:hidden property="idObjective" ></html:hidden>
			<html:hidden property="idNode" ></html:hidden>
			<html:hidden property="idKey" ></html:hidden>
			<html:hidden property="selecPath"></html:hidden>
			<html:hidden property="navPath"></html:hidden>
			<html:hidden property="operacio"></html:hidden>
			<html:hidden property="entornOperacio"></html:hidden>
			<div>
			<bean:message key="application.nodeform.term"/>:<html:text property="term" ></html:text>
			</div>
			<div id="formNodeType">
				<bean:message key="application.nodeform.nodeType"/>: <html:select onchange="javascript:chtype(this);" property="nodeType" >
								<html:option value="level" ><bean:message key="application.level"/></html:option>
								<html:option value="area" ><bean:message key="application.area"/></html:option>
								<html:option value="content" ><bean:message key="application.content"/></html:option>
								<html:option value="objective" ><bean:message key="application.objective"/></html:option>
							</html:select>
			</div>
			<div>
			<bean:message key="application.nodeform.description"/>:<html:textarea property="description"  rows="8" cols="20" ></html:textarea>
			</div>
			<div id="formcontent">
				<bean:message key="application.nodeform.category"/>:<br/>
					<PHTM:Radio id="content" name="category"/>
				<br/>
			</div>
			<div id="formobjective">
				<bean:message key="application.nodeform.category"/>:<br/>
				<PHTM:Radio id="objective" name="category"/>
				<br/>
			</div>
			<div id="formlevel">
				<bean:message key="application.nodeform.category"/>: <br/>
				<PHTM:Radio id="level" name="category"/>
				<br/>
			</div>
			<div class="opcional"><div class="option" onclick="swapDisplay('formThesaure')"><bean:message key="application.nodeform.thesaurus"/></div>				
			</div>			
			<div class="opcional"><div class="option" onclick="swapDisplay('formObservacions')"><bean:message key="application.nodeform.observations"/></div>
				<div id="formObservacions">
				<jsp:getProperty name="NodeForm" property="lastNotes"/>
				<html:textarea property="note"  rows="8" cols="20" ></html:textarea>
				</div>
			</div>
			<div class="opcional"><div class="option" onclick="swapDisplay('formReferencia')"><bean:message key="application.nodeform.references"/></div>
				<div id="formReferencia">
				<html:textarea property="references"  rows="8" cols="20" ></html:textarea>
				</div>
			</div>
<script type="text/javascript">
 setCapes('<jsp:getProperty name="NodeForm" property="contentcategory"/>','<jsp:getProperty name="NodeForm" property="objectivecategory"/>');
 setCategory('<jsp:getProperty name="NodeForm" property="category"/>');
 var zone="<jsp:getProperty name="NodeForm" property="nodeType"/>";
 </script>
			<html:reset><bean:message key="application.reset"/></html:reset>
			<html:submit><bean:message key="application.submit"/></html:submit>
	</div>
	<html:errors property="addnode"/>
	<html:errors property="setnode"/>
</div>
	<div id="formThesaure">
	<PHTM:ul id="thesaurus" name="NodeForm" property="idlevel"></PHTM:ul>
	</div>
</html:form>
</body>
</html:html>