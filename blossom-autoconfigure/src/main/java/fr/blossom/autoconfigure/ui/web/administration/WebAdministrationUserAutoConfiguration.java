package fr.blossom.autoconfigure.ui.web.administration;

import fr.blossom.autoconfigure.ui.common.privileges.UserPrivilegesConfiguration;
import fr.blossom.autoconfigure.ui.web.WebInterfaceAutoConfiguration;
import fr.blossom.core.common.search.SearchEngineImpl;
import fr.blossom.core.user.UserDTO;
import fr.blossom.core.user.UserService;
import fr.blossom.ui.menu.MenuItem;
import fr.blossom.ui.menu.MenuItemBuilder;
import fr.blossom.ui.web.administration.user.UsersController;
import org.apache.tika.Tika;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * Created by Maël Gargadennnec on 04/05/2017.
 */
@Configuration
@ConditionalOnClass({UserService.class, UsersController.class})
@ConditionalOnBean(UserService.class)
@AutoConfigureAfter(WebInterfaceAutoConfiguration.class)
@Import(UserPrivilegesConfiguration.class)
public class WebAdministrationUserAutoConfiguration {
  private final UserPrivilegesConfiguration userPrivilegesConfiguration;

  public WebAdministrationUserAutoConfiguration(
    UserPrivilegesConfiguration userPrivilegesConfiguration) {
    this.userPrivilegesConfiguration = userPrivilegesConfiguration;
  }

  @Bean
  public MenuItem administrationUserMenuItem(MenuItemBuilder builder,
    @Qualifier("administrationMenuItem") MenuItem administrationMenuItem) {
    return builder
      .key("users")
      .label("menu.administration.users")
      .link("/blossom/administration/users")
      .icon("fa fa-user")
      .order(1)
      .privilege(userPrivilegesConfiguration.usersReadPrivilege())
      .parent(administrationMenuItem)
      .build();
  }

  @Bean
  public UsersController usersController(UserService userService, Tika tika,
    SearchEngineImpl<UserDTO> searchEngine) {
    return new UsersController(userService, searchEngine, tika);
  }
}
