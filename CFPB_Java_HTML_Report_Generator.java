import java.io.BufferedWriter;
import java.io.FileWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

// Author:  APPLICANT ID:  2925511

public class CFPB_Java_HTML_Report_Generator {

	public static void main(String args[]) {
		createReport1HtmlFile();
		createReport2HtmlFile();

	}

	
	// Number of Complaints per state with zip codes having at least 10,000 population who are aged 15 years and older whose annual income is $75K or more from 2013 to 2015
	public static void createReport1HtmlFile() {
		Connection c = null;
		Statement stmt = null;

		StringBuilder sb = new StringBuilder();

		sb.append("<html>");
		sb.append("<head>");
		sb.append("<title>Title Of the page");
		sb.append("</title>");
		sb.append("</head>");
		sb.append("<body>");

		sb.append("<p>Number of Complaints per state with zip codes having at least 10,000 population who are aged 15 years and older whose annual income is $75K or more from 2013 to 2015</p>");
		sb.append("<table border=\"1\" style=\"width:100%\">");
		sb.append("<tr>");

		sb.append("<th>State</th>");
		sb.append("<th>Number Of Complaints</th>");
		sb.append("<th>Calendar Year</th>");
		
		sb.append("</tr>");

		try {
			Class.forName("org.postgresql.Driver");
			c = DriverManager.getConnection("jdbc:postgresql://localhost:5432/ConsumerComplaintsAndACS", "postgres",
					"supw1@");
			c.setAutoCommit(false);
			

			stmt = c.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT  distinct "
					+ " c.state "
					+ ", count(c.*) as NumberOfComplaints "
					+ ", extract (year from date_received) as CalendarYear "

					+ " FROM public.geography_data g "
					+ " inner join consumer_complaints c"
					+ " on g.zcta5 = c.zip_code "
					+ " inner join income_data_seq15 i "
					+ " on g.logrecno = i.logrecno"
					+ " where  c.state <> '' "
					+ " and B06010_011 > 10000 "
					+ " and date_received between '2013-01-01' and '2015-12-31' "
					+ " group by c.state, CalendarYear "
					+ " order by 1, 3, 2");

			while (rs.next()) {

				String state = rs.getString("state");
				int NumberOfComplaints = rs.getInt("NumberOfComplaints");
				String CalendarYear = rs.getString("CalendarYear");
				

				sb.append("<tr>");

				sb.append("<td>" + state + "</td>");
				sb.append("<td>" + NumberOfComplaints + "</td>");
				sb.append("<td>" + CalendarYear + "</td>");
				
				sb.append("</tr>");

			}

			rs.close();
			stmt.close();
			c.close();

			sb.append("</table>");
			sb.append("</body>");
			sb.append("</html>");
			FileWriter fstream = new FileWriter("Report1.html");
			BufferedWriter out = new BufferedWriter(fstream);
			out.write(sb.toString());
			out.close();
			System.out.println("Created Report1.html successfully");
			
		} catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			System.exit(0);
		}

		
	}

	
	
	
	// Number of Complaints by product per state with zip codes having at least 10,000 population who are aged 15 years and older whose annual income is $75K or more for 2015
	public static void createReport2HtmlFile() {
		Connection c = null;
		Statement stmt = null;

		StringBuilder sb = new StringBuilder();

		sb.append("<html>");
		sb.append("<head>");
		sb.append("<title>Title Of the page");
		sb.append("</title>");
		sb.append("</head>");
		sb.append("<body>");

		sb.append("<p>Number of Complaints by product per state with zip codes having at least 10,000 population who are aged 15 years and older whose annual income is $75K or more for 2015</p>");
		sb.append("<table border=\"1\" style=\"width:100%\">");
		sb.append("<tr>");

		sb.append("<th>State</th>");
		sb.append("<th>Product</th>");
		sb.append("<th>Number Of Complaints</th>");
		sb.append("<th>Calendar Year</th>");
		
		sb.append("</tr>");

		try {
			Class.forName("org.postgresql.Driver");
			c = DriverManager.getConnection("jdbc:postgresql://localhost:5432/ConsumerComplaintsAndACS", "postgres",
					"supw1@");
			c.setAutoCommit(false);
			

			stmt = c.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT  distinct "
					+ " c.state "
					+ ", product "
					+ ", count(c.*) as NumberOfComplaints "
					+ ", extract (year from date_received) as CalendarYear "

					+ " FROM public.geography_data g "
					+ " inner join consumer_complaints c"
					+ " on g.zcta5 = c.zip_code "
					+ " inner join income_data_seq15 i "
					+ " on g.logrecno = i.logrecno"
					+ " where  c.state <> '' "
					+ " and B06010_011 > 10000 "
					+ " and date_received between '2015-01-01' and '2015-12-31'"
					+ " group by c.state, CalendarYear, product "
					+ " order by 1, 3, 2");

			while (rs.next()) {

				String state = rs.getString("state");
				String product = rs.getString("product");
				int NumberOfComplaints = rs.getInt("NumberOfComplaints");
				String CalendarYear = rs.getString("CalendarYear");
				

				sb.append("<tr>");

				sb.append("<td>" + state + "</td>");
				sb.append("<td>" + product + "</td>");
				sb.append("<td>" + NumberOfComplaints + "</td>");
				sb.append("<td>" + CalendarYear + "</td>");
				
				sb.append("</tr>");

			}

			rs.close();
			stmt.close();
			c.close();

			sb.append("</table>");
			sb.append("</body>");
			sb.append("</html>");
			FileWriter fstream = new FileWriter("Report2.html");
			BufferedWriter out = new BufferedWriter(fstream);
			out.write(sb.toString());
			out.close();
			System.out.println("Created Report2.html successfully");
			
		} catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			System.exit(0);
		}

		
	}
	

}
