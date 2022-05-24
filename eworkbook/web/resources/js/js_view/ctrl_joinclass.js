app.controller("joinclass_controller", function ($scope, $http) {
    $scope.tableJoinClass = []; // variable para obtener las clases que se ha unido el usuario
    $scope.classAllId = false; // variable para conocer si se seleccionaron todas las clases
    $scope.countRowsSelected = 0; // variable contadora de filas seleccionadas
    $scope.flagUpdate = false; // variable para saber si se agregara o editara un periodo
    $scope.idClass = 0; // variable para saber el id de un periodo seleccionado
    $scope.classDetails = {}; // variable para visualizar los datos de un periodo seleccionado

    $(document).ready(() => {
        $scope.loadJoinClass();
    });

    //#region FUNCIONES UTILITARIAS

    $scope.openModalJoinClass = () => {
        openModalUtil("modalAddJoinClass");
    };

    $scope.closeModalJoinClass = () => {
        closeModalUtil("modalAddJoinClass");
    };

    $scope.resetForm = (form) => {
        $scope.codeClass = "";

        form.$setPristine();
        form.$setUntouched();
    };

    //#endregion  FUNCIONES UTILITARIAS

    //region CONEXION A LOS WS

    // funcion para uniser a una clase meediante le codigo de la clase
    $scope.appJoinClass = (form) => {
        if(form.$valid){
            let dataJoinClass = {
                "user_token": $scope.DatoUsuario.user_token,
                "code_class": form.codeClass.$modelValue
            };
            wsAppJoinClass(dataJoinClass, form);
        }
    };

    // funcion ajax para uniser a una clase meediante le codigo de la clase
    let wsAppJoinClass = (api_param, form) => {
        $.ajax({
            method: "POST",
            dataType: "json",
            contentType: "application/json; charset=utf-8",
            url: urlWebServicies + 'classroomapis/joinclassroom',
            data: JSON.stringify({...api_param}),
            beforeSend: (xhr) => {
                loading();
            },
            success: (data) => {
                swal.close();
                console.log(data);
                $scope.$apply(() => {
                    $scope.resetForm(form);
                });
                alertAll(data);
            },
            error: (objXMLHttpRequest) => {
                console.log("error: ", objXMLHttpRequest);
            }
        });
    }

    // funcion para cargar las clases unidas
    $scope.loadJoinClass = () => {
        let dataLoadJoinClass = {
            "user_token" : $scope.DatoUsuario.user_token,
            "type_class": "MY CLASS CREATED JOIN",
            "type_period": "0",
            "state_class" : "A"
        };
        wsLoadJoinClass(dataLoadJoinClass);
    };

    let wsLoadJoinClass = (api_param) => {
        $.ajax({
            method: "POST",
            dataType: "json",
            contentType: "application/json; charset=utf-8",
            url: urlWebServicies + 'classroomapis/myclassroom',
            data: JSON.stringify({...api_param}),
            beforeSend: (xhr) => {
                loading();
            },
            success: (data) => {
                swal.close();
                console.log(data);
                $scope.$apply(() => {
                    $scope.tableJoinClass = data.data;
                });
            },
            error: (objXMLHttpRequest) => {
                console.log("error: ", objXMLHttpRequest);
            }
        });
    };

    //endregion CONEXION A LOS WS

});
