drop table CURSO cascade;

drop table DISCIPLINA cascade;

drop table DOCENTE cascade;

drop table TURMA cascade;

drop table CREDITOMINISTRADO cascade;

drop table SALA cascade;

drop table DISCCURR cascade;

drop table CURRICULO cascade;

drop table CALOUROS cascade;

drop table HORARIO cascade;

drop table CALOUROS_CURSO cascade;

create table Curso (
	ID NUMERIC(19,0) not null,
	NOME VARCHAR(21) not null,
	TURNO VARCHAR(10) not null,
	CODIGO VARCHAR(21) not null,
	/*CALOUROS_PRIMEIRO_SEMESTRE_FK NUMERIC(19,0) not null,*/
	/*CALOUROS_SEGUNDO_SEMESTRE_FK NUMERIC(19,0) not null,*/
	primary key (ID),
	unique (CODIGO)
);
	
create table Disciplina (
	ID NUMERIC(19,0) not null,
	CODIGO VARCHAR(21) not null,
	CREDITO int not null,
	NOME VARCHAR(50) not null,
	PERFIL VARCHAR(50) not null,
	primary key (ID),
	UNIQUE (CODIGO)
);

create table Docente (
	ID NUMERIC(19,0) not null,
	CODIGO VARCHAR(21) not null,
	NOME VARCHAR(50) not null,
	NOME_COMPLETO VARCHAR(100) not null,
	CREDITACAO_ESPERADA int not null,
	primary key (ID),
	UNIQUE (CODIGO)
);

create table Turma(
	ID NUMERIC(19,0) not null,
	CODIGO VARCHAR(21) not null,
	TURNO VARCHAR (10) not null,
	MAX_VAGAS INT not null,
	DISCIPLINA_FK NUMERIC(19,0) not null,
	SALA_FK NUMERIC(19,0) not null,
	primary key(ID)
);

create table CreditoMinistrado(
	ID NUMERIC(19,0) not null,
	CREDITO FLOAT not null,
	DOCENTE_FK NUMERIC(19,0) not null,
	TURMA_FK NUMERIC(19,0) not null,
	primary key(ID)
);

create table Sala(
	ID NUMERIC(19,0) not null,
	NUMERO VARCHAR(10) not null,
	LOCAL VARCHAR(21) not null,
	primary key(ID),
	UNIQUE (NUMERO)
);

create table DiscCurr(
	ID NUMERIC(19,0) not null,
	PERIODO INT not null,
	CARACTER VARCHAR(50) not null,
	TIMETABLEBD_DISCIPLINA_FK NUMERIC(19,0) not null,
	TIMETABLEBD_CURRICULO_FK NUMERIC(19,0) not null,
	primary key (ID),
	UNIQUE (TIMETABLEBD_DISCIPLINA_FK)
);

create table Curriculo(
	ID NUMERIC(19,0) not null,
	ANO_INICIO INT not null,
	ATIVO BOOLEAN not null,
	TIMETABLEBD_CURSO_FK NUMERIC(19,0) not null,
	primary key(ID)
);

create table Calouros(
	ID NUMERIC(19,0) not null,
	NUM_VAGAS INT not null,
	primary key(ID),
	UNIQUE(NUM_VAGAS)
);

create table Horario(
	ID NUMERIC(19,0) not null,
	HORARIO VARCHAR(21) not null,
	TIMETABLEBD_TURMA_FK NUMERIC(19,0) not null,
	primary key (ID)
);

create table Calouros_Curso(
	ID SERIAL PRIMARY KEY,
	CURSO_FK NUMERIC(19,0) not null,
	CALOUROS_FK NUMERIC(19,0) not null
);


alter table TURMA
       add constraint FKC_TURMA_DISICPLINA
       foreign key (DISICPLINA_FK) 
       references DISCIPLINA;

alter table TURMA 
       add constraint FKC_TURMA_SALA
       foreign key (SALA_FK) 
       references SALA;
	   
alter table CREDITOMINISTRADO 
       add constraint FKC_CREDITOMINASTRADO_DOCENTE
       foreign key (DOCENTE_FK) 
       references DOCENTE;
	   
alter table CREDITOMINISTRADO 
       add constraint FKC_CREDITOMINASTRADO_TURMA
       foreign key (TURMA_FK) 
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

alter table HORARIO
       add constraint FKC_HORARIO_TURMA
       foreign key (TIMETABLEBD_TURMA_FK)
       references TURMA;

alter table CALOUROS_CURSO
	add constraint FKC_CURSO
	foreign key (CURSO_FK)
	references CURSO;

alter table CALOUROS_CURSO
	add constraint FKC_CALOUROS
	foreign key (CALOUROS_FK)
	references CALOUROS;