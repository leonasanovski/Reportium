package apps.spring.reportium.entity.converter;

import apps.spring.reportium.entity.enumerations.DoctorSpecialization;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = false)
public class DoctorSpecConverter implements AttributeConverter<DoctorSpecialization, String> {

    @Override
    public String convertToDatabaseColumn(DoctorSpecialization specialization) {
        if (specialization == null) return null;
        String[] parts = specialization.name().split("_");
        StringBuilder sb = new StringBuilder();
        for (String part : parts) {
            sb.append(Character.toUpperCase(part.charAt(0)))
                    .append(part.substring(1).toLowerCase())
                    .append(" ");
        }
        return sb.toString().trim();
    }

    @Override
    public DoctorSpecialization convertToEntityAttribute(String s) {
        if (s == null) return null;
        return DoctorSpecialization.valueOf(s.toUpperCase().replace(" ", "_"));
    }
}
