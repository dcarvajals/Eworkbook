app.expandMultipleChoise = function($scope, $http) {

    $scope.tableQuestionBank = [];
    /** variable que obtiene las preguntas creadas por el usuario */
    $scope.tableQuestionType = [];
    /** variable que obtiene los tipos de preguntas registrados */
    $scope.tableQuestion = [];
    /** variable para obtener las preguntas registradas */
    $scope.newQuestion = {};
    $scope.identifiquer = "";
    $scope.countRowsSelected = 0;
    $scope.viewJsonQuestion = [];
    $scope.viewList = false;
    let structureSelected = {};
    let structureItem = {};
    let structureResponse = {};
    $('.dropdown-toggle').dropdown();

    const URL_CLOUD = 'https://api.cloudinary.com/v1_1/cleancodecompany/upload';
    const PASSWORD_CLOUD = 'cex8rpfo';
    
    $scope.loadDocumentAdd = () => {
        $(".modal-backdrop").modal("hide");
        closeModalUtil("modalQuestionType");
        console.log("add loaded");
        $scope.loadQuestionBank(1);
        $scope.loadQuestionType(1);
        
    };

    //#region FUNCIONES UTILITARIAS

    $scope.loadJsonQuestionType = (identifiquer) => {
        $scope.identifiquer = identifiquer;
        $http.get('resources/json/questionCategories.json', {}).then(function (response) {
            structureSelected = Object.assign({}, response.data[identifiquer]);
            $scope.newQuestion = response.data[identifiquer];
            //structureItem = Object.assign({} , response.data[identifiquer].structure.itemsQuestions[0]);
            response.data = {};
            console.log(structureSelected);
        }, function (response) {
            console.log("error");
            console.log(response);
        });
    };

    $scope.validateQuestion = () => {
        if (JSON.stringify($scope.newQuestion) !== '{}') {
            return true;
        } else {
            return false
        }
    }
    

    // funcion para agregar la pregunta generada por el usuario
    $scope.addQuestion = async (form) => {
        if (form.$valid) {

            loading();

            let fileQuestion = undefined;
            let newQuestionStructure = $scope.newQuestion["structure"];
            let urlSelected = {};
            let formData = new FormData();

            if (JSON.stringify(newQuestionStructure.statement_img) !== "{}") {
                urlSelected = newQuestionStructure.statement_img;
            } else if (JSON.stringify(newQuestionStructure.statement_video) !== "{}") {
                urlSelected = newQuestionStructure.statement_video;
            } else if (JSON.stringify(newQuestionStructure.statement_audio) !== "{}") {
                urlSelected = newQuestionStructure.statement_audio;
            }

            if (JSON.stringify(urlSelected) !== "{}")
                fileQuestion = urlSelected.file;

            if (fileQuestion !== undefined) {
                formData.append('file', fileQuestion);
                formData.append('upload_preset', PASSWORD_CLOUD);
                let headers = {headers: {'Content-Type': 'multipart/form-data'}};
                // Enviar a cloudianry
                let response = await axios.post(URL_CLOUD, formData, headers);
                urlSelected.url = response.data["secure_url"];
            }

            for (let x = 0; x < newQuestionStructure["itemsQuestions"].length; x++) {
                let itemQuestions = newQuestionStructure["itemsQuestions"][x];
                urlSelected = {};
                fileQuestion = undefined;
                if (JSON.stringify(itemQuestions.statement_img) !== "{}") {
                    urlSelected = itemQuestions.statement_img;
                } else if (JSON.stringify(itemQuestions.statement_video) !== "{}") {
                    urlSelected = itemQuestions.statement_video;
                } else if (JSON.stringify(itemQuestions.statement_audio) !== "{}") {
                    urlSelected = itemQuestions.statement_audio;
                }

                if (JSON.stringify(urlSelected) !== "{}")
                    fileQuestion = urlSelected.file;

                if (fileQuestion !== undefined) {
                    formData.append('file', fileQuestion);
                    formData.append('upload_preset', PASSWORD_CLOUD);
                    let headers = {headers: {'Content-Type': 'multipart/form-data'}};
                    // Enviar a cloudianry
                    let response = await axios.post(URL_CLOUD, formData, headers);
                    urlSelected.url = response.data["secure_url"];
                }

                for (let y = 0; y < itemQuestions["responses"].length; y++) {
                    let itemResponses = itemQuestions["responses"][y];
                    urlSelected = {};
                    fileQuestion = undefined;
                    if (JSON.stringify(itemResponses.statement_img) !== "{}") {
                        urlSelected = itemResponses.statement_img;
                    } else if (JSON.stringify(itemResponses.statement_video) !== "{}") {
                        urlSelected = itemResponses.statement_video;
                    } else if (JSON.stringify(itemResponses.statement_audio) !== "{}") {
                        urlSelected = itemResponses.statement_audio;
                    }

                    if (JSON.stringify(urlSelected) !== "{}")
                        fileQuestion = urlSelected.file;

                    if (fileQuestion !== undefined) {
                        formData.append('file', fileQuestion);
                        formData.append('upload_preset', PASSWORD_CLOUD);
                        let headers = {headers: {'Content-Type': 'multipart/form-data'}};
                        // Enviar a cloudianry
                        let response = await axios.post(URL_CLOUD, formData, headers);
                        urlSelected.url = response.data["secure_url"];
                    }

                }

            }

            console.log(JSON.stringify($scope.newQuestion));
            let dataAddQuestion = {
                "user_token": $scope.DatoUsuario.user_token,
                "name_question": $scope.questionName_q,
                "description_question": $scope.question_q,
                "required_question": "",
                "file_question": btoa(JSON.stringify($scope.newQuestion)),
                "question_group_id_questiongroup": form.questionbank_q.$modelValue,
                "evaluation_categories_id_categorie": form.questionType_q.$modelValue
            };
            console.log(dataAddQuestion);
            wsAddQuestion(dataAddQuestion);
        }
    };

    async function uploadImage(file) {
        if (file !== undefined) {
            let urlCloud = 'https://api.cloudinary.com/v1_1/cleancodecompany/upload';
            let passwordCloud = 'cex8rpfo';
            let formData = new FormData();
            formData.append('file', file);
            formData.append('upload_preset', passwordCloud);
            // Enviar a cloudianry
            let response = await axios.post(
                urlCloud,
                formData,
                {
                    headers: {
                        'Content-Type': 'multipart/form-data'
                    }
                }
            );
            url_view = response.data.secure_url.toString();
            console.log("F");
            console.log(response.data.secure_url.toString());
            return response.data.secure_url.toString();
        } else {
            return "";
        }
    }

    let wsAddQuestion = (api_param) => {
        $.ajax({
            method: "POST",
            dataType: "json",
            contentType: "application/json; charset=utf-8",
            url: urlWebServicies + 'questionsapis/insertquestions',
            data: JSON.stringify({...api_param}),
            beforeSend: () => {
                // loading();
            },
            success: (data) => {
                swal.close();
                console.log(data);
                $scope.$apply(() => {
                    $scope.loadQuestions();
                    $scope.newQuestion = {};
                    $("#modalAddQuestion").modal("hide");
                });
                alertAll(data);
            },
            error: (objXMLHttpRequest) => {
                console.log("error: ", objXMLHttpRequest);
            }
        });
    }

    // funcion para cancelar el ingreso de una pregunta
    $scope.cancelNewQuestion = (form) => {
        $scope.questionbank_q = "-1";
        $scope.questionType_q = "-1";
        $scope.questionName_q = "";
        $scope.question_q = "";
        $scope.newQuestion = {};
        form.$setPristine();
        form.$setUntouched();
        $scope.closeModalAddQuestion();
    }

    // funcion para agregar un nuevo item depedendiendo de la estrcutra seleccionada
    $scope.addIemQuestion = () => {
        $http.get('resources/json/questionCategories.json', {}).then(function (response) {
            let identifiquer = $scope.identifiquer;
            structureItem = Object.assign({}, response.data[identifiquer].structure.itemsQuestions[0]);
            let item = $scope.newQuestion.structure.itemsQuestions;
            item.push(structureItem);
            console.log(structureItem);
        }, function (response) {
            console.log("error");
            console.log(response);
        });
    };

    // funcion para agregar las posibles respuestas de cada item
    $scope.addPossibleResponse = (itemsQuestions) => {
        $http.get('resources/json/questionCategories.json', {}).then(function (response) {
            structureResponse = Object.assign({}, response.data[$scope.identifiquer].structure.itemsQuestions[0].responses[0]);
            itemsQuestions.responses.push(structureResponse);
            console.log($scope.newQuestion);
        }, function (response) {
            console.log("error");
            console.log(response);
        });
    }

    let messageFileSuccessfully = (finename) => {
        let obj = {
            "data": {},
            "information": "File " + finename + " successfully loaded.",
            "status": 1,
            "tittle": "File Selection"
        };
        alertAll(obj);
    }

    let messageFileOnError = () => {
        let obj = {
            "data": {},
            "information": "File format not supported.",
            "status": 4,
            "tittle": "File Selection."
        };
        alertAll(obj);
    };

    let resetFiles = (object) => {
        object.statement_video = {};
        object.statement_audio = {};
        object.statement_img = {};
    };

    // funcion para obtener el indice que contiene el identificador seleccionado
    let getIndex = (identifiquer) => {
        let index = identifiquer.split("@")[1];
        return parseInt(index);
    }

    // funcion para agregar imagenes/videos/audios a la pregunta general
    $scope.uploadFile = (file, typeFile) => {
        let fileUpload = file.files[0];
        let fileExtension = fileUpload.name.toString().substring(fileUpload.name.toString().lastIndexOf(".") + 1, fileUpload.name.toString().length);
        let finename = fileUpload.name.toString().replace("." + fileExtension, "");
        let jsonFile = {"name": finename, "ext": fileExtension, "file": fileUpload, "url": ""};
        resetFiles($scope.newQuestion.structure);
        switch (typeFile) {
            case 1:
                if (fileExtension === "png" || fileExtension === "jpg") {
                    $scope.$apply(() => {
                        $scope.newQuestion.structure.statement_img = Object.assign({}, jsonFile);
                    });
                    messageFileSuccessfully(finename);
                } else {
                    messageFileOnError();
                }
                break;
            case 2 :
                if (fileExtension === "mp4") {
                    $scope.$apply(() => {
                        $scope.newQuestion.structure.statement_video = Object.assign({}, jsonFile);
                    });
                    messageFileSuccessfully(finename);
                } else {
                    messageFileOnError();
                }
                break;
            case 3:
                if (fileExtension === "mp3" || fileExtension === "ogg") {
                    $scope.$apply(() => {
                        $scope.newQuestion.structure.statement_audio = Object.assign({}, jsonFile);
                    });
                    messageFileSuccessfully(finename);
                } else {
                    messageFileOnError();
                }
                break;
        }
        console.log($scope.newQuestion);
    };

    // funcion que permite agregar imagenes/video/audio para las preguntas items
    $scope.uploadFileItem = (file, typeFile) => {
        let index = getIndex(file.id);
        let fileUpload = file.files[0];
        let fileExtension = fileUpload.name.toString().substring(fileUpload.name.toString().lastIndexOf(".") + 1, fileUpload.name.toString().length);
        let finename = fileUpload.name.toString().replace("." + fileExtension, "");
        let jsonFile = {"name": finename, "ext": fileExtension, "file": fileUpload, "url": ""};
        resetFiles($scope.newQuestion.structure.itemsQuestions[index]);
        switch (typeFile) {
            case 1:
                if (fileExtension === "png" || fileExtension === "jpg") {
                    $scope.$apply(() => {
                        $scope.newQuestion.structure.itemsQuestions[index].statement_img = Object.assign({}, jsonFile);
                    });
                    messageFileSuccessfully(finename);
                } else {
                    messageFileOnError();
                }
                break;
            case 2 :
                if (fileExtension === "mp4") {
                    $scope.$apply(() => {
                        $scope.newQuestion.structure.itemsQuestions[index].statement_video = Object.assign({}, jsonFile);
                    });
                    messageFileSuccessfully(finename);
                } else {
                    messageFileOnError();
                }
                break;
            case 3:
                if (fileExtension === "mp3") {
                    $scope.$apply(() => {
                        $scope.newQuestion.structure.itemsQuestions[index].statement_audio = Object.assign({}, jsonFile);
                    });
                    messageFileSuccessfully(finename);
                } else {
                    messageFileOnError();
                }
                break;
        }
        console.log($scope.newQuestion);
    }

    let getIndexResponse = (identifiquer) => {
        return [parseInt(identifiquer.split('@')[1].split('.')[0]),
            parseInt(identifiquer.split('@')[1].split('.')[1])];
    }

    // funcion para agregar imagen/video/audio en las posibles respuestas de las preguntas
    $scope.uploadResponse = (file, typeFile) => {
        let index = getIndexResponse(file.id);
        let fileUpload = file.files[0];
        let fileExtension = fileUpload.name.toString().substring(fileUpload.name.toString().lastIndexOf(".") + 1, fileUpload.name.toString().length);
        let finename = fileUpload.name.toString().replace("." + fileExtension, "");
        let jsonFile = {"name": finename, "ext": fileExtension, "file": fileUpload, "url": ""};
        let json = $scope.newQuestion.structure.itemsQuestions[index[0]].responses[index[1]];
        resetFiles(json);
        switch (typeFile) {
            case 1:
                if (fileExtension === "png" || fileExtension === "jpg") {
                    $scope.$apply(() => {
                        json.statement_img = Object.assign({}, jsonFile);
                    });
                    messageFileSuccessfully(finename);
                } else {
                    messageFileOnError();
                }
                break;
            case 2 :
                if (fileExtension === "mp4") {
                    $scope.$apply(() => {
                        json.statement_video = Object.assign({}, jsonFile);
                    });
                    messageFileSuccessfully(finename);
                } else {
                    messageFileOnError();
                }
                break;
            case 3:
                if (fileExtension === "mp3" || fileExtension === "ogg") {
                    $scope.$apply(() => {
                        json.statement_audio = Object.assign({}, jsonFile);
                    });
                    messageFileSuccessfully(finename);
                } else {
                    messageFileOnError();
                }
                break;
        }
        console.log($scope.newQuestion);
    }

    // funcion para agregar el texto a la pregunta principal
    $scope.changeQuestionGeneral = (model) => {
        $scope.newQuestion.structure.statement_text = model
        console.log($scope.newQuestion);
    };

    // funcion para agregar texto a los items
    $scope.changeTextItem = (index) => {
        $scope.newQuestion.structure.itemsQuestions[index].statement_text = $("#ipItem" + (index + 1)).val();
        console.log($scope.newQuestion);
    };

    // funcion para agregar nota a los items
    $scope.changeNoteItem = (index) => {
        $scope.newQuestion.structure.itemsQuestions[index].note = $("#ipItemNote" + (index + 1)).val();
        console.log($scope.newQuestion);
    };

    // funcion para agregar texto a las posibles respuestas
    $scope.changeTextResponse = (index, indexResponse) => {
        $scope.newQuestion.structure.itemsQuestions[index].responses[indexResponse].statement_text = $("#ipResponse" + index + '_' + indexResponse).val();
        console.log($scope.newQuestion);
    };

    // funcion para saber que respuesta es la correcta
    $scope.changeStatusResponse = (index, indexResponse) => {
        $scope.newQuestion.structure.itemsQuestions[index].responses[indexResponse].flag_correct =
            !$scope.newQuestion.structure.itemsQuestions[index].responses[indexResponse].flag_correct;
        console.log($scope.newQuestion);
    };



//#endregion FUNCIONES UTILITARIAS

    //#region CONEXION A LOS WS

// funcion para cargar los bancos de preguntas creados
    $scope.loadQuestionBank = (typeOrden) => {
        let dataLoadQuestionbank = {
            "user_token": $scope.DatoUsuario.user_token,
            "type_orden": typeOrden === 1 ? "BY DATE" : typeOrden === 2 ? "BY NAME" : "BY RANDOM"
        };
        wsLoadQuestionBank(dataLoadQuestionbank);
    };

// funcion ajax para cargar los bancos de preguntas creados
    let wsLoadQuestionBank = (apiParam) => {
        $.ajax({
            method: "POST",
            dataType: "json",
            contentType: "application/json; charset=utf-8",
            url: urlWebServicies + 'questionsgroupapis/selectquestionsgroup',
            data: JSON.stringify({...apiParam}),
            beforeSend: () => {
                loading();
            },
            success: (data) => {
                swal.close();
                console.log(data);
                $scope.$apply(() => {
                    $scope.tableQuestionBank = data.data;
                });
                console.log(data.data);
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

//#endregion CONEXION A LOS WS

};
