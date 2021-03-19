package com.test.task.service;

import com.rometools.rome.feed.synd.SyndFeed;
import com.rometools.rome.io.FeedException;
import com.rometools.rome.io.SyndFeedInput;
import com.rometools.rome.io.XmlReader;
import com.test.task.repository.RssPersistence;
import com.test.task.model.Rss;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class RssFeedService {

    Logger logger = LoggerFactory.getLogger(RssFeedService.class);

    @Autowired
    private RssPersistence rssPersistence;

    @Value("${rss.url}")
    public String feedUrl;

    final Pattern pattern = Pattern.compile("<p>(.+?)</p>", Pattern.DOTALL);

    @Scheduled(fixedDelay = 300000)
    @Transactional
    public void poolFeed() {
        logger.info("Pooling feed time: {}", LocalDateTime.now());
        try (XmlReader reader = new XmlReader(new URL(feedUrl))) {
            SyndFeed feed = new SyndFeedInput().build(reader);
            feed.getEntries().stream().limit(15).forEach(
                i -> {
                    Rss rssModel = rssPersistence.findByTitle(i.getTitle()).orElse(new Rss());
                    rssModel.setTitle(i.getTitle());
                    var s = i.getDescription().getValue();
                    Matcher matcher = pattern.matcher(s);
                    matcher.find();
                    rssModel.setDescription(matcher.group(1));
                    rssModel.setLink(i.getLink());
                    rssModel.setPublicationDate(i.getPublishedDate());
                    rssPersistence.save(rssModel);
                }
            );

        } catch (IOException | FeedException e) {
            e.printStackTrace();
        }
    }
}
