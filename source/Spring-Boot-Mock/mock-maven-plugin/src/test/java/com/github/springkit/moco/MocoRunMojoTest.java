package com.github.springkit.moco;

import com.google.common.base.Strings;
import org.apache.http.client.fluent.Request;
import org.junit.Test;

import java.io.File;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class MocoRunMojoTest extends AbstractMocoMojoTest {

    @Test
    public void testRun() throws Exception {
        File pom = getTestFile("src/test/resources/test-pom.xml");
        assertTrue(pom.exists());

        final MocoRunMojo runMojo = (MocoRunMojo) lookupMojo("run", pom);
        assertNotNull(runMojo);

        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.submit(getMojoExecutionTask(runMojo));

        waitForMocoStartCompleted(runMojo.getPort());

        String getResponse = Request.Get(getMocoUri(runMojo, "/foo")).execute().returnContent().asString();
        assertEquals("bar", getResponse);

        executorService.shutdownNow();
        executorService.awaitTermination(1, TimeUnit.SECONDS);
    }

    @Test
    public void test2Run() throws Exception {
        File pom = getTestFile("src/test/resources/test-pom.xml");
        assertTrue(pom.exists());

        final MocoRunMojo runMojo = (MocoRunMojo) lookupMojo("run", pom);
        assertNotNull(runMojo);

        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.submit(getMojoExecutionTask(runMojo));

        waitForMocoStartCompleted(runMojo.getPort());

        String getResponse = Request.Get(getMocoUri(runMojo, "/json?id=1")).execute().returnContent().asString();
        assertEquals("foo1", getResponse);

        executorService.shutdownNow();
        executorService.awaitTermination(1, TimeUnit.SECONDS);
    }

    @Test
    public void test3Run() throws Exception {
        File pom = getTestFile("src/test/resources/test-pom.xml");
        assertTrue(pom.exists());

        final MocoRunMojo runMojo = (MocoRunMojo) lookupMojo("run", pom);
        assertNotNull(runMojo);

        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.submit(getMojoExecutionTask(runMojo));

        waitForMocoStartCompleted(runMojo.getPort());
        System.in.read();
    }

    private String getMocoUri(MocoRunMojo runMojo, String path) {
        return Strings.isNullOrEmpty(path) ? getMocoUri(runMojo.getPort()) : getMocoUri(runMojo.getPort()) + path;
    }
}
