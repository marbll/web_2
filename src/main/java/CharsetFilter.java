import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

public class CharsetFilter implements Filter {
    public CharsetFilter() {
    }

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain next) throws IOException, ServletException, NullPointerException {
        try {
            if (!request.getContentType().equals("text/css") && null != request.getCharacterEncoding()) {
                response.setContentType("text/html; charset=UTF-8");
                response.setCharacterEncoding("UTF-8");
            }
        } catch (NullPointerException e) {
        } finally {
            next.doFilter(request, response);
        }

    }
}
