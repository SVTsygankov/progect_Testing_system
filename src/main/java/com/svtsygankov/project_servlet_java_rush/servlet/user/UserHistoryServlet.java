package com.svtsygankov.project_servlet_java_rush.servlet.user;

import com.svtsygankov.project_servlet_java_rush.entity.Result;
import com.svtsygankov.project_servlet_java_rush.entity.User;
import com.svtsygankov.project_servlet_java_rush.listener.ContextListener;
import com.svtsygankov.project_servlet_java_rush.service.ResultService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

@WebServlet("/secure/history")
public class UserHistoryServlet extends HttpServlet {
    private ResultService resultService;

    @Override
    public void init() {
        this.resultService = (ResultService) getServletContext()
                .getAttribute(ContextListener.RESULTS_SERVICE);
    }

    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        User user = (User) req.getSession().getAttribute("user");
        var userId = user.getId();
        try {
            List<Result> results = resultService.getUserResults(userId);
            req.setAttribute("results", results);
            req.getRequestDispatcher("/WEB-INF/views/secure/history.jsp")
                    .forward(req, resp);
        } catch (IOException e) {
            req.setAttribute("error", "Error loading history");
            req.getRequestDispatcher("/WEB-INF/views/error.jsp")
                    .forward(req, resp);
        }
    }
}
