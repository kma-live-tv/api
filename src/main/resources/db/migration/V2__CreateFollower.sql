CREATE TABLE followers
(
    id             BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
    created_at     TIMESTAMP WITHOUT TIME ZONE             NOT NULL,
    updated_at     TIMESTAMP WITHOUT TIME ZONE,
    followed_by_id BIGINT                                  NOT NULL,
    follow_to_id   BIGINT                                  NOT NULL,
    CONSTRAINT pk_followers PRIMARY KEY (id)
);

ALTER TABLE followers
    ADD CONSTRAINT FK_FOLLOWERS_ON_FOLLOWEDBY FOREIGN KEY (followed_by_id) REFERENCES users (id);

ALTER TABLE followers
    ADD CONSTRAINT FK_FOLLOWERS_ON_FOLLOWTO FOREIGN KEY (follow_to_id) REFERENCES users (id);