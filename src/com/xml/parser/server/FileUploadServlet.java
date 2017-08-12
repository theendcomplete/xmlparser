package com.xml.parser.server;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.logging.Logger;

@MultipartConfig
public class FileUploadServlet extends HttpServlet {
    private static final long serialVersionUID = -5897221701350776117L;
    private static final Logger log = Logger.getLogger(FileUploadServlet.class
            .getName());

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        super.doGet(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        InputStream sis = req.getPart("uploadFormElement").getInputStream();

        BufferedReader bufferedReader = new BufferedReader(
                new InputStreamReader(sis));
        StringBuilder stringBuilder = new StringBuilder();
        String line = null;

        while ((line = bufferedReader.readLine()) != null) {
            stringBuilder.append(line);
        }

        bufferedReader.close();
        String xml = stringBuilder.toString();
        log.info(xml);
        resp.setContentType("text/html");
        resp.setStatus(HttpServletResponse.SC_CREATED);
        resp.getWriter().write(xml);
        resp.setHeader("Cache-Control", "no-cache");
        resp.flushBuffer();
    }
}
