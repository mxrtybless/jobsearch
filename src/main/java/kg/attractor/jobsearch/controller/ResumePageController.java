package kg.attractor.jobsearch.controller;

import jakarta.validation.Valid;
import kg.attractor.jobsearch.dto.ContactInfoDto;
import kg.attractor.jobsearch.dto.EducationInfoDto;
import kg.attractor.jobsearch.dto.ResumeDto;
import kg.attractor.jobsearch.dto.WorkExperienceInfoDto;
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

    @GetMapping
    public String getResumeList(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "dateDesc") String sort,
            Model model
    ) {
        Page<ResumeDto> resumePage = resumeService.findAllActive(page, 6, sort);
        model.addAttribute("resumes", resumePage.getContent());
        model.addAttribute("currentPage", resumePage.getNumber() + 1);
        model.addAttribute("totalPages", resumePage.getTotalPages());
        model.addAttribute("sort", sort);
        return "resumes/list";
    }

    @GetMapping("form/create")
    public String createResume(
            Model model
    ) {
        ResumeDto resumeDto =
                new ResumeDto();

        resumeDto.setIsActive(true);

        prepareContactInfo(resumeDto);
        prepareSingleResumeDetails(resumeDto);

        addFormData(
                model,
                resumeDto,
                "create",
                null
        );

        return "resumes/form";
    }

    @PostMapping("form/create")
    public String createResume(
            @Valid
            @ModelAttribute("resumeDto")
            ResumeDto resumeDto,
            BindingResult bindingResult,
            Authentication authentication,
            Model model
    ) {
        prepareContactInfo(resumeDto);
        prepareSingleResumeDetails(resumeDto);

        if (bindingResult.hasErrors()) {
            addFormData(
                    model,
                    resumeDto,
                    "create",
                    null
            );

            return "resumes/form";
        }

        try {
            resumeService.createResume(
                    resumeDto,
                    authentication.getName()
            );
        } catch (
                InvalidContactValueException
                | InvalidEducationPeriodException e
        ) {
            bindingResult.reject(
                    "resume.invalid",
                    e.getMessage()
            );

            addFormData(
                    model,
                    resumeDto,
                    "create",
                    null
            );

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
        ResumeDto resumeDto =
                resumeService.findOwnedById(
                        id,
                        authentication.getName()
                );

        prepareContactInfo(resumeDto);
        prepareSingleResumeDetails(resumeDto);

        addFormData(
                model,
                resumeDto,
                "edit",
                id
        );

        return "resumes/form";
    }

    @PostMapping("form/edit/{id}")
    public String editResume(
            @PathVariable Integer id,
            @Valid
            @ModelAttribute("resumeDto")
            ResumeDto resumeDto,
            BindingResult bindingResult,
            Authentication authentication,
            Model model
    ) {
        prepareContactInfo(resumeDto);
        prepareSingleResumeDetails(resumeDto);

        if (bindingResult.hasErrors()) {
            addFormData(
                    model,
                    resumeDto,
                    "edit",
                    id
            );

            return "resumes/form";
        }

        try {
            resumeService.editResume(
                    id,
                    resumeDto,
                    authentication.getName()
            );
        } catch (
                InvalidContactValueException
                | InvalidEducationPeriodException e
        ) {
            bindingResult.reject(
                    "resume.invalid",
                    e.getMessage()
            );

            addFormData(
                    model,
                    resumeDto,
                    "edit",
                    id
            );

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
        model.addAttribute(
                "resumeDto",
                resumeDto
        );

        model.addAttribute(
                "categories",
                categoryService.findAll()
        );

        model.addAttribute(
                "contactTypes",
                contactTypeService.findAll()
        );

        model.addAttribute(
                "formMode",
                formMode
        );

        if (resumeId != null) {
            model.addAttribute(
                    "resumeId",
                    resumeId
            );
        }
    }

    private void prepareContactInfo(
            ResumeDto resumeDto
    ) {
        List<ContactType> contactTypes =
                contactTypeService.findAll();

        Map<Integer, ContactInfoDto> existing =
                new HashMap<>();

        if (resumeDto.getContactInfo()
                != null) {

            for (ContactInfoDto contactInfoDto
                    : resumeDto.getContactInfo()) {

                if (contactInfoDto != null
                        && contactInfoDto.getTypeId()
                        != null) {

                    existing.put(
                            contactInfoDto.getTypeId(),
                            contactInfoDto
                    );
                }
            }
        }

        List<ContactInfoDto> normalized =
                new ArrayList<>();

        for (ContactType contactType
                : contactTypes) {

            ContactInfoDto contactInfoDto =
                    existing.get(
                            contactType.getId()
                    );

            if (contactInfoDto == null) {
                contactInfoDto =
                        new ContactInfoDto();

                contactInfoDto.setTypeId(
                        contactType.getId()
                );
            }

            normalized.add(contactInfoDto);
        }

        resumeDto.setContactInfo(normalized);
    }

    private void prepareSingleResumeDetails(
            ResumeDto resumeDto
    ) {
        List<WorkExperienceInfoDto> workExperience =
                new ArrayList<>();

        if (resumeDto.getWorkExperienceInfo() != null
                && !resumeDto.getWorkExperienceInfo().isEmpty()) {
            workExperience.add(
                    resumeDto.getWorkExperienceInfo().get(0)
            );
        } else {
            workExperience.add(
                    new WorkExperienceInfoDto()
            );
        }

        resumeDto.setWorkExperienceInfo(
                workExperience
        );

        List<EducationInfoDto> education =
                new ArrayList<>();

        if (resumeDto.getEducationInfo() != null
                && !resumeDto.getEducationInfo().isEmpty()) {
            education.add(
                    resumeDto.getEducationInfo().get(0)
            );
        } else {
            education.add(
                    new EducationInfoDto()
            );
        }

        resumeDto.setEducationInfo(
                education
        );
    }

}
