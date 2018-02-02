package fr.blossom.autoconfigure.ui.common.privileges;

import fr.blossom.core.common.utils.privilege.Privilege;
import fr.blossom.core.common.utils.privilege.SimplePrivilege;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ArticlePrivilegesConfiguration {

  @Bean
  public Privilege articleReadPrivilegePlugin() {
    return new SimplePrivilege("content", "articles", "read");
  }

  @Bean
  public Privilege articleManagerWritePrivilegePlugin() {
    return new SimplePrivilege("content", "articles", "write");
  }

  @Bean
  public Privilege articleManagerCreatePrivilegePlugin() {
    return new SimplePrivilege("content", "articles", "create");
  }

  @Bean
  public Privilege articleManagerDeletePrivilegePlugin() {
    return new SimplePrivilege("content", "articles", "delete");
  }
}
