CREATE EXTENSION IF NOT EXISTS "uuid-ossp";


CREATE TABLE offer (
		id uuid DEFAULT uuid_generate_v4 (),
		title VARCHAR ( 255 ),
		description VARCHAR ( 255 ),
		company_name VARCHAR ( 255 ),
		company_description VARCHAR ( 255 ) ,
		city_name VARCHAR ( 255 ) ,
		contract_period_value VARCHAR ( 255 ) ,
		validity_end_date  DATE,
		creation_date DATE,
		contract_type_value VARCHAR ( 255 ) ,
		member_id uuid,
		valid boolean,
		ranking float8,
		latitude float8,
		longitude float8,
		contract_type VARCHAR ( 255 ),
		PRIMARY KEY (id)
	);

CREATE TABLE company (
		name VARCHAR ( 255 ) PRIMARY KEY,
		description VARCHAR ( 255 )
);