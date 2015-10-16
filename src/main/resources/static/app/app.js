
var myApp = angular.module('myApp', ["ngRoute","ngResource", "myApp.services", "myApp.controllers"])
	.config(
		[ '$routeProvider', '$locationProvider', '$httpProvider', function($routeProvider, $locationProvider, $httpProvider) {
			
			$routeProvider.when('/', {
				templateUrl: 'index.html',
				controller: MacDetailController
			});
            
        }]);

        