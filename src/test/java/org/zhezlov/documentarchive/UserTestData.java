package org.zhezlov.documentarchive;


import org.zhezlov.documentarchive.model.Role;
import org.zhezlov.documentarchive.model.User;

import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;

public class UserTestData {

    public static Set<Role> rolesAdmin;
    public static Set<Role> rolesUser;
    static  {
        rolesUser = new HashSet<>();
        rolesUser.add(Role.USER);
        rolesAdmin = new HashSet<>();
        rolesAdmin.add(Role.ADMIN);
    }
    public static final User admin = new User(1L, "andro1d1", "$2a$11$uSXS6rLJ91WjgOHhEGDx..VGs7MkKZV68Lv5r1uwFu7HgtRn3dcXG", rolesAdmin);
    public static final User user = new User(2L, "user", "$2a$11$uSXS6rLJ91WjgOHhEGDx..VGs7MkKZV68Lv5r1uwFu7HgtRn3dcXG", rolesUser);

    public static User getNew() {
        return new User(3L, "Buddy", "$2a$11$95z575PsVj2aJEr3y5sH0utaClZi/g76xdHSGfr8cYcQONiFc9Jbe", Collections.singleton(Role.USER));
    }

    public static void assertMatch(User actual, User expected) {
        assertThat(actual).usingRecursiveComparison().ignoringFields("confirmPassword").isEqualTo(expected);
    }

    public static void assertMatch(Iterable<User> actual, User... expected) {
        assertMatch(actual, Arrays.asList(expected));
    }
    public static void assertMatch(Iterable<User> actual, Iterable<User> expected) {
        assertThat(actual).usingElementComparatorIgnoringFields("confirmPassword").isEqualTo(expected);
    }
}
