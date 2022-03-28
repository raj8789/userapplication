package com.health;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import com.codahale.metrics.health.HealthCheck;
public class AppHealthCheck extends HealthCheck {
    private  Client client=null;
    public AppHealthCheck()
    {
        client = ClientBuilder.newClient();
    }
    @Override
    protected Result check() throws Exception {
        WebTarget webTarget = client.target("http://localhost:8080/api/user/3");
        Invocation.Builder invocationBuilder =  webTarget.request(MediaType.APPLICATION_JSON);
        Response response = invocationBuilder.get();
        if(response !=null && response.getStatus()==200){
            return Result.healthy("Application is Running Fine");
        }
        else {
            return Result.unhealthy("Application is not fine");
        }
    }
}
