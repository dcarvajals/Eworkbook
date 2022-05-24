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
public class PeriodCtrl {

    /**
     * Instancia del acceso a datos de persona.
     */
    PeriodDAO pdao;

    Period pmodel;
    /**
     * Instancia de la clase destinada a encriptar y generar el código de
     * verificación
     */
    WeEncoder codec;

    public PeriodCtrl() {
        pdao = new PeriodDAO();
    }

    public String[] insertPeriod(
            String name_period,
            //String state_period,
            String person_id_person,
            String start_date_period,
            String end_date_period,
            String description_period,
            String specialty_period
    ) {
        String status = "4", message = "Error in the entered parameters", data = "[]";
        pmodel = new Period();
        pmodel.setName_period(name_period);
        pmodel.setState_period("A");
        pmodel.setPerson_id_person(person_id_person);
        pmodel.setStart_date_period(start_date_period);
        pmodel.setEnd_date_period(end_date_period);
        pmodel.setDescription_period(description_period);
        pmodel.setSpecialty_period(specialty_period);

        if (Methods.verifyMaxWords(pmodel.getName_period(), 20, " ")) {

            String resp[] = pdao.insertPeriod(pmodel);

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

    public String[] selectAllPeriod(
            String type_period,
            String person_id_person
    ) {
        String status = "4", message = "Error in the entered parameters", data = "[]";
        pmodel = new Period();
        codec = new WeEncoder();
        String flag = "-1";
        if (type_period.equals("PERIOD ALL")) {
            flag = "1";//Todos los periodos
        }
        if (type_period.equals("PERIOD ALL MY")) {
            flag = "2";//Todos los periodos
        }

        if (flag.matches("[12]")) {
            String resp[] = pdao.selectAllPeriod(flag, person_id_person);

            if (resp[0].equals("2")) {

                JsonArray jarr = Methods.stringToJsonArray(resp[1]);

                for (int index = 0; index < jarr.size(); index++) {

                    String code = jarr.get(index).getAsJsonObject().get("id_period").getAsString();
                    System.out.println(code);
                    code = codec.textEncryptor(code);
                    jarr.get(index).getAsJsonObject().addProperty("id_period", code);
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

    public String[] updatePeriod(
            String id_period,
            String name_period,
            //String state_period,
            String person_id_person,
            String start_date_period,
            String end_date_period,
            String description_period,
            String specialty_period
    ) {
        String status = "4", message = "Error in the entered parameters", data = "[]";
        pmodel = new Period();
        codec = new WeEncoder();

        pmodel.setId_period(codec.textDecryptor(id_period));
        pmodel.setName_period(name_period);
        pmodel.setState_period("A");
        pmodel.setPerson_id_person(person_id_person);
        pmodel.setStart_date_period(start_date_period);
        pmodel.setEnd_date_period(end_date_period);
        pmodel.setDescription_period(description_period);
        pmodel.setSpecialty_period(specialty_period);

        if (Methods.verifyMaxWords(pmodel.getName_period(), 20, " ")) {

            String resp[] = pdao.updatePeriod(pmodel);

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
}
