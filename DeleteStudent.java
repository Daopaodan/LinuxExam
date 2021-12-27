import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import com.google.gson.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import com.google.gson.reflect.TypeToken;
import javax.servlet.annotation.WebServlet;

@WebServlet(urlPatterns = "/DeleteStudent")
public class DeleteStudent extends HttpServlet {
    final static String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
    final static String URL = "jdbc:mysql://180.76.165.153/Linux_finalexam";
    final static String USER = "root";
    final static String PASS = "Wcy123..";
    final static String SQL_DELETE_STUDENT = "DELETE FROM final_student WHERE id= ?";

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        Student req = getRequestBody(request);
        getServletContext().log(req.toString());
        PrintWriter out = response.getWriter();

        out.println(deleteNote(req));
        out.flush();
        out.close();
    }

    private Student getRequestBody(HttpServletRequest request) throws IOException {
        Student note = new Student();
        StringBuffer bodyJ = new StringBuffer();
        String line = null;
        BufferedReader reader = request.getReader();
        while ((line = reader.readLine()) != null)
            bodyJ.append(line);
        Gson gson = new Gson();
        note = gson.fromJson(bodyJ.toString(), new TypeToken<Student>() {
        }.getType());
        return note;
    }

    private int deleteNote(Student req) {
        Connection conn = null;
        PreparedStatement stmt = null;
        int retcode = -1;
        try {
            Class.forName(JDBC_DRIVER);
            conn = DriverManager.getConnection(URL, USER, PASS);
            stmt = conn.prepareStatement(SQL_DELETE_STUDENT);

            stmt.setInt(1, req.id);
            int row = stmt.executeUpdate();
            if (row > 0)
                retcode = 1;

            stmt.close();
            conn.close();
        } catch (SQLException se) {
            se.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (stmt != null)
                    stmt.close();
                if (conn != null)
                    conn.close();
            } catch (SQLException se) {
                se.printStackTrace();
            }
        }
        return retcode;
    }

    public class Student {
        int id;
        String name;
        int age;
    }

}
