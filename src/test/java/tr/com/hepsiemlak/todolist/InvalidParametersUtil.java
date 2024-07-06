package tr.com.hepsiemlak.todolist;

import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;

import java.util.stream.Stream;

public class InvalidParametersUtil {

    protected static ValidatorFactory validatorFactory;

    protected static Validator validator;

    static Stream<String> invalidIdValues() {
        return Stream.of("", "   ", null);
    }

    static Stream<String> invalidNameValues() {
        return Stream.of("", "   ", null, "t", "Lorem ipsum dolor sit amet, consectetuer adipiscing elit. " +
                "Aenean commodo ligula eget dolor. Aenean massa. Cum sociis natoque penatibus et magnis dis parturient " +
                "montes, nascetur ridiculus mus. Donec quam felis, ultricies nec, pellentesque eu, pretium quis, sem. Nul" +
                "la consequat massa quis enim. Donec pede justo, fringilla vel, aliquet nec, vulputate eget, arcu. In e" +
                "nim justo, rhoncus ut, imperdiet a, venenatis vitae, justo. Nullam dictum felis eu pede mollis pretium. " +
                "Integer tincidunt. Cras dapibus. Vivamus elementum semper nisi. Aenean vulputate eleifend tellus. Aenean " +
                "leo ligula, porttitor eu, consequat vitae, eleifend ac, enim. Aliquam lorem ante, dapibus in, viverra q" +
                "uis, feugiat a, tellus. Phasellus viverra nulla ut metus varius laoreet. Quisque rutrum. Aenean imperdi" +
                "et. Etiam ultricies nisi vel augue. Curabitur ullamcorper ultricies nisi. Nam eget dui. Etiam rhoncus" +
                ". Maecenas tempus, tellus eget condimentum rhoncus, sem quam semper libero, sit amet adipiscing sem ne" +
                "que sed ipsum. Nam quam nunc, blandit vel, luctus pulvinar, hendrerit id, lorem. Maecenas nec odio et a" +
                "nte tincidunt tempus. Donec vitae sapien ut libero venenatis faucibus. Nullam quis ante. Etiam sit amet" +
                " orci eget eros faucibus tincidunt. Duis leo. Sed fringilla mauris sit amet nibh. Donec sodales sagitti" +
                "s magna. Sed consequat, leo eget bibendum sodales, augue velit cursus nunc, quis");
    }

    @BeforeAll
    static void beforeAll() {
        validatorFactory = Validation.buildDefaultValidatorFactory();
        validator = validatorFactory.getValidator();
    }

    @AfterAll
    static void afterAll() {
        validatorFactory.close();
    }
}
