'use strict';

// Register `orderDetail` component, along with its associated controller and template
angular.
    module('orderDetail').
    component('orderDetail', {
        templateUrl: 'order-detail/order-detail.template.html',
        controller: ['$routeParams', 'Order',
            function OrderDetailController($routeParams, Order) {
                var self = this;

                self.order = Order.get({orderId: $routeParams.orderId}, function(order) {});
            }
        ]
    });

