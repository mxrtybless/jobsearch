package kg.attractor.jobsearch.service;

import kg.attractor.jobsearch.dto.CustomerDto;

import java.util.List;

public interface CustomerService {
    List<CustomerDto> getCustomerList();

    CustomerDto findById(Integer id);
}
