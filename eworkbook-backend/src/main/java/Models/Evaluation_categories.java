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
public class Evaluation_categories {

    String id_categorie = "";
    String name_categorie = "";
    String name_spanish_categorie = "";
    String description_categorie = "";
    String registration_date_category = "";
    String state_categorie = "";

    public Evaluation_categories() {
    }

    public String getId_categorie() {
        return id_categorie;
    }

    public void setId_categorie(String id_categorie) {
        this.id_categorie = id_categorie;
    }

    public String getName_categorie() {
        return name_categorie;
    }

    public void setName_categorie(String name_categorie) {
        this.name_categorie = name_categorie;
    }

    public String getState_categorie() {
        return state_categorie;
    }

    public void setState_categorie(String state_categorie) {
        this.state_categorie = state_categorie;
    }

    public String getDescription_categorie() {
        return description_categorie;
    }

    public void setDescription_categorie(String description_categorie) {
        this.description_categorie = description_categorie;
    }

    public String getName_spanish_categorie() {
        return name_spanish_categorie;
    }

    public void setName_spanish_categorie(String name_spanish_categorie) {
        this.name_spanish_categorie = name_spanish_categorie;
    }

    public String getRegistration_date_category() {
        return registration_date_category;
    }

    public void setRegistration_date_category(String registration_date_category) {
        this.registration_date_category = registration_date_category;
    }

    public String returnXml() {
        JSONObject jsonU = new JSONObject(this);//getClass().getName()
        return "<evaluation_categories>" + XML.toString(jsonU) + "</evaluation_categories>";
    }
}
