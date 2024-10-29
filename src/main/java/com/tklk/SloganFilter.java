package com.tklk;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;
import org.springframework.web.filter.OncePerRequestFilter;

public class SloganFilter extends OncePerRequestFilter {

  @Override
  protected void doFilterInternal(
      HttpServletRequest request,
      HttpServletResponse response,
      FilterChain filterChain) throws ServletException, IOException {

    // You can prevent processing remaining filters in the chain by just returning:
    // return;

    if (Objects.equals(request.getHeader("x-secret-slogan"), "let_me_in")) {
      // do whatever you want here, e.g. log, enhance request data, store some information in session etc.
      System.out.println("Successfully passed with expected slogan!");
    }

    // Call back into the filter chain to continue processing the remaining filters
    filterChain.doFilter(request, response);
  }
}
