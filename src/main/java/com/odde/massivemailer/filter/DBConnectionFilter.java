package com.odde.massivemailer.filter;

import org.javalite.activejdbc.Base;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import java.io.IOException;

@SuppressWarnings("unused")
@WebFilter("/*")
public class DBConnectionFilter implements Filter {
    private FilterConfig filterConfig;

    @Override
    public void init(FilterConfig filterConfig) {
        this.filterConfig = filterConfig;
    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws IOException, ServletException {
        try{
            Base.open();
            Base.openTransaction();
            chain.doFilter(req, resp);
            Base.commitTransaction();
        }
        catch (IOException | ServletException e){
            Base.rollbackTransaction();
            throw e;
        } finally{
            Base.close();
        }
    }

    @Override
    public void destroy() { }
}
