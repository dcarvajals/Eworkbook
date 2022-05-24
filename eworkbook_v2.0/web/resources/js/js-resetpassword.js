app = angular.module('app', []);
app.controller('ctrl_recoveraccount', function ($scope, $http) {

    var code = "";
    var email = "";

    $(document).ready(() => {
        let urlParams = new URLSearchParams(window.location.search);
        code = urlParams.get('code');
        email = urlParams.get('email');
    });

    $scope.changePassword = (form) => {
        if (form.$valid) {
            let user_data = {
                "flag": "2",
                "code": code,
                "email": email,
                "password": form.cpassword_user.$viewValue
            };
            changePasswordApi(user_data);
        }
    };


    changePasswordApi = (api_param) => {
        $.ajax({
            method: "POST",
            dataType: "json",
            contentType: "application/json; charset=utf-8",
            url: urlWebServicies + 'personapis/changePassword',
            data: JSON.stringify(api_param),
            beforeSend: (xhr) => {
                loading();
            },
            success: (data) => {
                swal.close();
                console.log(data);
                alertAll(data);
                $scope.user_email = "";
                $scope.password_user = "";
                $scope.cpassword_user = "";
            },
            error: (objXMLHttpRequest) => {
                console.log("error: ", objXMLHttpRequest);
            }
        });
    }
});