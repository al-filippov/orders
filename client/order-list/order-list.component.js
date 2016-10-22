'use strict';

// Register `orderList` component, along with its associated controller and template
angular.
    module('orderList').
    component('orderList', {
        templateUrl: 'order-list/order-list.template.html',
        controller: ['Order', '$filter', function OrderListController(Order, $filter) {
            var self = this;

            self.orders = Order.query();
            self.sortProp = 'creationDate';

            self.dateFilter = function (filterVar) {
                return function (item) {
                    if(angular.isUndefined(filterVar)) filterVar = '';

                    if (angular.isUndefined(item['id'])) return false;
                    if (angular.isUndefined(item['creationDate'])) return false;

                    var itemDate = $filter('date')(item['creationDate'], 'dd.MM.yyyy HH.mm');
                    var itemId = '№' + item['id'].toString();

                    return (itemDate.indexOf(filterVar) > -1) || (itemId.indexOf(filterVar) > -1);
                }
            }

            self.addOrder = function() {
                if(angular.isUndefined(self.orders)) return;

                var order = new Order();
                order.$save();
                self.orders.push(order);
            }

            self.deleteOrder = function(order) {
                if(angular.isUndefined(order)) return;

                if (!confirm('Удалить заказ?')) {
                    return;
                }

                try {
                    Order.delete({orderId: order.id});
                } catch (err) {
                    console.log(err);
                }

                var index = self.orders.indexOf(order);
                self.orders.splice(index, 1);
            }
        }]
  });