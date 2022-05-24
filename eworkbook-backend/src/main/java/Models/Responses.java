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
public class Responses {

    String id_response = "";
    String state_response = "";
    String date_register_response = "";
    String answer_options = "";
    String questions_id_questions = "";

    public Responses() {
    }

    public String getId_response() {
        return id_response;
    }

    public void setId_response(String id_response) {
        this.id_response = id_response;
    }

    public String getState_response() {
        return state_response;
    }

    public void setState_response(String state_response) {
        this.state_response = state_response;
    }

    public String getDate_register_response() {
        return date_register_response;
    }

    public void setDate_register_response(String date_register_response) {
        this.date_register_response = date_register_response;
    }

    public String getAnswer_options() {
        return answer_options;
    }

    public void setAnswer_options(String answer_options) {
        this.answer_options = answer_options;
    }

    public String getQuestions_id_questions() {
        return questions_id_questions;
    }

    public void setQuestions_id_questions(String questions_id_questions) {
        this.questions_id_questions = questions_id_questions;
    }

    public String returnXml() {
        JSONObject jsonU = new JSONObject(this);//getClass().getName()
        return "<responses>" + XML.toString(jsonU) + "</responses>";
    }
}
