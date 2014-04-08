<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
	"http://www.w3.org/TR/html4/loose.dtd">

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Welcome to Spring Web MVC project</title>
		<link href="/TeaClassifier/resources/css/JSTreeGraph.css" rel="stylesheet" type="text/css" />
		<script src="/TeaClassifier/resources/js/JSTreeGraph.min.js" type="text/javascript"></script>
		<script src="http://code.jquery.com/jquery-latest.min.js"></script>
		<link href="http://www.headjump.de/stylesheets/arrowsandboxes.css" rel="stylesheet" type="text/css" />
		<script type="text/javascript" src="http://code.jquery.com/jquery-1.4.1.min.js"></script>
		<script src="http://www.headjump.de/javascripts/jquery_wz_jsgraphics.js" type="text/javascript"></script>
		<script src="http://www.headjump.de/javascripts/arrowsandboxes.js" type="text/javascript"></script>
    </head>

    <body>
		<div id="dvTreeContainer"></div>
		<div id="printHere"></div>
		<div id="printGraph"></div>
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

			var tree = ${treeJson};
			var nodesList = ${nodesList};
			var a = tree.root;
			var rootNode = new Object();
			rootNode.Content = tree.root.attribute;
			rootNode.Nodes = new Array();

			// Get the reference to a container layer
			var container = document.getElementById("dvTreeContainer");

			// Build tree with options
			//DrawTree({
			//	Container: container,
			//	RootNode: rootNode
			//});
			//document.getElementById("printHere").innerHTML = printNodes(tree.root, 500, 0);


			function buildTree2(level) {
				var nodes = nodesForLevel(level);
				var html = "";
				for (var i = 0; i < nodes.length; i++) {
					html += "(" + nodes[i].parent.label + nodes[i].label + ")";
				}
			}


			function nodesForLevel(level) {
				var nodesList = ${nodesList};
				var arr = new Array();
				for (var i = 0; i < nodesList.length; i++) {
					if (nodesList[i].level == level) {
						arr.push(nodesList[i]);
					}
				}
				return arr;
			}
			;

			var rootCode = "<pre class='arrows-and-boxes'>";
			rootCode += buildTree2(tree.root);
			rootCode += "</pre>";
			document.getElementById("printGraph").innerHTML = nodesForLevel(1);
		</script>
		<pre class='arrows-and-boxes'>(us er >c c [a]) || (a:us er2 >d d [b] >hyhy [c]) || (b:us er3 >hyhy [c]) (c:gggg)</pre>
		<pre class='arrows-and-boxes'>${treeGraphHtml}</pre>
	</body>
</html>
