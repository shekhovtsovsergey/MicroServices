angular.module('market', []).controller('uploadController', function ($scope, $http, $location, $localStorage) {
    const contextPath = 'http://localhost:5555/core/';

    // Добавляем поле для ввода нового продукта
    $scope.newProduct = {};

    // Функция для отправки POST запроса на сохранение нового продукта
    $scope.saveNewProduct = function() {
        const productDto = {
            id: null,
            title: $scope.newProduct.title,
            description: $scope.newProduct.description,
            price: $scope.newProduct.price,
            category: {id: 1},
            imageUrl: $scope.newProduct.imageUrl
        };

        $http({
            url: contextPath + 'api/v1/products',
            method: 'POST',
            data: productDto
        }).then(function (response) {
            // Загружаем обновленную таблицу товаров после сохранения
            $scope.loadProducts();
        });
    }
});