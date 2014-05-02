'use strict';

angular.module('moonBuddyApp')
  .controller('MainCtrl', function ($scope, $http, $routeParams) {
      $http.get('/questions').success(function(res) {
          console.log(res);
          $scope.questions = res;



          if ($routeParams.user_id) {
              //go get the user data!
              $scope.getUserAnswers($routeParams.user_id);
              $scope.user = {};
              $scope.user.username = $routeParams.user_id.substr(0, 
                                                    $routeParams.user_id.indexOf('@'));
          }
      });

      //update questions with users previous answers
      $scope.updateQuestions = function(ans) {
          angular.forEach(ans, function(a, v) {
              angular.forEach($scope.questions, function(key, val) {
                  if (key.id === a.question_id) {
                      key.value = a.answer;
                  }
              });
          });
              
      };

      $scope.getUserAnswers = function(id) {
          $http.get('/users/' + id + '/answers')
              .success(function(res) {
                  $scope.answers = res;
                  $scope.updateQuestions($scope.answers);
              })
              .error(function(res) {
                  console.log(res);
              });
      };

      $scope.submitAnswers = function(q, a) {
          //submit to back end;
          var data = {};
          if (!$scope.user.username.length) { 
              return this.invalid(); 
          }

          data.user_id = $scope.user.username + "@moonfruit.com";
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
              console.log(q);

              angular.forEach($scope.questions, function(key, value) {
                  if (key.id === res.question_id) {
                      key.success = 'Success';
                  }
              });

          }).error(function(res) {
              console.log(res);

              angular.forEach($scope.questions, function(key, value) {
                  if (key.id === res.question_id) {
                      key.success = 'Error';
                  }
              });

          });

          return true;
          
      };

      $scope.invalid = function() {
          alert('you missed something');
      };

  });
