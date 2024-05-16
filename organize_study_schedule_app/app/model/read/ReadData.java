package app.model.read;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import app.model.course.Course;
import app.model.course.CourseManager;
import app.model.course.MyClass;
import app.model.course.Time;
import resource.arraylist.MyArrayList;
import resource.node.CourseNode;

public class ReadData {

    private CourseManager courseManager = CourseManager.getInstance();
    private MyArrayList<CourseNode<String, MyArrayList<MyClass>>> exerciseClassesList = courseManager
            .getExerciseClassesList();
    private MyArrayList<CourseNode<String, MyArrayList<MyClass>>> theoryClassesList = courseManager
            .getTheoryClassesList();

    public void readData() {

        BufferedReader dataReader = null;
        String prevCourseId = null;
        MyClass prevClass = new MyClass();

        try {
            String line;
            dataReader = new BufferedReader(
                    new FileReader("TKB-gui-SV.csv"));
            // Read file line by line?
            while ((line = dataReader.readLine()) != null) {
                List<String> dataList = parseDataLineToList(line);

                // course infor
                String courseId = dataList.get(0).trim();
                String courseName = dataList.get(1).trim();
                String creditNumber = dataList.get(2).trim();

                Course course = new Course(courseId, courseName, creditNumber);

                // time infor
                String dayOfWeek = dataList.get(4).trim();
                String time = dataList.get(5).trim();
                String room = dataList.get(6).trim();

                Time classTime = new Time(dayOfWeek, time, room);

                MyArrayList<Time> theoryTime = new MyArrayList<>();

                // class infor
                String classId = dataList.get(3).trim();
                MyClass myClass = new MyClass(classId, null, course, classTime, theoryTime);

                if (!courseId.equals(prevCourseId)) {
                    CourseNode<String, MyArrayList<MyClass>> exerciseCourseNode = new CourseNode<>(courseId,
                            new MyArrayList<MyClass>());

                    CourseNode<String, MyArrayList<MyClass>> theoryCourseNode = new CourseNode<>(courseId,
                            new MyArrayList<MyClass>());

                    exerciseClassesList.add(exerciseCourseNode);
                    exerciseClassesList.get(exerciseClassesList.size() - 1).getValue().add(myClass);

                    // mỗi 1 course đều được add vào theoryClassesList
                    theoryClassesList.add(theoryCourseNode);

                    prevCourseId = courseId;
                    prevClass = myClass;
                } else { // xử lý dữ liệu về tiết lý thuyết ở đây
                    /*
                     * nếu classId có dạng number_number thì đó là tiết lý thuyết -> add vào trong
                     * list các lớp lt
                     */
                    if (classId.matches(".*\\d+_\\d+.*")) {
                        theoryClassesList.get(theoryClassesList.size() - 1).getValue().add(myClass);
                    } else if (classId.equals(prevClass.getClassId())) {
                        mergeTimeHandler(prevClass, myClass);
                    } else { // nếu không phải là tiết lý thuyết thì add vào trong list các lớp bài tập
                        exerciseClassesList.get(exerciseClassesList.size() - 1).getValue().add(myClass);
                        prevClass = myClass;
                    }
                }
            }
            mergeClassTime();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (dataReader != null)
                    dataReader.close();
            } catch (IOException crunchifyException) {
                crunchifyException.printStackTrace();
            }
        }

    }

    // hợp nhất thời gian tiết bài tập với tiết lý thuyết tương ứng
    private void mergeClassTime() {
        for (int i = 0; i < theoryClassesList.size(); i++) {
            int index = 0;
            CourseNode<String, MyArrayList<MyClass>> theoryCourseNode = theoryClassesList.get(i);
            MyArrayList<MyClass> theoryClassesList = theoryCourseNode.getValue();

            CourseNode<String, MyArrayList<MyClass>> exerciseCourseNode = exerciseClassesList.get(i);
            MyArrayList<MyClass> exerciseClassesList = exerciseCourseNode.getValue();

            if (theoryClassesList.size() == 0) {
                continue;
            }

            for (int j = 0; j < theoryClassesList.size(); j++) {
                int maxNumOfTheoryClass = getMaxNumOfTheoryClass(theoryClassesList.get(j));

                for (int k = index; k < maxNumOfTheoryClass; k++) {
                    mergeTimeHandler(exerciseClassesList.get(k), theoryClassesList.get(j));
                    index++;
                }
            }
        }
    }

    // xử lý logic việc hợp nhất thời gian
    // thời gian của các tiết bài tập và lý thuyết được tách bởi dấu |
    private void mergeTimeHandler(MyClass exercise, MyClass theory) {
        String theoryTime = theory.getClassTime().getTime();
        String theoryDOW = theory.getClassTime().getDayOfWeek();
        String theoryRoom = theory.getClassTime().getRoom();

        exercise.setTheoryId(theory.getClassId());
        exercise.getTheoryTime().add(new Time(theoryDOW, theoryTime, theoryRoom));
    }

    // PHY3626 1_2_3 -> lấy ra 3
    // PHY3626 1_2_3_4_5_6 KHMTVTT -> lấy ra 6
    // ...
    private int getMaxNumOfTheoryClass(MyClass theoryClass) {
        String theoryClassId = theoryClass.getClassId();
        String[] splitArray = theoryClassId.split("\\s+");
        char result = splitArray[1].charAt(splitArray[1].length() - 1);

        return Integer.parseInt(result + "");
    }

    public List<String> parseDataLineToList(String dataLine) {
        List<String> result = new ArrayList<>();
        if (dataLine != null) {
            String[] splitData = dataLine.split(",");
            for (int i = 0; i < splitData.length; i++) {
                result.add(splitData[i]);
            }
        }
        return result;
    }

    public String[] parseDataLineToArray(String dataLine) {
        if (dataLine == null) {
            return null;
        }

        return dataLine.split("\\,");
    }

}