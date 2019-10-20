package com.pitchbook.bootcamp;

import com.pitchbook.bootcamp.entity.Image;
import com.pitchbook.bootcamp.repository.ImageRepository;
import com.pitchbook.bootcamp.repository.ImageRepositoryImpl;
import com.pitchbook.bootcamp.service.ImageService;
import com.pitchbook.bootcamp.service.ImageServiceImpl;
import org.flywaydb.core.Flyway;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import javax.sql.DataSource;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentSkipListSet;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class WebCrawlerWithDepth {

    private static final DataSource dataSource = ConnectionPool.getInstance();
    private final ImageRepository repository = new ImageRepositoryImpl(dataSource);
    private final ImageService imageService = new ImageServiceImpl(repository);
    private final ExecutorService executor = Executors.newFixedThreadPool(5);

    public void crawlUrl(String url, int depth) throws InterruptedException, ExecutionException {
        Flyway flyway = Flyway
                .configure()
                .dataSource(dataSource).load();
        flyway.migrate();

        doRecursion(List.of(url), 0, depth);
        executor.shutdown();
    }

    private void doRecursion(List<String> links, int currentDepth, int maxDepth) throws ExecutionException, InterruptedException {
        if (currentDepth < maxDepth) {
            List<String> innerLinks = new ArrayList<>();
            for (String link : links) {
                Future<ConcurrentSkipListSet<String>> listFuture
                        = executor.submit(new Crawler(link));
                innerLinks.addAll(listFuture.get());
            }
            currentDepth++;
            doRecursion(innerLinks, currentDepth, maxDepth);
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


    class Crawler implements Callable {

        private String url;

        Crawler(String url) {
            this.url = url;
        }

        @Override
        public ConcurrentSkipListSet<String> call() throws IOException {
            ConcurrentSkipListSet<String> innerLinks = new ConcurrentSkipListSet<>();
            if (checkIfResponseStatusIsOK(url)) {
                Document document = Jsoup.connect(url).get();
                Elements images = document.select("img[src]");
                Elements hrefs = document.select("a[href]");
                for (Element image : images) {
                    imageService.insert(new Image(image.attr("abs:src")));
                }
                for (Element href : hrefs) {
                    String linkURL = href.attr("abs:href");
                    innerLinks.add(linkURL);
                }
            }
            return innerLinks;
        }

    }


}


