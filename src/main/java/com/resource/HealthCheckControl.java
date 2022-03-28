package com.resource;

import com.codahale.metrics.health.HealthCheck;
import com.codahale.metrics.health.HealthCheckRegistry;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.Map;
import java.util.Set;

@Produces(MediaType.APPLICATION_JSON)
@Path("/api")
public class HealthCheckControl {
    private HealthCheckRegistry registry;
    public HealthCheckControl(HealthCheckRegistry registry) {
        this.registry = registry;
    }
    @GET
    @Path("/status")
    public Set<Map.Entry<String, HealthCheck.Result>> getStatus(){
        return registry.runHealthChecks().entrySet();
    }
}
