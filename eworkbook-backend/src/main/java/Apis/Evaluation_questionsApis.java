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
@Path("evaluationquestionsapis")
public class Evaluation_questionsApis {

    @Context
    private UriInfo context;
    @Context
    private HttpServletRequest request;

    Evaluation_questionsCtrl eqControl;

    /**
     * Creates a new instance
     */
    public Evaluation_questionsApis() {
        eqControl = new Evaluation_questionsCtrl();
    }

    @POST
    @Path("/insertevaluationquestions")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response insertEvaluationquestions(String data) {
        String message = "";
        System.out.println("insert_evaluationquestionsapis...");
        JsonObject jso = Methods.stringToJSON(data);
        if (jso.size() > 0) {
            String user_token = Methods.JsonToString(jso, "user_token", "");

            String qualification = Methods.JsonToString(jso, "qualification", "");
            String comment_answer = Methods.JsonToString(jso, "comment_answer", "");
            String result_answer = Methods.JsonToString(jso, "result_answer", "");
            String evaluation_id_evaluation = Methods.JsonToString(jso, "evaluation_id_evaluation", "A");
            String questions_id_questions = Methods.JsonToString(jso, "questions_id_questions", "");

            String[] clains = Methods.getDataToJwt(user_token);
            String[] res = Methods.validatePermit(clains[0], clains[1], 1);
            if (res[0].equals("2")) {
                res = eqControl.insetEvaluation_questions(qualification, comment_answer, result_answer, evaluation_id_evaluation, questions_id_questions, clains[0]);
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
