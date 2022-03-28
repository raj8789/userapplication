package com.resource;

import com.service.UserImpl;
import org.eclipse.jetty.util.StringUtil;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Produces(MediaType.APPLICATION_JSON)
@Path("/api/user")
public class UserControl {
    UserImpl userImpl;
    public UserControl()
    {
        userImpl=new UserImpl();
    }
    @PUT
    @Path("")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response SaveInformation(User user)
    {
        String message=userImpl.save(user);
       if(message.equals("Record inserted in database successfully"))
         return Response.ok(message).build();
       else
           return Response.status(Response.Status.NOT_ACCEPTABLE.getStatusCode(),message).build();
    }
    @GET
    @Path("{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response getInformation(@PathParam("id") int id)
    {
        List<User> users = userImpl.retrieve(id);
        if (users.isEmpty())
        {
           return Response.status(404,"No User Found In The  Database").build();
        }
        else {
            return Response.ok(users.get(0)).build();
        }

    }
    @DELETE
    @Path("{id}/{email}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response deleteUser(@PathParam("id") int id,@PathParam("email")  String email){
        String message=null;
        if (id>0&& (!StringUtil.isEmpty(email))) {
            message=userImpl.delete(id,email);
        }
        else{
            message="Please  Provide Id And Email Information";
        }

        if(message.equals("User Not found") || message.equals("Please  Provide Id And Email Information")){
            return Response.status(404,message).build();
        }
        else {
            return Response.ok(message).build();
        }
    }
}
