angular.module('market').controller('ordersController', function ($scope, $http) {
    const contextPath = 'http://arch.homework/core/';

    $scope.loadOrders = function () {
        $http.get(contextPath + 'api/v1/orders').then(function (response) {
            $scope.orders = response.data;
        });
    }

    $scope.loadOrders();
});