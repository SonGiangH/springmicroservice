package com.shopme.accountservice.service;

import com.shopme.accountservice.entity.Account;
import com.shopme.accountservice.model.AccountDTO;
import com.shopme.accountservice.repository.AccountRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

public interface AccountService {
    void add(AccountDTO accountDTO);
    void update(AccountDTO accountDTO);
    void updatePassword(AccountDTO accountDTO);
    void delete(Long id);
    List<AccountDTO> getAll();
    AccountDTO getAccountById(Long id);
}


@Service
@Transactional
class AccountServiceImpl implements AccountService {
    @Autowired
    ModelMapper modelMapper;

    @Autowired
    AccountRepository accountRepo;

    @Override
    public void add(AccountDTO accountDTO) {
        Account account = modelMapper.map(accountDTO, Account.class);
        account.setPassword(new BCryptPasswordEncoder().encode(accountDTO.getPassword()));

        accountRepo.save(account);

        // set ngc lai id cho accountDTO
        accountDTO.setId(account.getId());
    }

    @Override
    public void update(AccountDTO accountDTO) {
        // get account from repo using accountDTO id
        Account account = accountRepo.findById(accountDTO.getId()).get();

        if (account != null) {
            // map account and accountDTO skip password field
            modelMapper.typeMap(AccountDTO.class, Account.class)
                    .addMappings(mapper -> mapper.skip(Account::setPassword)).map(accountDTO, account);

            accountRepo.save(account);
        }

    }

    @Override
    public void updatePassword(AccountDTO accountDTO) {
        // get account from repo using accountDTO id
        Account account = accountRepo.findById(accountDTO.getId()).get();
        if (account != null) {
            account.setPassword(new BCryptPasswordEncoder().encode(accountDTO.getPassword()));
            accountRepo.save(account);
        }
    }

    @Override
    public void delete(Long id) {
        Account account = accountRepo.findById(id).get();
        if (account != null) {
            accountRepo.delete(account);
        }
    }

    @Override
    public List<AccountDTO> getAll() {
        List<AccountDTO> accountDTOList = new ArrayList<>();
        List<Account> accounts = accountRepo.findAll();

        accounts.forEach(account -> {
            accountDTOList.add(modelMapper.map(account, AccountDTO.class));
        });
         return accountDTOList;
    }

    @Override
    public AccountDTO getAccountById(Long id) {
        Account account = accountRepo.findById(id).get();
        if (account != null) {
            return modelMapper.map(account, AccountDTO.class);
        }
        return null;
    }
}
