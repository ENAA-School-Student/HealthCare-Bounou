create table patient(
    id BIGINT primary key auto_increment,
    nom varchar(50) not null,
    prenom varchar(50) not null,
    email varchar(100) not null unique,
    telephone VARCHAR(10) not null,
    date_naissance date
);

create table medecin(
    id BIGINT primary key auto_increment,
    nom varchar(50) not null,
    email varchar(100) not null unique,
    specialite varchar(100) not null,
    telephone varchar(10) not null
);

create table dossier_medical(
    id BIGINT primary key auto_increment,
    diagnostic varchar(255) ,
    observation varchar(255) ,
    medecin_id int not null,
    date_creation datetime not null,
    patient_id BIGINT UNIQUE NOT NULL,
    constraint fk_dm_patient foreign key (patient_id) references patient(id) on delete cascade ,
    constraint fk_dm_medecin foreign key (medecin_id) references medecin(id) on delete restrict
);

create table rendez_vous(
    id BIGINT primary key auto_increment,
    date_rendez_vous datetime not null,
    patient_id int not null,
    medecin_id int not null,
    dossier_medical_id int not null,
    status varchar(20) not null,
    constraint f_rdv_patient foreign key (patient_id) references patient(id) on delete cascade,
    constraint f_rdv_medecin foreign key (medecin_id) references medecin(id) on delete cascade ,
    constraint fk_rdv_dossier foreign key (dossier_medical_id) references dossier_medical(id) on delete casacade
);

