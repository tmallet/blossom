package fr.blossom.autoconfigure.ui.web.administration;

import fr.blossom.autoconfigure.ui.common.privileges.MembershipPrivilegesConfiguration;
import fr.blossom.autoconfigure.ui.web.WebInterfaceAutoConfiguration;
import fr.blossom.core.association_user_group.AssociationUserGroupService;
import fr.blossom.core.group.GroupService;
import fr.blossom.core.user.UserService;
import fr.blossom.ui.web.administration.membership.MembershipsController;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@ConditionalOnClass({MembershipsController.class})
@ConditionalOnBean({AssociationUserGroupService.class, UserService.class, GroupService.class})
@AutoConfigureAfter(WebInterfaceAutoConfiguration.class)
@Import(MembershipPrivilegesConfiguration.class)
public class WebAdministrationMembershipAutoConfiguration {

    @Bean
    public MembershipsController membershipsController(AssociationUserGroupService associationUserGroupService, UserService userService, GroupService groupService) {
        return new MembershipsController(associationUserGroupService, userService, groupService);
    }

}
