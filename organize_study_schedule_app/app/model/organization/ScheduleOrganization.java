package app.model.organization;

import app.model.course.CourseManager;
import app.model.course.MyClass;
import app.model.course.Time;
import app.model.read.ReadData;
import app.model.user.RegisterCourses;
import resource.arraylist.MyArrayList;
import resource.node.CourseNode;

import java.util.Scanner;

public class ScheduleOrganization {
    public MyArrayList<String[][]> getTimeTableList() {
        // CourseManager courseManager = CourseManager.getInstance();
        // ReadData readData = new ReadData();
        // readData.readData();
        // MyArrayList<CourseNode<String, MyArrayList<MyClass>>> courseList =
        // courseManager.getExerciseClassesList();
        MyArrayList<MyArrayList<MyClass>> userRegister = RegisterCourses.getInstance()
                .getRegisteredCoursesList();

        // Sắp xếp danh sách học phần
        MyArrayList<String[][]> timeTableList = new MyArrayList<>();
        String[][] board = new String[5][10];

        organize(timeTableList, board, 0, 0, userRegister);
        return timeTableList;
    }

    // public MyArrayList<MyArrayList<MyClass>> enterData(
    // MyArrayList<CourseNode<String, MyArrayList<MyClass>>> courseList) {
    // // Nhập mã học phần muốn đăng kí vào MyArrayList<danh sách mã lớp>
    // // MyArrayList này lưu trữ các classList của mỗi học phần
    // MyArrayList<MyArrayList<MyClass>> allClassOfCourseList = new MyArrayList<>();
    // Scanner input = new Scanner(System.in);
    // System.out.print("Nhập số môn học muốn đăng kí: ");
    // int n = input.nextInt();

    // String[] courseIds = new String[n];

    // for (int i = 0; i < n; i++) {
    // System.out.print("\nNhập mã học phần thứ " + (i + 1) + ": ");
    // courseIds[i] = input.next();

    // // Kiểm tra các học phần vừa nhập có trong danh sách mã học phần hay không
    // // Nếu không có ---> báo lỗi và yêu cầu nhập lại
    // // Nếu có --> thêm vào MyArrayList<danh sách mã lớp>
    // int indexOfCourseIdsInCourseList =
    // findIndexOfCourseIdsInCourseList(courseList, courseIds[i]);

    // if (indexOfCourseIdsInCourseList != -1) {
    // allClassOfCourseList.add(courseList.get(indexOfCourseIdsInCourseList).getValue());

    // // In ra các lớp của môn học này
    // for (int k = 0; k <
    // courseList.get(indexOfCourseIdsInCourseList).getValue().size(); k++) {
    // System.out.println(courseList.get(indexOfCourseIdsInCourseList).getValue().get(k));
    // }
    // } else {
    // System.out.print("Mã học phần thứ " + (i + 1) + " không tìm thấy. Vui lòng
    // nhập lại: ");
    // courseIds[i] = input.next();
    // i--;
    // }
    // }

    // return allClassOfCourseList;
    // }

    // public int findIndexOfCourseIdsInCourseList(MyArrayList<CourseNode<String,
    // MyArrayList<MyClass>>> courseList,
    // String courseIds) {
    // for (int j = 0; j < courseList.size(); j++) {
    // if (courseIds.equals(courseList.get(j).getKey())) {
    // return j;
    // }
    // }

    // return -1;
    // }

    // public void organize(MyArrayList<String[][]> timeTableList, String[][] board,
    // int indexOfCourse,
    // int indexOfClass, MyArrayList<MyArrayList<MyClass>> allClassOfCourseList) {
    // // Nếu đã duyệt hết các môn hoặc các lớp thì return
    // if (indexOfCourse >= allClassOfCourseList.size()
    // || indexOfClass >= allClassOfCourseList.get(indexOfCourse).size()) {
    // return;
    // }

    // // Kiểm tra xem lớp myClass có thể đăng kí hay không(Bị trùng lịch --->
    // flase,
    // // không bị trùng ---> true)
    // MyClass myClass = allClassOfCourseList.get(indexOfCourse).get(indexOfClass);
    // int dayOfWeek = Integer.valueOf(myClass.getClassTime().getDayOfWeek());
    // int timeStart = Integer.valueOf(myClass.getClassTime().getTime().substring(0,
    // 1));
    // int timeEnd = Integer.valueOf(myClass.getClassTime().getTime().substring(2));

    // // Tạo cloneBoard để đăng kí các lớp tiếp theo trong học phần này
    // String[][] cloneBoard = new String[5][10];
    // for (int i = 0; i < board.length; i++) {
    // for (int j = 0; j < board[i].length; j++) {
    // cloneBoard[i][j] = board[i][j];
    // }
    // }

    // // Thêm myClass vào board nếu insertToBoard = true
    // if (classCanBeInserted(myClass, board, dayOfWeek, timeStart, timeEnd,
    // myClass.getTheoryTime())) {
    // for (int i = timeStart - 1; i < timeEnd; i++) {
    // board[dayOfWeek - 2][i] = myClass.getCourse().getCourseName() + " - "
    // + myClass.getClassTime().getRoom();
    // }

    // // Nếu myClass có lớp lý thuyết ---> add lớp lý thuyết vào board
    // if (myClass.getTheoryTime().size() > 0) {
    // for (int i = 0; i < myClass.getTheoryTime().size(); i++) {
    // int dayOfWeekTheory =
    // Integer.valueOf(myClass.getTheoryTime().get(i).getDayOfWeek());
    // int timeStartTheory =
    // Integer.valueOf(myClass.getTheoryTime().get(i).getTime().substring(0, 1));
    // int timeEndTheory =
    // Integer.valueOf(myClass.getTheoryTime().get(i).getTime().substring(2));
    // for (int k = timeStartTheory - 1; k < timeEndTheory; k++) {
    // board[dayOfWeekTheory - 2][k] = myClass.getCourse().getCourseName() + " - "
    // + myClass.getTheoryTime().get(i).getRoom();
    // }
    // }
    // }

    // // Nếu là môn cuối cùng thì thêm board vào timeTableList
    // // Ngược lại thì duyệt môn tiếp theo
    // if (indexOfCourse >= allClassOfCourseList.size() - 1) {
    // timeTableList.add(board);
    // } else {
    // organize(timeTableList, board, indexOfCourse + 1, 0, allClassOfCourseList);
    // }
    // }

    // // Duyệt lớp tiếp theo (Lúc này sử dụng cloneBoard thay cho board hiện tại)
    // organize(timeTableList, cloneBoard, indexOfCourse, indexOfClass + 1,
    // allClassOfCourseList);
    // }

    public boolean classCanBeInserted(MyClass myClass, String[][] board, int dayOfWeek, int timeStart,
            int timeEnd, MyArrayList<Time> theoryTime) {
        for (int i = timeStart - 1; i < timeEnd; i++) {
            // thứ trong tuần bắt đầu từ thứ 2 nhưng ở trong mảng 2 chiều thì bắt đầu từ 0
            // nên trừ đi 2
            if (board[dayOfWeek - 2][i] != null) {
                return false;
            }
        }

        if (myClass.getTheoryId() != null) {
            for (int i = 0; i < theoryTime.size(); i++) {
                int dayOfWeekTheory = Integer.valueOf(theoryTime.get(i).getDayOfWeek());
                int timeStartTheory = Integer.valueOf(theoryTime.get(i).getTime().substring(0, 1));
                int timeEndTheory = Integer.valueOf(theoryTime.get(i).getTime().substring(2));
                for (int k = timeStartTheory - 1; k < timeEndTheory; k++) {
                    if (board[dayOfWeekTheory - 2][k] != null) {
                        return false;
                    }
                }
            }
        }

        return true;
    }

    public void organize(MyArrayList<String[][]> timeTableList, String[][] board, int indexOfCourse,
            int indexOfClass, MyArrayList<MyArrayList<MyClass>> allClassOfCourseList) {
        // Nếu đã duyệt hết các môn hoặc các lớp thì return
        if (indexOfCourse >= allClassOfCourseList.size()
                || indexOfClass >= allClassOfCourseList.get(indexOfCourse).size()) {
            return;
        }

        // Kiểm tra xem lớp myClass có thể đăng kí hay không(Bị trùng lịch ---> flase,
        // không bị trùng ---> true)
        MyClass myClass = allClassOfCourseList.get(indexOfCourse).get(indexOfClass);
        int dayOfWeek = Integer.valueOf(myClass.getClassTime().getDayOfWeek());
        int timeStart = Integer.valueOf(myClass.getClassTime().getTime().substring(0, 1));
        int timeEnd = Integer.valueOf(myClass.getClassTime().getTime().substring(2));

        // Tạo cloneBoard để đăng kí các lớp tiếp theo trong học phần này
        String[][] cloneBoard = new String[5][10];
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                cloneBoard[i][j] = board[i][j];
            }
        }

        // Thêm myClass vào board nếu insertToBoard = true
        if (classCanBeInserted(myClass, board, dayOfWeek, timeStart, timeEnd, myClass.getTheoryTime())) {
            for (int i = timeStart - 1; i < timeEnd; i++) {
                board[dayOfWeek - 2][i] = myClass.getClassId() + "-" + myClass.getCourse().getCourseName()
                        + "-"
                        + myClass.getClassTime().getRoom();
            }

            // Nếu myClass có lớp lý thuyết ---> add lớp lý thuyết vào board
            if (myClass.getTheoryTime().size() > 0) {
                for (int i = 0; i < myClass.getTheoryTime().size(); i++) {
                    int dayOfWeekTheory = Integer.valueOf(myClass.getTheoryTime().get(i).getDayOfWeek());
                    int timeStartTheory = Integer.valueOf(myClass.getTheoryTime().get(i).getTime().substring(0, 1));
                    int timeEndTheory = Integer.valueOf(myClass.getTheoryTime().get(i).getTime().substring(2));
                    for (int k = timeStartTheory - 1; k < timeEndTheory; k++) {
                        board[dayOfWeekTheory - 2][k] = myClass.getTheoryId() + "-"
                                + myClass.getCourse().getCourseName() + "-"
                                + myClass.getTheoryTime().get(i).getRoom();
                    }
                }
            }

            // Nếu là môn cuối cùng thì thêm board vào timeTableList
            // Ngược lại thì duyệt môn tiếp theo
            if (indexOfCourse >= allClassOfCourseList.size() - 1) {
                timeTableList.add(board);
            } else {
                organize(timeTableList, board, indexOfCourse + 1, 0, allClassOfCourseList);
            }
        }

        // Duyệt lớp tiếp theo (Lúc này sử dụng cloneBoard thay cho board hiện tại)
        organize(timeTableList, cloneBoard, indexOfCourse, indexOfClass + 1, allClassOfCourseList);
    }

    // public void printtimeTableList(MyArrayList<String[][]> timeTableList) {
    // for (int k = 0; k < timeTableList.size(); k++) {
    // System.out.println("\n\n\n");
    // System.out.printf("%-8s", "");
    // for (int i = 0; i < 5; i++) {
    // System.out.printf("%s%-15s", "|", "Thứ " + (i + 2));
    // }
    // System.out.println();
    // for (int j = 0; j < timeTableList.get(k)[0].length; j++) {
    // System.out.printf("%-8s", "Tiết" + (j + 1));
    // for (int l = 0; l < timeTableList.get(k).length; l++) {
    // System.out.printf("%s%-15s", "|", timeTableList.get(k)[l][j]);
    // }
    // System.out.println();
    // }
    // }
    // }
}