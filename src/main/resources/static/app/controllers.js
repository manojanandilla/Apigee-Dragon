(function(angular) {
  var AppController = function($scope,WSDL) {
	  
	  $scope.hideSelect = false;
	  
	  WSDL.getWSDL().query().$promise.then(function(result){
		  $scope.wsdls = result;
	  });
	  
	  
	  $scope.getOperations = function(name){
		  $scope.operations = WSDL.getOperations(name).query();
		  $scope.hideSelect = true;
		  $scope.types = ['Request','Response'];
	  }
	  
  };
  
  AppController.$inject = ['$scope','WSDL'];
  angular.module("myApp.controllers").controller("AppController", AppController);
}(angular));