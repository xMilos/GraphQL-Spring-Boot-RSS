package com.test.task.resolver;

import com.test.task.repository.RssPersistence;
import com.test.task.model.Rss;
import graphql.kickstart.tools.GraphQLQueryResolver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class Query implements GraphQLQueryResolver {

    Logger logger = LoggerFactory.getLogger(Query.class);

    @Autowired
    private RssPersistence rssPersistence;

    public List<Rss> getAllFeed() {
        logger.info("Getting all feed");
        return rssPersistence.findAll();
    }
}
