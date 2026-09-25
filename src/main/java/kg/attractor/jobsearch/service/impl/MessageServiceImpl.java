package kg.attractor.jobsearch.service.impl;

import kg.attractor.jobsearch.model.Message;
import kg.attractor.jobsearch.repository.MessageRepository;
import kg.attractor.jobsearch.service.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class MessageServiceImpl implements MessageService {
    private final MessageRepository messageRepository;
    private final DiscussionService discussionService;
    private final UserService userService;

    @Override
    @Transactional(readOnly = true)
    public List<Message> list(Integer responseId, String email) {
        discussionService.get(responseId, email);
        return messageRepository.findByRespondedApplicant_IdOrderByTimestampAscIdAsc(responseId);
    }

    @Override
    @Transactional
    public void send(Integer responseId, String content, String email) {
        var discussion = discussionService.get(responseId, email);
        if (content == null || content.isBlank() || content.length() > 2000) {
            throw new IllegalArgumentException("Invalid message length");
        }
        Message message = new Message();
        message.setRespondedApplicant(discussion);
        message.setSender(userService.findByEmail(email).orElseThrow());
        message.setContent(content.trim());
        message.setTimestamp(LocalDateTime.now());
        messageRepository.save(message);
        log.info("User {} sent a message in discussion {}", message.getSender().getId(), responseId);
    }
}
