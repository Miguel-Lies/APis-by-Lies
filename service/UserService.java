package com.enterprise.bank_lies.service;

import com.enterprise.bank_lies.dto.UserDTO;
import com.enterprise.bank_lies.entity.AddressOfUser;
import com.enterprise.bank_lies.entity.BankDataAccountUser;
import com.enterprise.bank_lies.repository.AccountUserRepository;
import com.enterprise.bank_lies.repository.AddressRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    AccountUserRepository repositoryAccountUser;

    @Autowired
    AddressRepository repositoryAddress;

    @Transactional
    public void toCreateAccount(UserDTO userDTO){
        BankDataAccountUser bankDataAccountUser=new BankDataAccountUser();
        AddressOfUser addressOfUser=new AddressOfUser();

        bankDataAccountUser.setName(userDTO.getName());
        bankDataAccountUser.setEmail(userDTO.getEmail());
        bankDataAccountUser.setPassword(userDTO.getPassword());
        bankDataAccountUser.setDateOfBirth(userDTO.getDateOfBirth());
        addressOfUser.setCountryOfOrigin(userDTO.getCountry());
        addressOfUser.setState(userDTO.getState());
        addressOfUser.setCity(userDTO.getCity());
        addressOfUser.setNeighbordhood(userDTO.getNeighborhood());
        addressOfUser.setHouseNumber(userDTO.getNumber());

        repositoryAccountUser.save(bankDataAccountUser);
        repositoryAddress.save(addressOfUser);
    }

    public void deleteInformation(Integer id){
        repositoryAccountUser.deleteById(id);
        repositoryAddress.deleteById(id);
    }
}
