angular.module('Testing').config(function ($stateProvider, $urlRouterProvider) {

    $urlRouterProvider.otherwise('/register');

    $stateProvider

        .state('register', {
            url: '/register',
            templateUrl: 'angular/view/register.html',
            controller: 'RegisterController'
        })
});
