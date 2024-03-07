import java.io.*;
class Combine {
    public static void main(String[] args) {

        int choice = 1;

        Student student;

        StudentFile studentfile = new StudentFile();

        PrintStudents printStudents = new PrintStudents();

        Manager manager = new Manager();

        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        //get the choice first time
        choice=getChoice();

        while (choice!=0) {
            switch (choice) {
                case 1:
                    {
                        student=manager.getStudentInput(); //input student details
                        System.out.println(":   Verify Details  :");
                        printStudents.printStudent(student); //display the details
                        System.out.println("Enter 0 to save and 1 to edit");
                        try {
                        while(Integer.parseInt(in.readLine())!=0) {
                           student=manager.editStudentInput(student);
                        System.out.println("Enter 0 to save and 1 to edit again");
                        }
                        } catch(Exception e) { }
                        studentfile.saveStudentDetails(student); //save the details in a file
                    }
                break;
                case 2:
                    {
                        Student[] studentlist;
                        studentlist=studentfile.fetchStudents(); //fetch the list of students from file
                        student = manager.searchStudent(studentlist,getSearchValue()); //search in the given list(based on roll no)
                        if(student== null) 
                            System.out.println("No record found");
                        else
                            printStudents.printStudent(student);
                    }
                break;    
                case 3:
                    {
                        //fetch the list of students from file and print the list
                        Student[] studentlist;
                        studentlist=studentfile.fetchStudents();
                        printStudents.printStudentList(studentlist);
                    }
                break;
            }
            //get the choice during next execution
            choice=getChoice();
        }
    }

    //proper input format for enter roll no to search a student
    static int getSearchValue () {
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        int search = 0;
        try {
        System.out.println("Enter Roll No. of Student");
        search=Integer.parseInt(in.readLine());
        } catch(Exception e) { }
        return search;
    }

    //format to enter choice for operations
    static int getChoice() {
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        
        int input=0;

        System.out.println("Choose the Option");
        System.out.println(" 1 : Enter Details of a new student");
        System.out.println(" 2 : Search Details of a student");
        System.out.println(" 3 : Fetch all Data");
        System.out.println(" 0 : End the Program");
        try {
        input = Integer.parseInt(in.readLine());
        } catch (Exception e) {
            System.out.println("Error Occured");
            System.out.println("Re Enter Choices");
            return getChoice();
        }
        return input;
    }


}


class Manager {

    //get Details of Student
    Student getStudentInput() {
        Student student=new Student();
        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
            System.out.println(":  Enter details  :");
            System.out.print("Roll No. : ");
            student.rollNo = Integer.parseInt(in.readLine());
            System.out.print("Name : ");
            student.name=in.readLine();
            System.out.print("Age : ");
            student.age=Integer.parseInt(in.readLine());
            System.out.print("Gender : ");
            student.gender=in.readLine().charAt(0);
            System.out.print("Parent Name : ");
            student.parent_name = in.readLine();
            System.out.print("Phone No. : ");
            student.phone_number = Long.parseLong(in.readLine());
            System.out.print("Address : ");
            student.address =  in.readLine();

        } catch (Exception E) {
            System.out.println("Error Occured");
            System.out.println("Re Enter Details Again");
        }
        return student;
    }   
    
    //Edit details of student if entered wrong
    Student editStudentInput(Student student) {
        
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        int choice = editStudentInputChoice();
        try {
            while(choice!=0) {
                System.out.print("Enter "+getAlternateString(choice)+" : ");
                
                switch (choice) {
                case 1 ->student.rollNo = Integer.parseInt(in.readLine());
                case 2 ->student.name=in.readLine();
                case 3 ->student.age=Integer.parseInt(in.readLine());
                case 4 ->student.gender=in.readLine().charAt(0);
                case 5 ->student.parent_name = in.readLine();
                case 6 ->student.phone_number = Long.parseLong(in.readLine());
                case 7 ->student.address =  in.readLine();
            }
            choice = editStudentInputChoice();
        }
        } catch (Exception e) {
            System.out.println("Error Occured, Retry");
        }
        return student;
    }
    
    //format to choose which data to be edited in function editStudentInput(student)
    int editStudentInputChoice() {
        int choice=0;
        System.out.println(": Chooose Data to be Edited :");
        System.out.println("0 : Stop Editing");
        System.out.println("1 : Roll No.");
        System.out.println("2 : Name");
        System.out.println("3 : Age");
        System.out.println("4 : Gender");
        System.out.println("5 : Parent Name");
        System.out.println("6 : Phone Number");
        System.out.println("7 : Address");
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        try {
            choice=Integer.parseInt(in.readLine());
        } catch(Exception e) {
            System.out.println("Error Occured");
            System.out.println("Re Enter Choices");
            return editStudentInputChoice();
        }
        return choice;
    }
    
    //provide a name to every value of variable choice in function editStudentInput(student)
    String getAlternateString(int n) {
        return(switch (n) {
            case 1 -> { yield "Roll No."; }
            case 2 -> { yield "Name"; }
            case 3 -> { yield "Age"; }
            case 4 -> { yield "Gender"; }
            case 5 -> { yield "Parent Name"; }
            case 6 -> { yield "Phone Number"; }
            case 7 -> { yield "Address"; }
            default -> { yield "Error";}
        });
    }
    
    
    Student searchStudent(Student[] studentlist,int search_rollno) {
        int l = studentlist.length;
        for ( int i = 0; i<l; i++) {
            if(studentlist[i].rollNo==search_rollno)
            return studentlist[i];
        }
        return null;
    }
}


class Student {
    String name;
    int age;
    char gender;
    String address;
    String parent_name;
    Long phone_number;
    int rollNo;    
}


class StudentFile {
    
    Format format = new Format();
    void saveStudentDetails(Student student) {
        try {
            BufferedWriter b = new BufferedWriter(new FileWriter(new File("Student.txt"), true));
            b.write(format.encode(student));
            b.close();
        } catch (Exception e) {

        }
    }

    Student[] fetchStudents() {
        Student[] student=null;
        int count = 0;
        try {
        BufferedReader b = new BufferedReader(new FileReader(new File("Student.txt")));
        while(b.readLine()!=null) {
                count++;
        }
        b.close();
        b= new BufferedReader(new FileReader(new File("Student.txt")));
        student = new Student[count];
        for( int i = 0; i < count; i++) {
            student[i]=format.decode(b.readLine());
        }
        b.close();
        } catch( Exception e) { }
        return student;
    }
    
}


class Format {
    
    /** The Format used to encode/decode follows the following rules and syntax
     * Each student will be separated by line break in the file
     * Attributes of Student will be separated by a ;
     */
    StringBuilder change(StringBuilder stringBuilder,char old,char fresh) {
        StringBuilder exchange = new StringBuilder();
        int l = stringBuilder.length();
        char ch;
        for ( int i = 0 ; i < l ; i++ ) {
            ch=stringBuilder.charAt(i);
            if(ch!=old)
            exchange.append(ch);
            else
            exchange.append(fresh);
        }
        return exchange;
    }
    String encode(Student student) {
        StringBuilder value = new StringBuilder();
        
        value.append(student.rollNo);
        value.append(";");
        value.append(student.name);
        value.append(";");
        value.append(student.age);
        value.append(";");
        value.append(student.gender);
        value.append(";");
        value.append(student.parent_name);
        value.append(";");
        value.append(student.phone_number);
        value.append(";");
        value.append(student.address);
        value.append(";");
        value.append("\n");

        value=change(value,' ','_');
        
        return value.toString();
    }
    

    Student decode(String value) {
        Student student =new Student();

        int state=0,position=0,last_position=0;

        String st="";

        //remove the ";;" at the beginning of the String
        value.substring(2);
        StringBuilder stringBuilder = new StringBuilder(value);
        value = change(stringBuilder,'_',' ').toString();
        int l = value.length();
        while(position<l) {

            if(value.charAt(position)==';') {
                st=value.substring(last_position,position);
                switch(state) {
                    case 0 -> student.rollNo=Integer.parseInt(st);
                    case 1 -> student.name=st;
                    case 2 -> student.age=Integer.parseInt(st);
                    case 3 -> student.gender=st.charAt(0);
                    case 4 -> student.parent_name=st;
                    case 5 -> student.phone_number=Long.parseLong(st);
                    case 6 -> student.address=st;
                }
                state++;//move to the next element of the Student Variable
                last_position=position+1;//store the beginning position of the next String

                //termination condition
            }

            position++;
        }
        
        return student;
    }
}


class PrintStudents {
    void printStudent(Student student) {
        System.out.println(":  Details of Student  :");
        System.out.println("  Roll No      : "+student.rollNo);
        System.out.println("  Name         : "+student.name);
        System.out.println("  Parent Name  : "+student.parent_name);
        System.out.println("  Age          : "+student.age);
        System.out.println("  Gender       : "+student.gender);
        System.out.println("  Phone No.    : "+student.phone_number);
        System.out.println("  Address      : "+student.address);
    }

    void printStudentList(Student [] student) {
        int l = student.length;
        for ( int i = 0; i<l ; i++) {
            printStudent(student[i]);
        }
    }
    
}
