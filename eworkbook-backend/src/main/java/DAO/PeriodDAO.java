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
public class PeriodDAO {

    private Conection conex;

    public String[] insertPeriod(Period prd) {
        String query = String.format("select * from period_insert('%s')", prd.returnXml());
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

    public String[] selectAllPeriod(String type, String person) {
        String query = String.format("select * from period_select(%s, %s)", type, person);
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

    public String[] updatePeriod(Period prd) {
        String query = String.format("select * from period_update('%s')", prd.returnXml());
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
