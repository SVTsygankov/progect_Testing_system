package com.svtsygankov.project_servlet_java_rush.servlet.user;

import com.svtsygankov.project_servlet_java_rush.servlet.BaseListTestsServlet;
import jakarta.servlet.annotation.WebServlet;

@WebServlet("/secure/tests")
public class ListTestsServlet extends BaseListTestsServlet {

        @Override
        protected String getContentPage() {
                return "/WEB-INF/views/secure/test-list.jsp";
        }

        @Override
        protected String getResourceName() {
                return "/WEB-INF/views/secure/tests.jsp";
        }
}