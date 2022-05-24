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
import com.google.gson.Gson;
import com.google.gson.JsonElement;

/**
 *
 * @author CleanCode
 */
public class EvaluationCtrl {

    /**
     * Instancia del acceso a datos de persona.
     */
    EvaluationDAO edao;
    /**
     * Instancia de la clase destinada a encriptar y generar el código de
     * verificación
     */
    WeEncoder codec;

    public EvaluationCtrl() {
        edao = new EvaluationDAO();
    }

    public String[] insetEvaluation(
            String name_evaluation, String description_evaluation, String state_evaluation,
            String date_init_evaluation, String date_finish_evaluation, String file_route_evaluation,
            String external_resource, String classroom_id_classroom
    ) {
        String status = "4", message = "Error in the entered parameters", data = "[]";
        String datetimeMatch = "/(\\d{4})-(\\d{2})-(\\d{2}) (\\d{2}):(\\d{2}):(\\d{2})/";

        System.out.println("date_init_evaluation: " + Methods.testregex(datetimeMatch, date_init_evaluation));
        System.out.println("date_finish_evaluation: " + Methods.testregex(datetimeMatch, date_finish_evaluation));

        if (Methods.verifyString(name_evaluation, "", 50)
                && Methods.verifyParraf(description_evaluation, 250, " ")
                && Methods.verifyString(state_evaluation, "", 1)
                && Methods.testregex(datetimeMatch, date_init_evaluation)
                && Methods.testregex(datetimeMatch, date_finish_evaluation)
                && Methods.isInteger(classroom_id_classroom)) {

            Evaluation evaluation = new Evaluation();

            evaluation.setName_evaluation(name_evaluation);
            evaluation.setDescription_evaluation(description_evaluation);
            evaluation.setState_evaluation(state_evaluation);
            evaluation.setDate_init_evaluation(date_init_evaluation);
            evaluation.setDate_finish_evaluation(date_finish_evaluation);
            evaluation.setFile_route_evaluation(file_route_evaluation);
            evaluation.setExternal_resource(external_resource);
            evaluation.setClassroom_id_classroom(classroom_id_classroom);

            String[] insertEv = edao.insertEvaluation(evaluation);

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

    public String[] createEvaluation(
            String name_evaluation, String description_evaluation, String state_evaluation,
            String date_init_evaluation, String date_finish_evaluation, String file_route_evaluation,
            String external_resource, String classroom_id_classroom, JsonArray evaluation_questions
    ) {
        String status = "4", message = "Error in the entered parameters", data = "[]";
        String datetimeMatch = "/(\\d{4})-(\\d{2})-(\\d{2}) (\\d{2}):(\\d{2}):(\\d{2})/";

        System.out.println("date_init_evaluation: " + Methods.testregex(datetimeMatch, date_init_evaluation));
        System.out.println("date_finish_evaluation: " + Methods.testregex(datetimeMatch, date_finish_evaluation));

        if (Methods.verifyString(name_evaluation, "", 50)
                && Methods.verifyParraf(description_evaluation, 250, " ")
                && Methods.verifyString(state_evaluation, "", 1)
                && Methods.testregex(datetimeMatch, date_init_evaluation)
                && Methods.testregex(datetimeMatch, date_finish_evaluation)
                && Methods.isInteger(classroom_id_classroom)) {

            Evaluation evaluation = new Evaluation();

            evaluation.setName_evaluation(name_evaluation);
            evaluation.setDescription_evaluation(description_evaluation);
            evaluation.setState_evaluation(state_evaluation);
            evaluation.setDate_init_evaluation(date_init_evaluation);
            evaluation.setDate_finish_evaluation(date_finish_evaluation);
            evaluation.setFile_route_evaluation(file_route_evaluation);
            evaluation.setExternal_resource(external_resource);
            evaluation.setClassroom_id_classroom(classroom_id_classroom);

            Gson gson = new Gson();

            String all_evaluation_questions = "";
            for (JsonElement jsonTmp : evaluation_questions) {
                //convertir los elementos de jsonarray en un objeto
                Evaluation_questions evtq = gson.fromJson(Methods.JsonElementToJSO(jsonTmp), Evaluation_questions.class);
                //decodificar el id de preguntas y asignarlo al mismo objeto
                String temp_questionId = evtq.getQuestions_id_questions();
                temp_questionId = WeEncoder.decodeANGY(temp_questionId);
                evtq.setQuestions_id_questions(temp_questionId);
                //convertir objeto entidad en xml
                all_evaluation_questions += evtq.returnXml();
            }
            String[] insertEv = edao.createEvaluation(evaluation, all_evaluation_questions);

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
