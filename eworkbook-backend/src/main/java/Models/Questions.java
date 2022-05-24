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
public class Questions {

    String id_questions = "";
    String name_question = "";
    String description_question = "";
    String state_question = "";
    String date_registration_question = "";
    String date_update_question = "";
    String required_question = "";
    String file_question = "";
    String question_group_id_questiongroup = "";
    String evaluation_categories_id_categorie = "";
    String id_person = "";

    public Questions() {
    }

    public String getId_person() {
        return id_person;
    }

    public void setId_person(String id_person) {
        this.id_person = id_person;
    }
    
    

    public String getId_questions() {
        return id_questions;
    }

    public void setId_questions(String id_questions) {
        this.id_questions = id_questions;
    }

    public String getName_question() {
        return name_question;
    }

    public void setName_question(String name_question) {
        this.name_question = name_question;
    }

    public String getDescription_question() {
        return description_question;
    }

    public void setDescription_question(String description_question) {
        this.description_question = description_question;
    }

    public String getState_question() {
        return state_question;
    }

    public void setState_question(String state_question) {
        this.state_question = state_question;
    }

    public String getDate_registration_question() {
        return date_registration_question;
    }

    public void setDate_registration_question(String date_registration_question) {
        this.date_registration_question = date_registration_question;
    }

    public String getRequired_question() {
        return required_question;
    }

    public void setRequired_question(String required_question) {
        this.required_question = required_question;
    }

    public String getFile_question() {
        return file_question;
    }

    public void setFile_question(String file_question) {
        this.file_question = file_question;
    }

    public String getQuestion_group_id_questiongroup() {
        return question_group_id_questiongroup;
    }

    public void setQuestion_group_id_questiongroup(String question_group_id_questiongroup) {
        this.question_group_id_questiongroup = question_group_id_questiongroup;
    }

    public String getEvaluation_categories_id_categorie() {
        return evaluation_categories_id_categorie;
    }

    public void setEvaluation_categories_id_categorie(String evaluation_categories_id_categorie) {
        this.evaluation_categories_id_categorie = evaluation_categories_id_categorie;
    }

    public String getDate_update_question() {
        return date_update_question;
    }

    public void setDate_update_question(String date_update_question) {
        this.date_update_question = date_update_question;
    }

    public String returnXml() {
        JSONObject jsonU = new JSONObject(this);//getClass().getName()
        return "<questions>" + XML.toString(jsonU) + "</questions>";
    }
}
