package com.techelevator.dao;

import com.techelevator.tenmo.dao.*;
import com.techelevator.tenmo.model.Transfer;
import com.techelevator.tenmo.model.User;
import org.junit.Before;
import org.junit.Test;
import org.springframework.jdbc.core.JdbcTemplate;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class TransferDaoTests extends BaseDaoTests {
        private JdbcTransferDao transferDao;
        private AccountDao accountDao;
        private TransferStatusDao transferStatusDao;
        private TransferTypeDao transferTypeDao;
        private UserDao userDao;




        @Before
        public void setup() {
            JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
            transferDao =  new JdbcTransferDao(jdbcTemplate, accountDao, transferStatusDao, transferTypeDao);
            userDao=new JdbcUserDao(jdbcTemplate);
        }

    @Test
    public void getTransferById() {
            try {
                Transfer transfer = transferDao.getTransferById(3001);


                // Assertions
                assertEquals(3001, transfer.getTransferId());
                assertEquals(new BigDecimal("50.00"), transfer.getAmount());
                assertEquals(2001, transfer.getAccountFrom().getAccountId());
                assertEquals(2002, transfer.getAccountTo().getAccountId());
            }

        catch (NullPointerException e) {
            e.printStackTrace();
        }
        // Add more assertions for other attributes
    }

    @Test
    public void updateTransfer() {
            try {
        Transfer transfer = transferDao.getTransferById(3001);
        transfer.setAmount(new BigDecimal(100));
        transferDao.updateTransfer(transfer);

        Transfer updatedTransfer = transferDao.getTransferById(1);


        assertEquals(3001, updatedTransfer.getTransferId());
        assertEquals(new BigDecimal("100.00"), updatedTransfer.getAmount());
        assertEquals(2001, updatedTransfer.getAccountFrom().getAccountId());
        assertEquals(2002, updatedTransfer.getAccountTo().getAccountId());
        }
        catch (NullPointerException e) {
            e.printStackTrace();
        }
        // Add more assertions for other attributes
    }

    @Test
    public void getAllTransfersSent() {
        User user = userDao.getUserById(1001);

        List<Transfer> actualTransfers = transferDao.getAllTransferSent(user);
        List<Transfer> expectedTransfers = new ArrayList<>();

        assertEquals(expectedTransfers, actualTransfers);
    }

        @Test
        public void getAllTransferReceived() {
            User user = new User();
            user = userDao.getUserById(1001);
            System.out.println(transferDao.getAllTransferReceived(user));
        }

}
