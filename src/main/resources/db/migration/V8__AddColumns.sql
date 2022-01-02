ALTER TABLE notifications
    ADD livestream_key VARCHAR(255);

ALTER TABLE livestreams
    ADD waiting_from TIMESTAMP WITHOUT TIME ZONE;