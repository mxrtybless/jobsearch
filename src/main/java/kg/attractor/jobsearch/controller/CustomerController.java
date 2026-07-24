package kg.attractor.jobsearch.controller;

import jakarta.validation.Valid;
import kg.attractor.jobsearch.dto.CustomerDto;
import kg.attractor.jobsearch.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("customers")
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerService customerService;

    @GetMapping
    public List<CustomerDto> getList() {
        return customerService
                .getCustomerList();
    }

    @GetMapping("{email}")
    public CustomerDto findByEmail(
            @PathVariable String email
    ) {
        return customerService.findById(
                email
        );
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createCustomer(
            @Valid
            @RequestBody
            CustomerDto customerDto
    ) {
        customerService.save(customerDto);
    }
}