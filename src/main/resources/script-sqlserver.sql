-- =============================================================
-- Hero API - Script de criação do banco no SQL Server
-- Execute no SQL Server Management Studio, Azure Data Studio
-- ou via sqlcmd. As tabelas também são criadas automaticamente
-- pelo Hibernate (spring.jpa.hibernate.ddl-auto=update),
-- mas o BANCO precisa existir antes de subir a aplicação.
-- =============================================================

IF DB_ID('herois_db') IS NULL
    CREATE DATABASE herois_db;
GO

USE herois_db;
GO

IF OBJECT_ID('herois', 'U') IS NULL
CREATE TABLE herois (
    id          BIGINT IDENTITY(1,1) NOT NULL,
    codinome    VARCHAR(100) NOT NULL,
    nome_civil  VARCHAR(100) NULL,
    poder       VARCHAR(150) NOT NULL,
    universo    VARCHAR(50)  NOT NULL,
    nivel_poder INT          NOT NULL,
    PRIMARY KEY (id)
);
GO

IF OBJECT_ID('equipes', 'U') IS NULL
CREATE TABLE equipes (
    id             BIGINT IDENTITY(1,1) NOT NULL,
    nome_equipe    VARCHAR(100) NOT NULL,
    base_operacoes VARCHAR(150) NULL,
    PRIMARY KEY (id)
);
GO

-- Dados iniciais para teste
INSERT INTO herois (codinome, nome_civil, poder, universo, nivel_poder) VALUES
    ('Homem-Aranha', 'Peter Parker',   'Sentido aranha e agilidade sobre-humana', 'Marvel', 75),
    ('Homem de Ferro', 'Tony Stark',   'Armadura tecnológica',                    'Marvel', 85),
    ('Superman',     'Clark Kent',     'Superforça, voo e visão de calor',         'DC',     98),
    ('Mulher-Maravilha', 'Diana Prince','Força divina e laço da verdade',           'DC',     95);
GO

INSERT INTO equipes (nome_equipe, base_operacoes) VALUES
    ('Vingadores',      'Torre dos Vingadores - Nova York'),
    ('Liga da Justiça', 'Torre de Vigilância - Órbita da Terra');
GO
