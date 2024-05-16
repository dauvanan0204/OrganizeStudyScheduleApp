package app.model.user;

import app.model.course.CourseManager;
import app.model.course.MyClass;
import resource.arraylist.MyArrayList;
import resource.node.CourseNode;

public class RegisterCourses {
    private static RegisterCourses registerCourses;
    private MyArrayList<MyArrayList<MyClass>> registeredCoursesList;
    private MyArrayList<CourseNode<String, MyArrayList<MyClass>>> courseList = CourseManager.getInstance()
            .getExerciseClassesList();

    private RegisterCourses() {
        registeredCoursesList = new MyArrayList<>();
    }

    public MyArrayList<MyArrayList<MyClass>> getRegisteredCoursesList() {
        return registeredCoursesList;
    }

    public static RegisterCourses getInstance() {
        if (registerCourses == null) {
            synchronized (CourseManager.class) {
                if (registerCourses == null) {
                    registerCourses = new RegisterCourses();
                }
            }
        }
        return registerCourses;
    }

    public int registerCourse(String courseId) {
        if (isContain(courseId) != -1) {
            return 0; // đã đăng ký rồi
        }

        int courseIndex = binarySearch(courseList, 0, courseList.size() - 1,
                courseId);

        if (courseIndex == -1) {
            return -1; // không tìm thấy mã học phần
        }

        registeredCoursesList.add(courseList.get(courseIndex).getValue());
        return 1;
    }

    public void removeRegisteredCourse(String courseId) {
        int index = isContain(courseId);

        if (index == -1) {
            throw new IllegalStateException("You have not registered for this course!!");
        }

        registeredCoursesList.remove(index);
    }

    public void removeRegisteredCourse(int index) {
        registeredCoursesList.remove(index);
    }

    private int binarySearch(MyArrayList<CourseNode<String, MyArrayList<MyClass>>> registeredCoursesList,
            int left, int right, String courseId) {
        if (right >= left) {
            int mid = left + (right - left) / 2;
            String currentCourseId = registeredCoursesList.get(mid).getKey();

            if (currentCourseId.equals(courseId))
                return mid;

            if (currentCourseId.compareTo(courseId) > 0)
                return binarySearch(registeredCoursesList, left, mid - 1, courseId);

            return binarySearch(registeredCoursesList, mid + 1, right, courseId);
        }

        return -1;
    }

    private int isContain(String courseId) {
        for (int i = 0; i < registeredCoursesList.size(); i++) {
            String registeredCourseId = registeredCoursesList.get(i).get(0).getCourse().getCourseId();

            if (registeredCourseId.equals(courseId)) {
                return i;
            }
        }
        return -1;
    }

}
