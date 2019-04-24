angular.module('Testing', ['ui.router'])

    .run(function ($rootScope, $state) {

        $rootScope.$on('$stateChangeStart', function () {

            $state.go('register');


        });
    });