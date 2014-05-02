'use strict';

angular.module('moonBuddyApp')
  .controller('UserCtrl', function ($scope, $http, $routeParams) {
      $http.get('/users').success(function(res) {
          $scope.existingUsers = res;
          console.log('eu:');
          console.log(res);

          if ($routeParams.user_id) {
              $scope.user = $scope.getUser($routeParams.user_id);
              $scope.getUserAnswers($routeParams.user_id);
          } else {
              $scope.user = {
                  username: "mweiss",
                  department: "Developers"
              };
          }

      });
      
      $scope.getUser = function(id) {
          var foundUser = {name: 'User not found'};
          angular.forEach($scope.existingUsers, function(user, index) {
              console.log(user);
              if (user.id === id) {
                  foundUser = user;
              }
          });
          return foundUser;
      };

      $scope.getUserAnswers = function(id) {
          $http.get('/users/' + id + '/answers')
              .success(function(res) {
                  $scope.answers = res;
              })
              .error(function(res) {
                  console.log(res);
              });
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

      $scope.findMatch = function(id) {
          $http.get('/buddies/:' + id).success(function(res) {
              $scope.matchingUsers = res;
          }).error(function(res) {
              alert('something went wrong :(');
          });
      };

  });
