package common.model;

import com.devProject.jwtsecurity.user.Role;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {

  private String firstname;
  private String lastname;
  
  @NotBlank(message = "Email is mandatory")
  @Email(message = "Email should be valid")
  private String email;
  
  private String password;
  private Role role;
}
