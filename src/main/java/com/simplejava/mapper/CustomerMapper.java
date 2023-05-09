package com.simplejava.mapper;

import com.simplejava.customer.CustomerEntity;
import com.simplejava.customer.CustomerDto;
import com.simplejava.customer.CustomerEntity;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * Description :
 * User: Tanveer Haider
 * Date: 2/25/2023
 * Time: 12:13 AM
 */
@Mapper
public interface CustomerMapper {

    CustomerMapper MAPPER = Mappers.getMapper(CustomerMapper.class);

    CustomerDto mapToDto(CustomerEntity customer);

    CustomerEntity mapToEntity(CustomerDto customerDto);
}
