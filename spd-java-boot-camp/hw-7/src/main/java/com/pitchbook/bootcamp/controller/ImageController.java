package com.pitchbook.bootcamp.controller;

import com.pitchbook.bootcamp.service.WebCrawlerWithDepth;
import com.pitchbook.bootcamp.entity.Image;
import com.pitchbook.bootcamp.service.ImageService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

@Controller
public class ImageController {

    private final WebCrawlerWithDepth crawler;
    private final ImageService imageService;

    public ImageController(WebCrawlerWithDepth crawler, ImageService imageService) {
        this.crawler = crawler;
        this.imageService = imageService;
    }

    @GetMapping("/images")
    public String getAllImages(Model model) {
        model.addAttribute("images", imageService.getAll()
                .stream()
                .map(Image::getUrl)
                .collect(Collectors.toList()));
        return "images";
    }

    @PostMapping("/crawl")
    public String getImages(@RequestParam String image, @RequestParam int depth) throws ExecutionException, InterruptedException {
        crawler.crawlUrl(image, depth);
        return "redirect:/images";
    }

    @PostMapping("/delete")
    public String deleteImage(@RequestParam String url) {
        imageService.delete(url);
        return "redirect:/images";
    }
}
