CREATE OR REPLACE PROCEDURE insert_employment_report(
    IN param_person_id INT,
    IN param_start_date DATE,
    IN param_end_date DATE,
    IN param_job_role TEXT,
    IN param_income NUMERIC,
    IN param_summary TEXT
)
    LANGUAGE plpgsql
AS
$$
DECLARE
    new_report_id INT;
BEGIN
    --check if the person exists in the database
    IF NOT EXISTS (SELECT 1
                   FROM person
                   WHERE person_id = param_person_id) THEN
        RAISE EXCEPTION 'Person with ID % does not exist', param_person_id;
    END IF;
    --check if end date is before start date
    IF param_end_date is not null and param_end_date < param_start_date then
        raise exception 'End date can not be before starting date';
    end if;
    --checks if the income is positive value
    IF param_income is not null and param_income <= 0 then
        raise exception 'Income must be greater than 0';
    end if;
    -- Insert into report (superclass)
    INSERT INTO report (report_type, summary, created_at, person_id)
    VALUES ('Employment', param_summary, CURRENT_TIMESTAMP, param_person_id)
    RETURNING report_id INTO new_report_id;

    -- Insert into employment_report (subclass)
    INSERT INTO employmentreport (report_id, start_date, end_date, job_role, income_per_month)
    VALUES (new_report_id,
            param_start_date,
            param_end_date,
            param_job_role,
            param_income);
END;
$$;
