CREATE TABLE empleados (
    id               BIGSERIAL PRIMARY KEY,
    num_empleado     VARCHAR(20)  NOT NULL UNIQUE,
    nombres          VARCHAR(100) NOT NULL,
    apellido_paterno VARCHAR(100) NOT NULL,
    apellido_materno VARCHAR(100),
    telefono         VARCHAR(20),
    email            VARCHAR(150),
    fecha_ingreso    DATE         NOT NULL,
    activo           BOOLEAN      NOT NULL DEFAULT TRUE,

    -- Datos personales
    fecha_nacimiento DATE,
    genero           VARCHAR(20),
    estado_civil     VARCHAR(30),
    curp             VARCHAR(18),
    rfc              VARCHAR(13),
    nss              VARCHAR(15),
    foto             VARCHAR(255),

    -- Relaciones / catálogos
    departamento_id  BIGINT,
    puesto_id        BIGINT,
    turno_id         BIGINT,
    horario_id       BIGINT,
    supervisor_id    BIGINT,

    -- Domicilio
    calle            VARCHAR(150),
    num_ext          VARCHAR(20),
    num_int          VARCHAR(20),
    colonia          VARCHAR(150),
    municipio        VARCHAR(150),
    estado           VARCHAR(150),
    cp               VARCHAR(10),
    nacionalidad     VARCHAR(80),
    lugar_nacimiento VARCHAR(150),

    -- Escolaridad / salud
    escolaridad      VARCHAR(80),
    tipo_sangre      VARCHAR(10),

    -- Contacto de emergencia
    contacto_nombre      VARCHAR(150),
    contacto_telefono    VARCHAR(30),
    contacto_parentesco  VARCHAR(80),

    -- Nómina / bancario
    banco            VARCHAR(80),
    cuenta_bancaria  VARCHAR(30),
    clabe            VARCHAR(30),

    -- Contrato / baja
    tipo_contrato    VARCHAR(50),
    tipo_jornada     VARCHAR(50),
    fecha_baja       DATE,
    motivo_baja      VARCHAR(150),

    -- Seguridad social
    imss_reg_patronal VARCHAR(50),
    infonavit_numero  VARCHAR(30),
    fonacot_numero    VARCHAR(30)
);

-- Este índice es opcional porque el UNIQUE ya genera índice,
-- pero si quieres dejarlo explícito, no pasa nada grave:
CREATE UNIQUE INDEX idx_empleados_num_empleado
    ON empleados (num_empleado);
