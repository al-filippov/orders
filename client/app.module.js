'use strict';

// Define the `orderApp` module
angular.module('orderApp', [
    // ...which depends on the `orderList` module
    'ngRoute',
    'orderDetail',
    'orderDetailEdit',
    'orderList',
]);