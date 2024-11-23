package edu.annauniv.dist.webtech.quiz_backend.Models;

public class TestModel {
    private int testId;
    private String testName;
    private int testMarks;
    private String testStartTiming;
    private String testEndTiming;
    private String testLocation;
    private String testDate;
    private int testDuration;

    public int getTestId() {
        return testId;
    }

    public void setTestId(int testId) {
        this.testId = testId;
    }

    public String getTestName() {
        return testName;
    }

    public void setTestName(String testName) {
        this.testName = testName;
    }

    public int getTestMarks() {
        return testMarks;
    }

    public void setTestMarks(int testMarks) {
        this.testMarks = testMarks;
    }

    public String getTestStartTiming() {
        return testStartTiming;
    }

    public void setTestStartTiming(String testStartTiming) {
        this.testStartTiming = testStartTiming;
    }

    public String getTestEndTiming() {
        return testEndTiming;
    }

    public void setTestEndTiming(String testEndTiming) {
        this.testEndTiming = testEndTiming;
    }

    public String getTestLocation() {
        return testLocation;
    }

    public void setTestLocation(String testLocation) {
        this.testLocation = testLocation;
    }

    public String getTestDate() {
        return testDate;
    }

    public void setTestDate(String testDate) {
        this.testDate = testDate;
    }

    public int getTestDuration() {
        return testDuration;
    }

    public void setTestDuration(int testDuration) {
        this.testDuration = testDuration;
    }
}