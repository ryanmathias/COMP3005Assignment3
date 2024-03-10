import java.sql.*;
import java.util.Scanner;

public class DatabaseInteract {
    // Connection Details
    static String url = "jdbc:postgresql://localhost:5432/Assignment3Question1";
    static String user = "postgres";
    static String password = "Your_Password";
    static String studentTable = "students";
    private static Connection connection;

    public static void main(String[] args) {
        try {
            // Establishing a Connection
            Class.forName("org.postgresql.Driver");
            connection = DriverManager.getConnection(url, user, password);
            Statement statement = connection.createStatement();
            DatabaseMetaData metaData = connection.getMetaData();

            // This is to see if the table already exists
            ResultSet checkTable = metaData.getTables(null, null, studentTable, null);
            // If the table doesn't exist, create it
            if (!checkTable.next()) {
                statement.executeUpdate("CREATE TABLE students (\n" +
                        "\n" +
                        "\tstudent_id\tSERIAL PRIMARY KEY,\n" +
                        "\tfirst_name\tTEXT NOT NULL,\n" +
                        "\tlast_name\tTEXT NOT NULL,\n" +
                        "\temail\t\tTEXT UNIQUE NOT NULL,\n" +
                        "\tenrollment_date\tDATE" +
                        ");");

                // Fill the table
                statement.executeUpdate("INSERT INTO students (first_name, last_name, email, enrollment_date) VALUES\n" +
                        "('John', 'Doe', 'john.doe@example.com', '2023-09-01'),\n" +
                        "('Jane', 'Smith', 'jane.smith@example.com', '2023-09-01'),\n" +
                        "('Jim', 'Beam', 'jim.beam@example.com', '2023-09-02');");
            }

            // This whole portion of main is reading user input to test functions
            Scanner scanner = new Scanner(System.in);

            // Read a line of input from the console
            System.out.println("What would you like to do?");
            System.out.println("1: Print all students");
            System.out.println("2: Add a student");
            System.out.println("3: Change a student's email");
            System.out.println("4: Delete a student");
            System.out.println("0: Exit");
            String userChoice = scanner.nextLine();
            while (userChoice != null && userChoice != "0") {
                if (userChoice.compareTo("1") == 0) {
                    getAllStudents();
                }

                else if (userChoice.compareTo("2") == 0) {
                    System.out.println("First Name: ");
                    String firstName = scanner.nextLine();
                    System.out.println("Last Name: ");
                    String lastName = scanner.nextLine();
                    System.out.println("Email: ");
                    String email = scanner.nextLine();
                    System.out.println("Date Enrolled (YYYY-MM-DD): ");
                    String date_enrolled = scanner.nextLine();
                    addStudent(firstName, lastName, email, Date.valueOf(date_enrolled));
                }

                else if (userChoice.compareTo("3") == 0) {
                    System.out.println("Student ID for email change: ");
                    String studentId = scanner.nextLine();
                    System.out.println("New Email: ");
                    String email = scanner.nextLine();
                    updateStudentEmail(Integer.parseInt(studentId), email);
                }

                else if (userChoice.compareTo("4") == 0) {
                    System.out.println("Student to remove (ID): ");
                    String studentId = scanner.nextLine();
                    deleteStudent(Integer.parseInt(studentId));
                }

                else if (userChoice.compareTo("0") == 0) {
                    System.out.println("Goodbye!");
                    break;
                }

                System.out.println("What would you like to do?");
                System.out.println("1: Print all students");
                System.out.println("2: Add a student");
                System.out.println("3: Change a student's email");
                System.out.println("4: Delete a student");
                System.out.println("0: Exit");
                userChoice = scanner.nextLine();
            }
        }

        catch(Exception e) {
            System.out.println(e);
        }
    }

    public static void getAllStudents() {
        try {
            // Make sure application knows what connection is again
            connection = DriverManager.getConnection(url, user, password);
            Statement statement = connection.createStatement();

            statement.executeQuery("SELECT * FROM students");
            ResultSet resultSet = statement.getResultSet();

            // Print out the contents of every column in the student table
            while(resultSet.next()) {
                System.out.print(resultSet.getString("student_id") + "\t");
                System.out.print(resultSet.getString("first_name") + " ");
                System.out.print(resultSet.getString("last_name") + "\t");
                System.out.print(resultSet.getString("email") + "\t");
                System.out.println(resultSet.getDate("enrollment_date"));
            }
            System.out.println();

        } catch (SQLException e) {
            System.out.println(e);
        }
    }

    public static void addStudent(String first_name, String last_name, String email, Date enrollment_date) {
        try {
            // Make sure application knows what connection is again
            // Insert Query
            connection = DriverManager.getConnection(url, user, password);
            String query = "INSERT INTO students (first_name, last_name, email, enrollment_date) VALUES " +
                    "(?, ?, ?, ?)"; // First I am inserting a '?' into each column for the new row (default value)

            // This is where all the arguments get set for the statement
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, first_name);
            preparedStatement.setString(2, last_name);
            preparedStatement.setString(3, email);
            preparedStatement.setDate(4, enrollment_date);
            preparedStatement.executeUpdate();
            System.out.println();
            System.out.println("New student added!");
            System.out.println();

        } catch (SQLException e) {
            System.out.println(e);
        }
    }

    public static void updateStudentEmail(Integer student_id, String new_email) {
        try {
            connection = DriverManager.getConnection(url, user, password);

            // Update the table so that we set the student's email to the passed in value
            // if the student id is the one passed in
            String query = "UPDATE students SET email = ? WHERE student_id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, new_email);
            preparedStatement.setInt(2, student_id);
            preparedStatement.executeUpdate();
            System.out.println();
            System.out.println("Email Updated!");
            System.out.println();

        } catch (SQLException e) {
            System.out.println(e);
        }
    }

    public static void deleteStudent(Integer student_id) {
        try {
            connection = DriverManager.getConnection(url, user, password);
            String query = "DELETE FROM students WHERE student_id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);

            // Setting the query to delete the student with the passed in student id
            preparedStatement.setInt(1, student_id);
            preparedStatement.executeUpdate();
            System.out.println();
            System.out.println("Student Removed!");
            System.out.println();
        }

        catch (Exception e) {
            System.out.println(e);
        }
    }
}
