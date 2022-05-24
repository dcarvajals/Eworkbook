/* global store, urlWebServicies, swal, Swal */

app.controller("questionbank_controller", function ($scope, $http) {

    //app.expandControllerA($scope); // expandir el controlador a otro script js

    //#region VARIABLES
    $scope.tableTypesQuestions = []; // variable para obtener los tipos de preguntas registradas
    $scope.periodAll = false; // variable para conocer si se seleccionaron todos los periodos
    $scope.countRowsSelected = 0; // variable contadora de filas seleccionadas
    $scope.flagUpdate = false; // variable para saber si se agregara o editara un periodo
    $scope.idTypeQuestion = 0; // variable para saber el id de un periodo seleccionado
    $scope.typeQuestionBankDetails = {}; // variable para visualizar los datos de un periodo seleccionado
    $scope.tableQuestionType = [];


    //#endregion VARIABLES

    $(document).ready(() => {
        $scope.$apply(() => {
            $scope.loadQuestionBank(1);
            $scope.loadQuestionType(1);
            $scope.loadQuestions();
        });
    });

    //region FUNCIONES UTILITARIAS

    // funcion para abrir el modal de agregar un nuevo banco de preguntas
    $scope.openModalAddTypeQuestion = () => {
        openModalUtil("modalAddNewTypeQuestion");
    };

    // funcion para cerrar el modal de agregar un nuevo banco de preguntas
    $scope.closeModalAddTypeQuestion = (form) => {
        $scope.resetForm(form);
        closeModalUtil("modalAddNewTypeQuestion");
    };

    // funcion para abrir el modal de los detalles de cada banco de preguntas
    $scope.openModalBankQuestionDetails = () => {
        openModalUtil("modalBankQuestionDetails");
    }

    // funcion para cecrrar el modal de los detalles de cada banco de preguntas
    $scope.closeModalBankQuestionDetails = () => {
        closeModalUtil("modalBankQuestionDetails");
    }

    // funcion para abrir exportar los bancos de preguntas
    $scope.openModalExportQB = () => {
        openModalUtil("modalExporQuestionBank");
    };
    
        /** funcion para abrir el modal de una nueva pregunta */
    $scope.openModalAddQuestion = () => {
        openModalUtil("modalQuestionType");
    };

    /** funcion para cerraar el modal de una nueva pregunta */
    $scope.closeModalAddQuestion = () => {
        closeModalUtil("modalQuestionType");
    };

    // funcion para cerrar el modal de exportar los bancos de preguntas
    $scope.closeModalQB = (form) => {
        $scope.exportFormat = "-1";
        form.$setPristine();
        form.$setUntouched()
        closeModalUtil("modalExporQuestionBank");
    };

    // funcion para limpiar el form
    $scope.resetForm = (form) => {
        $scope.idTypeQuestion = 0;
        $scope.name_tq = "";
        $scope.description_tq = "";
        form.$setPristine();
        form.$setUntouched();
    };

    // funcion para seleccionar los datos al visualizar
    $scope.selectedView = (object) => {
        $scope.typeQuestionBankDetails = object;
        $scope.openModalBankQuestionDetails();
    };

    // funcion para seleccionar los datos a editar
    $scope.selectedUpdate = (object) => {
        $scope.flagUpdate = true; // variable en true para editar los datos seleccionados
        $scope.idTypeQuestion = object["id_questiongroup"]; // id del banco de pregunta
        $scope.name_tq = object.group_name;
        $scope.description_tq = object.group_description;
        $scope.openModalAddTypeQuestion();
    };

    /**
     * funcion para seleccionar todos los elementos
     * */
    $scope.selectedAllQB = () => {
        if (!$scope.classAllId) {
            $scope.classAllId = true;
            $scope.countRowsSelected = $scope.tableTypesQuestions.length;
            for (let i = 0; i < $scope.tableTypesQuestions.length; i++) {
                let idQuestiongroup = $scope.tableTypesQuestions[i].id_questiongroup;
                selectedAllCheck(idQuestiongroup);
            }
        } else {
            $scope.classAllId = false;
            $scope.countRowsSelected = 0;
            for (let i = 0; i < $scope.tableTypesQuestions.length; i++) {
                let idQuestiongroup = $scope.tableTypesQuestions[i].id_questiongroup;
                deselectedCheck(idQuestiongroup);
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

    //funcion para cambiar de pestania en los tabs
    $scope.showTab = (id, idHide) => {
        $("#" + id).show();
        $("#" + idHide).hide();
    };

    $scope.addQuestion = (folder) => {
        closeModalUtil("modalQuestionType");
        $scope.redirect(folder);
    };

    // funcion para visualizar la pregunta 
    $scope.viewQuestion = (obj) => {
        if (obj["name_categorie"] === "Multiple choices") {
            $scope.redirect("mch_view/" + obj["id_questions"]);
        }
    };

    //endregion FUNCIONES UTILITARIAS

    //#region ACCESO A LOS WS

    // funcion para cargar los bancos de preguntas registrados
    $scope.loadQuestionBank = (typeOrden) => {
        let dataLoadQuestionbank = {
            "user_token": $scope.DatoUsuario.user_token,
            "type_orden": typeOrden === 1 ? "BY DATE" : typeOrden === 2 ? "BY NAME" : "BY RANDOM"
        };
        wsLoadQuestionBank(dataLoadQuestionbank);
    };

    // funcion ajax para cargar los bancos de preguntas registrados
    let wsLoadQuestionBank = (apiParam) => {
        $.ajax({
            method: "POST",
            dataType: "json",
            contentType: "application/json; charset=utf-8",
            url: urlWebServicies + 'questionsgroupapis/selectquestionsgroup',
            data: JSON.stringify({...apiParam}),
            beforeSend: (xhr) => {
                loading();
            },
            success: (data) => {
                swal.close();
                console.log(data);
                $scope.$apply(() => {
                    $scope.tableTypesQuestions = data.data;
                });
            },
            error: (objXMLHttpRequest) => {
                console.log("error: ", objXMLHttpRequest);
            }
        });
    };

    // funcion para cargar los tipos de preguntas registrados
    $scope.loadQuestionType = (typeQuestion) => {
        let dataLoadQuestionType = {
            "user_token": $scope.DatoUsuario.user_token,
            "id_type": typeQuestion === 1 ? "ORDER BY NAME" : "ORDER BY REGISTRATION"
        };
        wsLoadQuestionType(dataLoadQuestionType);
    };

    // funcion ajax para cargar los tipos de preguntas registrados
    let wsLoadQuestionType = (apiParam) => {
        $.ajax({
            method: "POST",
            dataType: "json",
            contentType: "application/json; charset=utf-8",
            url: urlWebServicies + 'evaluationcategoriesapis/selectevaluationcategories',
            data: JSON.stringify({...apiParam}),
            beforeSend: () => {
                loading();
            },
            success: (data) => {
                swal.close();
                console.log(data);
                $scope.$apply(() => {
                    $scope.tableQuestionType = data.data;
                });
            },
            error: (objXMLHttpRequest) => {
                console.log("error: ", objXMLHttpRequest);
            }
        });
    };

    // funcion para agregar un nuevo tipo de pregunta
    $scope.addQuestionBank = (form) => {
        if (form.$valid) {
            let dataTypeQuestion = {
                "user_token": $scope.DatoUsuario.user_token,
                "group_name": form.name_tq.$modelValue,
                "group_description": form.description_tq.$modelValue
            };
            wsAddQuestionBank(dataTypeQuestion, form);
        }
    };

    // funcion ajax para agregar un nuevo tupo de pregunta
    let wsAddQuestionBank = (apiParam, form) => {
        $.ajax({
            method: "POST",
            dataType: "json",
            contentType: "application/json; charset=utf-8",
            url: urlWebServicies + 'questionsgroupapis/insertquestionsgroup',
            data: JSON.stringify({...apiParam}),
            beforeSend: (xhr) => {
                loading();
            },
            success: (data) => {
                swal.close();
                console.log(data);
                $scope.$apply(() => {
                    $scope.loadQuestionBank(1);
                    $scope.resetForm(form);
                });
                closeModalUtil('modalAddNewTypeQuestion');
                alertAll(data);
            },
            error: (objXMLHttpRequest) => {
                console.log("error: ", objXMLHttpRequest);
            }
        });
    };

    // funcion para editar los datos del banco de pregunta
    $scope.updateQuestionBank = (form) => {
        if (form.$valid) {
            let dataTypeQuestion = {
                "user_token": $scope.DatoUsuario.user_token,
                "id_questiongroup": $scope.idTypeQuestion,
                "group_name": form.name_tq.$modelValue,
                "group_description": form.description_tq.$modelValue
            };
            wsUpdateQuestionBank(dataTypeQuestion, form);
        }
    }

    let wsUpdateQuestionBank = (apiParam, form) => {
        $.ajax({
            method: "POST",
            dataType: "json",
            contentType: "application/json; charset=utf-8",
            url: urlWebServicies + 'questionsgroupapis/updatequestionsgroup',
            data: JSON.stringify({...apiParam}),
            beforeSend: (xhr) => {
                loading();
            },
            success: (data) => {
                swal.close();
                console.log(data);
                $scope.$apply(() => {
                    $scope.loadQuestionBank(1);
                    $scope.resetForm(form);
                });
                closeModalUtil('modalAddNewTypeQuestion');
                alertAll(data);
            },
            error: (objXMLHttpRequest) => {
                console.log("error: ", objXMLHttpRequest);
            }
        });
    };

    // funcion para cargar las preguntas por banco de pregunta
    $scope.changeQuestionsBank = () => {
        let dataLoadQuestions = {
            "user_token": $scope.DatoUsuario.user_token,
            "type_group": "QUESTIONS BY GROUP",
            "parameter_group": $scope.selectQG,
            "parameter_categorie": "0"
        };
        wsLoadQuestions(dataLoadQuestions);
    };

    // funcion para cargar las preguntas por tipo de pregunta
    $scope.changeQuestionsType = () => {
        let dataLoadQuestions = {
            "user_token": $scope.DatoUsuario.user_token,
            "type_group": "QUESTIONS BY CATEGORY",
            "parameter_group": "0",
            "parameter_categorie": $scope.selectQT
        };
        wsLoadQuestions(dataLoadQuestions);
    };

    $scope.loadQuestions = () => {
        let dataLoadQuestions = {
            "user_token": $scope.DatoUsuario.user_token,
            "type_group": "ALL",
            "parameter_group": "0",
            "parameter_categorie": "0"
        };
        wsLoadQuestions(dataLoadQuestions);
    };

    // funcion para cargar todas las preguntas sin filtros
    let wsLoadQuestions = (api_param) => {
        $.ajax({
            method: "POST",
            dataType: "json",
            contentType: "application/json; charset=utf-8",
            url: urlWebServicies + 'questionsapis/selectquestions',
            data: JSON.stringify({...api_param}),
            beforeSend: () => {
                loading();
            },
            success: (data) => {
                swal.close();
                console.log(data);
                $scope.$apply(() => {
                    $scope.tableQuestion = data.data;
                    if (api_param.type_group === "ALL") {
                        $scope.viewList = $scope.tableQuestion.length > 0;
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

    //#endregion ACCESO A LOS WS

});
