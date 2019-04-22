package com.github.springkit;

import org.junit.Test;
import org.neo4j.driver.v1.*;

/**
 * BoltDriver_test
 *
 * @author alex.chen
 * @version 1.0.0
 * @date 2016/9/12
 */
public class BoltDriver_test {
    @Test
    public void search_acted_in_test() {
        String query = "MATCH (a:Person) WHERE a.name = 'alex' RETURN a.name AS name, a.title AS title";
        try (Driver driver = GraphDatabase.driver("bolt://localhost", AuthTokens.basic("neo4j", "123456"));
             Session session = driver.session()) {
            //session.run("CREATE (a:Person {name:'alex', title:'King'})");
            StatementResult result = session.run(query);
            Record record = null;
            while (result.hasNext()) {
                record = result.next();
                System.out.println(record.get("name").asString());
                System.out.println(record.get("title").asString());
            }
        } catch (RuntimeException ex) {
            ex.printStackTrace();
        }
    }
}
