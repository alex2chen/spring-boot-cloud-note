package com.github.springkit.moco;

import com.github.springkit.moco.util.Waiter;
import org.apache.http.client.fluent.Request;
import org.apache.maven.plugin.Mojo;
import org.apache.maven.plugin.testing.AbstractMojoTestCase;

import java.io.IOException;

public abstract class AbstractMocoMojoTest extends AbstractMojoTestCase {

    private final static String MOCO_URI = "http://localhost:%d";
    private final Waiter waiter;

    protected AbstractMocoMojoTest() {
        waiter = new Waiter();
    }

    public Waiter getWaiter() {
        return waiter;
    }

    protected Runnable getMojoExecutionTask(final Mojo mojo) {
        return new Runnable() {
            @Override
            public void run() {
                try {
                    mojo.execute();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
    }

    protected void waitForMocoStartCompleted(final int port) {
        waiter.until(new Waiter.Condition() {
            @Override
            public boolean check() {
                try {
                    Request.Get(getMocoUri(port)).execute();
                    return true;
                } catch (IOException e) {
                    return false;
                }
            }
        });
    }

    protected String getMocoUri(int port) {
        return String.format(MOCO_URI, port);
    }

    protected boolean isServerShutdown(final String uri) throws Exception {
        getWaiter().until(new Waiter.Condition() {
            @Override
            public boolean check() {
                try {
                    Request.Get(uri).execute();
                    return false;
                } catch (IOException e) {
                    return true;
                }
            }
        }, new Waiter.TimeoutCallback() {
            @Override
            public void execute() {
                fail("Moco server was not shutdown");
            }
        });
        return true;
    }
}
