package com.task.testclearsolutions.hendler;

import com.task.testclearsolutions.exeption.UserNotFoundException;
import com.task.testclearsolutions.service.UserService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

@RunWith(SpringRunner.class)
public class RestExceptionHandlerTest {

    @InjectMocks
    private RestExceptionHandler restExceptionHandler;

    @Mock
    private UserService userService;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testHandleUserNotFoundException() {
        String errorMessage = "User not found";
        UserNotFoundException exception = new UserNotFoundException(errorMessage);

        ResponseEntity<ErrorResponse> response = restExceptionHandler.handleUserNotFoundException(exception);

        assertThat(response.getStatusCode(), is(HttpStatus.NOT_FOUND));
        ErrorResponse errorResponse = response.getBody();
        assertThat(errorResponse.getStatus(), is(HttpStatus.NOT_FOUND.value()));
        assertThat(errorResponse.getMessage(), is(errorMessage));
    }

    @Test
    public void testHandleIllegalArgumentException() {
        String errorMessage = "Invalid argument";
        IllegalArgumentException exception = new IllegalArgumentException(errorMessage);

        ResponseEntity<ErrorResponse> response = restExceptionHandler.handleIllegalArgumentException(exception);

        assertThat(response.getStatusCode(), is(HttpStatus.BAD_REQUEST));
        ErrorResponse errorResponse = response.getBody();
        assertThat(errorResponse.getStatus(), is(HttpStatus.BAD_REQUEST.value()));
        assertThat(errorResponse.getMessage(), is(errorMessage));
    }
}
