'use strict';

angular
  .module('moonBuddyApp', [
    'ngResource',
    'ngSanitize',
    'ngRoute'
  ])
  .config(function ($routeProvider) {
    $routeProvider
      .when('/', {
        templateUrl: 'assets/views/main.html',
        controller: 'MainCtrl'
      })
      .when('/existing', {
        templateUrl: 'assets/views/existing.html',
        controller: 'MainCtrl'
      })
      .when('/admin', {
        templateUrl: 'assets/views/admin.html',
        controller: 'MainCtrl'
      })
      .otherwise({
        redirectTo: '/'
      });
  });
