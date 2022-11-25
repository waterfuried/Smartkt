// https://docs.angularjs.org/api - AngularJS API documentation
angular.module('app', []).controller('mainController', function ($scope, $http) {
    const contextPath = 'http://localhost:8189/app/api/v1',
          products = contextPath+'/products',
          cart = contextPath+'/cart';

    $scope.getProducts = function (page = 1) {
        //$http.get(contextPath+'/all')
        $http({
            url: products,
            method: 'GET',
            // вместо параметров можно передать ссылку на объект из html -
            // data: newProduct
            params: {
                title: $scope.filter ? $scope.filter.title : null,
                min_cost: $scope.filter ? $scope.filter.min_cost : null,
                max_cost: $scope.filter ? $scope.filter.max_cost : null,
                page_size: $scope.filter ? $scope.filter.page_size : null,
                p: page
            }
        }).then(function (response) {
            $scope.productsPage/*productsList*/ = response.data;//.content
            /*по запросу получается список с заданными параметрами, в том числе -
              с кол-вом отображаемых на странице элементов. Чтобы узнать общее число
              страниц, нужно выполнять другой запрос, получающий полный список*/
            /*document.getElementById("btnPrev").style.visibility="hidden";*/
        });
    };

    $scope.delProduct = function (id) {
        $http.delete(products+'/'+id)
            .then(function (response) { $scope.getProducts(); });
    };

    $scope.addProduct = function () {
        $http.post(products, $scope.newProduct)
            .then(function (response) { $scope.getProducts(); });
    };

    $scope.showPage = function (dir) {
        $http({
            url: products+'/'+dir,
            method: 'GET'
        })
        .then(function (response) { $scope.getProducts(); });
    }

    $scope.addToCart = function (id, title, cost) {
	    console.log('cart: product.id='+id);
	    var data = {
			'id': id,
			'title': title,
			'cost': cost,
			'quantity': 1
	    };
	    $http.post(cart, data)
	    //возможен также вариант $http.post(cart, data, func)
	    //в котором func - функция, выполняющая действия по добавлению в корзину:
	    //func = function() { /* действия, но какие ? */ }
            .then(function (response) { $scope.getCartContent(); });
    };

    $scope.getCartContent = function () {
        $http({
            url: cart,
            method: 'GET'
            //data: cartList
        }).then(function (response) {
            $scope.cartContent = response.data.content;
        });
    };

    $scope.getProducts();
});