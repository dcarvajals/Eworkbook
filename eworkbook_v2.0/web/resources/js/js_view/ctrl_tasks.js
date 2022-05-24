// tasks_controller
app.controller("tasks_controller", function ($scope, $http) {
    //#region VARIABLES
    $scope.tableTasks = []; // variable para obtener las tareas que han sido registradas
    $scope.countRowsSelected = 0 // variable para saber las filas seleccionadas
    //#endregion VARIABLES

    //funcion que se ejecuta al cargar la pagina
    $(document).ready(() => {
        $scope.$apply(() => {
            $scope.loadDataDefault(); // cargar datos por defecto para pruebas
            console.log($scope.tableTasks)
        });
    });

    //#region FUNCIONES UTILITARIAS

    // funcion para cargar datos para prueba
    $scope.loadDataDefault = () => {
      for(let x = 0; x < 10; x++){
          $scope.tableTasks.push({
              "id_evaluation": x,
              "name_evaluation": "TAREA # " + x,
              "description_evaluation": "Lorem Ipsum es simplemente el texto de relleno de las imprentas y archivos de " +
                  "texto. Lorem Ipsum ha sido el texto de relleno estándar de las industrias",
              "state_evaluation": "Habilitada", // cerrada finalizada
              "date_registration_evaluation": "13-01-2022",
              "date_finished_evaluation": "16-01-2022"
          });
      }
    };

    // funcion para redirigirse al formulario de una nueva tarea
    $scope.openFormAddTask = () => {
        angular.element($('[ng-controller="application"]')).scope().redirect("newtasks");
    };

    //#endregion FUNCIONES UTILITARIAS
});