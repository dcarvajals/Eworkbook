/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import DAO.*;
import Models.*;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import javax.swing.table.DefaultTableModel;
import Util.DataStatic;
import Util.FileAccess;
import Util.Methods;
import Util.TemplateEmail;
import Util.WeEncoder;

/**
 *
 * @author CleanCode
 */
public class GeneralCtrl {

    /**
     * Instancia del acceso a datos de persona.
     */
    GeneralDAO gdao;

    /**
     * Instancia de la clase destinada a encriptar y generar el código de
     * verificación
     */
    WeEncoder codec;

    public GeneralCtrl() {
        gdao = new GeneralDAO();
    }

    public String[] selectHome(
            String type_period,
            String person_id_person
    ) {
        String status = "4", message = "Error in the entered parameters", data = "[]";

//        codec = new WeEncoder();
        String flag = "-1";
        if (type_period.equals("HOME ALL")) {
            flag = "1";
        }

        if (flag.matches("[1]")) {

            String resp[] = gdao.selectHome(flag, person_id_person);

            if (resp[0].equals("2")) {

                status = "2";
                message = "Data returned correctly.";
                data = resp[1];

            } else {
                status = "4";
                message = "An error has occurred.";

            }
        } else {
            status = "3";
            message = "Check the parameters entered.";
        }

        return new String[]{status, message, data};
    }

}
