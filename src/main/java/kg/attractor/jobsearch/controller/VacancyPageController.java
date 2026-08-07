package kg.attractor.jobsearch.controller;

import kg.attractor.jobsearch.service.VacancyService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("vacancies")
@RequiredArgsConstructor
public class VacancyPageController {

    private final VacancyService vacancyService;

    @GetMapping
    public String getVacancyList(
            Model model
    ) {
        model.addAttribute(
                "vacancies",
                vacancyService.findAllActive()
        );

        return "vacancies/list";
    }
}