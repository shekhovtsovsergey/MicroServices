angular.module('market').controller('uploadController', function ($scope, $http, $location, $localStorage) {
    const contextPath = 'http://myapp-service-5555.default.svc.cluster.local:5555/core/';
    $scope.saveNewProduct = function () {
        // Получаем значения полей из формы
        const title = document.getElementById("productTitleInput").value;
        const price = document.getElementById("productPriceInput").value;
        const image = document.getElementById("productImageInput").files[0];

        // Создаем объект FormData и добавляем значения полей
        const formData = new FormData();
        formData.append("title", title)
        formData.append("price", price);
        formData.append('category','Wine');
        formData.append("image", image);

        // Отправляем POST запрос на сервер, передавая объект FormData
        fetch(contextPath + 'api/v1/products', {
            method: "POST",
            body: formData
        }).then(function (response) {
            alert("Отправлено");
        }).catch(error => console.error(error));
    };

});