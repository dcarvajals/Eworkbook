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
public class Evaluation_responsesCtrl {

    /**
     * Instancia del acceso a datos de persona.
     */
    Evaluation_responseDAO erdao;

    Evaluation_response ermodel;
    /**
     * Instancia de la clase destinada a encriptar y generar el código de
     * verificación
     */
    WeEncoder codec;

    public Evaluation_responsesCtrl() {
        erdao = new Evaluation_responseDAO();
    }

    public String[] insetEvaluation_questions(
            String evaluation_questions_id_evaluationquestions, String person_id_person,
            String qualification, String state_response
    ) {
        String status = "4", message = "Error in the entered parameters", data = "[]";

        if ((Double.parseDouble(qualification) >= 0.00)
                && Methods.isInteger(person_id_person)
                && Methods.isInteger(evaluation_questions_id_evaluationquestions)) {

            ermodel = new Evaluation_response();
            ermodel.setEvaluation_questions_id_evaluationquestions(evaluation_questions_id_evaluationquestions);
            ermodel.setQualification(qualification);
            ermodel.setPerson_id_person(person_id_person);
            ermodel.setState_response(state_response);

            String[] insertEv = erdao.insertEvaluation_response(ermodel);

            if (insertEv[0].equals("2")) {
                status = "2";
                message = "Data returned correctly.";
                data = insertEv[1];
            } else {
                status = "4";
                message = "An error has occurred.";
            }
        }
        return new String[]{status, message, data};
    }
}
