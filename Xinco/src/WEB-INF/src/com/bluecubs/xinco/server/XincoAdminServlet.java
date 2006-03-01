/**
 *Copyright 2006 blueCubs.com
 *
 *Licensed under the Apache License, Version 2.0 (the "License");
 *you may not use this file except in compliance with the License.
 *You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 *Unless required by applicable law or agreed to in writing, software
 *distributed under the License is distributed on an "AS IS" BASIS,
 *WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *See the License for the specific language governing permissions and
 *limitations under the License.
 *
 *************************************************************
 * This project supports the blueCubs vision of giving back
 * to the community in exchange for free software!
 * More information on: http://www.bluecubs.org
 *************************************************************
 *
 * Name:            XincoAdminServlet
 *
 * Description:     administration servlet
 *
 * Original Author: Alexander Manes
 * Date:            2004
 *
 * Modifications:
 * 
 * Who?             When?             What?
 * -                -                 -
 *
 *************************************************************
 */

package com.bluecubs.xinco.server;

import java.sql.*;
import java.io.*;
import java.util.Vector;

import javax.servlet.*;
import javax.servlet.http.*;

import com.bluecubs.xinco.core.*;
import com.bluecubs.xinco.core.server.*;
import com.bluecubs.xinco.index.XincoIndexer;

public class XincoAdminServlet extends HttpServlet {
    
    /** Initializes the servlet.
     */
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
    }
    
    /** Destroys the servlet.
     */
    public void destroy() {
    }
    
    /** Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
     * @param request servlet request
     * @param response servlet response
     */
    protected synchronized void processRequest(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        
        XincoDBManager dbm;
        String global_error_message = "";
        
		int i = 0, j = 0;
//		XincoCoreUserServer user;
		XincoCoreUserServer temp_user;
		XincoCoreGroupServer temp_group;
		String current_location = "";
		String current_location_desc = "";
		int current_user_selection = 0;
		int current_group_selection = 0;
		int status = 0;
		String error_message = "";

		HttpSession session = request.getSession(true);
		
		//start output
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        
		//connect to db
		try {
			dbm = new XincoDBManager();
		} catch (Exception e) {
			global_error_message = "" + e.toString() + " | NOTE: Check whether the web application's configuration file (e.g. context.xml/xinco.xml) has been deployed correctly!";
			out.println(global_error_message);
			return;
		}

		//do processing of requests
		
		//check login status
		if (session.getAttribute("XincoAdminServlet.status") == null) {
			status = 0;
			session.setAttribute("XincoAdminServlet.status", new Integer(status));
			current_location = "MainLogin";
			session.setAttribute("XincoAdminServlet.current_location", current_location);
			current_location_desc = "Login Dialog";
			session.setAttribute("XincoAdminServlet.current_location_desc", current_location_desc);
			current_user_selection = 0;
			session.setAttribute("XincoAdminServlet.current_user_selection", new Integer(current_user_selection));
			current_group_selection = 0;
			session.setAttribute("XincoAdminServlet.current_group_selection", new Integer(current_group_selection));
		} else {
			status = ((Integer)session.getAttribute("XincoAdminServlet.status")).intValue();
			current_location = ((String)session.getAttribute("XincoAdminServlet.current_location"));
			current_location_desc = ((String)session.getAttribute("XincoAdminServlet.current_location_desc"));
			current_user_selection = ((Integer)session.getAttribute("XincoAdminServlet.current_user_selection")).intValue();
			current_group_selection = ((Integer)session.getAttribute("XincoAdminServlet.current_group_selection")).intValue();
			if (status == 0) {
				current_location = "MainLogin";
				session.setAttribute("XincoAdminServlet.current_location", current_location);
				current_location_desc = "Login Dialog";
				session.setAttribute("XincoAdminServlet.current_location_desc", current_location_desc);
				current_user_selection = 0;
				session.setAttribute("XincoAdminServlet.current_user_selection", new Integer(current_user_selection));
				current_group_selection = 0;
				session.setAttribute("XincoAdminServlet.current_group_selection", new Integer(current_group_selection));
			}
		}

		//do login
		if (request.getParameter("DialogLoginSubmit") != null) {
			try {
				try {
					temp_user = new XincoCoreUserServer(request.getParameter("DialogLoginUsername"), request.getParameter("DialogLoginPassword"), dbm);
				} catch (Exception loginex) {
					throw new XincoException("Login failed! Username and/or Password may be incorrect!");
				}
				//check for admin group
				for (i=0;i<temp_user.getXinco_core_groups().size();i++) {
					if (((XincoCoreGroup)temp_user.getXinco_core_groups().elementAt(i)).getId() == 1) {
						break;
					}
				}
				if (i == temp_user.getXinco_core_groups().size()) {
					throw new XincoException("You are not a member of the Main Admin Group (ID = 1)!");
				}
				current_user_selection = temp_user.getId();
				session.setAttribute("XincoAdminServlet.current_user_selection", new Integer(current_user_selection));
				status = 1;
				session.setAttribute("XincoAdminServlet.status", new Integer(status));
				current_location = "MainMenu";
				session.setAttribute("XincoAdminServlet.current_location", current_location);
				current_location_desc = "Main Menu";
				session.setAttribute("XincoAdminServlet.current_location_desc", current_location_desc);
			} catch (Exception e) {
				error_message = "[" + global_error_message + " | " + e.toString() + "]";
				status = 0;
				session.setAttribute("XincoAdminServlet.status", new Integer(status));
			}
		}
		//switch to overview
		if (request.getParameter("MenuMainOverview") != null) {
			current_location = "MainMenu";
			session.setAttribute("XincoAdminServlet.current_location", current_location);
			current_location_desc = "Main Menu";
			session.setAttribute("XincoAdminServlet.current_location_desc", current_location_desc);
		}
		//switch to user admin
		if (request.getParameter("MenuMainAdminUsers") != null) {
			current_location = "UserAdmin";
			session.setAttribute("XincoAdminServlet.current_location", current_location);
			current_location_desc = "User Administration";
			session.setAttribute("XincoAdminServlet.current_location_desc", current_location_desc);
		}
		//switch to group admin
		if (request.getParameter("MenuMainAdminGroups") != null) {
			current_location = "GroupAdmin";
			session.setAttribute("XincoAdminServlet.current_location", current_location);
			current_location_desc = "Group Administration";
			session.setAttribute("XincoAdminServlet.current_location_desc", current_location_desc);
		}
		//switch to user profile modification
		if (request.getParameter("MenuMainEditUserProfile") != null) {
			current_location = "UserProfileEdit";
			session.setAttribute("XincoAdminServlet.current_location", current_location);
			current_location_desc = "User Profile";
			session.setAttribute("XincoAdminServlet.current_location_desc", current_location_desc);
		}
		//switch to overview
		if (request.getParameter("MenuMainEmptyTrash") != null) {
			current_location = "MainMenu";
			session.setAttribute("XincoAdminServlet.current_location", current_location);
			current_location_desc = "Main Menu";
			session.setAttribute("XincoAdminServlet.current_location_desc", current_location_desc);
		}
		//switch to index rebuilt
		if (request.getParameter("MenuMainRebuildIndex") != null) {
			current_location = "RebuildIndex";
			session.setAttribute("XincoAdminServlet.current_location", current_location);
			current_location_desc = "Rebuild Index";
			session.setAttribute("XincoAdminServlet.current_location_desc", current_location_desc);
		}
		//lock user
		if (request.getParameter("DialogAdminUsersLock") != null) {
			//main admin cannot be locked
			if (!(Integer.parseInt(request.getParameter("DialogAdminUsersLock")) == 1)) {
				try {
					i = Integer.parseInt(request.getParameter("DialogAdminUsersLock"));
					temp_user = new XincoCoreUserServer(i, dbm);
					temp_user.setStatus_number(2);
					temp_user.write2DB(dbm);
				} catch (Exception e) {
				}
			} else {
				error_message = "This User cannot be locked!";
			}
		}
		//unlock user
		if (request.getParameter("DialogAdminUsersUnlock") != null) {
			try {
				i = Integer.parseInt(request.getParameter("DialogAdminUsersUnlock"));
				temp_user = new XincoCoreUserServer(i, dbm);
				temp_user.setStatus_number(1);
				temp_user.write2DB(dbm);
			} catch (Exception e) {
			}
		}
		//reset user's password
		if (request.getParameter("DialogAdminUsersResetPW") != null) {
			try {
				i = Integer.parseInt(request.getParameter("DialogAdminUsersResetPW"));
				temp_user = new XincoCoreUserServer(i, dbm);
				temp_user.setUserpassword("123456");
				temp_user.write2DB(dbm);
			} catch (Exception e) {
			}
		}
		//create new user
		if (request.getParameter("DialogNewUserSubmit") != null) {
			try {
				temp_user = new XincoCoreUserServer(0, request.getParameter("DialogNewUserUsername"), request.getParameter("DialogNewUserPassword"), request.getParameter("DialogNewUserLastname"), request.getParameter("DialogNewUserFirstname"), request.getParameter("DialogNewUserEmail"), 1, dbm);
				temp_group = new XincoCoreGroupServer(2, dbm);
				temp_user.getXinco_core_groups().addElement(temp_group);
				temp_user.write2DB(dbm);
			} catch (Exception e) {
			}
		}
		//create new group
		if (request.getParameter("DialogNewGroupSubmit") != null) {
			try {
				temp_group = new XincoCoreGroupServer(0, request.getParameter("DialogNewGroupName"), 1);
				temp_group.write2DB(dbm);
			} catch (Exception e) {
			}
		}
		//switch to group modification
		if (request.getParameter("DialogAdminGroupsSelect") != null) {
			current_group_selection = Integer.parseInt(request.getParameter("DialogAdminGroupsSelect"));
			session.setAttribute("XincoAdminServlet.current_group_selection", new Integer(current_group_selection));
			current_location = "GroupAdminSingle";
			session.setAttribute("XincoAdminServlet.current_location", current_location);
			current_location_desc = "Specific Group Administration";
			session.setAttribute("XincoAdminServlet.current_location_desc", current_location_desc);
		}
		//modify group
		if (request.getParameter("DialogEditGroupSubmit") != null) {
			try {
				temp_group = new XincoCoreGroupServer(current_group_selection, dbm);
				temp_group.setDesignation(request.getParameter("DialogEditGroupName"));
				temp_group.write2DB(dbm);
			} catch (Exception e) {
			}
		}
		//remove user from group
		if (request.getParameter("DialogEditGroupRemoveUser") != null) {
			//main admin always is admin and everyone is a regular user
			if (!(((current_group_selection == 1) && (Integer.parseInt(request.getParameter("DialogEditGroupRemoveUser")) == 1)) || (current_group_selection == 2))) {
				try {
					Statement stmt = dbm.con.createStatement();
					stmt.executeUpdate("DELETE FROM xinco_core_user_has_xinco_core_group WHERE xinco_core_user_id=" + Integer.parseInt(request.getParameter("DialogEditGroupRemoveUser")));
					stmt.close();
					dbm.con.commit();
				} catch (Exception e) {
					try {
						dbm.con.rollback();
					} catch (Exception rbe) {
					}
				}
			} else {
				error_message = "You cannot remove this User from the Group! e.g. ALL Users belong to Main User Group (ID = 2)";
			}
		}
		//add user to group
		if (request.getParameter("DialogEditGroupAddUser") != null) {
			try {
				Statement stmt = dbm.con.createStatement();
				stmt.executeUpdate("INSERT INTO xinco_core_user_has_xinco_core_group VALUES (" + Integer.parseInt(request.getParameter("DialogEditGroupAddUser")) + ", " + current_group_selection + ", " + "1)");
				stmt.close();
				dbm.con.commit();
			} catch (Exception e) {
				try {
					dbm.con.rollback();
				} catch (Exception rbe) {
				}
			}
		}
		//modify user profile
		if (request.getParameter("DialogEditUserProfileSubmit") != null) {
			try {
				temp_user = new XincoCoreUserServer(Integer.parseInt(request.getParameter("DialogEditUserProfileID")), dbm);
				temp_user.setUsername(request.getParameter("DialogEditUserProfileUsername"));
				temp_user.setUserpassword(request.getParameter("DialogEditUserProfilePassword"));
				temp_user.setName(request.getParameter("DialogEditUserProfileLastname"));
				temp_user.setFirstname(request.getParameter("DialogEditUserProfileFirstname"));
				temp_user.setEmail(request.getParameter("DialogEditUserProfileEmail"));
				temp_user.write2DB(dbm);
			} catch (Exception e) {
			}
		}
		//empty trash
		if (request.getParameter("MenuMainEmptyTrash") != null) {
			try {
				(new XincoCoreNodeServer(2, dbm)).deleteFromDB(false, dbm);
			} catch (Exception e) {
			}
		}
		//do logout
		if (request.getParameter("MenuMainLogout") != null) {
			try {
				session.removeAttribute("XincoAdminServlet.user");
				status = 0;
				session.setAttribute("XincoAdminServlet.status", new Integer(status));
			} catch (Exception e) {
				status = 0;
				session.setAttribute("XincoAdminServlet.status", new Integer(status));
			}
		}

        //show header
        out.println("<html>");
        out.println("<head>");
        out.println("<title>XincoAdmin</title>");
		out.println("<link rel=\"stylesheet\" href=\"xincostyle.css\" type=\"text/css\"/>");
        out.println("</head>");
		out.println("<body>");
		out.println("<center>");
		out.println("<span class=\"text\">");
                
		out.println("");

		//if not logged in
		if (status == 0) {
		
			//show welcome message
			out.println("<br><img src=\"blueCubs.gif\" border=\"0\"/>");
			out.println("<br><span class=\"bigtext\">XincoAdmin</span><br><br>");
			
			//show login dialog
			out.println("<form action=\"XincoAdmin\" method=\"post\">");
			out.println("<table border=\"0\" cellspacing=\"10\" cellpadding=\"0\">");
			out.println("<tr>");
			out.println("<td class=\"text\">User:</td>");
			out.println("<td class=\"text\"><input type=\"text\" name=\"DialogLoginUsername\" size=\"40\"/></td>");
			out.println("</tr>");
			out.println("<tr>");
			out.println("<td class=\"text\">Password:</td>");
			out.println("<td class=\"text\"><input type=\"password\" name=\"DialogLoginPassword\" size=\"40\"/></td>");
			out.println("</tr>");
			if (error_message.compareTo("") != 0) {
				out.println("<tr>");
				out.println("<td class=\"text\">&nbsp;</td>");
				out.println("<td class=\"text\">" + error_message + "</td>");
				out.println("</tr>");
			}
			out.println("<tr>");
			out.println("<td class=\"text\">&nbsp;</td>");
			out.println("<td class=\"text\"><input type=\"submit\" name=\"DialogLoginSubmit\" value=\"Login\"/></td>");
			out.println("</tr>");
			out.println("</table>");
			out.println("</form>");
		
		} else {
			
			//show main menu
			out.println("<br>");
			out.println("<table border=\"0\" width=\"750\" cellspacing=\"10\" cellpadding=\"0\">");
			out.println("<tr>");
			out.println("<td class=\"text\" rowspan=\"3\"><img src=\"blueCubsSmall.gif\" border=\"0\"/></td>");
			out.println("<td class=\"bigtext\" colspan=\"5\">XincoAdmin</td>");
			out.println("</tr>");
			out.println("<tr>");
			out.println("<td class=\"text\" colspan=\"5\">Location: " + current_location_desc + "</td>");
			out.println("</tr>");
			out.println("<tr>");
			out.println("<td class=\"text\"><a href=\"XincoAdmin?MenuMainOverview=Overview\" class=\"link\">Overview</a></td>");
			out.println("<td class=\"text\">|</td>");
			out.println("<td class=\"text\"><a href=\"XincoAdmin?MenuMainAdminUsers=AdminUsers\" class=\"link\">Edit Users</a></td>");
			out.println("<td class=\"text\">|</td>");
			out.println("<td class=\"text\"><a href=\"XincoAdmin?MenuMainAdminGroups=AdminGroups\" class=\"link\">Edit Groups</a></td>");
			out.println("<td class=\"text\">|</td>");
			out.println("<td class=\"text\"><a href=\"XincoAdmin?MenuMainEditUserProfile=EditProfile\" class=\"link\">Edit Profile</a></td>");
			out.println("<td class=\"text\">|</td>");
			out.println("<td class=\"text\"><a href=\"XincoAdmin?MenuMainEmptyTrash=EmptyTrash\" class=\"link\">Empty Trash</a></td>");
			out.println("<td class=\"text\">|</td>");
			out.println("<td class=\"text\"><a href=\"XincoAdmin?MenuMainRebuildIndex=RebuildIndex\" class=\"link\">Rebuild Index</a></td>");
			out.println("<td class=\"text\">|</td>");
			out.println("<td class=\"text\"><a href=\"XincoAdmin?MenuMainLogout=Logout\" class=\"link\">Logout</a></td>");
			out.println("</tr>");
			out.println("</table>");
			out.println("<br><br>");
		
			if (current_location.compareTo("MainMenu") == 0) {

				//show overview
				out.println("<table border=\"0\" width=\"750\" cellspacing=\"10\" cellpadding=\"0\">");
				out.println("<tr>");
				out.println("<td class=\"bigtext\">Overview</td>");
				out.println("<td class=\"text\">While the xinco Document Management System is usually accessed via XincoExplorer, this tool is meant for administrating users' and groups' access rights to the system.</td>");
				out.println("</tr>");
				out.println("<tr>");
				out.println("<td class=\"bigtext\">User Administration</td>");
				out.println("<td class=\"text\">View a list of all users, lock/unlock them and add new users to the system.</td>");
				out.println("</tr>");
				out.println("<tr>");
				out.println("<td class=\"bigtext\">Group Administration</td>");
				out.println("<td class=\"text\">View a list of all groups and assign/remove users to/from these groups.</td>");
				out.println("</tr>");
				out.println("<tr>");
				out.println("<td class=\"bigtext\">User Profile</td>");
				out.println("<td class=\"text\">Keep your User Profile up-to-date and change your password periodically to prevent abuse.</td>");
				out.println("</tr>");
				out.println("<tr>");
				out.println("<td class=\"bigtext\">Empty Trash</td>");
				out.println("<td class=\"text\">Permanently deletes all data and subfolders in folder Trash (ID=2).</td>");
				out.println("</tr>");
				out.println("<tr>");
				out.println("<td class=\"bigtext\">Rebuild Index</td>");
				out.println("<td class=\"text\">You must rebuild the index after upgrading xinco DMS or after executing one of the database translation scripts!<br>Periodically rebuilding the index will increase performance and stability.<br>(NOT recommended while users can access the xinco DMS server!)</td>");
				out.println("</tr>");
				out.println("</table>");

			}
			
			if (current_location.compareTo("UserAdmin") == 0) {

				//show new user dialog
				out.println("<form action=\"XincoAdmin\" method=\"post\">");
				out.println("<table border=\"0\" cellspacing=\"10\" cellpadding=\"0\">");
				out.println("<tr>");
				out.println("<td class=\"text\">Username:</td>");
				out.println("<td class=\"text\"><input type=\"text\" name=\"DialogNewUserUsername\" size=\"40\"/></td>");
				out.println("</tr>");
				out.println("<tr>");
				out.println("<td class=\"text\">Password:</td>");
				out.println("<td class=\"text\"><input type=\"password\" name=\"DialogNewUserPassword\" size=\"40\"/></td>");
				out.println("</tr>");
				out.println("<tr>");
				out.println("<td class=\"text\">Firstname:</td>");
				out.println("<td class=\"text\"><input type=\"text\" name=\"DialogNewUserFirstname\" size=\"40\"/></td>");
				out.println("</tr>");
				out.println("<tr>");
				out.println("<td class=\"text\">Lastname:</td>");
				out.println("<td class=\"text\"><input type=\"text\" name=\"DialogNewUserLastname\" size=\"40\"/></td>");
				out.println("</tr>");
				out.println("<tr>");
				out.println("<td class=\"text\">Email:</td>");
				out.println("<td class=\"text\"><input type=\"text\" name=\"DialogNewUserEmail\" size=\"40\"/></td>");
				out.println("</tr>");
				out.println("<tr>");
				out.println("<td class=\"text\">&nbsp;</td>");
				out.println("<td class=\"text\"><input type=\"submit\" name=\"DialogNewUserSubmit\" value=\"Add User\"/></td>");
				out.println("</tr>");
				out.println("</table>");
				out.println("</form>");

				//show user list
				out.println("<table border=\"0\" width=\"750\" cellspacing=\"10\" cellpadding=\"0\">");
				if (error_message.compareTo("") != 0) {
					out.println("<tr>");
					out.println("<td class=\"text\" colspan=\"6\">" + error_message + "</td>");
					out.println("</tr>");
				}
				out.println("<tr>");
				out.println("<td class=\"bigtext\">ID:</td>");
				out.println("<td class=\"bigtext\">Username:</td>");
				out.println("<td class=\"bigtext\">Firstname:</td>");
				out.println("<td class=\"bigtext\">Lastname:</td>");
				out.println("<td class=\"bigtext\">EMail:</td>");
				out.println("<td class=\"bigtext\">&nbsp;</td>");
				out.println("</tr>");

				Vector allusers = XincoCoreUserServer.getXincoCoreUsers(dbm);
				for (i=0;i<allusers.size();i++) {
					out.println("<tr>");
					out.println("<td class=\"text\">" + ((XincoCoreUserServer)allusers.elementAt(i)).getId() + "</td>");
					out.println("<td class=\"text\">" + ((XincoCoreUserServer)allusers.elementAt(i)).getUsername() + "</td>");
					out.println("<td class=\"text\">" + ((XincoCoreUserServer)allusers.elementAt(i)).getFirstname() + "</td>");
					out.println("<td class=\"text\">" + ((XincoCoreUserServer)allusers.elementAt(i)).getName() + "</td>");
					out.println("<td class=\"text\">" + ((XincoCoreUserServer)allusers.elementAt(i)).getEmail() + "</td>");
					if (((XincoCoreUserServer)allusers.elementAt(i)).getStatus_number() == 1) {
						out.println("<td class=\"text\"><a href=\"XincoAdmin?DialogAdminUsersLock=" + ((XincoCoreUserServer)allusers.elementAt(i)).getId() + "\" class=\"link\">[Lock]</a>&nbsp;<a href=\"XincoAdmin?DialogAdminUsersResetPW=" + ((XincoCoreUserServer)allusers.elementAt(i)).getId() + "\" class=\"link\">[Reset Password*]</a></td>");
					}
					if (((XincoCoreUserServer)allusers.elementAt(i)).getStatus_number() == 2) {
						out.println("<td class=\"text\"><b>Locked!</b> <a href=\"XincoAdmin?DialogAdminUsersUnlock=" + ((XincoCoreUserServer)allusers.elementAt(i)).getId() + "\" class=\"link\">[Unlock]</a>&nbsp;<a href=\"XincoAdmin?DialogAdminUsersResetPW=" + ((XincoCoreUserServer)allusers.elementAt(i)).getId() + "\" class=\"link\">[Reset Password*]</a></td>");
					}
					out.println("</tr>");
				}
	        
				out.println("<tr>");
				out.println("<td colspan=\"6\" class=\"text\">&nbsp;</td>");
				out.println("</tr>");
				out.println("<tr>");
				out.println("<td colspan=\"6\" class=\"text\">* The user's password will be reset to \"123456\"!</td>");
				out.println("</tr>");
				out.println("</table>");

			}
			
			if (current_location.compareTo("GroupAdmin") == 0) {

				//show new group dialog
				out.println("<form action=\"XincoAdmin\" method=\"post\">");
				out.println("<table border=\"0\" cellspacing=\"10\" cellpadding=\"0\">");
				out.println("<tr>");
				out.println("<td class=\"text\">Name:</td>");
				out.println("<td class=\"text\"><input type=\"text\" name=\"DialogNewGroupName\" size=\"40\"/></td>");
				out.println("</tr>");
				out.println("<tr>");
				out.println("<td class=\"text\">&nbsp;</td>");
				out.println("<td class=\"text\"><input type=\"submit\" name=\"DialogNewGroupSubmit\" value=\"Add Group\"/></td>");
				out.println("</tr>");
				out.println("</table>");
				out.println("</form>");

				//show group list
				out.println("<table border=\"0\" width=\"750\" cellspacing=\"10\" cellpadding=\"0\">");
				out.println("<tr>");
				out.println("<td class=\"bigtext\">ID:</td>");
				out.println("<td class=\"bigtext\">Name:</td>");
				out.println("<td class=\"bigtext\">&nbsp;</td>");
				out.println("</tr>");

				Vector allgroups = XincoCoreGroupServer.getXincoCoreGroups(dbm);
				for (i=0;i<allgroups.size();i++) {
					out.println("<tr>");
					out.println("<td class=\"text\">" + ((XincoCoreGroupServer)allgroups.elementAt(i)).getId() + "</td>");
					out.println("<td class=\"text\">" + ((XincoCoreGroupServer)allgroups.elementAt(i)).getDesignation() + "</td>");
					out.println("<td class=\"text\"><a href=\"XincoAdmin?DialogAdminGroupsSelect=" + ((XincoCoreGroupServer)allgroups.elementAt(i)).getId() + "\" class=\"link\">[Edit]</a></td>");
					out.println("</tr>");
				}
	        
				out.println("</table>");

			}
			
			if (current_location.compareTo("GroupAdminSingle") == 0) {

				//show group modification dialog
				try {
					temp_group = new XincoCoreGroupServer(current_group_selection, dbm);
					out.println("<form action=\"XincoAdmin\" method=\"post\">");
					out.println("<table border=\"0\" cellspacing=\"10\" cellpadding=\"0\">");
					out.println("<tr>");
					out.println("<td class=\"text\">Name:</td>");
					out.println("<td class=\"text\"><input type=\"text\" name=\"DialogEditGroupName\" size=\"40\" value=\"" + temp_group.getDesignation() + "\"/></td>");
					out.println("</tr>");
					out.println("<tr>");
					out.println("<td class=\"text\">&nbsp;</td>");
					out.println("<td class=\"text\"><input type=\"hidden\" name=\"DialogEditGroupID\" value=\"" + current_group_selection + "\"/><input type=\"submit\" name=\"DialogEditGroupSubmit\" value=\"Save!\"/></td>");
					out.println("</tr>");
					out.println("</table>");
					out.println("</form>");
				} catch (Exception e) {
				}

				//show user list
				Vector allusers = XincoCoreUserServer.getXincoCoreUsers(dbm);
				boolean member_of_group = false;

				out.println("<table border=\"0\" width=\"750\" cellspacing=\"10\" cellpadding=\"0\">");

				if (error_message.compareTo("") != 0) {
					out.println("<tr>");
					out.println("<td class=\"text\" colspan=\"6\">" + error_message + "</td>");
					out.println("</tr>");
				}
				out.println("<tr>");
				out.println("<td class=\"bigtext\" colspan=\"6\">Members of this Group:</td>");
				out.println("</tr>");
				out.println("<tr>");
				out.println("<td class=\"bigtext\">ID:</td>");
				out.println("<td class=\"bigtext\">Username:</td>");
				out.println("<td class=\"bigtext\">Firstname:</td>");
				out.println("<td class=\"bigtext\">Lastname:</td>");
				out.println("<td class=\"bigtext\">EMail:</td>");
				out.println("<td class=\"bigtext\">&nbsp;</td>");
				out.println("</tr>");
				for (i=0;i<allusers.size();i++) {
					member_of_group = false;
					for (j=0;j<((XincoCoreUserServer)allusers.elementAt(i)).getXinco_core_groups().size();j++) {
						if (((XincoCoreGroupServer)((XincoCoreUserServer)allusers.elementAt(i)).getXinco_core_groups().elementAt(j)).getId() == current_group_selection) {
							member_of_group = true;
							break;
						}
					}
					if (member_of_group) {
						out.println("<tr>");
						out.println("<td class=\"text\">" + ((XincoCoreUserServer)allusers.elementAt(i)).getId() + "</td>");
						out.println("<td class=\"text\">" + ((XincoCoreUserServer)allusers.elementAt(i)).getUsername() + "</td>");
						out.println("<td class=\"text\">" + ((XincoCoreUserServer)allusers.elementAt(i)).getFirstname() + "</td>");
						out.println("<td class=\"text\">" + ((XincoCoreUserServer)allusers.elementAt(i)).getName() + "</td>");
						out.println("<td class=\"text\">" + ((XincoCoreUserServer)allusers.elementAt(i)).getEmail() + "</td>");
						out.println("<td class=\"text\"><a href=\"XincoAdmin?DialogEditGroupRemoveUser=" + ((XincoCoreUserServer)allusers.elementAt(i)).getId() + "\" class=\"link\">[Remove from Group]</a></td>");
						out.println("</tr>");
					}
				}
	        
				out.println("<tr>");
				out.println("<td class=\"bigtext\" colspan=\"6\">&nbsp;</td>");
				out.println("</tr>");
				out.println("<tr>");
				out.println("<td class=\"bigtext\" colspan=\"6\">Users who are NOT member of this Group:</td>");
				out.println("</tr>");
				out.println("<tr>");
				out.println("<td class=\"bigtext\">ID:</td>");
				out.println("<td class=\"bigtext\">Username:</td>");
				out.println("<td class=\"bigtext\">Firstname:</td>");
				out.println("<td class=\"bigtext\">Lastname:</td>");
				out.println("<td class=\"bigtext\">EMail:</td>");
				out.println("<td class=\"bigtext\">&nbsp;</td>");
				out.println("</tr>");
				for (i=0;i<allusers.size();i++) {
					member_of_group = false;
					for (j=0;j<((XincoCoreUserServer)allusers.elementAt(i)).getXinco_core_groups().size();j++) {
						if (((XincoCoreGroupServer)((XincoCoreUserServer)allusers.elementAt(i)).getXinco_core_groups().elementAt(j)).getId() == current_group_selection) {
							member_of_group = true;
							break;
						}
					}
					if (!member_of_group) {
						out.println("<tr>");
						out.println("<td class=\"text\">" + ((XincoCoreUserServer)allusers.elementAt(i)).getId() + "</td>");
						out.println("<td class=\"text\">" + ((XincoCoreUserServer)allusers.elementAt(i)).getUsername() + "</td>");
						out.println("<td class=\"text\">" + ((XincoCoreUserServer)allusers.elementAt(i)).getFirstname() + "</td>");
						out.println("<td class=\"text\">" + ((XincoCoreUserServer)allusers.elementAt(i)).getName() + "</td>");
						out.println("<td class=\"text\">" + ((XincoCoreUserServer)allusers.elementAt(i)).getEmail() + "</td>");
						out.println("<td class=\"text\"><a href=\"XincoAdmin?DialogEditGroupAddUser=" + ((XincoCoreUserServer)allusers.elementAt(i)).getId() + "\" class=\"link\">[Add to Group]</a></td>");
						out.println("</tr>");
					}
				}
	        
				out.println("</table>");

			}
			
			if (current_location.compareTo("UserProfileEdit") == 0) {

				//show user profile modification dialog
				try {
					temp_user = new XincoCoreUserServer(current_user_selection, dbm);
					out.println("<form action=\"XincoAdmin\" method=\"post\">");
					out.println("<table border=\"0\" cellspacing=\"10\" cellpadding=\"0\">");
					out.println("<tr>");
					out.println("<td class=\"text\">Username:</td>");
					out.println("<td class=\"text\"><input type=\"text\" name=\"DialogEditUserProfileUsername\" value=\"" + temp_user.getUsername() + "\" size=\"40\"/></td>");
					out.println("</tr>");
					out.println("<tr>");
					out.println("<td class=\"text\">Password:</td>");
					//out.println("<td class=\"text\"><input type=\"password\" name=\"DialogEditUserProfilePassword\" value=\"" + temp_user.getUserpassword() + "\" size=\"40\"/></td>");
					out.println("<td class=\"text\"><input type=\"password\" name=\"DialogEditUserProfilePassword\" value=\"\" size=\"40\"/></td>");
					out.println("</tr>");
					out.println("<tr>");
					out.println("<td class=\"text\">Firstname:</td>");
					out.println("<td class=\"text\"><input type=\"text\" name=\"DialogEditUserProfileFirstname\" value=\"" + temp_user.getFirstname() + "\" size=\"40\"/></td>");
					out.println("</tr>");
					out.println("<tr>");
					out.println("<td class=\"text\">Lastname:</td>");
					out.println("<td class=\"text\"><input type=\"text\" name=\"DialogEditUserProfileLastname\" value=\"" + temp_user.getName() + "\" size=\"40\"/></td>");
					out.println("</tr>");
					out.println("<tr>");
					out.println("<td class=\"text\">Email:</td>");
					out.println("<td class=\"text\"><input type=\"text\" name=\"DialogEditUserProfileEmail\" value=\"" + temp_user.getEmail() + "\" size=\"40\"/></td>");
					out.println("</tr>");
					out.println("<tr>");
					out.println("<td class=\"text\">&nbsp;</td>");
					out.println("<td class=\"text\"><input type=\"hidden\" name=\"DialogEditUserProfileID\" value=\"" + current_user_selection + "\"/><input type=\"submit\" name=\"DialogEditUserProfileSubmit\" value=\"Save!\"/></td>");
					out.println("</tr>");
					out.println("</table>");
					out.println("</form>");
				} catch (Exception e) {
				}
				
			}

			if (current_location.compareTo("RebuildIndex") == 0) {

				//rebuild index and list status
				try {
					out.println("<table border=\"0\" width=\"750\" cellspacing=\"10\" cellpadding=\"0\">");
					out.println("<tr>");
					out.println("<td class=\"bigtext\" colspan=\"2\">Rebuilding Index...</td>");
					out.println("</tr>");
					out.println("<tr>");
					out.println("<td class=\"text\" colspan=\"2\">Rebuilding the index will take quite some time, please be patient.</td>");
					out.println("</tr>");
					out.println("<tr>");
					out.println("<td class=\"text\" colspan=\"2\">&nbsp;</td>");
					out.println("</tr>");

					//delete existing index
					File indexDirectory = null;
					File indexDirectoryFile = null;
					String[] indexDirectoryFileList = null;
					boolean index_directory_deleted = false;
					indexDirectory = new File(dbm.config.FileIndexPath);
					if (indexDirectory.exists()) {
						indexDirectoryFileList = indexDirectory.list();
						for (i=0;i<indexDirectoryFileList.length;i++) {
							indexDirectoryFile = new File(dbm.config.FileIndexPath + indexDirectoryFileList[i]);
							indexDirectoryFile.delete();
						}
						index_directory_deleted = indexDirectory.delete();
						out.println("<tr>");
						out.println("<td class=\"text\"><b>Deleting existing Index...</b></td>");
						if (index_directory_deleted) {
							out.println("<td class=\"text\">OK!</td>");
						} else {
							out.println("<td class=\"text\">Failed!</td>");
						}
						out.println("</tr>");
					}
					out.println("<tr>");
					out.println("<td class=\"text\">&nbsp;</td>");
					out.println("<td class=\"text\">&nbsp;</td>");
					out.println("</tr>");
					
					//select all data
					out.println("<tr>");
					out.println("<td class=\"text\"><b>Data (by Designation)</b></td>");
					out.println("<td class=\"text\"><b>Indexing Status</b></td>");
					out.println("</tr>");
					XincoCoreDataServer xdata_temp = null;
					boolean index_result = false;
					Statement stmt = dbm.con.createStatement();
					ResultSet rs = stmt.executeQuery("SELECT id FROM xinco_core_data ORDER BY designation");
					while (rs.next()) {
						xdata_temp = new XincoCoreDataServer(rs.getInt("id"), dbm);
						index_result = XincoIndexer.indexXincoCoreData(xdata_temp, true, dbm);
						out.println("<tr>");
						out.println("<td class=\"text\">" + xdata_temp.getDesignation() + "</td>");
						if (index_result) {
							out.println("<td class=\"text\">OK!</td>");
						} else {
							out.println("<td class=\"text\">Failed!</td>");
						}
						out.println("</tr>");
						out.flush();
					}
					stmt.close();
					
					//optimize index
					index_result = XincoIndexer.optimizeIndex(dbm);
					out.println("<tr>");
					out.println("<td class=\"text\">Optimizing index...</td>");
					if (index_result) {
						out.println("<td class=\"text\">OK!</td>");
					} else {
						out.println("<td class=\"text\">Failed!</td>");
					}
					out.println("</tr>");
					out.flush();

					out.println("</table>");

				} catch (Exception e) {
					out.println("</table>");
				}
				
			}

		}

		//show footer
		out.println("<br><br><br>");
		out.println("<table border=\"0\" cellspacing=\"10\" cellpadding=\"0\">");
		out.println("<tr>");
		out.println("<td class=\"text\">&nbsp;</td>");
		out.println("<td class=\"text\">&copy; 2004-2006, <a href=\"http://www.bluecubs.com\" target=\"_blank\" class=\"link\">blueCubs.com</a> and <a href=\"http://www.xinco.org\" target=\"_blank\" class=\"link\">xinco.org</a></td>");
		out.println("</tr>");
		out.println("</table>");

		out.println("</span>");
		out.println("</center>");
		out.println("</body>");
        out.println("</html>");

        out.close();

		//close db connection
		try {
			dbm.con.close();
		} catch (Exception e) {
			global_error_message = global_error_message + e.toString();
		}
		
    }
    
    /** Handles the HTTP <code>GET</code> method.
     * @param request servlet request
     * @param response servlet response
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        processRequest(request, response);
    }
    
    /** Handles the HTTP <code>POST</code> method.
     * @param request servlet request
     * @param response servlet response
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        processRequest(request, response);
    }
    
    /** Returns a short description of the servlet.
     */
    public String getServletInfo() {
        return "Servlet of xinco";
    }
    
}
