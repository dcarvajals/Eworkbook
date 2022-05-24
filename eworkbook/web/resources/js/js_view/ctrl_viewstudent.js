app.controller("viewstudent_controller", function ($scope, $http) {
    $scope.tableStudents = [];
    $scope.codeGenerate = {
        "status": false,
        "password": "##########"
    };
    $scope.idStudent = "";
    $scope.idClass = "";

    $(document).ready(() => {
        $scope.$apply(() => {
            $scope.idClass = getParameterByName("code");
            $scope.loadStudentsClass($scope.idClass, 2);
        });
    });

    // funcion para cargar los estudiantes unidos a esa clase
    $scope.loadStudentsClass = (idClass, type) => {
        let dataStudentClass = {
            "user_token" : $scope.DatoUsuario.user_token,
            "type_class" : type === 1 ? "LIST STUDENT LASTNAME" : type === 2 ? "LIST STUDENT NAME" : "LIST STUDENT DATE",
            "id_classroom" : idClass
        };
        wsLoadStudentsClass(dataStudentClass);
    };

    let wsLoadStudentsClass = (api_param) => {
        $.ajax({
            method: "POST",
            dataType: "json",
            contentType: "application/json; charset=utf-8",
            url: urlWebServicies + 'classroomapis/studentclassroom',
            data: JSON.stringify({...api_param}),
            beforeSend: (xhr) => {
                loading();
            },
            success: (data) => {
                swal.close();
                console.log(data);
                $scope.$apply(() => {
                    $scope.tableStudents = data.data;
                });
            },
            error: (objXMLHttpRequest) => {
                console.log("error: ", objXMLHttpRequest);
            }
        });
    };

    $scope.generatePassword = () => {
        if(!$scope.codeGenerate.status){
            $scope.codeGenerate.status = true;
            $scope.codeGenerate.password = generateRandomString(10);
        } else {
            alertAll({status: 3, information: "No puede volver a generar otra contraseña"});
        }
    }

    $scope.modalChangePassword = (studenSelected) => {
        $scope.idStudent = studenSelected.idStudent;
        $scope.studentName = studenSelected.lastname_person +' '+ studenSelected.name_person;
        openModalUtil("modalChangePassword");
    }

    $scope.closeModalPassword = () => {
        $scope.codeGenerate.status = false;
        $scope.codeGenerate.password = "##########"
      closeModalUtil("modalChangePassword");
    };

    $scope.openModalChangeStudentName = (studenSelected) => {
        $scope.studentNewName = studenSelected.lastname_person +' '+ studenSelected.name_person;
        openModalUtil("modalChangeName");
    }

    $scope.closeModalChangeStudentName = () => {
        closeModalUtil("modalChangeName");
    }

});