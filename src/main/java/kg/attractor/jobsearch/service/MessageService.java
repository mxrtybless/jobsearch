package kg.attractor.jobsearch.service;
import kg.attractor.jobsearch.model.Message;
import java.util.List;

public interface MessageService {
    List<Message> list(Integer responseId, String email);
    void send(Integer responseId, String content, String email);
}
