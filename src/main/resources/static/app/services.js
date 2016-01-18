(function(angular) {
	
  var WSDLFactory = function($resource) {
	  
	var urlBase = '/dmvalidator';
	
	this.getWSDL = function () {
        return $resource(urlBase);
    };
    
    this.getOperations = function (name) {
        return $resource(urlBase + '/operations?name='+name);
    };
    
    /*return $resource('http://someurl.com/api/:path', {
        path: '@path'
    }, {
        getBook: {
            method: 'GET',
            params: {
                path: 'book'
            }
        }
    }, {
        getArticles: {
            method: 'GET',
            params: {
                path: 'articles'
            },
            isArray: true
        }
    });*/
    
  };
  
  
  
  WSDLFactory.$inject = ['$resource'];
  
  
  angular.module("myApp.services").service("WSDL", WSDLFactory);
 
}(angular));