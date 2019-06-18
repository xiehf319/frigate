CREATE TABLE subscribe_user(id INTEGER GENERATED BY DEFAULT AS IDENTITY(START WITH 1) NOT NULL PRIMARY KEY, channel_id VARCHAR(32), client_id VARCHAR(32), topic VARCHAR(32), create_time INTEGER);
CREATE INDEX idx_client_id on subscribe_user(client_id);
CREATE INDEX idx_topic on subscribe_user(topic);
CREATE UNIQUE INDEX idx_client_topic ON subscribe_user(client_id, topic);