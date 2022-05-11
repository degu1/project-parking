package se.iths.parking_lot.utils;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter
public class PasswordConverter implements AttributeConverter<String, String> {

    BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    @Override
    public String convertToDatabaseColumn(String password) {
        return encoder.encode(password);
    }

    @Override
    public String convertToEntityAttribute(String s) {
        return s;
    }
}
