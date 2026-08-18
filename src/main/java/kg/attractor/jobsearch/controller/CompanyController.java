package kg.attractor.jobsearch.controller;

import kg.attractor.jobsearch.dto.VacancyDto;
import kg.attractor.jobsearch.model.AccountType;
import kg.attractor.jobsearch.model.User;
import kg.attractor.jobsearch.service.UserService;
import kg.attractor.jobsearch.service.VacancyService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("companies")
@RequiredArgsConstructor
public class CompanyController {

    private final UserService userService;
    private final VacancyService vacancyService;

    @GetMapping
    public String companies(
            @RequestParam(defaultValue = "1") int page,
            Model model
    ) {
        Page<User> companyPage = userService.findEmployers(
                PageRequest.of(
                        Math.max(page, 1) - 1,
                        6,
                        Sort.by(Sort.Direction.ASC, "name")
                )
        );
        model.addAttribute("companies", companyPage.getContent());
        model.addAttribute("currentPage", companyPage.getNumber() + 1);
        model.addAttribute("totalPages", companyPage.getTotalPages());
        return "companies/list";
    }

    @GetMapping("{id}")
    public String companyDetails(
            @PathVariable Integer id,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "date") String sort,
            Model model
    ) {
        User company = userService.findProfileById(id);
        if (company.getAccountType() != AccountType.EMPLOYER) {
            throw new IllegalArgumentException("User is not an employer");
        }
        Page<VacancyDto> vacancyPage = vacancyService.findActiveByAuthorId(id, page, 6, sort);
        model.addAttribute("company", company);
        model.addAttribute("vacancies", vacancyPage.getContent());
        model.addAttribute("currentPage", vacancyPage.getNumber() + 1);
        model.addAttribute("totalPages", vacancyPage.getTotalPages());
        model.addAttribute("sort", sort);
        return "companies/details";
    }
}
