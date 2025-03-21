package Student;

public class Student {
    private String id;
    private String name;
    private Module module;
    //  initialized student ID,name and a module object
    public Student(String id, String name) {
        this.id = id;
        this.name = name;
        this.module = new Module();
    }

    public String getId() { return id; }//get method for student id
    public String getName() { return name; }//get method for student name
    public void setName(String name) { this.name = name; }//set method for student name
    public int getModule1Mark() { return module.getModule1Mark(); }//get method for module1mark
    public int getModule2Mark() { return module.getModule2Mark(); }//get method for module2mark
    public int getModule3Mark() { return module.getModule3Mark(); }//get method for module3mark
    public double getAverage() { return module.getAverage(); }//get method for the average mark
    public String getGrade() { return module.getGrade(); }//get method for the grade
    //set the marks for all modules
    public void setModuleMarks(int mark1, int mark2, int mark3) {
        module.setModuleMarks(mark1, mark2, mark3);
    }
    //method to generate a report for the all module marks,total,average and grade
    public String generateReport() {
        return String.format("ID: %s, Name: %s, Module1: %d, Module2: %d, Module3: %d, Total: %d, Average: %.2f, Grade: %s",
                id, name, getModule1Mark(), getModule2Mark(), getModule3Mark(),
                getModule1Mark() + getModule2Mark() + getModule3Mark(), getAverage(), getGrade());
    }

    @Override
    public String toString() {
        return "Student ID: " + id + ", Name: " + name;
    }
}
