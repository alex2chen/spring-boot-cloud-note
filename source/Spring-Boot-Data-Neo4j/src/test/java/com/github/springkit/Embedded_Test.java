package com.github.springkit;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.neo4j.graphdb.*;
import org.neo4j.graphdb.index.Index;
import org.neo4j.graphdb.index.IndexManager;
import org.neo4j.graphdb.index.UniqueFactory;
import org.neo4j.graphdb.schema.IndexDefinition;
import org.neo4j.graphdb.schema.Schema;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * Embedded_Test
 *
 * @author alex.chen
 * @version 1.0.0
 * @date 2016/9/6
 */
public class Embedded_Test {
    private static GraphDatabaseService graphDb;

    @BeforeClass
    public static void prepareDatabase() {
        graphDb = Neo4jEmbeddedTools.getGraphDbServiceInstance();
    }

    @AfterClass
    public static void destroyDatabase() {
        Neo4jEmbeddedTools.registerShutdownHook(graphDb);
    }

    @Test
    public void helloWorld() {
        try (Transaction tx = graphDb.beginTx()) {
            Node firstNode = graphDb.createNode();
            firstNode.setProperty("message", "hello,");
            firstNode.addLabel(Label.label("test"));
            Node secondNode = graphDb.createNode();
            secondNode.setProperty("message", "World!");
            Relationship relationship = firstNode.createRelationshipTo(secondNode, RelTypes.KNOWS);
            relationship.setProperty("message", "brave Neo4j");

            System.out.println(String.format("helloWord created,%s %s %s", firstNode.getProperty("message"), relationship.getProperty("message"),
                    secondNode.getProperty("message")));
            Label label = Label.label("User");
            // Create some users
            for (int id = 0; id < 20; id++) {
                Node userNode = graphDb.createNode(label);
                userNode.setProperty("username", "user" + id + "@neo4j.org");
            }
            System.out.println("Users created.");
            tx.success();
        } catch (RuntimeException ex) {
            ex.printStackTrace();
        }

    }

    @Test
    public void createLableIndex() {
        try (Transaction tx = graphDb.beginTx()) {
            Schema schema = graphDb.schema();
            IndexDefinition indexDefinition = schema.indexFor(Label.label("User")).on("username").create();
            System.out.println("Users Index created." + indexDefinition.isConstraintIndex());
            tx.success();
        } catch (RuntimeException ex) {
            ex.printStackTrace();
        }
    }

    @Test
    public void updateNode() {
        try (Transaction tx = graphDb.beginTx()) {
            Node user = graphDb.findNode(Label.label("User"), "username", "user6@neo4j.org");
            if (user == null) {
                System.out.println("node not find");
            }
            user.setProperty("username", "alex@neo4j.org");
            //Remove the data
//            firstNode.getSingleRelationship(RelTypes.KNOWS, Direction.OUTGOING).delete();
//            firstNode.delete();
//            secondNode.delete();
            System.out.println("Users(user6) update.");
            tx.success();
        } catch (RuntimeException ex) {
            ex.printStackTrace();
        }
    }

    @Test
    public void delNode() {
        try (Transaction tx = graphDb.beginTx()) {
            ResourceIterator<Node> users = graphDb.findNodes(Label.label("User"), "username", "user8@neo4j.org");
            while (users.hasNext()) {
                users.next().delete();
            }
            System.out.println("Users(user8) delete.");
            tx.success();
        } catch (RuntimeException ex) {
            ex.printStackTrace();
        }
    }

    @Test
    public void findNode() {
        LoggerFactory.getLogger(Embedded_Test.class).debug("debug...");
        try (Transaction tx = graphDb.beginTx()) {
            ResourceIterator<Node> users = graphDb.findNodes(Label.label("User"));
            Node node = null;
            while (users.hasNext()) {
                node = users.next();
                System.out.print(node);
                System.out.println(node.getProperty("username"));
            }
            System.out.println("Users query label.");
            tx.success();
        } catch (RuntimeException ex) {
            ex.printStackTrace();
        }
    }

    @Test
    public void delIndex() {
        try (Transaction tx = graphDb.beginTx()) {
            Iterable<IndexDefinition> indexs = graphDb.schema().getIndexes(Label.label("User"));
            for (IndexDefinition indexDefinition : indexs) {
                indexDefinition.drop();
            }
            System.out.println("Users Index del.");
            tx.success();
        } catch (RuntimeException ex) {
            ex.printStackTrace();
        }


    }

    @Test
    public void manualIndex() {
        String USERNAME_KEY = "username";
        try (Transaction tx = graphDb.beginTx()) {
            IndexManager indexManager = graphDb.index();
            Index<Node> nodeIndex = indexManager.forNodes("nodes");
            Node node = null;
            for (int i = 20; i < 30; i++) {
                node = graphDb.createNode();
                node.setProperty(USERNAME_KEY, "user" + i + "@neo4j.org");
                nodeIndex.add(node, USERNAME_KEY, "user" + i + "@neo4j.org");
            }
            System.out.println("Users manual Index create.");
            tx.success();
        } catch (RuntimeException ex) {
            ex.printStackTrace();
        }
    }

    @Test
    public void findNodeUseIndex() {
        String username = "user22@neo4j.org";
        try (Transaction tx = graphDb.beginTx()) {
            Node foundUser = graphDb.index().forNodes("nodes").get("username", username).getSingle();
            System.out.println(foundUser.getProperty("username"));
            tx.success();
        } catch (RuntimeException ex) {
            ex.printStackTrace();
        }

    }

    @Test
    public void uniqueConstraint() {
        try (Transaction tx = graphDb.beginTx()) {
            graphDb.schema().constraintFor(Label.label("User")).assertPropertyIsUnique("name").create();
            tx.success();
        } catch (RuntimeException ex) {
            ex.printStackTrace();
        }
        System.out.println("唯一约束已创建.");
    }

    @Test
    public void uniqueNode() {
        String username = "user22@neo4j.org";
        ResourceIterator<Node> resultIterator = null;
        try (Transaction tx = graphDb.beginTx()) {
            String queryString = "MERGE (n:User {username: {name}}) RETURN n";
            Map<String, Object> parameters = new HashMap<>();
            parameters.put("name", username);
            resultIterator = graphDb.execute(queryString, parameters).columnAs("n");
            Node result = resultIterator.next();
            System.out.println(result.getProperty("username"));
            tx.success();
        } catch (RuntimeException ex) {
            ex.printStackTrace();
        }
    }

    @Test
    public void uniqueNodeUseManualIndex() {
        try (Transaction tx = graphDb.beginTx()) {
            UniqueFactory factory = new UniqueFactory.UniqueNodeFactory(graphDb, "usersIndex") {
                @Override
                protected void initialize(Node node, Map<String, Object> map) {
                    node.addLabel(Label.label("User"));
                    node.setProperty("username", map.get("name"));
                }
            };
            Node node = (Node) factory.getOrCreate("name", "alexchen@kxtx.cn");
            System.out.println(node.getProperty("username"));
        } catch (RuntimeException ex) {
            ex.printStackTrace();
        }
    }

    @Test
    public void pessimisticLock() {
        String username = "alexchen@kxtx.cn";
        try (Transaction tx = graphDb.beginTx()) {
            Index<Node> usersIndex = graphDb.index().forNodes("usersIndex");
            Node userNode = usersIndex.get("username", username).getSingle();
            if (username != null) {
                return;
            }

            tx.success();
        } catch (RuntimeException ex) {
            ex.printStackTrace();
        }

    }

}
