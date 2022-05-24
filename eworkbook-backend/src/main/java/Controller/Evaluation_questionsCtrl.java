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
public class Evaluation_questionsCtrl {

    /**
     * Instancia del acceso a datos de persona.
     */
    Evaluation_questionsDAO eqdao;

    Evaluation_questions eqmodel;
    /**
     * Instancia de la clase destinada a encriptar y generar el código de
     * verificación
     */
    WeEncoder codec;

    public Evaluation_questionsCtrl() {
        eqdao = new Evaluation_questionsDAO();
    }

    public String[] insetEvaluation_questions(
            String qualification, String comment_answer,
            String result_answer, String evaluation_id_evaluation,
            String questions_id_questions, String person_id
    ) {
        String status = "4", message = "Error in the entered parameters", data = "[]";

        if ((Double.parseDouble(qualification) >= 0.00)
                && Methods.verifyParraf(comment_answer, 500, " ")
                && Methods.isInteger(person_id)
                && Methods.isInteger(evaluation_id_evaluation)) {

            eqmodel = new Evaluation_questions();

            eqmodel.setQualification(qualification);
            eqmodel.setComment_answer(comment_answer);
            eqmodel.setResult_answer(result_answer);
            eqmodel.setEvaluation_id_evaluation(evaluation_id_evaluation);
            eqmodel.setQuestions_id_questions(questions_id_questions);

            String[] insertEv = eqdao.insertEvaluation_questions(eqmodel);

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
