package com.conference.converter;

import com.conference.data.entity.Role;
import com.conference.data.entity.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import javax.servlet.http.HttpServletRequest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class RequestToUserConverterTest {
    @Mock private ConversionService conversionService;
    @Mock private HttpServletRequest request;

    @InjectMocks
    private RequestToUserConverter converter;

    @BeforeEach
    void init() {
        when(request.getParameter("id")).thenReturn("42");
        when(request.getParameter("name")).thenReturn("usr");
        when(request.getParameter("password")).thenReturn("12345");
        when(request.getParameter("email")).thenReturn("a@i.com");
        when(request.getParameter("role")).thenReturn("USER");

        when(conversionService.convert("42", Integer.class)).thenReturn(42);
        when(conversionService.convert("USER", Role.class)).thenReturn(Role.USER);
    }

    @Test
    void convert() {
        User actual = converter.convert(request);
        assertEquals(Integer.valueOf(42), actual.getId());
        assertEquals("usr", actual.getName());
        assertEquals("a@i.com", actual.getEmail());
        assertEquals("12345", actual.getPassword());
        assertEquals(Role.USER, actual.getRole());
    }
}