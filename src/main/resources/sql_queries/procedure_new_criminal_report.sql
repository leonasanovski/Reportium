CREATE OR REPLACE PROCEDURE insert_criminal_report(
    IN param_person_id INT,
    IN param_case_summary TEXT,
    IN param_location TEXT,
    IN param_is_resolved BOOLEAN,
    IN param_crime_type_id INT,
    IN param_punishment_type TEXT,
    IN param_fine_to_pay NUMERIC,
    IN param_release_date DATE
)
    LANGUAGE plpgsql
AS
$$
DECLARE
    new_report_id     INT;
    new_punishment_id INT;
BEGIN
    IF NOT EXISTS (
        SELECT 1 FROM person WHERE person_id = param_person_id
    ) THEN
        RAISE EXCEPTION 'Person with ID % does not exist', param_person_id;
    END IF;

    IF NOT EXISTS (
        SELECT 1 FROM crimetype WHERE crime_type_id = param_crime_type_id
    ) THEN
        RAISE EXCEPTION 'CrimeType with ID % does not exist', param_crime_type_id;
    END IF;

    --insert report
    INSERT INTO report (report_type, summary, created_at, person_id)
    VALUES ('Criminal', param_case_summary, CURRENT_TIMESTAMP, param_person_id)
    RETURNING report_id INTO new_report_id;

    -- insert into criminalreport
    INSERT INTO criminalreport (report_id, location, resolved, crime_type_id)
    VALUES (new_report_id, param_location, param_is_resolved, param_crime_type_id);

    --insertin punishment obj
    IF param_punishment_type = 'PRISON' AND param_release_date IS NOT NULL THEN
        INSERT INTO punishment (report_id, value_unit, punishment_type, fine_to_pay, release_date)
        VALUES (new_report_id, 'years', LOWER(param_punishment_type), NULL, param_release_date)
        RETURNING punishment_id INTO new_punishment_id;
    END IF;

    IF param_punishment_type = 'FINE' AND param_fine_to_pay IS NOT NULL THEN
        INSERT INTO punishment (report_id, value_unit, punishment_type, fine_to_pay, release_date)
        VALUES (new_report_id, 'euros', LOWER(param_punishment_type), param_fine_to_pay, NULL)
        RETURNING punishment_id INTO new_punishment_id;
    END IF;

END;
$$;
