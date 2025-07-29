package com.svtsygankov.project_servlet_java_rush.servlet.admin;

import com.svtsygankov.project_servlet_java_rush.servlet.BaseListTestsServlet;
import jakarta.servlet.annotation.WebServlet;


@WebServlet("/admin/tests")
public class AdminTestsServlet extends BaseListTestsServlet {

        @Override
        protected String getContentPage() {
                return "/WEB-INF/views/admin/admin-test-list.jsp";
        }

        @Override
        protected String getResourceName() {
                return "/WEB-INF/views/admin/admin-tests.jsp";
        }
}