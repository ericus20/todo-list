package com.developersboard.todoreact.backend.persistence.domain.base;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.time.LocalDateTime;

import javax.persistence.MappedSuperclass;
import javax.persistence.Version;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

/**
 * This class is to allow an entity to inherit properties from a it.
 *
 * @author Eric Opoku on 7/29/2019
 * @version 1.0
 * @since 1.0
 */
@Data
@MappedSuperclass
@EqualsAndHashCode(of = {"version"})
@JsonIgnoreProperties({"createdAt", "updatedAt", "version"})
public class BaseEntity {

  /**
   * Manages the version of Entities to measure the amount of
   * modifications made to this entity.
   */
  @Version
  private int version;

  /**
   * Keeps record of when an Entity wss created.
   */
  @CreatedDate
  @CreationTimestamp
  private LocalDateTime createdAt;

  /**
   * Keeps record of when the item was last Modified.
   */
  @LastModifiedDate
  @UpdateTimestamp
  private LocalDateTime updatedAt;
}
