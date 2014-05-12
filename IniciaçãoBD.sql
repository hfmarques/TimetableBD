BEGIN;

DROP schema TimetableBD cascade;

CREATE schema TimetableBD;

create table TimetableBD.Disciplina (
	idDiciplina int not null,
	codigo char[] not null,
	credito int not null,
	nome char[] not null,
	perfil char[] not null,
	primary key (idDiciplina)	
);

create table TimetableBD.Docente (
	idDocente int not null,
	codigo char[] not null,
	nome char[] not null,
	nomeCompleto char[] not null,
	creditacaoEsperada int not null,
	primary key (idDocente)
);

create table TimetableBD.Turma(
	idTurma int not null,
	codigo char[] not null,
	turno char[] not null,
	maxVagas int not null,
	idDisciplina int not null,
	idSala int not null,
	primary key(idTurma)
);

create table TimetableBD.CreditoMinistrado(
	credito double precision not null,
	idDocente int not null,
	idTurma int not null
);

create table TimetableBD.Sala(
	idSala int not null,
	numero char[] not null,
	local char[] not null,
	primary key(idSala)
);

create table TimetableBD.DiscCurr(
	periodo int not null,
	carater char[] not null,
	idDisciplina int not null,
	idCurriculo int not null
);

create table TimetableBD.Curriculo(
	idCurriculo int not null,
	anoInicio int not null,
	ativo boolean not null,
	idCurso int not null,
	primary key(idCurriculo)
);

create table TimetableBD.Curso(
	idCurso int not null,
	nome char[] not null,
	codigo char[] not null,
	turno char[] not null,
	idCalouros int not null,
	primary key(idCurso)
);

create table TimetableBD.Calouros(
	idCalouros int not null;
	semestre int not null,
	numVagas int not null,
	idCurso int not null,
	primary key(idCalouros)
);

create table TimetableBD.Horario(
	horario char[] not null,
	idTurma char[] not null
);

--FOREIGN KEYs
ALTER TABLE TimetableBD.Turma 
ADD FOREIGN KEY (idDisciplina) REFERENCES TimetableBD.Disciplina(idDiciplina),
ADD FOREIGN KEY (idSala) REFERENCES TimetableBD.Sala(idSala);

ALTER TABLE TimetableBD.Credito_Ministrado 
ADD FOREIGN KEY (idDocente) REFERENCES TimetableBD.Docente(idDocente),
ADD FOREIGN KEY (idTurma) REFERENCES TimetableBD.Turma(idTurma);

ALTER TABLE TimetableBD.Disc_Curr 
ADD FOREIGN KEY (idDisciplina) REFERENCES TimetableBD.Disciplina(idDiciplina),
ADD FOREIGN KEY (idCurriculo) REFERENCES TimetableBD.Curriculo(idCurriculo);

ALTER TABLE TimetableBD.Curriculo 
ADD FOREIGN KEY (idCurso) REFERENCES TimetableBD.Curso(idCurso);

ALTER TABLE TimetableBD.Curso
ADD FOREIGN KEY (idCalouros) REFERENCES TimetableBD.Calouros(idCalouros);

ALTER TABLE TimetableBD.Horario 
ADD FOREIGN KEY (idTurma) REFERENCES TimetableBD.Turma(idTurma);

--Relacionamentos
--ALTER TABLE stroele.perfumaria_venda
--ADD FOREIGN KEY (numero_perfume) REFERENCES stroele.perfumaria(numero);

--ALTER TABLE stroele.perfumaria_venda
--ADD FOREIGN KEY (numero_nota) REFERENCES stroele.venda(numero_nota);

--ALTER TABLE stroele.medicamento_receita_venda
--ADD FOREIGN KEY (numero_med) REFERENCES stroele.medicamento(numero);

--ALTER TABLE stroele.medicamento_receita_venda
--ADD FOREIGN KEY (numero_nota) REFERENCES stroele.venda(numero_nota);

--ALTER TABLE stroele.medicamento_receita_venda
--ADD FOREIGN KEY (crm, numero_receita) REFERENCES stroele.receita_medica(crm, numero);

--ALTER TABLE stroele.receita_medica
--ADD nome_medico varchar not null;

--Alteracoes na tabela VENDA
--ALTER TABLE stroele.venda
--ALTER COLUMN cidade_cliente SET DEFAULT 'Juiz de Fora';

--ALTER TABLE stroele.venda
--ADD cpf_cliente varchar(11) DEFAULT 0 not null;

--Receita medica
--A data da receita nao pode ser menor que a data de hoje menos 6 meses
--ALTER TABLE stroele.receita_medica
--ADD CONSTRAINT ck_data_receita CHECK (data >= (current_date - '6 month'::INTERVAL));

--ALTER TABLE stroele.produto
--ADD CONSTRAINT uk_nome_comercial UNIQUE (nome_comercial);


COMMIT;
