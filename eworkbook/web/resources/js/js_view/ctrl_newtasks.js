app.controller("newtasks_controller", function ($scope) {

    //#region VARIABLES
    $scope.tablePeriod = [];
    $scope.tableClass = [];
    $scope.tableTypesQuestions = [];
    $scope.tableQuestion = [];
    $scope.tableQuestionType = [];
    $scope.countRowsSelected = 0;
    $scope.viewJsonQuestion = {};

    $scope.questionSelected = [];
    //#endregion VARIABLES

    // funcion que cargara al momento de ingresar al modulo
    $(document).ready(() => {
        $scope.loadPeriod(); // cargar los periodos
        $scope.loadQuestionBank(); // cargar los bancos de preguntas
        $scope.loadQuestionType(); // cargar los tipos de preguntas registrados
    });

    $scope.openModalRandomQuestion = () => {
        openModalUtil("modalRandomQuestion");
    };

    $scope.closeModalRandomQuestion = () => {
        closeModalUtil("modalRandomQuestion");
    };

    // funcion para seleccionar una fila
    $scope.selectedRow = (id, obj) => {
        let nameCheck = document.getElementById("check_" + id);
        console.log(nameCheck.checked)
        $scope.countRowsSelected += nameCheck.checked ? 1 : -1;
        let searchRow = $scope.objEqualsPosition($scope.questionSelected, "id_questions", id);
        // verificamos si la pregunta ya fue agregada anteriormente
        if(!searchRow[1]){
            obj["idQuestionBank"] = $scope.questionBSelect;
            // agregamos la pregunta seleccionada que vamos a agregar a la tarea
            $scope.questionSelected.push(obj);
            console.log($scope.countRowsSelected);
        } else {
            if(!nameCheck.checked){
                nameCheck.checked = false;
                $scope.questionSelected.splice(searchRow[0], 1);
            } else {
                alertAll({status: 4, information: "The question is already selected"});
            }
        }

    }

    $scope.objEquals = (array, key, attribute) => {
        for(let x = 0; x < array.length; x++){
            if(array[x][key] === attribute)
                return true
        }
        return false
    }

    $scope.objEqualsPosition = (array, key, attribute) => {
        for(let x = 0; x < array.length; x++){
            if(array[x][key] === attribute)
                return [x, true]
        }
        return [-1, false]
    }

    // funcion para borrar las preguntas seleccionadas
    $scope.deleteQuestionSelected = (index, id) => {
        let nameCheck = document.getElementById("check_" + id);
        nameCheck.checked = false;
        $scope.questionSelected.splice(index, 1);
        $scope.countRowsSelected -- ;
    };

    // funcion regla de 3 para calcular la calificaicon total
    $scope.calculateCalification = () => {
        let quantityQuestions = $scope.questionSelected.length;
        let bass = $scope.bass;

        // buscamos la cantidad de preguntas por cada item de pregunta



        let calification = bass / quantityQuestions;
        for(let i = 0; i < quantityQuestions; i++){
            $("#calf" + i).val(calification.toFixed(2));
        }
    };

    // funcion para visualizar la pregunta seleccionada
    $scope.viewQuestion = (obj) => {
        $("#modalViewQuestion").modal();
        let jsonQuestion = convertToJson(obj.file_question);
        $scope.viewJsonQuestion = jsonQuestion;
        console.log(jsonQuestion);
    };

    //funcion para convertir el base 64 a json
    let convertToJson = (jsonText) => {
        return JSON.parse(atob(jsonText));
    }

    $scope.validateJSON = (json) => {
        return JSON.stringify(json) !== "{}";
    };

    $scope.validateTEXT = (text) => {
        if (text === undefined)
            return false;
        else return text !== "";
    }

    $scope.questionBankAux = [];

    // funcion para generar las preguntas aleatorias
    $scope.generateQuestionRandom = (form) => {
        if(form.$valid){
            let countRandom = form.countNumberQuestion.$modelValue;
            let conuntQuestion = $scope.tableQuestion.length;
            let idQuestionBank = form.questionBSelect.$modelValue;

            if(countRandom > conuntQuestion){
                alertAll({status: 3, information: "The range of random questions exceeds the total number of questions in the question bank."});
                return;
            }

            if($scope.questionSelected.length > 0){
                let flag = false;
                // buscamos las preguntas que se agregaron del banco de pregunta seleccionado
                for(let x = 0; x < $scope.questionSelected.length; x++){
                    let idQuestionBankTable = $scope.questionSelected[x]["idQuestionBank"];
                    if(idQuestionBankTable === idQuestionBank){ // si son iguales
                        $scope.questionSelected.splice(x, 1); // borramos los registros del banco de preguntas
                        x--;
                        $scope.countRowsSelected --;
                        flag = true;
                    }
                }
                if(flag){
                    //limpiar los check
                    for(let posQuestion = 0; posQuestion < $scope.tableQuestion.length; posQuestion++){
                        let idQuestion = $scope.tableQuestion[posQuestion]["id_questions"];
                        let nameCheck = document.getElementById("check_" + idQuestion);
                        nameCheck.checked = false;
                    }

                    let positionsQuestionsSelected = getRandomInt(countRandom, conuntQuestion);
                    // agregamos las preguntas
                    for(let positionQuestion = 0; positionQuestion < positionsQuestionsSelected.length; positionQuestion++){
                        let obj = $scope.tableQuestion[positionsQuestionsSelected[positionQuestion]];
                        let idQuestion = obj["id_questions"];
                        let nameCheck = document.getElementById("check_" + idQuestion);
                        nameCheck.checked = true;
                        $scope.selectedRow(idQuestion, obj);
                    }
                    console.log(positionsQuestionsSelected);
                } else {
                    //limpiar los check
                    for(let posQuestion = 0; posQuestion < $scope.tableQuestion.length; posQuestion++){
                        let idQuestion = $scope.tableQuestion[posQuestion]["id_questions"];
                        let nameCheck = document.getElementById("check_" + idQuestion);
                        nameCheck.checked = false;
                    }

                    let positionsQuestionsSelected = getRandomInt(countRandom, conuntQuestion);
                    // agregamos las preguntas
                    for(let positionQuestion = 0; positionQuestion < positionsQuestionsSelected.length; positionQuestion++){
                        let obj = $scope.tableQuestion[positionsQuestionsSelected[positionQuestion]];
                        let idQuestion = obj["id_questions"];
                        let nameCheck = document.getElementById("check_" + idQuestion);
                        nameCheck.checked = true;
                        $scope.selectedRow(idQuestion, obj);
                    }
                    console.log(positionsQuestionsSelected);
                }
            }else {
                //limpiar los check
                for(let posQuestion = 0; posQuestion < $scope.tableQuestion.length; posQuestion++){
                    let idQuestion = $scope.tableQuestion[posQuestion]["id_questions"];
                    let nameCheck = document.getElementById("check_" + idQuestion);
                    nameCheck.checked = false;
                }

                //reinicar el contador de preguntas seleccionadas
                $scope.countRowsSelected = 0;

                //limpiar la tabla de preguntas seleccionadas
                $scope.questionSelected.length = 0;

                let positionsQuestionsSelected = getRandomInt(countRandom, conuntQuestion);
                // agregamos las preguntas
                for(let positionQuestion = 0; positionQuestion < positionsQuestionsSelected.length; positionQuestion++){
                    let obj = $scope.tableQuestion[positionsQuestionsSelected[positionQuestion]];
                    let idQuestion = obj["id_questions"];
                    let nameCheck = document.getElementById("check_" + idQuestion);
                    nameCheck.checked = true;
                    $scope.selectedRow(idQuestion, obj);
                }
                console.log(positionsQuestionsSelected);
            }

            $scope.closeModalRandomQuestion();
        }
    };

    $scope.objEqualsArray = (array, attribute) => {
        for(let x = 0; x < array.length; x++){
            if(array[x] === attribute)
                return[x, true];
        }
        return[-1, false];
    }

    function getRandomInt(cantidadNumeros, cantidadPreguntas) {
        var myArray = []
        while(myArray.length < cantidadNumeros ){
            var numeroAleatorio = Math.floor(Math.random() * (cantidadPreguntas - 0));
            var existe = false;
            for(var i=0;i<myArray.length;i++){
                if(myArray[i] === numeroAleatorio){
                    existe = true;
                    break;
                }
            }
            if(!existe){
                myArray.push(numeroAleatorio);
            }
        }
        return myArray;
    }

    //#region CONEXION A WS

    $scope.calculatePoints = () => {
        for(let positionQuestion = 0; positionQuestion < $scope.questionSelected.length; positionQuestion++){
            let idQuestion = $scope.questionSelected[positionQuestion]["id_questions"];
                let dataLoadQuestion = {"user_token": $scope.DatoUsuario.user_token, "id_question": idQuestion};
                let encryptQuestion = getDataQuestion(dataLoadQuestion);
                console.log("Pregunta encritpada: ", encryptQuestion);
        }
    };

    async function getDataQuestion(apiParam) {
        return new Promise(function (resolve, reject) {
            $.ajax({
                method: "POST",
                dataType: "json",
                contentType: "application/json; charset=utf-8",
                url: urlWebServicies + 'questionsapis/viewQuestion',
                data: JSON.stringify({...apiParam}),
                beforeSend: function () {
                },
                success: function (data) {
                    resolve(data) // Resolve promise and when success
                },
                error: function (err) {
                    reject(err) // Reject the promise and go to catch()
                }
            });
        });
    }

    // funcion para cargar los peridoos disponibles por el usuario
    $scope.loadPeriod = () => {
        let dataParam = {
            "user_token": $scope.DatoUsuario.user_token,
            "type_period": "PERIOD ALL MY"
        };
        console.log(dataParam);
        wsLoadPeriodsDefault(dataParam);
    };

    // funcion ajax para cargar los periodos disponibles por el usuario
    let wsLoadPeriodsDefault = (api_param) => {
        $.ajax({
            method: "POST",
            dataType: "json",
            contentType: "application/json; charset=utf-8",
            url: urlWebServicies + 'periodapis/selectperiod',
            data: JSON.stringify({...api_param}),
            beforeSend: () => {
                loading();
            },
            success: (data) => {
                swal.close();
                console.log(data);
                $scope.$apply(() => {
                    $scope.tablePeriod = data.data;
                });
            },
            error: (objXMLHttpRequest) => {
                console.log("error: ", objXMLHttpRequest);
            }
        });
    }

    // funcion para cargar las clases quepertenecen a cada periodo
    $scope.loadClass = () => {
        let dataClass = {
            "user_token": $scope.DatoUsuario.user_token,
            "type_classroom": "PERIOD FOR CLASSROOM",
            "type_period": $scope.periodSelect
        };
        console.log(dataClass);
        wsLoadClass(dataClass);
    }

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
                $scope.$apply(() => {
                    $scope.tableClass = data.data;
                });
                console.log($scope.tableClass);
            },
            error: (objXMLHttpRequest) => {
                console.log("error: ", objXMLHttpRequest);
            }
        });
    };

    // funcion para cargar los bancos de preguntas registrados
    $scope.loadQuestionBank = (typeOrden) => {
        let dataLoadQuestionbank = {
            "user_token": $scope.DatoUsuario.user_token,
            "type_orden": "BY NAME"
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
            "id_type":"ORDER BY NAME"
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

    // funcion para cargar las preguntas dependiendo del  banco de pregunta
    $scope.loadQuestionsByCategory = () => {
        let dataLoadQuestions = {
            "user_token": $scope.DatoUsuario.user_token,
            "type_group": "QUESTIONS BY CATEGORY",
            "parameter_group": "0",
            "parameter_categorie": $scope.questionTSelect
        };
        wsLoadQuestions(dataLoadQuestions);
    };

    // funcion para cargar las preguntas dependiendo del tipo de pregunta

    // funcion para cargar pregunas por banco de preguntas
    $scope.loadQuestionByGroup = () => {
        let dataLoadQuestions = {
            "user_token": $scope.DatoUsuario.user_token,
            "type_group": "QUESTIONS BY GROUP",
            "parameter_group": $scope.questionBSelect,
            "parameter_categorie": "0"
        };
        wsLoadQuestions(dataLoadQuestions);
    };

    // funcion para cargar las preguntas
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
                });
            },
            error: (objXMLHttpRequest) => {
                console.log("error: ", objXMLHttpRequest);
            }
        });
    };

    $scope.createEvaluation = (form) => {
        //if(form.$valid){
            let dateInit = form.openActivity_nt.$viewValue + " " + form.startTime_nt.$viewValue;
            let dateFinish = form.closeActivity_nt.$viewValue + " " + form.timeEnd_nt.$viewValue;
            let dataCreateEvaluation = {
                "classroom_id_classroom": form.classSelect.$modelValue,
                "name_evaluation": form.nameTask.$modelValue,
                "description_evaluation": form.descrptionTask.$modelValue,
                "date_init_evaluation": dateInit,
                "date_finish_evaluation": dateFinish,
                "grade_base": form.bass.$modelValue,
                "number_attempts": form.attempts_nt.$modelValue,
                "highest_score": form.points_attempts.$modelValue === "hs",
                "average_score" : form.points_attempts.$modelValue === "as" ,
                "hide_answers": form.hide_answers.$modelValue !== undefined
            };
            console.log(dataCreateEvaluation);
       // }
    };

    //#endregion CONEXION A WS
});
