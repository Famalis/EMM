<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
	"http://www.w3.org/TR/html4/loose.dtd">

<html ng-app>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Welcome to Spring Web MVC project</title>
		<script src="https://ajax.googleapis.com/ajax/libs/angularjs/1.3.0-beta.5/angular.min.js"></script>
		<script type="text/javascript" src="/TeaClassifier/resources/js/index.js"></script>
    </head>

    <body ng-controller="IndexCtrl">
        <form action="/customData.htm">
			<textarea name="data">
				
			</textarea>
			<input type="submit" value="Rebuild tree"/>
		</form>
		${tree}
    </body>
</html>
