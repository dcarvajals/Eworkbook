/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Apis;

import Controller.*;
import Util.DataStatic;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Produces;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PUT;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import Util.Methods;

/**
 * REST Web Service
 *
 * @author geova
 */
@Path("classroomapis")
public class ClassroomApis {

    @Context
    private UriInfo context;
    @Context
    private HttpServletRequest request;

    ClassroomCtrl cControl;

    /**
     * Creates a new instance
     */
    public ClassroomApis() {
        cControl = new ClassroomCtrl();
    }

    @POST
    @Path("/insertclassroom")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response insertClassroom(String data) {
        String message = "";
        System.out.println("insert_classroomapis...");
        JsonObject jso = Methods.stringToJSON(data);
        if (jso.size() > 0) {
            String user_token = Methods.JsonToString(jso, "user_token", "");
//            String code_class = Methods.JsonToString(jso, "code_class", "");
            String class_description = Methods.JsonToString(jso, "class_description", "");
            // String class_section = Methods.JsonToString(jso, "class_section", "");
            String name_class = Methods.JsonToString(jso, "name_class", "");
            String classroom_number = Methods.JsonToString(jso, "classroom_number", "");
//            String class_state = Methods.JsonToString(jso, "class_state", "");
            // String period_id_period = Methods.JsonToString(jso, "period_id_period", "");

            String[] clains = Methods.getDataToJwt(user_token);
            String[] res = Methods.validatePermit(clains[0], clains[1], 1);
            if (res[0].equals("2")) {
                res = cControl.insertCassroom("", class_description,
                        name_class, classroom_number, clains[0]);
                message = Methods.getJsonMessage(res[0], res[1], res[2]);
            } else {
                message = Methods.getJsonMessage("4", "Error in the request parameters.", "[]");
            }
        } else {
            message = Methods.getJsonMessage("4", "Missing data.", "{}");
        }
        return Response.ok(message)
                .header("Access-Control-Allow-Origin", "*")
                .header("Access-Control-Allow-Methods", "POST, GET, PUT, UPDATE, OPTIONS")
                .header("Access-Control-Allow-Headers", "Content-Type, Accept, X-Requested-with")
                .build();
    }

    @POST
    @Path("/selectclassroom")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response selectClassroom(String data) {
        String message = "";
        System.out.println("select_classroomapis...");
        JsonObject jso = Methods.stringToJSON(data);
        if (jso.size() > 0) {
            String user_token = Methods.JsonToString(jso, "user_token", "");
            String type_classroom = Methods.JsonToString(jso, "type_classroom", "");//1 all 2 period
            String type_period = Methods.JsonToString(jso, "type_period", "");

            String[] clains = Methods.getDataToJwt(user_token);
            String[] res = Methods.validatePermit(clains[0], clains[1], 1);
            if (res[0].equals("2")) {
                res = cControl.selectAllClassroom(type_classroom, type_period, clains[0]);
                message = Methods.getJsonMessage(res[0], res[1], res[2]);
            } else {
                message = Methods.getJsonMessage("4", "Error in the request parameters.", "[]");
            }
        } else {
            message = Methods.getJsonMessage("4", "Missing data.", "{}");
        }
        return Response.ok(message)
                .header("Access-Control-Allow-Origin", "*")
                .header("Access-Control-Allow-Methods", "POST, GET, PUT, UPDATE, OPTIONS")
                .header("Access-Control-Allow-Headers", "Content-Type, Accept, X-Requested-with")
                .build();
    }

    @POST
    @Path("/joinclassroom")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response joinClassroom(String data) {
        String message = "";
        System.out.println("join_classroomapis...");
        JsonObject jso = Methods.stringToJSON(data);
        if (jso.size() > 0) {
            String user_token = Methods.JsonToString(jso, "user_token", "");
            String code_class = Methods.JsonToString(jso, "code_class", "");

            String[] clains = Methods.getDataToJwt(user_token);
            String[] res = Methods.validatePermit(clains[0], clains[1], 1);
            if (res[0].equals("2")) {
                res = cControl.joinCassroom(clains[0], code_class);
                message = Methods.getJsonMessage(res[0], res[1], res[2]);
            } else {
                message = Methods.getJsonMessage("4", "Error in the request parameters.", "[]");
            }
        } else {
            message = Methods.getJsonMessage("4", "Missing data.", "{}");
        }
        return Response.ok(message)
                .header("Access-Control-Allow-Origin", "*")
                .header("Access-Control-Allow-Methods", "POST, GET, PUT, UPDATE, OPTIONS")
                .header("Access-Control-Allow-Headers", "Content-Type, Accept, X-Requested-with")
                .build();
    }

    @POST
    @Path("/myclassroom")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response myClassroom(String data) {
        String message = "";
        System.out.println("my_classroomapis...");
        JsonObject jso = Methods.stringToJSON(data);
        if (jso.size() > 0) {
            String user_token = Methods.JsonToString(jso, "user_token", "");
            String type_class = Methods.JsonToString(jso, "type_class", "");// all -  period
            String type_period = Methods.JsonToString(jso, "type_period", "");
            String state_class = Methods.JsonToString(jso, "state_class", "");

            String[] clains = Methods.getDataToJwt(user_token);
            String[] res = Methods.validatePermit(clains[0], clains[1], 1);
            if (res[0].equals("2")) {
                res = cControl.myClassroom(type_class, clains[0], type_period, state_class);
                message = Methods.getJsonMessage(res[0], res[1], res[2]);
            } else {
                message = Methods.getJsonMessage("4", "Error in the request parameters.", "[]");
            }
        } else {
            message = Methods.getJsonMessage("4", "Missing data.", "{}");
        }
        return Response.ok(message)
                .header("Access-Control-Allow-Origin", "*")
                .header("Access-Control-Allow-Methods", "POST, GET, PUT, UPDATE, OPTIONS")
                .header("Access-Control-Allow-Headers", "Content-Type, Accept, X-Requested-with")
                .build();
    }

    @POST
    @Path("/studentclassroom")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response studentClassroom(String data) {
        String message = "";
        System.out.println("student_classroomapis...");
        JsonObject jso = Methods.stringToJSON(data);
        if (jso.size() > 0) {
            String user_token = Methods.JsonToString(jso, "user_token", "");
            String type_class = Methods.JsonToString(jso, "type_class", "");
            String id_classroom = Methods.JsonToString(jso, "id_classroom", "");

            String[] clains = Methods.getDataToJwt(user_token);
            String[] res = Methods.validatePermit(clains[0], clains[1], 1);
            if (res[0].equals("2")) {
                res = cControl.studentClassroom(type_class, id_classroom, "A");
                message = Methods.getJsonMessage(res[0], res[1], res[2]);
            } else {
                message = Methods.getJsonMessage("4", "Error in the request parameters.", "[]");
            }
        } else {
            message = Methods.getJsonMessage("4", "Missing data.", "{}");
        }
        return Response.ok(message)
                .header("Access-Control-Allow-Origin", "*")
                .header("Access-Control-Allow-Methods", "POST, GET, PUT, UPDATE, OPTIONS")
                .header("Access-Control-Allow-Headers", "Content-Type, Accept, X-Requested-with")
                .build();
    }

    @POST
    @Path("/updateclassroom")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateClassroom(String data) {
        String message = "";
        System.out.println("update_classroomapis...");
        JsonObject jso = Methods.stringToJSON(data);
        if (jso.size() > 0) {
            String user_token = Methods.JsonToString(jso, "user_token", "");
            String id_classroom = Methods.JsonToString(jso, "id_classroom", "");
//            String code_class = Methods.JsonToString(jso, "code_class", "");
            String class_description = Methods.JsonToString(jso, "class_description", "");
            String class_section = Methods.JsonToString(jso, "class_section", "");
            String name_class = Methods.JsonToString(jso, "name_class", "");
            String classroom_number = Methods.JsonToString(jso, "classroom_number", "");
//            String class_state = Methods.JsonToString(jso, "class_state", "");
            String period_id_period = Methods.JsonToString(jso, "period_id_period", "");

            String[] clains = Methods.getDataToJwt(user_token);
            String[] res = Methods.validatePermit(clains[0], clains[1], 1);
            if (res[0].equals("2")) {
                res = cControl.updateCassroom(id_classroom, "", class_description, class_section,
                        name_class, classroom_number, period_id_period, clains[0]);
                message = Methods.getJsonMessage(res[0], res[1], res[2]);
            } else {
                message = Methods.getJsonMessage("4", "Error in the request parameters.", "[]");
            }
        } else {
            message = Methods.getJsonMessage("4", "Missing data.", "{}");
        }
        return Response.ok(message)
                .header("Access-Control-Allow-Origin", "*")
                .header("Access-Control-Allow-Methods", "POST, GET, PUT, UPDATE, OPTIONS")
                .header("Access-Control-Allow-Headers", "Content-Type, Accept, X-Requested-with")
                .build();
    }

}
