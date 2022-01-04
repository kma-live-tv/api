CREATE TABLE orders
(
    id                   BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
    created_at           TIMESTAMP WITHOUT TIME ZONE             NOT NULL,
    updated_at           TIMESTAMP WITHOUT TIME ZONE,
    user_id              BIGINT                                  NOT NULL,
    livestream_id        BIGINT                                  NOT NULL,
    amount               INTEGER,
    intent_id            VARCHAR(255),
    intent_client_secret VARCHAR(255),
    CONSTRAINT pk_orders PRIMARY KEY (id)
);

ALTER TABLE orders
    ADD CONSTRAINT FK_ORDERS_ON_LIVESTREAM FOREIGN KEY (livestream_id) REFERENCES livestreams (id);

ALTER TABLE orders
    ADD CONSTRAINT FK_ORDERS_ON_USER FOREIGN KEY (user_id) REFERENCES users (id);