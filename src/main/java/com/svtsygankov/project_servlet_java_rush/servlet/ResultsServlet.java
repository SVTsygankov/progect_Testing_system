package com.svtsygankov.project_servlet_java_rush.servlet;

import com.svtsygankov.project_servlet_java_rush.dao.ResultsDao;
import com.svtsygankov.project_servlet_java_rush.entity.Result;
import com.svtsygankov.project_servlet_java_rush.entity.User;
import com.svtsygankov.project_servlet_java_rush.listener.ContextListener;
import com.svtsygankov.project_servlet_java_rush.service.ResultsService;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;

import java.io.IOException;
import java.util.List;

@AllArgsConstructor
@WebServlet("/secure/results")
public class ResultsServlet extends HttpServlet {

    private ResultsService resultsService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        ServletContext context = config.getServletContext();
        resultsService = new ResultsService((ResultsDao) context.getAttribute(ContextListener.RESULTS_SERVICE));
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Long userId = ((User) req.getSession().getAttribute("user")).getId();
        List<Result> results = resultsService.getResultsByUserId(userId);

        req.setAttribute("results", results);
        req.getRequestDispatcher("/WEB-INF/views/results.jsp").forward(req, resp);
    }
}
