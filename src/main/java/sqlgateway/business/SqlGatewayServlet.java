package sqlgateway.business;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.sql.*;

@WebServlet(name = "SqlGatewayServlet", urlPatterns = {"/sqlGateway"})
public class SqlGatewayServlet extends HttpServlet{
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String sqlStatement = request.getParameter("sqlStatement");
        String sqlResult = "";
        try{
            // load the driver
            Class.forName("com.mysql.cj.jdbc.Driver");
            // get a connection
            String dbURL = "jdbc:mysql://localhost:3306/murach";
            String username = "root";
            String password = "admin";
            Connection connection = DriverManager.getConnection(dbURL, username, password);

            // Parse the SQL string
            sqlStatement = sqlStatement.trim();
            if(sqlStatement.length() >= 6){
                String sqlType = sqlStatement.substring(0, 6);
                PreparedStatement preparedStatement = connection.prepareStatement(sqlStatement);

                if(sqlType.equals("select")){
                    ResultSet resultSet = preparedStatement.executeQuery();
                    sqlResult = SQLUtil.getHtmlTable(resultSet);
                    resultSet.close();
                }
                else{
                    // For Insert, Update, and Delete statements
                    int rowsAffected = preparedStatement.executeUpdate();
                    sqlResult = "<p>The statement executed successfully.<br>"
                            + rowsAffected + " rows were affected.</p>";
                }
                preparedStatement.close();
            }
            connection.close();
        }catch (ClassNotFoundException e){
            sqlResult = "<p>Error loading the databse driver: <br>"
                    + e.getMessage() + "</p>";
        }
        catch ( Exception e ) {
            sqlResult = "<p>Error executing the SQL statement: <br>"
                    + e.getMessage() + "</p>";
        }

        HttpSession session = request.getSession();
        session.setAttribute("sqlResult", sqlResult);
        session.setAttribute("sqlStatement", sqlStatement);

        String url = "/index.jsp";
        getServletContext().getRequestDispatcher(url).forward(request, response);
    }
}