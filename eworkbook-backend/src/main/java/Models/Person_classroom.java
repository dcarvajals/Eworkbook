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
public class Person_classroom {

    String id_person_classroom = "";
    String person_id_person = "";
    String classroom_id_classroom = "";
    String state_person_classroom = "";
    String state_student_classroom = "";
    String date_register_person_classroom = "";
    //EXTRA
    String code_class = "";

    public Person_classroom() {
    }

    public String getId_person_classroom() {
        return id_person_classroom;
    }

    public void setId_person_classroom(String id_person_classroom) {
        this.id_person_classroom = id_person_classroom;
    }

    public String getPerson_id_person() {
        return person_id_person;
    }

    public void setPerson_id_person(String person_id_person) {
        this.person_id_person = person_id_person;
    }

    public String getClassroom_id_classroom() {
        return classroom_id_classroom;
    }

    public void setClassroom_id_classroom(String classroom_id_classroom) {
        this.classroom_id_classroom = classroom_id_classroom;
    }

    public String getState_person_classroom() {
        return state_person_classroom;
    }

    public void setState_person_classroom(String state_person_classroom) {
        this.state_person_classroom = state_person_classroom;
    }

    public String getCode_class() {
        return code_class;
    }

    public void setCode_class(String code_class) {
        this.code_class = code_class;
    }

    public String getState_student_classroom() {
        return state_student_classroom;
    }

    public void setState_student_classroom(String state_student_classroom) {
        this.state_student_classroom = state_student_classroom;
    }

    public String getDate_register_person_classroom() {
        return date_register_person_classroom;
    }

    public void setDate_register_person_classroom(String date_register_person_classroom) {
        this.date_register_person_classroom = date_register_person_classroom;
    }

    public String returnXml() {
        JSONObject jsonU = new JSONObject(this);//getClass().getName()
        return "<person_classroom>" + XML.toString(jsonU) + "</person_classroom>";
    }
}
