'use strict';

angular.module('moonBuddyApp')
  .controller('MainCtrl', function ($scope, $http) {
      $http.get('/questions').success(function(res) {
          console.log(res);
          $scope.questions = res;
      });

      $scope.testing = function(e) {
          console.log(e);
      };
  });
