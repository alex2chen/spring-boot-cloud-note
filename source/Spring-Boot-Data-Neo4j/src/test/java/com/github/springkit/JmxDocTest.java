package com.github.springkit;

import org.junit.Test;
import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.jmx.JmxUtils;
import org.neo4j.test.TestGraphDatabaseFactory;

import javax.management.ObjectName;
import java.util.Date;

import static org.junit.Assert.assertTrue;

/**
 * 2.12. Reading a management attribute
 *
 * @author alex.chen
 * @version 1.0.0
 * @date 2016/9/7
 */
public class JmxDocTest {
    @Test
    public void readJmxProperties() {
        GraphDatabaseService graphDbService = new TestGraphDatabaseFactory().newImpermanentDatabase();
        try {
            //api http://neo4j.com/docs/java-reference/current/javadocs/
            ObjectName objKernel = JmxUtils.getObjectName(graphDbService, "Kernel");
            Date startTime = JmxUtils.getAttribute(objKernel, "KernelStartTime");
//            Date now = new Date();
//            assertTrue(startTime.before(now) || startTime.equals(now));
            System.out.println(startTime);
            System.out.println(JmxUtils.getAttribute(objKernel, "DatabaseName").toString());


        } finally {
            graphDbService.shutdown();
        }
    }
}
