package com.np.order.action;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Arrays;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.np.base.utils.UDebugger;

public class LoggerFilter implements Filter {
	private static final Logger logger = Logger.getLogger(LoggerFilter.class);

	@Override
	public void init(FilterConfig arg0) throws ServletException {
		// TODO Auto-generated method stub

	}

	@Override
	public void destroy() {
		// TODO Auto-generated method stub

	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		String uri = ((HttpServletRequest) request).getRequestURI();
		if (isResource(uri)) {
			chain.doFilter(request, response);
			return;
		} else {
			log((HttpServletRequest) request);
			try {
				chain.doFilter(request, response);
			} catch (Throwable t) {
				logger.debug(UDebugger.trace(t));
				throw new ServletException(t);
			}
			/*
			 * javax.servlet.jsp.jspException: java.lang.Exception
			 * javax.servlet.error.exception: java.lang.Exception
			 */
			Exception exception = (Exception) request.getAttribute("javax.servlet.error.exception");
			if (exception != null)
				logger.trace(UDebugger.trace(exception));

			HttpServletResponse httpResponse = ((HttpServletResponse) response);
			logger.debug("Status Code: " + httpResponse.getStatus());
			if (httpResponse.getStatus() == 500) {
			}
		}
	}

	private void log(HttpServletRequest req) {
		logger.debug("[Reuqest] URI=" + req.getRequestURI());
		logger.debug("[Reuqest] ContentType=" + req.getContentType());
		if (isMultiParts(req))
			return;

		String[] names = req.getParameterMap().keySet().toArray(new String[0]);
		Arrays.sort(names);
		for (String name : names) {
			Object value = req.getParameter(name);
			logger.debug("\t [Parameter] " + name + "=" + value);
		}
	}

	@SuppressWarnings("unused")
	private void log(ServletResponseWrapper resp) {
		String responseType = resp.getContentType();
		logger.debug("[Response] ContentType=" + responseType);
		String encode = resp.getCharacterEncoding();
		logger.debug("[Response] CharacterEncoding=" + encode);
		for (String name : resp.getHeaderNames()) {
			logger.debug("[Response] [Header] " + name + "=" + resp.getHeader(name));
		}

		if (responseType != null) {
			if (responseType.startsWith("image"))
				return;
			if (responseType.startsWith("text/html"))
				return;
		}

		if (resp.getStringWriter() != null) {
			logger.debug(resp.getStringWriter());
		} else {
			byte[] content = resp.getOut().toByteArray();
			if (encode != null)
				try {
					logger.debug(new String(content, encode));
				} catch (UnsupportedEncodingException e) {
					logger.trace(UDebugger.trace(e));
					logger.trace(new String(content));
				}
			else
				logger.debug(new String(content));
		}
	}

	private boolean isMultiParts(HttpServletRequest request) {
		if (request.getContentType() != null
				&& request.getContentType().toLowerCase().indexOf("multipart/form-data") != -1) {
			return true;
		}
		return false;
	}

	private boolean isResource(String uri) {
		int pos = uri.lastIndexOf(".");
		if (pos == -1)
			return false;

		String ext = uri.substring(pos + 1).toLowerCase();
		if (ext.equals("js"))
			return true;
		if (ext.equals("css"))
			return true;
		if (ext.equals("html"))
			return true;
		if (ext.equals("htm"))
			return true;
		if (ext.equals("jpg"))
			return true;
		if (ext.equals("gif"))
			return true;

		return false;
	}
}
