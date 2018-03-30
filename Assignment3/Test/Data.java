// Kaustav Vats (2016048)
// Yashit Maheshwari (2016123)

public class Data{
    private String ValidTag = "0000";
    private String InstructorID;
    private String Name;
    private String Department;
    private int Salary;

    public Data(String instructorID, String name, String department, int salary)
    {
        this.InstructorID = instructorID.substring(0, 4);
        this.Name = name.substring(0, 20);
        this.Department = department.substring(0, 10);
        this.Salary = Integer.valueOf(String.valueOf(salary).substring(0, 10));
    }

    public String getValidityTag()
    {
        return this.ValidTag;
    }

    public void Delete()
    {
        this.ValidTag = "1111";
    }

    public String toString()
    {
        String out = InstructorID + " " + Name + " " + Department + " " + String.valueOf(Salary) + "\n";
        return out;
    }
}