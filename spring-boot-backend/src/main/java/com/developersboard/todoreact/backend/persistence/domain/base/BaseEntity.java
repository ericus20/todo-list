package com.developersboard.todoreact.backend.persistence.domain.base;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.time.LocalDateTime;

import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;

import lombok.Data;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

/**
 * This class is to allow an entity to inherit properties from a it.
 *
 * @author Eric Opoku on 7/29/2019
 * @version 1.0
 * @since 1.0
 */
@Data
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
@JsonIgnoreProperties({"createdAt", "createdBy", "updatedAt", "updatedBy"})
public class BaseEntity {

  /**
   * Records who updated an Entity by saving username.
   */
  @CreatedBy
  private String createdBy;

  /**
   * Keeps record of when the item was last Modified.
   */
  @UpdateTimestamp
  @LastModifiedDate
  private LocalDateTime updatedAt;

  /**
   * Manages the timestamps for each update made to an Entity.
   */
  @LastModifiedBy
  private String updatedBy;
}
