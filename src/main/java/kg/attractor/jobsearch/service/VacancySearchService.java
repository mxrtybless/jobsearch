package kg.attractor.jobsearch.service;
import kg.attractor.jobsearch.dto.VacancyCardDto;
import kg.attractor.jobsearch.dto.VacancyFilterDto;
import org.springframework.data.domain.Page;

public interface VacancySearchService {
    Page<VacancyCardDto> search(VacancyFilterDto filter);
}
