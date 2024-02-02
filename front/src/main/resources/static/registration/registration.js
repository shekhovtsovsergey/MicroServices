angular.module('market').controller('registrationController', function ($scope, $http, $location, $localStorage) {
    const contextPath = 'http://myapp-service-5555.default.svc.cluster.local:5555/security/';

    $scope.functionRegistration = function () {
        $http.post(contextPath + 'registration', $scope.reguser).then(function (response) {
            if (response.data.token) {
                $http.defaults.headers.common.Authorization = 'Bearer ' + response.data.token;
                $localStorage.winterMarketUser = {username: $scope.reguser.username, token: response.data.token};
                $localStorage.reguser = null;
                $location.path("/");
            }
        });
    }
});