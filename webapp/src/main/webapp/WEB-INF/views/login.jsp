<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

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
				code="dashboard.title" /></a>
				            <div class="pull-right">
            	<a href="?lang=fr">FR</a>
            	<a href="?lang=en">EN</a>
			</div>
	</div>
	</header>

	<section id="main">
	<div class="container">
		<div class="row">
			<div class="col-xs-8 col-xs-offset-2 box">
				<input type="hidden" name="${_csrf.parameterName}"
					value="${_csrf.token}" />
				<h1>Login</h1>

				<c:if test="${param.error != null}">
					<div class="alert alert-danger">
						Invalid pseudo and password. <br />
					</div>
				</c:if>
				<c:if test="${param.logout != null}">
					<div class="alert alert-success">
						You have been logged out. <br />
					</div>
				</c:if>
				<form:form action="login" method="POST">
					<fieldset>
						<div class="form-group">
							<label>Pseudo</label> <input type="text" id="pseudo"
								name="pseudo" class="form-control" value="" />
						</div>
						<div class="form-group">
							<label>Password</label> <input type="password" id="password"
								class="form-control" name="password" />
						</div>
					</fieldset>
					<div class="actions pull-right">
						<input type="submit" name="submit" value="Submit"
							class="btn btn-primary">
					</div>
				</form:form>
			</div>
		</div>
	</div>
	</section>


</body>
</html>