package kg.attractor.jobsearch.service.impl;

import kg.attractor.jobsearch.dto.VacancyCardDto;
import kg.attractor.jobsearch.dto.VacancyFilterDto;
import kg.attractor.jobsearch.model.Vacancy;
import kg.attractor.jobsearch.repository.VacancyRepository;
import kg.attractor.jobsearch.service.VacancySearchService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class VacancySearchServiceImpl implements VacancySearchService {
    private final VacancyRepository vacancyRepository;

    @Override
    @Transactional(readOnly = true)
    public Page<VacancyCardDto> search(VacancyFilterDto filter) {
        String name = filter.getName() == null ? "" : filter.getName().trim();
        String order = filter.getSort() == null ? "dateDesc" : filter.getSort();
        int page = Math.max(0, filter.getPage() - 1);
        Sort sort = switch (order) {
            case "dateAsc" -> Sort.by("updateTime").ascending();
            case "salaryAsc" -> Sort.by("salary").ascending();
            case "salaryDesc" -> Sort.by("salary").descending();
            default -> Sort.by("updateTime").descending();
        };
        Page<Vacancy> result;
        if (order.equals("responsesDesc")) {
            result = vacancyRepository.searchActiveResponsesDesc(name, filter.getCategory(),
                    filter.getSalary(), filter.getExperience(), PageRequest.of(page, 20));
        } else if (order.equals("responsesAsc")) {
            result = vacancyRepository.searchActiveResponsesAsc(name, filter.getCategory(),
                    filter.getSalary(), filter.getExperience(), PageRequest.of(page, 20));
        } else {
            result = vacancyRepository.searchActive(name, filter.getCategory(),
                    filter.getSalary(), filter.getExperience(),
                    PageRequest.of(page, 20, sort.and(Sort.by("id").descending())));
        }
        return result.map(this::toCard);
    }

    private VacancyCardDto toCard(Vacancy v) {
        VacancyCardDto dto = new VacancyCardDto();
        dto.setId(v.getId());
        dto.setName(v.getName());
        dto.setDescription(v.getDescription());
        dto.setSalary(v.getSalary());
        dto.setExpFrom(v.getExpFrom());
        dto.setExpTo(v.getExpTo());
        dto.setCreatedDate(v.getCreatedDate());
        dto.setUpdateTime(v.getUpdateTime());
        dto.setAuthorId(v.getAuthor().getId());
        dto.setAuthorName(v.getAuthor().getName());
        dto.setCategoryName(v.getCategory().getName());
        return dto;
    }
}
