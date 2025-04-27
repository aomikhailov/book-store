package ru.almidev.bookstore.filters;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.File;
import java.io.IOException;

@WebFilter("/static/*")
public class StaticResourceFilter implements Filter {

    private String staticFilePath;

    @Override
    public void init(FilterConfig filterConfig) {
        this.staticFilePath = filterConfig.getServletContext().getRealPath("/static");
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        String requestURI = httpRequest.getRequestURI();
        File staticFile = new File(this.staticFilePath, requestURI.replace("/static", ""));

        if (!staticFile.exists()) {
            request.setAttribute("jakarta.servlet.error.status_code", HttpServletResponse.SC_NOT_FOUND);
            request.setAttribute("jakarta.servlet.error.message", "Файл [" + requestURI + "] не найден.");
            request.getRequestDispatcher("/error").forward(request, response);
        } else {
            chain.doFilter(request, response);
        }

    }

    @Override
    public void destroy() {
    }
}