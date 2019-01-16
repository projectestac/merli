<%@ tag description="Pagination" %>
<%@ taglib prefix="core" uri="http://java.sun.com/jsp/jstl/core" %>

<%@ attribute name="last" required="true" %>
<%@ attribute name="current" required="true" %>

<div class="pagination">
  <div class="pagination-meta">
    Pàgina ${current} de ${last}
  </div>
  <div class="pagination-links">
    <core:if test="${current != 1}">
      <a href="./usuaris.do?page=${current - 1}">
        « Anterior
      </a>
    </core:if>
    <core:if test="${current != last}">
      <a href="./usuaris.do?page=${current + 1}">
        Següent »
      </a>
    </core:if>
  </div>
</div>
