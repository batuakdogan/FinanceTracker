-- Insert sample users (passwords are encoded versions of 'password123')
INSERT INTO users (username, password, email) VALUES
('john_doe', '$2a$10$xn3LI/AjqicFYZFruSwve.681477XaVNaUQbr1gioaWPn4t1KsnmG', 'john.doe@example.com'),
('jane_smith', '$2a$10$xn3LI/AjqicFYZFruSwve.681477XaVNaUQbr1gioaWPn4t1KsnmG', 'jane.smith@example.com');

-- Insert sample transactions for John Doe
INSERT INTO transactions (user_id, amount, type, category, description, transaction_date) VALUES
(1, 5000.00, 'INCOME', 'Salary', 'Monthly salary', CURRENT_TIMESTAMP - INTERVAL '1 month'),
(1, 1000.00, 'INCOME', 'Freelance', 'Web development project', CURRENT_TIMESTAMP - INTERVAL '15 days'),
(1, 500.00, 'EXPENSE', 'Groceries', 'Monthly groceries', CURRENT_TIMESTAMP - INTERVAL '20 days'),
(1, 1200.00, 'EXPENSE', 'Rent', 'Monthly rent', CURRENT_TIMESTAMP - INTERVAL '25 days'),
(1, 100.00, 'EXPENSE', 'Entertainment', 'Movie night', CURRENT_TIMESTAMP - INTERVAL '10 days'),
(1, 300.00, 'EXPENSE', 'Utilities', 'Electricity bill', CURRENT_TIMESTAMP - INTERVAL '5 days');

-- Insert sample transactions for Jane Smith
INSERT INTO transactions (user_id, amount, type, category, description, transaction_date) VALUES
(2, 6000.00, 'INCOME', 'Salary', 'Monthly salary', CURRENT_TIMESTAMP - INTERVAL '1 month'),
(2, 2000.00, 'INCOME', 'Bonus', 'Performance bonus', CURRENT_TIMESTAMP - INTERVAL '15 days'),
(2, 600.00, 'EXPENSE', 'Groceries', 'Monthly groceries', CURRENT_TIMESTAMP - INTERVAL '18 days'),
(2, 1500.00, 'EXPENSE', 'Rent', 'Monthly rent', CURRENT_TIMESTAMP - INTERVAL '22 days'),
(2, 200.00, 'EXPENSE', 'Entertainment', 'Concert tickets', CURRENT_TIMESTAMP - INTERVAL '8 days'),
(2, 250.00, 'EXPENSE', 'Utilities', 'Water and electricity', CURRENT_TIMESTAMP - INTERVAL '3 days');

-- Insert sample budgets for John Doe
INSERT INTO budgets (user_id, category, amount, budget_month) VALUES
(1, 'Groceries', 600.00, DATE_TRUNC('month', CURRENT_DATE)),
(1, 'Rent', 1200.00, DATE_TRUNC('month', CURRENT_DATE)),
(1, 'Entertainment', 200.00, DATE_TRUNC('month', CURRENT_DATE)),
(1, 'Utilities', 300.00, DATE_TRUNC('month', CURRENT_DATE));

-- Insert sample budgets for Jane Smith
INSERT INTO budgets (user_id, category, amount, budget_month) VALUES
(2, 'Groceries', 700.00, DATE_TRUNC('month', CURRENT_DATE)),
(2, 'Rent', 1500.00, DATE_TRUNC('month', CURRENT_DATE)),
(2, 'Entertainment', 300.00, DATE_TRUNC('month', CURRENT_DATE)),
(2, 'Utilities', 250.00, DATE_TRUNC('month', CURRENT_DATE)); 