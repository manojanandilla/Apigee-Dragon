(function(angular) {
  var AppController = function($scope,WSDL) {
	  
	  $scope.hideSelect = false;
	  $scope.wsdl = {};
	  
	  WSDL.getWSDL().query().$promise.then(function(result){
		  $scope.wsdls = result;
	  });
	  
	  
	  $scope.getOperations = function(name){
		  $scope.operations = WSDL.getOperations(name).query();
		  $scope.hideSelect = true;
		  $scope.types = ['Request','Response'];
	  };
	  
	  
	  
	  $scope.validate = function(){
		  
		  WSDL.getWSDL().save($scope.wsdl,function(){
			  alert('Data Saved Successfully');
		  });
		
	  };
	  
  };
  
  AppController.$inject = ['$scope','WSDL'];
  angular.module("myApp.controllers").controller("AppController", AppController);
}(angular));