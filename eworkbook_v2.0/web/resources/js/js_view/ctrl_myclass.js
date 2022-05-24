/* global store, urlWebServicies, swal, Swal */

app.controller("myclass_controller", function ($scope, $http) {

    //#region VARIABLES
    $scope.viewListClass = true; // variable para ocultar y visualizar la lista de periodos
    $scope.tableClass = []; // variable para la lista de las clases registradas
    $scope.classAllId = false; // variable para conocer si se seleccionaron todas las clases
    $scope.countRowsSelected = 0; // variable contadora de filas seleccionadas
    $scope.flagUpdate = false; // variable para saber si se agregara o editara un periodo
    $scope.idClass = 0; // variable para saber el id de un periodo seleccionado
    $scope.classDetails = {}; // variable para visualizar los datos de un periodo seleccionado
    $scope.tablePeriod = []; // variable para cargar los periodos registrados
    $scope.viewList = false;
    //#endregion VARIABLES

    $(document).ready(() => {
        $scope.appPage.Select = "classroom";
        $scope.$apply(() => {
            $scope.loadClassDefault(0, 1);
        });
    });


    //region FUNCIONES UTILITARIAS
    /**
     * funcion para abrir el modal de una nueva clase
     * */
    $scope.openModalClass = () => {
        $scope.flagUpdate = false;
        openModalUtil("modalAddClass");
    }

    /**
     * funcion para cerrar el modal de una nueva clase
     * */
    $scope.closeModalClass = (form) => {
        $scope.flagUpdate = false;
        $scope.resetForm(form)
        closeModalUtil("modalAddClass");
    }

    /**
     * funcion para abrir el modala de exportacion
     * */
    $scope.openModalExport = () => {
        openModalUtil("modalExportClass");
    };

    /**
     * funcion para cerrar el modal de exportacion
     * */
    $scope.closeModalExport = (form) => {
        form.$setPristine();
        form.$setUntouched();
        closeModalUtil("modalExportClass");
    };

    /**
     * funcion para visualizar los detalles de cada periodo
     * */
    $scope.openModalClassDetails = (classSelected) => {
        console.log(classSelected);
        $scope.classDetails = classSelected;
        openModalUtil("modalClassDetails");
    }

    /**
     * funcion para cerrar el modal de los detalles de cada periodo
     * */
    $scope.closeModalClassDetails = () => {
        closeModalUtil("modalClassDetails");
    };

    /**
     * funcion para obtener los datos de una fila de la lista de clases
     * */
    $scope.selectedRow = (rowData) => {
        $scope.idClass = rowData.id_classroom;
        $scope.selectperiod_cl = rowData.id_period;
        $scope.name_cl = rowData.name_class;
        $scope.number_cl = parseInt(rowData.classroom_number);
        $scope.section_cl = rowData.class_section;
        $scope.description_cl = rowData.class_description;
        console.log("SELECT PERIODOS",$scope.selectperiod_cl);

        $scope.flagUpdate = true;
        openModalUtil("modalAddClass");
    };

    $scope.resetForm = (form) => {
        $scope.selectperiod_cl = -1;
        $scope.name_cl = "";
        $scope.number_cl = "";
        $scope.section_cl = "";
        $scope.description_cl = "";


        form.$setPristine();
        form.$setUntouched();
    };

    /**
     * funcion para seleccionar todos los elementos
     * */
    $scope.selectedAllClass = () => {
        if (!$scope.classAllId) {
            $scope.classAllId = true;
            $scope.countRowsSelected = $scope.tableClass.length;
            for (let i = 0; i < $scope.tableClass.length; i++) {
                let idClass =  $scope.tableClass[i].id_classroom;
                selectedAllCheck(idClass);
            }
        } else {
            $scope.classAllId = false;
            $scope.countRowsSelected = 0;
            for (let i = 0; i < $scope.tableClass.length; i++) {
                let idClass =  $scope.tableClass[i].id_classroom;
                deselectedCheck(idClass);
            }
        }
        console.log($scope.classAllId);
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

    $scope.copyCode = () => {
        var content = document.getElementById('codeClass').innerHTML;
        navigator.clipboard.writeText(content)
            .then(() => {
                console.log("Text copied to clipboard...")
            })
            .catch(err => {
                console.log('Something went wrong', err);
            })
        alertAll({status: 2, information: "Code copied"});
    }

    $scope.openModalSendEmail = (classSelected) => {
        $scope.classDetails = classSelected;
        openModalUtil("modalSendEmail");
    };

    $scope.closeModalSendEmail = () => {
        closeModalUtil("modalSendEmail");
    }

    // funcion para exportar los datos deseados
    $scope.exportClass = (form) => {
        if(form.$valid){
            var datos = document.getElementById("listableclass");
            switch (form.exportFormat.$modelValue) {
                case "1":
                    Exporter.export(datos, 'class.xls', 'Class');
                    break;
                case "2":
                    Exporter.export(datos, 'class.doc', 'Class');
                    break;
            }
        }
    };

    //endregion FUNCIONES UTILITARIAS


    //region CCONEXION A LOS WS

    //funcion para cargar los periodos en un combo
    $scope.loadPeriods = () => {
        let dataParam = {
            "user_token": $scope.DatoUsuario.user_token,
            "type_period": "PERIOD ALL MY"
        };
        wsLoadPeriods(dataParam);
    };

    // funcion ajax para cargar los periodos de un combo
    let wsLoadPeriods = (api_param) => {
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
                console.log(data);
                $scope.$apply( () => {
                    $scope.tablePeriod = data.data;
                    // primero cargaremos todas las clases
                    $scope.loadClassDefault(0, 1);
                });
            },
            error: (objXMLHttpRequest) => {
                console.log("error: ", objXMLHttpRequest);
            }
        });
    };

    $scope.loadAllClass = () => {
        loading();
        $scope.loadClassDefault(0, 1);
    }

    // funcion ajax para cargar las clases registradas
    $scope.loadClassDefault = (idPeriod, type) => {
        let dataClass = {
            "user_token": $scope.DatoUsuario.user_token,
            "type_classroom": type === 1 ? "CLASSROOM ALL" : "PERIOD FOR CLASSROOM",
            "type_period": idPeriod
        };
        wsLoadClass(dataClass);
    };

    // funcion ajax para cargar las clases registradas
    let wsLoadClass = (api_param) => {
        $.ajax({
            method: "POST",
            dataType: "json",
            contentType: "application/json; charset=utf-8",
            url: urlWebServicies + 'classroomapis/selectclassroom',
            data: JSON.stringify({...api_param}),
            beforeSend: (xhr) => {
                //
            },
            success: (data) => {
                swal.close();
                console.log(data);
                $scope.$apply( () => {
                    $scope.tableClass = data.data;
                    if(api_param.type_period === 0){
                        if($scope.tableClass.length > 0){
                            $scope.viewList = true;
                        } else {
                            $scope.viewList = false;
                        }
                    } else {
                        $scope.viewList = true;
                    }
                });
            },
            error: (objXMLHttpRequest) => {
                console.log("error: ", objXMLHttpRequest);
            }
        });
    };

    // funcion para agregar una nueva clase
    $scope.addClass = (form) => {
        if(form.$valid){
            let dataAddClass = {
                "user_token": $scope.DatoUsuario.user_token,
                "name_class" : form.name_cl.$modelValue,
                "classroom_number" : form.number_cl.$modelValue,
                "class_description" : form.description_cl.$modelValue
            };
            wsAddClass(dataAddClass, form);
        }
    };

    // funcion ajax para agregar una nueva clase
    let wsAddClass = (api_param ,form) => {
        $.ajax({
            method: "POST",
            dataType: "json",
            contentType: "application/json; charset=utf-8",
            url: urlWebServicies + 'classroomapis/insertclassroom',
            data: JSON.stringify({...api_param}),
            beforeSend: (xhr) => {
                loading();
            },
            success: (data) => {
                swal.close();
                console.log(data);
                $scope.$apply(() => {
                    $scope.resetForm(form)
                    closeModalUtil("modal_classroom");
                    $scope.loadClassDefault(0, 1);
                });
                alertAll(data);
            },
            error: (objXMLHttpRequest) => {
                console.log("error: ", objXMLHttpRequest);
            }
        });
    };

    // funcion para editar la clase
    $scope.updateClass = (form) => {
        if(form.$valid){
            let dataUpdateClass = {
                "user_token": $scope.DatoUsuario.user_token,
                "id_classroom": $scope.idClass,
                "period_id_period": form.selectperiod_cl.$modelValue,
                "name_class" : form.name_cl.$modelValue,
                "classroom_number" : form.number_cl.$modelValue,
                "class_section" : form.section_cl.$modelValue,
                "class_description" : form.description_cl.$modelValue
            };
            wsUpdateClass(dataUpdateClass, form);
        }
    };

    // funcion ajax para editar la clase
    let wsUpdateClass = (api_param, form) => {
        $.ajax({
            method: "POST",
            dataType: "json",
            contentType: "application/json; charset=utf-8",
            url: urlWebServicies + 'classroomapis/updateclassroom',
            data: JSON.stringify({...api_param}),
            beforeSend: (xhr) => {
                loading();
            },
            success: (data) => {
                swal.close();
                console.log(data);
                $scope.$apply(() => {
                    $scope.flagUpdate = false;
                    $scope.resetForm(form)
                    closeModalUtil("modalAddClass");
                    $scope.loadClassDefault(0, 1);
                });
                alertAll(data);
            },
            error: (objXMLHttpRequest) => {
                console.log("error: ", objXMLHttpRequest);
            }
        });
    }

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
                let idClass =  $scope.tableClass[i].idClass;
                deselectedCheck(idClass);
            }
            $scope.$apply(() => {
                $scope.countRowsSelected = 0;
                $scope.classAllId = false;
            });
        })
    };

    // funcion para cargar los estudiantes que pertenecen a una clase
    $scope.viewStudent = (rowData) => {
        angular.element($('[ng-controller="application"]')).scope().redirect("viewstudent?code="+rowData.id_classroom);
    }

    // funcion para parametrizar el listado de las clases mediante el periodo seleccionado
    $scope.changeListClass = (idPeriod) => {
        loading();
        $scope.loadClassDefault(idPeriod, idPeriod === "0" ? 1 : 2);
    };

    //endregion CCONEXION A LOS WS

});
