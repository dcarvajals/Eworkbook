/**
 * Esta clase esta destinada a la realización del acceso a datos de la Entidad
 * Persona, permitiendo procesos de registro, modificación, logeo, entre otras
 */
package DAO;

import Models.*;
import com.google.gson.JsonObject;
import java.util.Date;
import javax.swing.table.DefaultTableModel;
import Util.Conection;
import Util.DataStatic;
import Util.Methods;

/**
 *
 * @author geova
 */
public class QuestionsDAO {

    private Conection conex;

    public String[] insertQuestions(Questions qts) {
        String query = String.format("select * from questions_insert('%s')", qts.returnXml());
        System.out.println(query);
        conex = new Conection();
        DefaultTableModel tab = conex.returnRecord(query);
        if (tab.getRowCount() > 0) {
            return new String[]{
                tab.getValueAt(0, 0).toString(),
                tab.getValueAt(0, 1).toString()};
        } else {
            return new String[]{"4", "[]"};
        }
    }

    public String[] updateQuestions(Questions qts) {
        String query = String.format("select * from questions_update('%s')", qts.returnXml());
        System.out.println(query);
        conex = new Conection();
        DefaultTableModel tab = conex.returnRecord(query);
        if (tab.getRowCount() > 0) {
            return new String[]{
                tab.getValueAt(0, 0).toString(),
                tab.getValueAt(0, 1).toString()};
        } else {
            return new String[]{"4", "[]"};
        }
    }

    public String[] selectQuestions(String id_type, Questions qts) {
        String query = String.format("select * from questions_select(%s, %s, %s, '%s', %s)",
                id_type, qts.getQuestion_group_id_questiongroup(), qts.getEvaluation_categories_id_categorie(), qts.getState_question(), qts.getId_person());
        System.out.println(query);
        conex = new Conection();
        DefaultTableModel tab = conex.returnRecord(query);
        if (tab.getRowCount() > 0) {
            return new String[]{
                tab.getValueAt(0, 0).toString(),
                tab.getValueAt(0, 1).toString()};
        } else {
            return new String[]{"4", "[]"};
        }
    }
    
    public String viewQuestion(Questions qts){
        conex = new Conection();
        return conex.getRecordsInJson("select * from questions where id_questions = " + qts.getId_questions());
    }

}
