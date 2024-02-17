angular.module('market').controller('uploadController', function ($scope, $http, $location, $localStorage) {
    const contextPath = 'http://arch.homework/core/';

    // Установка токена в заголовок при инициализации контроллера
    $http.defaults.headers.common.Authorization = 'Bearer ' + $localStorage.winterMarketUser.token;

    console.log('Токен:', $localStorage.winterMarketUser.token); // Логирование токена в консоль

    $scope.saveNewProduct = function () {
        const title = document.getElementById("productTitleInput").value;
        const price = document.getElementById("productPriceInput").value;
        const image = document.getElementById("productImageInput").files[0];
        const headers = new Headers({
            'Authorization': 'Bearer ' + $localStorage.winterMarketUser.token
        });

        const formData = new FormData();
        formData.append("title", title)
        formData.append("price", price);
        formData.append('category', 'Wine');
        formData.append("image", image);

        // Токен уже установлен в $http.defaults.headers.common.Authorization, так что не нужно устанавливать его снова

        fetch(contextPath + 'api/v1/products', {
            method: "POST",
            headers: headers, // Добавляем заголовок с токеном
            body: formData
        }).then(function (response) {
            alert("Отправлено");
        }).catch(error => console.error(error));
    };
});