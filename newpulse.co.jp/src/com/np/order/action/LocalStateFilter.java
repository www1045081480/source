package com.np.order.action;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class LocalStateFilter implements Filter {
	@Override
	public void init(FilterConfig conf) throws ServletException {

	}

	@Override
	public void destroy() {
		// TODO Auto-generated method stub

	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {

		HttpServletResponse httpResponse = ((HttpServletResponse) response);
		HttpServletRequest httpRequest = ((HttpServletRequest) request);
		boolean gotoNext = SessionMgr.begin(httpRequest);

		if (gotoNext) {
			chain.doFilter(request, response);
		} else {
			/*
			 * Session Timeout
			 */
			String url = httpRequest.getRequestURL().toString();
			int pos = url.indexOf(".action");
			if (pos != -1) {
				pos = url.lastIndexOf("/", pos);
				url = url.substring(0, pos + 1);

				if (url.endsWith("/xinan/xinan/"))
					url = url.substring(0, url.length() - "xinan".length());
				url += "logout.action";
			}
			httpResponse.sendRedirect(url);
		}
	}

}
