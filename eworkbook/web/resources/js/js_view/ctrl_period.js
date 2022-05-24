/* global urlWebServicies, store, swal */

app.controller("period_controller", function ($scope, $http) {

    //#region VARIABLES
    $scope.viewListPeriod = false; // variable para ocultar y visualizar la lista de periodos
    $scope.tablePeriod = []; // variable para la lista de los periodos registrados
    $scope.periodAll = false; // variable para conocer si se seleccionaron todos los periodos
    $scope.countRowsSelected = 0; // variable contadora de filas seleccionadas
    $scope.flagUpdate = false; // variable para saber si se agregara o editara un periodo
    $scope.idPeriod = 0; // variable para saber el id de un periodo seleccionado
    $scope.perdioDetails = {}; // variable para visualizar los datos de un periodo seleccionado

    //#endregion VARIABLES

    $(document).ready(() => {
        $scope.$apply(() => {
            $scope.loadPeriodsDefault();
        });
        console.log($scope.tablePeriod);
    });


    //#region FUNCIONES UTILITARIAS

    /**
     * funcion para abrir el modal de un nuevo periodo
     * */
    $scope.openModalPeriod = () => {
        $scope.flagUpdate = false;
        openModalUtil("modalAddPriod");
    };

    /**
     * funcion para cerrar el modal de un nuevo periodo
     * */
    $scope.closeModalPeriod = (form) => {
        resetFormPeriod(form);
        $scope.flagUpdate = false;
        closeModalUtil("modalAddPriod");
    }

    /**
     * funcion para abrir el modala de exportacion
     * */
    $scope.openModalExport = () => {
        openModalUtil("modalExportPeriods");
    };

    /**
     * funcion para cerrar el modal de exportacion
     * */
    $scope.closeModalExport = (form) => {
        form.$setPristine();
        form.$setUntouched();
        closeModalUtil("modalExportPeriods");
    };

    /**
     * funcion para visualizar los detalles de cada periodo
     * */
    $scope.openModalPeriodDetails = (perdioSelected) => {
        $scope.perdioDetails = perdioSelected;
        openModalUtil("modalPeriodDetails");
    }

    /**
     * funcion para cerrar el modal de los detalles de cada periodo
     * */
    $scope.closeModalPeriodDetails = () => {
        closeModalUtil("modalPeriodDetails");
    };

    /**
     * funcion para resetear el form de un nuevo periodo
     * */
    resetFormPeriod = (form) => {
        $scope.name_ap = "";
        $scope.specialty_ap = "";
        $scope.description_ap = "";
        $scope.startdate_np = "";
        $scope.enddate_np = "";

        form.$setPristine();
        form.$setUntouched();
    }

    /**
     * funcion para seleccionar todos los elementos
     * */
    $scope.selectedAllPeriod = () => {
        if (!$scope.periodAll) {
            $scope.periodAll = true;
            $scope.countRowsSelected = $scope.tablePeriod.length;
            for (let i = 0; i < $scope.tablePeriod.length; i++) {
                let idPeriod = $scope.tablePeriod[i].id_period;
                selectedAllCheck(idPeriod);
            }
        } else {
            $scope.periodAll = false;
            $scope.countRowsSelected = 0;
            for (let i = 0; i < $scope.tablePeriod.length; i++) {
                let idPeriod = $scope.tablePeriod[i].id_period;
                deselectedCheck(idPeriod);
            }
        }
        console.log($scope.periodAll);
    }

    /**
     * funcion para seleccionar 1 sola fila
     * */
    $scope.selectedRowDelete = (id) => {
        let nameCheck = document.getElementById("check_" + id);
        console.log(nameCheck.checked)
        $scope.countRowsSelected += nameCheck.checked ? 1 : -1;
        console.log($scope.countRowsSelected);
    }

    /**
     * funcion para obtener los datos de una fila de la lista de periodos
     * */
    $scope.selectedRow = (rowData) => {
        $scope.idPeriod = rowData.id_period;
        $scope.name_ap = rowData.name_period;
        $scope.specialty_ap = rowData.specialty_period;
        $scope.description_ap = rowData.description_period;
        let startDate = rowData.start_date_period;
        $scope.startdate_np = new Date(startDate.replaceAll('-', '/'));
        let endDate = rowData.end_date_period;
        $scope.enddate_np = new Date(endDate.replaceAll('-', '/'));

        $scope.flagUpdate = true;
        openModalUtil("modalAddPriod");
    };

    // funcion para exportar los datos deseados
    $scope.exportPeriods = (form) => {
        if(form.$valid){
            var datos = document.getElementById("listableperiod");
            switch (form.exportFormat.$modelValue) {
                case "1":
                    Exporter.export(datos, 'periodos.xls', 'Periodos');
                    break;
                case "2":
                    Exporter.export(datos, 'periodos.doc', 'Periodos');
                    break;
            }
            $("#modalExportPeriods").modal("hide");
        }
    };

    // funcion para activar los botones de cada registro
    $scope.activeButton = (id) => {
        let name = "#btn_" + id.replace('=', '');
        console.log(name);
        //$(name).dropdown("show");
    };

    //endregion FUNCIONES UTILITARIAS

    //#region CONEXION A LOS WS

    // funcion para cargar los periodos registrados
    $scope.loadPeriodsDefault = () => {
        let dataParam = {
            "user_token": $scope.DatoUsuario.user_token,
            "type_period": "PERIOD ALL MY"
        };
        console.log(dataParam);
        wsLoadPeriodsDefault(dataParam);
    };

    wsLoadPeriodsDefault = (api_param) => {
        $.ajax({
            method: "POST",
            dataType: "json",
            contentType: "application/json; charset=utf-8",
            url: urlWebServicies + 'periodapis/selectperiod',
            data: JSON.stringify({...api_param}),
            beforeSend: (xhr) => {
                loading();
            },
            success: (data) => {
                swal.close();
                console.log(data);
                $scope.$apply( () => {
                    $scope.tablePeriod = data.data;
                });
            },
            error: (objXMLHttpRequest) => {
                console.log("error: ", objXMLHttpRequest);
            }
        });
    }

    // funcion para agregar un nuevo periodo
    $scope.AddPeriod = (form) => {
        if(form.$valid){
            let dataAddPeriod = {
                "user_token": $scope.DatoUsuario.user_token,
                "name_period": form.name_ap.$modelValue,
                "specialty_period": form.specialty_ap.$modelValue,
                "description_period": form.description_ap.$modelValue,
                "start_date_period": form.startdate_np.$modelValue,
                "end_date_period": form.enddate_np.$modelValue
            };
            wsAddPeriod(dataAddPeriod);
        }
    }

    // funcion ajax para agregar un nuevo periodo
    let wsAddPeriod = (apiParam) => {
        $.ajax({
            method: "POST",
            dataType: "json",
            contentType: "application/json; charset=utf-8",
            url: urlWebServicies + 'periodapis/insertperiod',
            data: JSON.stringify({...apiParam}),
            beforeSend: (xhr) => {
                loading();
            },
            success: (data) => {
                swal.close();
                $scope.$apply( () => {
                    //actualizar los periodos
                    $scope.loadPeriodsDefault();
                });
                closeModalUtil('modalAddPriod');
                alertAll(data);
            },
            error: (objXMLHttpRequest) => {
                console.log("error: ", objXMLHttpRequest);
            }
        });
    }

    // funcion para editar un periodo
    $scope.updatePeriod = (form) => {
        let dataUpadatePeriod = {
            "user_token": $scope.DatoUsuario.user_token,
            "id_period": $scope.idPeriod,
            "name_period": form.name_ap.$modelValue,
            "specialty_period": form.specialty_ap.$modelValue,
            "description_period": form.description_ap.$modelValue,
            "start_date_period": form.startdate_np.$modelValue,
            "end_date_period": form.enddate_np.$modelValue
        };
        wsUpadatePeriod(dataUpadatePeriod);
    };

    // funcion ajax para editar un periodo
    let wsUpadatePeriod = (apiParam, form) => {
        $.ajax({
            method: "POST",
            dataType: "json",
            contentType: "application/json; charset=utf-8",
            url: urlWebServicies + 'periodapis/updateperiod',
            data: JSON.stringify({...apiParam}),
            beforeSend: (xhr) => {
                loading();
            },
            success: (data) => {
                swal.close();
                $scope.$apply( () => {
                    //actualizar los periodos
                    $scope.loadPeriodsDefault();
                    $scope.flagUpdate = false;
                });
                closeModalUtil('modalAddPriod');
                alertAll(data);
            },
            error: (objXMLHttpRequest) => {
                console.log("error: ", objXMLHttpRequest);
            }
        });
    };

    // funcion para eliminar todos los periodos o los seleccionados
    $scope.deleteAllSelected = () => {
        Swal.fire({
            title: 'Are you sure?',
            text: "You won't be able to revert this!",
            icon: 'warning',
            showCancelButton: true,
            confirmButtonColor: '#3085d6',
            cancelButtonColor: '#d33',
            confirmButtonText: 'Yes, delete it!'
        }).then((result) => {
            if (result.isConfirmed) {
                Swal.fire(
                    'Deleted!',
                    'Your file has been deleted.',
                    'success'
                )
            }
            for (let i = 0; i < 10; i++) {
                let idPeriod = $scope.tablePeriod[i].idPeriod;
                deselectedCheck(idPeriod);
            }
            $scope.$apply(() => {
                $scope.countRowsSelected = 0;
                $scope.periodAll = false;
            });
        })
    };

    //#endregion CONEXION A LOS WS

});