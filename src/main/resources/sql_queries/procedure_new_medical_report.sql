CREATE OR REPLACE PROCEDURE insert_medical_report(
    IN param_person_id INT,
    IN param_summary TEXT,
    IN param_doctor_id INT,
    IN param_next_control_date DATE,
    IN param_diagnosis_ids INT[]
)
    LANGUAGE plpgsql
AS
$$
DECLARE
    new_report_id         INT;
    variable_diagnosis_id INT;
BEGIN
    --check if person exists
    IF NOT EXISTS (SELECT 1
                   FROM person
                   WHERE person_id = param_person_id) THEN
        RAISE EXCEPTION 'Person with ID % does not exist', param_person_id;
    END IF;
    --check if doctor exists
    IF NOT EXISTS (SELECT 1 FROM doctor WHERE doctor_id = param_doctor_id) THEN
        RAISE EXCEPTION 'Doctor with ID % does not exist', param_doctor_id;
    END IF;

    --make the generic report first
    INSERT INTO report (report_type, summary, created_at, person_id)
    VALUES ('Medical', param_summary, CURRENT_TIMESTAMP, param_person_id)
    RETURNING report_id INTO new_report_id;

    --create the medical report
    INSERT INTO medicalreport (report_id, doctor_id, next_control_date)
    VALUES (new_report_id, param_doctor_id, param_next_control_date);

    --add in the m to n relation now medical-report <-> diagnosis
    IF param_diagnosis_ids IS NOT NULL THEN
        FOREACH variable_diagnosis_id IN ARRAY param_diagnosis_ids
            LOOP
                IF NOT EXISTS (SELECT 1 FROM diagnosis WHERE variable_diagnosis_id = variable_diagnosis_id) THEN
                    RAISE EXCEPTION 'Diagnosis ID % does not exist', variable_diagnosis_id;
                END IF;
                INSERT INTO medicalreport_diagnosis (report_id, diagnosis_id, added_on)
                VALUES (new_report_id, variable_diagnosis_id, CURRENT_TIMESTAMP);
            END LOOP;
    END IF;

END;
$$;