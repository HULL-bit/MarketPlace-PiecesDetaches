-- ============================================================
-- MARKETPLACE PIECES DETACHEES - Extended Data
-- ============================================================

-- Insert Annee (Years)
INSERT INTO annee (libelle, code, ref, date, nombre) VALUES 
('2015', 2015, 'AN2015', '2024-01-01', 0),
('2016', 2016, 'AN2016', '2024-01-01', 0),
('2017', 2017, 'AN2017', '2024-01-01', 0),
('2018', 2018, 'AN2018', '2024-01-01', 0),
('2019', 2019, 'AN2019', '2024-01-01', 0),
('2020', 2020, 'AN2020', '2024-01-01', 0),
('2021', 2021, 'AN2021', '2024-01-01', 0),
('2022', 2022, 'AN2022', '2024-01-01', 0),
('2023', 2023, 'AN2023', '2024-01-01', 0),
('2024', 2024, 'AN2024', '2024-01-01', 0);

-- Insert Mois (Months)
INSERT INTO mois (libelle, code, ref, date, nombre) VALUES 
('Janvier', 1, 'MOIS01', '2024-01-01', 0),
('Février', 2, 'MOIS02', '2024-01-01', 0),
('Mars', 3, 'MOIS03', '2024-01-01', 0),
('Avril', 4, 'MOIS04', '2024-01-01', 0),
('Mai', 5, 'MOIS05', '2024-01-01', 0),
('Juin', 6, 'MOIS06', '2024-01-01', 0),
('Juillet', 7, 'MOIS07', '2024-01-01', 0),
('Août', 8, 'MOIS08', '2024-01-01', 0),
('Septembre', 9, 'MOIS09', '2024-01-01', 0),
('Octobre', 10, 'MOIS10', '2024-01-01', 0),
('Novembre', 11, 'MOIS11', '2024-01-01', 0),
('Décembre', 12, 'MOIS12', '2024-01-01', 0);

-- Insert Jour (Days)
INSERT INTO jour (libelle, code, ref, date, nombre) VALUES 
('Lundi', 1, 'JOUR01', '2024-01-01', 0),
('Mardi', 2, 'JOUR02', '2024-01-01', 0),
('Mercredi', 3, 'JOUR03', '2024-01-01', 0),
('Jeudi', 4, 'JOUR04', '2024-01-01', 0),
('Vendredi', 5, 'JOUR05', '2024-01-01', 0),
('Samedi', 6, 'JOUR06', '2024-01-01', 0),
('Dimanche', 7, 'JOUR07', '2024-01-01', 0);

-- Insert Pays
INSERT INTO pays (libelle, code, ref, date, nombre) VALUES 
('Sénégal', 1, 'PAYS_SN', '2024-01-01', 0),
('Mali', 2, 'PAYS_ML', '2024-01-01', 0),
('Côte d\'Ivoire', 3, 'PAYS_CI', '2024-01-01', 0),
('Burkina Faso', 4, 'PAYS_BF', '2024-01-01', 0),
('Guinée', 5, 'PAYS_GN', '2024-01-01', 0);

-- Insert Regions (Sénégal)
INSERT INTO region (libelle, code, ref, date, nombre, idpays) VALUES 
('Dakar', 1, 'REG_DKR', '2024-01-01', 0, 1),
('Thiès', 2, 'REG_THIES', '2024-01-01', 0, 1),
('Saint-Louis', 3, 'REG_SL', '2024-01-01', 0, 1),
('Kaolack', 4, 'REG_KAOL', '2024-01-01', 0, 1),
('Ziguinchor', 5, 'REG_ZIG', '2024-01-01', 0, 1);

-- Insert Localites
INSERT INTO localite (libelle, code, ref, date, nombre, idregion, idcategorielocalite) VALUES 
('Dakar Centre', 1, 'LOC_DKC', '2024-01-01', 0, 1, NULL),
('Mermoz', 2, 'LOC_MER', '2024-01-01', 0, 1, NULL),
('Plateau', 3, 'LOC_PLAT', '2024-01-01', 0, 1, NULL),
('Almadies', 4, 'LOC_ALM', '2024-01-01', 0, 1, NULL),
('Pikine', 5, 'LOC_PIK', '2024-01-01', 0, 1, NULL),
('Thiès Ville', 6, 'LOC_THV', '2024-01-01', 0, 2, NULL);

-- Insert Cylindres
INSERT INTO cylindre (libelle, code, ref, date, nombre, annee_min, annee_max) VALUES 
('1.2L', 1, 'CYL_1_2', '2024-01-01', 0, 2000, 2024),
('1.4L', 2, 'CYL_1_4', '2024-01-01', 0, 2000, 2024),
('1.6L', 3, 'CYL_1_6', '2024-01-01', 0, 2000, 2024),
('1.8L', 4, 'CYL_1_8', '2024-01-01', 0, 2000, 2024),
('2.0L', 5, 'CYL_2_0', '2024-01-01', 0, 2000, 2024),
('2.2L', 6, 'CYL_2_2', '2024-01-01', 0, 2000, 2024),
('2.5L', 7, 'CYL_2_5', '2024-01-01', 0, 2000, 2024),
('3.0L', 8, 'CYL_3_0', '2024-01-01', 0, 2000, 2024);

-- Insert More Personnes (Clients)
INSERT INTO personne (prenom, nom, email, tel, role, etat, login, motpasse, date_creation) VALUES 
('Mamadou', 'Diallo', 'mdiallo@gmail.com', 771234567, 'CLIENT', 'ACTIF', 'mdiallo', 
'$2a$12$LQv3c1yqBWVHxkd0LHAkCOYz6TtxMQJqhN8/LewKyNiAYMyzJ/IjK', NOW()),
('Aminata', 'Ndiaye', 'andiaye@yahoo.fr', 772345678, 'CLIENT', 'ACTIF', 'andiaye', 
'$2a$12$LQv3c1yqBWVHxkd0LHAkCOYz6TtxMQJqhN8/LewKyNiAYMyzJ/IjK', NOW()),
('Ousmane', 'Fall', 'ofall@gmail.com', 773456789, 'CLIENT', 'ACTIF', 'ofall', 
'$2a$12$LQv3c1yqBWVHxkd0LHAkCOYz6TtxMQJqhN8/LewKyNiAYMyzJ/IjK', NOW()),
('Fatou', 'Sow', 'fsow@gmail.com', 774567890, 'CLIENT', 'ACTIF', 'fsow', 
'$2a$12$LQv3c1yqBWVHxkd0LHAkCOYz6TtxMQJqhN8/LewKyNiAYMyzJ/IjK', NOW()),
('Ibrahima', 'Ba', 'iba@hotmail.com', 775678901, 'LIVREUR', 'ACTIF', 'livreur1', 
'$2a$12$LQv3c1yqBWVkd0LHAkCOYz6TtxMQJqhN8/LewKyNiAYMyzJ/IjK', NOW()),
('Jean', 'Dupont', 'jdupont@boutique.com', 776789012, 'BOUTIQUE', 'ACTIF', 'manager1', 
'$2a$12$LQv3c1yqBWVHxkd0LHAkCOYz6TtxMQJqhN8/LewKyNiAYMyzJ/IjK', NOW());

-- Insert Clients
INSERT INTO client (idclient, adresse, datenaiss) VALUES 
(2, 'Mermoz, Dakar', '1990-05-15'),
(3, 'Plateau, Dakar', '1988-11-22'),
(4, 'Almadies, Dakar', '1995-03-08'),
(5, 'Pikine, Dakar', '1992-07-30');

-- Insert Livreurs
INSERT INTO livreur (idlivreur, idzone, disponible, note) VALUES 
(6, 1, 'DISPONIBLE', 4.5);

-- Insert Managers
INSERT INTO manager (idmanager, idboutique) VALUES 
(7, 1);

-- Insert More Boutiques
INSERT INTO boutique (libelle, date, nombre, tel, email, description, etat) VALUES 
('Pieces Auto Thiès', '2024-02-01', 0, 778887777, 'contact@piecesthies.com', 
'Boutique de pièces à Thiès', 'ACTIF'),
('AutoPlus Dakar', '2024-02-15', 0, 779996666, 'vente@autoplus.com', 
'Importateur pièces Européennes', 'ACTIF'),
('Méca Express Rufisque', '2024-03-01', 0, 771112222, 'info@mecaexpress.com', 
'Spécialiste pièces rapides', 'ACTIF');

-- Update existing boutiques with manager
UPDATE boutique SET idmanager = 1 WHERE idboutique = 1;
UPDATE boutique SET idmanager = 2 WHERE idboutique = 2;
UPDATE boutique SET idmanager = 3 WHERE idboutique = 3;

-- Insert More Piece Modeles
INSERT INTO piece_modele (libelle, marque, modele, annee) VALUES 
('Peugeot 206 2000-2009', 'Peugeot', '206', '2000-2009'),
('Renault Clio 3 2005-2012', 'Renault', 'Clio 3', '2005-2012'),
('Mercedes C180 2007-2014', 'Mercedes', 'C180', '2007-2014'),
('BMW Serie 3 2005-2012', 'BMW', 'Serie 3', '2005-2012'),
('Nissan Qashqai 2014-2021', 'Nissan', 'Qashqai', '2014-2021'),
('Kia Sportage 2016-2022', 'Kia', 'Sportage', '2016-2022'),
('Dacia Duster 2010-2017', 'Dacia', 'Duster', '2010-2017'),
('Toyota Hilux 2016-2023', 'Toyota', 'Hilux', '2016-2023');

-- Insert More Categories and Sub-categories
INSERT INTO categorie_piece (libelle, code, ref, date, nombre, parent_id) VALUES 
('Embrayage', 13, 'TRANS_EMB', '2024-01-01', 0, 2),
('Boîte de vitesses', 14, 'TRANS_BV', '2024-01-01', 0, 2),
('Cardans', 15, 'TRANS_CARD', '2024-01-01', 0, 2),
('Plaquettes de frein', 16, 'FREIN_PLAQ', '2024-01-01', 0, 3),
('Étriers de frein', 17, 'FREIN_ETRI', '2024-01-01', 0, 3),
('Liquide de frein', 18, 'FREIN_LIQ', '2024-01-01', 0, 3),
('Ressorts', 19, 'SUSP_RESS', '2024-01-01', 0, 4),
('Triangles', 20, 'SUSP_TRI', '2024-01-01', 0, 4),
('Rotules', 21, 'SUSP_ROT', '2024-01-01', 0, 4),
('Pare-chocs', 22, 'CARRO_PC', '2024-01-01', 0, 5),
('Capots', 23, 'CARRO_CAP', '2024-01-01', 0, 5),
('Ailes', 24, 'CARRO_AIL', '2024-01-01', 0, 5),
('Phares', 25, 'ELEC_PHA', '2024-01-01', 0, 6),
('Feux arrière', 26, 'ELEC_FAR', '2024-01-01', 0, 6),
('Alternateurs', 27, 'ELEC_ALT', '2024-01-01', 0, 6),
('Démarreurs', 28, 'ELEC_DEM', '2024-01-01', 0, 6),
('Radiateurs', 29, 'REF_RAD', '2024-01-01', 0, 8),
('Thermostats', 30, 'REF_THERM', '2024-01-01', 0, 8),
('Pompe à eau', 31, 'REF_PA', '2024-01-01', 0, 8),
('Silencieux', 32, 'ECHAP_SIL', '2024-01-01', 0, 7),
('Tubes', 33, 'ECHAP_TUB', '2024-01-01', 0, 7);

-- Insert More Pieces (25 additional pieces)
INSERT INTO piece (libelle, code, ref, date, etat, likes, idboutique, idcatpiece, idpiecemodele, description, nombre) VALUES 
('Kit embrayage Peugeot 206', 7, 'KE-PEU-007', '2024-01-15', 'DISPONIBLE', 5, 1, 13, 6, 
'Kit embrayage complet avec mécanisme et disque', 8),
('Plaquettes de frein avant Clio 3', 8, 'PF-REN-008', '2024-01-20', 'DISPONIBLE', 12, 2, 16, 7, 
'Plaquettes de qualité premium', 15),
('Amortisseurs avant Mercedes C180', 9, 'AMT-MER-009', '2024-02-01', 'DISPONIBLE', 3, 3, 4, 8, 
'Amortisseurs hydrauliques avant', 6),
('Alternateur BMW Serie 3', 10, 'ALT-BMW-010', '2024-02-10', 'DISPONIBLE', 8, 1, 27, 9, 
'Alternateur reconditionné garanti', 4),
('Pompe à eau Nissan Qashqai', 11, 'PA-NIS-011', '2024-02-15', 'DISPONIBLE', 6, 2, 31, 10, 
'Pompe à eau avec joint', 10),
('Phare avant gauche Kia Sportage', 12, 'PHA-KIA-012', '2024-03-01', 'DISPONIBLE', 4, 3, 25, 11, 
'Phare complet avec ampoules', 5),
('Pare-chocs arrière Dacia Duster', 13, 'PC-DAC-013', '2024-03-10', 'DISPONIBLE', 2, 1, 22, 12, 
'Pare-chocs peint selon demande', 3),
('Ressorts arrière Toyota Hilux', 14, 'RES-TYT-014', '2024-03-15', 'DISPONIBLE', 7, 2, 19, 13, 
'Ressorts renforcés pour utilitaire', 8),
('Cardan gauche Peugeot 206', 15, 'CG-PEU-015', '2024-03-20', 'DISPONIBLE', 9, 3, 15, 6, 
'Cardan neuf avec soufflets', 6),
('Radiateur Clio 3', 16, 'RAD-REN-016', '2024-04-01', 'DISPONIBLE', 11, 1, 29, 7, 
'Radiateur avec ventilateur', 7),
('Démarreur Mercedes C180', 17, 'DEM-MER-017', '2024-04-05', 'DISPONIBLE', 4, 2, 28, 8, 
'Démarreur échange standard', 5),
('Boîte de vitesses BMW Serie 3', 18, 'BV-BMW-018', '2024-04-10', 'INDISPONIBLE', 1, 3, 14, 9, 
'Boîte de vitesses reconditionnée', 0),
('Étrier de frein Nissan Qashqai', 19, 'ET-NIS-019', '2024-04-15', 'DISPONIBLE', 6, 1, 17, 10, 
'Étrier complet avec plaquettes', 8),
('Feux arrière Kia Sportage', 20, 'FAR-KIA-020', '2024-04-20', 'DISPONIBLE', 3, 2, 26, 11, 
'Feux arrière LED', 6),
('Capot Dacia Duster', 21, 'CAP-DAC-021', '2024-05-01', 'DISPONIBLE', 2, 3, 23, 12, 
'Capot avec peinture au choix', 4),
('Triangle de suspension Toyota Hilux', 22, 'TRI-TYT-022', '2024-05-05', 'DISPONIBLE', 5, 1, 20, 13, 
'Triangle complet avec rotule', 9),
('Silencieux arrière Peugeot 206', 23, 'SIL-PEU-023', '2024-05-10', 'DISPONIBLE', 8, 2, 32, 6, 
'Silencieux inox', 12),
('Tube d\'échappement Clio 3', 24, 'ECH-REN-024', '2024-05-15', 'DISPONIBLE', 4, 3, 33, 7, 
'Tube intermédiaire', 7),
('Rotule de direction Mercedes C180', 25, 'ROT-MER-025', '2024-05-20', 'DISPONIBLE', 2, 1, 21, 8, 
'Rotule axiale et rotule de suspension', 10),
('Aile avant droite BMW Serie 3', 26, 'AIL-BMW-026', '2024-06-01', 'DISPONIBLE', 1, 2, 24, 9, 
'Aile à peindre', 3),
('Thermostat Nissan Qashqai', 27, 'THE-NIS-027', '2024-06-05', 'DISPONIBLE', 5, 3, 30, 10, 
'Thermostat avec calorstat', 15),
('Liquide de frein DOT 4', 28, 'LF-UNI-028', '2024-06-10', 'DISPONIBLE', 25, 1, 18, NULL, 
'Bidon 1L liquide de frein', 50),
('Filtre à air Toyota', 29, 'FA-TYT-029', '2024-06-15', 'DISPONIBLE', 18, 2, 10, 1, 
'Filtre à air haute performance', 20),
('Bougies d\'allumage x4', 30, 'BA-UNI-030', '2024-06-20', 'DISPONIBLE', 30, 3, 11, NULL, 
'Lot de 4 bougies iridium', 35),
('Courroie alternateur universelle', 31, 'CA-UNI-031', '2024-06-25', 'DISPONIBLE', 12, 1, 9, NULL, 
'Courroie trapézoïdale', 25);

-- Insert Prices for new pieces
INSERT INTO prix_piece (achat, vente, ventereduit, tva, date, idpiece) VALUES 
(35000, 55000, 52000, 18, '2024-01-15', 7),
(12000, 22000, 20000, 18, '2024-01-20', 8),
(45000, 75000, 70000, 18, '2024-02-01', 9),
(80000, 120000, 115000, 18, '2024-02-10', 10),
(25000, 42000, 40000, 18, '2024-02-15', 11),
(55000, 85000, 82000, 18, '2024-03-01', 12),
(40000, 65000, 62000, 18, '2024-03-10', 13),
(38000, 62000, 58000, 18, '2024-03-15', 14),
(28000, 48000, 45000, 18, '2024-03-20', 15),
(22000, 38000, 35000, 18, '2024-04-01', 16),
(35000, 58000, 55000, 18, '2024-04-05', 17),
(180000, 280000, 270000, 18, '2024-04-10', 18),
(45000, 72000, 68000, 18, '2024-04-15', 19),
(40000, 65000, 62000, 18, '2024-04-20', 20),
(35000, 55000, 52000, 18, '2024-05-01', 21),
(18000, 32000, 30000, 18, '2024-05-05', 22),
(25000, 42000, 39000, 18, '2024-05-10', 23),
(18000, 32000, 30000, 18, '2024-05-15', 24),
(8000, 15000, 13500, 18, '2024-05-20', 25),
(45000, 70000, 65000, 18, '2024-06-01', 26),
(12000, 22000, 20000, 18, '2024-06-05', 27),
(3000, 8000, 7500, 18, '2024-06-10', 28),
(8000, 18000, 16500, 18, '2024-06-15', 29),
(12000, 25000, 23000, 18, '2024-06-20', 30),
(5000, 12000, 11000, 18, '2024-06-25', 31);

-- Insert Moyens de Transport
INSERT INTO moyen_transport (libelle, code, date, etat) VALUES 
('Moto', 'MOTO', '2024-01-01', 'ACTIF'),
('Voiture', 'VOITURE', '2024-01-01', 'ACTIF'),
('Camionnette', 'CAMIO', '2024-01-01', 'ACTIF');

-- Insert Livreur Moyen Transport
INSERT INTO livreur_moyentransport (idlivreur, idmoyen, date, qte) VALUES 
(6, 1, '2024-01-15', 1),
(6, 2, '2024-01-15', 1);

-- Insert Zones de Livraison
INSERT INTO zone_livraison (libelle, tarif, idregion) VALUES 
('Dakar Centre', 1500, 1),
('Dakar Banlieue', 2500, 1),
('Thiès', 5000, 2),
('Saint-Louis', 8000, 3),
('Kaolack', 6000, 4);

-- Insert Preference Livraison
INSERT INTO preference_livraison (libelle, description) VALUES 
('Standard', 'Livraison en 24-48h'),
('Express', 'Livraison le jour même'),
('Programmée', 'Livraison à date choisie');

-- Insert Client Preference Livraison
INSERT INTO client_preference_livraison (idclient, idpreference, actif) VALUES 
(2, 1, 'ACTIF'),
(3, 2, 'ACTIF'),
(4, 1, 'ACTIF');

-- Insert Seuil Livraison Panier
INSERT INTO seuil_livraison_panier (montant_min, tarif, date_debut, idlocalite) VALUES 
(50000, 0, '2024-01-01', 1),
(50000, 0, '2024-01-01', 2),
(100000, 0, '2024-01-01', 5);

COMMIT;
