package com.techelevator.dao;

import com.techelevator.tenmo.dao.JdbcTransferStatusDao;
import com.techelevator.tenmo.dao.TransferStatusDao;
import com.techelevator.tenmo.model.TransferStatus;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.jdbc.core.JdbcTemplate;

public class TransferStatusDaoTests extends BaseDaoTests {
        private TransferStatusDao transferStatusDao;

        private static final TransferStatus TRANSFER_STATUS_1 = new TransferStatus(1, "Pending");
        private static final TransferStatus TRANSFER_STATUS_2 = new TransferStatus(2, "Approved");
        private static final TransferStatus TRANSFER_STATUS_3 = new TransferStatus(3, "Rejected");

        @Before
        public void setup() {
            JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
            transferStatusDao = new JdbcTransferStatusDao(jdbcTemplate);
        }

        @Test
        public void getTransferStatusById() {

            Assert.assertEquals(TRANSFER_STATUS_1, transferStatusDao.getTransferStatusById(TRANSFER_STATUS_1.getTransferStatusId()));
        }


}
