package kg.attractor.jobsearch.repository;

import kg.attractor.jobsearch.model.Message;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MessageRepository
        extends JpaRepository<Message, Integer> {

    java.util.List<Message> findByRespondedApplicant_IdOrderByTimestampAscIdAsc(Integer responseId);

}
