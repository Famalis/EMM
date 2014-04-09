function IndexCtrl($scope, $http) {
	$scope.aVar = "some var";
    
    $scope.query = function() {
        if(isNaN($scope.sugar)){
            $scope.errorMsg = "Pole cukier musi zawierać liczbę.";
            $scope.resultString = "";
            return;
        } else {
            $scope.errorMsg = "";
        }
        var s = $scope.sugar?$scope.sugar : 0;
        $http.post('/TeaClassifier/query.htm',
        {query:$scope.teaType
            +','+$scope.addition
            +','+s}).success(function(returnData){
            $scope.queryResult = returnData;
            if(returnData=="0") {
                $scope.resultString = "Zły wybór";
            }
            if(returnData=="1") {
                $scope.resultString = "Dobry wybór";
            }
            if(returnData=="2") {
                $scope.resultString = "Nie umiem sklasyfikować wyboru";
            }
            }).error(function(){
                aler("Błąd przy wysyłaniu danych");
            });
    };
}