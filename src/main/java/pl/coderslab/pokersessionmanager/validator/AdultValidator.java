package pl.coderslab.pokersessionmanager.validator;

//import javax.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

//import javax.validation.ConstraintValidatorContext;
import java.time.LocalDate;
import java.time.Period;

public class AdultValidator implements ConstraintValidator<Adult, LocalDate> {

    @Override
    public void initialize(Adult constraintAnnotation) {

    }

    @Override
    public boolean isValid(LocalDate dateOfBirth, ConstraintValidatorContext constraintValidatorContext) {
        if (dateOfBirth == null) {
            return true;
        }
        int isAdult = (Period.between(dateOfBirth, LocalDate.now())).getYears();
        return isAdult > 18;
    }
}
