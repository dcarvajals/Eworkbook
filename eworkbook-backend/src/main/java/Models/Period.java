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
public class Period {

    String id_period = "";
    String name_period = "";
    String date_registration_period = "";
    String state_period = "";
    String person_id_person = "";
    String start_date_period = "";
    String end_date_period = "";
    String description_period = "";
    String specialty_period = "";

    public Period() {
    }

    public String getId_period() {
        return id_period;
    }

    public void setId_period(String id_period) {
        this.id_period = id_period;
    }

    public String getName_period() {
        return name_period;
    }

    public void setName_period(String name_period) {
        this.name_period = name_period;
    }

    public String getDate_registration_period() {
        return date_registration_period;
    }

    public void setDate_registration_period(String date_registration_period) {
        this.date_registration_period = date_registration_period;
    }

    public String getState_period() {
        return state_period;
    }

    public void setState_period(String state_period) {
        this.state_period = state_period;
    }

    public String getPerson_id_person() {
        return person_id_person;
    }

    public void setPerson_id_person(String person_id_person) {
        this.person_id_person = person_id_person;
    }

    public String getStart_date_period() {
        return start_date_period;
    }

    public void setStart_date_period(String start_date_period) {
        this.start_date_period = start_date_period;
    }

    public String getEnd_date_period() {
        return end_date_period;
    }

    public void setEnd_date_period(String end_date_period) {
        this.end_date_period = end_date_period;
    }

    public String getDescription_period() {
        return description_period;
    }

    public void setDescription_period(String description_period) {
        this.description_period = description_period;
    }

    public String getSpecialty_period() {
        return specialty_period;
    }

    public void setSpecialty_period(String specialty_period) {
        this.specialty_period = specialty_period;
    }

    public String returnXml() {
        JSONObject jsonU = new JSONObject(this);//getClass().getName()
        return "<period>" + XML.toString(jsonU) + "</period>";
    }
}
