ALTER TABLE empleados
  ADD COLUMN fecha_nacimiento          DATE,
  ADD COLUMN genero                    VARCHAR(20),
  ADD COLUMN estado_civil              VARCHAR(30),
  ADD COLUMN curp                      VARCHAR(18),
  ADD COLUMN rfc                       VARCHAR(13),
  ADD COLUMN nss                       VARCHAR(15),
  ADD COLUMN foto                      VARCHAR(255),

  ADD COLUMN departamento_id           BIGINT,
  ADD COLUMN puesto_id                 BIGINT,
  ADD COLUMN turno_id                  BIGINT,
  ADD COLUMN horario_id                BIGINT,
  ADD COLUMN supervisor_id             BIGINT,

  ADD COLUMN calle                     VARCHAR(150),
  ADD COLUMN num_ext                   VARCHAR(20),
  ADD COLUMN num_int                   VARCHAR(20),
  ADD COLUMN colonia                   VARCHAR(150),
  ADD COLUMN municipio                 VARCHAR(150),
  ADD COLUMN estado                    VARCHAR(150),
  ADD COLUMN cp                        VARCHAR(10),
  ADD COLUMN nacionalidad              VARCHAR(80),
  ADD COLUMN lugar_nacimiento          VARCHAR(150),

  ADD COLUMN escolaridad               VARCHAR(80),
  ADD COLUMN tipo_sangre               VARCHAR(10),

  ADD COLUMN contacto_nombre           VARCHAR(150),
  ADD COLUMN contacto_telefono         VARCHAR(30),
  ADD COLUMN contacto_parentesco       VARCHAR(80),

  ADD COLUMN banco                     VARCHAR(80),
  ADD COLUMN cuenta_bancaria           VARCHAR(30),
  ADD COLUMN clabe                     VARCHAR(30),

  ADD COLUMN salario_base              NUMERIC(12,2),
  ADD COLUMN tipo_contrato             VARCHAR(50),
  ADD COLUMN tipo_jornada              VARCHAR(50),
  ADD COLUMN fecha_baja                DATE,
  ADD COLUMN motivo_baja               VARCHAR(150),

  ADD COLUMN imss_reg_patronal         VARCHAR(50),
  ADD COLUMN infonavit_numero          VARCHAR(30),
  ADD COLUMN infonavit_descuento_tipo  VARCHAR(20),
  ADD COLUMN infonavit_descuento_valor NUMERIC(12,2),
  ADD COLUMN fonacot_numero            VARCHAR(30),

  ADD COLUMN licencia_numero           VARCHAR(30),
  ADD COLUMN licencia_tipo             VARCHAR(30),
  ADD COLUMN licencia_vigencia         DATE;
