CREATE TABLE IF NOT EXISTS employees (
    id SERIAL PRIMARY KEY,
    type VARCHAR(50) NOT NULL,
    first_name VARCHAR(100) NOT NULL,
    last_name VARCHAR(100) NOT NULL,
    salary NUMERIC(10, 2) NOT NULL,
    quantity INT DEFAULT 1,
    bonus NUMERIC(10, 2),
    contract_months INT,
    team_size INT,
    university_name VARCHAR(150)
    );
