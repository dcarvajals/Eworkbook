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
public class Classroom {

    String id_classroom = "";
    String code_class = "";
    String date_registration_class = "";
    String class_description = "";
    String class_section = "";
    String name_class = "";
    String classroom_number = "";
    String class_state = "";
    String person_id_person = "";
    String period_id_period = "";

    public Classroom() {
    }

    public String getId_classroom() {
        return id_classroom;
    }

    public void setId_classroom(String id_classroom) {
        this.id_classroom = id_classroom;
    }

    public String getCode_class() {
        return code_class;
    }

    public void setCode_class(String code_class) {
        this.code_class = code_class;
    }

    public String getDate_registration_class() {
        return date_registration_class;
    }

    public void setDate_registration_class(String date_registration_class) {
        this.date_registration_class = date_registration_class;
    }

    public String getClass_description() {
        return class_description;
    }

    public void setClass_description(String class_description) {
        this.class_description = class_description;
    }

    public String getClass_section() {
        return class_section;
    }

    public void setClass_section(String class_section) {
        this.class_section = class_section;
    }

    public String getName_class() {
        return name_class;
    }

    public void setName_class(String name_class) {
        this.name_class = name_class;
    }

    public String getClassroom_number() {
        return classroom_number;
    }

    public void setClassroom_number(String classroom_number) {
        this.classroom_number = classroom_number;
    }

    public String getClass_state() {
        return class_state;
    }

    public void setClass_state(String class_state) {
        this.class_state = class_state;
    }

    public String getPerson_id_person() {
        return person_id_person;
    }

    public void setPerson_id_person(String person_id_person) {
        this.person_id_person = person_id_person;
    }

    public String getPeriod_id_period() {
        return period_id_period;
    }

    public void setPeriod_id_period(String period_id_period) {
        this.period_id_period = period_id_period;
    }

    public String returnXml() {
        JSONObject jsonU = new JSONObject(this);//getClass().getName()
        return "<classroom>" + XML.toString(jsonU) + "</classroom>";
    }

}
