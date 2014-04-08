function IndexCtrl($scope, $http) {
	$scope.aVar = "this is a var";
	$scope.tree = "";
	var loadDataPromise = $http.get("getTree.htm").success(function(returnData) {
		return returnData;
	}).error(function(error) {
		$scope.status = "Błąd";
		return null;
	});
	;

	loadDataPromise.then(function(initData) {
		if (initData != null) {
			$scope.tree = initData;
		} else {
			alert('err');
		}
	});
	
	$scope.printTree = function() {
		
	};
	$scope.printNodes = function(parent) {
		
	};
}