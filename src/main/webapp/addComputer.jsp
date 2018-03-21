<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Computer Database</title>
<link href="static/css/bootstrap.min.css" rel="stylesheet"
	media="screen">
<link href="static/css/font-awesome.css" rel="stylesheet" media="screen">
<link href="static/css/main.css" rel="stylesheet" media="screen">
</head>
<body>

	<header class="navbar navbar-inverse navbar-fixed-top">
	<div class="container">
		<a class="navbar-brand" href="dashboard.html"> Application -
			Computer Database </a>
	</div>
	</header>

	<%-- <c:choose>
	<c:redirect url="AddComputerServlet"/>
	</c:choose>
	  --%>

	<section id="main">
	<div class="container">
		<div class="row">
			<div class="col-xs-8 col-xs-offset-2 box">
				<h1>Add Computer</h1>
				<form action="${pageContext.request.contextPath}/createProduct"
					method="POST">
					<fieldset>
						<div class="form-group">
							<label for="computerName">Computer name</label> <input
								type="text" class="form-control" id="computerName"
								placeholder="Computer name" value=${product.name}>
						</div>
						<div class="form-group">
							<label for="introduced">Introduced date</label> <input
								type="date" class="form-control" id="introduced"
								placeholder="Introduced date" value=${product.introduced}>
						</div>
						<div class="form-group">
							<label for="discontinued">Discontinued date</label> <input
								type="date" class="form-control" id="discontinued"
								placeholder="Discontinued date" value=${product.discontinued}>
						</div>


						<div class="form-group">
							<label for="companyId">Company</label> 
							<select class="form-control" id="companyId">
								<c:forEach items="${companyList}" var="company">
									<option value="${company.id}">${company.name}</option>

								</c:forEach>
							</select>
						</div>

					</fieldset>

					<div class="actions pull-right">
						<input type="submit" value="Add" class="btn btn-primary">
						or <a href="dashboard.jsp" class="btn btn-default">Cancel</a>
					</div>

				</form>
			</div>
		</div>
	</div>
	</section>
</body>
</html>