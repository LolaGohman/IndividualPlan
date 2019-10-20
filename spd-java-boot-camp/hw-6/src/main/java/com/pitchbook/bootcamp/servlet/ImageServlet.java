package com.pitchbook.bootcamp.servlet;

import com.pitchbook.bootcamp.ConnectionPool;
import com.pitchbook.bootcamp.WebCrawlerWithDepth;
import com.pitchbook.bootcamp.entity.Image;
import com.pitchbook.bootcamp.repository.ImageRepository;
import com.pitchbook.bootcamp.repository.ImageRepositoryImpl;
import com.pitchbook.bootcamp.service.ImageService;
import com.pitchbook.bootcamp.service.ImageServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.ExecutionException;

@WebServlet("/images")
public class ImageServlet extends HttpServlet {

    private Logger logger = LoggerFactory.getLogger(ImageServlet.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        RequestDispatcher requestDispatcher = req.getRequestDispatcher("views/crawl.jsp");
        requestDispatcher.forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        ImageRepository repository = new ImageRepositoryImpl(ConnectionPool.getInstance());
        ImageService imageService = new ImageServiceImpl(repository);
        WebCrawlerWithDepth crawler = new WebCrawlerWithDepth();

        String imageUrl = req.getParameter("image");
        int crawlingDepth = Integer.parseInt(req.getParameter("depth"));
        try {
            crawler.crawlUrl(imageUrl, crawlingDepth);
        } catch (InterruptedException | ExecutionException e) {
            logger.error(e.getMessage());
        }
        List<Image> images = imageService.getAll();
        req.getSession().setAttribute("images", images);
        resp.sendRedirect("views/images.jsp");
    }
}
