package edu.xtec.merli.gestorusuaris;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import edu.xtec.merli.basedades.UserBD;
import edu.xtec.merli.segur.User;

public class UserAction extends Action {

	/** User results default page number */
	private static final int DEFAULT_PAGE = 1;

	/**
	 * Recull la sol�licitud de l'usuari per insertar un nou recurs.
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @throws Exception
	 * @return ActionForward
	 */
	public ActionForward execute( 	ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response)
			throws Exception{
		UserBD ubd = new UserBD();
		Integer unitat;
		try{
			unitat = Integer.valueOf(((UserForm)form).getUnitat());
		}catch(Exception e)
		{
			unitat = null;
		}

		// Queries the list of users from the database and stores it on
		// the current request. Notice that the returned results are
		// paginated according to a predefined limit.

		if (mustFetchUsers(request)) {
			int page = getCurrentPage(request);
			int count = ubd.fetchUsersCount();
			int limit = UserBD.DEFAULT_LIMIT;

			List<User> users = ubd.fetchUsersPage(page);

			request.setAttribute("users", users);
			request.setAttribute("currentPage", page);
			request.setAttribute("lastPage", 1 + (count / limit));
		}

		User u = new User(((UserForm)form).getUsername().toLowerCase(), ((UserForm)form).getEmail(),unitat,((UserForm)form).getPw(),((UserForm)form).getUs_merli());
		if (((UserForm)form).getOperation() != null){
			if (((UserForm)form).getOperation().compareTo("addUser")==0){
				try{
					ubd.addUser(u);
				}catch(Exception e){
					return mapping.findForward("error");
				}
			}
			if (((UserForm)form).getOperation().compareTo("setUser")==0){
				try{
					ubd.setUser(u);
				}catch(Exception e){
					return mapping.findForward("error");
				}
			}
			if (((UserForm)form).getOperation().compareTo("delUser")==0){
				try{
					ubd.delUser(u.getUser());
				}catch(Exception e){
					return mapping.findForward("error");
				}
			}
		}
		return (mapping.findForward("inserit"));
	}


	/**
	 * Checks if a list of users was requested. That is, if the page must
	 * show the list of users or otherwise the list of permissions.
	 *
	 * @param request       Current request instance
	 * @return              If the list of users was requested
	 */
	private boolean mustFetchUsers(HttpServletRequest request) {
		return !"area".equals(request.getParameter("nodeType"));
	}


	/**
	 * Obtains the requested page of results. This method default to
	 * {@code DEFAULT_PAGE} if no "page" parameter was provided or the
	 * provided value is not a valid integer value.
	 *
	 * @param request       Current request instance
	 * @return              Page number
	 */
	private int getCurrentPage(HttpServletRequest request) {
		String page = request.getParameter("page");

		try {
			int number = Integer.parseInt(page);
			return number > 0 ? number : DEFAULT_PAGE;
		} catch (NumberFormatException e) {
			return DEFAULT_PAGE;
		}
	}

}
