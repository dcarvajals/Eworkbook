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
public class EvaluationDAO {

    private Conection conex;

    public String[] insertEvaluation(Evaluation eva) {
        String query = String.format("select * from evaluation_insert('%s')", eva.returnXml());
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

    public String[] createEvaluation(Evaluation eva, String evaluation_questions) {
        String total_xml = String.format("<evaluation>%s<questions>%s</questions></evaluation>", eva.returnXml(), evaluation_questions);
        String query = String.format("select * from evaluation_insert('%s')", total_xml);
        System.out.println("Super XML");
        System.out.println(total_xml);
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

}
