package es.vortech.demo.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "token")
public final class Token {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "token_id")
    private Integer id;

    @Column(name = "token", unique = true)
    private String token;

    @Column(name = "token_type")
    @Enumerated(value = EnumType.STRING)
    private TokenType tokenType = TokenType.BEARER;

    @Column(name = "revoked", nullable = false)
    private Boolean isRevoked;

    @Column(name = "expired", nullable = false)
    private Boolean isExpired;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", updatable = false)
    private User user;

    public enum TokenType {
        BEARER
    }

}