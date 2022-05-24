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
public class Question_groupCtrl {

    /**
     * Instancia del acceso a datos de persona.
     */
    Question_groupDAO qgdao;

    Question_group qgmodel;
    /**
     * Instancia de la clase destinada a encriptar y generar el código de
     * verificación
     */
    WeEncoder codec;

    public Question_groupCtrl() {
        qgdao = new Question_groupDAO();
    }

    public String[] insertQuestion_group(
            String group_name,
            String group_description,
            //String state_group,
            String person_id_person
    ) {
        String status = "4", message = "Error in the entered parameters", data = "[]";
        qgmodel = new Question_group();
        codec = new WeEncoder();

        qgmodel.setGroup_name(group_name);
        qgmodel.setGroup_description(group_description);
        qgmodel.setState_group("A");
        qgmodel.setPerson_id_person(person_id_person);

        if (Methods.verifyMaxWords(qgmodel.getGroup_name(), 30, " ")) {
            String resp[] = qgdao.insertQuestion_group(qgmodel);

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
            message = "Check input parameters.";
        }

        return new String[]{status, message, data};
    }

    public String[] updateQuestion_group(
            String id_questiongroup,
            String group_name,
            String group_description,
            //String state_group,
            String person_id_person
    ) {
        String status = "4", message = "Error in the entered parameters", data = "[]";
        qgmodel = new Question_group();
        codec = new WeEncoder();

        qgmodel.setId_questiongroup(codec.textDecryptor(id_questiongroup));
        qgmodel.setGroup_name(group_name);
        qgmodel.setGroup_description(group_description);
        qgmodel.setState_group("A");
        qgmodel.setPerson_id_person(person_id_person);

        if (Methods.verifyMaxWords(qgmodel.getGroup_name(), 30, " ")) {
            String resp[] = qgdao.updateQuestion_group(qgmodel);

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
            message = "Check input parameters.";
        }

        return new String[]{status, message, data};
    }

    public String[] selectQuestion_group(
            String type_question_group,
            String person_id_person
    ) {
        String status = "4", message = "Error in the entered parameters", data = "[]";
        qgmodel = new Question_group();
        codec = new WeEncoder();

        String flag = "-1";
        if (type_question_group.equals("BY DATE")) {
            flag = "1";//
        }
        if (type_question_group.equals("BY NAME")) {
            flag = "2";//
        }
        if (type_question_group.equals("BY RANDOM")) {
            flag = "3";//
        }

        if (flag.matches("[123]")) {

            String resp[] = qgdao.selectQuestion_group(flag, person_id_person);

            if (resp[0].equals("2")) {

                JsonArray jarr = Methods.stringToJsonArray(resp[1]);

                for (int index = 0; index < jarr.size(); index++) {
                    String id_questiongroup = jarr.get(index).getAsJsonObject().get("id_questiongroup").getAsString();
                    id_questiongroup = codec.textEncryptor(id_questiongroup);
                    jarr.get(index).getAsJsonObject().addProperty("id_questiongroup", id_questiongroup);

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
