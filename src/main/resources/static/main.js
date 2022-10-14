// https://docs.angularjs.org/api - AngularJS API documentation
angular.module('app', []).controller('mainController', function ($scope, $http) {
    const contextPath = 'http://localhost:8189/app';

    $scope.getAllProducts = function () {
        $http.get(contextPath+'/products/all')
            .then(function (response) {
                $scope.productsList = response.data;
            });
    };

    $scope.delProduct = function (id) {
        // вместо get можно использовать метод delete, принимающий адрес, но без параметров (id),
        // передать id как-то можно вторым аргументом delete, но так удаление не срабатывает
        $http.get(contextPath+'/products/delete/'+id)
            .then(function (response){
                $scope.getAllProducts();
            });
    };

    $scope.addProduct = function () {
    // TODO: реализовать добавление нового товара можно
    //  1) добавлением некой стандартной болванки в список и последующим редактированием в нем полей продукта
    //  2) созданием формы ввода нового товара с теми же полями и передачей введенных данных в список
    // как реализовывать это в Angular - вопрос, видимо, за рамками курса
        //.then(function (response) { $scope.getAllProducts(); });
    };

    $scope.getAllProducts();
});