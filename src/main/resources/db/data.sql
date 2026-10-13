-- ============================================================
-- MARKETPLACE PIECES DETACHEES - Initial Data
-- ============================================================

-- Insert Profiles/Roles
INSERT INTO profile (idprofile, libelle, etat) VALUES 
('ADMIN', 'Administrateur', 'ACTIF'),
('CLIENT', 'Client', 'ACTIF'),
('BOUTIQUE', 'Responsable Boutique', 'ACTIF'),
('LIVREUR', 'Livreur', 'ACTIF'),
('AGENT_ATELIER', 'Agent Atelier', 'ACTIF'),
('MANAGER', 'Manager', 'ACTIF');

-- Insert Branche Permissions
INSERT INTO branche_permission (libelle, code, ref, date, etat) VALUES 
('Administration', 1, 'ADM', '2024-01-01', 'ACTIF'),
('Boutique', 2, 'BTQ', '2024-01-01', 'ACTIF'),
('Client', 3, 'CLT', '2024-01-01', 'ACTIF'),
('Livraison', 4, 'LIV', '2024-01-01', 'ACTIF'),
('Atelier', 5, 'ATL', '2024-01-01', 'ACTIF'),
('Rapports', 6, 'RPT', '2024-01-01', 'ACTIF');

-- Insert Permissions
INSERT INTO permission (libelle, code, ref, date, etat, idbranche) VALUES 
('Gestion utilisateurs', 1, 'USER_MGT', '2024-01-01', 'ACTIF', 1),
('Gestion boutiques', 2, 'BTQ_MGT', '2024-01-01', 'ACTIF', 1),
('Gestion pièces', 3, 'PIECE_MGT', '2024-01-01', 'ACTIF', 1),
('Gestion commandes', 4, 'CMD_MGT', '2024-01-01', 'ACTIF', 1),
('Gestion livreurs', 5, 'LIV_MGT', '2024-01-01', 'ACTIF', 1),
('Voir rapports', 6, 'VIEW_RPT', '2024-01-01', 'ACTIF', 1),
('Gestion stock', 7, 'STOCK_MGT', '2024-01-01', 'ACTIF', 2),
('Gestion promotions', 8, 'PROMO_MGT', '2024-01-01', 'ACTIF', 2),
('Passer commande', 9, 'PASS_CMD', '2024-01-01', 'ACTIF', 3),
('Voir catalogue', 10, 'VIEW_CAT', '2024-01-01', 'ACTIF', 3),
('Effectuer livraison', 11, 'DO_LIV', '2024-01-01', 'ACTIF', 4),
('Gestion rendez-vous', 12, 'RV_MGT', '2024-01-01', 'ACTIF', 5);

-- Insert Role-Profile associations
INSERT INTO role_profile (idprofile, idpermission, date, etat) VALUES 
('ADMIN', 1, '2024-01-01', 'ACTIF'),
('ADMIN', 2, '2024-01-01', 'ACTIF'),
('ADMIN', 3, '2024-01-01', 'ACTIF'),
('ADMIN', 4, '2024-01-01', 'ACTIF'),
('ADMIN', 5, '2024-01-01', 'ACTIF'),
('ADMIN', 6, '2024-01-01', 'ACTIF'),
('BOUTIQUE', 7, '2024-01-01', 'ACTIF'),
('BOUTIQUE', 8, '2024-01-01', 'ACTIF'),
('CLIENT', 9, '2024-01-01', 'ACTIF'),
('CLIENT', 10, '2024-01-01', 'ACTIF'),
('LIVREUR', 11, '2024-01-01', 'ACTIF'),
('AGENT_ATELIER', 12, '2024-01-01', 'ACTIF');

-- Insert Admin Personne (password: admin123 - bcrypt encoded)
INSERT INTO personne (prenom, nom, email, tel, role, etat, login, motpasse, date_creation) VALUES 
('Admin', 'System', 'admin@marketplace.com', 77889900, 'ADMIN', 'ACTIF', 'admin', 
'$2a$12$LQv3c1yqBWVHxkd0LHAkCOYz6TtxMQJqhN8/LewKyNiAYMyzJ/IjK', -- admin123
NOW());

-- Insert Sample Boutiques
INSERT INTO boutique (libelle, date, nombre, tel, email, description, etat) VALUES 
('Auto Pieces Dakar', '2024-01-01', 0, 773331111, 'contact@autopiecesdakar.com', 
'Spécialiste pièces Toyota et Honda', 'ACTIF'),
('Mécanique Express', '2024-01-01', 0, 772222222, 'info@mecaniqueexpress.com', 
'Pièces détachées toutes marques', 'ACTIF'),
('Carrosserie Pro', '2024-01-01', 0, 776663333, 'contact@carrosseriepro.com', 
'Spécialiste carrosserie et peinture', 'ACTIF');

-- Insert Sample Categories
INSERT INTO categorie_piece (libelle, code, ref, date, nombre) VALUES 
('Moteur', 1, 'MOT', '2024-01-01', 0),
('Transmission', 2, 'TRANS', '2024-01-01', 0),
('Freinage', 3, 'FREIN', '2024-01-01', 0),
('Suspension', 4, 'SUSP', '2024-01-01', 0),
('Carrosserie', 5, 'CARRO', '2024-01-01', 0),
('Électricité', 6, 'ELEC', '2024-01-01', 0),
('Échappement', 7, 'ECHAP', '2024-01-01', 0),
('Refroidissement', 8, 'REFROID', '2024-01-01', 0);

-- Insert Sub-categories for Moteur
INSERT INTO categorie_piece (libelle, code, ref, date, nombre, parent_id) VALUES 
('Courroies', 9, 'MOT_COUR', '2024-01-01', 0, 1),
('Filtres', 10, 'MOT_FILT', '2024-01-01', 0, 1),
('Bougies', 11, 'MOT_BOUG', '2024-01-01', 0, 1),
('Pistons', 12, 'MOT_PIST', '2024-01-01', 0, 1);

-- Insert Sample Piece Modeles
INSERT INTO piece_modele (libelle, marque, modele, annee) VALUES 
('Toyota Corolla 2015-2020', 'Toyota', 'Corolla', '2015-2020'),
('Honda Civic 2016-2021', 'Honda', 'Civic', '2016-2021'),
('Ford Focus 2014-2018', 'Ford', 'Focus', '2014-2018'),
('VW Golf 7 2013-2020', 'Volkswagen', 'Golf', '2013-2020'),
('Hyundai Tucson 2015-2020', 'Hyundai', 'Tucson', '2015-2020');

-- Insert Sample Pieces (for boutique 1)
INSERT INTO piece (libelle, code, ref, date, etat, likes, idboutique, idcatpiece, idpiecemodele, description) VALUES 
('Courroie de distribution Toyota', 1, 'CT-TYT-001', '2024-01-01', 'DISPONIBLE', 12, 1, 9, 1, 
'Courroie de distribution haute qualité pour Toyota Corolla'),
('Filtre à huile Honda', 2, 'FH-HND-002', '2024-01-01', 'DISPONIBLE', 8, 1, 10, 2, 
'Filtre à huile original Honda'),
('Disques de frein avant', 3, 'DF-UNI-003', '2024-01-01', 'DISPONIBLE', 15, 2, 3, 1, 
'Disques de frein avant ventilés'),
('Amortisseurs arrière', 4, 'AMT-TYT-004', '2024-01-01', 'DISPONIBLE', 6, 1, 4, 1, 
'Amortisseurs arrière gaz'),
('Batterie 12V 60Ah', 5, 'BAT-UNI-005', '2024-01-01', 'DISPONIBLE', 20, 2, 6, NULL, 
'Batterie automobile 12V 60Ah maintenance free'),
('Pot catalytique', 6, 'PC-VW-006', '2024-01-01', 'DISPONIBLE', 4, 2, 7, 4, 
'Pot catalytique homologué');

-- Insert Prices for pieces
INSERT INTO prix_piece (achat, vente, ventereduit, tva, date, idpiece) VALUES 
(15000, 25000, 22000, 18, '2024-01-01', 1),
(8000, 15000, 13500, 18, '2024-01-01', 2),
(20000, 35000, 32000, 18, '2024-01-01', 3),
(18000, 30000, 28000, 18, '2024-01-01', 4),
(45000, 75000, 70000, 18, '2024-01-01', 5),
(80000, 120000, 115000, 18, '2024-01-01', 6);

-- Insert Type Facture
INSERT INTO type_facture (libelle) VALUES 
('Facture Standard'),
('Facture Proforma'),
('Avoir'),
('Devis');

-- Insert Emplacements
INSERT INTO emplacement (pays, ville, quartier, localisation, idboutique) VALUES 
('Sénégal', 'Dakar', 'Mermoz', 'Route de Mermoz, près de la station Shell', 1),
('Sénégal', 'Dakar', 'Plateau', 'Avenue du Président Lamine Gueye', 2),
('Sénégal', 'Dakar', 'Pikine', 'Route de Pikine, km 5', 3);

COMMIT;
