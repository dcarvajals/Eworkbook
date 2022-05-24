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
public class QuestionsCtrl {

    /**
     * Instancia del acceso a datos de persona.
     */
    QuestionsDAO qdao;

    Questions qmodel;
    /**
     * Instancia de la clase destinada a encriptar y generar el código de
     * verificación
     */
    WeEncoder codec;

    public QuestionsCtrl() {
        qdao = new QuestionsDAO();
    }

    public String[] insertQuestions(
            String name_question,
            String description_question,
            //String state_question,
            String required_question,
            String file_question,
            String question_group_id_questiongroup,
            String evaluation_categories_id_categorie,
            String person_id_person
    ) {
        String status = "4", message = "Error in the entered parameters", data = "[]";
        qmodel = new Questions();
        codec = new WeEncoder();

        qmodel.setName_question(name_question);
        qmodel.setDescription_question(description_question);
        qmodel.setState_question("A");
        qmodel.setRequired_question(required_question);
        qmodel.setFile_question(file_question);
        qmodel.setQuestion_group_id_questiongroup(codec.textDecryptor(question_group_id_questiongroup));
        qmodel.setEvaluation_categories_id_categorie(codec.textDecryptor(evaluation_categories_id_categorie));

//        if (Methods.verifyMaxWords(qmodel.getName_question(), 200, " ")
//                && Methods.verifyMaxWords(qmodel.getDescription_question(), 200, " ")) {
        String resp[] = qdao.insertQuestions(qmodel);

        if (resp[0].equals("2")) {
            status = "2";
            message = "Data returned correctly.";
            data = resp[1];

        } else {
            status = "4";
            message = "An error has occurred.";
        }
//
//        } else {
//            status = "3";
//            message = "Check input parameters.";
//        }

        return new String[]{status, message, data};
    }

    public String[] updateQuestions(
            String id_questions,
            String name_question,
            String description_question,
            //String state_question,
            String required_question,
            String file_question,
            String question_group_id_questiongroup,
            String evaluation_categories_id_categorie,
            String person_id_person
    ) {
        String status = "4", message = "Error in the entered parameters", data = "[]";
        qmodel = new Questions();
        codec = new WeEncoder();

        qmodel.setId_questions(codec.textDecryptor(id_questions));
        qmodel.setName_question(name_question);
        qmodel.setDescription_question(description_question);
//        qmodel.setState_question("A");
        qmodel.setRequired_question(required_question);
        qmodel.setFile_question(file_question);
        qmodel.setQuestion_group_id_questiongroup(codec.textDecryptor(question_group_id_questiongroup));
        qmodel.setEvaluation_categories_id_categorie(codec.textDecryptor(evaluation_categories_id_categorie));

//        if (Methods.verifyMaxWords(qmodel.getName_question(), 200, " ")
//                && Methods.verifyMaxWords(qmodel.getDescription_question(), 200, " ")) {
        String resp[] = qdao.updateQuestions(qmodel);

        if (resp[0].equals("2")) {
            status = "2";
            message = "Data returned correctly.";
            data = resp[1];

        } else {
            status = "4";
            message = "An error has occurred.";
        }
//
//        } else {
//            status = "3";
//            message = "Check input parameters.";
//        }

        return new String[]{status, message, data};
    }

    public String[] selectQuestions(
            String type_questions,
            String question_group_id_questiongroup,
            String evaluation_categories_id_categorie,
            String state_question,
            String person_id_person
    ) {
        String status = "4", message = "Error in the entered parameters", data = "[]";
        qmodel = new Questions();
        codec = new WeEncoder();
        String flag = "-1";
        if (type_questions.equals("ALL")) {
            flag = "0";//
        }
        if (type_questions.equals("QUESTIONS BY CATEGORY")) {
            flag = "1";//
        }
        if (type_questions.equals("QUESTIONS BY GROUP")) {
            flag = "2";//
        }
        if (type_questions.equals("QUESTIONS BY GROUP AND CATEGORIES")) {
            flag = "3";//Sacar preguntas de cierta categoria en un grupo
        }

        if (flag.matches("[0123]")) {
            question_group_id_questiongroup = question_group_id_questiongroup.equals("0") ? question_group_id_questiongroup : codec.textDecryptor(question_group_id_questiongroup);
            evaluation_categories_id_categorie = evaluation_categories_id_categorie.equals("0") ? evaluation_categories_id_categorie : codec.textDecryptor(evaluation_categories_id_categorie);
            
            qmodel.setId_person(person_id_person);
            qmodel.setQuestion_group_id_questiongroup(question_group_id_questiongroup);
            qmodel.setEvaluation_categories_id_categorie(evaluation_categories_id_categorie);
            qmodel.setState_question(state_question);

            String resp[] = qdao.selectQuestions(flag, qmodel);

            if (resp[0].equals("2")) {

                JsonArray jarr = Methods.stringToJsonArray(resp[1]);

                for (int index = 0; index < jarr.size(); index++) {
                    String id_questions = jarr.get(index).getAsJsonObject().get("id_questions").getAsString();
                    id_questions = codec.textEncryptor(id_questions);
                    jarr.get(index).getAsJsonObject().addProperty("id_questions", id_questions);

//                    String id_questions = jarr.get(index).getAsJsonObject().get("id_questions").getAsString();
//                    id_questions = codec.textEncryptor(id_questions);
//                    jarr.get(index).getAsJsonObject().addProperty("id_questions", id_questions);
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
    
    public String[] viewQuestion (String idQuestion) {
        String status = "4", message = "Error in the entered parameters", data = "[]";
        qmodel = new Questions();
        codec = new WeEncoder();
        if(!idQuestion.equals("")){
            idQuestion = codec.textDecryptor(idQuestion);
            System.out.println("ID QUESTION: " + idQuestion);
            qmodel.setId_questions(idQuestion);
            data = qdao.viewQuestion(qmodel);
            System.out.println("que pex"+data);
            if(!data.equals("[]")){
                status = "2";
                message = "Question loads successfully";
            }
        }
        return new String[]{status, message, data};
    }

}
