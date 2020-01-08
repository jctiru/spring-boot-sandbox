package io.jctiru.springbootsandbox.service;

import java.util.List;

import io.jctiru.springbootsandbox.shared.dto.AddressDto;

public interface AddressService {

	List<AddressDto> getAddresses(String userId);

}
