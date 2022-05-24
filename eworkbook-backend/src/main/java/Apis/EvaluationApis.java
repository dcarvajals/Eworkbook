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
import com.google.gson.JsonArray;

/**
 * REST Web Service
 *
 * @author geova
 */
@Path("evaluationapis")
public class EvaluationApis {

    @Context
    private UriInfo context;
    @Context
    private HttpServletRequest request;

    EvaluationCtrl eControl;

    /**
     * Creates a new instance
     */
    public EvaluationApis() {
        eControl = new EvaluationCtrl();
    }

    @POST
    @Path("/insertevaluation")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response insertEvaluation(String data) {
        String message = "";
        System.out.println("insert_evaluationapis...");
        JsonObject jso = Methods.stringToJSON(data);
        if (jso.size() > 0) {
            String user_token = Methods.JsonToString(jso, "user_token", "");

            String name_evaluation = Methods.JsonToString(jso, "name_evaluation", "");
            String description_evaluation = Methods.JsonToString(jso, "description_evaluation", "");
            String state_evaluation = Methods.JsonToString(jso, "state_evaluation", "A");
            String date_init_evaluation = Methods.JsonToString(jso, "date_init_evaluation", "");
            String date_finish_evaluation = Methods.JsonToString(jso, "date_finish_evaluation", "");
            String file_route_evaluation = Methods.JsonToString(jso, "file_route_evaluation", "");
            String external_resource = Methods.JsonToString(jso, "external_resource", "");
            String classroom_id_classroom = Methods.JsonToString(jso, "classroom_id_classroom", "-1");

            String[] clains = Methods.getDataToJwt(user_token);
            String[] res = Methods.validatePermit(clains[0], clains[1], 1);
            if (res[0].equals("2")) {
                res = eControl.insetEvaluation(name_evaluation, description_evaluation, state_evaluation, date_init_evaluation, date_finish_evaluation, file_route_evaluation, external_resource, classroom_id_classroom);
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
    @Path("/createevaluation")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createEvaluation(String data) {
        String message = "";
        System.out.println("Evaluation...");
        JsonObject jso = Methods.stringToJSON(data);
        if (jso.size() > 0) {
            String user_token = Methods.JsonToString(jso, "user_token", "");

            String name_evaluation = Methods.JsonToString(jso, "name_evaluation", "");
            String description_evaluation = Methods.JsonToString(jso, "description_evaluation", "");
            String state_evaluation = Methods.JsonToString(jso, "state_evaluation", "A");
            String date_init_evaluation = Methods.JsonToString(jso, "date_init_evaluation", "");
            String date_finish_evaluation = Methods.JsonToString(jso, "date_finish_evaluation", "");
            String file_route_evaluation = Methods.JsonToString(jso, "file_route_evaluation", "");
            String external_resource = Methods.JsonToString(jso, "external_resource", "");
            String classroom_id_classroom = Methods.JsonToString(jso, "classroom_id_classroom", "-1");
            
            JsonArray evaluation_questions = Methods.JsonToArray(jso, "evaluation_questions_list");

            String[] clains = Methods.getDataToJwt(user_token);
            String[] res = Methods.validatePermit(clains[0], clains[1], 1);
            if (res[0].equals("2")) {
                res = eControl.createEvaluation(name_evaluation, description_evaluation, state_evaluation, date_init_evaluation, date_finish_evaluation, file_route_evaluation, external_resource, classroom_id_classroom, evaluation_questions);
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
