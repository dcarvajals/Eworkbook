/* global store, urlWebServicies, swal, Swal */
/**
 * @param $scope var angular
 * */
app.controller("home_controller", function ($scope) {

    $scope.queryHome = [];

    $(document).ready(() => {
        $scope.$apply(() => {
            $scope.loadHome();
        });
    });

    // funcion para cargar datos estadisticos
    $scope.loadHome = () => {
        let jsonLoadHome = {
            "user_token": $scope.DatoUsuario.user_token,
            "type_select": "HOME ALL"
        };
        loadHome_AJAX(jsonLoadHome);
    };

    // funcion ajax para cargar datos estadisticos
    let loadHome_AJAX = (api_param) => {
        $.ajax({
            method: "POST",
            dataType: "json",
            contentType: "application/json; charset=utf-8",
            url: urlWebServicies + 'generalapis/selecthome',
            data: JSON.stringify({...api_param}),
            beforeSend: () => {
                loading();
            },
            success: (data) => {
                swal.close();
                console.log(data);
                $scope.$apply(() => {
                    $scope.queryHome = data.data[0];
                });
            },
            error: (objXMLHttpRequest) => {
                console.log("error: ", objXMLHttpRequest);
            }
        });
    }

});