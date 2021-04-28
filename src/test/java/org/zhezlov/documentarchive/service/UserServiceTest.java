package org.zhezlov.documentarchive.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit4.SpringRunner;
import org.zhezlov.documentarchive.UserTestData;
import org.zhezlov.documentarchive.model.User;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@ContextConfiguration({
        "classpath:appconfig-root.xml"
})
@RunWith(SpringRunner.class)
@Sql(scripts = "classpath:populateDB.sql", config = @SqlConfig(encoding = "UTF-8"))
public class UserServiceTest {


    @Autowired
    UserService userService;

    @Test
    public void save() {
        User newUser = UserTestData.getNew();
        userService.save(newUser);
        User createdUser = userService.findByUsername(newUser.getUsername());
        assertThat(createdUser).usingRecursiveComparison().ignoringFields("confirmPassword", "password").isEqualTo(UserTestData.getNew());
    }

    @Test
    public void findByUsername() {
        User user = userService.findByUsername(UserTestData.admin.getUsername());
//        Assert.assertEquals(user.getId(), UserTestData.admin.getId());
//        Assert.assertEquals(UserTestData.admin.getUsername(), user.getUsername());
//        Assert.assertEquals(UserTestData.admin.getPassword(), user.getPassword());
//        Assert.assertEquals(UserTestData.admin.getRoles(), user.getRoles());
        UserTestData.assertMatch(user, UserTestData.admin);

    }

    @Test
    public void findAll() {
        List<User> users = userService.findAll();
        UserTestData.assertMatch(users, UserTestData.admin, UserTestData.user);
    }

    @Test
    public void getNameLoggedUser() {
    }
}