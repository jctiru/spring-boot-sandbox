package io.jctiru.springbootsandbox.io.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import io.jctiru.springbootsandbox.io.entity.AddressEntity;
import io.jctiru.springbootsandbox.io.entity.UserEntity;

@Repository
public interface AddressRepository extends JpaRepository<AddressEntity, Long> {

	List<AddressEntity> findAllByUser(UserEntity user);

	AddressEntity findByAddressId(String addressId);

}
