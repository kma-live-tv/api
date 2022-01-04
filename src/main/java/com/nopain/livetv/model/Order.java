package com.nopain.livetv.model;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "orders")
public class Order extends ApplicationModel {
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(nullable = false)
    private Livestream livestream;

    @Column
    private Integer amount;

    @Column
    private String intentId;

    @Column
    private String intentClientSecret;
}
