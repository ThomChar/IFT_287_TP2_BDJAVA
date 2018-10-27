CREATE TABLE IF NOT EXISTS Ligue
 (
   nomLigue VARCHAR(32) NOT NULL  ,
   nbJoueursMaxParEquipe INTEGER NOT NULL  
   , PRIMARY KEY (nomLigue) 
 );

CREATE TABLE IF NOT EXISTS Participant
 (
   matricule VARCHAR(32) NOT NULL  ,
   nomEquipe VARCHAR(128) NULL,
   nom VARCHAR(32) NOT NULL  ,
   prenom VARCHAR(32) NOT NULL  ,
   motDePasse VARCHAR(32) NOT NULL  ,
   statut VARCHAR(10) NOT NULL DEFAULT 'NONE'
   , PRIMARY KEY (matricule) 
 );

CREATE TABLE IF NOT EXISTS Equipe
 (
   nomEquipe VARCHAR(128) NOT NULL  ,
   nomLigue VARCHAR(32) NOT NULL  ,
   matriculeCapitaine VARCHAR(32) NULL
   , PRIMARY KEY (nomEquipe) 
 );

CREATE TABLE IF NOT EXISTS resultat
 (
   nomEquipeA VARCHAR(128) NOT NULL  ,
   nomEquipeB VARCHAR(128) NOT NULL  ,
   scoreEquipeA INTEGER NULL  ,
   scoreEquipeB INTEGER NULL  
   , PRIMARY KEY (nomEquipeA,nomEquipeB) 
 );

ALTER TABLE Participant 
  ADD CONSTRAINT FKParticipantEquipe FOREIGN KEY (nomEquipe)
      REFERENCES Equipe (nomEquipe) ;


ALTER TABLE Equipe 
  ADD CONSTRAINT FKEquipeLigue FOREIGN KEY (nomLigue)
      REFERENCES Ligue (nomLigue) ;


ALTER TABLE Equipe 
  ADD CONSTRAINT FKEquipeParticipant FOREIGN KEY (matriculeCapitaine)
      REFERENCES participant(matricule) ;


ALTER TABLE Resultat 
  ADD CONSTRAINT FKResultatEquipe FOREIGN KEY (nomEquipeA)
      REFERENCES Equipe (nomEquipe) ;


ALTER TABLE Resultat
  ADD CONSTRAINT FKResultatEquipe1 FOREIGN KEY (nomEquipeB)
      REFERENCES Equipe (nomEquipe) ;
    