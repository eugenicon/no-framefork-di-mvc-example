package com.conference.servlet.resolver;

import com.conference.Component;
import com.conference.Order;
import com.conference.servlet.ControllerRegistry;
import com.conference.validation.annotation.Valid;
import com.conference.validation.ValidationException;
import com.conference.validation.ValidationResult;
import com.conference.validation.ValidationService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.lang.reflect.Parameter;

@Order(1)
@Component
public class ValidationResolver implements Resolver {
    private static final Logger LOGGER = LogManager.getLogger(ValidationResolver.class);
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
                if (value == null) {
                    LOGGER.warn("Can not validate empty data of type {}", parameters[i].getType().getName());
                    throw new IllegalStateException(String.format("Can not validate empty data of type %s. Perhaps there is no appropriate converter", parameters[i].getType().getSimpleName()));
                }
                ValidationResult result = validationService.validate(value);
                if (!result.isSuccess()) {
                    LOGGER.warn("Invalid data: {}", result);
                    throw new ValidationException(result, value);
                }
            }
        }
    }
}
