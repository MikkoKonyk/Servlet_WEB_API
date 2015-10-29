package com.konyk;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.konyk.JsonResponse;
import com.konyk.MetaData;

/**
 * <h1>Servlet WEB API</h1> 
 * 
 * This API reads text file from file system and apply
 * filtering/querying logic to produce response.
 * 
 * @author Konyk
 * @version 1.1
 * @since 2015-10
 */

@WebServlet("/ServletWEBAPI")
public class ServletWEBAPI extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private ServletContext servletContext;
	private String rootPath;
	private String fileName;
	private static final String error = "error";
	private static final String errorStatus = "errorStatus";
	private static final String metaData = "metaData";
	private static final String text = "text";

	/**
	 * <H2>Method reads data from file and saves it in Map</H2>
	 * 
	 * @return Map with data from file
	 */

	public Map<String, Object> reader() {
		List<String> resultSet = new ArrayList<String>();
		Map<String, Object> allResult = new ConcurrentHashMap<String, Object>();
		allResult.put(error, false);
		String line = null;
		try (BufferedReader in = new BufferedReader(new FileReader(new File(rootPath)));) {
			BasicFileAttributes attr = Files.readAttributes(Paths.get(rootPath), BasicFileAttributes.class);
			allResult.put(metaData,	new MetaData(fileName, attr.size() / 1000 + "KB",
							new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(attr.creationTime().toMillis())));
			while ((line = in.readLine()) != null) {
				resultSet.add(line);
			}
		} catch (FileNotFoundException exc) {
			allResult.put(error, true);
			allResult.put(errorStatus, "fileNotFound");
		} catch (IOException e) {
			allResult.put(error, true);
			allResult.put(errorStatus, "IOException");
		}
		allResult.put(text, resultSet);
		return allResult;
	}

	/**
	 * <H2>Method creates a response</H2>
	 * 
	 * @param limit - represents max number of chars
	 * @param q - represents text to search in file
	 * @param length - represents max string length
	 * @param includeMetaData - exposes file metadata in API response
	 * alongside textual content
	 * @return Json response
	 */

	public JsonResponse responseCreate(int limit, String q, int length,	boolean includeMetaData) {
		JsonResponse response = new JsonResponse();
		Map<String, Object> resultSet = reader();

		if ((boolean) resultSet.get(error)) {
			response.setTextData((String) resultSet.get(errorStatus));
		}

		int responseLength = 0;

		for (String sub : (ArrayList<String>) resultSet.get(text)) {

			List<String> container = new CopyOnWriteArrayList<String>();

			Pattern pat = Pattern.compile("\\w+");
			Matcher matcher = pat.matcher(sub);
			while (matcher.find()) {
				container.add(matcher.group());
			}

			if (q != null) {
				String regexp = q;

				for (String string : container) {
					if ((string.contains(regexp)) && ((responseLength + string.length() - 1) < limit) && (string.length() <= length)) {
						response.setTextData("\"" + string + "\"");
						responseLength += string.length();
					}
				}

			}
		}

		if (includeMetaData) {
			response.setMetaData((MetaData) resultSet.get(metaData));
		}

		return response;
	}


	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		servletContext = config.getServletContext();
		fileName = "text.txt";
		rootPath = servletContext.getRealPath("/files/" + fileName);
	}

	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		response.setContentType("application/json");

		PrintWriter out = response.getWriter();
		try {
			String limit = request.getParameter("limit");
			String q = request.getParameter("q");
			String length = request.getParameter("length");
			String includeMetaData = request.getParameter("includeMetaData");
			if (limit == null || limit.equals("")) {
				limit = "10000";
			}
			if (length == null || length.equals("")) {
				length = limit;
			}
			if (includeMetaData == null || includeMetaData.equals("")) {
				includeMetaData = "false";
			}
			if (q == null || q.equals("")) {
				q = null;
			}
			out.println(responseCreate(Integer.parseInt(limit), q, Integer.parseInt(length), Boolean.parseBoolean(includeMetaData)));
		} finally {
			out.close();
		}
	}
}
