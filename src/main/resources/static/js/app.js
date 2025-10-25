var app=angular.module("MyApp", ['ui.router']);
app.config(function($stateProvider, $urlRouterProvider){
    $stateProvider.state('home', {
        url: '/home',
        templateUrl:'views/home.html',
        controller: 'HomeController'
    });
    $stateProvider.state('chercher', {
        url: '/chercher',
        templateUrl:'views/chercher.html',
        controller: 'MyController'
    });
    $stateProvider.state('newPatient', {
        url: '/newPatient',
        templateUrl:'views/NewPatient.html',
        controller: 'NewPatientController'
    });

    $urlRouterProvider.otherwise('/');
})
app.controller('HomeController', function(){

})
app.controller('NewPatientController', function($scope, $http){
    $scope.patient={};
    $scope.savePatient=function(){
        $http.post("http://localhost:8080/newPatient",$scope.patient)
        .then(function(data){
            alert("le patient est enregistre avec succes")
        })
        .catch(function(err){
            alert(err)
        })
    }
    
})
app.controller("MyController", function($scope, $http){
    $scope.pagePatients=null;
    $scope.motCle="";
    $scope.currentPage=0;
    $scope.size=4
    $scope.pages=[]
     $http.get("http://localhost:8080/patients?page="+$scope.currentPage+"&size="+$scope.size)
    .then(function(data){
        console.log("data received: ",data)
        $scope.pagePatients=data.data.patientDtos
        $scope.pages=new Array(data.data.totalPages)
    })
    .catch(function(err){
        console.log(err)
    })
    $scope.searchAll=function(){
        $http.get("http://localhost:8080/patients?page="+$scope.currentPage+"&size="+$scope.size)
    .then(function(data){
        console.log("data received: ",data)
        $scope.pagePatients=data.data.patientDtos
        $scope.pages=new Array(data.data.totalPages)
    })
    .catch(function(err){
        console.log(err)
    })
    }
    $scope.goToPage=function(p){
        $scope.currentPage=p;
        $scope.searchAll()
    }
    $scope.chercherPatient=function(){
        $http.get("http://localhost:8080/patientsByName/"+$scope.motCle)
    .then(function(data){
        console.log("data received: ",data)
        $scope.pagePatients=data.data
    })
    .catch(function(err){
        console.log(err)
    })
    }
    
});