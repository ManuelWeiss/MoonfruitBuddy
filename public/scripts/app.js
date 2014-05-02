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
      .when('/user/:user_id/answers', {
        templateUrl: 'assets/views/main.html',
        controller: 'MainCtrl'
      })
      .when('/existing', {
        templateUrl: 'assets/views/existing.html',
        controller: 'UserCtrl'
      })
      .when('/admin', {
        templateUrl: 'assets/views/admin.html',
        controller: 'UserCtrl'
      })
      .when('/user/:user_id', {
        templateUrl: 'assets/views/user.html',
        controller: 'UserCtrl'
      })
      .otherwise({
        redirectTo: '/'
      });
  });
