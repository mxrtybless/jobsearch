package kg.attractor.jobsearch.controller;

import kg.attractor.jobsearch.service.DiscussionService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("responses")
@RequiredArgsConstructor
public class DiscussionController {
    private final DiscussionService discussionService;

    @GetMapping
    public String list(@RequestParam(required = false) Integer vacancyId,
                       Authentication authentication, Model model) {
        model.addAttribute("responses", discussionService.list(authentication.getName(), vacancyId));
        model.addAttribute("ownVacancies", discussionService.ownVacancies(authentication.getName()));
        model.addAttribute("vacancyId", vacancyId);
        return "responses/list";
    }

    @PostMapping("open")
    public String open(@RequestParam Integer resumeId, @RequestParam Integer vacancyId,
                       Authentication authentication) {
        Integer id = discussionService.open(resumeId, vacancyId, authentication.getName());
        return "redirect:/responses/" + id + "/chat";
    }
}
