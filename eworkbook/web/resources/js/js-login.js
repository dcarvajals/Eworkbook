app = angular.module('app', []);
app.controller('ctrl_login', function ($scope, $http) {

    $(document).ready(function () {
        var email_login = document.getElementById('email_login');
        email_login.onpaste = function (e) {
            e.preventDefault();
            alertAll({"status": 3, "information": "Action not allowed"});
        };

        email_login.oncopy = function (e) {
            e.preventDefault();
            alertAll({"status": 3, "information": "Action not allowed"});
        };
        var password_login = document.getElementById('password_login');
        password_login.onpaste = function (e) {
            e.preventDefault();
            alertAll({"status": 3, "information": "Action not allowed"});
        };

        password_login.oncopy = function (e) {
            e.preventDefault();
            alertAll({"status": 3, "information": "Action not allowed"});
        };

        var email_recover = document.getElementById('email_recover');
        email_recover.onpaste = function (e) {
            e.preventDefault();
            alertAll({"status": 3, "information": "Action not allowed"});
        };

        email_recover.oncopy = function (e) {
            e.preventDefault();
            alertAll({"status": 3, "information": "Action not allowed"});
        };

    });

    var firebaseConfig = {
        apiKey: "AIzaSyD3hdaX1PMTmokOcxqsAX7qybpz0C1LTVI",
        authDomain: "eworkbook-apis.firebaseapp.com",
        projectId: "eworkbook-apis",
        storageBucket: "eworkbook-apis.appspot.com",
        messagingSenderId: "61971212451",
        appId: "1:61971212451:web:d10e75925a0d901a927716",
        measurementId: "G-ST7ZS03F6D"
    };

    // Initialize Firebase
    firebase.initializeApp(firebaseConfig);
    const auth = firebase.auth();

    $scope.logGoogle = function () {
        let provider = new firebase.auth.GoogleAuthProvider();
        provider.setCustomParameters({
            prompt: 'select_account'
        });
        $scope.consume(provider);
    };

    $scope.visible = 1; //bandera para mostrar div
    $scope.mostrar = function (param) {
        if (param) {
            $scope.visible = param;
        }
    };

    $scope.cancelar = function () {
        $scope.modelregistrar = {};
        $scope.mostrar(1);
    };

    $scope.consume = function (provider) {
        auth.signInWithPopup(provider).then(function (result) {
            let userinfo = result.additionalUserInfo;
            let userprofile = userinfo.profile;
            let datosUser = {
                isNewUser: userinfo.isNewUser,
                provider: userinfo.providerId,
                userid: String(userinfo.profile.id)
            };
//            console.log(userinfo);
            switch (userinfo.providerId) {
                case "google.com":
                    {
                        //userimage: userinfo.profile.picture,
                        datosUser['userimage'] = userprofile.picture;
                        datosUser['useremail'] = userprofile.email;
                        datosUser['username'] = userprofile.given_name;
                        datosUser['userlastname'] = userprofile.family_name;
                    }
                    break;
                default:
                    break;
            }
            if (datosUser['userlastname'] === undefined)
            {
                let {username, ...datos} = datosUser;//desestructuración de objetdos
                username = operarnombre(username);//dividir el name de forma pro
                datosUser = {...datos, ...username};//juntar ambos json en uno solo :3
            }

            $scope.cargarApiModal(datosUser);
        }).catch(function (error) {
            swalDelay({
                status: 4,
                tittle: "Service provider error!",
                information: error.message
            });
        });
    };

    $scope.cargarApiModal = function (datosUser) {
        $scope.mostrar(2);
        $scope.$apply(function () {
            $scope.modelregistrar = datosUser;
        });
        let canvas = document.getElementById("login_api_canvas");
        let ctx = canvas.getContext("2d");
        let img = new Image();
        img.crossOrigin = "Anonymous";
        img.onload = function () {
            canvas.width = img.width;
            canvas.height = img.height;
            ctx.drawImage(img, 0, 0);
            $scope.modelregistrar.userimage = canvas.toDataURL("image/png");
        };
        img.src = $scope.modelregistrar.userimage;
//        console.log("new user data", $scope.modelregistrar);
    };

    $scope.logIn = (form) => {

        $.ajax({
            method: "POST",
            dataType: "json",
            contentType: "application/json; charset=utf-8",
            url: urlWebServicies + 'personapis/logIn',
            data: JSON.stringify({
                "email": form.email_login.$viewValue,
                "password": form.password_login.$viewValue}),
            beforeSend: function (xhr) {
                loading();
            },
            success: function (data) {
                swal.close();
                console.log(data);
                if (data.status === 2) {
                    store.session.set("user_eworkook", data.data[0]);
                    location.href = 'app.html#!/home';
                }
                alertAll(data);
            },
            error: function (objXMLHttpRequest) {
                console.log("error: ", objXMLHttpRequest);
            }
        });
    };

    $scope.loginApi = function () {
        callApipersona($scope.modelregistrar);
    };


    function callApipersona(jsonparam) {
        console.log(jsonparam);
        $.when(personaAjax(jsonparam)).then(function (data) {
            swal.close();
            console.log("procesa data", data);
            if (data.status === 2)
            {
                data.tittle = "Log In";
                store.session.set("user_eworkook", data.data[0]);
                toastrDelay(data, 'app.html#!/home');
            } else {
                $scope.cancelar();
            }
        });
    }

    function personaAjax(data) {
        return $.ajax({
            method: "POST",
            dataType: "json",
            contentType: "application/json; charset=utf-8",
            url: urlWebServicies + 'personapis/logInApis',
            data: JSON.stringify(data),
            beforeSend: function () {
                loading();
            }, error: function (objXMLHttpRequest) {
                console.log("error", objXMLHttpRequest);
                swal.fire("!Oh no¡", "Se ha producido un problema.", "error");
            }
        });
    }

    $scope.sendCodeEmail = (form) => {
        if (form.$valid) {
            sendCodeEmail(form.email_recover.$viewValue);
        }
    };

    function sendCodeEmail(email) {
        $.ajax({
            method: "POST",
            dataType: "json",
            contentType: "application/json; charset=utf-8",
            url: urlWebServicies + "personapis/requestCode",
            data: JSON.stringify({"email": email, flag: "2"}),
            beforeSend: function () {
                loading();
            },
            success: function (data) {
                swal.close();
                data.tittle = "Recuperación de Cuenta.";
                console.log(data);
                alertAll(data);
            },
            error: function (jqXHR, textStatus, errorThrown) {
                console.log(jqXHR);
                console.log(textStatus);
                console.log(errorThrown);
            }
        });
    }

    $scope.openModal = (modal) => {
        openModalUtil(modal);
    };

    function operarnombre(paramName) {
        let partes = paramName.toString().trim().split(" ");
        let obj = {
            username: '',
            userlastname: ''
        };
        let limit = (partes.length / 2).toFixed(0);
        for (var ind = 0; ind < partes.length; ind++) {
            let minpart = partes[ind];
            if (minpart.length > 0)
            {
                if (ind < limit) {
                    obj['username'] = obj['username'].length > 0 ? " " : "" + minpart;
                } else {
                    obj['userlastname'] = obj['userlastname'].length > 0 ? " " : "" + minpart;
                }
            } else {
            }
        }
        return obj;
    }

});