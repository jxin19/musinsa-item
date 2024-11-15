-- Insert INTO brand table
INSERT INTO brand (name) VALUES ('A');
INSERT INTO brand (name) VALUES ('B');
INSERT INTO brand (name) VALUES ('C');
INSERT INTO brand (name) VALUES ('D');
INSERT INTO brand (name) VALUES ('E');
INSERT INTO brand (name) VALUES ('F');
INSERT INTO brand (name) VALUES ('G');
INSERT INTO brand (name) VALUES ('H');
INSERT INTO brand (name) VALUES ('I');

-- INSERT INTO item table for brand A
INSERT INTO item (category, brand_id, retail_price) VALUES ('TOPS', (SELECT brand_id FROM brand WHERE name = 'A'), 11200);
INSERT INTO item (category, brand_id, retail_price) VALUES ('OUTERWEAR', (SELECT brand_id FROM brand WHERE name = 'A'), 5500);
INSERT INTO item (category, brand_id, retail_price) VALUES ('PANTS', (SELECT brand_id FROM brand WHERE name = 'A'), 4200);
INSERT INTO item (category, brand_id, retail_price) VALUES ('SNEAKERS', (SELECT brand_id FROM brand WHERE name = 'A'), 9000);
INSERT INTO item (category, brand_id, retail_price) VALUES ('BAGS', (SELECT brand_id FROM brand WHERE name = 'A'), 2000);
INSERT INTO item (category, brand_id, retail_price) VALUES ('HATS', (SELECT brand_id FROM brand WHERE name = 'A'), 1700);
INSERT INTO item (category, brand_id, retail_price) VALUES ('SOCKS', (SELECT brand_id FROM brand WHERE name = 'A'), 1800);
INSERT INTO item (category, brand_id, retail_price) VALUES ('ACCESSORIES', (SELECT brand_id FROM brand WHERE name = 'A'), 2300);

-- INSERT INTO item table for brand B
INSERT INTO item (category, brand_id, retail_price) VALUES ('TOPS', (SELECT brand_id FROM brand WHERE name = 'B'), 10500);
INSERT INTO item (category, brand_id, retail_price) VALUES ('OUTERWEAR', (SELECT brand_id FROM brand WHERE name = 'B'), 5900);
INSERT INTO item (category, brand_id, retail_price) VALUES ('PANTS', (SELECT brand_id FROM brand WHERE name = 'B'), 3800);
INSERT INTO item (category, brand_id, retail_price) VALUES ('SNEAKERS', (SELECT brand_id FROM brand WHERE name = 'B'), 9100);
INSERT INTO item (category, brand_id, retail_price) VALUES ('BAGS', (SELECT brand_id FROM brand WHERE name = 'B'), 2100);
INSERT INTO item (category, brand_id, retail_price) VALUES ('HATS', (SELECT brand_id FROM brand WHERE name = 'B'), 2000);
INSERT INTO item (category, brand_id, retail_price) VALUES ('SOCKS', (SELECT brand_id FROM brand WHERE name = 'B'), 2000);
INSERT INTO item (category, brand_id, retail_price) VALUES ('ACCESSORIES', (SELECT brand_id FROM brand WHERE name = 'B'), 2200);

-- INSERT INTO item table for brand C
INSERT INTO item (category, brand_id, retail_price) VALUES ('TOPS', (SELECT brand_id FROM brand WHERE name = 'C'), 10000);
INSERT INTO item (category, brand_id, retail_price) VALUES ('OUTERWEAR', (SELECT brand_id FROM brand WHERE name = 'C'), 6200);
INSERT INTO item (category, brand_id, retail_price) VALUES ('PANTS', (SELECT brand_id FROM brand WHERE name = 'C'), 3300);
INSERT INTO item (category, brand_id, retail_price) VALUES ('SNEAKERS', (SELECT brand_id FROM brand WHERE name = 'C'), 9200);
INSERT INTO item (category, brand_id, retail_price) VALUES ('BAGS', (SELECT brand_id FROM brand WHERE name = 'C'), 2200);
INSERT INTO item (category, brand_id, retail_price) VALUES ('HATS', (SELECT brand_id FROM brand WHERE name = 'C'), 1900);
INSERT INTO item (category, brand_id, retail_price) VALUES ('SOCKS', (SELECT brand_id FROM brand WHERE name = 'C'), 2200);
INSERT INTO item (category, brand_id, retail_price) VALUES ('ACCESSORIES', (SELECT brand_id FROM brand WHERE name = 'C'), 2100);

-- INSERT INTO item table for brand D
INSERT INTO item (category, brand_id, retail_price) VALUES ('TOPS', (SELECT brand_id FROM brand WHERE name = 'D'), 10100);
INSERT INTO item (category, brand_id, retail_price) VALUES ('OUTERWEAR', (SELECT brand_id FROM brand WHERE name = 'D'), 5100);
INSERT INTO item (category, brand_id, retail_price) VALUES ('PANTS', (SELECT brand_id FROM brand WHERE name = 'D'), 3000);
INSERT INTO item (category, brand_id, retail_price) VALUES ('SNEAKERS', (SELECT brand_id FROM brand WHERE name = 'D'), 9500);
INSERT INTO item (category, brand_id, retail_price) VALUES ('BAGS', (SELECT brand_id FROM brand WHERE name = 'D'), 2500);
INSERT INTO item (category, brand_id, retail_price) VALUES ('HATS', (SELECT brand_id FROM brand WHERE name = 'D'), 1500);
INSERT INTO item (category, brand_id, retail_price) VALUES ('SOCKS', (SELECT brand_id FROM brand WHERE name = 'D'), 2400);
INSERT INTO item (category, brand_id, retail_price) VALUES ('ACCESSORIES', (SELECT brand_id FROM brand WHERE name = 'D'), 2000);

-- INSERT INTO item table for brand E
INSERT INTO item (category, brand_id, retail_price) VALUES ('TOPS', (SELECT brand_id FROM brand WHERE name = 'E'), 10700);
INSERT INTO item (category, brand_id, retail_price) VALUES ('OUTERWEAR', (SELECT brand_id FROM brand WHERE name = 'E'), 5000);
INSERT INTO item (category, brand_id, retail_price) VALUES ('PANTS', (SELECT brand_id FROM brand WHERE name = 'E'), 3800);
INSERT INTO item (category, brand_id, retail_price) VALUES ('SNEAKERS', (SELECT brand_id FROM brand WHERE name = 'E'), 9900);
INSERT INTO item (category, brand_id, retail_price) VALUES ('BAGS', (SELECT brand_id FROM brand WHERE name = 'E'), 2300);
INSERT INTO item (category, brand_id, retail_price) VALUES ('HATS', (SELECT brand_id FROM brand WHERE name = 'E'), 1800);
INSERT INTO item (category, brand_id, retail_price) VALUES ('SOCKS', (SELECT brand_id FROM brand WHERE name = 'E'), 2100);
INSERT INTO item (category, brand_id, retail_price) VALUES ('ACCESSORIES', (SELECT brand_id FROM brand WHERE name = 'E'), 2100);

-- INSERT INTO item table for brand F
INSERT INTO item (category, brand_id, retail_price) VALUES ('TOPS', (SELECT brand_id FROM brand WHERE name = 'F'), 11200);
INSERT INTO item (category, brand_id, retail_price) VALUES ('OUTERWEAR', (SELECT brand_id FROM brand WHERE name = 'F'), 7200);
INSERT INTO item (category, brand_id, retail_price) VALUES ('PANTS', (SELECT brand_id FROM brand WHERE name = 'F'), 4000);
INSERT INTO item (category, brand_id, retail_price) VALUES ('SNEAKERS', (SELECT brand_id FROM brand WHERE name = 'F'), 9300);
INSERT INTO item (category, brand_id, retail_price) VALUES ('BAGS', (SELECT brand_id FROM brand WHERE name = 'F'), 2100);
INSERT INTO item (category, brand_id, retail_price) VALUES ('HATS', (SELECT brand_id FROM brand WHERE name = 'F'), 1600);
INSERT INTO item (category, brand_id, retail_price) VALUES ('SOCKS', (SELECT brand_id FROM brand WHERE name = 'F'), 2300);
INSERT INTO item (category, brand_id, retail_price) VALUES ('ACCESSORIES', (SELECT brand_id FROM brand WHERE name = 'F'), 1900);

-- INSERT INTO item table for brand G
INSERT INTO item (category, brand_id, retail_price) VALUES ('TOPS', (SELECT brand_id FROM brand WHERE name = 'G'), 10500);
INSERT INTO item (category, brand_id, retail_price) VALUES ('OUTERWEAR', (SELECT brand_id FROM brand WHERE name = 'G'), 5800);
INSERT INTO item (category, brand_id, retail_price) VALUES ('PANTS', (SELECT brand_id FROM brand WHERE name = 'G'), 3900);
INSERT INTO item (category, brand_id, retail_price) VALUES ('SNEAKERS', (SELECT brand_id FROM brand WHERE name = 'G'), 9000);
INSERT INTO item (category, brand_id, retail_price) VALUES ('BAGS', (SELECT brand_id FROM brand WHERE name = 'G'), 2200);
INSERT INTO item (category, brand_id, retail_price) VALUES ('HATS', (SELECT brand_id FROM brand WHERE name = 'G'), 1700);
INSERT INTO item (category, brand_id, retail_price) VALUES ('SOCKS', (SELECT brand_id FROM brand WHERE name = 'G'), 2100);
INSERT INTO item (category, brand_id, retail_price) VALUES ('ACCESSORIES', (SELECT brand_id FROM brand WHERE name = 'G'), 2000);

-- INSERT INTO item table for brand H
INSERT INTO item (category, brand_id, retail_price) VALUES ('TOPS', (SELECT brand_id FROM brand WHERE name = 'H'), 10800);
INSERT INTO item (category, brand_id, retail_price) VALUES ('OUTERWEAR', (SELECT brand_id FROM brand WHERE name = 'H'), 6300);
INSERT INTO item (category, brand_id, retail_price) VALUES ('PANTS', (SELECT brand_id FROM brand WHERE name = 'H'), 3100);
INSERT INTO item (category, brand_id, retail_price) VALUES ('SNEAKERS', (SELECT brand_id FROM brand WHERE name = 'H'), 9700);
INSERT INTO item (category, brand_id, retail_price) VALUES ('BAGS', (SELECT brand_id FROM brand WHERE name = 'H'), 2100);
INSERT INTO item (category, brand_id, retail_price) VALUES ('HATS', (SELECT brand_id FROM brand WHERE name = 'H'), 1600);
INSERT INTO item (category, brand_id, retail_price) VALUES ('SOCKS', (SELECT brand_id FROM brand WHERE name = 'H'), 2000);
INSERT INTO item (category, brand_id, retail_price) VALUES ('ACCESSORIES', (SELECT brand_id FROM brand WHERE name = 'H'), 2000);

-- INSERT INTO item table for brand I
INSERT INTO item (category, brand_id, retail_price) VALUES ('TOPS', (SELECT brand_id FROM brand WHERE name = 'I'), 11400);
INSERT INTO item (category, brand_id, retail_price) VALUES ('OUTERWEAR', (SELECT brand_id FROM brand WHERE name = 'I'), 6700);
INSERT INTO item (category, brand_id, retail_price) VALUES ('PANTS', (SELECT brand_id FROM brand WHERE name = 'I'), 3200);
INSERT INTO item (category, brand_id, retail_price) VALUES ('SNEAKERS', (SELECT brand_id FROM brand WHERE name = 'I'), 9500);
INSERT INTO item (category, brand_id, retail_price) VALUES ('BAGS', (SELECT brand_id FROM brand WHERE name = 'I'), 2400);
INSERT INTO item (category, brand_id, retail_price) VALUES ('HATS', (SELECT brand_id FROM brand WHERE name = 'I'), 1700);
INSERT INTO item (category, brand_id, retail_price) VALUES ('SOCKS', (SELECT brand_id FROM brand WHERE name = 'I'), 1700);
INSERT INTO item (category, brand_id, retail_price) VALUES ('ACCESSORIES', (SELECT brand_id FROM brand WHERE name = 'I'), 2400);
