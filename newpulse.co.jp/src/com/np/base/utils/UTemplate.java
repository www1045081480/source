package com.np.base.utils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.nio.charset.Charset;
import java.util.Map;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;

public class UTemplate {
	static {
		try {
			Velocity.init("./conf/velocity.properties");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static String generate(String templateFile, Map<String, Object> params, String charset) throws Exception {
		Template template = Velocity.getTemplate(templateFile, "UTF-8");
		VelocityContext context = new VelocityContext();

		ByteArrayOutputStream bout = new ByteArrayOutputStream();
		Writer writer = getWriter(bout, Charset.forName(charset));
		for (Map.Entry<String, Object> param : params.entrySet())
			context.put(param.getKey(), param.getValue());
		template.merge(context, writer);
		writer.close();
		return new String(bout.toByteArray(), charset);
	}

	public static void generate(String templateFile, Map<String, Object> params, File file) throws Exception {
		Template template = Velocity.getTemplate(templateFile, "UTF-8");
		VelocityContext context = new VelocityContext();

		File folder = file.getParentFile();
		UFile.validate(folder);

		Writer writer = getWriter(file, Charset.forName("UTF-8"));
		for (Map.Entry<String, Object> param : params.entrySet())
			context.put(param.getKey(), param.getValue());
		template.merge(context, writer);
		writer.close();
	}

	private static Writer getWriter(File file, Charset charset) throws Exception {
		UFile.ccheckFilepath(file);
		if (file.exists()) {
			file.setWritable(true);
			UFile.delete(file);
		}

		OutputStream out = new FileOutputStream(file);
		Writer writer = new OutputStreamWriter(out, charset);
		return writer;
	}

	private static Writer getWriter(OutputStream out, Charset charset) throws Exception {
		Writer writer = new OutputStreamWriter(out, charset);
		return writer;
	}
}
