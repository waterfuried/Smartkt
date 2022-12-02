angular
.module('app', ['ngStorage'])
.controller('sessionController', function ($scope, $rootScope, $http, $localStorage) {

    if ($localStorage.springWebUser) {
        $http.defaults.headers.common.Authorization = 'Bearer ' + $localStorage.springWebUser.token;
    }

    const contextPath = 'http://localhost:8189/app/api/v1',
                 auth = contextPath+'/auth',
                 users = contextPath+'/users';

    $scope.tryToAuth = function() {
        $http.post(auth, $scope.user)
            .then(function successCallback(response) {
                if (response.data.token) {
                    $http.defaults.headers.common.Authorization = 'Bearer ' + response.data.token;
                    $localStorage.springWebUser = {
                        username: $scope.user.username,
                        token: response.data.token
                    };
                    $scope.user.username = null;
                    $scope.user.password = null;
                }
            }, function errorCallback(response) {});
    };

    $scope.tryToLogout = function() {
        $scope.clearUser();
        if ($scope.user.username) { $scope.user.username = null; }
        if ($scope.user.password) { $scope.user.password = null; }
    };

    $scope.clearUser = function() {
        delete $localStorage.springWebUser;
        $http.defaults.headers.common.Authorization = '';
    };

    $rootScope.isUserLoggedIn = function() {
        console.log('user='+$localStorage.springWebUser)
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
});