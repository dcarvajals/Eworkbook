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
public class ClassroomCtrl {

    /**
     * Instancia del acceso a datos de persona.
     */
    ClassroomDAO cdao;

    Classroom cmodel;
    /**
     * Instancia de la clase destinada a encriptar y generar el código de
     * verificación
     */
    WeEncoder codec;

    public ClassroomCtrl() {
        cdao = new ClassroomDAO();
    }

    public String[] insertCassroom(
            String code_class,
            String class_description,
            String name_class,
            String classroom_number,
            //String class_state,
            String person_id_person
    ) {
        String status = "4", message = "Error in the entered parameters", data = "[]";
        cmodel = new Classroom();
        codec = new WeEncoder();

        int amountCode = 6;//Change if you want a longer code

        code_class = codec.getUrlGenericAmount(amountCode);

        while (!cdao.verifyCodeClassroom(code_class)) {
            code_class = codec.getUrlGenericAmount(amountCode);
        }

        cmodel.setCode_class(code_class);
        cmodel.setClass_description(class_description);
        // cmodel.setClass_section(class_section);
        cmodel.setName_class(name_class);
        cmodel.setClassroom_number(classroom_number);
        cmodel.setClass_state("A");
        // cmodel.setPeriod_id_period(codec.textDecryptor(period_id_period));
        cmodel.setPerson_id_person(person_id_person);

        if (Methods.verifyMaxWords(cmodel.getCode_class(), amountCode, " ")
                && Methods.verifyMaxWords(cmodel.getName_class(), 50, " ")
                && Methods.verifyMaxWords(cmodel.getClassroom_number(), 10, " ")) {

            String resp[] = cdao.insertClassroom(cmodel);

            if (resp[0].equals("2")) {
                status = "2";
                message = "Data returned correctly.";
                data = resp[1];

            } else {
                status = "4";
                message = "An error has occurred.";
            }

        } else {
            status = "3";
            message = "Check input parameters.";
        }

        return new String[]{status, message, data};
    }

    public String[] selectAllClassroom(
            String type_period,
            String period_id_period,
            String person_id_person
    ) {
        String status = "4", message = "Error in the entered parameters", data = "[]";
        cmodel = new Classroom();
        codec = new WeEncoder();
        String flag = "-1";
        if (type_period.equals("CLASSROOM ALL")) {
            flag = "1";//Todos las clases de un usuario
        }
        if (type_period.equals("PERIOD FOR CLASSROOM")) {
            flag = "2";//Todos las clases de un usuario y de un periodo
        }

        if (flag.matches("[12]")) {
            period_id_period = period_id_period.equals("0") ? period_id_period : codec.textDecryptor(period_id_period);
            String resp[] = cdao.selectAllClassroom(flag, period_id_period, person_id_person);

            if (resp[0].equals("2")) {

                JsonArray jarr = Methods.stringToJsonArray(resp[1]);

                for (int index = 0; index < jarr.size(); index++) {
                    String id_classroom = jarr.get(index).getAsJsonObject().get("id_classroom").getAsString();
                    id_classroom = codec.textEncryptor(id_classroom);
                    jarr.get(index).getAsJsonObject().addProperty("id_classroom", id_classroom);

//                    String id_period = jarr.get(index).getAsJsonObject().get("id_period").getAsString();
//                    id_period = codec.textEncryptor(id_period);
//                    jarr.get(index).getAsJsonObject().addProperty("id_period", id_period);
                }
                status = "2";
                message = "Data returned correctly.";
                data = jarr.toString();

            } else {
                status = "4";
                message = "An error has occurred.";

            }
        } else {
            status = "3";
            message = "Check the parameters entered.";
        }

        return new String[]{status, message, data};
    }

    public String[] myClassroom(
            String type_classroom,
            String person_id_person,
            String type_period,
            String state_class
    ) {
        String status = "4", message = "Error in the entered parameters", data = "[]";
        codec = new WeEncoder();
        String flag = "-1";

        if (type_classroom.equals("MY CLASS CREATED JOIN")) {
            flag = "1";//todas mis clases unidas
        }
        if (type_classroom.equals("MY CLASS CREATED PERIOD")) {
            flag = "2";//todas mis clases unidas
        }
        if (flag.matches("[12]")) {

            type_period = type_period.equals("0") ? type_period : codec.textDecryptor(type_period);
            String resp[] = cdao.myClassroom(flag, person_id_person, type_period, state_class);

            if (resp[0].equals("2")) {
                JsonArray jarr = Methods.stringToJsonArray(resp[1]);
                for (int index = 0; index < jarr.size(); index++) {
                    String id_classroom = jarr.get(index).getAsJsonObject().get("id_classroom").getAsString();
                    id_classroom = codec.textEncryptor(id_classroom);
                    jarr.get(index).getAsJsonObject().addProperty("id_classroom", id_classroom);

                }
                status = "2";
                message = "Data returned correctly.";
                data = jarr.toString();

            } else {
                status = "4";
                message = "An error has occurred.";
            }
        } else {
            status = "3";
            message = "Check the parameters entered.";
        }
        return new String[]{status, message, data};
    }

    public String[] joinCassroom(
            String person_id_person,
            String code_class
    ) {
        String status = "4", message = "Error in the entered parameters", data = "[]";
        Person_classroom pn_cm = new Person_classroom();

        pn_cm.setPerson_id_person(person_id_person);
        pn_cm.setCode_class(code_class);
        pn_cm.setState_person_classroom("A");
        pn_cm.setState_student_classroom("A");
        String resp[] = cdao.joinClassroom(pn_cm);

        if (resp[0].equals("2")) {
            status = "2";
            message = "Data returned correctly.";
            data = resp[1];

        } else {
            status = "4";
            message = "An error has occurred.";
        }

        return new String[]{status, message, data};
    }

    public String[] studentClassroom(
            String type_classroom,
            String classroom_id,
            String state_student
    ) {
        String status = "4", message = "Error in the entered parameters", data = "[]";
        codec = new WeEncoder();
        classroom_id = codec.textDecryptor(classroom_id);
        String flag = "-1";
        if (type_classroom.equals("LIST STUDENT LASTNAME")) {
            flag = "1";//Ordenado por apellido
        }
        if (type_classroom.equals("LIST STUDENT NAME")) {
            flag = "2";//Ordenado por nombre
        }
        if (type_classroom.equals("LIST STUDENT DATE")) {
            flag = "3";//Ordenado por fecha unido
        }
        if (flag.matches("[123]")) {
            String resp[] = cdao.studentClassroom(flag, classroom_id, state_student);

            if (resp[0].equals("2")) {
                JsonArray jarr = Methods.stringToJsonArray(resp[1]);
                for (int index = 0; index < jarr.size(); index++) {
                    String id_person = jarr.get(index).getAsJsonObject().get("id_person").getAsString();
                    id_person = codec.textEncryptor(id_person);
                    jarr.get(index).getAsJsonObject().addProperty("id_person", id_person);

                }
                status = "2";
                message = "Data returned correctly.";
                data = jarr.toString();

            } else {
                status = "4";
                message = "An error has occurred.";
            }
        } else {
            status = "3";
            message = "Check the parameters entered.";
        }
        return new String[]{status, message, data};
    }

    public String[] updateCassroom(
            String id_classroom,
            String code_class,
            String class_description,
            String class_section,
            String name_class,
            String classroom_number,
            //String class_state,
            String period_id_period,
            String person_id_person
    ) {
        String status = "4", message = "Error in the entered parameters", data = "[]";
        cmodel = new Classroom();
        codec = new WeEncoder();

        cmodel.setId_classroom(codec.textDecryptor(id_classroom));
//        cmodel.setCode_class(code_class);
        cmodel.setClass_description(class_description);
        cmodel.setClass_section(class_section);
        cmodel.setName_class(name_class);
        cmodel.setClassroom_number(classroom_number);
        cmodel.setClass_state("A");
        cmodel.setPeriod_id_period(codec.textDecryptor(period_id_period));
        cmodel.setPerson_id_person(person_id_person);

        if (Methods.verifyMaxWords(cmodel.getClass_section(), 50, " ")
                && Methods.verifyMaxWords(cmodel.getName_class(), 50, " ")
                && Methods.verifyMaxWords(cmodel.getClassroom_number(), 10, " ")) {

            String resp[] = cdao.updateClassroom(cmodel);

            if (resp[0].equals("2")) {
                status = "2";
                message = "Data returned correctly.";
                data = resp[1];

            } else {
                status = "4";
                message = "An error has occurred.";
            }

        } else {
            status = "3";
            message = "Check input parameters.";
        }

        return new String[]{status, message, data};
    }
}
