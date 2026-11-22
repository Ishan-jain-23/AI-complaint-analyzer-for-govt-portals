-- ============================================================
-- db_model_sync.sql
-- Category/Department seed data ko trained model (svm_model.pkl) ke
-- exact 6 labels ke saath sync karta hai.
-- Safe to re-run: duplicate nahi banata, aur jin rows ko kisi complaint
-- ne use kiya hai unhe delete nahi karta (FK error se bachata hai).
-- Requires: backup_fixed.sql (UNIQUE keys on category_name/department_name)
-- ============================================================

USE grievance_db;

-- Purane/unused seed rows hatao (sirf jo model ke labels mein nahi hain
-- aur kisi complaint se linked nahi hain)
DELETE FROM category
WHERE category_name NOT IN
      ('electricity','healthcare','ration_aadhar','roads_transport','sanitation','water_supply')
  AND category_id NOT IN (SELECT DISTINCT category_id FROM complaint WHERE category_id IS NOT NULL);

DELETE FROM department
WHERE department_name NOT IN
      ('Electricity Board','Health Dept','Food & Civil Supplies Dept',
       'PWD (Public Works Dept)','Municipal Corporation','Water Board')
  AND department_id NOT IN (SELECT DISTINCT department_id FROM complaint WHERE department_id IS NOT NULL);

-- Model jo 6 categories predict karta hai, wahi daalo (already hain to skip)
INSERT IGNORE INTO category (category_name) VALUES
('electricity'),
('healthcare'),
('ration_aadhar'),
('roads_transport'),
('sanitation'),
('water_supply');

-- Har category ke liye ek department
INSERT IGNORE INTO department (department_name) VALUES
('Electricity Board'),          -- electricity
('Health Dept'),                -- healthcare
('Food & Civil Supplies Dept'), -- ration_aadhar
('PWD (Public Works Dept)'),    -- roads_transport
('Municipal Corporation'),      -- sanitation
('Water Board');                -- water_supply

SELECT * FROM category;
SELECT * FROM department;
