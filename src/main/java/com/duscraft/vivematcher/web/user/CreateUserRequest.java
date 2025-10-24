package com.duscraft.vivematcher.web.user;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class CreateUserRequest {
  @NotBlank
  @Size(min = 3, max = 100)
  private String displayName;
  @NotBlank
  private String hero;
  @NotBlank
  private String rank;

  public String getDisplayName() {
    return this.displayName;
  }

  public void setDisplayName(String displayName) {
    this.displayName = displayName;
  }

  public String getHero() {
    return this.hero;
  }

  public void setHero(String hero) {
    this.hero = hero;
  }

  public String getRank() {
    return this.rank;
  }

  public void setRank(String rank) {
    this.rank = rank;
  }
}
