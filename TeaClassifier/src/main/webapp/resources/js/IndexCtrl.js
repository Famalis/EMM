function IndexCtrl($scope, $http) {
    $scope.getSelectionCombo = function(){
        return $scope.teaType + "," + $scope.addition + "," + $scope.sugar;
    }
    
    $scope.query = function() {
        if(isNaN($scope.sugar)){
            $scope.errorMsg = "Pole cukier musi zawierać liczbę.";
            $scope.resultString = "";
            return;
        } else if ($scope.sugar<0) {
            $scope.errorMsg = "Ilość cukru nie może być ujemna.";
            $scope.resultString = "";
            return;
        } 
        else {
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