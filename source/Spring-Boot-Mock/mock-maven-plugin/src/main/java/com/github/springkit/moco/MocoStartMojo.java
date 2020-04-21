package com.github.springkit.moco;

import com.github.dreamhead.moco.bootstrap.arg.HttpArgs;
import com.github.dreamhead.moco.runner.RunnerFactory;
import com.github.dreamhead.moco.runner.ShutdownRunner;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.Mojo;

/**
 * Starts a Moco server with a config file and port number. Note that the server will
 * be run asynchronously and can be stopped using the `stop` goal.
 */
@Mojo(name = "start")
public class MocoStartMojo extends AbstractMocoExecutionMojo {

    public void execute() throws MojoExecutionException, MojoFailureException {
        checkParams();

        HttpArgs startArgs;
        if (configFile != null) {
            startArgs = HttpArgs.httpArgs().withPort(port).withShutdownPort(stopPort).withConfigurationFile(configFile.getAbsolutePath()).build();
        } else {
            startArgs = HttpArgs.httpArgs().withPort(port).withShutdownPort(stopPort).withConfigurationFile(globalFile.getAbsolutePath()).build();
        }
        ShutdownRunner runner = new RunnerFactory(MONITOR_KEY).createRunner(startArgs);
        runner.run();

        // Store the shutdown port in a system property so we can use it to stop the server later
        System.setProperty(SHUTDOWN_PORT_PROPERTY_NAME, Integer.valueOf(runner.shutdownPort()).toString());
    }

    @Override
    protected void checkParams() throws MojoExecutionException {
        super.checkParams();

        if (stopPort != null && stopPort < 0) {
            throw new MojoExecutionException("Invalid stop port number specified.");
        }
    }

}
