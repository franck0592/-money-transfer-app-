package com.techelevator.dao;

import com.techelevator.tenmo.dao.JdbcTransferTypeDao;
import com.techelevator.tenmo.dao.TransferTypeDao;
import com.techelevator.tenmo.model.TransferType;
import org.junit.Before;
import org.junit.Test;
import org.springframework.jdbc.core.JdbcTemplate;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TransferTypeDaoTests extends BaseDaoTests {
        private TransferTypeDao transferTypeDao;

        private static final TransferType TRANSFER_TYPE_1 = new TransferType(1, "Send");
        private static final TransferType TRANSFER_TYPE_2 = new TransferType(2, "Request");

        @Before
        public void setup() {
            JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
            transferTypeDao = new JdbcTransferTypeDao(jdbcTemplate);
        }

        @Test
        public void getTransferTypeById() {
            TransferType transferType = transferTypeDao.getTransferTypeById(TRANSFER_TYPE_1.getTransfertTypeId());

            assertEquals(TRANSFER_TYPE_1, transferType);
        }
}
