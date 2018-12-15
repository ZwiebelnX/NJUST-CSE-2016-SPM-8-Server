package com.spm8.goodgoodstudyserver.dao;

import com.spm8.goodgoodstudyserver.entities.*;

public interface AccountDB {
        UserEntity getAccountbyUsername(String accountname);

}
