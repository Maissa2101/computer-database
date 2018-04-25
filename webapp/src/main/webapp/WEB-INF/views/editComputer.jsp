<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<!DOCTYPE html>
<html>
<head>
<title><spring:message code="title"/></title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<!-- Bootstrap -->
<link href="static/css/bootstrap.min.css" rel="stylesheet"
	media="screen">
<link href="static/css/font-awesome.css" rel="stylesheet" media="screen">
<link href="static/css/main.css" rel="stylesheet" media="screen">
</head>
<body>
	<header class="navbar navbar-inverse navbar-fixed-top">
		<div class="container">
			<a class="navbar-brand" href="dashboard"> <spring:message code="dashboard.title"/> </a>
		</div>
	</header>
	<section id="main">
		<div class="container">
			<div class="row">
				<div class="col-xs-8 col-xs-offset-2 box">
					<div class="label label-default pull-right">id:
						${computer.id}</div>
					<h1><spring:message code="editComputer.edit"/></h1>
					<c:if test="${not empty computer}">
						<form action="editComputer" method="POST">
							<input type="hidden" value="${computer.id}" id="id" name="id" />
							<fieldset>
								<div class="form-group">
									<label for="computerName"><spring:message code="dashboard.name"/></label> <input
										type="text" class="form-control" id="computerName"
										placeholder="Computer name" value="${computer.name}"
										name="name" data-validation="custom"
										data-validation-regexp="^[\wÀ-ÿ]+[\wÀ-ÿ_\-'\+\*\. ]+$">
								</div>
								<div class="form-group">
									<label for="introduced"><spring:message code="dashboard.introduced"/></label> <input
										type="date" class="form-control" id="introduced"
										placeholder="Introduced date" value="${computer.introduced}"
										name="introduced" data-validation="date"
										data-validation-format="yyyy-mm-dd"
										data-validation-optional="true">
								</div>
								<div class="form-group">
									<label for="discontinued"><spring:message code="dashboard.discontinued"/></label> <input
										type="date" class="form-control" id="discontinued"
										placeholder="Discontinued date"
										value="${computer.discontinued}" name="discontinued"
										data-validation="date" data-validation-format="yyyy-mm-dd"
										data-validation-optional="true">
								</div>
								<div class="form-group">
									<label for="companyId"><spring:message code="dashboard.company"/></label> <select
										class="form-control" id="companyId" name="manufacturer">
										<option value="null"></option>
										<c:forEach items="${companyList}" var="company">
											<option value="${company.id}">${company.name}</option>
										</c:forEach>
									</select>
								</div>
							</fieldset>
							<div class="actions pull-right">
								<input type="submit" value="Edit" class="btn btn-primary">
								<spring:message code="or"/> <a href="dashboard" class="btn btn-default"><spring:message code="dashboard.cancel"/></a>
							</div>
						</form>
					</c:if>
				</div>
			</div>
		</div>
	</section>

	<script
		src="//ajax.googleapis.com/ajax/libs/jquery/1.10.2/jquery.min.js"></script>
	<script
		src="//cdnjs.cloudflare.com/ajax/libs/jquery-form-validator/2.3.26/jquery.form-validator.min.js"></script>
	<script>
	  $.validate({
	    lang: 'en'
	  });
	</script>
</body>
</html>