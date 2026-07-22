package local.benchmark.spring;

import org.springframework.stereotype.Service;

@Service
public class FakeUserRepository {
    public <T> T save(T entity) {
        return entity;
    }

    public SupportModels.Account currentAccount() {
        SupportModels.Account account = new SupportModels.Account();
        account.setId(1001L);
        account.setDisplayName("current-user");
        account.setEmail("current@example.test");
        account.setAdmin(false);
        account.setRole("USER");
        account.setCreditLimit(100);
        return account;
    }
}
