package Student;

public class Module {
    //private field to store marks for three modules
    private int module1Mark;
    private int module2Mark;
    private int module3Mark;

    public int getModule1Mark() { return module1Mark; }//get method for module1mark
    public int getModule2Mark() { return module2Mark; }//get method for module2mark
    public int getModule3Mark() { return module3Mark; }//get method for module3mark
    public double getAverage() { return (module1Mark + module2Mark + module3Mark) / 3.0; }//method to calculate the average mark
    //method to determine the grade based on average
    public String getGrade() {

        double average = getAverage();
        if (average >= 80) return "Distinction";// if the average is 80or more than ,the grade is"Distinction"
        if (average >= 70) return "Merit";//if the average is 70 or more ,but less than 80, the grade is "Merit"
        if (average >= 40) return "Pass";//if the average is 40 or more , but less than 70, the grade is "Pass"
        //if the average mark is below 40, return "Fail"
        return "Fail";
    }
    //method to set module marks for three modules
    public void setModuleMarks(int mark1, int mark2, int mark3) {
        //set the marks to the provided values
        this.module1Mark = mark1;
        this.module2Mark = mark2;
        this.module3Mark = mark3;
    }
}
