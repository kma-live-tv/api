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
    @Builder.Default
    private Long livestreamId = -1L;

    @Column
    @Builder.Default
    private String livestreamKey = "";

    @Column
    @Builder.Default
    private Long triggeredUserId = -1L;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(nullable = false)
    private User user;

    @Column
    @Builder.Default
    private Boolean isRead = false;
}
