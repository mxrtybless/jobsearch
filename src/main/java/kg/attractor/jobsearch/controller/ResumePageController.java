package kg.attractor.jobsearch.controller;

import kg.attractor.jobsearch.service.ResumeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("resumes")
@RequiredArgsConstructor
public class ResumePageController {

    private final ResumeService resumeService;

    @GetMapping
    public String getResumeList(
            Model model
    ) {
        model.addAttribute(
                "resumes",
                resumeService.findAllActive()
        );

        return "resumes/list";
    }
}