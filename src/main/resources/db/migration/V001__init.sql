CREATE TABLE empleados (
    id               BIGSERIAL PRIMARY KEY,
    num_empleado     VARCHAR(20)  NOT NULL UNIQUE,
    nombres          VARCHAR(100) NOT NULL,
    apellido_paterno VARCHAR(100) NOT NULL,
    apellido_materno VARCHAR(100),
    telefono         VARCHAR(20),
    email            VARCHAR(150),
    fecha_ingreso    DATE         NOT NULL,
    activo           BOOLEAN      NOT NULL DEFAULT TRUE
);

CREATE UNIQUE INDEX idx_empleados_num_empleado
    ON empleados (num_empleado);
