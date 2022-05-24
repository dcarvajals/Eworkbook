var app = angular.module('app', ["ngRoute"]);//, "ngAnimate"
app.config(function ($routeProvider) {
    $routeProvider
        .when("/", {
            templateUrl: "home.html",
            controller: "home_controller"
        })
        .when("/home", {
            templateUrl: "home.html",
            controller: "home_controller"
        })
        .when("/period", {
            templateUrl: "period.html",
            controller: "period_controller"
        })
        .when("/classroom", {
            templateUrl: "classroom.html",
            controller: "myclass_controller"
        })
        .when("/tasks", {
            templateUrl: "tasks.html",
            controller: "tasks_controller"
        })
        .when("/newtasks", {
            templateUrl: "newtasks.html",
            controller: "newtasks_controller"
        })
        .when("/viewstudent", {
            templateUrl: "view_student.html",
            controller: "viewstudent_controller"
        })
        .when("/questionbank", {
            templateUrl: "questionbank.html",
            controller: "questionbank_controller"
        })
        .when("/question", {
            templateUrl: "question.html",
            controller: "question_controller"
        })
        .when("/joinclass", {
            templateUrl: "joinclass.html",
            controller: "joinclass_controller"
        })
        .when("/myclassjoin", {
            templateUrl: "myclassjoin.html",
            controller: "myclassjoin_controller"
        })

        /**
         * MODULOS PARA LAS PREGUNTAS
         * */

        //#region multiple_choise
        .when("/mch_view/:questionId", {
            templateUrl: "questions/multiple_choise/view.html",
            controller: "multipleChoise_controller"
        })
        .when("/mch_add", {
            templateUrl: "questions/multiple_choise/add.html",
            controller: "multipleChoise_controller"
        })
        //#endregion mutiple_choise

        /**
         * MODULOS PARA LAS PREGUNTAS
         * */

        .otherwise({
            redirectTo: 'notfound',
            templateUrl: 'notfound.html',
            controller: "notfound_controller"
        });
});

//#region FILTROS DE BUSQUEDA

app.filter('searchPeriod', function () {
    return function (caracteristicas, consulta) {
        //si no hay ninguna consulta, devolver toda la lista
        if (!consulta) return caracteristicas;

        var periodosFiltrados = [];

        //recorremos las valores para ver cual se queda y cual se va
        angular.forEach(caracteristicas, function (valueSearch) {
            //obtenemos el valor de cada atributo, o un string vacío, para evitar problemas
            var periodname = valueSearch.name_period || '';
            var subject = valueSearch.specialty_period || '';

            //si el valor de la consulta existe dentro del atributo, va a ser `true`
            var periodNameValid = periodname.indexOf(consulta) !== -1;
            var subjectValid = subject.indexOf(consulta) !== -1;

            //si el nombre o la direccion contienen a la consulta, entonces agrego la estación
            if (periodNameValid || subjectValid) {
                periodosFiltrados.push(valueSearch);
            }
        });
        return periodosFiltrados;
    };
})

//#endregion FILTROS DE BUSQUEDA


app.controller("application", function ($scope, $http) {

    $scope.DatoUsuario = {};
    $scope.rutaImgUser = location.origin + rutasStorage.imguser;
    let menu = false;
    $scope.activateMenu = true;

    $scope.appPage = {
        tittle: "Home",
        Select: 'home'
    };

    $(document).ready(function () {
        $scope.DatoUsuario = getDataSession();
        console.log("datos sesion: ", $scope.DatoUsuario);
        $scope.$apply(() => {
            /*$('.dropdown').dropdown();
            $('.dropdown-menu').dropdown();*/
        });
    });

    $scope.changeRol = (rol) => {
        $scope.activateMenu = rol === 'T';
    };

    $scope.closeMenu = () => {
        if(!menu){
            $('#menuUser').dropdown('show');
            menu = true;
        } else {
            $('#menuUser').dropdown('hide');
            menu = false;
        }
    };

    $scope.changeTittlePage = function (tittle, apply) {
        if (apply) {
            $scope.$apply(() => {
                $scope.appPage.tittle = tittle;
            });
        } else {
            $scope.appPage.tittle = tittle;
        }
    };

    $scope.redirect = function (page) {
        if (page) {
            $scope.appPage.Select = page;
            location.href = "#!" + page;
        }
    };

    $scope.selectedOptionmenu = (page) => {
        if (page) {
            $scope.appPage.Select = page;
        }
    }

    $scope.logOut = () => {
        cerrarSesion();
    }

    // funcion para exportar los datos deseados
    $scope.exportTable = (form, idTable, name, modal) => {
        if(form.$valid){
            var datos = document.getElementById(idTable);
            switch (form.exportFormat.$modelValue) {
                case "1":
                    Exporter.export(datos, name +'.xls', name);
                    break;
                case "2":
                    Exporter.export(datos, name + '.doc', name);
                    break;
            }
            closeModalUtil(modal);
        }
    };

});

function verpunto(val) {
    console.log("llamar a", val);
    angular.element($('[ng-view')).scope().verinfopunto(val);
}

function verRuta(val) {
    console.log("llamar a", val);
    angular.element($('[ng-view')).scope().verinforuta(val);
}

//angular.element($('[ng-view')).scope().fun()
