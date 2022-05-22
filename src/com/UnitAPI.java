package com;

//import com.Unit;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig; 
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

/**
* Servlet implementation class UnitAPI
*/
@WebServlet("/UnitAPI")
public class UnitAPI extends HttpServlet {
	private static final long serialVersionUID = 1L;

	Unit unitObj = new Unit();

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public UnitAPI() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.getWriter().append("Served at: ").append(request.getContextPath());

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException, NumberFormatException {
					String UserID=request.getParameter("UserID");
					String UserName=request.getParameter("UserName");
					String Units=	request.getParameter("Units");
					String Month=request.getParameter("Month");
					String Email=request.getParameter("Email");
				
				String output = unitObj.insertUnit(UserID,UserName,Units,Month,Email);
		response.getWriter().write(output);

	}

	/**
	 * @see HttpServlet#doPut(HttpServletRequest, HttpServletResponse)
	 */
	protected void doPut(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		int UID = Integer.parseInt(request.getParameter("hidUnitIdSave"));
		String UserID=request.getParameter("UserID");
		String UserName=request.getParameter("UserName");
		String Units=	request.getParameter("Units");
		String Month=request.getParameter("Month");
		String Email=request.getParameter("Email");
		 
		
		String output = unitObj.updateUnit(UID,UserID,UserName,Units,Month,Email);

		response.getWriter().write(output);
	}


	/**
	 * @see HttpServlet#doDelete(HttpServletRequest, HttpServletResponse)
	 */
	protected void doDelete(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

					Map paras = getParasMap(request);
					int UID = Integer.parseInt(paras.get("UID").toString());

		  String output = unitObj.deleteUnit(UID);

		response.getWriter().write(output);
	}

	// Convert request parameters to a Map
	private static Map getParasMap(HttpServletRequest request) {
		Map<String, String> map = new HashMap<String, String>();
		try {
			Scanner scanner = new Scanner(request.getInputStream(), "UTF-8");
			String queryString = scanner.hasNext() ? scanner.useDelimiter("\\A").next() : "";
			scanner.close();

			String[] params = queryString.split("&");
			for (String param : params) {

				String[] p = param.split("=");
				map.put(p[0], p[1]);
			}
		} catch (Exception e) {

		}
		return map;

	}
}
