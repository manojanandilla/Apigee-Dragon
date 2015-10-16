



myApp.controller('MacDetailController',function($scope, $location, MacDetailService){
	alert('i am here ');
	$scope.macDetail = new MacDetailService();
	
	$scope.getMacDetails = function(){
    
      $scope.macDetail.$save()
        .then(function(macDetails){
          alert(macDetails);
        });
    
  }
});