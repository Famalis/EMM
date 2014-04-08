function IndexCtrl($scope, $http) {
	$scope.aVar = "this is a var";
	
	var loadDataPromise = $http.get("getTree.htm").success(function(returnData) {
		return returnData;
	}).error(function(error) {
		$scope.status = "Błąd";
		return null;
	});
	;

	loadDataPromise.then(function(initData) {
		if (initData != null) {
			$scope.initData = initData;
			alert(initData.attribute);
		} else {
			alert('err');
		}
	});
}