-- Roles base del sistema
INSERT INTO roles (name, descripcion)
VALUES
    ('SUPERADMIN', 'Super administrador del sistema'),
    ('ADMIN',      'Administrador'),
    ('RRHH',       'Recursos Humanos'),
    ('SUPERVISOR', 'Supervisor'),
    ('GERENTE',    'Gerente'),
    ('USUARIO',    'Usuario estándar')
ON CONFLICT (name) DO NOTHING;

-- Usuario admin base
-- Password real: F3n1xh311*  (BCRYPT)
INSERT INTO users (username, password, full_name, enabled, locked)
VALUES (
   'netrok',
   '{bcrypt}$2b$10$tcxK4K6BGNNQmNEObmelceE/PBM2Lfg62eb6IV4sE31p.O5nAzrLm',
   'Administrador',
   TRUE,
   FALSE
)
ON CONFLICT (username) DO UPDATE
SET
    password  = EXCLUDED.password,
    full_name = EXCLUDED.full_name,
    enabled   = EXCLUDED.enabled,
    locked    = EXCLUDED.locked;

-- Asignar roles al usuario admin
INSERT INTO users_roles (user_id, role_id)
SELECT u.id, r.id
FROM users u
JOIN roles r ON r.name IN ('SUPERADMIN', 'ADMIN', 'RRHH')
WHERE u.username = 'netrok'
ON CONFLICT DO NOTHING;
