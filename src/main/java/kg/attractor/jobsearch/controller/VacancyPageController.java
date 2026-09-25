package kg.attractor.jobsearch.controller;

import jakarta.validation.Valid;
import kg.attractor.jobsearch.dto.VacancyDto;
import kg.attractor.jobsearch.exception.CategoryNotFoundException;
import kg.attractor.jobsearch.exception.InvalidExperienceRangeException;
import kg.attractor.jobsearch.service.CategoryService;
import kg.attractor.jobsearch.service.UserService;
import kg.attractor.jobsearch.service.VacancyService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("vacancies")
@RequiredArgsConstructor
public class VacancyPageController {

    private final VacancyService vacancyService;
    private final CategoryService categoryService;
    private final UserService userService;

    private final kg.attractor.jobsearch.service.VacancySearchService vacancySearchService;

    @GetMapping
    public String getVacancyList(
            @Valid @ModelAttribute("filter") kg.attractor.jobsearch.dto.VacancyFilterDto filter,
            BindingResult errors, Model model
    ) {
        addSearchData(filter, errors, model);
        model.addAttribute("categories", categoryService.findAll());
        return "vacancies/list";
    }

    @GetMapping("filter")
    public String filterVacancies(
            @Valid @ModelAttribute("filter") kg.attractor.jobsearch.dto.VacancyFilterDto filter,
            BindingResult errors, Model model
    ) {
        addSearchData(filter, errors, model);
        return "vacancies/results";
    }

    private void addSearchData(kg.attractor.jobsearch.dto.VacancyFilterDto filter,
                               BindingResult errors, Model model) {
        if (errors.hasErrors()) {
            model.addAttribute("filterInvalid", true);
            return;
        }
        var result = vacancySearchService.search(filter);
        model.addAttribute("vacancies", result.getContent());
        model.addAttribute("currentPage", result.getNumber() + 1);
        model.addAttribute("totalPages", result.getTotalPages());
        model.addAttribute("totalElements", result.getTotalElements());
    }

    @GetMapping("{id}")
    public String vacancyDetails(
            @PathVariable Integer id,
            Model model
    ) {
        VacancyDto vacancy = vacancyService.findById(id);

        model.addAttribute("vacancy", vacancy);
        model.addAttribute(
                "author",
                userService.findProfileById(vacancy.getAuthorId())
        );

        return "vacancies/details";
    }

    @GetMapping("form/create")
    public String createVacancy(Model model) {
        VacancyDto vacancyDto = new VacancyDto();
        vacancyDto.setIsActive(true);

        addFormData(model, vacancyDto, "create", null);

        return "vacancies/form";
    }

    @PostMapping("form/create")
    public String createVacancy(
            @Valid @ModelAttribute("vacancyDto") VacancyDto vacancyDto,
            BindingResult bindingResult,
            Authentication authentication,
            Model model
    ) {
        if (bindingResult.hasErrors()) {
            addFormData(model, vacancyDto, "create", null);
            return "vacancies/form";
        }

        try {
            vacancyService.createVacancy(
                    vacancyDto,
                    authentication.getName()
            );
        } catch (InvalidExperienceRangeException e) {
            bindingResult.rejectValue(
                    "expTo",
                    "experience.range",
                    e.getMessage()
            );

            addFormData(model, vacancyDto, "create", null);
            return "vacancies/form";
        } catch (CategoryNotFoundException e) {
            bindingResult.rejectValue(
                    "categoryId",
                    "category.notFound",
                    e.getMessage()
            );

            addFormData(model, vacancyDto, "create", null);
            return "vacancies/form";
        }

        return "redirect:/profile?vacancyCreated=true";
    }

    @GetMapping("form/edit/{id}")
    public String editVacancy(
            @PathVariable Integer id,
            Authentication authentication,
            Model model
    ) {
        VacancyDto vacancyDto =
                vacancyService.findOwnedById(id, authentication.getName());

        addFormData(model, vacancyDto, "edit", id);

        return "vacancies/form";
    }

    @PostMapping("form/edit/{id}")
    public String editVacancy(
            @PathVariable Integer id,
            @Valid @ModelAttribute("vacancyDto") VacancyDto vacancyDto,
            BindingResult bindingResult,
            Authentication authentication,
            Model model
    ) {
        if (bindingResult.hasErrors()) {
            addFormData(model, vacancyDto, "edit", id);
            return "vacancies/form";
        }

        try {
            vacancyService.editVacancy(
                    id,
                    vacancyDto,
                    authentication.getName()
            );
        } catch (InvalidExperienceRangeException e) {
            bindingResult.rejectValue(
                    "expTo",
                    "experience.range",
                    e.getMessage()
            );

            addFormData(model, vacancyDto, "edit", id);
            return "vacancies/form";
        } catch (CategoryNotFoundException e) {
            bindingResult.rejectValue(
                    "categoryId",
                    "category.notFound",
                    e.getMessage()
            );

            addFormData(model, vacancyDto, "edit", id);
            return "vacancies/form";
        }

        return "redirect:/profile?vacancyUpdated=true";
    }

    @PostMapping("form/update/{id}")
    public String updateVacancyDate(
            @PathVariable Integer id,
            Authentication authentication
    ) {
        vacancyService.updateVacancyDate(
                id,
                authentication.getName()
        );

        return "redirect:/profile?vacancyRefreshed=true";
    }

    private void addFormData(
            Model model,
            VacancyDto vacancyDto,
            String formMode,
            Integer vacancyId
    ) {
        model.addAttribute("vacancyDto", vacancyDto);
        model.addAttribute("categories", categoryService.findAll());
        model.addAttribute("formMode", formMode);

        if (vacancyId != null) {
            model.addAttribute("vacancyId", vacancyId);
        }
    }
}
