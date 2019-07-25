package com.conference.servlet.resolver;

import com.conference.Component;
import com.conference.Order;
import com.conference.servlet.ControllerRegistry;
import com.conference.validation.Valid;
import com.conference.validation.ValidationException;
import com.conference.validation.ValidationResult;
import com.conference.validation.ValidationService;

import java.lang.reflect.Parameter;

@Order(1)
@Component
public class ValidationResolver implements Resolver {
    private final ValidationService validationService;

    public ValidationResolver(ValidationService validationService) {
        this.validationService = validationService;
    }

    @Override
    public void resolve(ControllerRegistry controllerRegistry, RequestResolution requestResolution) throws ValidationException {
        Parameter[] parameters = controllerRegistry.getParameters();
        Object[] arguments = requestResolution.getArguments();
        for (int i = 0; i < parameters.length; i++) {
            if (parameters[i].isAnnotationPresent(Valid.class)) {
                Object value = arguments[i];
                ValidationResult result = validationService.validate(value);
                if (!result.isSuccess()) {
                    throw new ValidationException(result, value);
                }
            }
        }
    }
}
