/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import DAO.*;
import Models.*;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import javax.swing.table.DefaultTableModel;
import Util.DataStatic;
import Util.FileAccess;
import Util.Methods;
import Util.TemplateEmail;
import Util.WeEncoder;

/**
 *
 * @author CleanCode
 */
public class ResponsesCtrl {

    /**
     * Instancia del acceso a datos de persona.
     */
    ResponsesDAO rdao;
    /**
     * Instancia de la clase destinada a encriptar y generar el código de
     * verificación
     */
    WeEncoder codec;

    public ResponsesCtrl() {
        rdao = new ResponsesDAO();
    }

}
