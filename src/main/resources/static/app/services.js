var services = angular.module('myApp.services', ['ngResource']);

services.factory('MacDetailService',function($resource) {
    return $resource('/cablemodem',{});
});