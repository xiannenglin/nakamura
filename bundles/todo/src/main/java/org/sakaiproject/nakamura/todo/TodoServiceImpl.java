package org.sakaiproject.nakamura.todo;

import javax.jcr.Node;
import javax.jcr.NodeIterator;
import javax.jcr.PathNotFoundException;
import javax.jcr.RepositoryException;
import javax.jcr.Session;

import org.apache.jackrabbit.api.security.user.Authorizable;
import org.apache.sling.api.SlingHttpServletRequest;

import org.sakaiproject.nakamura.api.personal.PersonalUtils;
import org.sakaiproject.nakamura.api.todo.TodoService;

public class TodoServiceImpl implements TodoService {

	public String clear(SlingHttpServletRequest request) {

		String result = "";
		// String result = request.getResource().getResourceType();

		try {
			Session session = request.getResourceResolver().adaptTo(Session.class);
			Authorizable au = PersonalUtils.getAuthorizable(session, request.getRemoteUser());
			String privatePath = PersonalUtils.getPrivatePath(au);
			Node privateNode = session.getRootNode().getNode(privatePath.substring(1));
			Node todoNode = privateNode.getNode("todo");

			NodeIterator nodes = todoNode.getNodes();
			for (int i = 0; i < nodes.getSize(); i++) {
				nodes.nextNode().remove();
			}

			session.save();
			
			result += "Clear successfully!";

			return(result);
		} catch (PathNotFoundException pnfe) {
			return(pnfe.toString());
		} catch (RepositoryException re) {
			return(re.toString());
		}

	}

	public String list(SlingHttpServletRequest request) {
		try {
			Session session = request.getResourceResolver().adaptTo(Session.class);
			Authorizable au = PersonalUtils.getAuthorizable(session, request.getRemoteUser());
			String privatePath = PersonalUtils.getPrivatePath(au);
			Node privateNode = session.getRootNode().getNode(privatePath.substring(1));
			Node todoNode = privateNode.getNode("todo");
			NodeIterator nodes = todoNode.getNodes();

			String result = "{\"todo\": [";
			for (int i = 0; i < nodes.getSize(); i++) {

				if (i > 0) {
					result += ", ";
				}

				Node item = nodes.nextNode();
				result += "{";
				result += "\"task\": \"" + item.getProperty("task").getValue().getString() + "\", ";
				result += "\"time\": \"" + item.getProperty("time").getValue().getString() + "\", ";
				result += "\"location\": \"" + item.getProperty("location").getValue().getString() + "\"";
				result += "}";

			}
			result += "]}";

			return(result);
		}
		catch(PathNotFoundException pnfe) {
			return(pnfe.toString());
			
		}
		catch(RepositoryException re) {
			return(re.toString());
		}
	}

	public String save(SlingHttpServletRequest request) {

		String task = request.getParameter("task");
		String time = request.getParameter("time");
		String location = request.getParameter("location");

		try {
			Session session = request.getResourceResolver().adaptTo(Session.class);
			Authorizable au = PersonalUtils.getAuthorizable(session, request.getRemoteUser());

			String privatePath = PersonalUtils.getPrivatePath(au);
			Node privateNode = session.getRootNode().getNode(privatePath.substring(1));
			Node todoNode = null;

			try {

				todoNode = privateNode.getNode("todo");

			} catch (PathNotFoundException e) {

				todoNode = privateNode.addNode("todo");

			}

			long number = todoNode.getNodes().getSize();
			Node newNode = todoNode.addNode("item" + String.valueOf(number));

			newNode.setProperty("task", task);
			newNode.setProperty("time", time);
			newNode.setProperty("location", location);

			session.save();

			return("Saved successfully!");
		} catch (RepositoryException re) {
			return(re.toString());
		}

	}

}