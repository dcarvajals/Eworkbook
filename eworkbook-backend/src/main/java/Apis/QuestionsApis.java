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
@Path("questionsapis")
public class QuestionsApis {

    @Context
    private UriInfo context;
    @Context
    private HttpServletRequest request;

    QuestionsCtrl qControl;

    /**
     * Creates a new instance
     */
    public QuestionsApis() {
        qControl = new QuestionsCtrl();
    }

    @POST
    @Path("/insertquestions")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response insertQuestions(String data) {
        String message = "";
        System.out.println("insert_questionsapis...");
        JsonObject jso = Methods.stringToJSON(data);
        if (jso.size() > 0) {
            String user_token = Methods.JsonToString(jso, "user_token", "");
            String name_question = Methods.JsonToString(jso, "name_question", "");
            String description_question = Methods.JsonToString(jso, "description_question", "");
            //String state_question = Methods.JsonToString(jso, "state_questio", "");
            String required_question = Methods.JsonToString(jso, "required_question", "");
            String file_question = Methods.JsonToString(jso, "file_question", "");
            System.out.println("JSON PRO: " + file_question);
            String question_group_id_questiongroup = Methods.JsonToString(jso, "question_group_id_questiongroup", "");
            String evaluation_categories_id_categorie = Methods.JsonToString(jso, "evaluation_categories_id_categorie", "");

            String[] clains = Methods.getDataToJwt(user_token);
            String[] res = Methods.validatePermit(clains[0], clains[1], 1);
            if (res[0].equals("2")) {
                res = qControl.insertQuestions(name_question, description_question, required_question, file_question, question_group_id_questiongroup, evaluation_categories_id_categorie, clains[0]);
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
    @Path("/updatequestions")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateQuestions(String data) {
        String message = "";
        System.out.println("update_questionsapis...");
        JsonObject jso = Methods.stringToJSON(data);
        if (jso.size() > 0) {
            String user_token = Methods.JsonToString(jso, "user_token", "");
            String id_questions = Methods.JsonToString(jso, "id_questions", "");
            String name_question = Methods.JsonToString(jso, "name_question", "");
            String description_question = Methods.JsonToString(jso, "description_question", "");
            //String state_question = Methods.JsonToString(jso, "state_questio", "");
            String required_question = Methods.JsonToString(jso, "required_question", "");
            String file_question = Methods.JsonToString(jso, "file_question", "");
            String question_group_id_questiongroup = Methods.JsonToString(jso, "question_group_id_questiongroup", "");
            String evaluation_categories_id_categorie = Methods.JsonToString(jso, "evaluation_categories_id_categorie", "");

            String[] clains = Methods.getDataToJwt(user_token);
            String[] res = Methods.validatePermit(clains[0], clains[1], 1);
            if (res[0].equals("2")) {
                res = qControl.updateQuestions(id_questions, name_question, description_question, required_question, file_question, question_group_id_questiongroup, evaluation_categories_id_categorie, clains[0]);
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
    @Path("/selectquestions")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response selectQuestions(String data) {
        String message = "";
        System.out.println("select_questionsapis...");
        JsonObject jso = Methods.stringToJSON(data);
        if (jso.size() > 0) {
            String user_token = Methods.JsonToString(jso, "user_token", "");
            String type_group = Methods.JsonToString(jso, "type_group", "");
            String parameter_group = Methods.JsonToString(jso, "parameter_group", "");
            String parameter_categorie = Methods.JsonToString(jso, "parameter_categorie", "");

            String[] clains = Methods.getDataToJwt(user_token);
            System.out.println("IDPERSON" + clains[0]);
            String[] res = Methods.validatePermit(clains[0], clains[1], 1);
            if (res[0].equals("2")) {
                res = qControl.selectQuestions(type_group, parameter_group, parameter_categorie, "A", clains[0]);
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
    @Path("/viewQuestion")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response viewQuestion(String data) {
        String message = "";
        System.out.println("select_viewQuestion...");
        JsonObject jso = Methods.stringToJSON(data);
        if (jso.size() > 0) {
            String user_token = Methods.JsonToString(jso, "user_token", "");
            String id_question = Methods.JsonToString(jso, "id_question", "");

            String[] clains = Methods.getDataToJwt(user_token);
            String[] res = Methods.validatePermit(clains[0], clains[1], 1);
            if (res[0].equals("2")) {
                res = qControl.viewQuestion(id_question);
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
