<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
	"http://www.w3.org/TR/html4/loose.dtd">

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Welcome to Spring Web MVC project</title>
    </head>

    <body>
		<table>
			<tr>
				<th>
					White
				</th>
				<th>
					Dark
				</th>
				<th>
					Flat
				</th>
			</tr>
			<tr>
				<td>
					<div style="width: 500px; height: 300px; overflow: auto">
						<c:forEach items="${map1}" var="set">
							${set}<br/>
						</c:forEach>
					</div>					
				</td>
				<td>
					<div style="width: 500px; height: 300px; overflow: auto">
						<c:forEach items="${map2}" var="set">
							${set}<br/>
						</c:forEach>
					</div>
				</td>
				<td>
					<div style="width: 500px; height: 300px; overflow: auto">
						<c:forEach items="${map3}" var="set">
							${set}<br/>
						</c:forEach>
					</div>
				</td>
			</tr>
			<tr>
				<th>
					Cheese
				</th>
				<th>
					Ham
				</th>
				<th>
					Cottage cheese
				</th>
			</tr>
			<tr>
				<td>
					<div style="width: 500px; height: 300px; overflow: auto">
						<c:forEach items="${map4}" var="set">
							${set}<br/>
						</c:forEach>
					</div>					
				</td>
				<td>
					<div style="width: 500px; height: 300px; overflow: auto">
						<c:forEach items="${map5}" var="set">
							${set}<br/>
						</c:forEach>
					</div>
				</td>
				<td>
					<div style="width: 500px; height: 300px; overflow: auto">
						<c:forEach items="${map6}" var="set">
							${set}<br/>
						</c:forEach>
					</div>
				</td>
			</tr>
			<tr>
				<th>
					None
				</th>
				<th>
					Ketchup
				</th>
				<th>
					Honey mustard
				</th>
			</tr>
			<tr>
				<td>
					<div style="width: 500px; height: 300px; overflow: auto">
						<c:forEach items="${map7}" var="set">
							${set}<br/>
						</c:forEach>
					</div>					
				</td>
				<td>
					<div style="width: 500px; height: 300px; overflow: auto">
						<c:forEach items="${map8}" var="set">
							${set}<br/>
						</c:forEach>
					</div>
				</td>
				<td>
					<div style="width: 500px; height: 300px; overflow: auto">
						<c:forEach items="${map9}" var="set">
							${set}<br/>
						</c:forEach>
					</div>
				</td>
			</tr>
		</table>
    </body>
</html>
