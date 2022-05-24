/* global swal, urlWebServicies */

app = angular.module('app', []);
app.controller('ctrl_register', function ($scope, $http) {

    $(document).ready(function () {
        // resetear formulario de registro
        $scope.resetForm($scope.form_register);
    });

    $scope.registerUser = (form) => {
        if (form.$valid) {
            if (form.password_user.$viewValue === form.cpassword_user.$viewValue) {
                let user_data = {
                    "name": form.name_user.$viewValue,
                    "lastname": form.lastname_user.$viewValue,
                    "email": form.email_user.$viewValue,
                    "password": form.password_user.$viewValue,
                    "provider": "native",
                    "base64": ""
                };
                apiRegisterUser(user_data, form);
            } else {
                alertAll({"status": 3, "information": "Passwords do not match"});
            }
        }
    };


    $scope.resetForm = (form) => {
        $scope.name_user = "";
        $scope.lastname_user = "";
        $scope.email_user = "";
        $scope.password_user = "";
        $scope.cpassword_user = "";

        form.$setPristine();
        form.$setUntouched();
    };

    apiRegisterUser = (api_param, form) => {
        $.ajax({
            method: "POST",
            dataType: "json",
            contentType: "application/json; charset=utf-8",
            url: urlWebServicies + 'personapis/register',
            data: JSON.stringify({...api_param}),
            beforeSend: (xhr) => {
                loading();
            },
            success: (data) => {
                swal.close();
                console.log(data);
                alertAll(data);
                $scope.$apply(() => {
                    $scope.resetForm(form);
                });
            },
            error: (objXMLHttpRequest) => {
                console.log("error: ", objXMLHttpRequest);
            }
        });
    };

});