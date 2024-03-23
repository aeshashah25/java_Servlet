import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        // Get user input from the login form
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        // Check if username and password are valid (You need to implement your own
        // validation logic)
        boolean isValidUser = validateUser(username, password);

        if (isValidUser) {
            // Create a session using HttpSession
            HttpSession session = request.getSession();
            session.setAttribute("username", username);

            // Create a cookie for the username
            Cookie usernameCookie = new Cookie("username", username);
            usernameCookie.setMaxAge(10); // Cookie will expire in 24 hours
            response.addCookie(usernameCookie);

            out.println("<html><body>");
            out.println("<h2>Welcome, " + username + "!</h2>");
            out.println("<p>You have successfully logged in.</p>");
            out.println("</body></html>");
        } else {
            out.println("<html><body>");
            out.println("<h2>Login Failed</h2>");
            out.println("<p>Invalid username or password.</p>");
            out.println("</body></html>");
        }
    }

    private boolean validateUser(String username, String password) {
    
        return username != null && !username.isEmpty() && password != null && !password.isEmpty();
    }
}
