package com.nopain.livetv.model;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "notifications")
public class Notification extends ApplicationModel {
    @Column(nullable = false)
    private String content;

    @Column
    private Long livestreamId;

    @Column
    private Long triggeredUserId;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(nullable = false)
    private User user;

    @Column
    @Builder.Default
    private Boolean isRead = false;
}
