package org.sakaiproject.nakamura.todo;

import org.apache.felix.scr.annotations.sling.SlingServlet;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.servlets.SlingAllMethodsServlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;

@SlingServlet(methods = { "GET", "POST" }, paths = { "/system/todo" }, generateComponent = true, generateService = true)

public class TodoStoreServlet extends SlingAllMethodsServlet {

	private static final long serialVersionUID = -3279889579407055346L;

	private static TodoServiceImpl todoService = new TodoServiceImpl();

	// @Reference
	// protected transient SlingRepository slingRepository;

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.apache.sling.api.servlets.SlingSafeMethodsServlet#doGet(org.apache.sling.api.SlingHttpServletRequest,
	 *      org.apache.sling.api.SlingHttpServletResponse)
	 */
	@Override
	protected void doGet(SlingHttpServletRequest request,
	        SlingHttpServletResponse response) throws ServletException, IOException {

		response.setCharacterEncoding("UTF-8");
		PrintWriter out = response.getWriter();
		String result = "";

		if (request.getParameter("operation").equals("list")) {

			result = todoService.list(request);

		}

		out.print(result);

	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.apache.sling.api.servlets.SlingSafeMethodsServlet#doPost(org.apache.sling.api.SlingHttpServletRequest,
	 *      org.apache.sling.api.SlingHttpServletResponse)
	 */
	@Override
	protected void doPost(SlingHttpServletRequest request,
	        SlingHttpServletResponse response) throws ServletException,
	        IOException {

		response.setCharacterEncoding("UTF-8");
		PrintWriter out = response.getWriter();
		String result = "";

		if (request.getParameter("operation").equals("clear")) {

			result = todoService.clear(request);

		} else if (request.getParameter("operation").equals("save")) {

			result = todoService.save(request);

		}

		out.print(result);
	}

}