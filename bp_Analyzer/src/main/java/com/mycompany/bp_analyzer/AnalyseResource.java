/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.bp_analyzer;

import db.DBManager;
import db.DBManagerReal;
import dto.Analyze;
import java.util.List;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Produces;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * REST Web Service
 *
 * @author Dato
 */
@Path("/")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class AnalyseResource {

    @Context
    private UriInfo context;

    private DBManager dbManager;
    
    /**
     * Creates a new instance of AnalyseResource
     */
    public AnalyseResource() {
        dbManager = DBManagerReal.instance;
    }

    /**
     * Retrieves representation of an instance of com.mycompany.bp_analyzer.AnalyseResource
     * @param task_id
     * @return an instance of java.lang.String
     */
    @GET
    @Path("/comments/{task_id}")
    public Response getAllComment(@PathParam("task_id") int task_id) {
        List<Analyze> analyses = dbManager.getAnalysesOn(task_id);
        return Response.status(200).entity(analyses).build();
    }

    /**
     * POST method for updating or creating an instance of AnalyseResource
     * @param analize
     * @return 
     */
    @POST
    @Path("/new_comment")
    public Response saveComment(Analyze analize) {
        dbManager.saveAnalyse(analize);
        return Response.status(200).build();
    }
}
