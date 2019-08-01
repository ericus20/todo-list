package com.developersboard.todoreact.backend.persistence.domain;

import java.io.Serializable;
import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@Entity
@ToString
@NoArgsConstructor
@EqualsAndHashCode(of = {"name", "dueDate", "completed"})
public class Todo implements Serializable {
  private static final long serialVersionUID = -5280898519700045951L;

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @NotBlank(message = "Todo item name is required")
  private String name;

  private LocalDate dueDate;
  private boolean completed;

  public Todo(String name, LocalDate dueDate) {
    this.name = name;
    this.dueDate = dueDate;
  }
}
