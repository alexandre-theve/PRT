<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<t:template>
	<jsp:attribute name="page_title">
      Welcome
    </jsp:attribute>
	<jsp:attribute name="title">
      <h1>Welcome ${name}</h1>
    </jsp:attribute>
	<jsp:attribute name="body">
			<c:if test="${not empty message}">
				<div class="row">    
			        <div class="alert alert-success">${message}</div>
		    	</div>
		    </c:if>
		    
		    <div class="row">
		    	<a type="submit" class="btn btn-success" style="margin-bottom: 10px" href="events/edit/0">Créer un evemenent</a>
		    </div>
		    
			<div class="row">
	            <table class="table table-bordered">
	                <thead>
	                    <tr>
	                        <th>Nom</th>
	                        <th>Date</th>
	                        <th>Lieu</th>
	                        <th>Description</th>
	                        <th></th>
	                    </tr>
	                </thead>
	                <tbody>
	                    <c:forEach var="evenement" items="${evenements}">                 	
	                        <tr>
	                            <td>${evenement.nom}</td>
	                            <td>${evenement.dateDebut}</td>
	                            <td>${evenement.lieu}</td>
	                            <td>${evenement.description}</td>
	                            <td>	
	                            	<div style="margin-bottom: 10px">
		                            	<a type="submit" class="btn btn-info" href="events/push/${evenement.id}" style="width: 49%;">Notification</a>
		                            	<a type="submit" class="btn btn-warning" href="events/edit/${evenement.id}" style="width: 49%;">Modifier</a>
	                            	</div>
	                            	<form:form id="formDelete${evenement.id}" action="/events/${evenement.id}" method="DELETE" class="form-horizontal" style="margin-bottom: 10px;">
	                            		<input type="submit" value="Supprimer" class="btn btn-danger" style="width: 100%;"></input>
	                            	</form:form>
	                            </td>
	                         </tr>
	                    </c:forEach>
					</tbody>
				</table>
			</div>
	</jsp:attribute>
</t:template>