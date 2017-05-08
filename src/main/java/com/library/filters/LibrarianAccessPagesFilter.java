package com.library.filters;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletResponse;

import com.library.users.User;

@WebFilter(urlPatterns={ "/book/*", "/orders", "/add-book", "/categories", "/add-category", "/order/*" })
public class LibrarianAccessPagesFilter implements Filter {
	private CheckUserAccess checkUserAccess = new CheckUserAccess();
    /**
     * Default constructor. 
     */
    public LibrarianAccessPagesFilter() {
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see Filter#destroy()
	 */
	public void destroy() {
		// TODO Auto-generated method stub
	}

	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		User user = checkUserAccess.getUser(request);
		if(user != null && user.getRole() != 3) {
			chain.doFilter(request, response);
		}
		else {
			HttpServletResponse resp = (HttpServletResponse)response;
			resp.sendRedirect("/");
		}
	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
		// TODO Auto-generated method stub
	}
}
