package com.newsilkroad.views.dataFill;

import com.newsilkroad.views.MainLayout;
import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.dependency.Uses;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.EmailField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.spring.annotation.UIScope;

@UIScope
@PageTitle("Data Fill")
@Route(value = "data-fill", layout = MainLayout.class)
@Uses(Icon.class)
public class DataFillView extends Composite<VerticalLayout> {

    public DataFillView() {
        VerticalLayout mainLayout = new VerticalLayout();
        mainLayout.setAlignItems(FlexComponent.Alignment.CENTER);
        mainLayout.setJustifyContentMode(FlexComponent.JustifyContentMode.CENTER);
        mainLayout.setMaxWidth("max-content");
//        mainLayout.setPadding(true);
//        mainLayout.setSpacing(true);

        H3 header = new H3("Data Fill");
        header.getElement().getThemeList().add("dark");

        TextField firstNameField = new TextField("First Name");
        TextField lastNameField = new TextField("Last Name");
        EmailField emailField = new EmailField("Email");

        DatePicker birthdayPicker = new DatePicker("Birthday");
        TextField phoneNumberField = new TextField("Phone Number");
        TextField occupationField = new TextField("Business");

        VerticalLayout leftColumn = new VerticalLayout(firstNameField, lastNameField, emailField);
        leftColumn.setSpacing(true);

        VerticalLayout rightColumn = new VerticalLayout(birthdayPicker, phoneNumberField, occupationField);
        rightColumn.setSpacing(true);

        HorizontalLayout buttonLayout = new HorizontalLayout();
        buttonLayout.setWidthFull();
        buttonLayout.setJustifyContentMode(FlexComponent.JustifyContentMode.CENTER);

        Button saveButton = new Button("Save");
        saveButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        Button cancelButton = new Button("Cancel");

        buttonLayout.add(saveButton, cancelButton);

        mainLayout.add(header, leftColumn, rightColumn, buttonLayout);
        getContent().add(mainLayout);
    }
}
