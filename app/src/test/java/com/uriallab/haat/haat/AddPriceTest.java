package com.uriallab.haat.haat;

import com.uriallab.haat.haat.viewModels.AddOfferViewModel;

import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class AddPriceTest {

    AddOfferViewModel viewModel;

    @Before
    public void init() {
        viewModel = new AddOfferViewModel();
    }

    @Test
    public void validatePrice() {

        boolean result = viewModel.isAcceptedPrice("0");

        assertThat(viewModel.isAcceptedPrice("1"), is(true));
    }
}