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
        $http.post("/newPatient",$scope.patient)
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
    $scope.searchByName=function(){
        $http.get("/patients?keyWord="+$scope.motCle+"&page="+$scope.currentPage+"&size="+$scope.size)
    .then(function(data){
        console.log("data received: ",data)
        $scope.pagePatients=data.data.patientDtos
        $scope.pages=new Array(data.data.totalPages)
    })
    .catch(function(err){
        console.log(err)
    })
    };
    $scope.searchByName();
    $scope.goToPage=function(p){
        $scope.currentPage=p;
        $scope.searchByName()
    };

    $scope.exporterExcel=function(){
        $http.post("/exportExcel",$scope.pagePatients, {responseType: 'arraybuffer'})
        .then(function(response){
            var blob = new Blob([response.data], {
                type: 'application/vnd.openxmlformats-officedocument.spreadsheetml.sheet'
            });

            var link = document.createElement('a');
            link.href = window.URL.createObjectURL(blob);
            link.download = 'patients.xlsx';
            link.click();
        },
    function(error){
        console.log('Error exporting Excel:', error);
    });
    };
    
});