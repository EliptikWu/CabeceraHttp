package connection;

import com.example.exceptions.ServiceJdbcException;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;


//@WebFilter("/*")
public class ConnectionFilter implements Filter {
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain
            chain) throws IOException, ServletException {
        try (Connection conn = ConnectionDB.getInstance()) {
            if (conn.getAutoCommit()) {
                conn.setAutoCommit(false);
            }
            try {
                request.setAttribute("conn", conn);
                chain.doFilter(request, response);
                conn.commit();
            } catch (SQLException | ServiceJdbcException e) {
                conn.rollback();
                ((HttpServletResponse) response).sendError(HttpServletResponse
                        .SC_INTERNAL_SERVER_ERROR, e.getMessage());
                e.printStackTrace();
            }
        } catch (SQLException | ClassNotFoundException throwables) {
            throw new ServiceJdbcException("Unable to conect filter");

        }
    }
}