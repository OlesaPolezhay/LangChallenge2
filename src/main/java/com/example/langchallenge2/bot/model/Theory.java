package com.example.langchallenge2.bot.model;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Entity
@Table(name = "theory")
@Getter
public class Theory {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Integer theoryID;

  @Column(name = "theory")
  private String theory;

  @Column(name = "dayNumber")
  private Integer dayNumber;

  @Column(name = "msgNumber")
  private Integer msgNumber;

}
