package com.tklk;

import java.util.Objects;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * Demonstrates a simple custom authentication provider.
 * <p>
 * A real AuthenticationProvider would consult the database or some external service to verify the
 * user.
 */
public class AdminAuthProvider implements AuthenticationProvider {

  @Override
  public Authentication authenticate(Authentication auth) throws AuthenticationException {
    // for the admin user, we ignore the password stored in auth.getCredentials()
    if (Objects.equals(auth.getName(), "admin")) {
      final UserDetails user = User.withUsername("admin")
          .password("** cleared-for-security-reasons **")
          .roles("ADMIN")
          .build();

      return
          UsernamePasswordAuthenticationToken.authenticated(user, null, user.getAuthorities());
    }

    // authentication was unsuccessful.
    // signal the AuthenticationManger, that it should try the next AuthenticationProvider in its list
    return null;
  }

  @Override
  public boolean supports(Class<?> authentication) {
    return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
  }
}
