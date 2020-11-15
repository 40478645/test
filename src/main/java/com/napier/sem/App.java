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
    public void connect()
    {
        try
        {
            // Load Database driver
            Class.forName("com.mysql.jdbc.Driver");
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
                Thread.sleep(3000);
                // Connect to database
                con = DriverManager.getConnection("jdbc:mysql://db:3306/employees?useSSL=false", "root", "example");
                System.out.println("Successfully connected");
                break;
            }
            catch (SQLException sqle)
            {
                System.out.println("Failed to connect to database attempt " + i);
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
    public ArrayList<Employee> getEmployee(int ID)
    {
        try
        {
            System.out.println(
                    "EmployeeNo" + "\t" + "FirstName" + "\t" + "LastName");
            // Create an SQL statement
            Statement stmt = con.createStatement();
            // Create string for SQL statement
            String strSelect =
                    "SELECT emp_no, first_name, last_name "
                            + "FROM employees "
                            + "WHERE emp_no = " + ID;
            // Execute SQL statement
            ResultSet rset = stmt.executeQuery(strSelect);
            ArrayList<Employee> emp = new ArrayList<>();
            // Return new country if valid.
            // Check one is returned
            while (rset.next())
            {
                Employee employee = new Employee();
                employee.setEmp_no(rset.getInt("emp_no"));
                employee.setFirst_name(rset.getString("first_name"));
                employee.setLast_name(rset.getString("last_name"));
                emp.add(employee);
            }
            return emp;
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
    public static void main(String[] args)
    {
        // Create new Application
        App a = new App();

        // Connect to database
        a.connect();
        // Get Employee
        // ArrayList<Employee> emp = a.getEmployee(23333);
        // Display results
        // a.displayEmployee(emp);
        // ArrayList<Salaries> sal = a.getSalaryEmployee();
        // a.displaySalary(sal);
        // ArrayList<Dept_manager> dept = a.getDeptManager();
        // a.displayDeptSalary(dept);
        ArrayList<Department> dept = a.getSalary();
        a.displayDepartment(dept);
        // Disconnect from database
        a.disconnect();
    }
}