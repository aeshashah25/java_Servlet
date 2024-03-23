import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/DatabaseAccessServlet")
public class DatabaseAccessServlet extends HttpServlet {

    protected void service(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            Class.forName("com.mysql.jdbc.Driver");
            System.out.println("Driver Activated");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/bookdb", "root", "");
            System.out.println("Database Connected");
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("select * from books");
            PrintWriter pw = response.getWriter();
            pw.print("<table border=5 width=100% height=10%>");
            pw.print("<tr>");
            pw.print("<th>Isbn</th>");
            pw.print("<th>Title</th>");
            pw.print("<th>Author</th>");

            pw.print("</tr>");
            while (rs.next()) {
                pw.print("<tr>");

                pw.print("<td>" + rs.getString(1) + "</td>");
                pw.print("<td>" + rs.getString(2) + "</td>");
                pw.print("<td>" + rs.getString(3) + "</td>");

                pw.print("</tr>");
            }
            pw.print("</table>");
        } catch (Exception e) {
            System.out.println("Error : " + e.getMessage());
        }
    }

}
