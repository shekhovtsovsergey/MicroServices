angular.module('market').controller('ordersController', function ($scope, $http) {
    const contextPath = 'http://myapp-service-5555.default.svc.cluster.local:5555/core/';

    $scope.loadOrders = function () {
        $http.get(contextPath + 'api/v1/orders').then(function (response) {
            $scope.orders = response.data;
        });
    }

    $scope.loadOrders();
});