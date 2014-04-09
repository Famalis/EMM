<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
	"http://www.w3.org/TR/html4/loose.dtd">

<html ng-app>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Welcome to Spring Web MVC project</title>
		<script type="text/javascript" src="http://code.jquery.com/jquery-latest.min.js"></script>
		<script type="text/javascript" src="https://ajax.googleapis.com/ajax/libs/angularjs/1.3.0-beta.5/angular.min.js"></script>
		<script type="text/javascript" src="/TeaClassifier/resources/js/IndexCtrl.js"></script>
    </head>

    <body ng-controller="IndexCtrl" bgcolor="grey">
		<div style="position: absolute; left: 0px; height: 200px; width: 100%; background-color: lightblue">
			Rodzaj herbaty:
			<select>
				<option ng-model="teaType" value="black tea">Czarna</option>
				<option ng-model="teaType" value="white tea">Biała</option>
				<option ng-model="teaType" value="green tea">Zielona</option>
			</select>
			Dodatek:
			<select>
				<option ng-model="addition" value="none">Brak</option>
				<option ng-model="addition" value="lemon">Cytryna</option>
				<option ng-model="addition" value="milk">Mleko</option>
			</select>
			<input type="text" ng-model="sugar" placeholder="Ile cukru..." value="0"/>
		</div>
		<div style="position: absolute; left: 0px; top: 210px; width: 200px; background-color: lightblue" id="trainingSet">
			<p>
				Dane do nauki:
			</p>
			<c:forEach var="item" items="${trainingSet}">
				<p style="border: 1px solid">${item}</p>
			</c:forEach>
		</div>
		<div style="position: absolute; left: 210px; top: 210px; background-color: lightblue" id="printGraph"></div>
		<script type="text/javascript">
			var tree = ${treeJson};
			var nodesList = ${nodesList};
			function drawLine(x1, y1, x2, y2, label) {
				var midX = getMidPoint(x1, y1, x2, y2)[0] + 5;
				var midY = getMidPoint(x1, y1, x2, y2)[1];
				var html = ""
				html += "<line x1='" + x1 + "' y1='" + y1 + "' x2='" + x2 + "' y2='" + y2 + "' style='stroke:rgb(0,0,0);stroke-width:2' />";
				html += "<text x='" + (midX) + "' y='" +
						(midY) + "' fill='red'" +
						" transform='rotate(" + getAngleBetweenPoints(x1, y1, x2, y2) + " " + midX + "," + midY + ")'>" + label + "</text>";
				html += "";
				return html;
			}
			;

			function drawNode(x1, y1, node) {
				var html = "";
				html += "<ellipse cx=" + x1 + " cy=" + y1 + " rx='10' ry='10' style='fill:white;stroke:black;stroke-width:2' />"
				if (node.classValue != null) {
					html += "<text x='" + (x1 - 20) + "' y='" + (y1 + 30) + "' fill='black' style='font-size: 12px'>" + node.classValue + "</text>";
				} else {
					html += "<text x='" + (x1 + 13) + "' y='" + (y1) + "' fill='black'>" + node.attribute + "</text>";
				}
				return html;
			}
			;

			function drawGraph(width, height, tree) {
				var html = "<svg width='" + width + "' height='" + height + "'>"
				var x = width / 2;
				var y = 15;
				html += drawNode(x, y, tree.root);
				html += drawChildren(tree.root, (x) - width * 0.3, (x) + width * 0.3, y + 200, x, y, 0.2);
				html += "</svg>";
				return html;
			}
			;

			function drawChildren(parent, minWidth, maxWidth, height, px, py, newMod) {
				var html = "";
				var x = minWidth;
				var y = height;
				var labelHeightMod = 0;
				for (var i = 0; i < parent.children.length; i++) {
					var child = parent.children[i];
					html += drawNode(x, y, child);
					html += drawLine(px, py, x, y, child.label);
					labelHeightMod += 20;
					var newMinWidth = x - ((maxWidth - minWidth) * newMod);
					var newMaxWidth = x + ((maxWidth - minWidth) * newMod);
					html += drawChildren(child, newMinWidth, newMaxWidth, height + 200, x, y, newMod*0.3);
					x += ((maxWidth - minWidth)) / (parent.children.length - 1);
				}
				return html;
			}

			function getMidPoint(x1, y1, x2, y2) {
				var midX = ((x1 + x2) / 2);
				var midY = ((y1 + y2) / 2);
				return [midX, midY];
			}

			function getAngleBetweenPoints(x1, y1, x2, y2) {
				var angleDeg = Math.atan2(y2 - y1, x2 - x1) * 180 / Math.PI;
				return angleDeg;
			}
			document.getElementById("printGraph").innerHTML = drawGraph(1800, 1000, tree);
		</script>
	</body>
</html>
