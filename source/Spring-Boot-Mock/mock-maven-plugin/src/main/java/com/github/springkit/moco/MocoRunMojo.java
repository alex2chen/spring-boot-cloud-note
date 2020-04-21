package com.github.springkit.moco;

import com.github.dreamhead.moco.bootstrap.arg.HttpArgs;
import com.github.dreamhead.moco.runner.JsonRunner;
import com.github.dreamhead.moco.runner.Runner;
import com.github.dreamhead.moco.runner.SettingRunner;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.Mojo;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Arrays;

/**
 * Runs a Moco server with a config file and port number. Note that the server will run
 * synchronously and can be stopped by killing the process.
 */
@Mojo(name = "run")
public class MocoRunMojo extends AbstractMocoExecutionMojo {

    public void execute() throws MojoExecutionException, MojoFailureException {
        checkParams();

        Runner runner;
        try {
            HttpArgs args = HttpArgs.httpArgs().withPort(port).build();
            if (configFile != null) {
                runner = JsonRunner.newJsonRunnerWithStreams(Arrays.asList(new FileInputStream(configFile)), args);
            } else {
                runner = new SettingRunner(new FileInputStream(globalFile), args);
            }
        } catch (FileNotFoundException e) {
            throw new MojoExecutionException("Unable to load config file", e);
        }
        runner.run();

        try {
            Thread.currentThread().join();
        } catch (InterruptedException e) {
            runner.stop();
        }
    }

}
