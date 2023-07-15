package com.example.quickprep.controllers;

import com.example.quickprep.exceptions.DocumentNotFoundException;
import com.example.quickprep.models.ThymeModel;
import com.example.quickprep.service.ApplicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PrepController {
    private ApplicationService applicationService;

    @Autowired
    public PrepController(ApplicationService applicationService) {
        this.applicationService = applicationService;
    }

    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("maintext", "Welcome to Quick Prep");
        model.addAttribute("thymeModel", new ThymeModel());
        return "index.html";
    }

    @GetMapping("getSummary")
    public String getSummary(ThymeModel thymeModel, Model model) throws DocumentNotFoundException {
        applicationService.clear();
        String summary = this.applicationService.getSummary("C:\\Users\\ketan\\Downloads\\quickprep\\quickprep\\src\\main\\resources\\" + thymeModel.getText() + ".txt");
        model.addAttribute("docsummary", summary);
        return "summary.html";
    }

    @GetMapping("getQuestions")
    public String getQuestions(Model model) {
        model.addAttribute("thymeModel", new ThymeModel());
        String questions = applicationService.getQuestions();
        model.addAttribute("questions", questions);
        return "questions.html";
    }

    @GetMapping("getResult")
    public String getAnswersAndSuggestions(ThymeModel thymeModel, Model model) {
        String response = applicationService.getAnswersAndSuggestions(thymeModel.getText());
        model.addAttribute("ansAndSuggestion", response);
        return "result.html";
    }
}
