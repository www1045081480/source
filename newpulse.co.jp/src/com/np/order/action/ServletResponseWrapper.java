package com.np.order.action;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Collection;
import java.util.Locale;

import javax.servlet.ServletOutputStream;
import javax.servlet.WriteListener;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

public class ServletResponseWrapper implements HttpServletResponse {
	private HttpServletResponse target;
	private StringWriter stringWriter;
	private PrintWriter writer;
	private ByteArrayOutputStream out;
	private ServletOutputStream servletOut;

	class MyServletOutputStream extends ServletOutputStream {

		private OutputStream out;

		MyServletOutputStream(OutputStream out) {
			this.out = out;
		}

		@Override
		public boolean isReady() {
			return true;
		}

		@Override
		public void setWriteListener(WriteListener arg0) {
		}

		@Override
		public void write(int b) throws IOException {
			out.write(b);
		}

	}

	public ServletResponseWrapper(HttpServletResponse target) {
		this.target = target;
		this.out = new ByteArrayOutputStream();
	}

	public void flush() {
		try {
			if (writer != null)
				writer.flush();
			if (servletOut != null)
				servletOut.flush();
			if (out != null)
				out.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void close() {
		try {
			flush();
			if (writer != null)
				writer.close();
			if (servletOut != null)
				servletOut.close();
			if (out != null)
				out.close();
			out = null;
			writer = null;
			servletOut = null;
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public ServletOutputStream getOutputStream() throws IOException {
		if (servletOut == null)
			servletOut = new MyServletOutputStream(out);
		return servletOut;
	}

	@Override
	public PrintWriter getWriter() throws IOException {
		if (writer == null) {
			// writer = new PrintWriter(new OutputStreamWriter(out));
			stringWriter = new StringWriter();
			writer = new PrintWriter(stringWriter);
		}
		return writer;
	}

	@Override
	public void flushBuffer() throws IOException {
		flush();
	}

	@Override
	public int getBufferSize() {
		return target.getBufferSize();
	}

	@Override
	public String getCharacterEncoding() {
		return target.getCharacterEncoding();
	}

	@Override
	public String getContentType() {
		return target.getContentType();
	}

	@Override
	public Locale getLocale() {
		return target.getLocale();
	}

	@Override
	public boolean isCommitted() {
		return target.isCommitted();
	}

	@Override
	public void reset() {
		target.reset();
		out.reset();
	}

	@Override
	public void resetBuffer() {
		target.resetBuffer();

	}

	@Override
	public void setBufferSize(int arg0) {
		target.setBufferSize(arg0);
	}

	@Override
	public void setCharacterEncoding(String arg0) {
		target.setCharacterEncoding(arg0);
	}

	@Override
	public void setContentLength(int arg0) {
		target.setContentLength(arg0);
	}

	@Override
	public void setContentLengthLong(long arg0) {
		target.setContentLengthLong(arg0);
	}

	@Override
	public void setContentType(String arg0) {
		target.setContentType(arg0);
	}

	@Override
	public void setLocale(Locale arg0) {
		target.setLocale(arg0);

	}

	@Override
	public void addCookie(Cookie arg0) {
		target.addCookie(arg0);

	}

	@Override
	public void addDateHeader(String arg0, long arg1) {
		target.addDateHeader(arg0, arg1);

	}

	@Override
	public void addHeader(String arg0, String arg1) {
		target.addHeader(arg0, arg1);

	}

	@Override
	public void addIntHeader(String arg0, int arg1) {
		target.addIntHeader(arg0, arg1);

	}

	@Override
	public boolean containsHeader(String arg0) {
		return target.containsHeader(arg0);
	}

	@Override
	public String encodeRedirectURL(String arg0) {
		return target.encodeRedirectURL(arg0);
	}

	@SuppressWarnings("deprecation")
	@Override
	public String encodeRedirectUrl(String arg0) {
		return target.encodeRedirectUrl(arg0);
	}

	@Override
	public String encodeURL(String arg0) {
		return target.encodeURL(arg0);
	}

	@SuppressWarnings("deprecation")
	@Override
	public String encodeUrl(String arg0) {
		return target.encodeUrl(arg0);
	}

	@Override
	public String getHeader(String arg0) {
		return target.getHeader(arg0);
	}

	@Override
	public Collection<String> getHeaderNames() {
		return target.getHeaderNames();
	}

	@Override
	public Collection<String> getHeaders(String arg0) {
		return target.getHeaders(arg0);
	}

	@Override
	public int getStatus() {
		return target.getStatus();
	}

	@Override
	public void sendError(int arg0) throws IOException {
		target.sendError(arg0);

	}

	@Override
	public void sendError(int arg0, String arg1) throws IOException {
		target.sendError(arg0, arg1);

	}

	@Override
	public void sendRedirect(String arg0) throws IOException {
		target.sendRedirect(arg0);

	}

	@Override
	public void setDateHeader(String arg0, long arg1) {
		target.setDateHeader(arg0, arg1);

	}

	@Override
	public void setHeader(String arg0, String arg1) {
		target.setHeader(arg0, arg1);

	}

	@Override
	public void setIntHeader(String arg0, int arg1) {
		target.setIntHeader(arg0, arg1);

	}

	@Override
	public void setStatus(int arg0) {
		target.setStatus(arg0);

	}

	@SuppressWarnings("deprecation")
	@Override
	public void setStatus(int arg0, String arg1) {
		target.setStatus(arg0, arg1);
	}

	public ByteArrayOutputStream getOut() {
		return out;
	}

	public StringWriter getStringWriter() {
		return stringWriter;
	}

}
