-- ============================================
-- V004__empleados_catalogos_basicos.sql
-- Catálogos y FKs para empleados
-- ============================================

-- ===== CATÁLOGO: PUESTOS =====
CREATE TABLE IF NOT EXISTS cat_puestos (
    id          BIGSERIAL PRIMARY KEY,
    nombre      VARCHAR(150) NOT NULL,
    descripcion VARCHAR(255),
    activo      BOOLEAN NOT NULL DEFAULT TRUE,
    created_at  TIMESTAMP WITHOUT TIME ZONE DEFAULT NOW(),
    updated_at  TIMESTAMP WITHOUT TIME ZONE DEFAULT NOW()
);

-- ===== CATÁLOGO: DEPARTAMENTOS =====
CREATE TABLE IF NOT EXISTS cat_departamentos (
    id          BIGSERIAL PRIMARY KEY,
    nombre      VARCHAR(150) NOT NULL,
    descripcion VARCHAR(255),
    activo      BOOLEAN NOT NULL DEFAULT TRUE,
    created_at  TIMESTAMP WITHOUT TIME ZONE DEFAULT NOW(),
    updated_at  TIMESTAMP WITHOUT TIME ZONE DEFAULT NOW()
);

-- ===== CATÁLOGO: BANCOS =====
CREATE TABLE IF NOT EXISTS cat_bancos (
    id          BIGSERIAL PRIMARY KEY,
    nombre      VARCHAR(150) NOT NULL,
    clave       VARCHAR(20), -- opcional, CLABE interbancaria/clave banco
    activo      BOOLEAN NOT NULL DEFAULT TRUE,
    created_at  TIMESTAMP WITHOUT TIME ZONE DEFAULT NOW(),
    updated_at  TIMESTAMP WITHOUT TIME ZONE DEFAULT NOW()
);

-- ===== CATÁLOGO: TIPOS DE CONTRATO =====
CREATE TABLE IF NOT EXISTS cat_tipos_contrato (
    id          BIGSERIAL PRIMARY KEY,
    nombre      VARCHAR(150) NOT NULL,
    descripcion VARCHAR(255),
    activo      BOOLEAN NOT NULL DEFAULT TRUE,
    created_at  TIMESTAMP WITHOUT TIME ZONE DEFAULT NOW(),
    updated_at  TIMESTAMP WITHOUT TIME ZONE DEFAULT NOW()
);

-- ===== CATÁLOGO: TIPOS DE JORNADA =====
CREATE TABLE IF NOT EXISTS cat_tipos_jornada (
    id          BIGSERIAL PRIMARY KEY,
    nombre      VARCHAR(150) NOT NULL,
    descripcion VARCHAR(255),
    activo      BOOLEAN NOT NULL DEFAULT TRUE,
    created_at  TIMESTAMP WITHOUT TIME ZONE DEFAULT NOW(),
    updated_at  TIMESTAMP WITHOUT TIME ZONE DEFAULT NOW()
);

-- ===== CATÁLOGO: MOTIVOS DE BAJA =====
CREATE TABLE IF NOT EXISTS cat_motivos_baja (
    id          BIGSERIAL PRIMARY KEY,
    nombre      VARCHAR(150) NOT NULL,
    descripcion VARCHAR(255),
    activo      BOOLEAN NOT NULL DEFAULT TRUE,
    created_at  TIMESTAMP WITHOUT TIME ZONE DEFAULT NOW(),
    updated_at  TIMESTAMP WITHOUT TIME ZONE DEFAULT NOW()
);

-- ============================================
-- ALTER TABLE EMPLEADOS: columnas FK que SÍ existen en la entidad
-- ============================================

ALTER TABLE empleados
    ADD COLUMN IF NOT EXISTS puesto_id       BIGINT,
    ADD COLUMN IF NOT EXISTS departamento_id BIGINT,
    ADD COLUMN IF NOT EXISTS supervisor_id   BIGINT;

-- ============================================
-- FKs válidas según la entidad Empleado actual
-- ============================================

ALTER TABLE empleados
    ADD CONSTRAINT fk_empleado_puesto
        FOREIGN KEY (puesto_id)
        REFERENCES cat_puestos (id),
    ADD CONSTRAINT fk_empleado_departamento
        FOREIGN KEY (departamento_id)
        REFERENCES cat_departamentos (id),
    ADD CONSTRAINT fk_empleado_supervisor
        FOREIGN KEY (supervisor_id)
        REFERENCES empleados (id);

-- ============================================
-- Índices para mejorar joins/búsquedas
-- ============================================

CREATE INDEX IF NOT EXISTS idx_empleados_puesto_id
    ON empleados (puesto_id);

CREATE INDEX IF NOT EXISTS idx_empleados_departamento_id
    ON empleados (departamento_id);

CREATE INDEX IF NOT EXISTS idx_empleados_supervisor_id
    ON empleados (supervisor_id);
