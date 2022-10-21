package com.hilton;

import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;

public class HiltonProjectApplication extends Application<HiltonProjectConfiguration> {

    public static void main(final String[] args) throws Exception {
        new HiltonProjectApplication().run(args);
    }

    @Override
    public String getName() {
        return "HiltonProject";
    }

    @Override
    public void initialize(final Bootstrap<HiltonProjectConfiguration> bootstrap) {
        // TODO: application initialization
    }

    @Override
    public void run(final HiltonProjectConfiguration configuration,
                    final Environment environment) {
        // TODO: implement application
    }

}
