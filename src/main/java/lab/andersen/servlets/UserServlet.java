//package lab.andersen.servlets;
//
//import com.google.gson.Gson;
//import jakarta.servlet.annotation.WebServlet;
//import jakarta.servlet.http.HttpServlet;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import lab.andersen.entity.User;
//import lab.andersen.service.UserService;
//
//import java.io.BufferedReader;
//import java.io.IOException;
//import java.io.PrintWriter;
//import java.nio.charset.StandardCharsets;
//import java.util.List;
//import java.util.stream.Collectors;
//
//@WebServlet("/users")
//public class UserServlet extends HttpServlet {
//    @Override
//    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
//        try (BufferedReader reader = req.getReader()) {
//            String collect = reader.lines().collect(Collectors.joining());
//            Gson gson = new Gson();
//            User user = gson.fromJson(collect, User.class);
//            UserService.getInstance().insert(user.getFirstName(), user.getSecondName(), user.getAge());
//        }
//    }
//
//    @Override
//    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws IOException {
//        Integer id = Integer.parseInt(req.getParameter("id"));
//        boolean isDeleted = UserService.getInstance().deleteById(id);
//
//        try (PrintWriter writer = resp.getWriter()) {
//            writer.write(String.valueOf(isDeleted));
//        }
//    }
//
//    @Override
//    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws IOException {
//        Integer id = Integer.parseInt(req.getParameter("id"));
//
//        try (
//                BufferedReader reader = req.getReader();
//                PrintWriter writer = resp.getWriter()
//        ) {
//            String lines = reader.lines().collect(Collectors.joining());
//            Gson gson = new Gson();
//            User user = gson.fromJson(lines, User.class);
//            boolean isUpdated = UserService.getInstance()
//                    .update(id, user.getFirstName(), user.getSecondName(), user.getAge());
//
//            writer.write(String.valueOf(isUpdated));
//        }
//    }
//
//    @Override
//    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
//        resp.setContentType("application/json");
//        resp.setCharacterEncoding(StandardCharsets.UTF_8.name());
//
//        List<User> users = UserService.getInstance().getAll();
//
//        try (PrintWriter writer = resp.getWriter()) {
//            Gson gson = new Gson();
//
//            writer.write(gson.toJson(users));
//        }
//    }
//}
