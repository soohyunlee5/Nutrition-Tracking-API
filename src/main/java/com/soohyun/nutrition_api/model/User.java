package com.soohyun.nutrition_api.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

@Entity
@Table(
        name = "users"
)
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private String name;
    @Email(message = "Must be valid email address")
    @NotBlank(message = "Email cannot be empty")
    @Column(unique = true)
    private String email;
    @NotBlank(message = "Password cannot be empty")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;
    @OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE, fetch = FetchType.EAGER)
    @JsonIgnore
    private List<Meal> meals;

    protected User() {}

    public User(String name, String email, String password) {
        this.name = name;
        this.email = email;
        this.password = password;
    }

    @Override
    @JsonIgnore
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of();
    }

    // All true for now, implement account locking/expiration later
    @Override
    @JsonIgnore
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    @JsonIgnore
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    @JsonIgnore
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    @JsonIgnore
    public boolean isEnabled() {
        return true;
    }

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public String getPassword() {
        return password;
    }

    public List<Meal> getMeals() {
        return meals;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setUsername(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
