package edu.annauniv.dist.webtech.quiz_backend.Controllers;

import edu.annauniv.dist.webtech.quiz_backend.Models.TestModel;
import edu.annauniv.dist.webtech.quiz_backend.Views.TestView;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/test")
@CrossOrigin
public class TestController {
    private final TestView testView;

    public TestController() {
        this.testView = new TestView();
    }

    @PostMapping("/create")
    public Map<String, Object> createTest(@RequestBody Map<String, String> payload) {
        Map<String, Object> response = new HashMap<>();
        try {
            String testName = payload.get("testName");
            int testMarks = Integer.parseInt(payload.get("testMarks"));
            String testStartTiming = payload.get("testStartTiming");
            String testEndTiming = payload.get("testEndTiming");
            String testLocation = payload.get("testLocation");
            String testDate = payload.get("testDate");
            int testDuration = Integer.parseInt(payload.get("testDuration"));

            testView.createTest(testName, testMarks, testStartTiming, testEndTiming, testLocation, testDate, testDuration);

            response.put("status", "success");
            response.put("message", "Test created successfully");
        } catch (Exception e) {
            response.put("status", "error");
            response.put("message", e.getMessage());
        }
        return response;
    }

    @GetMapping("/fetch")
    public List<TestModel> fetchTests() {
        return testView.fetchTests();
    }
}