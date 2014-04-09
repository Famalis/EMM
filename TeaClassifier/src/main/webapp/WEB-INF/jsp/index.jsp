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
			function getChildren(parent, parentNode) {
				for (var i = 0; i<parent.children.length; i++) {
					var child = parent.children[i];
					var childNode = new Object();
					if(child.classValue != null) {
						childNode.Content = child.classValue;
					} else {
						childNode.Content = child.attribute;
					}					 
					childNode.Nodes = new Array();
					parentNode.Nodes[i] = childNode; 
				}
			}
			var tree = ${treeJson};
			var nodesList = ${nodesList};
			var a = tree.root;
			var rootNode = new Object();
			rootNode.Content = tree.root.attribute;
			rootNode.Nodes = new Array();
			getChildren(tree.root, rootNode);

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

			function drawLine(x1, y1, x2, y2, label) {
				var html = ""
					html+= "<line x1='"+x1+"' y1='"+y1+"' x2='"+x2+"' y2='"+y2+"' style='stroke:rgb(0,0,0);stroke-width:2' />";
					html+= "<text x='"+x2/2+"' y='"+y2/2+"' fill='red'>"+label+"</text>";
					html+= "";
				return html;
			};
			
			function drawNode(x1, y1, node) {
				var html = "";
				html += "<ellipse cx="+x1+" cy="+y1+" rx='10' ry='10' style='fill:white;stroke:black;stroke-width:2' />"
				html += "<text x='"+(x1-25)+"' y='"+(y1+25)+"' fill='black'>";
				if(node.classValue != null) {
					html += node.classValue+"</text>";
				} else {
					html += node.attribute+"</text>";
				}
				return html;
			};
			
			function drawGraph(width, height, tree) {
				var html = "<svg style='background-color: lightgrey' width='"+width+"' height='"+height+"'>"
				var x = width/2;
				var y = 10;
				html+=drawNode(x,y,tree.root);
				html+=drawChildren(tree.root, 400,width-400, y+50, x, y);
				html+="</svg>";
				return html;
			};
			
			function drawChildren(parent, minWidth, maxWidth, height, px, py) {
				var html = "";
				var x = minWidth;
				var y = height;
				for (var i = 0; i<parent.children.length; i++){
					var child = parent.children[i];
					html += drawNode(x,y,child);
					html += drawLine(px,py,x,y,child.label);
					var newMinWidth = x-(x/2);
					var newMaxWidth = x+(x/2);
					//html += drawChildren(child,newMinWidth,newMaxWidth,height+50);
					x += (maxWidth-minWidth)/(parent.children.length-1);
				}
				return html;
			}
			var rootCode = "<pre class='arrows-and-boxes'>";
			rootCode += buildTree2(tree.root);
			rootCode += "</pre>";
			document.getElementById("printGraph").innerHTML = drawGraph(1200,1000,tree);
		</script>
	</body>
</html>
