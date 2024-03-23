import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/fetchBook")
public class FetchBookServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Hardcoded ISBN value
        String Isbn = "1";

        // Call fetchBookDetails method with hardcoded ISBN
        Book book = fetchBookDetails(Isbn);

        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        out.println("<html>");
        out.println("<head>");
        out.println("<title>Book Details</title>");
        out.println("</head>");
        out.println("<body>");
        if (book != null) {
            out.println("<h2>Book Details</h2>");
            out.println("<p>Title: " + book.getTitle() + "</p>");
            out.println("<p>Author: " + book.getAuthor() + "</p>");
            out.println("<p>ISBN: " + book.getIsbn() + "</p>");
        } else {
            out.println("<h2>Book not found!</h2>");
        }
        out.println("</body>");
        out.println("</html>");
    }

    private Book fetchBookDetails(String Isbn) {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        Book book = null;

        try {
            // Establish database connection
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/bookdb", "root", "");

            // Prepare SQL statement to fetch book details by ISBN
            String sql = "SELECT * FROM books where Isbn=?";
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, Isbn); // Use the isbn parameter directly

            // Execute query
            rs = stmt.executeQuery();

            // If book found, create Book object
            // If book found, create Book object
            if (rs.next()) {
                book = new Book(rs.getString("Isbn"), rs.getString("Title"), rs.getString("Author"));
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // Close JDBC resources
            try {
                if (rs != null)
                    rs.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            try {
                if (stmt != null)
                    stmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            try {
                if (conn != null)
                    conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return book;
    }
}