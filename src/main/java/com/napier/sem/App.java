package com.napier.sem;

import java.sql.*;
import java.util.ArrayList;

public class App {


    // I think here to add


    public Employee getEmployee(int ID)
    {
        try
        {
            // Create an SQL statement
            Statement stmt = con.createStatement();

            // Create the SQL query
            String strSelect =
                    "SELECT emp_no, first_name, last_name "
                            + "FROM employees "
                            + "WHERE emp_no = " + ID;

            // Execute the SQL query
            ResultSet rset = stmt.executeQuery(strSelect); //The ResultSet holds the records returned by the query.

            // Check whether an employee was found
            if (rset.next()) // if true (employee is found)
            {
                Employee emp = new Employee();

                emp.emp_no = rset.getInt("emp_no");
                emp.first_name = rset.getString("first_name");
                emp.last_name = rset.getString("last_name");

                return emp;
            }
            else
            {
                return null;
            }
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
            System.out.println("Failed to get employee details");
            return null;
        }
    }

    public void displayEmployee(Employee emp)
    {
        if (emp != null)
        {
            System.out.println(
                    emp.emp_no + " "
                            + emp.first_name + " "
                            + emp.last_name + "\n"
                            + emp.title + "\n"
                            + "Salary:" + emp.salary + "\n"
                            + emp.dept_name + "\n"
                            + "Manager: " + emp.manager + "\n");
        }
    }

    /**
     * Connection to MySQL database.
     */
    private Connection con = null;

    /**
     * Connect to the MySQL database.
     */
//    public void connect() {
//        try {
//            // Load Database driver
//            Class.forName("com.mysql.cj.jdbc.Driver");
//        } catch (ClassNotFoundException e) {
//            System.out.println("Could not load SQL driver");
//            System.exit(-1);
//        }
//
//        int retries = 10;
//        for (int i = 0; i < retries; ++i) {
//            System.out.println("Connecting to database...");
//            try {
//                // Wait a bit for db to start
//                Thread.sleep(30000);
//                // Connect to database
////                con = DriverManager.getConnection("jdbc:mysql://db:3306/employees?allowPublicKeyRetrieval=true&useSSL=false", "root", "example");
//                con = DriverManager.getConnection(
//                        "jdbc:mysql://localhost:33060/employees?allowPublicKeyRetrieval=true&useSSL=false",
//                        "root",
//                        "example"
//                );
//                System.out.println("Successfully connected");
//                break;
//            } catch (SQLException sqle) {
//                System.out.println("Failed to connect to database attempt " + Integer.toString(i));
//                System.out.println(sqle.getMessage());
//            } catch (InterruptedException ie) {
//                System.out.println("Thread interrupted? Should not happen.");
//            }
//        }
//    }

// so delay should be included as when testing the connection,
// if it js test immediately, if the connection in between is still in progress
// it would show no connection, so setting delay is needed,
// but delay time should be decided properly not to slow down the system
public void connect(String location, int delay) {
    try {
        // Load Database driver
        Class.forName("com.mysql.cj.jdbc.Driver");
    } catch (ClassNotFoundException e) {
        System.out.println("Could not load SQL driver");
        System.exit(-1);
    }

    int retries = 10;
    for (int i = 0; i < retries; ++i) {
        System.out.println("Connecting to database...");
        try {
            // Wait a bit for db to start
            Thread.sleep(delay);
            // Connect to database
            con = DriverManager.getConnection("jdbc:mysql://" + location
                            + "/employees?allowPublicKeyRetrieval=true&useSSL=false",
                    "root", "example");
            System.out.println("Successfully connected");
            break;
        } catch (SQLException sqle) {
            System.out.println("Failed to connect to database attempt " +                                  Integer.toString(i));
            System.out.println(sqle.getMessage());
        } catch (InterruptedException ie) {
            System.out.println("Thread interrupted? Should not happen.");
        }
    }
}
    /**
     * Disconnect from the MySQL database.
     */
    public void disconnect() {
        if (con != null) {
            try {
                // Close connection
                con.close();
            } catch (Exception e) {
                System.out.println("Error closing connection to database");
            }
        }
    }


    public static void main(String[] args) {
        // Create new Application and connect to database
        App a = new App();

        if(args.length < 1){
            a.connect("localhost:33060", 30000);
        }else{
            a.connect(args[0], Integer.parseInt(args[1]));
        }

        Department dept = a.getDepartment("Development");
        ArrayList<Employee> employees = a.getSalariesByDepartment(dept);


        // Print salary report
        a.printSalaries(employees);

        // Disconnect from database
        a.disconnect();
    }

    public void printSalaries(ArrayList<Employee> employees)
    {
        // Check employees is not null
        if (employees == null)
        {
            System.out.println("No employees");
            return;
        }
        // Print header
        System.out.println(String.format("%-10s %-15s %-20s %-8s", "Emp No", "First Name", "Last Name", "Salary"));
        // Loop over all employees in the list
        for (Employee emp : employees)
        {
            String emp_string =
                    String.format("%-10s %-15s %-20s %-8s",
                            emp.emp_no, emp.first_name, emp.last_name, emp.salary);
            System.out.println(emp_string);
        }
    }

//    public Department getDepartment(String name) {
//        // Find department by name
//        return null;
//    }
    // it return null ofc it would show nothing, tho the connection and input are all ready !!!

    public Department getDepartment(String name) {
        try {
            Statement stmt = con.createStatement();

            String strSelect =
                    "SELECT dept_no, dept_name " +
                            "FROM departments " +
                            "WHERE dept_name = '" + name + "'";

            ResultSet rset = stmt.executeQuery(strSelect);

            if (rset.next()) {
                Department dept = new Department();

                dept.dept_no = rset.getString("dept_no");
                dept.dept_name = rset.getString("dept_name");

                return dept;
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return null;
    }

//    public ArrayList<Employee> getSalariesByDepartment(Department dept) {
//        ArrayList<Employee> employees = new ArrayList<>();
//
//        // Retrieve employees belonging to this department
//
//        return employees;
//    }
    // this only return null ofc it would show nothing

    public ArrayList<Employee> getSalariesByDepartment(Department dept) {

        ArrayList<Employee> employees = new ArrayList<>();

        try {
            Statement stmt = con.createStatement();

            String strSelect =
                    "SELECT e.emp_no, e.first_name, e.last_name, s.salary " +
                            "FROM employees e " +
                            "JOIN dept_emp de ON e.emp_no = de.emp_no " +
                            "JOIN salaries s ON e.emp_no = s.emp_no " +
                            "WHERE de.dept_no = '" + dept.dept_no + "' " +
                            "AND de.to_date = '9999-01-01' " +
                            "AND s.to_date = '9999-01-01'" +
                            "LIMIT 5";;

            ResultSet rset = stmt.executeQuery(strSelect);

            while (rset.next()) {
                Employee emp = new Employee();

                emp.emp_no = rset.getInt("emp_no");
                emp.first_name = rset.getString("first_name");
                emp.last_name = rset.getString("last_name");
                emp.salary = rset.getInt("salary");

                employees.add(emp);
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return employees;
    }
}