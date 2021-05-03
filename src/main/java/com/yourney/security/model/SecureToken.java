package com.yourney.security.model;

import java.sql.Timestamp;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.CreationTimestamp;

import lombok.Data;

@Entity
@Data
@Table(name = "secure_tokens")
public class SecureToken {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String token;

    @CreationTimestamp
    @Column(updatable = false)
    private Timestamp timestamp;

    private LocalDateTime expiteAt;

    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    @Transient
    private boolean isExpired;

    @Transient
    private boolean isRefreshAvailable;

    public boolean isExpired() {
        return getExpiteAt().isBefore(LocalDateTime.now());
    }

    public boolean isRefreshAvailable() {
        return LocalDateTime.now().isAfter(getTimestamp().toLocalDateTime().plusSeconds(30));
    }
}
