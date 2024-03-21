package lab.andersen.controller;

import lab.andersen.dao.UserDaoImpl;
import lab.andersen.exception.ServiceException;
import lab.andersen.model.User;
import lab.andersen.service.UserService;
import lab.andersen.service.UserServiceImpl;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Base64;

@WebFilter(asyncSupported = true, urlPatterns = {"/*"})
public class BasicAuthFilter implements Filter {
    private final UserService userService = new UserServiceImpl(new UserDaoImpl());

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;

        boolean authorized = false;

        String authHeader = req.getHeader("Authorization");
        if (authHeader != null) {

            String[] authHeaderSplit = authHeader.split("\\s");

            for (int i = 0; i < authHeaderSplit.length; i++) {
                String token = authHeaderSplit[i];
                if (token.equalsIgnoreCase("Basic")) {

                    String credentials = new String(Base64.getDecoder().decode(authHeaderSplit[i + 1]));
                    int index = credentials.indexOf(":");
                    if (index != -1) {
                        String username = credentials.substring(0, index).trim();
                        String password = credentials.substring(index + 1).trim();
                        try {
                            User user = userService.findByUsername(username);
                            authorized = password.equals(user.getPassword());
                        } catch (ServiceException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }

        if (!authorized) {
            res.setHeader("WWW-Authenticate", "Basic realm=\"Insert credentials\"");
            res.sendError(HttpServletResponse.SC_UNAUTHORIZED);
        } else {
            chain.doFilter(req, res);
        }
    }

    @Override
    public void destroy() {
    }
}
