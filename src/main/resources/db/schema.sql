-- ============================================================
-- MARKETPLACE PIECES DETACHEES - Database Schema (PostgreSQL)
-- ============================================================

-- Drop tables if they exist (for development only)
DROP TABLE IF EXISTS asso_etape_livraison CASCADE;
DROP TABLE IF EXISTS livraison CASCADE;
DROP TABLE IF EXISTS ligne_commande CASCADE;
DROP TABLE IF EXISTS reglement CASCADE;
DROP TABLE IF EXISTS facture CASCADE;
DROP TABLE IF EXISTS type_facture CASCADE;
DROP TABLE IF EXISTS commande CASCADE;
DROP TABLE IF EXISTS ligne_panier CASCADE;
DROP TABLE IF EXISTS panier CASCADE;
DROP TABLE IF EXISTS valeur_avis CASCADE;
DROP TABLE IF EXISTS avis_piece CASCADE;
DROP TABLE IF EXISTS detail_piece CASCADE;
DROP TABLE IF EXISTS image_video_piece CASCADE;
DROP TABLE IF EXISTS prix_piece CASCADE;
DROP TABLE IF EXISTS piece_promotion CASCADE;
DROP TABLE IF EXISTS promotion CASCADE;
DROP TABLE IF EXISTS lot CASCADE;
DROP TABLE IF EXISTS stock CASCADE;
DROP TABLE IF EXISTS livreur_moyen_transport CASCADE;
DROP TABLE IF EXISTS livreur_boutique CASCADE;
DROP TABLE IF EXISTS agentatelier CASCADE;
DROP TABLE IF EXISTS horaire_agent CASCADE;
DROP TABLE IF EXISTS rv CASCADE;
DROP TABLE IF EXISTS manager CASCADE;
DROP TABLE IF EXISTS livreur CASCADE;
DROP TABLE IF EXISTS client_preference_livraison CASCADE;
DROP TABLE IF EXISTS mon_horaire_livraison CASCADE;
DROP TABLE IF EXISTS client_boutique CASCADE;
DROP TABLE IF EXISTS client CASCADE;
DROP TABLE IF EXISTS seuil_livraison_panier CASCADE;
DROP TABLE IF EXISTS zone_livraison CASCADE;
DROP TABLE IF EXISTS emplacement CASCADE;
DROP TABLE IF EXISTS log_action_security CASCADE;
DROP TABLE IF EXISTS role_profile CASCADE;
DROP TABLE IF EXISTS role_utilisateur CASCADE;
DROP TABLE IF EXISTS profile CASCADE;
DROP TABLE IF EXISTS permission CASCADE;
DROP TABLE IF EXISTS branche_permission CASCADE;
DROP TABLE IF EXISTS piece CASCADE;
DROP TABLE IF EXISTS piece_modele CASCADE;
DROP TABLE IF EXISTS categorie_piece CASCADE;
DROP TABLE IF EXISTS boutique CASCADE;
DROP TABLE IF EXISTS personne CASCADE;

-- ============================================================
-- TABLE: PERSONNE (Base entity for all users)
-- ============================================================
CREATE TABLE personne (
    idpersonne SERIAL PRIMARY KEY,
    prenom VARCHAR(100),
    nom VARCHAR(100) NOT NULL,
    sexe VARCHAR(10),
    datenaiss VARCHAR(20),
    lieunaiss VARCHAR(200),
    codepostal INTEGER,
    adresse TEXT,
    email VARCHAR(150) UNIQUE NOT NULL,
    email2 VARCHAR(150),
    email3 VARCHAR(150),
    tel BIGINT,
    tel2 BIGINT,
    tel3 BIGINT,
    codesecret INTEGER,
    role VARCHAR(50),
    etat VARCHAR(20) DEFAULT 'ACTIF',
    session INTEGER DEFAULT 0,
    matrimoniale VARCHAR(50),
    login VARCHAR(100) UNIQUE NOT NULL,
    motpasse VARCHAR(255) NOT NULL,
    date_creation TIMESTAMP DEFAULT NOW(),
    date_maj TIMESTAMP DEFAULT NOW()
);

-- ============================================================
-- TABLE: BOUTIQUE
-- ============================================================
CREATE TABLE boutique (
    idboutique SERIAL PRIMARY KEY,
    libelle VARCHAR(300) NOT NULL,
    date VARCHAR(20),
    datemaj VARCHAR(20),
    nombre INTEGER DEFAULT 0,
    creepar INTEGER,
    tel BIGINT,
    mobile1 BIGINT,
    mobile2 BIGINT,
    mobile3 BIGINT,
    qrcode VARCHAR(500),
    modepaielivraison VARCHAR(200),
    email VARCHAR(150),
    description TEXT,
    logo VARCHAR(500),
    banniere VARCHAR(500),
    etat VARCHAR(20) DEFAULT 'ACTIF'
);

-- ============================================================
-- TABLE: CATEGORIE_PIECE
-- ============================================================
CREATE TABLE categorie_piece (
    idcatpiece SERIAL PRIMARY KEY,
    libelle VARCHAR(300) NOT NULL,
    code INTEGER,
    ref VARCHAR(100),
    date VARCHAR(20),
    datemaj VARCHAR(20),
    nombre INTEGER DEFAULT 0,
    creepar INTEGER,
    parent_id INTEGER REFERENCES categorie_piece(idcatpiece) ON DELETE SET NULL
);

-- ============================================================
-- TABLE: PIECE_MODELE
-- ============================================================
CREATE TABLE piece_modele (
    idpiecemodele SERIAL PRIMARY KEY,
    libelle VARCHAR(200),
    marque VARCHAR(100),
    modele VARCHAR(100),
    annee VARCHAR(20)
);

-- ============================================================
-- TABLE: PIECE
-- ============================================================
CREATE TABLE piece (
    idpiece SERIAL PRIMARY KEY,
    libelle VARCHAR(300) NOT NULL,
    code INTEGER,
    ref VARCHAR(100),
    date VARCHAR(20),
    datemaj VARCHAR(20),
    nombre INTEGER DEFAULT 0,
    creepar INTEGER,
    etat VARCHAR(20) DEFAULT 'DISPONIBLE',
    likes INTEGER DEFAULT 0,
    unlikes INTEGER DEFAULT 0,
    vie INTEGER DEFAULT 0,
    qrcode VARCHAR(500),
    image VARCHAR(500),
    video INTEGER,
    description TEXT,
    idboutique INTEGER REFERENCES boutique(idboutique),
    idcatpiece INTEGER REFERENCES categorie_piece(idcatpiece),
    idpiecemodele INTEGER REFERENCES piece_modele(idpiecemodele)
);

-- ============================================================
-- TABLE: PRIX_PIECE
-- ============================================================
CREATE TABLE prix_piece (
    idprixpiece SERIAL PRIMARY KEY,
    achat INTEGER,
    vente INTEGER,
    ventereduit INTEGER,
    tva INTEGER DEFAULT 0,
    date VARCHAR(20),
    datemaj VARCHAR(20),
    reduction INTEGER DEFAULT 0,
    promotion INTEGER DEFAULT 0,
    seuilachatpromo INTEGER DEFAULT 0,
    idpiece INTEGER REFERENCES piece(idpiece) ON DELETE CASCADE
);

-- ============================================================
-- TABLE: IMAGE_VIDEO_PIECE
-- ============================================================
CREATE TABLE image_video_piece (
    idimage SERIAL PRIMARY KEY,
    type VARCHAR(20),
    url VARCHAR(500),
    ordre INTEGER DEFAULT 0,
    idpiece INTEGER REFERENCES piece(idpiece) ON DELETE CASCADE
);

-- ============================================================
-- TABLE: DETAIL_PIECE
-- ============================================================
CREATE TABLE detail_piece (
    iddetail SERIAL PRIMARY KEY,
    cle VARCHAR(100),
    valeur VARCHAR(500),
    idpiece INTEGER REFERENCES piece(idpiece) ON DELETE CASCADE
);

-- ============================================================
-- TABLE: AVIS_PIECE
-- ============================================================
CREATE TABLE avis_piece (
    idavispiece SERIAL PRIMARY KEY,
    libelle VARCHAR(200),
    code INTEGER,
    ref VARCHAR(100),
    date VARCHAR(20),
    datemaj VARCHAR(20),
    etat VARCHAR(20),
    creepar INTEGER,
    idpiece INTEGER REFERENCES piece(idpiece) ON DELETE CASCADE,
    idclient INTEGER
);

-- ============================================================
-- TABLE: VALEUR_AVIS
-- ============================================================
CREATE TABLE valeur_avis (
    idvaleuravis SERIAL PRIMARY KEY,
    libelle VARCHAR(200),
    valeur INTEGER DEFAULT 0,
    idavispiece INTEGER REFERENCES avis_piece(idavispiece) ON DELETE CASCADE
);

-- ============================================================
-- TABLE: STOCK
-- ============================================================
CREATE TABLE stock (
    idstock SERIAL PRIMARY KEY,
    libelle VARCHAR(300),
    code INTEGER,
    ref VARCHAR(100),
    date VARCHAR(20),
    datemaj VARCHAR(20),
    nombrelots INTEGER DEFAULT 0,
    creepar INTEGER,
    etat VARCHAR(20),
    qrcode VARCHAR(500),
    idboutique INTEGER REFERENCES boutique(idboutique)
);

-- ============================================================
-- TABLE: LOT
-- ============================================================
CREATE TABLE lot (
    idlot SERIAL PRIMARY KEY,
    libelle VARCHAR(300),
    code INTEGER,
    ref VARCHAR(100),
    etat VARCHAR(20),
    nombrepieces INTEGER DEFAULT 0,
    date VARCHAR(20),
    datemaj VARCHAR(20),
    creepar INTEGER,
    idstock INTEGER REFERENCES stock(idstock),
    idpiece INTEGER REFERENCES piece(idpiece)
);

-- ============================================================
-- TABLE: PROMOTION
-- ============================================================
CREATE TABLE promotion (
    idpromo SERIAL PRIMARY KEY,
    libelle VARCHAR(200),
    codepromo VARCHAR(50),
    date VARCHAR(20),
    datedebut VARCHAR(20),
    datefin VARCHAR(20),
    taux INTEGER DEFAULT 0,
    nombrearticles INTEGER DEFAULT 0,
    etat VARCHAR(20),
    qrcode VARCHAR(500),
    idboutique INTEGER REFERENCES boutique(idboutique)
);

-- ============================================================
-- TABLE: PIECE_PROMOTION
-- ============================================================
CREATE TABLE piece_promotion (
    idpiece INTEGER REFERENCES piece(idpiece) ON DELETE CASCADE,
    idpromo INTEGER REFERENCES promotion(idpromo) ON DELETE CASCADE,
    PRIMARY KEY (idpiece, idpromo)
);

-- ============================================================
-- TABLE: CLIENT
-- ============================================================
CREATE TABLE client (
    idclient SERIAL PRIMARY KEY,
    idpersonne INTEGER UNIQUE REFERENCES personne(idpersonne) ON DELETE CASCADE
);

-- ============================================================
-- TABLE: PANIER
-- ============================================================
CREATE TABLE panier (
    idpanier SERIAL PRIMARY KEY,
    date VARCHAR(20),
    datemaj VARCHAR(20),
    nombrearticles INTEGER DEFAULT 0,
    etat VARCHAR(20) DEFAULT 'EN_COURS',
    prix INTEGER DEFAULT 0,
    reduction INTEGER DEFAULT 0,
    idclient INTEGER REFERENCES client(idclient)
);

-- ============================================================
-- TABLE: LIGNE_PANIER
-- ============================================================
CREATE TABLE ligne_panier (
    idlignepanier SERIAL PRIMARY KEY,
    date VARCHAR(20),
    datemaj VARCHAR(20),
    quantite INTEGER DEFAULT 1,
    etat VARCHAR(20),
    idpanier INTEGER REFERENCES panier(idpanier) ON DELETE CASCADE,
    idpiece INTEGER REFERENCES piece(idpiece)
);

-- ============================================================
-- TABLE: COMMANDE
-- ============================================================
CREATE TABLE commande (
    idcommande SERIAL PRIMARY KEY,
    code INTEGER,
    date VARCHAR(20),
    datemaj VARCHAR(20),
    nombrearticle INTEGER DEFAULT 0,
    etat VARCHAR(20) DEFAULT 'EN_ATTENTE',
    creepar INTEGER,
    modelivraison VARCHAR(100),
    qrcode VARCHAR(500),
    annee VARCHAR(10),
    idclient INTEGER REFERENCES client(idclient),
    idpanier INTEGER UNIQUE REFERENCES panier(idpanier)
);

-- ============================================================
-- TABLE: LIGNE_COMMANDE
-- ============================================================
CREATE TABLE ligne_commande (
    idlignecommand SERIAL PRIMARY KEY,
    quantite INTEGER DEFAULT 1,
    date VARCHAR(20),
    prix INTEGER,
    remise INTEGER DEFAULT 0,
    datemaj VARCHAR(20),
    etat VARCHAR(20),
    idcommande INTEGER REFERENCES commande(idcommande) ON DELETE CASCADE,
    idpiece INTEGER REFERENCES piece(idpiece)
);

-- ============================================================
-- TABLE: TYPE_FACTURE
-- ============================================================
CREATE TABLE type_facture (
    idtypefacture SERIAL PRIMARY KEY,
    libelle VARCHAR(200)
);

-- ============================================================
-- TABLE: FACTURE
-- ============================================================
CREATE TABLE facture (
    idfacture SERIAL PRIMARY KEY,
    date VARCHAR(20),
    datemaj VARCHAR(20),
    etat VARCHAR(20),
    montant INTEGER,
    remise INTEGER DEFAULT 0,
    creepar INTEGER,
    idcommande INTEGER REFERENCES commande(idcommande),
    idtypefacture INTEGER REFERENCES type_facture(idtypefacture)
);

-- ============================================================
-- TABLE: REGLEMENT
-- ============================================================
CREATE TABLE reglement (
    idreglement SERIAL PRIMARY KEY,
    date VARCHAR(20),
    montant INTEGER,
    mode VARCHAR(50),
    statut VARCHAR(20),
    idcommande INTEGER REFERENCES commande(idcommande)
);

-- ============================================================
-- TABLE: LIVREUR
-- ============================================================
CREATE TABLE livreur (
    idlivreur SERIAL PRIMARY KEY,
    code INTEGER,
    ref VARCHAR(100),
    date VARCHAR(20),
    datemaj VARCHAR(20),
    etat VARCHAR(20),
    idpersonne INTEGER UNIQUE REFERENCES personne(idpersonne) ON DELETE CASCADE
);

-- ============================================================
-- TABLE: ZONE_LIVRAISON
-- ============================================================
CREATE TABLE zone_livraison (
    idzone SERIAL PRIMARY KEY,
    libelle VARCHAR(200),
    code INTEGER,
    ref VARCHAR(100),
    tarif INTEGER DEFAULT 0,
    idboutique INTEGER REFERENCES boutique(idboutique)
);

-- ============================================================
-- TABLE: LIVRAISON
-- ============================================================
CREATE TABLE livraison (
    idlivraison SERIAL PRIMARY KEY,
    libellcodee INTEGER,
    ref VARCHAR(100),
    date VARCHAR(20),
    datemaj VARCHAR(20),
    heure VARCHAR(10),
    adresse TEXT,
    etat VARCHAR(20),
    annee VARCHAR(10),
    mois VARCHAR(10),
    jour VARCHAR(20),
    idcommande INTEGER UNIQUE REFERENCES commande(idcommande),
    idlivreur INTEGER REFERENCES livreur(idlivreur),
    idzone INTEGER REFERENCES zone_livraison(idzone)
);

-- ============================================================
-- TABLE: ASSO_ETAPE_LIVRAISON
-- ============================================================
CREATE TABLE asso_etape_livraison (
    idasso SERIAL PRIMARY KEY,
    libelle VARCHAR(200),
    description TEXT,
    date VARCHAR(20),
    etat VARCHAR(20),
    idlivraison INTEGER REFERENCES livraison(idlivraison)
);

-- ============================================================
-- TABLE: MANAGER
-- ============================================================
CREATE TABLE manager (
    idmanager SERIAL PRIMARY KEY,
    etat VARCHAR(20),
    idpersonne INTEGER UNIQUE REFERENCES personne(idpersonne) ON DELETE CASCADE,
    idboutique INTEGER REFERENCES boutique(idboutique)
);

-- ============================================================
-- TABLE: AGENTATELIER
-- ============================================================
CREATE TABLE agentatelier (
    idagentatelier SERIAL PRIMARY KEY,
    etat VARCHAR(20),
    idpersonne INTEGER UNIQUE REFERENCES personne(idpersonne) ON DELETE CASCADE
);

-- ============================================================
-- TABLE: HORAIRE_AGENT
-- ============================================================
CREATE TABLE horaire_agent (
    idhoraire SERIAL PRIMARY KEY,
    jour VARCHAR(20),
    heuredebut VARCHAR(10),
    heurefin VARCHAR(10),
    idagentatelier INTEGER REFERENCES agentatelier(idagentatelier)
);

-- ============================================================
-- TABLE: RV
-- ============================================================
CREATE TABLE rv (
    idrv SERIAL PRIMARY KEY,
    date VARCHAR(20),
    heure VARCHAR(10),
    motif VARCHAR(500),
    etat VARCHAR(20),
    idclient INTEGER,
    idagentatelier INTEGER REFERENCES agentatelier(idagentatelier)
);

-- ============================================================
-- TABLE: LIVREUR_BOUTIQUE
-- ============================================================
CREATE TABLE livreur_boutique (
    idlivreur INTEGER REFERENCES livreur(idlivreur) ON DELETE CASCADE,
    idboutique INTEGER REFERENCES boutique(idboutique) ON DELETE CASCADE,
    dateaffectation VARCHAR(20),
    etat VARCHAR(20),
    PRIMARY KEY (idlivreur, idboutique)
);

-- ============================================================
-- TABLE: LIVREUR_MOYEN_TRANSPORT
-- ============================================================
CREATE TABLE livreur_moyen_transport (
    idmoytransport SERIAL PRIMARY KEY,
    type VARCHAR(100),
    libelle VARCHAR(200),
    matricule VARCHAR(100),
    couleur VARCHAR(50),
    idlivreur INTEGER REFERENCES livreur(idlivreur)
);

-- ============================================================
-- TABLE: EMPLACEMENT
-- ============================================================
CREATE TABLE emplacement (
    idemplacement SERIAL PRIMARY KEY,
    pays VARCHAR(100),
    ville VARCHAR(100),
    quartier VARCHAR(100),
    localisation VARCHAR(500),
    idboutique INTEGER REFERENCES boutique(idboutique)
);

-- ============================================================
-- TABLE: CLIENT_BOUTIQUE
-- ============================================================
CREATE TABLE client_boutique (
    idclient INTEGER,
    idboutique INTEGER REFERENCES boutique(idboutique),
    dateabonnement VARCHAR(20),
    etat VARCHAR(20),
    PRIMARY KEY (idclient, idboutique)
);

-- ============================================================
-- TABLE: SEUIL_LIVRAISON_PANIER
-- ============================================================
CREATE TABLE seuil_livraison_panier (
    idseuillivpan SERIAL PRIMARY KEY,
    libelle VARCHAR(200),
    montantmin INTEGER,
    montantmax INTEGER,
    livraisonofferte BOOLEAN DEFAULT FALSE,
    reduction INTEGER DEFAULT 0,
    idboutique INTEGER REFERENCES boutique(idboutique)
);

-- ============================================================
-- TABLE: MON_HORAIRE_LIVRAISON
-- ============================================================
CREATE TABLE mon_horaire_livraison (
    idmonhorliv SERIAL PRIMARY KEY,
    libelle VARCHAR(100),
    datedebut VARCHAR(20),
    datefin VARCHAR(20),
    etat VARCHAR(20),
    idclient INTEGER
);

-- ============================================================
-- TABLE: CLIENT_PREFERENCE_LIVRAISON
-- ============================================================
CREATE TABLE client_preference_livraison (
    idprefliv SERIAL PRIMARY KEY,
    jours VARCHAR(100),
    heuredebut VARCHAR(10),
    heurefin VARCHAR(10),
    adresse TEXT,
    idclient INTEGER
);

-- ============================================================
-- TABLE: BRANCHE_PERMISSION
-- ============================================================
CREATE TABLE branche_permission (
    idbranche SERIAL PRIMARY KEY,
    libelle VARCHAR(200),
    code INTEGER,
    ref VARCHAR(100),
    date VARCHAR(20),
    datemaj VARCHAR(20),
    etat VARCHAR(20) DEFAULT 'ACTIF',
    nombre INTEGER DEFAULT 0,
    niveau INTEGER DEFAULT 0
);

-- ============================================================
-- TABLE: PERMISSION
-- ============================================================
CREATE TABLE permission (
    idpermission SERIAL PRIMARY KEY,
    libelle VARCHAR(200),
    code INTEGER,
    ref VARCHAR(100),
    date VARCHAR(20),
    datemaj VARCHAR(20),
    etat VARCHAR(20) DEFAULT 'ACTIF',
    nombre INTEGER DEFAULT 0,
    niveau INTEGER DEFAULT 0,
    idbranche INTEGER REFERENCES branche_permission(idbranche)
);

-- ============================================================
-- TABLE: PROFILE
-- ============================================================
CREATE TABLE profile (
    idprofile VARCHAR(50) PRIMARY KEY,
    libelle VARCHAR(200) NOT NULL,
    etat VARCHAR(20) DEFAULT 'ACTIF',
    date VARCHAR(20),
    datemaj VARCHAR(20),
    nombre INTEGER DEFAULT 0
);

-- ============================================================
-- TABLE: ROLE_PROFILE
-- ============================================================
CREATE TABLE role_profile (
    idprofile VARCHAR(50) REFERENCES profile(idprofile) ON DELETE CASCADE,
    idpermission INTEGER REFERENCES permission(idpermission) ON DELETE CASCADE,
    date VARCHAR(20),
    etat VARCHAR(20),
    PRIMARY KEY (idprofile, idpermission)
);

-- ============================================================
-- TABLE: ROLE_UTILISATEUR
-- ============================================================
CREATE TABLE role_utilisateur (
    idpersonne INTEGER REFERENCES personne(idpersonne) ON DELETE CASCADE,
    idprofile VARCHAR(50) REFERENCES profile(idprofile) ON DELETE CASCADE,
    date VARCHAR(20),
    datemaj VARCHAR(20),
    nombreinactif INTEGER DEFAULT 0,
    etat VARCHAR(20),
    clos INTEGER DEFAULT 0,
    datecloture VARCHAR(20),
    type VARCHAR(50),
    PRIMARY KEY (idpersonne, idprofile)
);

-- ============================================================
-- TABLE: LOG_ACTION_SECURITY
-- ============================================================
CREATE TABLE log_action_security (
    idlogaction SERIAL PRIMARY KEY,
    action VARCHAR(500),
    date VARCHAR(20),
    etat VARCHAR(20),
    tablebd VARCHAR(100),
    idpersonne INTEGER REFERENCES personne(idpersonne)
);

-- Create indexes for performance
CREATE INDEX idx_personne_login ON personne(login);
CREATE INDEX idx_personne_email ON personne(email);
CREATE INDEX idx_piece_boutique ON piece(idboutique);
CREATE INDEX idx_piece_categorie ON piece(idcatpiece);
CREATE INDEX idx_commande_client ON commande(idclient);
CREATE INDEX idx_panier_client ON panier(idclient);
CREATE INDEX idx_livraison_livreur ON livraison(idlivreur);

COMMIT;
