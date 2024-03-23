import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.io.IOException;

public class SecurityCheckFilter implements Filter {

	public void init(FilterConfig config) throws ServletException {
		// Initialization code, if needed
	}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		// Perform security check here
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		if ("MSU".equals(username) && "JAVA".equals(password)) {
			chain.doFilter(request, response); // Continue with the filter chain
		} else {
			response.getWriter().write("Access Denied"); // Respond with access denied message
		}
	}

	public void destroy() {
		// Cleanup code, if needed
	}
}
