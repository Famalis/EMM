<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
	"http://www.w3.org/TR/html4/loose.dtd">

<html ng-app>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Welcome to Spring Web MVC project</title>
		<script src="https://ajax.googleapis.com/ajax/libs/angularjs/1.3.0-beta.5/angular.min.js"></script>
		<script type="text/javascript" src="/TeaClassifier/resources/js/index.js"></script>
		<link href="/TeaClassifier/resources/css/JSTreeGraph.css" rel="stylesheet" type="text/css" />
		<script src="/TeaClassifier/resources/js/JSTreeGraph.min.js" type="text/javascript"></script>
		<script src="http://code.jquery.com/jquery-latest.min.js"></script>
    </head>

    <body ng-controller="IndexCtrl">
		<div id="dvTreeContainer"></div>
		<div id="printHere"></div>
		<script type="text/javascript">
			/*
				function printNodes(parent, x, y) {
					var line = "<svg style='position: absolute; left:" + x + "px; top:" + y + "px' width='200' height='200'>";
					line += "<circle cx='35' cy='35' r='35' fill='black'/>";
					if (parent.classValue != null) {
						line += "<text style='font-size: 11px' x='8' y='40' fill='red'>" + parent.classValue + " " + x + "</text></svg>";
					} else {
						line += "<text style='font-size: 11px' x='8' y='40' fill='red'>" + parent.attribute + " " + x + "</text></svg>";
					}
					var newX = x;
					var newY = y;
					newX = x + x / 2;
					newY = y + 150;
					var tmpX = 0;
					for (var i = 0; i < parent.children.length; i++) {
						tmpX += (newX / parent.children.length);
						var node = parent.children[i];
						line += "</br>" + printNodes(node, tmpX, newY);
					}
					return line;
				}
				*/
				function addChildren(parent, parentNode) {
					for (var i = 0; i<parent.children.length; i++) {
						var node = parent.children[i];
						var content;
						if(node.classValue != null) {
							content = node.classValue;
						} else {
							content = node.attribute;
						}
						parentNode.Nodes[i] = {Content: content, ToolTip: node.label};
						parentNode.Nodes[i].Nodes = new Array();
						addChildren(node, parentNode.Nodes[i]);
					}
				}
				var tree = ${treeJson};
				var a = tree.root;
				var rootNode = new Object();
				rootNode.Content = tree.root.attribute;
				rootNode.Nodes = new Array();
				
				addChildren(tree.root, rootNode);

				// Get the reference to a container layer
				var container = document.getElementById("dvTreeContainer");

				// Build tree with options
				DrawTree({
					Container: container,
					RootNode: rootNode
				});
				//document.getElementById("printHere").innerHTML = printNodes(tree.root, 500, 0);


		</script>
		${treeHtml}
	</body>
</html>
