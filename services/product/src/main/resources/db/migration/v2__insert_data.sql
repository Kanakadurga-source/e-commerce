-- Insert categories
INSERT INTO public.category (id, description, name) VALUES (nextval('category_seq'), 'Gaming keyboards and accessories', 'Keyboards');
INSERT INTO public.category (id, description, name) VALUES (nextval('category_seq'), 'Wireless accessories', 'Accessories');
INSERT INTO public.category (id, description, name) VALUES (nextval('category_seq'), 'Ergonomic equipment', 'Ergonomics');
INSERT INTO public.category (id, description, name) VALUES (nextval('category_seq'), 'High-end gaming peripherals', 'Gaming Gear');
INSERT INTO public.category (id, description, name) VALUES (nextval('category_seq'), 'Office essentials', 'Office');

-- Insert products for the 'Keyboards' category (assuming its id = 1)
-- Insert products for the 'Keyboards' category (assuming its id = 1)
INSERT INTO public.product (id, available_quantity, description, name, price, category_id) VALUES
 (nextval('product_id_seq'), 10, 'Mechanical keyboard with RGB backLight', 'MechBoard X', 89.99, 1),
 (nextval('product_id_seq'), 15, 'Wireless compact keyboard', 'KeyFree Mini', 59.99, 1),
  (nextval('product_id_seq'), 20, 'Backlit gaming keyboard with macros', 'GameType Pro', 99.99, 1),
  (nextval('product_id_seq'), 25, 'Mechanical keyboard with numpad', 'OfficeBoard', 79.99, 1),
  (nextval('product_id_seq'), 18, 'Wireless keyboard and mouse combo', 'ComboKey 2.0', 49.99, 1);
