import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import com.google.gson.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.util.List;
import java.sql.ResultSet;
import com.google.gson.reflect.TypeToken;
import redis.clients.jedis.Jedis;
import javax.servlet.annotation.WebServlet;

@WebServlet(urlPatterns = "/GetStudentById")
public class GetStudentById extends HttpServlet {
    static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
    static final String DB_URL = "jdbc:mysql://180.76.165.153/Linux_finalexam";
    static final String USER = "root";
    static final String PASS = "Wcy123..";
    static final String SQL_BOOK_GETNAME = "SELECT * FROM final_student where id = ?";

    static Connection conn = null;
    static Jedis jedis = null;

    public void init() {
        try {
            Class.forName(JDBC_DRIVER);
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
            jedis = new Jedis("127.0.0.1"); 

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void destory() {
        try {
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json;charset=utf8");
        response.setCharacterEncoding("utf8");
        PrintWriter out = response.getWriter();
        getServletContext().log(request.getParameter("id"));
        String json = jedis.get(request.getParameter("id"));
        if (json == null) {
            Student stu = getStudent(Integer.parseInt(request.getParameter("id")));
            Gson gson = new Gson();
            json = gson.toJson(stu, new TypeToken<Student>() {
            }.getType());
            jedis.set(request.getParameter("id"), json);

            out.println(json);
        } else {
            out.println(json);
        }

    }


    private Student getStudent(int id) {
        Student stu =new Student();
        PreparedStatement stmt = null;
        try {
            stmt = conn.prepareStatement(SQL_BOOK_GETNAME);
            stmt.setInt(1,id);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()){
            stu.id = rs.getInt("id");
                stu.name = rs.getString("name");
                stu.age =rs.getInt("age");
            }
            
            
            rs.close();
            stmt.close();

        } catch (SQLException se) {
            se.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (stmt != null){
                    stmt.close();
                }
            } catch (SQLException se) {
                se.printStackTrace();
            }
        }
        return stu;
    }
}
 class Student {
    int id;
    String name;
    int age;
}



