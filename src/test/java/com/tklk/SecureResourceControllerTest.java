package com.tklk;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.formLogin;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.authenticated;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
class SecureResourceControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @Test
  public void accessHomepageUnauthenticated() throws Exception {
    mockMvc.perform(get("/"))
        .andExpect(status().isOk());
  }

  @Test
  public void denyUnauthenticatedAccessToSecuredEndpoint() throws Exception {
    mockMvc.perform(get("/secured"))
        .andExpect(status().isUnauthorized());
  }

  @Test
  public void allowAuthenticatedAccessToSecuredEndpoint() throws Exception {
    // first login
    // after a successful login we're redirected to /secured
    mockMvc.perform(
            formLogin("/login")
                .user("admin")
                .password("doesNotMatterAsItIsIgnored")
        )
        .andExpect(status().is3xxRedirection())
        .andExpect(header().string("Location", "/secured"))
        .andExpect(authenticated())
        .andReturn();

    // /secured endpoint should be accessible now
    mockMvc.perform(get("/secured"))
        .andExpect(status().isUnauthorized());
  }
}
