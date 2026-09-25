package kg.attractor.jobsearch.controller;

import kg.attractor.jobsearch.service.DiscussionService;
import kg.attractor.jobsearch.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@Controller
@RequestMapping("applicants")
@RequiredArgsConstructor
public class ApplicantController {
    private final UserService userService;
    private final DiscussionService discussionService;

    @GetMapping("{id}")
    public String details(@PathVariable Integer id, @RequestParam(defaultValue = "1") int page, Model model) {
        var applicant = userService.findProfileById(id);
        if (!applicant.isApplicant()) throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        var result = discussionService.applicantResumes(id, page);
        model.addAttribute("applicant", applicant);
        model.addAttribute("resumes", result.getContent());
        model.addAttribute("currentPage", result.getNumber() + 1);
        model.addAttribute("totalPages", result.getTotalPages());
        return "applicants/details";
    }
}
