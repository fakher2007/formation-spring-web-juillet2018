class BookController{
	
	constructor($http){
		this.filter = {};
		this.results = null;
		this.$http = $http;
	}
	
	find(){
		const uri = "http://localhost:8080/api/searches?domain=books";
		this.results = this.$http.post(uri, this.filter).success(x => this.results = x);
	}
}

angular.module('ex4', [ 'ngRoute' ])
	.config(function($routeProvider) {
		$routeProvider.when('/books', {templateUrl : 'partials/books.html'})
		$routeProvider.when('/books/:id', {templateUrl : 'partials/book.html'})
		.otherwise({redirectTo : '/home'});
	}).controller("BookController", BookController);