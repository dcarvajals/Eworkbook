app.controller("multipleChoise_controller", function ($scope, $routeParams, $http) {
    $scope.idQuestion = "";
    $scope.viewJsonQuestion = {}; // variable para visualizar el contenido de la pregunta
    
    app.expandMultipleChoise($scope, $http);

    // funcion que se ejecutara al inicio de cargar la pagina
    $scope.loadDocument = () => {
        // obtener la id que se envie por la url
        $scope.idQuestion = $routeParams["questionId"];
        $scope.loadQuestion($scope.idQuestion);
        console.log( $scope.idQuestion);
    };

    //#region view

    $scope.loadQuestion = (idQuestion) => {
        let dataLoadQuestion = {"user_token": $scope.DatoUsuario.user_token, "id_question": idQuestion};
        wsLoadQuestion(dataLoadQuestion);
    };

    let wsLoadQuestion = (apiParam) => {
        $.ajax({
            method: "POST",
            dataType: "json",
            contentType: "application/json; charset=utf-8",
            url: urlWebServicies + 'questionsapis/viewQuestion',
            data: JSON.stringify({...apiParam}),
            beforeSend: (xhr) => {
                loading();
            },
            success: (data) => {
                swal.close();
                console.log(data);
                $scope.$apply(() => {
                    $scope.viewJsonQuestion = convertToJson(data.data[0]["file_question"]);
                    console.log($scope.viewJsonQuestion);
                });
            },
            error: (objXMLHttpRequest) => {
                console.log("error: ", objXMLHttpRequest);
            }
        });
    };

    //#region FUNCIONES UTILITARIAS
    $scope.soundSelected = {};
    $scope.sound;
    $scope.playPause = (objSound) => {
        if (JSON.stringify($scope.soundSelected) === "{}") {
            // definir un nuevo objeto para escuchar
            $scope.soundSelected["name"] = objSound.name;
            $scope.soundSelected["url"] = objSound.url;
            $scope.soundSelected["play"] = true;
            $scope.sound = new Howl({src: [$scope.soundSelected["url"]]});
            $scope.sound.play();
        } else {
            // buscamos si el mismo objeto que se esta seleccionando
            let flag = false;
            if ($scope.soundSelected["name"] === objSound.name) {
                if (!$scope.soundSelected["play"]) { // iniciar el audio
                    $scope.sound.play();
                    $scope.soundSelected["play"] = true;
                } else { // para el audio
                    $scope.sound.stop();
                    $scope.soundSelected["play"] = false;
                }
            } else {
                // definir un nuevo objeto para escuchar
                $scope.soundSelected["name"] = objSound.name;
                $scope.soundSelected["url"] = objSound.url;
                $scope.soundSelected["play"] = true;
                $scope.sound = new Howl({src: [$scope.soundSelected["url"]]});
                $scope.sound.play();
            }
        }
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
    //#endregion FUNCIONES UTILITARIAS

    //#endregion view

});
