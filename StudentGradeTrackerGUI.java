import java.awt.*;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

public class StudentGradeTrackerGUI extends JFrame {
    private final ArrayList<Student> students = new ArrayList<>();
    private final JTextField nameField = new JTextField();
    private final JTextField rollField = new JTextField();
    private final JTextField marksField = new JTextField();
    private final DefaultTableModel tableModel;
    private final JTable studentTable;
    private final JLabel averageValue = new JLabel("—");
    private final JLabel highestValue = new JLabel("—");
    private final JLabel lowestValue = new JLabel("—");
    private final JLabel totalValue = new JLabel("0");

    private static final double PASSING_MARKS = 40.0;

    public StudentGradeTrackerGUI() {
        setTitle("Student Grade Tracker");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1050, 680);
        setMinimumSize(new Dimension(850, 560));
        setLocationRelativeTo(null);

        Color navy = new Color(25, 55, 95);
        Color blue = new Color(40, 115, 205);
        Color pale = new Color(242, 247, 253);
        Color border = new Color(215, 228, 244);

        JPanel root = new JPanel(new BorderLayout(14, 14));
        root.setBorder(new EmptyBorder(16, 16, 16, 16));
        root.setBackground(pale);
        setContentPane(root);

        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(navy);
        header.setBorder(new EmptyBorder(18, 22, 18, 22));

        JLabel title = new JLabel("Student Grade Tracker");
        title.setForeground(Color.WHITE);
        title.setFont(new Font("SansSerif", Font.BOLD, 27));
        JLabel subtitle = new JLabel("Manage student records and review grade summaries");
        subtitle.setForeground(new Color(225, 235, 248));
        subtitle.setFont(new Font("SansSerif", Font.PLAIN, 14));

        JPanel titleBox = new JPanel(new GridLayout(2, 1, 0, 5));
        titleBox.setOpaque(false);
        titleBox.add(title);
        titleBox.add(subtitle);
        header.add(titleBox, BorderLayout.CENTER);
        root.add(header, BorderLayout.NORTH);

        JPanel center = new JPanel(new BorderLayout(14, 14));
        center.setOpaque(false);
        root.add(center, BorderLayout.CENTER);

        JPanel left = new JPanel(new BorderLayout(0, 12));
        left.setOpaque(false);
        center.add(left, BorderLayout.CENTER);

        JPanel form = new JPanel(new GridBagLayout());
        form.setBackground(Color.WHITE);
        form.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(border),
                new EmptyBorder(14, 14, 14, 14)));
        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(5, 5, 5, 5);
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 1;

        addFormField(form, c, 0, "Student Name", nameField);
        addFormField(form, c, 1, "Roll Number", rollField);
        addFormField(form, c, 2, "Marks (0–100)", marksField);

        JButton addButton = new JButton("Add Student");
        addButton.setBackground(new Color(27, 155, 92));
        addButton.setForeground(Color.WHITE);
        addButton.setFocusPainted(false);
        addButton.setFont(new Font("SansSerif", Font.BOLD, 14));
        addButton.addActionListener(e -> addStudent());

        JButton resetButton = new JButton("Reset Fields");
        resetButton.addActionListener(e -> resetFields());

        JPanel formButtons = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 0));
        formButtons.setOpaque(false);
        formButtons.add(addButton);
        formButtons.add(resetButton);

        c.gridx = 0;
        c.gridy = 1;
        c.gridwidth = 3;
        c.weightx = 1;
        form.add(formButtons, c);
        left.add(form, BorderLayout.NORTH);

        String[] columns = {"S.No", "Name", "Roll Number", "Marks", "Result"};
        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        studentTable = new JTable(tableModel);
        studentTable.setRowHeight(29);
        studentTable.setFont(new Font("SansSerif", Font.PLAIN, 13));
        studentTable.getTableHeader().setFont(new Font("SansSerif", Font.BOLD, 13));
        studentTable.getTableHeader().setBackground(new Color(226, 237, 250));
        studentTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        JPanel tablePanel = new JPanel(new BorderLayout(0, 8));
        tablePanel.setBackground(Color.WHITE);
        tablePanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(border),
                new EmptyBorder(12, 12, 12, 12)));
        JLabel listTitle = new JLabel("Student List");
        listTitle.setFont(new Font("SansSerif", Font.BOLD, 18));
        listTitle.setForeground(navy);
        tablePanel.add(listTitle, BorderLayout.NORTH);
        tablePanel.add(new JScrollPane(studentTable), BorderLayout.CENTER);
        left.add(tablePanel, BorderLayout.CENTER);

        JPanel summary = new JPanel(new GridLayout(0, 1, 8, 8));
        summary.setPreferredSize(new Dimension(225, 0));
        summary.setBackground(Color.WHITE);
        summary.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(border),
                new EmptyBorder(16, 16, 16, 16)));
        JLabel summaryTitle = new JLabel("Summary Report");
        summaryTitle.setFont(new Font("SansSerif", Font.BOLD, 19));
        summaryTitle.setForeground(navy);
        summary.add(summaryTitle);
        summary.add(makeStat("Average Marks", averageValue, blue));
        summary.add(makeStat("Highest Marks", highestValue, new Color(25, 155, 100)));
        summary.add(makeStat("Lowest Marks", lowestValue, new Color(205, 65, 65)));
        summary.add(makeStat("Total Students", totalValue, new Color(120, 80, 190)));

        JLabel passNote = new JLabel("<html><b>Passing marks:</b> 40 and above</html>");
        passNote.setForeground(new Color(75, 90, 110));
        summary.add(passNote);
        center.add(summary, BorderLayout.EAST);

        JPanel bottom = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        bottom.setOpaque(false);
        JButton reportButton = new JButton("View Report");
        reportButton.addActionListener(e -> showReport());
        JButton sampleButton = new JButton("Load Sample Data");
        sampleButton.addActionListener(e -> loadSampleData());
        JButton clearButton = new JButton("Clear All");
        clearButton.addActionListener(e -> clearAll());
        bottom.add(reportButton);
        bottom.add(sampleButton);
        bottom.add(clearButton);
        root.add(bottom, BorderLayout.SOUTH);

        getRootPane().setDefaultButton(addButton);
    }

    private void addFormField(JPanel panel, GridBagConstraints c, int column,
                              String labelText, JTextField field) {
        JPanel box = new JPanel(new BorderLayout(0, 5));
        box.setOpaque(false);
        JLabel label = new JLabel(labelText);
        label.setFont(new Font("SansSerif", Font.BOLD, 13));
        field.setPreferredSize(new Dimension(150, 32));
        box.add(label, BorderLayout.NORTH);
        box.add(field, BorderLayout.CENTER);
        c.gridx = column;
        c.gridy = 0;
        c.gridwidth = 1;
        panel.add(box, c);
    }

    private JPanel makeStat(String labelText, JLabel value, Color color) {
        JPanel panel = new JPanel(new BorderLayout(0, 5));
        panel.setOpaque(false);
        JLabel label = new JLabel(labelText);
        label.setForeground(new Color(70, 80, 100));
        label.setFont(new Font("SansSerif", Font.PLAIN, 13));
        value.setForeground(color);
        value.setFont(new Font("SansSerif", Font.BOLD, 25));
        panel.add(label, BorderLayout.NORTH);
        panel.add(value, BorderLayout.CENTER);
        return panel;
    }

    private void addStudent() {
        String name = nameField.getText().trim();
        String rollText = rollField.getText().trim();
        String marksText = marksField.getText().trim();

        if (name.isEmpty() || rollText.isEmpty() || marksText.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill in all fields.",
                    "Missing Information", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int rollNo;
        double marks;
        try {
            rollNo = Integer.parseInt(rollText);
            marks = Double.parseDouble(marksText);
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this,
                    "Roll number must be a whole number and marks must be numeric.",
                    "Invalid Input", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (rollNo <= 0) {
            JOptionPane.showMessageDialog(this, "Roll number must be greater than zero.",
                    "Invalid Roll Number", JOptionPane.WARNING_MESSAGE);
            return;
        }
        if (marks < 0 || marks > 100) {
            JOptionPane.showMessageDialog(this, "Marks must be between 0 and 100.",
                    "Invalid Marks", JOptionPane.WARNING_MESSAGE);
            return;
        }
        for (Student student : students) {
            if (student.rollNo == rollNo) {
                JOptionPane.showMessageDialog(this, "This roll number already exists.",
                        "Duplicate Roll Number", JOptionPane.WARNING_MESSAGE);
                return;
            }
        }

        Student student = new Student(name, rollNo, marks);
        students.add(student);
        tableModel.addRow(new Object[] {
                students.size(), student.name, student.rollNo,
                String.format("%.2f", student.marks),
                student.marks >= PASSING_MARKS ? "Pass" : "Fail"
        });
        updateSummary();
        resetFields();
    }

    private void updateSummary() {
        if (students.isEmpty()) {
            averageValue.setText("—");
            highestValue.setText("—");
            lowestValue.setText("—");
            totalValue.setText("0");
            return;
        }

        double total = 0;
        double highest = students.get(0).marks;
        double lowest = students.get(0).marks;

        for (Student student : students) {
            total += student.marks;
            highest = Math.max(highest, student.marks);
            lowest = Math.min(lowest, student.marks);
        }

        averageValue.setText(String.format("%.2f", total / students.size()));
        highestValue.setText(String.format("%.2f", highest));
        lowestValue.setText(String.format("%.2f", lowest));
        totalValue.setText(String.valueOf(students.size()));
    }

    private void resetFields() {
        nameField.setText("");
        rollField.setText("");
        marksField.setText("");
        nameField.requestFocusInWindow();
    }

    private void clearAll() {
        if (students.isEmpty()) {
            return;
        }
        int choice = JOptionPane.showConfirmDialog(this,
                "Remove all student records?", "Confirm Clear",
                JOptionPane.YES_NO_OPTION);
        if (choice == JOptionPane.YES_OPTION) {
            students.clear();
            tableModel.setRowCount(0);
            updateSummary();
        }
    }

    private void loadSampleData() {
        if (!students.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Clear the current records before loading sample data.",
                    "Records Already Exist", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        students.add(new Student("Aman", 101, 85));
        students.add(new Student("Riya", 102, 92));
        students.add(new Student("Rahul", 103, 35));
        students.add(new Student("Neha", 104, 78));
        students.add(new Student("Sohan", 105, 60));

        for (int i = 0; i < students.size(); i++) {
            Student s = students.get(i);
            tableModel.addRow(new Object[] {
                    i + 1, s.name, s.rollNo, String.format("%.2f", s.marks),
                    s.marks >= PASSING_MARKS ? "Pass" : "Fail"
            });
        }
        updateSummary();
    }

    private void showReport() {
        if (students.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Add at least one student first.",
                    "No Records", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        StringBuilder report = new StringBuilder("STUDENT GRADE TRACKER REPORT\\n\\n");
        report.append("Name | Roll Number | Marks | Result\\n");
        report.append("------------------------------------\\n");
        for (Student s : students) {
            report.append(s.name).append(" | ")
                    .append(s.rollNo).append(" | ")
                    .append(String.format("%.2f", s.marks)).append(" | ")
                    .append(s.marks >= PASSING_MARKS ? "Pass" : "Fail")
                    .append("\\n");
        }
        report.append("\\nAverage: ").append(averageValue.getText());
        report.append("\\nHighest: ").append(highestValue.getText());
        report.append("\\nLowest: ").append(lowestValue.getText());
        report.append("\\nTotal students: ").append(totalValue.getText());

        JTextArea textArea = new JTextArea(report.toString(), 16, 42);
        textArea.setEditable(false);
        textArea.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 13));
        JOptionPane.showMessageDialog(this, new JScrollPane(textArea),
                "Summary Report", JOptionPane.INFORMATION_MESSAGE);
    }

    private static class Student {
        private final String name;
        private final int rollNo;
        private final double marks;

        private Student(String name, int rollNo, double marks) {
            this.name = name;
            this.rollNo = rollNo;
            this.marks = marks;
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception ignored) {
                // Use Java's default look and feel if the system style is unavailable.
            }
            new StudentGradeTrackerGUI().setVisible(true);
        });
    }
}
