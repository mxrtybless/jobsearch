package kg.attractor.jobsearch.controller;

import jakarta.validation.Valid;
import kg.attractor.jobsearch.dto.ContactInfoDto;
import kg.attractor.jobsearch.dto.EducationInfoDto;
import kg.attractor.jobsearch.dto.ResumeDto;
import kg.attractor.jobsearch.dto.WorkExperienceInfoDto;
import kg.attractor.jobsearch.exception.CategoryNotFoundException;
import kg.attractor.jobsearch.exception.InvalidContactValueException;
import kg.attractor.jobsearch.exception.InvalidEducationPeriodException;
import kg.attractor.jobsearch.model.ContactType;
import kg.attractor.jobsearch.service.CategoryService;
import kg.attractor.jobsearch.service.ContactTypeService;
import kg.attractor.jobsearch.service.ResumeService;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("resumes")
@RequiredArgsConstructor
public class ResumePageController {

    private final ResumeService resumeService;
    private final CategoryService categoryService;
    private final ContactTypeService contactTypeService;

    private final kg.attractor.jobsearch.service.DiscussionService discussionService;

    @GetMapping
    public String getResumeList(@RequestParam(defaultValue = "1") int page,
                                @RequestParam(required = false) Integer category,
                                @RequestParam(defaultValue = "dateDesc") String sort,
                                Model model) {
        var result = discussionService.resumes(category, page, sort);
        model.addAttribute("sort", sort);
        model.addAttribute("resumes", result.getContent());
        model.addAttribute("currentPage", result.getNumber() + 1);
        model.addAttribute("totalPages", result.getTotalPages());
        model.addAttribute("categories", categoryService.findAll());
        model.addAttribute("category", category);
        return "resumes/list";
    }

    @GetMapping("{id}")
    public String details(@PathVariable Integer id, Authentication authentication, Model model) {
        model.addAttribute("resume", discussionService.resume(id, authentication.getName()));
        model.addAttribute("ownVacancies", discussionService.ownVacancies(authentication.getName()));
        return "resumes/details";
    }

    @GetMapping("form/create")
    public String createResume(Model model) {
        ResumeDto resumeDto = new ResumeDto();
        resumeDto.setIsActive(true);

        addFormData(model, resumeDto, "create", null);

        return "resumes/form";
    }

    @PostMapping("form/create")
    public String createResume(
            @Valid @ModelAttribute("resumeDto") ResumeDto resumeDto,
            BindingResult bindingResult,
            Authentication authentication,
            Model model
    ) {
        if (bindingResult.hasErrors()) {
            addFormData(model, resumeDto, "create", null);
            return "resumes/form";
        }

        try {
            resumeService.createResume(
                    resumeDto,
                    authentication.getName()
            );
        } catch (InvalidContactValueException
                 | InvalidEducationPeriodException exception) {
            bindingResult.reject(
                    "resume.invalid",
                    exception.getMessage()
            );

            addFormData(model, resumeDto, "create", null);
            return "resumes/form";
        } catch (CategoryNotFoundException exception) {
            bindingResult.rejectValue(
                    "categoryId",
                    "category.notFound"
            );

            addFormData(model, resumeDto, "create", null);
            return "resumes/form";
        }

        return "redirect:/profile?resumeCreated=true";
    }

    @GetMapping("form/edit/{id}")
    public String editResume(
            @PathVariable Integer id,
            Authentication authentication,
            Model model
    ) {
        ResumeDto resumeDto = resumeService.findOwnedById(
                id,
                authentication.getName()
        );

        addFormData(model, resumeDto, "edit", id);

        return "resumes/form";
    }

    @PostMapping("form/edit/{id}")
    public String editResume(
            @PathVariable Integer id,
            @Valid @ModelAttribute("resumeDto") ResumeDto resumeDto,
            BindingResult bindingResult,
            Authentication authentication,
            Model model
    ) {
        if (bindingResult.hasErrors()) {
            addFormData(model, resumeDto, "edit", id);
            return "resumes/form";
        }

        try {
            resumeService.editResume(
                    id,
                    resumeDto,
                    authentication.getName()
            );
        } catch (InvalidContactValueException
                 | InvalidEducationPeriodException exception) {
            bindingResult.reject(
                    "resume.invalid",
                    exception.getMessage()
            );

            addFormData(model, resumeDto, "edit", id);
            return "resumes/form";
        } catch (CategoryNotFoundException exception) {
            bindingResult.rejectValue(
                    "categoryId",
                    "category.notFound"
            );

            addFormData(model, resumeDto, "edit", id);
            return "resumes/form";
        }

        return "redirect:/profile?resumeUpdated=true";
    }

    @PostMapping("form/update/{id}")
    public String updateResumeDate(
            @PathVariable Integer id,
            Authentication authentication
    ) {
        resumeService.updateResumeDate(
                id,
                authentication.getName()
        );

        return "redirect:/profile?resumeRefreshed=true";
    }

    private void addFormData(
            Model model,
            ResumeDto resumeDto,
            String formMode,
            Integer resumeId
    ) {
        prepareContactInfo(resumeDto);
        prepareResumeDetails(resumeDto);

        model.addAttribute("resumeDto", resumeDto);
        model.addAttribute("categories", categoryService.findAll());
        model.addAttribute("contactTypes", contactTypeService.findAll());
        model.addAttribute("formMode", formMode);

        if (resumeId != null) {
            model.addAttribute("resumeId", resumeId);
        }
    }

    private void prepareContactInfo(ResumeDto resumeDto) {
        List<ContactType> contactTypes = contactTypeService.findAll();
        Map<Integer, ContactInfoDto> existing = new HashMap<>();

        if (resumeDto.getContactInfo() != null) {
            for (ContactInfoDto contact : resumeDto.getContactInfo()) {
                if (contact != null && contact.getTypeId() != null) {
                    existing.put(contact.getTypeId(), contact);
                }
            }
        }

        List<ContactInfoDto> normalized = new ArrayList<>();

        for (ContactType contactType : contactTypes) {
            ContactInfoDto contact = existing.get(contactType.getId());

            if (contact == null) {
                contact = new ContactInfoDto();
                contact.setTypeId(contactType.getId());
            }

            normalized.add(contact);
        }

        resumeDto.setContactInfo(normalized);
    }

    private void prepareResumeDetails(ResumeDto resumeDto) {
        if (resumeDto.getWorkExperienceInfo() == null) {
            resumeDto.setWorkExperienceInfo(new ArrayList<>());
        }

        if (resumeDto.getWorkExperienceInfo().isEmpty()) {
            resumeDto.getWorkExperienceInfo().add(
                    new WorkExperienceInfoDto()
            );
        }

        if (resumeDto.getEducationInfo() == null) {
            resumeDto.setEducationInfo(new ArrayList<>());
        }

        if (resumeDto.getEducationInfo().isEmpty()) {
            resumeDto.getEducationInfo().add(
                    new EducationInfoDto()
            );
        }
    }
}
