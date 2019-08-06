package me.zidol.springbootjpa.account;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest    //슬라이스 테스트 : repository 관련 빈만 등록해서 테스트 할 수 있음(임베디드 디비사용)
//@SpringBootTest //application 전부 빈으로 등록되어서 application.properties를 사용
public class AccountRepositoryTest {
    @Autowired
    DataSource dataSource;

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Autowired
    AccountRepository accountRepository;

    @Test
    public void di() throws SQLException {
//        try (Connection connection = dataSource.getConnection()) {
//            DatabaseMetaData metaData = connection.getMetaData();
//            System.out.println(metaData.getURL());
//            System.out.println(metaData.getDriverName());
//            System.out.println(metaData.getUserName());
//        }

        Account account = new Account();
        account.setUsername("zidol");
        account.setPassword("1234");

        Account newAccount = accountRepository.save(account);

        assertThat(newAccount).isNotNull();

        Optional<Account> existringAccount = accountRepository.findByUsername(newAccount.getUsername());
        assertThat(existringAccount).isNotEmpty();
//        Account existringAccount = accountRepository.findByUsername(newAccount.getUsername());
//        assertThat(existringAccount).isNotEmpty();

        Optional<Account> nonExistringAccount = accountRepository.findByUsername("whieship");
        assertThat(nonExistringAccount).isEmpty();
//        Account nonExistringAccount = accountRepository.findByUsername("whieship");
//        assertThat(nonExistringAccount).isNull();

    }
}