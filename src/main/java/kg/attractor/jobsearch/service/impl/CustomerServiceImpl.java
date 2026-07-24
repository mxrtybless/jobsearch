package kg.attractor.jobsearch.service.impl;

import kg.attractor.jobsearch.dao.CustomerDao;
import kg.attractor.jobsearch.dto.CustomerDto;
import kg.attractor.jobsearch.exception.CustomerNotFoundException;
import kg.attractor.jobsearch.model.Customer;
import kg.attractor.jobsearch.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {
    private final CustomerDao customerDao;

    @Override
    public List<CustomerDto> getCustomerList() {
        List<Customer> customers = customerDao.findAll();

        return customers.stream()
                .map(e -> CustomerDto.builder()
                        .email(e.getEmail())
                        .name(e.getUsername())
                        .password(e.getPassword())
                        .build()
                )
                .toList();
    }

    @Override
    public CustomerDto findById(String email) {
        Customer customer = customerDao.findById(email)
                .orElseThrow(CustomerNotFoundException::new);

        return CustomerDto.builder()
                .email(customer.getEmail())
                .name(customer.getUsername())
                .password(customer.getPassword())
                .build();
    }

    @Override
    public void save(CustomerDto dto) {
        Customer customer = new Customer();
        customer.setEmail(dto.getEmail());
        customer.setUsername(dto.getName());
        customer.setPassword(dto.getPassword());

        customerDao.save(customer);
    }
}