package fr.blossom.ui.security;

import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetails;

public class LimitLoginAuthenticationProvider extends DaoAuthenticationProvider {

  private final LoginAttemptsService userLoginAttemptsService;

  @Autowired
  private HttpServletRequest request;


  public LimitLoginAuthenticationProvider(UserDetailsService userDetailsService,
    LoginAttemptsService userLoginAttemptsService) {
    super.setUserDetailsService(userDetailsService);
    this.userLoginAttemptsService = userLoginAttemptsService;
  }

  @Override
  public Authentication authenticate(Authentication authentication) throws AuthenticationException {
    final WebAuthenticationDetails details = (WebAuthenticationDetails) authentication.getDetails();

    if(userLoginAttemptsService.isBlocked(authentication.getName(), details.getRemoteAddress())){
      throw new LockedException("User is locked");
    }

    try {
      Authentication auth = super.authenticate(authentication);
      userLoginAttemptsService.successfulAttempt(authentication.getName(), details.getRemoteAddress());
      return auth;
    } catch (BadCredentialsException e) {
      userLoginAttemptsService.failAttempt(authentication.getName(), details.getRemoteAddress());
      throw e;
    }
  }
}