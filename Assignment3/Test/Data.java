import java.io.Serializable;

// Kaustav Vats (2016048)
// Yashit Maheshwari (2016123)

public class Data implements Serializable {
    private String ValidTag = "1111";
    private String InstructorID;
    private String Name;
    private String Department;
    private long Salary;

    public Data(String instructorID, String name, String department, long salary)
    {
        if(instructorID.length() > 4) {
            this.InstructorID = instructorID.substring(0, 4);
        }
        else {
            this.InstructorID = instructorID;
        }

        if(name.length() > 20) {
            this.Name = name.substring(0, 20);
        }
        else {
            this.Name = name;
        }

        if(department.length() > 10) {
            this.Department = department.substring(0, 10);
          }
        else {
            this.Department = department;
        }

        if(String.valueOf(salary).length() > 10) {
            this.Salary = Long.parseLong(String.valueOf(salary).substring(0, 10));
        }
        else {
            this.Salary = Long.parseLong(String.valueOf(salary));
        }
    }

    public String getValidityTag()
    {
        return this.ValidTag;
    }

    public void Delete()
    {
        this.ValidTag = "0000";
    }

    public String toString()
    {
        String out = InstructorID + " " + Name + " " + Department + " " + String.valueOf(Salary);
        return out;
    }

    public String getInstructorId() {
        return this.InstructorID;
    }

    public void setInstructorId(String InstructorID) {
        this.InstructorID = InstructorID;
    }

    public String getInstructorName() {
        return this.Name;
    }
    
    public void setInstructorName(String name) {
        this.Name = name;
    }

    public String getDepartment() {
        return this.Department;
    }
    
    public void setDepartment(String department) {
        this.Department = department;
    }

    public long getSalary() {
        return this.Salary;
    }
    
    public void setSalary(long salary) {
        this.Salary = Long.parseLong(String.valueOf(salary).substring(0, 10));
    }
}