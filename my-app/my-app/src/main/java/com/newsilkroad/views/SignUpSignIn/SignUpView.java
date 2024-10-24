package com.newsilkroad.views.SignUpSignIn;

import com.newsilkroad.views.MainLayout;
import com.newsilkroad.views.imagegallery.ImageGalleryView;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.VaadinSession;
import com.vaadin.flow.spring.annotation.UIScope;

@UIScope
@PageTitle("Registration")
@Route(value = "registration", layout = MainLayout.class)
public class SignUpView extends VerticalLayout {

    private TextField firstName = new TextField("First Name");
    private TextField secondName = new TextField("Second Name");
    private TextField username = new TextField("Username");
    private TextField email = new TextField("Email");
    private PasswordField password = new PasswordField("Password");
    private PasswordField confirmPassword = new PasswordField("Confirm Password");

    public SignUpView() {
        FormLayout form = new FormLayout();
        getElement().getStyle().set("background", "url('/BackGround/1.jpg') no-repeat center center fixed");
        getElement().getStyle().set("background-size", "cover");

        firstName.setWidth("400px");
        secondName.setWidth("400px");
        username.setWidth("400px");
        email.setWidth("400px");
        password.setWidth("400px");
        confirmPassword.setWidth("400px");

        form.getStyle().set("max-width", "630px");
        form.getStyle().set("margin", "auto");
        form.getStyle().set("margin-top", "140px");
        form.getStyle().set("margin-bottom", "80px");
        form.getStyle().set("margin-left", "400px");

        Button registerButton = new Button("Register", event -> register());
        form.addFormItem(firstName, "");
        form.addFormItem(secondName, "");
        form.addFormItem(username, "");
        form.addFormItem(email, "");
        form.addFormItem(password, "");
        form.addFormItem(confirmPassword, "");

        add(form, registerButton);
        setAlignItems(Alignment.CENTER);
    }

    private void register() {
        String enteredFirstName = firstName.getValue();
        String enteredSecondName = secondName.getValue();
        String enteredUsername = username.getValue();
        String enteredEmail = email.getValue();
        String enteredPassword = password.getValue();
        String enteredConfirmPassword = confirmPassword.getValue();

        if (enteredUsername.isEmpty() || enteredPassword.isEmpty() || enteredConfirmPassword.isEmpty() || enteredEmail.isEmpty()
                || enteredFirstName.isEmpty() || enteredSecondName.isEmpty()) {
            Notification.show("Please fill in all fields", 3000, Notification.Position.MIDDLE);
            return;
        }

        if (!enteredPassword.equals(enteredConfirmPassword)) {
            Notification.show("Passwords do not match", 3000, Notification.Position.MIDDLE);
            return;
        }

        // Создайте пользователя
        User user = new User(enteredUsername, enteredEmail);

        // Сохраните пользователя в сессии
        VaadinSession.getCurrent().setAttribute(User.class, user);

        Notification.show("Registration successful", 3000, Notification.Position.MIDDLE);
        clearFields();

        getUI().ifPresent(ui -> ui.navigate(ImageGalleryView.class));
    }

    private void clearFields() {
        firstName.clear();
        secondName.clear();
        username.clear();
        email.clear();
        password.clear();
        confirmPassword.clear();
    }
}
