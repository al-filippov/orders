'use strict';

angular.
    module('orderApp').
    config(['$locationProvider' ,'$routeProvider',
        function config($locationProvider, $routeProvider) {
            $locationProvider.hashPrefix('!');

            $routeProvider.
                when('/orders', {
                    template: '<order-list></order-list>'
                }).
                when('/orders/:orderId', {
                    template: '<order-detail></order-detail>'
                }).
                when('/orders/:orderId/edit', {
                    template: '<order-detail-edit></order-detail-edit>'
                }).
                otherwise('/orders');
        }
  ]);