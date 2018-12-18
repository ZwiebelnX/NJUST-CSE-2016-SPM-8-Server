package com.spm8.goodgoodstudyserver.dao;

import com.spm8.goodgoodstudyserver.entities.*;
import org.springframework.data.repository.CrudRepository;

public interface AccountDB{
        UserEntity getAccountByUsername(String accountname);

}
