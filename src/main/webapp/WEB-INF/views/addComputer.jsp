<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title><spring:message code="title" /></title>
<link href="static/css/bootstrap.min.css" rel="stylesheet"
	media="screen">
<link href="static/css/font-awesome.css" rel="stylesheet" media="screen">
<link href="static/css/main.css" rel="stylesheet" media="screen">
</head>
<body>

	<header class="navbar navbar-inverse navbar-fixed-top">
	<div class="container">
		<a class="navbar-brand" href="dashboard"> <spring:message
				code="dashboard.title" />
		</a>
	</div>
	</header>

	<section id="main">
	<div class="container">
		<div class="row">
			<div class="col-xs-8 col-xs-offset-2 box">
				<h1>
					<spring:message code="dashboard.add" />
				</h1>
				<form:form action="addComputer" method="POST"
					modelAttribute="computerDTO">
					<fieldset>
						<div class="form-group">
							<label for="computerName">
								<spring:message code="dashboard.name" var="computerName" />
									${computerName}
							</label>
							<form:input type="text" data-validation="custom"
								data-validation-regexp="^[\wÀ-ÿ]+[\wÀ-ÿ_\-'\+\* ]+$"
								class="form-control" id="computerName" path="name"
								placeholder="${computerName}" name="name"></form:input>
						</div>
						<div class="form-group">
							<label for="introduced">
								<spring:message code="dashboard.introduced"
									var="computerIntroduced" /> 
									${computerIntroduced}
							</label>
							<form:input type="date" data-validation="date"
								data-validation-format="yyyy-mm-dd"
								data-validation-optional="true" class="form-control"
								id="introduced" path="introduced" placeholder="${computerIntroduced}"
								name="introduced"></form:input>
						</div>
						<div class="form-group">
							<label for="discontinued">
								<spring:message code="dashboard.discontinued"
									var="computerDiscontinued" /> 
									${computerDiscontinued}
							</label>
							<input type="date" data-validation="date"
									data-validation-format="yyyy-mm-dd"
									data-validation-optional="true" class="form-control"
									id="discontinued" path="discontinued" placeholder="${computerDiscontinued}"
									name="discontinued">
						</div>
						<div class="form-group">
							<form:label for="companyId" path="manufacturer">
								<spring:message code="dashboard.company" />
							</form:label>
							<select class="form-control" id="companyId" name="manufacturer">
								<option value="null"></option>
								<c:forEach items="${companyList}" var="company">
									<option value="${company.id}">${company.name}</option>
								</c:forEach>
							</select>
						</div>
					</fieldset>

					<div class="actions pull-right">
						<input type="submit"
							value="<spring:message code="dashboard.add2"/>"
							class="btn btn-primary">
						<spring:message code="or" />
						<a href="dashboard" class="btn btn-default"><spring:message
								code="dashboard.cancel" /></a>
					</div>
				</form:form>
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
			lang : 'en'
		});
	</script>
</body>
</html>