package kg.attractor.jobsearch.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Message {
    private Integer id;

    private Integer respondedApplicantId;

    private String content;
    private LocalDateTime timestamp;
}