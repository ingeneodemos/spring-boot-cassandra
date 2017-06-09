package com.grupoprimario.springboot.cassandra;

import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.Metadata;
import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.Session;

public class SimpleClient {
    private Cluster cluster;
    private Session session;

    public Metadata connect(String keyspace, String... nodes) {
        cluster = Cluster.builder().addContactPoints(nodes).build();
        Metadata metadata = cluster.getMetadata();
        session = cluster.connect(keyspace);
        return metadata;
    }

    public ResultSet executeQuery(String query) {
        return session.execute(query);
    }

    public Session getSession() {
        return session;
    }

    public void close() {
        cluster.close();
    }
}
