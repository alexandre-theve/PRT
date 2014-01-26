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
      			<label for="lieu" class="col-sm-2 control-label">Lieu : </label>
			    <div class="col-sm-10">
					<form:input id="lieu" path="lieu" class="form-control"
						value="${evenement.lieu}" />
			    </div>
			</div>
			
			<div class="form-group">
			    <label class="col-sm-2 control-label" for="dateDebut">Date debut : </label>
			    
			    <div class='input-group date col-sm-9'
					style="padding-left: 15px;" id='datetimepicker1'>
				  	<fmt:formatDate value="${evenement.dateDebut}" var="dateString"
						pattern="dd/MM/yyyy HH:mm" />
			      	<form:input class="form-control" type="text" id="dateDebut"
						path="dateDebut" readonly="true" value="${dateString}" />
                    <span class="input-group-addon"><span
						class="glyphicon glyphicon-calendar" /></span>
                
				</div>
			</div>
			
			<div class="form-group">
      			<label for="dateFin" class="col-sm-2 control-label">Date fin : </label>
			    <div class='input-group date col-sm-9'
					style="padding-left: 15px;" id='datetimepicker2'>
			    	<fmt:formatDate value="${evenement.dateFin}" var="dateFinString"
						pattern="dd/MM/yyyy HH:mm" />
					<form:input id="dateFin" type="datetime" readonly="true" path="dateFin"
						class="form-control" value="${dateFinString}" />
					<span class="input-group-addon"><span
						class="glyphicon glyphicon-calendar" /></span>
			    </div>
			</div>
			
			<div class="form-group">
      			<label for="latitude" class="col-sm-2 control-label">Latitude : </label>
			    <div class="col-sm-10">
					<form:input id="latitude" path="latitude" class="form-control"
						value="${evenement.latitude}" />
			    </div>
			</div>
			
			<div class="form-group">
      			<label for="longitude" class="col-sm-2 control-label">Longitude : </label>
			    <div class="col-sm-10">
					<form:input id="longitude" path="longitude" class="form-control"
						value="${evenement.longitude}" />
			    </div>
			</div>
			
			<div class="form-group">
      			<label for="description" class="col-sm-2 control-label">Description : </label>
			    <div class="col-sm-10">
					<form:input id="description" path="description"
						class="form-control" value="${evenement.description}" />
			    </div>
			</div>
			
			<div class="form-group">
      			<label for="valide" class="col-sm-2 control-label">Valide : </label>
      			<div class="col-sm-1">
					<form:checkbox id="valide" path="valide" class="form-control"
						value="${evenement.valide}" /> 
				</div>
			</div>
      		
      		<div class="form-group">
				<div class="col-sm-offset-2 col-sm-10">
					<button type="submit" style="width: 100%" class="btn btn-success">Valider</button>
				</div>
			</div>
      	</form:form>
      	
      	<script>
			$(function() {
				$('#datetimepicker1').datetimepicker({
					language: 'fr'
				});
				$('#datetimepicker2').datetimepicker({
					language: 'fr'
				});
			});
		</script>
	</jsp:attribute>
</t:template>