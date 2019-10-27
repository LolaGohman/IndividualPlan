package com.pitchbook.bootcamp.service;

import com.pitchbook.bootcamp.entity.Image;
import com.pitchbook.bootcamp.service.ImageService;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentSkipListSet;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

@Service
public class WebCrawlerWithDepth {

    private final ImageService imageService;
    private final ExecutorService executor;


    public WebCrawlerWithDepth(ImageService imageService) {
        this.imageService = imageService;
        executor = Executors.newFixedThreadPool(5);
    }

    public void crawlUrl(String url, int depth) throws InterruptedException, ExecutionException {
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


