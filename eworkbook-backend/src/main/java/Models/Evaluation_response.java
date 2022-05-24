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
public class Evaluation_response {

    String id_evaluationresponse = "";
    String evaluation_questions_id_evaluationquestions = "";
    String person_id_person = "";
    String qualification = "";
    String date_register_evaluation_response = "";
    String state_response = "";

    public Evaluation_response() {
    }

    public String getId_evaluationresponse() {
        return id_evaluationresponse;
    }

    public void setId_evaluationresponse(String id_evaluationresponse) {
        this.id_evaluationresponse = id_evaluationresponse;
    }

    public String getEvaluation_questions_id_evaluationquestions() {
        return evaluation_questions_id_evaluationquestions;
    }

    public void setEvaluation_questions_id_evaluationquestions(String evaluation_questions_id_evaluationquestions) {
        this.evaluation_questions_id_evaluationquestions = evaluation_questions_id_evaluationquestions;
    }

    public String getPerson_id_person() {
        return person_id_person;
    }

    public void setPerson_id_person(String person_id_person) {
        this.person_id_person = person_id_person;
    }

    public String getQualification() {
        return qualification;
    }

    public void setQualification(String qualification) {
        this.qualification = qualification;
    }

    public String getDate_register_evaluation_response() {
        return date_register_evaluation_response;
    }

    public void setDate_register_evaluation_response(String date_register_evaluation_response) {
        this.date_register_evaluation_response = date_register_evaluation_response;
    }

    public String getState_response() {
        return state_response;
    }

    public void setState_response(String state_response) {
        this.state_response = state_response;
    }

    public String returnXml() {
        JSONObject jsonU = new JSONObject(this);//getClass().getName()
        return "<evaluation_reponse>" + XML.toString(jsonU) + "</evaluation_reponse>";
    }
}
