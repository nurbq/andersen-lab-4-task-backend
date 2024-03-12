package lab.andersen.servlets;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lab.andersen.service.UserService;

import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/users/delete")
public class DeleteUserServlet extends HttpServlet {

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        Integer id = Integer.parseInt(req.getParameter("id"));
        boolean isDeleted = UserService.getInstance().deleteById(id);

        try (PrintWriter writer = resp.getWriter()) {
            writer.write(String.valueOf(isDeleted));
        }
    }
}
