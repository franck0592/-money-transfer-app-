package com.techelevator.dao;

import com.techelevator.tenmo.dao.AccountDao;
import com.techelevator.tenmo.dao.JdbcAccountDao;
import com.techelevator.tenmo.dao.TransferDao;
import com.techelevator.tenmo.dao.UserDao;
import com.techelevator.tenmo.model.Account;
import com.techelevator.tenmo.model.Transfer;
import com.techelevator.tenmo.model.User;
import org.junit.Before;
import org.junit.Test;
import org.springframework.jdbc.core.JdbcTemplate;

public class AccountDaoTests extends BaseDaoTests {
        private AccountDao accountDao;
        private UserDao userDao;
        private TransferDao transferDao;

        @Before
        public void setup() {
            JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
            accountDao = new JdbcAccountDao(jdbcTemplate, userDao);
        }

        @Test
        public void getAccountByUser () {
            User user = new User();
            user = userDao.getUserById(1);
            Account account = accountDao.getAccountByUser(user);
            System.out.println(account);
        }

        @Test
        public void getAccountByAccountId () {
            Account account = accountDao.getAccountByAccountId(1);
            System.out.println(account);
        }

        @Test
        public void updateAccountUserFrom () {
            Transfer transfer = new Transfer();
            transfer = transferDao.getTransferById(1);
            accountDao.updateAccountUserFrom(transfer);
        }

        @Test
        public void updateAccountUserTo () {
            Transfer transfer = new Transfer();
            transfer = transferDao.getTransferById(1);
            accountDao.updateAccountUserTo(transfer);
        }

}
