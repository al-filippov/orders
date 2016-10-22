'use strict';

// Register `orderDetailEdit` component, along with its associated controller and template
angular.
    module('orderDetailEdit').
    component('orderDetailEdit', {
        templateUrl: 'order-detail-edit/order-detail-edit.template.html',
        controller: ['$routeParams', 'Order',
            function OrderDetailEditController($routeParams, Order) {
                var self = this;

                self.order = Order.get({orderId: $routeParams.orderId}, function(order) {});

                self.addOrderLine = function() {
                    if(angular.isUndefined(self.order)) return;

                    self.order.orderLines.push({
                        item: '',
                        price: '',
                        quantity: ''
                    });
                }

                self.saveOrder = function() {
                    if (!confirm('Сохранить заказ?')) {
                        return;
                    }

                    self.result = 'Ошибка созранения!';
                    self.resultColor = 'red';

                    if(angular.isUndefined(self.order)) {
                        return;
                    }

                    try {
                        Order.save({orderId: self.order.id}, self.order);
                    } catch (err) {
                        console.log(err);
                        return;
                    }

                    self.resultColor = 'green';
                    self.result = 'Заказ успешно сохранен!';
                }

                self.deleteOrderLine = function(orderLine) {
                    if(angular.isUndefined(self.order)) return;

                    if(angular.isUndefined(orderLine)) return;

                    if (!confirm('Удалить строку заказа?')) {
                        return;
                    }

                    var index = self.order.orderLines.indexOf(orderLine);
                    self.order.orderLines.splice(index, 1);
                }
            }
        ]
    });

