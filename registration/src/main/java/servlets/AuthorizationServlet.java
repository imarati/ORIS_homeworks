package servlets;

import models.User;
import models.UserUuid;
import repositories.UserRepository;
import repositories.UserRepositoryJdbcImpl;
import repositories.UuidRepository;
import repositories.UuidRepositoryJdbcImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.UUID;

@WebServlet("/auth")
public class AuthorizationServlet extends HttpServlet {
    private static final String DB_USERNAME = "postgres";
    private static final String DB_PASSWORD = "postgres";
    private static final String DB_URL = "jdbc:postgresql://localhost:5432/usersdb";

    private UserRepository usersRepository;
    private UuidRepository uuidRepository;

    @Override
    public void init() throws ServletException {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        try {
            Connection connection = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
            Statement statement = connection.createStatement();
            usersRepository = new UserRepositoryJdbcImpl(connection, statement);
            uuidRepository = new UuidRepositoryJdbcImpl(connection, statement);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("/html/auth.html").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String login = req.getParameter("login");
        String password = req.getParameter("password");

        try {
            int id = usersRepository.findBylogin(login, password);
//            HttpSession httpSession = req.getSession(true);
//            httpSession.setAttribute("authenticated", true);

            UUID uuid = UUID.randomUUID();

            UserUuid userUuid = UserUuid.builder()
                    .uuid(uuid.toString())
                    .userId(id)
                    .build();

            uuidRepository.save(userUuid);

            Cookie cookie = new Cookie("uuid", uuid.toString());
            resp.addCookie(cookie);

            System.out.println(true);
            resp.sendRedirect("/reg");
        }
        catch (Exception e) {
            resp.sendRedirect("/auth");
        }


    }
}
