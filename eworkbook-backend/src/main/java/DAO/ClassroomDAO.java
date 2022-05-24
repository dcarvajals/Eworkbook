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
public class ClassroomDAO {

    private Conection conex;

    public String[] insertClassroom(Classroom classrm) {
        String query = String.format("select * from classroom_insert('%s')", classrm.returnXml());
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

    public boolean verifyCodeClassroom(String code_classroom) {
        String query = String.format("select * from classroom_code('%s')", code_classroom);
        System.out.println(query);
        conex = new Conection();
        DefaultTableModel tab = conex.returnRecord(query);
        if (tab.getRowCount() > 0) {
            String resultSelect[] = new String[]{
                tab.getValueAt(0, 0).toString(),
                tab.getValueAt(0, 1).toString()};
            return "2".equals(resultSelect[0]);
//            return "2".equals(tab.getValueAt(0, 0).toString());
        } else {
            return false;
        }
    }

    public String[] selectAllClassroom(String type, String period, String person) {
        String query = String.format("select * from classroom_select(%s, %s, %s)", type, period, person);// mis clases
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

    public String[] myClassroom(String type_classroom, String person_id, String type_period, String state_class) {
        String query = String.format("select * from classroom_my_select(%s, %s, %s,'%s')", type_classroom, person_id, type_period, state_class);//clases unidas
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

    public String[] joinClassroom(Person_classroom pn_cm) {
        String query = String.format("select * from person_student_classroon_insert('%s')", pn_cm.returnXml());
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

    public String[] studentClassroom(String type_classroom, String classroom_id, String state_student) {
        String query = String.format("select * from person_student_select(%s, %s, '%s')", type_classroom, classroom_id, state_student);
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

    public String[] updateClassroom(Classroom classrm) {
        String query = String.format("select * from classroom_update('%s')", classrm.returnXml());
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
