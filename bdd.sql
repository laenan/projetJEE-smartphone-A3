#------------------------------------------------------------
#        Script MySQL.
#------------------------------------------------------------


CREATE TABLE Note(
        id_note          int (11) Auto_increment  NOT NULL ,
        API_key          Varchar (25) NOT NULL ,
        note             Int NOT NULL ,
        note_commentaire Varchar (255) NOT NULL ,
        PRIMARY KEY (id_note )
)ENGINE=InnoDB;


CREATE TABLE Menu_Restaurant(
        id_menu       int (11) Auto_increment  NOT NULL ,
        entree_nom    Varchar (255) ,
        entree_photo  Varchar (255) ,
        plat_nom      Varchar (255) ,
        plat_photo    Varchar (255) ,
        dessert_nom   Varchar (255) ,
        dessert_photo Varchar (255) ,
        tarif_menu    Float ,
        id_activite   Int NOT NULL ,
        PRIMARY KEY (id_menu )
)ENGINE=InnoDB;


CREATE TABLE Parc(
        id_parc          int (11) Auto_increment  NOT NULL ,
        nom_parc         Varchar (255) ,
        description_parc Varchar (255) ,
        PRIMARY KEY (id_parc )
)ENGINE=InnoDB;


CREATE TABLE Horaire(
        id_horaire  int (11) Auto_increment  NOT NULL ,
        date_debut  Date NOT NULL ,
        heure_debut Time NOT NULL ,
        PRIMARY KEY (id_horaire )
)ENGINE=InnoDB;


CREATE TABLE Type_activite(
        id_type  int (11) Auto_increment  NOT NULL ,
        nom_type Varchar (255) ,
        PRIMARY KEY (id_type )
)ENGINE=InnoDB;


CREATE TABLE Activite(
        id_activite          int (11) Auto_increment  NOT NULL ,
        nom_activite         Varchar (255) NOT NULL ,
        description_activite Varchar (255) NOT NULL ,
        duree_activite       Int NOT NULL ,
        photo_activite       Varchar (255) NOT NULL ,
        date_creation        Date ,
        nb_acteurs           Int NOT NULL ,
        evt_historique       Varchar (255) NOT NULL ,
        position             Int ,
        id_type              Int NOT NULL ,
        id_parc              Int NOT NULL ,
        PRIMARY KEY (id_activite )
)ENGINE=InnoDB;


CREATE TABLE a_un(
        id_activite Int NOT NULL ,
        id_horaire  Int NOT NULL ,
        PRIMARY KEY (id_activite ,id_horaire )
)ENGINE=InnoDB;


CREATE TABLE est_attribue(
        id_note              Int NOT NULL ,
        id_activite          Int NOT NULL ,
        id_note_Note         Int NOT NULL ,
        id_activite_Activite Int NOT NULL ,
        PRIMARY KEY (id_note_Note ,id_activite_Activite )
)ENGINE=InnoDB;

ALTER TABLE Menu_Restaurant ADD CONSTRAINT FK_Menu_Restaurant_id_activite FOREIGN KEY (id_activite) REFERENCES Activite(id_activite);
ALTER TABLE Activite ADD CONSTRAINT FK_Activite_id_type FOREIGN KEY (id_type) REFERENCES Type_activite(id_type);
ALTER TABLE Activite ADD CONSTRAINT FK_Activite_id_parc FOREIGN KEY (id_parc) REFERENCES Parc(id_parc);
ALTER TABLE a_un ADD CONSTRAINT FK_a_un_id_activite FOREIGN KEY (id_activite) REFERENCES Activite(id_activite);
ALTER TABLE a_un ADD CONSTRAINT FK_a_un_id_horaire FOREIGN KEY (id_horaire) REFERENCES Horaire(id_horaire);
ALTER TABLE est_attribue ADD CONSTRAINT FK_est_attribue_id_note_Note FOREIGN KEY (id_note_Note) REFERENCES Note(id_note);
ALTER TABLE est_attribue ADD CONSTRAINT FK_est_attribue_id_activite_Activite FOREIGN KEY (id_activite_Activite) REFERENCES Activite(id_activite);
