package com.simplejava.customer;

import com.simplejava.aspect.LogExecutionTime;
import com.simplejava.aspect.LogRequest;
import com.simplejava.aspect.LogResponse;
import com.simplejava.mapper.CustomerMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Description :
 * User: Tanveer Haider
 * Date: 5/3/2023
 * Time: 7:37 PM
 */

@Slf4j
@RestController
@RequestMapping("/api/v1/customers")
@AllArgsConstructor
public class CustomerController {

    private final CustomerRepository repository;

    @GetMapping("/{id}")
    @LogRequest
    @LogResponse
    @LogExecutionTime
    public ResponseEntity<CustomerDto> findById(@PathVariable Long id)  {
        CustomerEntity customerById = repository.findById(id);
        CustomerDto customerDto = CustomerMapper.MAPPER.mapToDto(customerById);
        return ResponseEntity.ok().body(customerDto);
    }

    @GetMapping
    @LogRequest
    @LogResponse
    @LogExecutionTime
    public ResponseEntity<List<CustomerDto>> findBAll() {
        List<CustomerEntity> customerEntities= repository.findAll();
        List<CustomerDto> customerDtoList = customerEntities.stream().map(t ->CustomerMapper.MAPPER.mapToDto(t))
                .collect(Collectors.toList());
        return ResponseEntity.ok().body(customerDtoList);
    }

}
