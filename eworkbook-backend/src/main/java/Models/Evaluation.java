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
public class Evaluation {

    String id_evaluation = "";
    String name_evaluation = "";
    String description_evaluation = "";
    String state_evaluation = "";
    String date_registration_evaluation = "";
    String date_init_evaluation = "";
    String date_finish_evaluation = "";
    String file_route_evaluation = "";
    String external_resource = "";
    String classroom_id_classroom = "";

    public Evaluation() {
    }

    public String getId_evaluation() {
        return id_evaluation;
    }

    public void setId_evaluation(String id_evaluation) {
        this.id_evaluation = id_evaluation;
    }

    public String getName_evaluation() {
        return name_evaluation;
    }

    public void setName_evaluation(String name_evaluation) {
        this.name_evaluation = name_evaluation;
    }

    public String getDescription_evaluation() {
        return description_evaluation;
    }

    public void setDescription_evaluation(String description_evaluation) {
        this.description_evaluation = description_evaluation;
    }

    public String getState_evaluation() {
        return state_evaluation;
    }

    public void setState_evaluation(String state_evaluation) {
        this.state_evaluation = state_evaluation;
    }

    public String getDate_registration_evaluation() {
        return date_registration_evaluation;
    }

    public void setDate_registration_evaluation(String date_registration_evaluation) {
        this.date_registration_evaluation = date_registration_evaluation;
    }

    public String getDate_init_evaluation() {
        return date_init_evaluation;
    }

    public void setDate_init_evaluation(String date_init_evaluation) {
        this.date_init_evaluation = date_init_evaluation;
    }

    public String getDate_finish_evaluation() {
        return date_finish_evaluation;
    }

    public void setDate_finish_evaluation(String date_finish_evaluation) {
        this.date_finish_evaluation = date_finish_evaluation;
    }

    public String getFile_route_evaluation() {
        return file_route_evaluation;
    }

    public void setFile_route_evaluation(String file_route_evaluation) {
        this.file_route_evaluation = file_route_evaluation;
    }

    public String getExternal_resource() {
        return external_resource;
    }

    public void setExternal_resource(String external_resource) {
        this.external_resource = external_resource;
    }

    public String getClassroom_id_classroom() {
        return classroom_id_classroom;
    }

    public void setClassroom_id_classroom(String classroom_id_classroom) {
        this.classroom_id_classroom = classroom_id_classroom;
    }

    public String returnXml() {
        JSONObject jsonU = new JSONObject(this);//getClass().getName()
        return "<evaluation>" + XML.toString(jsonU) + "</evaluation>";
    }
}
