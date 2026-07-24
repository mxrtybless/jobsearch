package kg.attractor.jobsearch.service;

import kg.attractor.jobsearch.dto.CustomerDto;

import java.util.List;

public interface CustomerService {
    List<CustomerDto> getCustomerList();

    CustomerDto findById(String email);

    void save(CustomerDto dto);
}