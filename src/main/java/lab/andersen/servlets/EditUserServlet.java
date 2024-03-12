package lab.andersen.servlets;

import com.google.gson.Gson;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lab.andersen.entity.User;
import lab.andersen.service.UserService;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.stream.Collectors;

@WebServlet("/users/edit")
public class EditUserServlet extends HttpServlet {

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        Integer id = Integer.parseInt(req.getParameter("id"));

        try (
                BufferedReader reader = req.getReader();
                PrintWriter writer = resp.getWriter()
        ) {
            String lines = reader.lines().collect(Collectors.joining());
            Gson gson = new Gson();
            User user = gson.fromJson(lines, User.class);
            boolean isUpdated = UserService.getInstance()
                    .update(id, user.getFirstName(), user.getSecondName(), user.getAge());

            writer.write(String.valueOf(isUpdated));
        }
    }
}
