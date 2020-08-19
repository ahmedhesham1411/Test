package com.uriallab.haat.haat;

import com.uriallab.haat.haat.viewModels.LoginViewModel;

import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {

    LoginViewModel loginViewModel;

    @Test
    public void addition_isCorrect() throws Exception {
        assertEquals(4, 2 + 2);
    }

    @Before
    public void init() {
        loginViewModel = new LoginViewModel();
    }

    @Test
    public void emailValidator_CorrectEmailSimple_ReturnsTrue() {
        //assertThat("123",is("123"));

        boolean result = loginViewModel.register("ali", "123456");

        assertThat(result, is(true));
    }
}