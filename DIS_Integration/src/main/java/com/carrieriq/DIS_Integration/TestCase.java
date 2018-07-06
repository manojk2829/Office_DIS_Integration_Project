package com.carrieriq.DIS_Integration;

public class TestCase {
    private String testCase;
    private int testCaseIndex;
//    private String testCaseDescription = null;

    public TestCase(String testCase, int testCaseIndex) {
        this.testCase = testCase;
        this.testCaseIndex = testCaseIndex;
        //this.testCaseDescription = testCaseDescription

    }

    public String getTestCase() {
        return testCase;
    }

    public int getTestCaseIndex() {
        return testCaseIndex;
    }

//    public String getTestCaseDescription() {
//        return testCaseDescription;
//    }
    @Override
    public String toString() { return "_" + testCase ;  }
}
