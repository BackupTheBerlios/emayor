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
 * Name:            XincoPublisherServlet
 *
 * Description:     publisher servlet
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

import java.io.*;
import java.sql.ResultSet;
import java.sql.Statement;
import javax.servlet.*;
import javax.servlet.http.*;
import com.bluecubs.xinco.core.*;
import com.bluecubs.xinco.add.*;
import com.bluecubs.xinco.core.server.*;

public class XincoPublisherServlet extends HttpServlet {
    
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
        
        int i = 0;
        int j = 0;
		String request_path;
		String request_path_array[];
    	XincoDBManager dbm;
        boolean fileDownload = false;
        int core_data_id = 0;
        XincoCoreDataServer xcd = null;
        XincoCoreDataTypeAttribute xcdta = null;
        XincoAddAttribute xaa = null;
        boolean printList = false;
        boolean browseFolder = false;
        String temp_url = "";
        String temp_server_url = "";
        boolean isPublic = false;
        
		//connect to db
		try {
			dbm = new XincoDBManager();
		} catch (Exception e) {
			//start output
	        response.setContentType("text/html");
	        PrintWriter out = response.getWriter();
			out.println(e.toString());
			return;
		}

		//get requested data
		if (request.getParameter("MainMenu") == null) {
			request_path = request.getPathInfo();
			if (request_path != null) {
				request_path_array = request_path.split("/");
				if (!(request_path_array.length > 1)) {
					core_data_id = 0;
				} else {
					try {
						core_data_id = Integer.parseInt(request_path_array[1]);
						xcd = new XincoCoreDataServer(core_data_id, dbm);
						isPublic = false;
						//check status (5 = published)
						if (xcd.getStatus_number() == 5) {
							isPublic = true;
						} else {
							//check read permission for group "public"
							for (i=0;i<xcd.getXinco_core_acl().size();i++) {
								if ((((XincoCoreACE)xcd.getXinco_core_acl().elementAt(i)).getXinco_core_group_id() == 3)
										&& ((XincoCoreACE)xcd.getXinco_core_acl().elementAt(i)).isRead_permission()) {
									isPublic = true;
									break;
								}
							}
						}
						if (!isPublic) {
							core_data_id = -1;
						}
					} catch (Exception e) {
						core_data_id = -1;
					}
				}
			} else {
				core_data_id = 0;
			}
		} else {
			core_data_id = 0;
			if (request.getParameter("MainMenu").compareTo("list") == 0) {
				printList = true;
			} else if ((request.getParameter("MainMenu").compareTo("browse") == 0)) {
				browseFolder = true;
			}
		}
		//check data type
		if (core_data_id > 0) {
			if (xcd.getXinco_core_data_type().getId() == 1) {
				fileDownload = true;
			} else {
				fileDownload = false;
			}
		}
		
		//generate specific output
		if (fileDownload) { // begin FILE output
			
	    	try {
	        	response.setContentType("unknown/unknown");
	        	OutputStream out = response.getOutputStream();
	        	
	    		FileInputStream in = new FileInputStream(dbm.config.FileRepositoryPath + core_data_id);
				byte[] buf = new byte[4096];
				int len;
	    		while ((len = in.read(buf)) > 0) {
	    			out.write(buf, 0, len);
	    		}
	    		in.close();
	    	} catch (Exception e) {
	    		System.out.println(e);
	    	}
	    	
	    	//end FILE output
			
		} else { // begin HTML output
			
			//start output
	        response.setContentType("text/html");
	        PrintWriter out = response.getWriter();
	        
	        //show header
	        out.println("<html>");
	        out.println("<head>");
	        if (core_data_id == 0) {
	        	out.println("<title>XincoPublisher</title>");
				out.println("<link rel=\"stylesheet\" href=\"xincostyle.css\" type=\"text/css\"/>");
	        }
	        if (core_data_id > 0) {
	        	out.println("<title>" +  xcd.getDesignation() + "</title>");
				out.println("<link rel=\"stylesheet\" href=\"../../xincostyle.css\" type=\"text/css\"/>");
	        }
	        out.println("</head>");
			out.println("<body>");
			out.println("<center>");
			out.println("<span class=\"text\">");
	                
			out.println("");
	
			//show main menu
			if (core_data_id == 0) {
				out.println("<br>");
				out.println("<table border=\"0\" width=\"750\" cellspacing=\"10\" cellpadding=\"0\">");
				out.println("<tr>");
				out.println("<td class=\"text\" width=\"100\"><img src=\"blueCubsSmall.gif\" border=\"0\"/></td>");
				out.println("<td class=\"bigtext\" width=\"650\">XincoPublisher</td>");
				out.println("</tr>");
				out.println("</table>");
				out.println("<br>");
				out.println("<table border=\"0\" cellspacing=\"10\" cellpadding=\"0\">");
				if (printList) {
					try {
						XincoCoreDataServer xdata_temp = null;
						Statement stmt = dbm.con.createStatement();
						ResultSet rs = stmt.executeQuery("SELECT DISTINCT xcd.id, xcd.designation FROM xinco_core_data xcd, xinco_core_ace xca WHERE xcd.id=xca.xinco_core_data_id AND (xcd.status_number=5 OR (xca.xinco_core_group_id=3 AND xca.read_permission=1)) ORDER BY xcd.designation");
						while (rs.next()) {
							xdata_temp = new XincoCoreDataServer(rs.getInt("id"), dbm);
							temp_server_url = request.getRequestURL().toString();
							temp_url = "";
							//file = 1
							if (xdata_temp.getXinco_core_data_type().getId() == 1) {
								temp_url = ((XincoAddAttribute)xdata_temp.getXinco_add_attributes().elementAt(0)).getAttrib_varchar();
							} else {
								temp_url = xdata_temp.getDesignation();
							}
							out.println("<tr>");
							out.println("<td class=\"text\">" + xdata_temp.getDesignation() + " (" + xdata_temp.getXinco_core_data_type().getDesignation() + " | " + xdata_temp.getXinco_core_language().getSign() + ")" + "</td>");
							out.println("<td class=\"text\"><a href=\"" + "XincoPublisher/" + xdata_temp.getId() + "/" + temp_url + "\" target=\"_blank\">" + temp_server_url + "/" + xdata_temp.getId() + "/" + temp_url + "</a></td>");
							out.println("</tr>");
							out.flush();
						}
						stmt.close();
					} catch (Exception sqle) {
					}
				} else if (browseFolder) {
					try {
						XincoCoreNodeServer xnode_temp = null;
						XincoCoreNodeServer xnode_temp2 = null;
						XincoCoreDataServer xdata_temp = null;
						String temp_path = null;
						String temp_path2 = null;
						int temp_xcn_id = 0;
						
						if (!(request.getParameter("FolderId") == null)) {
							temp_xcn_id = Integer.parseInt(request.getParameter("FolderId"));
							xnode_temp = new XincoCoreNodeServer(temp_xcn_id, dbm);
							//check read permission for group "public"
							isPublic = false;
							for (i=0;i<xnode_temp.getXinco_core_acl().size();i++) {
								if ((((XincoCoreACE)xnode_temp.getXinco_core_acl().elementAt(i)).getXinco_core_group_id() == 3)
										&& ((XincoCoreACE)xnode_temp.getXinco_core_acl().elementAt(i)).isRead_permission()) {
									isPublic = true;
									break;
								}
							}
							if (isPublic) {
								xnode_temp.fillXincoCoreNodes(dbm);
								xnode_temp.fillXincoCoreData(dbm);
								// print current path
								if (!(request.getParameter("Path") == null)) {
									temp_path = request.getParameter("Path");
									temp_path = new String(new sun.misc.BASE64Decoder().decodeBuffer(temp_path));
									out.println("<tr>");
									out.println("<td colspan=\"2\" class=\"text\"><b>Path:</b> " + temp_path + "</td>");
									out.println("</tr>");
									out.println("<tr>");
									out.println("<td colspan=\"2\" class=\"text\">&nbsp;</td>");
									out.println("</tr>");
									out.flush();
								} else {
									temp_path = null;
								}
								// list public folders
								out.println("<tr>");
								out.println("<td colspan=\"2\" class=\"text\"><b>Public Sub-Folders:</b></td>");
								out.println("</tr>");
								out.flush();
								for (i=0;i<xnode_temp.getXinco_core_nodes().size();i++) {
									xnode_temp2 = new XincoCoreNodeServer(((XincoCoreNodeServer)xnode_temp.getXinco_core_nodes().elementAt(i)).getId(), dbm);
									isPublic = false;
									//check read permission for group "public"
									for (j=0;j<xnode_temp2.getXinco_core_acl().size();j++) {
										if ((((XincoCoreACE)xnode_temp2.getXinco_core_acl().elementAt(j)).getXinco_core_group_id() == 3)
												&& ((XincoCoreACE)xnode_temp2.getXinco_core_acl().elementAt(j)).isRead_permission()) {
											isPublic = true;
											break;
										}
									}
									if (isPublic) {
										if (temp_path != null) {
											temp_path2 = temp_path + " / " + xnode_temp2.getDesignation() + " (" + xnode_temp2.getXinco_core_language().getSign() + ")";
											temp_path2 = new sun.misc.BASE64Encoder().encode(temp_path2.getBytes());
											temp_path2 = "&Path=" + temp_path2;
										} else {
											temp_path2 = "";
										}
										out.println("<tr>");
										out.println("<td class=\"text\">&nbsp;</td>");
										out.println("<td class=\"text\"><a href=\"" + "XincoPublisher?MainMenu=browse&FolderId=" + xnode_temp2.getId() + temp_path2 + "\">[" + xnode_temp2.getDesignation() + " (" + xnode_temp2.getXinco_core_language().getSign() + ")" + "]</a></td>");
										out.println("</tr>");
										out.flush();
									}
								}
								out.println("<tr>");
								out.println("<td colspan=\"2\" class=\"text\">&nbsp;</td>");
								out.println("</tr>");
								out.flush();
								// list public data
								out.println("<tr>");
								out.println("<td colspan=\"2\" class=\"text\"><b>Public Data:</b></td>");
								out.println("</tr>");
								out.flush();
								for (i=0;i<xnode_temp.getXinco_core_data().size();i++) {
									xdata_temp = new XincoCoreDataServer(((XincoCoreDataServer)xnode_temp.getXinco_core_data().elementAt(i)).getId(), dbm);
									isPublic = false;
									//check status (5 = published)
									if (xdata_temp.getStatus_number() == 5) {
										isPublic = true;
									} else {
										//check read permission for group "public"
										for (j=0;j<xdata_temp.getXinco_core_acl().size();j++) {
											if ((((XincoCoreACE)xdata_temp.getXinco_core_acl().elementAt(j)).getXinco_core_group_id() == 3)
													&& ((XincoCoreACE)xdata_temp.getXinco_core_acl().elementAt(j)).isRead_permission()) {
												isPublic = true;
												break;
											}
										}
									}
									if (isPublic) {
										temp_server_url = request.getRequestURL().toString();
										temp_url = "";
										//file = 1
										if (xdata_temp.getXinco_core_data_type().getId() == 1) {
											temp_url = ((XincoAddAttribute)xdata_temp.getXinco_add_attributes().elementAt(0)).getAttrib_varchar();
										} else {
											temp_url = xdata_temp.getDesignation();
										}
										out.println("<tr>");
										out.println("<td class=\"text\">" + xdata_temp.getDesignation() + " (" + xdata_temp.getXinco_core_data_type().getDesignation() + " | " + xdata_temp.getXinco_core_language().getSign() + ")" + "</td>");
										out.println("<td class=\"text\"><a href=\"" + "XincoPublisher/" + xdata_temp.getId() + "/" + temp_url + "\" target=\"_blank\">" + temp_server_url + "/" + xdata_temp.getId() + "/" + temp_url + "</a></td>");
										out.println("</tr>");
										out.flush();
									}
								}
							}
						}
					} catch (Exception sqle) {
					}
				} else {
					out.println("<tr>");
					out.println("<td class=\"text\" colspan=\"2\"><a href=\"XincoPublisher?MainMenu=list\" class=\"link\"><b>List</b></a> all publicly available data!</td>");
					out.println("</tr>");
					out.println("<tr>");
					out.println("<td class=\"text\" colspan=\"2\"><a href=\"XincoPublisher?MainMenu=browse&FolderId=1&Path=" + (new sun.misc.BASE64Encoder().encode((new String("xincoRoot")).getBytes())) + "\" class=\"link\"><b>Browse</b></a> the repository for publicly available data!</td>");
					out.println("</tr>");
				}
				out.println("<tr>");
				out.println("<td class=\"text\" colspan=\"2\">&nbsp;<br><br></td>");
				out.println("</tr>");
				out.println("<tr>");
				out.println("<td class=\"text\" colspan=\"2\"><b>HowTo:</b><br>When linking to published documents, use the following URL Syntax:<br><b>http://[server_name]:[port]/xinco/XincoPublisher/[id]/[designation|filename]</b></td>");
				out.println("</tr>");
				out.println("</table>");
			}
			if (core_data_id > 0) {

				out.println("<br>");
				out.println("<table border=\"0\" cellspacing=\"10\" cellpadding=\"0\">");
				out.println("<tr>");
				//out.println("<td class=\"text\">Designation:</td>");
				out.println("<td class=\"bigtext\" colspan=\"2\">" + xcd.getDesignation() + "</td>");
				out.println("</tr>");
				out.println("<tr>");
				out.println("<td class=\"text\" colspan=\"2\">&nbsp;</td>");
				out.println("</tr>");

				//print additional attributes
				for (i=0;i<xcd.getXinco_add_attributes().size();i++) {
					xcdta = ((XincoCoreDataTypeAttribute)xcd.getXinco_core_data_type().getXinco_core_data_type_attributes().elementAt(i));
					xaa = ((XincoAddAttribute)xcd.getXinco_add_attributes().elementAt(i));
					out.println("<tr>");
					out.println("<td class=\"text\" valign=\"top\"><b>" + xcdta.getDesignation() + ":</b></td>");
					if (xcdta.getData_type().toLowerCase().compareTo("int") == 0) {
						out.println("<td class=\"text\"><pre>" + xaa.getAttrib_int() + "</pre></td>");
					}
					if (xcdta.getData_type().toLowerCase().compareTo("unsignedint") == 0) {
						out.println("<td class=\"text\"><pre>" + xaa.getAttrib_unsignedint() + "</pre></td>");
					}
					if (xcdta.getData_type().toLowerCase().compareTo("double") == 0) {
						out.println("<td class=\"text\"><pre>" + xaa.getAttrib_double() + "</pre></td>");
					}
					if (xcdta.getData_type().toLowerCase().compareTo("varchar") == 0) {
						out.println("<td class=\"text\"><pre>" + xaa.getAttrib_varchar() + "</pre></td>");
					}
					if (xcdta.getData_type().toLowerCase().compareTo("text") == 0) {
						out.println("<td class=\"text\"><pre>" + xaa.getAttrib_text() + "</pre></td>");
					}
					if (xcdta.getData_type().toLowerCase().compareTo("datetime") == 0) {
						out.println("<td class=\"text\"><pre>" + xaa.getAttrib_datetime() + "</pre></td>");
					}
					out.println("</tr>");
				}
				
				out.println("</table>");
				
			}

			//show footer
			if (core_data_id == 0) {
				out.println("<br><br><br>");
				out.println("<table border=\"0\" cellspacing=\"10\" cellpadding=\"0\">");
				out.println("<tr>");
				out.println("<td class=\"text\">&nbsp;</td>");
				out.println("<td class=\"text\">&copy; 2004-2006, <a href=\"http://www.bluecubs.com\" target=\"_blank\" class=\"link\">blueCubs.com</a> and <a href=\"http://www.xinco.org\" target=\"_blank\" class=\"link\">xinco.org</a></td>");
				out.println("</tr>");
				out.println("</table>");
			}
		
			out.println("</span>");
			out.println("</center>");
			out.println("</body>");
	        out.println("</html>");
	
	        out.close();
	        
		} //end HTML output

		//close db connection
		try {
			dbm.con.close();
		} catch (Exception e) {
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
