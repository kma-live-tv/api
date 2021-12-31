package com.nopain.livetv.model;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "livestreams")
public class Livestream extends ApplicationModel {
    @Column(unique = true)
    private String streamKey;

    @Column
    private String content;

    @Column(unique = true)
    private String dvrFile;

    @Enumerated(EnumType.STRING)
    private LivestreamStatus status;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(nullable = false)
    private User user;

    @OneToMany(mappedBy = "livestream", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<Comment> comments;

    @OneToMany(mappedBy = "livestream", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<Reaction> reactions;

    @Column()
    private Integer viewsCount = 0;
}
