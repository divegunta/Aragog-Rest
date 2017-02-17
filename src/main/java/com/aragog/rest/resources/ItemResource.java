package com.aragog.rest.resources;

import javax.ws.rs.Consumes;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

/**
 * REST resource for Aragog Item.
 */
@Path("{authority}/items")
public interface ItemResource {

    /**
     *  Retrieves a list of items given the query filters.
     * @param _uriInfo
     * @param itemTitle Title to be used to filter the query.
     * @param _authority the tenant's domain that will be used to satisfy this query request
     * @return a list of items 
     */
    @Path("get")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getItem(@Context final UriInfo _uriInfo, final String itemTitle,
            @PathParam(value = "authority") final String _authority);

    /**
     *  Retrieves a list of items given the query filters.
     * @param _uriInfo
     * @param _authority the tenant's domain that will be used to satisfy this query request
     * @return Response of the call 
     */
    @Path("post")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response postItem(@Context final UriInfo _uriInfo,
            @PathParam(value = "authority") final String _authority);

}
