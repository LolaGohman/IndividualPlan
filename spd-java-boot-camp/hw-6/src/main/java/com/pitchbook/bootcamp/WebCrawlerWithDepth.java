package com.pitchbook.bootcamp;

import com.pitchbook.bootcamp.entity.Image;
import com.pitchbook.bootcamp.exception.MappingNotSupportedException;
import com.pitchbook.bootcamp.repository.ObjectRepositoryImpl;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.flywaydb.core.Flyway;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.sql.DataSource;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class WebCrawlerWithDepth {

    private Logger logger = LoggerFactory.getLogger(ObjectRepositoryImpl.class);

    private static final String DRIVER = "org.postgresql.Driver";
    private static final String URL = "jdbc:postgresql://127.0.0.1:5432/postgres";
    private static final String USER = "postgres";
    private static final String PASS = "postgres";
    private static final DataSource dataSource = getDataSource();
    private static final int CRAWLING_DEPTH = 2;
    private final ExecutorService executor = Executors.newFixedThreadPool(5);


    public static void main(String[] args) throws InterruptedException, ExecutionException {
        WebCrawlerWithDepth crawler = new WebCrawlerWithDepth();
        Flyway flyway = Flyway
                .configure()
                .locations("db/migration")
                .dataSource(dataSource).load();

        flyway.migrate();

        crawler.doRecursion(Collections.singletonList("http://ou3.r-pol.obr55.ru/"), 0);
        crawler.executor.shutdown();


    }

    private void doRecursion(List<String> links, int depth) throws ExecutionException, InterruptedException {
        if (depth < CRAWLING_DEPTH) {
            List<String> innerLinks = new ArrayList<>();
            for (String link : links) {
                Future<List<String>> listFuture = executor.submit(new Crawler(link));
                while (!listFuture.isDone()){
                    Thread.sleep(100);
                }
                innerLinks.addAll(listFuture.get());
            }
            depth++;
            doRecursion(innerLinks, depth);
        }
    }

    private boolean checkIfResponseStatusIsOK(String url) {
        int responseStatus = 0;
        try {
            Connection con = Jsoup.connect(url);
            responseStatus = con.execute().statusCode();
        } catch (Exception ignored) {
        }
        return responseStatus == 200;
    }

    private static DataSource getDataSource() {
        HikariConfig config = new HikariConfig();
        config.setDriverClassName(DRIVER);
        config.setJdbcUrl(URL);
        config.setUsername(USER);
        config.setPassword(PASS);

        return new HikariDataSource(config);
    }


    class Crawler implements Callable {

        private String url;

        Crawler(String url) {
            this.url = url;
        }

        @Override
        public List<String> call() {
            List<String> innerLinks = Collections.synchronizedList(new ArrayList<>());
            if (checkIfResponseStatusIsOK(url)) {
                try {
                    Document document = Jsoup.connect(url).get();
                    Elements images = document.select("img[src]");
                    Elements hrefs = document.select("a[href]");
                    for (Element image : images) {
                        saveElementToDatabase(image);
                    }
                    for (Element href : hrefs) {
                        String linkURL = href.attr("abs:href");
                        innerLinks.add(linkURL);
                    }
                } catch (IOException e) {
                    logger.error(e.getMessage());
                }

            }
            return innerLinks;
        }

        private void saveElementToDatabase(Element image) {
            Image imageToSave = new Image(image.attr("abs:src"));
            ObjectRepositoryImpl repository = new ObjectRepositoryImpl(dataSource);
            try {
                if (!repository.checkIfPrimaryKeyAlreadyExists(imageToSave.getUrl(), Image.class)) {
                    repository.insert(imageToSave);
                }
            } catch (MappingNotSupportedException | IllegalAccessException | SQLException | InstantiationException e) {
                logger.error(e.getMessage());
            }
        }
    }


}


