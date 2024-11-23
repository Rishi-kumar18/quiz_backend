package edu.annauniv.dist.webtech.quiz_backend.Controllers;

import edu.annauniv.dist.webtech.quiz_backend.Models.QuestionModel;
import edu.annauniv.dist.webtech.quiz_backend.Models.QuestionResponse;
import edu.annauniv.dist.webtech.quiz_backend.Views.QuestionView;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/questions")
public class QuestionController {

    private final QuestionView questionView;

    public QuestionController() {
        this.questionView = new QuestionView();
    }

    @PostMapping("/add")
    public void addQuestions(@RequestParam String testName, @RequestBody List<QuestionModel> questions) {
        questionView.createQuestions(testName, questions);
    }

    @PostMapping("/fetch")
    public QuestionResponse getQuestionsByTestName(@RequestParam String testName) {
        return questionView.fetchQuestions(testName);
    }
}