angular.module('market', []).controller('uploadController', function ($scope, $http, $location, $localStorage) {
    const contextPath = 'http://localhost:5555/core/';

    // Добавляем поле для ввода нового продукта
    $scope.newProduct = {};

    // Функция для отправки POST запроса на сохранение нового продукта
    $scope.saveNewProduct = function() {
        const productData = {
            title: $scope.newProduct.title,
            price: $scope.newProduct.price,
            category: 1
        };

        $http({
            url: contextPath + 'api/v1/products',
            method: 'POST',
            data: productData
        }).then(function (response) {
            // Загружаем обновленную таблицу товаров после сохранения
            $scope.loadProducts();
        });
    }
});