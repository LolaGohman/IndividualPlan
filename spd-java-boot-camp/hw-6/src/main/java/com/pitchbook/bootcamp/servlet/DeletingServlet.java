package com.pitchbook.bootcamp.servlet;

import com.pitchbook.bootcamp.ConnectionPool;
import com.pitchbook.bootcamp.entity.Image;
import com.pitchbook.bootcamp.repository.ImageRepository;
import com.pitchbook.bootcamp.repository.ImageRepositoryImpl;
import com.pitchbook.bootcamp.service.ImageService;
import com.pitchbook.bootcamp.service.ImageServiceImpl;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/delete")
public class DeletingServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        ImageRepository repository = new ImageRepositoryImpl(ConnectionPool.getInstance());
        ImageService imageService = new ImageServiceImpl(repository);
        String imageToDelete = req.getParameter("url");
        imageService.delete(imageToDelete);
        List<Image> images = imageService.getAll();
        req.getSession().setAttribute("images", images);
        resp.sendRedirect("views/images.jsp");
    }
}
