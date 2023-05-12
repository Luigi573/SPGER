package mx.uv.fei.logic.daos;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import mx.uv.fei.dataaccess.DataBaseManager;
import mx.uv.fei.logic.daosinterfaces.IGlobalReportDAO;
import mx.uv.fei.logic.domain.Course;
import mx.uv.fei.logic.domain.Student;

public class GlobalReportDAO implements IGlobalReportDAO{

    @Override
    public ArrayList<Course> getCoursesFromDatabase(int nrc) {
        ArrayList<Course> courses = new ArrayList<>();

        try {
            DataBaseManager dataBaseManager = new DataBaseManager();
            String query = "SELECT * FROM Cursos WHERE NRC LIKE ?";
            PreparedStatement preparedStatement = dataBaseManager.getConnection().prepareStatement(query);
            preparedStatement.setString(1, nrc + "%");

            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()){
                Course course = new Course();
                course.setNrc(resultSet.getInt("NRC"));
                course.setIdScholarPeriod(resultSet.getInt("IdPeriodoEscolar"));
                course.setEEName(resultSet.getString("nombre"));
                course.setSection(resultSet.getString("sección"));
                course.setBlock(resultSet.getInt("bloque"));
                courses.add(course);
            }
            resultSet.close();
            dataBaseManager.getConnection().close();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return courses;
        //throw new UnsupportedOperationException("Unimplemented method 'getCoursesFromDatabase'");
    }

    @Override
    public ArrayList<Student> getStudentsFromDatabase(String matricle) {
        ArrayList<Student> students = new ArrayList<>();

        try {
            DataBaseManager dataBaseManager = new DataBaseManager();
            String query = "SELECT * FROM Estudiantes E INNER JOIN Usuarios U ON E.IdUsuario = U.IdUsuario HAVING Matrícula LIKE ?";
            PreparedStatement preparedStatement = dataBaseManager.getConnection().prepareStatement(query);
            preparedStatement.setString(1, matricle + "%");

            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()){
                Student student = new Student();
                student.setName(resultSet.getString("nombre"));
                student.setFirstSurname(resultSet.getString("apellidoPaterno"));
                student.setSecondSurname(resultSet.getString("apellidoMaterno"));
                student.setEmailAddress(resultSet.getString("correo"));
                student.setPassword(resultSet.getString("contraseña"));
                student.setAlternateEmail(resultSet.getString("correoAlterno"));
                student.setMatricle(resultSet.getString("Matrícula"));
                students.add(student);
            }
            resultSet.close();
            dataBaseManager.getConnection().close();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return students;
        //throw new UnsupportedOperationException("Unimplemented method 'getStudentsFromDatabase'");
    }
    //YOP
}
