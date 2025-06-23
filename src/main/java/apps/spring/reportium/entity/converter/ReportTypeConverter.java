package apps.spring.reportium.entity.converter;

import apps.spring.reportium.entity.enumerations.ReportType;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = false)
public class ReportTypeConverter implements AttributeConverter<ReportType, String> {
    @Override
    public String convertToDatabaseColumn(ReportType attribute) {
        if (attribute == null) return null;
        String lower = attribute.name().toLowerCase();
        return Character.toUpperCase(lower.charAt(0)) + lower.substring(1);
    }

    @Override
    public ReportType convertToEntityAttribute(String dbData) {
        if (dbData == null) return null;
        return ReportType.valueOf(dbData.toUpperCase());
    }
}
