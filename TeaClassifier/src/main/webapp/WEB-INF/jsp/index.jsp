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
        <link rel="stylesheet" type="text/css" href="/TeaClassifier/resources/css/jquery.fullPage.css" />
        <link rel="stylesheet" type="text/css" href="/TeaClassifier/resources/css/style.css" />
        <!--[if IE]>
        <script type="text/javascript">
                 var console = { log: function() {} };
        </script>
<![endif]-->

        <script src="http://ajax.googleapis.com/ajax/libs/jquery/1.8.3/jquery.min.js"></script>
        <script src="http://ajax.googleapis.com/ajax/libs/jqueryui/1.9.1/jquery-ui.min.js"></script>	

        <script type="text/javascript" src="/TeaClassifier/resources/js/jquery.fullPage.js"></script>
        <script type="text/javascript">
            $(document).ready(function() {
                $('#fullpage').fullpage({
                    slidesColor: ['#f2f2f2', '#4BBFC3', '#7BAABE', 'whitesmoke', '#ccddff']
                });
            });
        </script>
        <script type="text/javascript">
            $(document).ready(function() {
                $('#przycisk').click(function() {
                    $('#ustawienia').toggle("fast");
                });
            });
        </script>

        <style>
            .normalNode
            {
                fill:black;
                stroke:white;
                stroke-width:2;
                z-index: 1000;

            }
            .selectedNode {
                fill:yellow;
                stroke:black;
                z-index: 1001;
            }
            .normalLine
            {

                stroke:black;
                stroke-width:2;
                z-index:1;

            }
            .selectedLine
            {
                stroke:rgb(0,255,0);
                stroke-width:2;
                z-index: 2;

            }

        </style>
    </head>
    <body ng-controller="IndexCtrl" bgcolor="grey">

        <div id="fullpage">
            <div class="section active" id="section0"><h1>Herbatki?<br>scrolnij w dół</h1></div>
            <div class="section" id="section1">
                <div class="ikonka" id="przycisk"></div>
                <div class="top" id="ustawienia">
                    <h1>DANE TESTOWE</h1>
                    <div class="dane-testowe">

                        <ul>
                            <c:forEach var="item" items="${trainingSet}">
                                <li>${item}</li>
                                </c:forEach>
                        </ul>
                        <h1>DANE UZUPEŁNIAJĄCE</h1>
                        <div class="pytania">
                        <form name="queryForm"/>
                        Rodzaj herbaty:
                        <select onchange='showPath()' id="teaSelect" ng-change="query()" ng-model="teaType" ng-init="teaType = 'black tea'">
                            <option value="black tea">Czarna</option>
                            <option value="white tea">Biała</option>
                            <option value="green tea">Zielona</option>
                        </select>
                        <br/>
                        Dodatek:
                        <select onchange='showPath()' id="additionSelect" ng-change="query()" ng-model="addition" ng-init="addition = 'none'">
                            <option value="none">Brak</option>
                            <option value="lemon">Cytryna</option>
                            <option value="milk">Mleko</option>
                        </select>
                        <br />
                        Ile cukru:
                        <input onchange='showPath()' id="sugarInput" ng-change="query()" type="number" ng-model="sugar" placeholder="Ile cukru..." value="0"/>                        
                        {{resultString}}{{errorMsg}}
                        </form>
                        </div>      
                    </div>              
                </div>
                <div class="drzewo-container">

                    <div class="drzewo" id="printGraph"></div>
                </div>
            </div>
        </div>
        <script type="text/javascript">
                    var tree = ${treeJson};
                    var nodesList = ${nodesList};
                    var sugarThreshold = ${sugarThreshold};
                            function drawLine(x1, y1, x2, y2, label, node) {
                                var midX = getMidPoint(x1, y1, x2, y2)[0] + 5;
                                var midY = getMidPoint(x1, y1, x2, y2)[1];
                                var html = ""
                                html += "<line x1='" + x1 + "' y1='" + y1 + "' x2='" + x2 + "' y2='" + y2 + "' id='" + node.idString + "line' class='normalLine' />";
                                html += "<text x='" + (midX - 15) + "' y='" +
                                        (midY - 1) + "' fill='white' style='font-weight:bold'" +
                                        " transform='rotate(" + getAngleBetweenPoints(x1, y1, x2, y2) + " " + midX + "," + midY + ")'>" + label + "</text>";
                                html += "";
                                return html;
                            }
                    ;

                    function drawNode(x1, y1, node) {
                        var html = "";
                        html += "<ellipse cx=" + x1 + " cy=" + y1 + " rx='10' ry='10' id='" + node.idString + "' class='normalNode'/>"
                        if (node.classValue != null) {
                            html += "<text x='" + (x1 - 20) + "' y='" + (y1 + 30) + "' fill='black' style='font-size: 12px'>" + node.classValue + "</text>";
                        } else {
                            html += "<text x='" + (x1 + 20) + "' y='" + (y1 + 4) + "' fill='black' style='font-weight:bold'>" + node.attribute + "</text>";
                        }
                        return html;
                    }
                    ;

                    function drawGraph(width, height, tree) {
                        var html = "<svg width='" + width + "' height='" + height + "'>"
                        var x = width / 2;
                        var y = 15;
                        html += drawNode(x, y, tree.root);
                        html += drawChildren(tree.root, (x) - width * 0.2, (x) + width * 0.2, y + 150, x, y, 0.3);
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
                            html += drawLine(px, py, x, y, child.label, child);
                            labelHeightMod += 20;
                            var newMinWidth = x - ((maxWidth - minWidth) * newMod);
                            var newMaxWidth = x + ((maxWidth - minWidth) * newMod);
                            html += drawChildren(child, newMinWidth, newMaxWidth, height + 150, x, y, newMod * 0.8);
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

                    function showPath(selection) {
                        var teaType = document.getElementById('teaSelect').value;
                        var addition = document.getElementById('additionSelect').value;
                        var sugar = document.getElementById('sugarInput').value;
                        paintPath(tree.root, teaType, addition, sugar);
                    }

                    function paintPath(node, teaType, addition, sugar) {
                        if (node.label == 'root') {
                            document.getElementById(node.idString)
                                    .setAttribute("class", "selectedNode");
                        }
                        for (var i = 0; i < node.children.length; i++) {
                            document.getElementById(node.children[i].idString)
                                    .setAttribute("class", "normalNode");
                            document.getElementById(node.children[i].idString + "line")
                                    .setAttribute("class", "normalLine");
                            if (node.attributeNum == 0) {
                                if (node.children[i].label == teaType) {
                                    document.getElementById(node.children[i].idString)
                                            .setAttribute("class", "selectedNode");
                                    document.getElementById(node.children[i].idString + "line")
                                            .setAttribute("class", "selectedLine");
                                    paintPath(node.children[i], teaType, addition, sugar);
                                } else {

                                }
                            } else if (node.attributeNum == 2) {
                                if (node.children[i].label == addition) {
                                    document.getElementById(node.children[i].idString)
                                            .setAttribute("class", "selectedNode");
                                    document.getElementById(node.children[i].idString + "line")
                                            .setAttribute("class", "selectedLine");
                                    paintPath(node.children[i], teaType, addition, sugar);
                                } else {

                                }
                            } else {
                                if (node.children[i].label.substring(0, 1) == ">"
                                        && sugar > sugarThreshold) {
                                    document.getElementById(node.children[i].idString)
                                            .setAttribute("class", "selectedNode");
                                    document.getElementById(node.children[i].idString + "line")
                                            .setAttribute("class", "selectedLine");
                                    paintPath(node.children[i], teaType, addition, sugar);

                                } else if (node.children[i].label.substring(0, 2) == "<="
                                        && sugar <= sugarThreshold) {
                                    document.getElementById(node.children[i].idString)
                                            .setAttribute("class", "selectedNode");
                                    document.getElementById(node.children[i].idString + "line")
                                            .setAttribute("class", "selectedLine");
                                    paintPath(node.children[i], teaType, addition, sugar);
                                } else {

                                }
                            }
                        }
                    }
                    document.getElementById("printGraph").innerHTML = drawGraph(1000, 550, tree);
        </script>-->
    </body>
</html>
