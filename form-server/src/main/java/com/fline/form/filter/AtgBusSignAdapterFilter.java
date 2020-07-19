package com.fline.form.filter;

import com.alipay.atgbusmng.api.filter.http.AtgBusSignFilter;

import javax.servlet.*;
import java.io.IOException;

public class AtgBusSignAdapterFilter implements Filter {

	private AtgBusSignFilter atgBusSignFilter;

    public void setAtgBusSignFilter(AtgBusSignFilter atgBusSignFilter) {
        this.atgBusSignFilter = atgBusSignFilter;
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        atgBusSignFilter.doFilter(request, response, chain);
    }
    
    @Override
    public void destroy() {
    	if (atgBusSignFilter != null) {
    		atgBusSignFilter.destroy();
    	}
    }

}
