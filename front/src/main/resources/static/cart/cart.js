angular.module('market').controller('cartController', function ($scope, $http, $location, $localStorage) {
    // const contextPath = 'http://localhost:5555/cart/';
    const contextPath = 'http://myapp-service-5555.default.svc.cluster.local:5555/cart/';
    // const coreContextPath = 'http://localhost:5555/core/';
    const coreContextPath = 'http://myapp-service-5555.default.svc.cluster.local:5555/core/';

    $scope.loadCart = function () {
        $http.get(contextPath + 'api/v1/cart/' + $localStorage.winterMarketGuestCartId).then(function (response) {
            $scope.cart = response.data;
        });
    }

    $scope.clearCart = function () {
        $http.delete(contextPath + 'api/v1/cart/' + $localStorage.winterMarketGuestCartId + '/clear').then(function (response) {
            $scope.loadCart();
        });
    }

    $scope.removeFromCart = function (productId) {
        $http.delete(contextPath + 'api/v1/cart/' + $localStorage.winterMarketGuestCartId + '/remove/' + productId).then(function (response) {
            $scope.loadCart();
        });
    }

    $scope.createOrder = function () {
        $http.post(coreContextPath + 'api/v1/orders').then(function (response) {
            alert('Заказ оформлен');
            $scope.loadCart();
        });
    }

    $scope.loadCart();
});