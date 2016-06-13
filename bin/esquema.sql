CREATE CACHED TABLE CONFIGURACION (
nombre				VARCHAR NOT NULL PRIMARY KEY,
valor				VARCHAR
);

CREATE UNIQUE INDEX INX_CONFIGURACION ON CONFIGURACION (nombre);

CREATE CACHED TABLE ESPECIES (
id					BIGINT NOT NULL PRIMARY KEY,
codigo				VARCHAR(4) NOT NULL,
aforo				DOUBLE NOT NULL,
stock				DOUBLE NOT NULL,
posicion			DOUBLE NOT NULL,
aforoInverso      	BOOLEAN NOT NULL
);

CREATE UNIQUE INDEX INX_ESPECIES ON ESPECIES (codigo);

CREATE CACHED TABLE CLIENTES (
id					BIGINT NOT NULL PRIMARY KEY,
codigo				VARCHAR NOT NULL
);

CREATE UNIQUE INDEX INX_CLIENTES ON CLIENTES (codigo);

CREATE CACHED TABLE DIAS (
id					BIGINT NOT NULL PRIMARY KEY,
fechaAsociada		DATE NOT NULL,
abierto		      	BOOLEAN,
descripcion			VARCHAR
)

CREATE UNIQUE INDEX INX_DIAS ON DIAS (fechaAsociada);

CREATE CACHED TABLE CIERRES (
id					BIGINT NOT NULL PRIMARY KEY,
diaId				BIGINT NOT NULL,
especieId			BIGINT NOT NULL,
aforo				DOUBLE NOT NULL,
stock				DOUBLE NOT NULL,
posicion			DOUBLE NOT NULL,
FOREIGN KEY (diaId) REFERENCES DIAS(id),
FOREIGN KEY (especieId) REFERENCES ESPECIES(id)
)

CREATE CACHED TABLE OPERACIONES (
id					BIGINT NOT NULL PRIMARY KEY,
numero				BIGINT NOT NULL,
fechaAlta			DATE NOT NULL,
fechaLiquidacion	DATE, 
tipo				VARCHAR NOT NULL, 
subTipo 			VARCHAR NOT NULL, 
diaId				BIGINT NOT NULL,
clienteId			BIGINT,
especieEntraId		BIGINT,
especieSaleId		BIGINT,
valorizacion		DOUBLE,
cantidad			DOUBLE NOT NULL,
cantidadSale		DOUBLE,
notas 				VARCHAR,
FOREIGN KEY (diaId) REFERENCES DIAS(id),
FOREIGN KEY (clienteId) REFERENCES CLIENTES(id),
FOREIGN KEY (especieEntraId) REFERENCES ESPECIES(id),
FOREIGN KEY (especieSaleId) REFERENCES ESPECIES(id)
);
       
CREATE UNIQUE INDEX INX_OPERACIONES ON OPERACIONES (numero);
       
CREATE CACHED TABLE MOVIMIENTOS (
id					BIGINT NOT NULL PRIMARY KEY,
operacionId			BIGINT NOT NULL,
clienteId			BIGINT,
especieId			BIGINT NOT NULL,
diaId				BIGINT NOT NULL,
cantidad			DOUBLE NOT NULL,
valorizacion		DOUBLE,
tipo				VARCHAR NOT NULL,
liquidado			BOOLEAN NOT NULL,
fechaAlta			DATE NOT NULL,
fechaConcrecion		DATE,
diaConcrecionId		BIGINT,
FOREIGN KEY (operacionid) REFERENCES OPERACIONES(id) ON DELETE CASCADE,
FOREIGN KEY (especieId) REFERENCES ESPECIES(id),
FOREIGN KEY (diaId) REFERENCES DIAS(id),
FOREIGN KEY (diaConcrecionId) REFERENCES DIAS(id)
);

CREATE INDEX INX_MOVIMIENTOS_1 ON MOVIMIENTOS (operacionId);
CREATE INDEX INX_MOVIMIENTOS_2 ON MOVIMIENTOS (operacionId, liquidado);

CREATE CACHED TABLE DUAL (
id				VARCHAR PRIMARY KEY
);
INSERT INTO DUAL VALUES (1);

CREATE SEQUENCE SEQ_CLIENTES;
CREATE SEQUENCE SEQ_ESPECIES;
CREATE SEQUENCE SEQ_OPERACIONES;
CREATE SEQUENCE SEQ_OPERACION_NUMERO;
CREATE SEQUENCE SEQ_MOVIMIENTOS;
CREATE SEQUENCE SEQ_DIAS;

INSERT INTO ESPECIES (id, codigo, aforo, stock, posicion, aforoInverso) VALUES (NEXT VALUE FOR SEQ_ESPECIES, 'BB', 1, 0, 0, false);
INSERT INTO ESPECIES (id, codigo, aforo, stock, posicion, aforoInverso) VALUES (NEXT VALUE FOR SEQ_ESPECIES, '$', 0.33, 0, 0, true);
