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
public class Question_group {

    String id_questiongroup = "";
    String group_name = "";
    String group_description = "";
    String date_registration_group = "";
    String state_group = "";
    String person_id_person = "";

    public Question_group() {
    }

    public String getId_questiongroup() {
        return id_questiongroup;
    }

    public void setId_questiongroup(String id_questiongroup) {
        this.id_questiongroup = id_questiongroup;
    }

    public String getGroup_name() {
        return group_name;
    }

    public void setGroup_name(String group_name) {
        this.group_name = group_name;
    }

    public String getGroup_description() {
        return group_description;
    }

    public void setGroup_description(String group_description) {
        this.group_description = group_description;
    }

    public String getDate_registration_group() {
        return date_registration_group;
    }

    public void setDate_registration_group(String date_registration_group) {
        this.date_registration_group = date_registration_group;
    }

    public String getState_group() {
        return state_group;
    }

    public void setState_group(String state_group) {
        this.state_group = state_group;
    }

    public String getPerson_id_person() {
        return person_id_person;
    }

    public void setPerson_id_person(String person_id_person) {
        this.person_id_person = person_id_person;
    }

    public String returnXml() {
        JSONObject jsonU = new JSONObject(this);//getClass().getName()
        return "<question_group>" + XML.toString(jsonU) + "</question_group>";
    }
}
