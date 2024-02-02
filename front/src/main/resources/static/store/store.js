angular.module('market').controller('storeController', function ($scope, $http, $location, $localStorage) {
    const contextPath = 'http://myapp-service-5555.default.svc.cluster.local:5555/core/';
    const cartContextPath = 'http://myapp-service-5555.default.svc.cluster.local:5555/cart/';
    $scope.loadProducts = function (pageIndex = 1) {
        $http({
            url: contextPath + 'api/v1/products',
            method: 'GET',
            params: {
                p: pageIndex
            }
        }).then(function (response) {
            $scope.productsPage = response.data;
            $scope.generatePagesList($scope.productsPage.totalPages);
        });
    }
    $scope.showProductInfo = function (productId) {
        $http.get(contextPath + 'api/v1/products/' + productId).then(function (response) {
            alert(response.data.title);
        });
    }
    $scope.addToCart = function (productId) {
        $http.post(cartContextPath + 'api/v1/cart/' + $localStorage.winterMarketGuestCartId + '/add/' + productId).then(function (response) {
        });
    }
    $scope.generatePagesList = function (totalPages) {
        out = [];
        for (let i = 0; i < totalPages; i++) {
            out.push(i + 1);
        }
        $scope.pagesList = out;
    }
    $scope.loadProducts();

    $scope.openImage = function(productId){
        $http.get(contextPath+'api/v1/picture/'+productId, { responseType: 'arraybuffer' })
            .then(function(response) {
                var blob = new Blob([response.data], {type: "image/jpeg"});
                var imageUrl = URL.createObjectURL(blob);
                window.open(imageUrl,'_blank','width=auto,height=auto');
            }, function(error) {
                alert("Ошибка загрузки картинки");
            });
    }

});