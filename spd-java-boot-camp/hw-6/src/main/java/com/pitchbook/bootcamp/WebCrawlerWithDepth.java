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
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class WebCrawlerWithDepth {

    private Logger logger = LoggerFactory.getLogger(ObjectRepositoryImpl.class);

    private static final String DRIVER = "org.postgresql.Driver";
    private static final String URL = "jdbc:postgresql://127.0.0.1:5432/postgres";
    private static final String USER = "postgres";
    private static final String PASS = "postgres";
    private static final DataSource dataSource = getDataSource();
    private static final int CRAWLING_DEPTH = 2;


    public static void main(String[] args) throws InterruptedException {
        Flyway flyway = Flyway
                .configure()
                .locations("db/migration")
                .dataSource(dataSource).load();

        flyway.migrate();
        WebCrawlerWithDepth crawler = new WebCrawlerWithDepth();
        ExecutorService executor = Executors.newFixedThreadPool(5);
        Lock locker = new ReentrantLock();


        executor.execute(crawler.new Crawler("http://ou3.r-pol.obr55.ru/", 0, executor, locker));
        executor.awaitTermination(120, TimeUnit.SECONDS);
        executor.shutdownNow();
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


    class Crawler implements Runnable {

        private String url;
        private int depth;
        private ExecutorService executorService;
        private Lock locker;

        Crawler(String url, int depth, ExecutorService executorService, Lock lock) {
            this.url = url;
            this.depth = depth;
            this.executorService = executorService;
            this.locker = lock;
        }

        @Override
        public void run() {
            try {
                locker.lock();
                if (depth < CRAWLING_DEPTH) {
                    if (checkIfResponseStatusIsOK(url)) {
                        try {
                            Document document = Jsoup.connect(url).get();
                            Elements images = document.select("img[src]");
                            Elements hrefs = document.select("a[href]");
                            for (Element image : images) {
                               saveElementToDatabase(image);
                            }
                            depth++;
                            for (Element href : hrefs) {
                                String linkURL = href.attr("abs:href");
                                executorService.execute(new Crawler(linkURL, depth, executorService, locker));
                            }
                        } catch (IOException e) {
                            logger.error(e.getMessage());
                        }
                    }
                }
            } finally {
                locker.unlock();
            }
        }

        private void saveElementToDatabase(Element image){
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


