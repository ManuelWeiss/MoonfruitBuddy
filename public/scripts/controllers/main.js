'use strict';

angular.module('moonBuddyApp')
  .controller('MainCtrl', function ($scope, $http) {
//      $http.get('http://gecko.greekattic.local:9000/questions', function(res) {
//          console.log(res);
//      });

      $scope.questions = [{"id":"test-q1","text":"Silly question No. 1","scaleExplain":"not at all - totally","scaleFrom":0.0,"scaleTo":10.0},{"id":"test-q2","text":"Silly question No. 2","scaleExplain":"not at all - totally","scaleFrom":0.0,"scaleTo":10.0},{"id":"test-q3","text":"Silly question No. 3","scaleExplain":"not at all - totally","scaleFrom":0.0,"scaleTo":10.0},{"id":"test-q4","text":"Silly question No. 4","scaleExplain":"not at all - totally","scaleFrom":0.0,"scaleTo":10.0},{"id":"test-q5","text":"Silly question No. 5","scaleExplain":"not at all - totally","scaleFrom":0.0,"scaleTo":10.0}];

      $scope.testing = function(e) {
          console.log(e);
      };
  });
