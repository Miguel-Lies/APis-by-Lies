package com.enterprise.bank_lies.service;

import com.enterprise.bank_lies.dto.UserDTO;
import com.enterprise.bank_lies.entity.AddressOfUser;
import com.enterprise.bank_lies.entity.BankDataAccountUser;
import com.enterprise.bank_lies.exceptions.InvalidPasswordException;
import com.enterprise.bank_lies.exceptions.NotFoundUserException;
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
        bankDataAccountUser.setNumberOfUser(userDTO.getNumberOfUser());
        bankDataAccountUser.setPassword(userDTO.getPassword());
        bankDataAccountUser.setDateOfBirth(userDTO.getDateOfBirth());
        addressOfUser.setCountryOfOrigin(userDTO.getCountry());
        addressOfUser.setState(userDTO.getState());
        addressOfUser.setCity(userDTO.getCity());
        addressOfUser.setNeighbordhood(userDTO.getNeighborhood());
        addressOfUser.setHouseNumber(userDTO.getNumber());

        bankDataAccountUser = repositoryAccountUser.save(bankDataAccountUser);

        addressOfUser.setUserIdWithAddress(bankDataAccountUser);

        repositoryAddress.save(addressOfUser);
    }

    public void deleteInformation(Integer id, String password){

        BankDataAccountUser bankDataAccountUser=repositoryAccountUser.findById(id)
                .orElseThrow(()-> new NotFoundUserException("Not found user."));

        if(!password.equals(bankDataAccountUser.getPassword())) {
            throw new InvalidPasswordException("Invalid password, try again.");
        }
            repositoryAccountUser.deleteById(id);
            repositoryAddress.deleteById(id);
        }
    }

