package com.github.springkit;

import javafx.beans.DefaultProperty;
import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.factory.GraphDatabaseFactory;
import org.neo4j.graphdb.factory.GraphDatabaseSettings;
import org.neo4j.kernel.api.security.AccessMode;
import org.neo4j.logging.slf4j.Slf4jLogProvider;
import org.slf4j.LoggerFactory;

import java.io.File;

/**
 * Neo4jEmbeddedManage
 *
 * @author alex.chen
 * @version 1.0.0
 * @date 2016/9/6
 */
public class Neo4jEmbeddedTools {
    private static final String NEODB_PATH = "E:\\neo4jData";

    /**
     * get an embedded database
     *
     * @return
     */
    public static GraphDatabaseService getGraphDbServiceInstance() {
        GraphDatabaseService graphDb = new GraphDatabaseFactory().setUserLogProvider(new Slf4jLogProvider()).newEmbeddedDatabaseBuilder(new File(NEODB_PATH)).setConfig(
                GraphDatabaseSettings.pagecache_memory, "512M").setConfig(GraphDatabaseSettings.string_block_size, "60").newGraphDatabase();
        return graphDb;
    }

    /**
     * get an embedded read-only instance
     *
     * @return
     */
    public static GraphDatabaseService getGraphDbServiceReadOnlyInstance() {
        GraphDatabaseService graphDb = new GraphDatabaseFactory().newEmbeddedDatabaseBuilder(new File(NEODB_PATH)).setConfig(GraphDatabaseSettings.read_only, "true").newGraphDatabase();
        return graphDb;
    }

    /**
     * shutdown
     *
     * @param graph
     */
    public static void registerShutdownHook(final GraphDatabaseService graph) {
        // Registers a shutdown hook for the Neo4j instance so that it
        // shuts down nicely when the VM exits (even if you "Ctrl-C" the
        // running application).
        Runtime.getRuntime().addShutdownHook(new Thread() {
            @Override
            public void run() {
                graph.shutdown();
            }
        });
    }
}
