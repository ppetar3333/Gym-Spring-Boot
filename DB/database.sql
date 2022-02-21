drop schema if exists teretana;
create schema teretana;

create table korisnici(
	id int primary key,
    korisnickoIme varchar(50) unique,
    lozinka varchar(50) not null,
    email varchar(50) not null,
    ime varchar(20) not null,
    prezime varchar(20) not null,
    datumRodjenja date not null,
    adresa varchar(50) not null,
    brojTelefona bigint not null,
    datumRegistracije datetime not null,
    tipKorisnika enum('clan','admin'),
    blokiran bool not null,
    aktivan bool not null
);
insert into korisnici values(1,'ppetar33','pera','ppetar33@gmail.com','petar','petrovic','2001-05-08','Lajkovacka Pruga',0653432441,'2020-06-06 12:35:00','clan',false,true);
insert into korisnici values(2,'jovan33','joca','jovan33@gmail.com','jovan','jovanovic','2001-11-12','Sime Matavulja',0645212332,'2020-08-04 13:36:00','clan',false,true);
insert into korisnici values(3,'marko33','mare','marko33@gmail.com','marko','marko','2001-05-02','Jevrejska 12',0653434353,'2020-08-30 11:11:00','admin',false,true);
insert into korisnici values(4,'dejan33','deki','dejan33@gmail.com','dejan','dejanovic','2001-06-13','Jovana Djordjevica',0632524534,'2020-12-20 20:25:00','admin',false,true);

create table clanskaKarta(
	id int primary key,
    popust int not null,
    brojPoena int not null,
    clanID int,
    foreign key(clanID) REFERENCES korisnici(id),
    prihvacen bool not null,
    iskoricena bool not null,
    aktivan bool not null
);
insert into clanskaKarta values(1,25,5,1,true,false,true);
insert into clanskaKarta values(2,10,2,2,true,true,true);

create table tipTreninga(
	id int primary key,
    ime varchar(50) not null,
    opis varchar(500) not null,
    aktivan bool not null
);
insert into tipTreninga values(1,'Fitness','Jedna od najpopularnijih metoda treninga. Zahteva trening partnera koji pomaze da nastavnite seriju koju sami vise niste u mogucnosti da izvedete',true);
insert into tipTreninga values(2,'Pilates','Ova metoda je gotovo jasna. Ako je guranje tereta na bench-u pozitivno ponavljanje, negativno je kada se teg spusta. I onaj vid treninga se mora raditi sa trening partnerom. Znaci vase je da skoncetrisete na negativna ponavljanja',true);
insert into tipTreninga values(3,'Joga','Jos intenzivnija metoda od Negativnih ponavljanja. Trening partner ili dodaje jos tereta na sipku ili se osloni na sipku',true);
insert into tipTreninga values(4,'Kardio','Popularan nacin treniranja skoro kao i Forsirana ponavljanja. Krajnje je jednostavna. Kako iz serije u seriju povecavate kilaze smanjujete broj ponavljanja',true);

create table trening(
	id int primary key,
    naziv varchar(50) not null,
    trener varchar(50) not null,
    opis varchar(300) not null,
    tipTreningaID int,
    foreign key (tipTreningaID) references tipTreninga(id),
    cena float not null,
    vrstaTreninga enum('grupni','pojedinacni'),
    nivoTreninga enum('amaterski','srednji','napredni'),
    slika varchar(100) not null,
    trajanjeTreninga int not null,
    prosecnaOcena float not null,
    aktivan bool not null
);
insert into trening values(1,'Trening za grudi','Darko Marinkovic','Po najnovijim istrazivanjima jedan od najboljih treninga za grudi',3,5000,'pojedinacni','srednji','slika-treninga-1.jpg',45,4.5,true);
insert into trening values(2,'Trening za ledja','Jovan Dacin','Uobicajeni trening za ledja koji praktikuju svi rekreativci',1,3000,'grupni','amaterski','slika-treninga-2.jpg',60,4.8,true);
insert into trening values(3,'Trening za noge','Mladen Sova','Kompleksan trening za noge radi brzeg napretka',2,2500,'pojedinacni','napredni','slika-treninga-3.jpg',45,5.0,true);
insert into trening values(4,'Trening za ruke','Marinko Magla','Jednostavan trening za ruke koji je jako ucinkovit',4,3500,'grupni','srednji','slika-treninga-6.jpg',90,3.5,true);

create table treningTipoviLista(
	id int primary key,
    treningID int,
    foreign key (treningID) references trening(id),
    tipTreningaID int,
    foreign key (tipTreningaID) references tipTreninga(id),
    aktivan boolean
);

insert into treningtipovilista values (1,1,2,true);
insert into treningtipovilista values (2,1,3,true);
insert into treningtipovilista values (3,2,1,true);
insert into treningtipovilista values (4,2,2,true);
insert into treningtipovilista values (5,3,2,true);
insert into treningtipovilista values (6,4,4,true);

create table komentar(
	id int primary key,
    tekstKomentara varchar(500),
    ocena float,
    datumPostavljanja datetime,
    clanID int,
    foreign key(clanID) references korisnici(id),
    treningID int,
    foreign key(treningID) references trening(id),
    statusKomentara enum('naCekanju','odobren','nijeOdobren'),
    anoniman bool not null,
    aktivan bool not null
);
insert into komentar values(1,'Trening je bio jako dobar. Preporucujem svima.', 5.0,'2020-05-05 15:30:00',2,3,'odobren',false,true);
insert into komentar values(2,'Sve pohvale za trening. Odlican, za svaku preporuku', 4.5,'2020-06-06 12:30:00',1,2,'naCekanju',false,true);
insert into komentar values(3,'Trening nije bio toliko dobar, moze biti bolji',2.5,'2020-03-03 14:00:00',2,1,'odobren',false,true);
insert into komentar values(4,'Okej je bio trening, mada nisam zadovoljan sa uslugom trenera',3.0,'2020-04-04 12:30:00',null,4,'odobren',true,true);

create table korpa(
	id int primary key,
    nazivTreninga varchar(50) not null,
    trener varchar(50) not null,
    tipTreningaID int,
    foreign key (tipTreningaID) references tipTreninga(id),
    datumOdrzavanja datetime not null,
    cena float not null,
    aktivan bool not null
);
insert into korpa values(1,'Trening za grudi','Darko Marinkovic',3,'2020-05-05 12:30:00',5000,true);
insert into korpa values(2,'Trening za ledja','Jovan Jovanovic',2,'2020-03-03 18:30:00',4000,true);
insert into korpa values(3,'Trening za ruke','Sale Dacin',1,'2021-05-05 12:40:00',3000,true);
insert into korpa values(4,'Trening za noge','Mirko Pajcin',4,'2021-07-07 11:20:00',5000,true);

create table listaZelja(
	id int primary key,
    clanID int,
    foreign key (clanID) references korisnici(id),
    treningID int,
    foreign key (treningID) references trening(id),
    aktivan bool not null
);
insert into listaZelja values(1,2,3,true);
insert into listaZelja values(2,1,2,true);
insert into listaZelja values(3,2,4,true);
insert into listaZelja values(4,1,1,true);

create table sala(
	id int primary key,
    oznakaSale varchar(50) not null,
    kapacitet int not null,
    popunjen bool not null,
    aktivan bool not null
);
insert into sala values(1,'Prva Sala',100,true,true);
insert into sala values(2,'Druga Sala',20,true,true);
insert into sala values(3,'Treca Sala',50,false,true);
insert into sala values(4,'Cetvrta Sala',5,false,true);

create table specijalniDatum(
	id int primary key,
    datum datetime not null,
    popust int not null,
    treningID int,
    foreign key (treningID) references trening(id),
    aktivan bool not null
);
insert into specijalniDatum values(1,'2021-01-07 12:00:00',45,1,true);
insert into specijalniDatum values(2,'2021-05-06 12:00:00',50,2,true);
insert into specijalniDatum values(3,'2020-12-31 18:00:00',70,3,true);
insert into specijalniDatum values(4,'2020-08-03 13:00:00',40,4,true);

create table terminTreninga(
	id int primary key,
    salaID int,
    foreign key (salaID) references sala(id),
    treningID int,
    foreign key (treningID) references trening(id),
    clanID int,
    foreign key (clanID) references korisnici(id),
    datumOdrzavanja datetime not null,
    aktivan bool not null,
    popunjen bool not null
);
insert into terminTreninga values(1,3,2,1,'2020-03-06 12:20:00',true,false);
insert into terminTreninga values(2,4,3,2,'2020-11-17 18:20:00',true,false);
insert into terminTreninga values(3,2,4,1,'2020-09-12 13:20:00',true,false);
insert into terminTreninga values(4,1,1,2,'2020-12-28 11:20:00',true,false);

select * from komentar;
select * from clanskaKarta;
select * from korisnici;
select * from korpa;
select * from listaZelja;
select * from sala;
select * from specijalnidatum;
select * from termintreninga;
select * from tiptreninga;
select * from trening;