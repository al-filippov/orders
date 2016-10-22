'use strict';

angular.
    module('core.order').
    factory('Order', ['$resource',
        function($resource) {
            return $resource('http://localhost:8080/orders/:orderId', {orderId: '@id'});
        }
    ]);