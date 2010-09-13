package org.sakaiproject.nakamura.notepaper;

import org.apache.felix.scr.annotations.sling.SlingServlet;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.servlets.SlingAllMethodsServlet;
import org.apache.sling.commons.json.JSONException;
import org.apache.sling.commons.json.io.JSONWriter;
import org.sakaiproject.nakamura.api.notepaper.Notepaper;
import org.sakaiproject.nakamura.api.notepaper.NotepaperConstants;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;

@SlingServlet(methods = { "GET", "POST" }, paths = { "/system/notepaper" }, generateComponent = true, generateService = true)

public class NotepaperServlet extends SlingAllMethodsServlet {

  private static final long serialVersionUID = -6447840238892707356L;
 
  private static NotepaperServiceImpl noteService = new NotepaperServiceImpl();

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
		Notepaper result = noteService.list(request);
		JSONWriter writer = new JSONWriter(response.getWriter());
		try {
      writer.object()
        .key(NotepaperConstants.SAKAI_NOTEPAPER_MESSAGE)
        .value(result.getMessage())
        .key(NotepaperConstants.SAKAI_NOTEPAPER_MODIFYTIME)
        .value(result.getModifyTime())
        .endObject();
    } catch (JSONException e) {
      e.printStackTrace();
    }
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.apache.sling.api.servlets.SlingSafeMethodsServlet#doPost(org.apache.sling.api.SlingHttpServletRequest,
	 *      org.apache.sling.api.SlingHttpServletResponse)
	 */
	@Override
	protected void doPost(SlingHttpServletRequest request,
	        SlingHttpServletResponse response) throws ServletException, IOException {
		response.setCharacterEncoding("UTF-8");
		PrintWriter out = response.getWriter();
		
		Notepaper notepaper = noteService.save(request);
		JSONWriter writer = new JSONWriter(out);
		try {
      writer.object()
            .key(NotepaperConstants.SAKAI_NOTEPAPER_MODIFYTIME)
            .value(notepaper.getModifyTime())
            .endObject();
    } catch (JSONException e) {
      e.printStackTrace();
    }
	}
}