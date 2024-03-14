package lab.andersen.controller;

import lab.andersen.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.PrintWriter;
import java.io.StringReader;
import java.io.StringWriter;

import static org.mockito.Mockito.*;

public class UserControllerTest {

    @Mock
    private UserService userService;

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @InjectMocks
    private UserServlet userServlet = new UserServlet();

    private StringWriter writer = new StringWriter();

    @BeforeEach
    public void init() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void findAllUsersWithInvalidPathInfo() throws Exception {
        when(request.getPathInfo()).thenReturn("/invalid-endpoint");
        when(response.getWriter()).thenReturn(new PrintWriter(writer));
        userServlet.doGet(request, response);
        verify(response).sendError(HttpServletResponse.SC_BAD_REQUEST, "Unable to parse user_id to number");
    }

    @Test
    public void updateUserWithInvalidUser() throws Exception {
        when(request.getReader()).thenReturn(new BufferedReader(new StringReader("invalid json")));
        userServlet.doPost(request, response);
        verify(userService, never()).create(any());
        verify(response).sendError(HttpServletResponse.SC_BAD_REQUEST);
    }
}

