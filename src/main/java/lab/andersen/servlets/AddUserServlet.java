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
import java.util.stream.Collectors;

@WebServlet("/users/add")
public class AddUserServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        try (BufferedReader reader = req.getReader()) {
            String collect = reader.lines().collect(Collectors.joining());
            Gson gson = new Gson();
            User user = gson.fromJson(collect, User.class);
            UserService.getInstance().insert(user.getFirstName(), user.getSecondName(), user.getAge());
        }
    }
}
