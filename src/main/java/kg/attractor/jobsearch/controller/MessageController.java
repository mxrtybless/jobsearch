package kg.attractor.jobsearch.controller;

import jakarta.validation.Valid;
import kg.attractor.jobsearch.dto.MessageFormDto;
import kg.attractor.jobsearch.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("responses/{id}/chat")
@RequiredArgsConstructor
public class MessageController {
    private final DiscussionService discussionService;
    private final MessageService messageService;

    @GetMapping
    public String chat(@PathVariable Integer id, Authentication authentication, Model model) {
        model.addAttribute("messageForm", new MessageFormDto());
        return page(id, authentication.getName(), model);
    }

    @PostMapping
    public String send(@PathVariable Integer id,
                       @Valid @ModelAttribute("messageForm") MessageFormDto form,
                       BindingResult errors, Authentication authentication, Model model) {
        if (errors.hasErrors()) {
            model.addAttribute("invalidMessage", true);
            return page(id, authentication.getName(), model);
        }
        messageService.send(id, form.getContent(), authentication.getName());
        return "redirect:/responses/" + id + "/chat";
    }

    private String page(Integer id, String email, Model model) {
        model.addAttribute("discussion", discussionService.get(id, email));
        model.addAttribute("messages", messageService.list(id, email));
        model.addAttribute("viewerEmail", email);
        return "responses/chat";
    }
}
