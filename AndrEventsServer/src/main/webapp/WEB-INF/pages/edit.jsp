<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<t:template>
	<jsp:attribute name="page_title">
      Welcome
    </jsp:attribute>
	<jsp:attribute name="title">
      <h1>Welcome ${name}</h1>
    </jsp:attribute>
	<jsp:attribute name="body">
      	<form:form id="form" action="/events/edit" method="post"
			modelAttribute="evenement" role="form" class="form-horizontal">
      		<form:input type="hidden" path="id" value="${evenement.id}" />
      		
      		<div class="form-group">
			    <label for="nom" class="col-sm-2 control-label">Nom : </label>
			    <div class="col-sm-10">
					<form:input id="nom" path="nom" type="text" class="form-control"
						value="${evenement.nom}" />
			    </div>
			</div>
      		
      		<div class="form-group">
			    <label for="dateDebut" class="col-sm-2 control-label">Date Debut : </label>
			    <div class="col-sm-10">
					<form:input id="dateDebut" path="dateDebut" class="form-control"
						value="${evenement.dateDebut}" />
			    </div>
			</div>
      		
      		<div class="form-group">
      			<label for="dateFin" class="col-sm-2 control-label">Date Fin : </label>
			    <div class="col-sm-10">
					<form:input id="dateFin" path="dateFin" class="form-control"
						value="${evenement.dateFin}" />
			    </div>
			</div>
      		
      		<div class="form-group">
				<div class="col-sm-offset-2 col-sm-10">
					<button type="submit" class="btn btn-success">Valider</button>
				</div>
			</div>
      	</form:form>
	</jsp:attribute>
</t:template>