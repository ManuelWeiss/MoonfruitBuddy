'use strict';

angular.module('moonBuddyApp')
  .controller('MainCtrl', function ($scope, $http) {
      $scope.user = {
          username: "mweiss",
          department: "a"
      };

      $http.get('/questions').success(function(res) {
          console.log(res);
          $scope.questions = res;
      });

      $http.get('/users').success(function(res) {
          $scope.existingUsers = res;
          console.log('eu:');
          console.log(res);
      });

      $scope.submitAnswers = function(q, a) {
          //submit to back end;
          var data = {};
          if (!$scope.user.username.length /*|| !$scope.user.department.length*/) { 
              return this.invalid(); 
          }

          console.log($scope.user);
          angular.forEach($scope.questions, function(key, value) {
              console.log(key, value);
          });

          data.user_id = $scope.user.username + "@moonfruit.com";
//          data.department = $scope.user.department;
          data.question_id = q;
          data.answer = (+a);
          
          console.log('This is what i\'m about to post to /answers : ');
          console.log(JSON.stringify(data));

          $http({
              url: '/answers',
              data: JSON.stringify(data),
              method: 'POST',
              headers: {'Content-Type': 'application/json'}
          }).success(function(res) {
              console.log(res);
          }).error(function(res) {
              console.log(res);
          });

          return true;
          
      };

      $scope.invalid = function() {
          alert('you missed something');
      };

      $scope.createUser = function() {
          var data = {
              id: $scope.user.username + "@moonfruit.com",
              name: $scope.user.name,
              department: $scope.user.department,
              team: $scope.user.team
          };
          
          $http({
              url: '/users',
              data: JSON.stringify(data),
              method: 'POST',
              headers: {'Content-Type': 'application/json'}
          }).success(function(res) {
              console.log(res);
          }).error(function(res) {
              console.log(res);
          });

      };


  });
