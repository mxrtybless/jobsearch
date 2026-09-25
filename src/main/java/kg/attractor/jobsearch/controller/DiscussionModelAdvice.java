package kg.attractor.jobsearch.controller;

import kg.attractor.jobsearch.model.Resume;
import kg.attractor.jobsearch.service.DiscussionService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;
import java.util.List;

@ControllerAdvice(assignableTypes = VacancyPageController.class)
@RequiredArgsConstructor
public class DiscussionModelAdvice {
    private final DiscussionService discussionService;

    @ModelAttribute("ownResumes")
    public List<Resume> ownResumes(Authentication authentication) {
        return authentication == null ? List.of() : discussionService.ownResumes(authentication.getName());
    }
}
