/*drop table CURSO;

drop table DISCIPLINA;

drop table DOCENTE;

drop table TURMA;

drop table CREDITOMINISTRADO;

drop table SALA;

drop table DISCCURR;

drop table CURRICULO;

drop table CALOUROS;

drop table HORARIOS; */

create table Curso (
	ID NUMERIC(19,0) not null,
	HIBERNATE_VERSION int default 0 not null,
	NOME VARCHAR(21) not null,
	TURNO VARCHAR(10) not null,
	CODIGO VARCHAR(21) not null,
	TIMETABLEBD_DISICPLINA_FK NUMERIC(19,0) not null,
	primary key (ID),
	unique (CODIGO)
);
	
create table Disciplina (
	ID NUMERIC(19,0) not null,
	HIBERNATE_VERSION int default 0 not null,
	CODIGO VARCHAR(21) not null,
	CREDITO int not null,
	NOME VARCHAR(50) not null,
	PERFIL VARCHAR(50) not null,
	primary key (ID),
	UNIQUE (CODIGO)
);

create table Docente (
	ID NUMERIC(19,0) not null,
	HIBERNATE_VERSION int default 0 not null,
	CODIGO VARCHAR(21) not null,
	NOME VARCHAR(50) not null,
	NOME_COMPLETO VARCHAR(100) not null,
	CREDITACAO_ESPERADA int not null,
	primary key (ID),
	UNIQUE (CODIGO)
);

create table Turma(
	ID NUMERIC(19,0) not null,
	HIBERNATE_VERSION int default 0 not null,
	CODIGO VARCHAR(21) not null,
	TURNO VARCHAR (10) not null,
	MAX_VAGAS INT not null,
	TIMETABLEBD_DISICPLINA_FK NUMERIC(19,0) not null,
	TIMETABLEBD_SALA_FK NUMERIC(19,0) not null,
	primary key(ID),
	UNIQUE (CODIGO)
);

create table CreditoMinistrado(
	CREDITO FLOAT not null,
	TIMETABLEBD_DOCENTE_FK NUMERIC(19,0) not null,
	TIMETABLEBD_TURMA_FK NUMERIC(19,0) not null
);

create table Sala(
	ID NUMERIC(19,0) not null,
	HIBERNATE_VERSION int default 0 not null,
	NUMERO VARCHAR(10) not null,
	LOCAL VARCHAR(21) not null,
	primary key(ID),
	UNIQUE (NUMERO)
);

create table DiscCurr(
	PERIODO INT not null,
	CARACTER VARCHAR(50) not null,
	TIMETABLEBD_DISCIPLINA_FK NUMERIC(19,0) not null,
	TIMETABLEBD_CURRICULO_FK NUMERIC(19,0) not null,
	UNIQUE (TIMETABLEBD_DISCIPLINA_FK)
);

create table Curriculo(
	ID NUMERIC(19,0) not null,
	HIBERNATE_VERSION int default 0 not null,
	ANO_INICIO INT not null,
	ATIVO BOOLEAN not null,
	TIMETABLEBD_CURSO_FK NUMERIC(19,0) not null,
	primary key(ID)
);

create table Calouros(
	ID NUMERIC(19,0) not null,
	HIBERNATE_VERSION int default 0 not null,
	SEMESTRE INT not null,
	NUM_VAGAS INT not null,
	TIMETABLEBD_CURSO_FK NUMERIC(19,0) not null,
	primary key(ID)
);

create table Horario(
	HORARIO VARCHAR(21) not null,
	TIMETABLEBD_TURMA_FK NUMERIC(19,0) not null
);

alter table CURSO 
       add constraint FKC_CURSO_DISICPLINA
       foreign key (TIMETABLEBD_DISICPLINA_FK) 
       references DISCIPLINA;
	   
alter table TURMA
       add constraint FKC_TURMA_DISICPLINA
       foreign key (TIMETABLEBD_DISICPLINA_FK) 
       references DISCIPLINA;

alter table TURMA 
       add constraint FKC_TURMA_SALA
       foreign key (TIMETABLEBD_SALA_FK) 
       references SALA;
	   
alter table CREDITOMINISTRADO 
       add constraint FKC_CREDITOMINASTRADO_DOCENTE
       foreign key (TIMETABLEBD_DOCENTE_FK) 
       references DOCENTE;
	   
alter table CREDITOMINISTRADO 
       add constraint FKC_CREDITOMINASTRADO_TURMA
       foreign key (TIMETABLEBD_TURMA_FK) 
       references TURMA;
	   
alter table DISCCURR 
       add constraint FKC_DISCCURR_DISCIPLINA
       foreign key (TIMETABLEBD_DISCIPLINA_FK) 
       references DISCIPLINA;
	   
alter table DISCCURR 
       add constraint FKC_DISCCURR_CURRICULO
       foreign key (TIMETABLEBD_CURRICULO_FK) 
       references CURRICULO;
	   
alter table CURRICULO 
       add constraint FKC_CURRICULO_CURSO
       foreign key (TIMETABLEBD_CURSO_FK) 
       references CURSO;
	   
alter table CALOUROS
       add constraint FKC_CALOUROS_CURSO
       foreign key (TIMETABLEBD_CURSO_FK)
       references CURSO;

alter table HORARIO
       add constraint FKC_HORARIO_TURMA
       foreign key (TIMETABLEBD_TURMA_FK)
       references TURMA;