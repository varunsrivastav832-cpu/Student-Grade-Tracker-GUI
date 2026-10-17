# Student Grade Tracker (Java Swing GUI)

A desktop-based student grade management application built with Java Swing.

## Features
- Add students with name, roll number, and marks.
- Validate required fields, numeric values, marks range (0–100), and duplicate roll numbers.
- Display student records in a table with Pass/Fail results.
- Calculate average, highest, and lowest marks.
- Show a summary report and total student count.
- Load sample records, reset input fields, and clear all records.
- Uses an `ArrayList` to store student records while the app is running.

## Requirements
- Java JDK installed (JDK 8 or newer recommended)
- VS Code or another Java IDE (optional)

## Run on macOS
1. Open Terminal.
2. Change directory to this folder. Example:
   ```bash
   cd ~/Downloads/Student_Grade_Tracker_GUI
   ```
3. Compile:
   ```bash
   javac StudentGradeTrackerGUI.java
   ```
4. Run:
   ```bash
   java StudentGradeTrackerGUI
   ```

## Notes
- Passing marks are set to 40 out of 100 in this version.
- Records are kept in memory while the application is open. They are not saved to a database or file.
- Test the program on your own computer and take your own screenshots before submitting.
