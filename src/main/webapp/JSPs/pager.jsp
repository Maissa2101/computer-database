<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Pager</title>
</head>
<body>
    

    <footer class="navbar-fixed-bottom">
        <div class="container text-center">
            <ul class="pagination">
                <li>
                	<c:if test="${from1 >= 0 }">
                    <a href="DashboardServlet?from=${from1}&to=${to1}" aria-label="Previous"></c:if>
                      <span aria-hidden="true">&laquo;</span>
                  	</a>
                  	
              </li>
              <li><a href="DashboardServlet">1</a></li>
              <li><a href="DashboardServlet?from=${from2}&to=${to2}">2</a></li>
              <li><a href="DashboardServlet?from=${from2}&to=${to2}">3</a></li>
              <li><a href="DashboardServlet?from=${from2}&to=${to2}">4</a></li>
              <li><a href="DashboardServlet?from=${from2}&to=${to2}">5</a></li>
              <li>
                <a href="DashboardServlet?from=${from2}&to=${to2}" aria-label="Next">
                    <span aria-hidden="true">&raquo;</span>
                </a>
            </li>
        </ul>
		</div>
        <div class="btn-group btn-group-sm pull-right" role="group" >
            <button type="button" class="btn btn-default">10</button>
            <button type="button" class="btn btn-default">50</button>
            <button type="button" class="btn btn-default">100</button>
        </div>

    </footer>		
		
</body>
</html>