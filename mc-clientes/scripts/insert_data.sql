-- ============================================
-- SCRIPT: Inserción de Personas y Vehículos
-- ============================================

-- 1. INSERTAR PERSONA NATURAL
-- Primero en la tabla padre 'personas'
INSERT INTO personas (id, identificacion, nombre, email, telefono, fecha_creacion, activo, direccion)
VALUES (
    gen_random_uuid(),
    '1712345678', -- Cédula válida (10 dígitos)
    'Juan Pérez García',
    'juan.perez@example.com',
    '0998765432',
    NOW(),
    true,
    'Av. Amazonas N24-03, Quito'
);

-- Luego en la tabla hija 'persona_natural'
-- genero: 0=M, 1=F, 2=O
INSERT INTO persona_natural (id, apellido, fecha_nacimiento, genero)
SELECT 
    id,
    'Pérez García',
    '1990-05-15',
    0
FROM personas
WHERE identificacion = '1712345678';


-- 2. INSERTAR PERSONA JURÍDICA
-- Primero en la tabla padre 'personas'
INSERT INTO personas (id, identificacion, nombre, email, telefono, fecha_creacion, activo, direccion)
VALUES (
    gen_random_uuid(),
    '1792345678001', -- RUC válido (13 dígitos)
    'Tech Solutions S.A.',
    'info@techsolutions.com',
    '0223456789',
    NOW(),
    true,
    'Av. 6 de Diciembre N34-120, Quito'
);

-- Luego en la tabla hija 'persona_juridica'
INSERT INTO persona_juridica (id, razon_social, representante_legal, actividad_economica)
SELECT 
    id,
    'TECH SOLUTIONS SOCIEDAD ANÓNIMA',
    'María González López',
    'Desarrollo de software y consultoría tecnológica'
FROM personas
WHERE identificacion = '1792345678001';


-- 3. INSERTAR AUTOMÓVIL
-- Primero en la tabla padre 'vehiculo'
INSERT INTO vehiculo (id, placa, marca, modelo, color, anio_fabricacion, id_persona, fecha_creacion, activo)
SELECT 
    gen_random_uuid(),
    'ABC-1234',
    'Toyota',
    'Corolla',
    'Gris',
    2022,
    p.id,
    NOW(),
    true
FROM personas p
WHERE p.identificacion = '1712345678';

-- Luego en la tabla hija 'automovil'
-- tipo: SEDAN, SUV, etc. / combustible: GASOLINA, DIESEL, ELECTRICO, HIBRIDO
INSERT INTO automovil (id, numero_puertas, tipo, combustible, cilindrada, numero_pasajeros, abs, airbags, aire_acondicionado, transmision)
SELECT 
    v.id,
    4,
    'SEDAN',
    'GASOLINA',
    1800,
    5,
    true,
    true,
    true,
    'AUTOMATICO'
FROM vehiculo v
WHERE v.placa = 'ABC-1234';


-- 4. INSERTAR MOTO
-- Primero en la tabla padre 'vehiculo'
INSERT INTO vehiculo (id, placa, marca, modelo, color, anio_fabricacion, id_persona, fecha_creacion, activo)
SELECT 
    gen_random_uuid(),
    'XYZ-789',
    'Yamaha',
    'MT-03',
    'Negro',
    2023,
    p.id,
    NOW(),
    true
FROM personas p
WHERE p.identificacion = '1792345678001';

-- Luego en la tabla hija 'moto'
-- tipo: 0=SCOOTER, 1=DEPORTIVA, 2=TOURING, 3=CRUISER, 4=TRAIL, 5=ENDURO, 6=CROSS, 7=NAKED, 8=CUSTOM, 9=ELECTRICA
INSERT INTO moto (id, cilindraje, tipo, tiene_casco)
SELECT 
    v.id,
    321,
    1,
    true
FROM vehiculo v
WHERE v.placa = 'XYZ-789';


-- ============================================
-- QUERIES PARA CONSULTAR LOS DATOS
-- ============================================

-- Query 1: Ver todas las personas con su tipo
SELECT 
    p.id,
    p.identificacion,
    p.nombre,
    p.email,
    p.telefono,
    p.direccion,
    CASE 
        WHEN pn.id IS NOT NULL THEN 'Natural'
        WHEN pj.id IS NOT NULL THEN 'Jurídica'
    END as tipo_persona,
    pn.apellido,
    pn.genero,
    pn.fecha_nacimiento,
    pj.razon_social,
    pj.representante_legal
FROM personas p
LEFT JOIN persona_natural pn ON p.id = pn.id
LEFT JOIN persona_juridica pj ON p.id = pj.id
ORDER BY p.fecha_creacion DESC;


-- Query 2: Ver todos los vehículos con su tipo y propietario
SELECT 
    v.id,
    v.placa,
    v.marca,
    v.modelo,
    v.color,
    v.anio_fabricacion,
    p.nombre as propietario,
    p.identificacion as id_propietario,
    CASE 
        WHEN a.id IS NOT NULL THEN 'Automóvil'
        WHEN m.id IS NOT NULL THEN 'Moto'
    END as tipo_vehiculo,
    a.numero_puertas,
    a.tipo as tipo_automovil,
    a.combustible,
    m.cilindraje,
    CASE m.tipo
        WHEN 0 THEN 'SCOOTER'
        WHEN 1 THEN 'DEPORTIVA'
        WHEN 2 THEN 'TOURING'
        WHEN 3 THEN 'CRUISER'
        WHEN 4 THEN 'TRAIL'
        WHEN 5 THEN 'ENDURO'
        WHEN 6 THEN 'CROSS'
        WHEN 7 THEN 'NAKED'
        WHEN 8 THEN 'CUSTOM'
        WHEN 9 THEN 'ELECTRICA'
    END as tipo_moto
FROM vehiculo v
INNER JOIN personas p ON v.id_persona = p.id
LEFT JOIN automovil a ON v.id = a.id
LEFT JOIN moto m ON v.id = m.id
ORDER BY v.fecha_creacion DESC;


-- Query 3: Ver solo personas naturales
SELECT 
    p.identificacion,
    p.nombre,
    pn.apellido,
    p.email,
    pn.genero,
    pn.fecha_nacimiento,
    p.telefono,
    p.direccion
FROM personas p
INNER JOIN persona_natural pn ON p.id = pn.id
WHERE p.activo = true;


-- Query 4: Ver solo personas jurídicas
SELECT 
    p.identificacion as ruc,
    p.nombre,
    pj.razon_social,
    pj.representante_legal,
    pj.actividad_economica,
    p.email,
    p.telefono,
    p.direccion
FROM personas p
INNER JOIN persona_juridica pj ON p.id = pj.id
WHERE p.activo = true;


-- Query 5: Ver solo automóviles
SELECT 
    v.placa,
    v.marca,
    v.modelo,
    v.color,
    v.anio_fabricacion,
    a.numero_puertas,
    a.tipo as tipo_automovil,
    a.combustible,
    p.nombre as propietario
FROM vehiculo v
INNER JOIN automovil a ON v.id = a.id
INNER JOIN personas p ON v.id_persona = p.id;


-- Query 6: Ver solo motos
SELECT 
    v.placa,
    v.marca,
    v.modelo,
    v.color,
    v.anio_fabricacion,
    m.cilindraje,
    CASE m.tipo
        WHEN 0 THEN 'SCOOTER'
        WHEN 1 THEN 'DEPORTIVA'
        WHEN 2 THEN 'TOURING'
        WHEN 3 THEN 'CRUISER'
        WHEN 4 THEN 'TRAIL'
        WHEN 5 THEN 'ENDURO'
        WHEN 6 THEN 'CROSS'
        WHEN 7 THEN 'NAKED'
        WHEN 8 THEN 'CUSTOM'
        WHEN 9 THEN 'ELECTRICA'
    END as tipo_moto,
    p.nombre as propietario
FROM vehiculo v
INNER JOIN moto m ON v.id = m.id
INNER JOIN personas p ON v.id_persona = p.id;


-- Query 7: Contar vehículos por propietario
SELECT 
    p.identificacion,
    p.nombre,
    COUNT(v.id) as total_vehiculos,
    SUM(CASE WHEN a.id IS NOT NULL THEN 1 ELSE 0 END) as automoviles,
    SUM(CASE WHEN m.id IS NOT NULL THEN 1 ELSE 0 END) as motos
FROM personas p
LEFT JOIN vehiculo v ON p.id = v.id_persona
LEFT JOIN automovil a ON v.id = a.id
LEFT JOIN moto m ON v.id = m.id
GROUP BY p.id, p.identificacion, p.nombre;
