package app.model.course;

import app.model.read.ReadData;
import resource.arraylist.MyArrayList;
import resource.node.CourseNode;

public class CourseManager {

    private static CourseManager courseManager;

    private MyArrayList<CourseNode<String, MyArrayList<MyClass>>> exerciseClassesList;
    private MyArrayList<CourseNode<String, MyArrayList<MyClass>>> theoryClassesList;

    private CourseManager() {
        exerciseClassesList = new MyArrayList<>();
        theoryClassesList = new MyArrayList<>();
    }

    public MyArrayList<CourseNode<String, MyArrayList<MyClass>>> getExerciseClassesList() {
        return exerciseClassesList;
    }

    public MyArrayList<CourseNode<String, MyArrayList<MyClass>>> getTheoryClassesList() {
        return theoryClassesList;
    }

    public static CourseManager getInstance() {
        if (courseManager == null) {
            synchronized (CourseManager.class) {
                if (courseManager == null) {
                    courseManager = new CourseManager();
                }
            }
        }
        return courseManager;
    }
}
