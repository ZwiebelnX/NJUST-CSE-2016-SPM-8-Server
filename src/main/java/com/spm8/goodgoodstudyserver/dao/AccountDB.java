package com.spm8.goodgoodstudyserver.Dao;

import com.spm8.goodgoodstudyserver.Entities.*;

public interface AccountDB {
        UserEntity getAccountbyUsername(String accountname);

}
