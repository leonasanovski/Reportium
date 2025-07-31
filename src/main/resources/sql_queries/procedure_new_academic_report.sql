CREATE OR REPLACE PROCEDURE insert_academic_report(
    IN param_person_id INT,
    IN param_institution_id INT,
    IN param_academic_field TEXT,
    IN param_description_of_report TEXT
)
    LANGUAGE plpgsql
as
$$
DECLARE
    new_report_id INT;
BEGIN
    IF NOT EXISTS (SELECT 1
                   FROM person
                   WHERE person_id = param_person_id) THEN
        RAISE EXCEPTION 'Person with ID % does not exist', param_person_id;
    END IF;
    IF NOT EXISTS (SELECT 1
                   FROM institution
                   WHERE institution_id = param_institution_id) THEN
        RAISE EXCEPTION 'Institution with ID % does not exist', param_institution_id;
    END IF;

    INSERT INTO report (report_type, summary, created_at, person_id)
    VALUES ('Academic', param_description_of_report, CURRENT_TIMESTAMP, param_person_id)
    RETURNING report_id INTO new_report_id;

    INSERT INTO academicreport (report_id, institution_id, academic_field, description_of_report)
    VALUES (new_report_id, param_institution_id, param_academic_field, param_description_of_report);
END;
$$;