package com.napier.sem;

import java.sql.*;
import java.util.ArrayList;

public class App
{
    /**
     * Connection to MySQL database.
     */
    private Connection con = null;
    private ArrayList<Dept_manager> Dept_managers;

    /**
     * Connect to the MySQL database.
     */
    public void connect(String location)
    {
        try
        {
            // Load Database driver
            Class.forName("com.mysql.cj.jdbc.Driver");
        }
        catch (ClassNotFoundException e)
        {
            System.out.println("Could not load SQL driver");
            System.exit(-1);
        }

        int retries = 10;
        for (int i = 0; i < retries; ++i)
        {
            System.out.println("Connecting to database...");
            try
            {
                // Wait a bit for db to start
                Thread.sleep(30000);
                // Connect to database
                con = DriverManager.getConnection("jdbc:mysql://" + location + "/employees?allowPublicKeyRetrieval=true&useSSL=false", "root", "example");
                System.out.println("Successfully connected");
                break;
            }
            catch (SQLException sqle)
            {
                System.out.println("Failed to connect to database attempt " + Integer.toString(i));
                System.out.println(sqle.getMessage());
            }
            catch (InterruptedException ie)
            {
                System.out.println("Thread interrupted? Should not happen.");
            }
        }
    }

    /**
     * Disconnect from the MySQL database.
     */
    public void disconnect()
    {
        if (con != null)
        {
            try
            {
                // Close connection
                con.close();
            }
            catch (Exception e)
            {
                System.out.println("Error closing connection to database");
            }
        }
    }
    /**
     * Gets all the current employees and salaries.
     * @return A list of all employees and salaries, or null if there is an error.
     */
    public ArrayList<Employee> getAllSalaries()
    {
        try
        {
            // Create an SQL statement
            Statement stmt = con.createStatement();
            // Create string for SQL statement
            String strSelect =
                    "SELECT employees.emp_no, employees.first_name, employees.last_name, salaries.salary "
                            + "FROM employees, salaries "
                            + "WHERE employees.emp_no = salaries.emp_no AND salaries.to_date = '9999-01-01' "
                            + "ORDER BY employees.emp_no ASC";
            // Execute SQL statement
            ResultSet rset = stmt.executeQuery(strSelect);
            // Extract employee information
            ArrayList<Employee> employees = new ArrayList<Employee>();
            while (rset.next())
            {
                Employee emp = new Employee();
                emp.emp_no = rset.getInt("employees.emp_no");
                emp.first_name = rset.getString("employees.first_name");
                emp.last_name = rset.getString("employees.last_name");
                emp.salary = rset.getInt("salaries.salary");
                employees.add(emp);
            }
            return employees;
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
            System.out.println("Failed to get salary details");
            return null;
        }
    }
    public ArrayList<Salaries> getSalaryEmployee()
    {
        try
        {
            System.out.println(
                    "EmployeeNo" + "\t" + "Salary");
            // Create an SQL statement
            Statement stmt = con.createStatement();
            // Create string for SQL statement
            String strSelect =
                    "SELECT emp_no, salary FROM salaries limit 10";
            // Execute SQL statement
            ResultSet rset = stmt.executeQuery(strSelect);
            ArrayList<Salaries> salary = new ArrayList<>();
            // Return new country if valid.
            // Check one is returned
            while (rset.next())
            {
                Salaries emp = new Salaries();
                emp.setEmp_no(rset.getString("emp_no"));
                emp.setSalary(rset.getString("salary"));
                salary.add(emp);
            }
            return salary;
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
            System.out.println("Failed to get salary details");
            return null;
        }
    }
    public ArrayList<Dept_manager> getDeptManager()
    {
        try
        {
            System.out.println(
                    "EmployeeNo" + "\t" + "Department" + "\t" + "Salary");
            // Create an SQL statement
            Statement stmt = con.createStatement();
            // Create string for SQL statement
            String strSelect =
                    "SELECT dept_manager.emp_no, dept_manager.dept_no, salaries.salary FROM dept_manager, salaries WHERE " +
                            "dept_manager.emp_no = salaries.emp_no limit 10";
            // Execute SQL statement
            ResultSet rset = stmt.executeQuery(strSelect);
            ArrayList<Dept_manager> depts = new ArrayList<>();
            // Return new country if valid.
            // Check one is returned
            while (rset.next())
            {
                Dept_manager dept = new Dept_manager();
                dept.setEmp_no(rset.getString("emp_no"));
                dept.setDept_no(rset.getString("dept_no"));
                dept.setSalary(rset.getString("salary"));
                depts.add(dept);
            }
            return depts;
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
            System.out.println("Failed to get salary details");
            return null;
        }
    }
    public ArrayList<Department> getSalary()
    {
        try
        {
            System.out.println(
                    "Department" + "\t" + "Department_name" + "\t");
            // Create an SQL statement
            Statement stmt = con.createStatement();
            // Create string for SQL statement
            String strSelect =
                    "SELECT * FROM departments";
            // Execute SQL statement
            ResultSet rset = stmt.executeQuery(strSelect);
            ArrayList<Department> dept = new ArrayList<>();
            // Return new country if valid.
            // Check one is returned
            while (rset.next())
            {
                Department de = new Department();
                de.setDept_no(rset.getString("dept_no"));
                de.setDept_name(rset.getString("dept_name"));
                dept.add(de);
            }
            return dept;
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
            System.out.println("Failed to get salary details");
            return null;
        }
    }
    public void displayDepartment(ArrayList<Department> departments){
        for (Department de: departments){
            System.out.println(de.getDept_no()+"\t"+de.getDept_name());
        }
    }
    public void displaySalary(ArrayList<Salaries> salaries){
        for (Salaries sal: salaries){
            System.out.println(sal.getEmp_no()+"\t"+sal.getSalary());
        }
    }
    public void displayEmployee(ArrayList<Employee> employees)
    {
        for(Employee emp: employees){
            System.out.println(emp.getEmp_no()+ " \t " + emp.getFirst_name() + " \t " + emp.getLast_name() + "  ");
        }
    }
    public void displayDeptSalary(ArrayList<Dept_manager> Dept_managers){
        for (Dept_manager dept: Dept_managers){
            System.out.println(dept.getEmp_no() + "\t" + dept.getDept_no()+ "\t" + dept.getSalary()) ;
        }
    }
    /**
     * Prints a list of employees.
     * @param employees The list of employees to print.
     */
    public void printSalaries(ArrayList<Employee> employees)
    {
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
    public static void main(String[] args)
    {
        // Create new Application
        App a = new App();

        // Connect to database
        a.connect("localhost:33060");

        // Extract employee salary information
        ArrayList<Employee> employees = a.getAllSalaries();

        // Test the size of the returned data - should be 240124
        System.out.println(employees.size());
        a.printSalaries(employees);

        // Disconnect from database
        a.disconnect();
    }
}