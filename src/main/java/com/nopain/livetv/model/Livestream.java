package com.nopain.livetv.model;

import lombok.*;

import javax.persistence.*;
import java.time.Instant;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "livestreams")
public class Livestream extends ApplicationModel {
    @Column
    private String content;

    @Column(unique = true)
    private String dvrFile;

    @Enumerated(EnumType.STRING)
    @Builder.Default
    private LivestreamStatus status = LivestreamStatus.WAITING;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(nullable = false)
    private User user;

    @OneToMany(mappedBy = "livestream", fetch = FetchType.EAGER, cascade = CascadeType.REFRESH)
    private List<Comment> comments;

    @OneToMany(mappedBy = "livestream", fetch = FetchType.EAGER, cascade = CascadeType.REFRESH)
    private List<Reaction> reactions;

    @Column
    private Integer viewsCount = 0;

    @Column
    @Builder.Default
    private Instant waitingFrom = Instant.now();
}
