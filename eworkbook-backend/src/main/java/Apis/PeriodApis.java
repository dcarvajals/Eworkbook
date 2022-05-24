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
@Path("periodapis")
public class PeriodApis {

    @Context
    private UriInfo context;
    @Context
    private HttpServletRequest request;

    PeriodCtrl pControl;

    /**
     * Creates a new instance
     */
    public PeriodApis() {
        pControl = new PeriodCtrl();
    }

    @POST
    @Path("/insertperiod")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response insertClassroom(String data) {
        String message = "";
        System.out.println("insert_periodroomapis...");
        JsonObject jso = Methods.stringToJSON(data);
        if (jso.size() > 0) {
            String user_token = Methods.JsonToString(jso, "user_token", "");
            String name_period = Methods.JsonToString(jso, "name_period", "");
//            String state_period = Methods.JsonToString(jso, "state_period", "");
            String start_date_period = Methods.JsonToString(jso, "start_date_period", "");
            String end_date_period = Methods.JsonToString(jso, "end_date_period", "");
            String description_period = Methods.JsonToString(jso, "description_period", "");
            String specialty_period = Methods.JsonToString(jso, "specialty_period", "");

            String[] clains = Methods.getDataToJwt(user_token);
            String[] res = Methods.validatePermit(clains[0], clains[1], 1);
            if (res[0].equals("2")) {
                res = pControl.insertPeriod(name_period, clains[0], start_date_period, end_date_period,
                        description_period, specialty_period);
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
    @Path("/selectperiod")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response selectClassroom(String data) {
        String message = "";
        System.out.println("select_periodapis...");
        JsonObject jso = Methods.stringToJSON(data);
        if (jso.size() > 0) {
            String user_token = Methods.JsonToString(jso, "user_token", "");
            String type_period = Methods.JsonToString(jso, "type_period", "");//1 all 2 person
            String[] clains = Methods.getDataToJwt(user_token);
            String[] res = Methods.validatePermit(clains[0], clains[1], 1);
            if (res[0].equals("2")) {
                res = pControl.selectAllPeriod(type_period, clains[0]);
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
    @Path("/updateperiod")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateClassroom(String data) {
        String message = "";
        System.out.println("update_periodroomapis...");
        JsonObject jso = Methods.stringToJSON(data);
        if (jso.size() > 0) {
            String user_token = Methods.JsonToString(jso, "user_token", "");
            String id_period = Methods.JsonToString(jso, "id_period", "");
            String name_period = Methods.JsonToString(jso, "name_period", "");
//            String state_period = Methods.JsonToString(jso, "state_period", "");
            String start_date_period = Methods.JsonToString(jso, "start_date_period", "");
            String end_date_period = Methods.JsonToString(jso, "end_date_period", "");
            String description_period = Methods.JsonToString(jso, "description_period", "");
            String specialty_period = Methods.JsonToString(jso, "specialty_period", "");

            String[] clains = Methods.getDataToJwt(user_token);
            String[] res = Methods.validatePermit(clains[0], clains[1], 1);
            if (res[0].equals("2")) {
                res = pControl.updatePeriod(id_period, name_period, clains[0], start_date_period, end_date_period,
                        description_period, specialty_period);
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
