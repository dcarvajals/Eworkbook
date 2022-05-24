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
public class Evaluation_categoriesCtrl {

    /**
     * Instancia del acceso a datos de persona.
     */
    Evaluation_categoriesDAO ecdao;
    /**
     * Instancia de la clase destinada a encriptar y generar el código de
     * verificación
     */
    WeEncoder codec;

    public Evaluation_categoriesCtrl() {
        ecdao = new Evaluation_categoriesDAO();
    }

    public String[] selectEvaluationCategoriesCtrl(
            String type_period
    ) {
        String status = "4", message = "Error in the entered parameters", data = "[]";

        codec = new WeEncoder();
        String flag = "-1";
        if (type_period.equals("ORDER BY NAME")) {
            flag = "1";//
        }
        if (type_period.equals("ORDER BY REGISTRATION")) {
            flag = "2";//
        }

        if (flag.matches("[12]")) {

            String resp[] = ecdao.selectEvaluation_categories(flag);

            if (resp[0].equals("2")) {

                JsonArray jarr = Methods.stringToJsonArray(resp[1]);

                for (int index = 0; index < jarr.size(); index++) {
                    String id_categorie = jarr.get(index).getAsJsonObject().get("id_categorie").getAsString();
                    id_categorie = codec.textEncryptor(id_categorie);
                    jarr.get(index).getAsJsonObject().addProperty("id_categorie", id_categorie);

                }
                status = "2";
                message = "Data returned correctly.";
                data = jarr.toString();

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
