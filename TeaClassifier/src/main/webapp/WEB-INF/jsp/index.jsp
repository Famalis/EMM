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
		<div style="width: 300px; height: 300px; background-color: grey;" id="printHere"></div>
		<script type="text/javascript">
				function printNodes(parent,x,y) {
					var line = "<svg style='position: absolute; left:"+x+"px; top:"+y+"px' width='50' height='50'>";
					line+="<circle cx='25' cy='25' r='20' fill='black'/>";
					if(parent.classValue != null) {
						line+="<text>"+parent.classValue+"</text></svg>";
					} else {
						line+="<text x='0' y='15' fill='red'>I love SVG!</text></svg>";
					}
					var newX = x;
					var newY = y;					
					for (var i = 0; i < parent.children.length; i++) {
						
						newX+=50;
						newY+=50;
						var node = parent.children[i];						
						line +="</br>"+printNodes(node,newX,newY);
					}
					return line;
				}
				var tree = ${treeJson};
				var a = tree.root;
				var rootNode = ${rootJson};

				// Get the reference to a container layer
				var container = document.getElementById("dvTreeContainer");

				// Build tree with options
				//DrawTree({
				//	Container: container,
				//	RootNode: rootNode
				//});
				document.getElementById("printHere").innerHTML = printNodes(tree.root,0,0);


		</script>
				${treeHtml}
	</body>
</html>
