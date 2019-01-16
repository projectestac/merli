<%@ tag description="User manager" %>

<%@ attribute name="name" required="true" %>
<%@ attribute name="mail" required="true" %>

<div id="${name}" class="element"><%--
--%><input name="usuaris" value="${name}" type="radio"><%--
--%><span class="title">${name}</span><%--
--%><span class="attribute">${mail}</span><%--
--%><img
     id="img${name}"
     src="web/images/avall.png"
     class="operacio" alt="Operacions"
     onclick="operationInfo(this, '${name}'); cancelAdd();"><%--
--%><div class="modifperm" id="perm${name}op"></div>
</div>
