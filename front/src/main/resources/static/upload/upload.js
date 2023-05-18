angular.module('market').controller('uploadController', function ($scope, $http, $location, $localStorage) {
    const contextPath = 'http://localhost:5555/core/';
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



/*

$scope.saveNewProduct = function () {
    // Получаем значения полей из формы
    const title = document.getElementById("productTitleInput").value;
    const price = document.getElementById("productPriceInput").value;
    const image = document.getElementById("productImageInput").files[0];

    // Создаем объект FormData и добавляем значения полей
    const formData = new FormData();
    formData.append("title", title)
    formData.append("price", price);
    formData.append('category', JSON.stringify({id: 1}));
    formData.append("companyImage", image);

    // Отправляем POST запрос на сервер, передавая объект FormData
    fetch(contextPath + 'api/v1/products', {
        method: "POST",
        body: formData
    }).then(function (response) {
        alert("Отправлено");
    }).catch(error => console.error(error));
};
*/

/*  // Добавляем поле для ввода нового продукта
  $scope.newProduct = {};
  // Функция для отправки POST запроса на сохранение нового продукта
  $scope.saveNewProduct = function($event) {
      const formData = new FormData();
      formData.append('title', $scope.newProduct.title);
      formData.append('price', $scope.newProduct.price);
      formData.append('category', JSON.stringify({id: 1}));
      formData.append('image', $scope.newProduct.image);
      $http.post(contextPath + 'api/v1/products', formData, {
          headers: {
              'Content-Type': undefined
          }
      }).then(function (response) {
          // Загружаем обновленную таблицу товаров после сохранения
          $scope.loadProducts();
          console.log('Продукт успешно сохранен', response.data);
      }, function (error) {
          console.log('Ошибка при сохранении продукта', error);
      });
  }*/