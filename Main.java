package tracker;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.*;
import java.util.stream.Collectors;


class Student {
    private int id = 0;
    private String firstName;
    private String lastName;
    private String email;
    private int java;
    private int DSA;
    private int database;
    private int spring;

    public Student() {
    }

    public Student(String firstName, String lastName, String email) {
        if (studentList.size() == 0) {
            this.id = 1;
        } else {
            this.id = studentList.get(studentList.size() - 1).getId() + 1;
        }
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
    }

    public Student(int id, String firstName, String lastName, String email, int java, int DSA, int database, int spring) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.java = java;
        this.DSA = DSA;
        this.database = database;
        this.spring = spring;
    }

    public Student(String firstName) {
        this.firstName = firstName;
    }

    static List<Student> studentList = new ArrayList<>();

    public static List<Student> getStudentList() {
        return studentList;
    }

    public static void setStudentList(Student student) {
        studentList.add(student);
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;

    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = studentList.get(studentList.size() - 1).getId() + 1;
    }

    public int getJava() {
        return java;
    }

    public void setJava(int java) {
        this.java = java;
    }

    public int getDSA() {
        return DSA;
    }

    public void setDSA(int DSA) {
        this.DSA = DSA;
    }

    public int getDatabase() {
        return database;
    }

    public void setDatabase(int database) {
        this.database = database;
    }

    public int getSpring() {
        return spring;
    }

    public void setSpring(int spring) {
        this.spring = spring;

    }

    @Override
    public String toString() {
        String id5digits = String.format("%05d", id);
        return
                id5digits + " points:" +
                        " Java=" + java + ";" +
                        " DSA=" + DSA + ";" +
                        " Databases=" + database + ";" +
                        " Spring=" + spring;
    }
}

class Notification {

    static List<Student> studentList = Student.studentList;

    static ArrayDeque<Student> completedCourse = new ArrayDeque<>();

    static List<Student> saveCompletedStudent = new ArrayList<>();

    void createCourseCompleteStudentsList() {

        for (Student x : studentList) {
            if (!saveCompletedStudent.contains(x)) {
                saveCompletedStudent.add(x);
                if (x.getDSA() == 400) {
                    Student tempDSA = new Student(x.getDSA(), x.getFirstName(), x.getLastName(), x.getEmail(), x.getJava(), x.getDSA(), x.getDatabase(), x.getSpring());
                    tempDSA.setSpring(0);
                    tempDSA.setDatabase(0);
                    tempDSA.setJava(0);
                    completedCourse.add(tempDSA);
                }
                if (x.getJava() == 600) {
                    Student tempJava = new Student(x.getDSA(), x.getFirstName(), x.getLastName(), x.getEmail(), x.getJava(), x.getDSA(), x.getDatabase(), x.getSpring());
                    tempJava.setDSA(0);
                    tempJava.setDatabase(0);
                    tempJava.setSpring(0);
                    completedCourse.add(tempJava);
                }
                if (x.getDatabase() == 480) {
                    Student tempDB = new Student(x.getDSA(), x.getFirstName(), x.getLastName(), x.getEmail(), x.getJava(), x.getDSA(), x.getDatabase(), x.getSpring());
                    tempDB.setDSA(0);
                    tempDB.setSpring(0);
                    tempDB.setJava(0);
                    completedCourse.add(tempDB);
                }
                if (x.getSpring() == 550) {
                    Student tempSpring = new Student(x.getDSA(), x.getFirstName(), x.getLastName(), x.getEmail(), x.getJava(), x.getDSA(), x.getDatabase(), x.getSpring());
                    tempSpring.setDSA(0);
                    tempSpring.setDatabase(0);
                    tempSpring.setJava(0);
                    completedCourse.add(tempSpring);
                }
            }
        }
    }

    void notifyStudentsAboutCompleteCourse() {

        createCourseCompleteStudentsList();

        if (completedCourse.size() == 0) {
            System.out.println("Total 0 students have been notified.");
        } else {
            int notifiedStudentCount = 0;
            String courseName = "";
            int previousStudentId = 0;
            for (Student x : completedCourse) {
                String studentFirstName = x.getFirstName();
                String studentLastName = x.getLastName();
                String javaCourseName = x.getJava() >= 600 ? "Java" : "";
                String springCourseName = x.getSpring() >= 550 ? "Spring" : "";
                String dsaCourseName = x.getDSA() >= 400 ? "DSA" : "";
                String dbCourseName = x.getDatabase() >= 480 ? "Java" : "";
                if (!javaCourseName.isEmpty()) {
                    courseName = javaCourseName;
                } else if (!springCourseName.isEmpty()) {
                    courseName = springCourseName;
                } else if (!dbCourseName.isEmpty()) {
                    courseName = dbCourseName;
                } else if (!dsaCourseName.isEmpty()) {
                    courseName = dsaCourseName;
                }
                System.out.println("To: " + x.getEmail());
                System.out.println("Re: Your Learning Progress");
                String showResult = String.format("Hello, %s %s! You have accomplished our %s course!", studentFirstName, studentLastName, courseName);
                System.out.println(showResult);
                completedCourse.removeFirst();
                if (previousStudentId != x.getId()) {
                    notifiedStudentCount++;
                }
                previousStudentId = x.getId();
            }
            System.out.println("Total " + notifiedStudentCount + " students have been notified.");
        }
    }
}

class CourseStatistic {

    static List<Student> studentList = Student.studentList;

    private String popularCourse;
    private int countJava;
    private int countSpring;
    private int countDatabase;
    private int countDSA;

    static Map<Integer, List<Float>> javaTopLearners = new HashMap<>();
    static Map<Integer, List<Float>> springTopLearners = new HashMap<>();
    static Map<Integer, List<Float>> databaseTopLearners = new HashMap<>();
    static Map<Integer, List<Float>> dsaTopLearners = new HashMap<>();

    String[] getPopularCourse() {
        String[] popularCourse = new String[2];
        Map<String, Integer> courseCounts = new HashMap<>();
        courseCounts.put("Java", 0);
        courseCounts.put("Spring", 0);
        courseCounts.put("Databases", 0);
        courseCounts.put("DSA", 0);
        for (Student x : studentList) {
            if (x.getJava() != 0) {
                courseCounts.put("Java", courseCounts.getOrDefault("Java", 0) + 1);
            }
            if (x.getSpring() != 0) {
                courseCounts.put("Spring", courseCounts.getOrDefault("Spring", 0) + 1);
            }
            if (x.getDatabase() != 0) {
                courseCounts.put("Databases", courseCounts.getOrDefault("Databases", 0) + 1);
            }
            if (x.getDSA() != 0) {
                courseCounts.put("DSA", courseCounts.getOrDefault("DSA", 0) + 1);
            }
        }
        if (!courseCounts.containsValue(0)) {
            popularCourse[1] = "n/a";
            List<String> filteredCourseKeys = courseCounts.keySet()
                    .stream()
                    .filter(key -> courseCounts.get(key) > 0)
                    .collect(Collectors.toList());
            String popularCourses = String.join(", ", filteredCourseKeys);
            popularCourse[0] = popularCourses;
            return popularCourse;
        } else {
            popularCourse[1] = Collections.min(courseCounts.entrySet(), Map.Entry.comparingByValue()).getKey();
            List<String> filteredCourseKeys = courseCounts.keySet()
                    .stream()
                    .filter(key -> courseCounts.get(key) > 0)
                    .collect(Collectors.toList());
            if (filteredCourseKeys.contains(popularCourse[1])) {
                filteredCourseKeys.remove(popularCourse[1]);
            }
            String popularCourses = String.join(", ", filteredCourseKeys);
            popularCourse[0] = popularCourses;
            return popularCourse;
        }
    }

    String[] getCourseActivity() {
        Map<String, Integer> courseCounts = new HashMap<>();
        String[] courseActivity = new String[2];
        for (Student x : studentList) {
            courseCounts.put("Java", courseCounts.getOrDefault("Java", 0) + x.getJava());
            courseCounts.put("Spring", courseCounts.getOrDefault("Spring", 0) + x.getSpring());
            courseCounts.put("Databases", courseCounts.getOrDefault("Databases", 0) + x.getDatabase());
            courseCounts.put("DSA", courseCounts.getOrDefault("DSA", 0) + x.getDSA());
        }
        if (!courseCounts.containsValue(0)) {
            courseActivity[1] = "n/a";
            List<String> filteredCourseKeys = courseCounts.keySet()
                    .stream()
                    .filter(key -> courseCounts.get(key) > 0)
                    .collect(Collectors.toList());
            String popularCourses = String.join(", ", filteredCourseKeys);
            courseActivity[0] = popularCourses;
            return courseActivity;
        } else {
            courseActivity[0] = Collections.max(courseCounts.entrySet(), Map.Entry.comparingByValue()).getKey();
            courseActivity[1] = Collections.min(courseCounts.entrySet(), Map.Entry.comparingByValue()).getKey();
            return courseActivity;
        }
    }

    String[] getCourseLevel() {
        Map<String, Double> courseLevel = new HashMap<>();
        String[] courseLvl = new String[2];
        double javaAverage = 0;
        double springAverage = 0;
        double dbAverage = 0;
        double dsaAverage = 0;
        double javaCompletePoint = 600;
        double dsaCompletePoint = 400;
        double dbCompletePoint = 480;
        double springCompletePoint = 550;

        for (Student x : studentList) {
            javaAverage = javaAverage + x.getJava();
            courseLevel.put("Java", courseLevel.getOrDefault("Java", 0d) + x.getJava());
            springAverage = springAverage + x.getSpring();
            courseLevel.put("Spring", courseLevel.getOrDefault("Spring", 0d) + x.getSpring());
            dbAverage = dbAverage + x.getDatabase();
            courseLevel.put("Databases", courseLevel.getOrDefault("Databases", 0d) + x.getDatabase());
            dsaAverage = dsaAverage + x.getDSA();
            courseLevel.put("DSA", courseLevel.getOrDefault("DSA", 0d) + x.getDSA());
        }

        courseLvl[0] = Collections.max(courseLevel.entrySet(), Map.Entry.comparingByValue()).getKey();
        courseLvl[1] = Collections.min(courseLevel.entrySet(), Map.Entry.comparingByValue()).getKey();

        return courseLvl;
    }

    void findCoursesTopLearners() {
        Float javaCompletePercent = 0f;
        Float springCompletePercent = 0f;
        Float dbCompletePercent = 0f;
        Float dsaCompletePercent = 0f;

        Float javaPoints = 0f;
        Float springPoints = 0f;
        Float dbPoints = 0f;
        Float dsaPoints = 0f;

        for (Student x : studentList) {
            javaCompletePercent = ((Float.valueOf(x.getJava()) / 600) * 100);
            Float roundedJavaCompletePercentValue = javaCompletePercent;
            javaPoints = Float.valueOf(x.getJava());
            List<Float> javaPointsLst = Arrays.asList(javaPoints, javaCompletePercent);
            javaTopLearners.put(x.getId(), javaPointsLst);
            springCompletePercent = ((Float.valueOf(x.getSpring()) / 550) * 100);
            springPoints = Float.valueOf(x.getSpring());
            List<Float> springPointsLst = Arrays.asList(springPoints, springCompletePercent);
            springTopLearners.put(x.getId(), springPointsLst);
            dbCompletePercent = ((Float.valueOf(x.getDatabase()) / 480) * 100);
            dbPoints = Float.valueOf(x.getDatabase());
            List<Float> dbPointsLst = Arrays.asList(dbPoints, dbCompletePercent);
            databaseTopLearners.put(x.getId(), dbPointsLst);
            dsaCompletePercent = ((Float.valueOf(x.getDSA()) / 400) * 100);
            dsaPoints = Float.valueOf(x.getDSA());
            List<Float> dsaPointsLst = Arrays.asList(dsaPoints, dsaCompletePercent);
            dsaTopLearners.put(x.getId(), dsaPointsLst);
        }
    }

    void showTopLearnersByCourse(Map<Integer, List<Float>> topLearners) {
        // Sort the map by the first value in each list, then by the key if the first values are equal
        List<Map.Entry<Integer, List<Float>>> list = new ArrayList<>(topLearners.entrySet());
        Collections.sort(list, (a, b) -> {
            int firstValueCompare = b.getValue().get(0).compareTo(a.getValue().get(0)); // compare first values in descending order
            if (firstValueCompare != 0) {
                return firstValueCompare; // if first values are not equal, return comparison result
            }
            return a.getKey().compareTo(b.getKey()); // if first values are equal, compare keys in ascending order
        });

// Print the sorted map
        Map<Integer, List<Float>> sortedMap = new LinkedHashMap<>();
        for (Map.Entry<Integer, List<Float>> entry : list) {
            sortedMap.put(entry.getKey(), entry.getValue());
        }
        for (Map.Entry<Integer, List<Float>> entry : sortedMap.entrySet()) {
            int key = entry.getKey();
            int value1 = Math.round(entry.getValue().get(0));
            float value2 = entry.getValue().get(1);
            if (value1 != 0) { // Check if value1 is non-zero
                String formattedKey = String.format("%05d", key);
                String formattedValue2 = String.format("%.1f", value2);
                System.out.printf("%s %d %7s%%%n", formattedKey, value1, formattedValue2);
            }
        }
    }

    void topLearnersUtil(String courseName) {
        findCoursesTopLearners();
        if (courseName.equalsIgnoreCase("Java")) {
            System.out.println("Java");
            System.out.println("id     " + "points     " + "completed");
            if (!studentList.isEmpty()) {
                showTopLearnersByCourse(javaTopLearners);
            }
        } else if (courseName.equalsIgnoreCase("Spring")) {
            System.out.println("Spring");
            System.out.println("id     " + "points     " + "completed");
            if (!studentList.isEmpty()) {
                showTopLearnersByCourse(springTopLearners);
            }

        } else if (courseName.equalsIgnoreCase("DSA")) {
            System.out.println("DSA");
            System.out.println("id     " + "points     " + "completed");
            if (!studentList.isEmpty()) {
                showTopLearnersByCourse(dsaTopLearners);
            }

        } else if (courseName.equalsIgnoreCase("Databases")) {
            System.out.println("Databases");
            System.out.println("id     " + "points     " + "completed");
            if (!studentList.isEmpty()) {
                showTopLearnersByCourse(databaseTopLearners);
            }
        }
    }


}

public class Main {

    String checkStudentsInfo(String firstName, String lastName, String email) {

        final String REGEX_FIRST_NAME = "(^[a-zA-Z]+\\-{1}[a-zA-Z].*$)|(^[a-zA-Z]+\\'{1}[a-zA-Z].*$)|(^[a-zA-Z]*$)|(^[a-zA-Z]+\\-{1}[a-zA-Z]+\\'{1}[a-zA-Z]*$)|(^[a-zA-Z]+\\'{1}[a-zA-Z].*$)";
        final String REGEX_LAST_NAME = "(^[a-zA-Z\\s]+\\-{1}[a-zA-Z\\s].*$)|(^[a-zA-Z\\s]+\\'{1}[a-zA-Z\\s].*$)|[a-zA-Z\\s]*$|(^[a-zA-Z\\s]+\\'{1}[a-zA-Z\\s]+\\-[a-zA-Z\\s]*$)|(^[a-zA-Z\\s]+\\-{1}[a-zA-Z\\s]+\\'[a-zA-Z\\s]*$)";
        final String REGEX_EMAIL = "^\\w+([-+.']\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*$";
        boolean firstNameCheck = firstName.matches(REGEX_FIRST_NAME) && firstName.length() > 1;
        boolean lastNameCheck = lastName.matches(REGEX_LAST_NAME) && lastName.length() > 1;
        boolean emailCheck = email.matches(REGEX_EMAIL) && email.length() > 1;
        if (firstNameCheck) {
        } else {
            return "Incorrect first name";
        }
        if (lastNameCheck) {
        } else {
            return "Incorrect last name";
        }
        if (emailCheck) {
        } else {
            return "Incorrect email";
        }
        return "";
    }

    boolean checkIncorrectCredentials(String... arr) {
        if (arr.length < 3) {
            return false;
        }
        for (int i = 0; i < arr.length; i++) {
            if (arr[i].isBlank() || arr[i] == null) {
                return false;
            }
        }
        return true;
    }

    void consolePrint() throws FileNotFoundException, UnsupportedEncodingException {
        CourseStatistic courseStatistic = new CourseStatistic();
        Notification notification = new Notification();
        System.out.println("Learning Progress Tracker");
        Scanner scanner = new Scanner(System.in);
        String userInput;
        boolean check = true;
        while (check) {
            userInput = scanner.nextLine();
            if (userInput.equalsIgnoreCase("exit")) {
                check = false;
            } else if (userInput.isBlank()) {
                System.out.println("No input");
            } else if (userInput.equals("back")) {
                System.out.println("Enter 'exit' to exit the program.");
            } else if (userInput.equals("list")) {
                if (Student.studentList.size() != 0) {
                    System.out.println("Students:");
                    for (int i = 0; i < Student.studentList.size(); i++) {
                        int studentId = Student.studentList.get(i).getId();
                        System.out.println(String.format("%05d", studentId));
                    }
                } else {
                    System.out.println("No students found");
                }
            } else if (userInput.equalsIgnoreCase("java") || userInput.equalsIgnoreCase("spring")
                    || userInput.equalsIgnoreCase("Databases") || userInput.equalsIgnoreCase("DSA")) {
                System.out.println("Unknown command!");
            } else if (userInput.equalsIgnoreCase("statistics")) {
                boolean statisticsBlock = true;
                System.out.println("Type the name of a course to see details or 'back' to quit:");
                if (Student.studentList.isEmpty()) {
                    System.out.println("Most popular: n/a");
                    System.out.println("Least popular: n/a");
                    System.out.println("Highest activity: n/a");
                    System.out.println("Lowest activity: n/a");
                    System.out.println("Easiest course: n/a");
                    System.out.println("Hardest course: n/a");
                    while (statisticsBlock) {
                        userInput = scanner.nextLine();
                        if (!userInput.equalsIgnoreCase("java") && !userInput.equalsIgnoreCase("spring")
                                && !userInput.equalsIgnoreCase("Databases") && !userInput.equalsIgnoreCase("DSA")) {
                            System.out.println("Unknown course.");
                        }
                        if (userInput.equalsIgnoreCase("Java")) {
                            courseStatistic.topLearnersUtil("Java");
                        }
                        if (userInput.equalsIgnoreCase("Spring")) {
                            courseStatistic.topLearnersUtil("Spring");
                        }
                        if (userInput.equalsIgnoreCase("DSA")) {
                            courseStatistic.topLearnersUtil("DSA");
                        }
                        if (userInput.equalsIgnoreCase("Databases")) {
                            courseStatistic.topLearnersUtil("Databases");
                        }
                        if (userInput.equalsIgnoreCase("back")) {
                            System.out.println("Enter 'exit' to exit the program.");
                            statisticsBlock = false;
                        }
                    }
                } else {
                    System.out.println("Most popular: " + courseStatistic.getPopularCourse()[0]);
                    System.out.println("Least popular: " + courseStatistic.getPopularCourse()[1]);
                    System.out.println("Highest activity: " + courseStatistic.getCourseActivity()[0]);
                    System.out.println("Lowest activity: " + courseStatistic.getCourseActivity()[1]);
                    System.out.println("Easiest course: " + courseStatistic.getCourseLevel()[0]);
                    System.out.println("Hardest course: " + courseStatistic.getCourseLevel()[1]);
                    while (statisticsBlock) {
                        userInput = scanner.nextLine();

                        if (userInput.equalsIgnoreCase("Java")) {
                            courseStatistic.topLearnersUtil("Java");
                        }
                        if (userInput.equalsIgnoreCase("Spring")) {
                            courseStatistic.topLearnersUtil("Spring");
                        }
                        if (userInput.equalsIgnoreCase("DSA")) {
                            courseStatistic.topLearnersUtil("DSA");
                        }
                        if (userInput.equalsIgnoreCase("Databases")) {
                            courseStatistic.topLearnersUtil("Databases");
                        }
                        if (userInput.equalsIgnoreCase("back")) {
                            System.out.println("Enter 'exit' to exit the program.");
                            statisticsBlock = false;
                        }
                    }
                }
            } else if (userInput.equals("notify")) {
                notification.notifyStudentsAboutCompleteCourse();
            } else if (userInput.equalsIgnoreCase("add points")) {
                boolean chkInputID = true;
                boolean incorrectFormat = false;
                boolean checkFirstInputElememnt = false;
                System.out.println("Enter an id and points or 'back' to return");
                while (chkInputID) {
                    String addPointInputBack = scanner.nextLine();
                    if (addPointInputBack.contains("back")) {
                        System.out.println("Enter 'exit' to exit the program.");
                        break;
                    }
                    String[] addPointInput = addPointInputBack.split(" ");
                    try {
                        Integer.valueOf(addPointInput[0]);
                    } catch (NumberFormatException nfe) {
                        checkFirstInputElememnt = true;
                        System.out.println("No student is found for id=" + addPointInput[0] + ".");
                    }
                    try {
                        if (!checkFirstInputElememnt && (addPointInput.length != 5 || Integer.valueOf(addPointInput[0]) < 0 || Integer.valueOf(addPointInput[1]) < 0 ||
                                Integer.valueOf(addPointInput[2]) < 0 || Integer.valueOf(addPointInput[3]) < 0 || Integer.valueOf(addPointInput[4]) < 0)) {
                            incorrectFormat = true;
                            System.out.println("Incorrect points format");
                        }
                    } catch (NumberFormatException nfe) {
                        incorrectFormat = true;
                        System.out.println("Incorrect points format");
                    }
                    if (!incorrectFormat && !checkFirstInputElememnt) {
                        int inputId = Integer.valueOf(addPointInput[0]);
                        boolean findResult = false;
                        for (int i = 0; i < Student.studentList.size(); i++) {
                            int studentId = Student.studentList.get(i).getId();
                            if (studentId == inputId && addPointInput.length == 5 && !incorrectFormat) {
                                findResult = true;
                                Student.studentList.get(i).setJava(Integer.valueOf(addPointInput[1]) + Student.studentList.get(i).getJava());
                                Student.studentList.get(i).setDSA(Integer.valueOf(addPointInput[2]) + Student.studentList.get(i).getDSA());
                                Student.studentList.get(i).setDatabase(Integer.valueOf(addPointInput[3]) + Student.studentList.get(i).getDatabase());
                                Student.studentList.get(i).setSpring(Integer.valueOf(addPointInput[4]) + Student.studentList.get(i).getSpring());
                                System.out.println("Points updated");
                                break;
                            }
                        }
                        if (!findResult && !incorrectFormat) {
                            System.out.println("No student is found for id=" + inputId + ".");
                        }
                    }
                }
            } else if (userInput.equals("find")) {
                System.out.println("Enter an id or 'back' to return");
                boolean chkInputID = true;
                while (chkInputID) {
                    String userInputID = scanner.next();
                    if (userInputID.contains("back")) {
                        System.out.println("Enter 'exit' to exit the program.");
                        break;
                    }
                    int findID = Integer.valueOf(userInputID);
                    boolean findResult = false;
                    for (int i = 0; i < Student.studentList.size(); i++) {
                        int studentId = Student.studentList.get(i).getId();
                        if (studentId == findID) {
                            findResult = true;
                            System.out.println(Student.studentList.get(i).toString());
                        }
                    }
                    if (!findResult) {
                        System.out.println("No student is found for id=" + findID + ".");
                    }
                }

            } else if (userInput.equalsIgnoreCase("add students")) {
                System.out.println("Enter student credentials or 'back' to return:");
                while (true) {
                    boolean flag = false;
                    boolean checkEmail = false;
                    String[] inputData = scanner.nextLine().split(" ");
                    PrintWriter writerInput = new PrintWriter("the-file-name.txt", "UTF-8");
                    writerInput.println("input arrays is" + Arrays.toString(inputData));

                    writerInput.close();

                    String mergeLastName = "";
                    if (inputData[0].equalsIgnoreCase("back")) {
                        break;
                    }
                    if (!checkIncorrectCredentials(inputData) || inputData.length == 1) {
                        System.out.println("Incorrect credentials.");
                    } else if (inputData.length > 1) {
                        if (inputData.length > 3) {
                            for (int i = 1; i < inputData.length - 1; i++) {
                                mergeLastName = mergeLastName + " " + inputData[i];
                            }
                        } else {
                            mergeLastName = inputData[1];
                        }
                        String firstName = inputData[0];
                        String lastName = mergeLastName;
                        String email = inputData[inputData.length - 1];
                        for (int i = 0; i < Student.getStudentList().size(); i++) {
                            if (Student.getStudentList().get(i).getEmail().equals(email)) {
                                flag = true;
                                System.out.println("This email is already taken");
                                break;
                            }
                        }

                        PrintWriter writer = new PrintWriter("inputdata.txt", "UTF-8");
                        writer.println("firstName" + " " + firstName);
                        writer.println("lastName" + " " + lastName);
                        writer.println("email" + " " + email);
                        writer.close();


                        String[] addStudentCredential = new String[]{firstName, lastName, email};

                        System.out.println(checkStudentsInfo(addStudentCredential[0], addStudentCredential[1], addStudentCredential[2]));
                        if (checkStudentsInfo(addStudentCredential[0], addStudentCredential[1], addStudentCredential[2]) == "" && !flag) {
                            Student.studentList.add(new Student(addStudentCredential[0], addStudentCredential[1], addStudentCredential[2]));
                            System.out.println("The student has been added.");
                        }
                    }
                }
                String totalStudentInf = String.format("Total %d students were added.", Student.studentList.size());
                System.out.println(totalStudentInf);
            } else {
                System.out.println("Unknown command!");
            }
        }
        System.out.println("Bye!");
    }

    public static void main(String[] args) throws FileNotFoundException, UnsupportedEncodingException {
        Main main = new Main();
        main.consolePrint();
    }
}

