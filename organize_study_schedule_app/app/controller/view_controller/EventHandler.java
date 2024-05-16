package app.controller.view_controller;

import java.awt.Color;
import java.awt.Component;
import java.util.*;

import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SwingUtilities;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import app.model.course.CourseManager;
import app.model.course.MyClass;
import app.model.organization.ScheduleOrganization;
import app.model.user.RegisterCourses;
import app.view.main.AppUI;
import resource.arraylist.MyArrayList;
import resource.node.CourseNode;
import resource.treemap.TreeMap;

public class EventHandler {
    private CourseManager courseManager = CourseManager.getInstance();
    private RegisterCourses userRegister = RegisterCourses.getInstance();

    private MyArrayList<CourseNode<String, MyArrayList<MyClass>>> exerciseClassesList = courseManager
            .getExerciseClassesList();

    AppUI appUI;

    public EventHandler(AppUI appUI) {
        this.appUI = appUI;
        suggestionInputCourseIdHandler();
        fillSelectCourseSuggestion();
        chooseCourseIdBtnHandler();
        jTableCoursesListReadData();
        deleteCourseHandler();
        organizeSchedule();
        registedCoursesHandler();
    }

    public void suggestionInputCourseIdHandler() {
        List<String> courseIdList = new ArrayList<String>();
        JTextField inputCourseJFT = appUI.getInputCourseJFT();
        JList<String> jListSuggestion = appUI.getjListSuggestion();
        DefaultListModel<String> listModel = new DefaultListModel<>();
        jListSuggestion.setModel(listModel);
        jListSuggestion.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        readCourseId(courseIdList);

        inputCourseJFT.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                updateSuggestion(inputCourseJFT, courseIdList, jListSuggestion, listModel);
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                updateSuggestion(inputCourseJFT, courseIdList, jListSuggestion, listModel);
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                updateSuggestion(inputCourseJFT, courseIdList, jListSuggestion, listModel);
            }

        });
    }



    private void updateSuggestion(JTextField inputCourseJFT, List<String> courseList, JList jListSuggestion,
            DefaultListModel listModel) {

        if (inputCourseJFT.getText().equals("")) {
            listModel.clear();
            jListSuggestion.setBorder(null);
            return;
        }

        jListSuggestion.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        String courseId = inputCourseJFT.getText().toLowerCase();
        List<String> matchedCourses = new ArrayList<>();

        for (String course : courseList) {
            int index = course.trim().indexOf(" ");
            String courseIdStr = course.substring(0, index);
            if (courseIdStr.toLowerCase().contains(courseId)) {
                matchedCourses.add(course);
            }
        }

        // Hiển thị kết quả tìm kiếm trong JList
        listModel.clear();
        for (String matchedCourse : matchedCourses) {
            listModel.addElement(matchedCourse);
        }
    }

    public void fillSelectCourseSuggestion() {
        JList<String> jListSuggestion = appUI.getjListSuggestion();
        JTextField inputCourseJFT = appUI.getInputCourseJFT();

        jListSuggestion.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting()) {
                    SwingUtilities.invokeLater(() -> {
                        // Lấy giá trị được chọn và đưa vào JTextField
                        String selectedValue = jListSuggestion.getSelectedValue();
                        if (selectedValue != null) {
                            inputCourseJFT.setText(selectedValue);
                            DefaultListModel<String> model = (DefaultListModel<String>) jListSuggestion.getModel();
                            model.clear();
                            jListSuggestion.setBorder(null);
                        }
                    });
                }
            }
        });
    }

    private void readCourseId(List<String> courseIdList) {
        for (int i = 0; i < exerciseClassesList.size(); i++) {
            String courseId = exerciseClassesList.get(i).getKey() + " - " +
                    exerciseClassesList.get(i).getValue().get(0).getCourse().getCourseName();

            courseIdList.add(courseId);
        }
    }

    public void chooseCourseIdBtnHandler() {
        JButton chooseCourseIdBtn = appUI.getChooseCourseIdBtn();

        chooseCourseIdBtn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                JTextField inputCourseJFT = appUI.getInputCourseJFT();
                String text = inputCourseJFT.getText();

                if (text.equals("")) {
                    JOptionPane.showMessageDialog(null,
                            "Bạn chưa nhập Mã Học Phần",
                            "Message Dialog",
                            JOptionPane.INFORMATION_MESSAGE);
                    inputCourseJFT.setText("");
                    return;
                }
                String courseId = "";
                if (!text.trim().contains(" ")) {
                    courseId = text;
                } else {
                    courseId = text.substring(0, text.trim().indexOf(" "));
                }

                int checkCourseId = userRegister.registerCourse(courseId.trim().toUpperCase());

                // tìm kiếm xem csdl có mã học phần tương ứng hay không nếu không thì báo lỗi
                if (checkCourseId == -1) {
                    JOptionPane.showMessageDialog(null,
                            "Không tìm thấy Mã Học Phần tương ứng! Vui lòng nhập lại!",
                            "Message Dialog",
                            JOptionPane.INFORMATION_MESSAGE);
                    inputCourseJFT.setText("");

                    return;
                } else if (checkCourseId == 0) {
                    JOptionPane.showMessageDialog(null,
                            "Bạn đã đăng ký Mã Học Phần này rồi! Vui lòng nhập lại!",
                            "Message Dialog",
                            JOptionPane.INFORMATION_MESSAGE);
                    inputCourseJFT.setText("");

                    return;
                } else {
                    JOptionPane.showMessageDialog(null,
                            "Đăng ký thành công " + courseId.toUpperCase(),
                            "Message Dialog",
                            JOptionPane.INFORMATION_MESSAGE);
                    inputCourseJFT.setText("");

                }

                JTable previewRegistedCoursesJTable = appUI.getPreviewRegistedCoursesJTable();
                DefaultTableModel tableModel = (DefaultTableModel) previewRegistedCoursesJTable.getModel();

                MyArrayList<MyArrayList<MyClass>> registeredCoursesList = userRegister.getRegisteredCoursesList();
                MyClass myClass = registeredCoursesList.get(registeredCoursesList.size() - 1).get(0);

                String courseName = myClass.getCourse().getCourseName();
                String creditNumber = myClass.getCourse().getCreditNumber();
                String numberOfClass = registeredCoursesList.get(registeredCoursesList.size() - 1).size() + " ";

                tableModel.addRow(
                        new String[] { tableModel.getRowCount() + 1 + "", courseId.toUpperCase(), courseName,
                                creditNumber, numberOfClass });
            }
        });
    }

    public void jTableCoursesListReadData() {
        JTable jTableCoursesList = appUI.getjTableCoursesList();
        DefaultTableModel tableModel = (DefaultTableModel) jTableCoursesList.getModel();

        tableModel.setRowCount(0);

        for (int i = 0; i < exerciseClassesList.size(); i++) {
            String courseId = exerciseClassesList.get(i).getKey();
            String courseName = exerciseClassesList.get(i).getValue().get(0).getCourse().getCourseName();
            String creditNumber = exerciseClassesList.get(i).getValue().get(0).getCourse().getCreditNumber();
            String numberOfClass = exerciseClassesList.get(i).getValue().size() + "";

            tableModel.addRow(new Object[] { courseId, courseName, creditNumber, numberOfClass });

        }

    }

    public void deleteCourseHandler() {
        JButton deleteCourseBtn = appUI.getDeleteCourseBtn();
        JTable previewRegistedCoursesJTable = appUI.getPreviewRegistedCoursesJTable();
        DefaultTableModel tableModel = (DefaultTableModel) previewRegistedCoursesJTable.getModel();

        deleteCourseBtn.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                int selectedRow = previewRegistedCoursesJTable.getSelectedRow();
                if (selectedRow != -1) {
                    // Remove the selected row
                    int confirm = JOptionPane.showConfirmDialog(appUI,
                            "Bạn có chắc chắn muốn xóa môn học này không?", "Xác Nhận Xóa",
                            JOptionPane.YES_NO_OPTION);
                    if (confirm == JOptionPane.YES_OPTION) {
                        // xóa
                        tableModel.removeRow(selectedRow);
                        userRegister.removeRegisteredCourse(selectedRow);

                    } else {
                        return;
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Please select a row to delete.", "Error",
                            JOptionPane.ERROR_MESSAGE);
                }
            }

        });
    }

    private int indexTimeTable = 0;

    public void organizeSchedule() {
        JButton organizeScheduleBtn = appUI.getOrganizeCheduleBtn();
        JTable myTimeTable = appUI.getMyTimeTable();
        DefaultTableModel tableModel = (DefaultTableModel) myTimeTable.getModel();

        organizeScheduleBtn.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                registedCoursesHandler();

                if (userRegister.getRegisteredCoursesList().size() <= 0) {
                    updateOrganizeSchedule(tableModel, new String[5][10]);
                    JOptionPane.showMessageDialog(null, "Bạn chưa đăng ký học phần nào!", "Error",
                            JOptionPane.ERROR_MESSAGE);
                    return;
                }
                ScheduleOrganization scheduleOrganization = new ScheduleOrganization();
                MyArrayList<String[][]> boardList = scheduleOrganization.getTimeTableList();

                if (boardList.size() == 0 && userRegister.getRegisteredCoursesList().size() > 0) {
                    updateOrganizeSchedule(tableModel, new String[5][10]);
                    JOptionPane.showMessageDialog(null, "Không đăng kí được thời khóa biểu do trùng lịch!", "Error",
                            JOptionPane.ERROR_MESSAGE);
                    return;
                }

                String[][] firstTimeTable = boardList.get(0);

                for (int i = 0; i < 10; i++) {
                    tableModel.setValueAt("Tiết " + (i + 1), i, 0);
                }

                updateOrganizeSchedule(tableModel, firstTimeTable);
                nextTimeTableBtnHandler(tableModel, boardList);
                prevTimeTableBtn(tableModel, boardList);

                // thao tác tạo màu cho ô - start
                CustomTableCellRenderer cellRenderer = new CustomTableCellRenderer();
                myTimeTable.setDefaultRenderer(Object.class, cellRenderer); // Sử dụng renderer tùy chỉnh cho tất cả các
                                                                            // ô
                // end
            }
        });
    }

    class CustomTableCellRenderer extends DefaultTableCellRenderer {
        private TreeMap<String, Color> cellColors = new TreeMap<>(); // Sử dụng Map để ánh xạ giữa giá trị ô và màu sắc

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
                int row, int column) {
            Component rendererComponent = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row,
                    column);
            String cellValue = String.valueOf(value);

            if (cellValue.length() < 10) {
                rendererComponent.setBackground(Color.WHITE); // Gãn màu với màu trên
                return rendererComponent;
            }

            cellValue = cellValue.substring(0, 8);

            // Kiểm tra giá trị ô và xác định màu sắc tương ứng
            if (!cellColors.containsKey(cellValue)) {
                Random random = new Random();
                Color color = new Color(random.nextInt(256), random.nextInt(256), random.nextInt(256));
                cellColors.put(cellValue, color); // Gán màu cho giá trị ô
            }
            rendererComponent.setBackground(cellColors.get(cellValue)); // Áp dụng màu sắc vào ô

            return rendererComponent;
        }
    }

    public void nextTimeTableBtnHandler(DefaultTableModel tableModel, MyArrayList<String[][]> boardList) {

        JButton nextTimeTableBtn = appUI.getNextTimeTableBtn();
        nextTimeTableBtn.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                // size - 2 để ko bị lỗi next đến tkb cuối cùng
                if (indexTimeTable < boardList.size() - 1) {
                    indexTimeTable++;
                    updateOrganizeSchedule(tableModel, boardList.get(indexTimeTable));
                    return;
                }
                JOptionPane.showMessageDialog(null, "Không có thời khóa biểu tiếp theo!", "Error",
                        JOptionPane.ERROR_MESSAGE);

            }
        });

    }

    public void prevTimeTableBtn(DefaultTableModel tableModel, MyArrayList<String[][]> boardList) {
        JButton prevTimeTableBtn = appUI.getPrevTimeTableBtn();
        prevTimeTableBtn.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                if (indexTimeTable > 0) {
                    indexTimeTable--;
                    updateOrganizeSchedule(tableModel, boardList.get(indexTimeTable));
                    return;
                }
                JOptionPane.showMessageDialog(null, "Không có thời khóa biểu đằng trước!", "Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        });

    }

    private void updateOrganizeSchedule(DefaultTableModel tableModel, String[][] boardList) {
        for (int i = 0; i <= 9; i++) {
            for (int j = 1; j <= 5; j++) {
                tableModel.setValueAt(boardList[j - 1][i], i, j);
            }
        }
    }

    public void registedCoursesHandler() {
        JTable registedCoursesTable = appUI.getRegistedCoursesTable();
        DefaultTableModel tableModel = (DefaultTableModel) registedCoursesTable.getModel();
        tableModel.setRowCount(0);
        for (int i = 0; i < userRegister.getRegisteredCoursesList().size(); i++) {
            MyClass myClass = userRegister.getRegisteredCoursesList().get(i).get(0);
            String courseId = myClass.getCourse().getCourseId();
            String courseName = myClass.getCourse().getCourseName();
            String creditNumber = myClass.getCourse().getCreditNumber();
            String numberOfClass = userRegister.getRegisteredCoursesList().get(i).size() + "";
            tableModel.addRow(new String[] { courseId, courseName, creditNumber, numberOfClass });
        }
    }
}
