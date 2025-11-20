package edu.annauniv.dist.webtech.quiz_backend.Controllers;

import edu.annauniv.dist.webtech.quiz_backend.Models.MarksModel;
import edu.annauniv.dist.webtech.quiz_backend.Models.QuestionModel;
import edu.annauniv.dist.webtech.quiz_backend.Models.TestModel;
import edu.annauniv.dist.webtech.quiz_backend.Views.MarksView;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/marks")
public class MarksController {

    private static final Logger log = LoggerFactory.getLogger(MarksController.class);

    private final MarksView marksView;

    public MarksController() {
        this.marksView = new MarksView();
    }

    @PostMapping("/submit")
    public void submitMarks(@RequestBody List<MarksModel> marksList) {
        for (MarksModel marks : marksList) {
            marksView.insertMarks(marks);
        }
    }

    @PostMapping("/fetch")
    public List<Object> fetchMarks(@RequestParam int userId, @RequestParam int testId) {
        return marksView.fetchMarksAndCorrectCount(userId, testId);
    }

    @PostMapping("/fetchTestsByUser")
    public List<TestModel> fetchTestsByUser(@RequestParam int userId) {
        return marksView.fetchTestsByUserId(userId);
    }

    @PostMapping("/fetchTestIdsByUser")
    public int[] fetchTestIdsByUser(@RequestParam int userId) {
        return marksView.fetchTestIdsByUserId(userId);
    }

    @PostMapping("/fetchByTest")
    public List<Map<String, Object>> fetchByTest(@RequestParam int testId) {
        log.info("Request /marks/fetchByTest testId={}", testId);
        return marksView.fetchStudentMarksPercentageByTestId(testId);
    }
}