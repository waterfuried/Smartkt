angular
.module('app', [])
.controller('orderController', function ($scope, $http, $location) {
    const contextPath = 'http://localhost:8189/app/api/v1',
          msgOrderRegistered = 'Заказ принят в обработку';

    var selectedIndex = -1;

    $scope.dropdownSelect = function () {
        selectedIndex = document.getElementById('deliveryType').selectedIndex;
        alert(selectedIndex);
    }

    $scope.isPoint = function () { console.log(selectedIndex); return selectedIndex == 1; }
    $scope.isLocker = function () { console.log(selectedIndex); return selectedIndex == 2; }
    $scope.isUser = function () { console.log(selectedIndex); return selectedIndex == 3; }

    $scope.registerOrder = function() {
        console.log('!!!!!');
        // TODO: сохранить заказ в БД -> orderController
        //$http.post(contextPath + ?);
        alert(msgOrderRegistered);
    }

    $scope.toMain = function () { $location.path('/'); };
});