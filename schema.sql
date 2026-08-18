-- Schema para o projeto DiasUteis (MySQL)
-- Execute este script no MySQL antes de rodar a aplicação.
-- Conexão configurada em exemplo.diasuteis.factory.FactoryConnector:
--   jdbc:mysql://localhost:3306/diasuteis  usuario=root  senha=(vazia)

CREATE DATABASE IF NOT EXISTS diasuteis CHARACTER SET utf8mb4;
USE diasuteis;

CREATE TABLE IF NOT EXISTS perfil (
    codigo INT AUTO_INCREMENT PRIMARY KEY,
    perfil VARCHAR(50) NOT NULL
);

CREATE TABLE IF NOT EXISTS usuario (
    codigo INT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    login VARCHAR(50) NOT NULL UNIQUE,
    senha VARCHAR(50) NOT NULL,
    email VARCHAR(100),
    perfil INT,
    CONSTRAINT fk_usuario_perfil FOREIGN KEY (perfil) REFERENCES perfil (codigo)
);

CREATE TABLE IF NOT EXISTS tipoferiado (
    codigo INT AUTO_INCREMENT PRIMARY KEY,
    tipo VARCHAR(50) NOT NULL
);

CREATE TABLE IF NOT EXISTS feriado (
    codigo INT AUTO_INCREMENT PRIMARY KEY,
    dia DATE NOT NULL,
    descricao VARCHAR(150) NOT NULL,
    tipo INT,
    CONSTRAINT fk_feriado_tipo FOREIGN KEY (tipo) REFERENCES tipoferiado (codigo)
);

-- Dados iniciais
INSERT INTO perfil (perfil) VALUES ('Administrador');

INSERT INTO usuario (nome, login, senha, email, perfil)
VALUES ('Administrador', 'admin', 'admin', 'admin@teste.com', 1);

INSERT INTO tipoferiado (tipo) VALUES ('Nacional'), ('Estadual'), ('Municipal');

INSERT INTO feriado (dia, descricao, tipo) VALUES
    ('2026-01-01', 'Confraternização Universal', 1),
    ('2026-04-21', 'Tiradentes', 1),
    ('2026-09-07', 'Independência do Brasil', 1),
    ('2026-12-25', 'Natal', 1);
