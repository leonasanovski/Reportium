-- Finds the list of persons with the most similar diagnosis
-- This functionality is part of the person report statistics in the app, in the section with stats, in medical report stats
with selected_person_diagnosis as(
    select distinct d.diagnosis_id as diagnosis_id, d.short_description as label
    from person p
             join report r on r.person_id = p.person_id
             join medicalreport_diagnosis mrd on mrd.report_id = r.report_id
             join diagnosis d on mrd.diagnosis_id = d.diagnosis_id
    where p.person_id = 14 --parameter (the most of them are person_id = 1)
)
select cast(p2.person_id as bigint),
       p2.name || ' ' || p2.surname as full_name,
       cast(count(distinct spd.diagnosis_id) as bigint) as matching_diagnoses_count,
       string_agg(distinct spd.label, ', ') as matching_labels
from selected_person_diagnosis spd
         join medicalreport_diagnosis mrd2 on mrd2.diagnosis_id = spd.diagnosis_id
         join report r2 on r2.report_id = mrd2.report_id
         join person p2 on p2.person_id = r2.person_id
where p2.person_id != 14 --parameter of the person
group by p2.person_id, p2.name, p2.surname
having count(distinct spd.diagnosis_id) >=1
order by matching_diagnoses_count desc;