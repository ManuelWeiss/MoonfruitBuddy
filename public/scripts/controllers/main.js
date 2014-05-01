'use strict';

angular.module('moonBuddyApp')
  .controller('MainCtrl', function ($scope, $http) {
      $scope.user = {
          username: "",
          department: ""
      };

      $http.get('/questions').success(function(res) {
          console.log(res);
          $scope.questions = res;
      });

      $scope.submitAnswers = function(q, a) {
          //submit to back end;
          var data = {};
          if (!$scope.user.username.length || !$scope.user.department.length) { 
              return this.invalid(); 
          }

          console.log($scope.user);
          angular.forEach($scope.questions, function(key, value) {
              console.log(key, value);
          });

          data.user_id = $scope.user.username + "@moonfruit.com";
          data.department = $scope.user.department;
          data.question_id = q;
          data.answer = a;
          
          console.log('This is what i\'m about to post to /answers : ');
          console.log(data);

          $http.post('/answers').success(function(res) {
              console.log(res);
          }).error(function(res) {
              console.log(res);
          });

          return true;
          
      };

      $scope.invalid = function() {
          alert('you missed something');
      };

  });
