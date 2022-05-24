/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Models;

import org.json.JSONObject;
import org.json.XML;

/**
 *
 * @author geova
 */
public class Evaluation_questions {

    String id_evaluationquestions = "";
    String qualification = "";
    String date_register_evaluation_question = "";
    String comment_answer = "";
    String result_answer = "";
    String evaluation_id_evaluation = "";
    String questions_id_questions = "";

    public Evaluation_questions() {
    }

    public String getId_evaluationquestions() {
        return id_evaluationquestions;
    }

    public void setId_evaluationquestions(String id_evaluationquestions) {
        this.id_evaluationquestions = id_evaluationquestions;
    }

    public String getQualification() {
        return qualification;
    }

    public void setQualification(String qualification) {
        this.qualification = qualification;
    }

    public String getDate_register_evaluation_question() {
        return date_register_evaluation_question;
    }

    public void setDate_register_evaluation_question(String date_register_evaluation_question) {
        this.date_register_evaluation_question = date_register_evaluation_question;
    }

    public String getComment_answer() {
        return comment_answer;
    }

    public void setComment_answer(String comment_answer) {
        this.comment_answer = comment_answer;
    }

    public String getResult_answer() {
        return result_answer;
    }

    public void setResult_answer(String result_answer) {
        this.result_answer = result_answer;
    }

    public String getEvaluation_id_evaluation() {
        return evaluation_id_evaluation;
    }

    public void setEvaluation_id_evaluation(String evaluation_id_evaluation) {
        this.evaluation_id_evaluation = evaluation_id_evaluation;
    }

    public String getQuestions_id_questions() {
        return questions_id_questions;
    }

    public void setQuestions_id_questions(String questions_id_questions) {
        this.questions_id_questions = questions_id_questions;
    }

    public String returnXml() {
        JSONObject jsonU = new JSONObject(this);//getClass().getName()
        return "<evaluation_questions>" + XML.toString(jsonU) + "</evaluation_questions>";
    }

}
