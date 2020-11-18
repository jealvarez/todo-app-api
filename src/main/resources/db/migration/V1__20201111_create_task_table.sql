-- -----------------------------------------------------
-- Table task`
-- -----------------------------------------------------
CREATE TABLE task
(
    id          INT UNSIGNED NOT NULL AUTO_INCREMENT,
    name        VARCHAR(150) NOT NULL,
    description VARCHAR(300) NULL,
    PRIMARY KEY (id)
)
    ENGINE = InnoDB;
