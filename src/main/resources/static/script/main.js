// https://docs.angularjs.org/api - AngularJS API documentation
// при наличии списка подключаемых модулей (в квадратных скобках) в контроллере
// Angular "считает" такой контроллер контроллером "приложения" в целом,
// при указании только имени приложения в модуле контроллера такой контроллер
// является контроллером отдельного модуля
// если обращений к контроллеру приложения более одного,
// его можно опеределить в переменной, к которой затем обращаться:
// var myApp = angular.module('app', ['ngStorage', 'ngRoute']);
//(function() {
    //angular.module('market-front',['ngRoute', 'ngStorage']).config(config).run(run);
/*
 <div ng-app="myApp" ng-controller="myCtrl">
    {{ firstName + " " + lastName }}
</div>

<script>
var app = angular.module("myApp", []);
app.controller("myCtrl", function($scope) {
    $scope.firstName = "John";
    $scope.lastName = "Doe";
});
</script>
*/
angular
.module('app', ['ngStorage', 'ngRoute'])
.controller('mainController', function ($rootScope, $scope, $http, $localStorage, $location) {
const contextPath = 'http://localhost:8189/app/api/v1',
      products = contextPath+'/products',
      cart = contextPath+'/cart',

      auth = contextPath+'/auth',
      users = contextPath+'/users',
      wrongUser = "Wrong username or password";

// определить соответствие переходов на страницы по запрошенным адресам
// - вводимым самим пользователем в адресной строке
// - используемым при его щелчках по кнопкам/ссылкам
//(function () {
//myApp
/*        .config(function ($routeProvider) {
            $routeProvider
                .when('/', {
                    templateUrl: 'main.html',
                    controller: 'mainController'
                })
                .when('/order', {
                    templateUrl: 'order.html',
                    controller: 'orderController'
                })
                /*.when('/cart', {
                    templateUrl: 'cart.html',
                    controller: 'cartController'
                })
                .when('/users', {
                    templateUrl: 'users.html',
                    controller: 'usersController'
                })
                .otherwise({
                    redirectTo: '/'
                });
        })
        .run(function ($rootScope, $http, $localStorage) {
        });*/
//})(myApp);
//}})();
// rootScope используется в контроллере приложения в целом, scope - в контроллере отдельного модуля
// http - для использования протолкола http,
// localStorage - для использования локального хранилища обозревателя,
// location - для переходов по каким-либо адресам
//myApp.controller('mainController', function ($scope, $rootScope, $http, $localStorage, $location) {
//angular.module('app', []).controller('mainController', function ($scope, $http) {

    /*
        функции работы с пользователями
    */
    // установить заголовок, используемый при/для авторизации
    $scope.setAuthHeader = function(token) {
        $http.defaults.headers.common.Authorization = 'Bearer ' + token;
    }

    // в локальном хранилище остается копия авторизационных данных пользователя (имя+токен),
    // которые уничтожаются после авторизации;
    // имя переменной для хранения может быть любым;
    // если в хранилище есть пользовательские данные, извлечь из них токен и вставить в заголовок
    if ($localStorage.springWebUser) $scope.setAuthHeader($localStorage.springWebUser.token);

    $scope.tryToAuth = function() {
        $http.post(auth, $scope.user)
            .then(function successCallback(response) {
                if (response.data.token) {
                    $scope.setAuthHeader(response.data.token);
                    $localStorage.springWebUser = {
                        username: $scope.user.username,
                        token: response.data.token
                    };
                    $scope.user.username = null;
                    $scope.user.password = null;
                }
            }, function errorCallback(response) { alert(wrongUser); });
    };

    $scope.tryToLogout = function() {
        $scope.clearUser();
        //if ($scope.user.username) { $scope.user.username = null; }
        //if ($scope.user.password) { $scope.user.password = null; }
        $scope.user = null;
    };

    $scope.clearUser = function() {
        delete $localStorage.springWebUser;
        $http.defaults.headers.common.Authorization = '';
    };

    $rootScope.isUserLoggedIn = function() {
        return $localStorage.springWebUser != null;
    };

    $scope.getUsers = function() {
        $http.get(users).then(function (response) {
            $scope.usersList = response.data.content;
        });
    };

    $scope.delUser = function (id) {
        $http.delete(users+'/'+id)
            .then(function (response) { $scope.getUsers(); });
    };

    $scope.addUser = function () {
        $http.post(users, $scope.newUser)
            .then(function (response) { $scope.getUsers(); });
    };

    /*
        функции для списка товаров
    */
    var maxPage;

    $scope.getProducts = function (page = 1) {
        //$http.get(contextPath+'/all')
        console.log("page="+page);
        $http({
            url: products,
            method: 'GET',
            // вместо параметров можно передать ссылку на объект из html -
            // data: newProduct
            // имена параметров должны совпадать с ожидаемыми в контроллере на стороне бэк-энда
            params: {
                p: page,
                psz: $scope.filter ? $scope.filter.page_size : null,
                title: $scope.filter ? $scope.filter.title : null,
                min_cost: $scope.filter ? $scope.filter.min_cost : null,
                max_cost: $scope.filter ? $scope.filter.max_cost : null
            }
        }).then(function (response) {
            console.log('products='+response.data);
            $scope.productsPage/*productsList*/ = response.data;//.content
            // сформировать ряд чисел (номеров страниц) между двумя указанными
            let numbers = [];
            maxPage = $scope.productsPage.totalPages;
            if (maxPage > 1)
                for (let i = 1; i < maxPage+1; i++) { numbers.push(i); }
            $scope.pageNumbers = numbers;
        });
    };

    $scope.getPageCount = function() { return maxPage; }

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


    /*
        функции работы с корзиной
    */
    $scope.addToCart = function (id) {
	    $http.get(cart+'/add/'+id)
	    /*var data = { 'id': id, };*/
	    //$http.post(cart, data)
	    //возможен также вариант $http.post(cart, data, func)
	    //в котором func - функция, выполняющая действия по добавлению в корзину:
	    //func = function() { /* действия, но какие ? */ }
            .then(function (response) { $scope.getCartContent(); });
    };

    $scope.removeFromCart = function (id) {
        $http.get(cart+'/delete/'+id)
            .then(function (response) { $scope.getCartContent(); });
    }

    $scope.clearCart = function () {
        $http.get(cart+'/clear')
            .then(function (response) { $scope.getCartContent(); });
    }

    $scope.changeAmount = function (id, inc) {
        $http({
            url: cart+'/change_amount/',
            method: 'GET',
            params: {
                id: id,
                delta: inc
            }
        }).then(function (response) { $scope.getCartContent(); });
    }

    var emptyCart;

    $scope.getCartContent = function () {
        $http.get(cart).then(function (response) {
            $scope.cartContent = response.data;
            emptyCart = response.data == null || response.data.length == 0;
        });
    };

    // проверить корзину на пустоту
    $scope.isCartEmpty = function () { return emptyCart; }


    /*
        функции переходов на страницы
    */
    $scope.toOrder = function () { $location.path('/order'); };


    // проверка пустоты полей логина и пароля,
    // используя библиотеку JS Knockout
    var formFields = { login: ko.observable(""), pw: ko.observable("") }
    formFields.filled = ko.computed(function() { return this.login() && this.pw(); }, formFields);
    ko.applyBindings(formFields);

    $scope.getProducts();
    $scope.getCartContent();
});