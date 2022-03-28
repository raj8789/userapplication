package com.app;

import com.config.UserConfiguration;
import com.health.AppHealthCheck;
import com.resource.HealthCheckControl;
import com.resource.UserControl;
import io.dropwizard.Application;
import io.dropwizard.setup.Environment;

public class ApplicationRunner extends Application<UserConfiguration> {
    @Override
    public void run(UserConfiguration userConfiguration, Environment environment) throws Exception {
        environment.jersey().register(new UserControl());
        environment.healthChecks().register("APIHealth",new AppHealthCheck());
        environment.jersey().register(new HealthCheckControl(environment.healthChecks()));
    }

    public static void main(String[] args) throws Exception {
        new ApplicationRunner().run("server");
    }
}
