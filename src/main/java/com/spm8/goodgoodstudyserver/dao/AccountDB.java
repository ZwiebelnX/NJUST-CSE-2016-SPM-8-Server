package com.spm8.goodgoodstudyserver.dao;

import com.spm8.goodgoodstudyserver.domain.UserEntity;

public interface AccountDB {
        UserEntity getAccountbyUsername(String accountname);

}
