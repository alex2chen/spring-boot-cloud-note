package com.github.springkit.moco;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugins.annotations.Parameter;

import java.io.File;

/**
 * A generic Mojo defining the base requirements for using a Moco server.
 */
public abstract class AbstractMocoExecutionMojo extends AbstractMojo {

    protected static String MONITOR_KEY = "_MOCO_MAVEN_PLUGIN_MONITOR_KEY";
    protected static String SHUTDOWN_PORT_PROPERTY_NAME = "moco.shutdown.port";

    /**
     * The file containing the JSON configuration.
     */
    @Parameter(required = false)
    protected File configFile;

    /**
     * The port to start the server on.
     */
    @Parameter(required = true)
    protected Integer port;

    /**
     * The port to stop the server on (optional).
     */
    @Parameter(required = false)
    protected Integer stopPort;
    /**
     * The global settings file.
     */
    @Parameter(required = false)
    protected File globalFile;

    public File getConfigFile() {
        return configFile;
    }

    public void setConfigFile(File configFile) {
        this.configFile = configFile;
    }

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }

    public Integer getStopPort() {
        return stopPort;
    }

    public void setStopPort(Integer stopPort) {
        this.stopPort = stopPort;
    }

    protected void checkParams() throws MojoExecutionException {
        if (fileNotExist(configFile)) {
            throw new MojoExecutionException("Moco config file does not exist.");
        }
        if (fileNotExist(globalFile)) {
            throw new MojoExecutionException("Moco global config file does not exist.");
        }
        if (port == null || port < 1) {
            throw new MojoExecutionException("Invalid port number specified.");
        }
    }

    protected boolean fileNotExist(File file) {
        return file != null && !file.exists();
    }
}
