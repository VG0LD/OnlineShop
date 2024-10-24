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
@PageTitle("Log in")
@Route(value = "login", layout = MainLayout.class)
public class SignInView extends VerticalLayout {

    private TextField username = new TextField("Username");
    private PasswordField password = new PasswordField("Password");

    public SignInView() {
        FormLayout form = new FormLayout();
        getElement().getStyle().set("background", "url('/BackGround/1.jpg') no-repeat center center fixed");
        getElement().getStyle().set("background-size", "cover");

        username.setWidth("400px");
        password.setWidth("400px");

        form.getStyle().set("max-width", "630px");
        form.getStyle().set("margin", "auto");
        form.getStyle().set("margin-top", "100px");
        form.getStyle().set("margin-bottom", "300px");
        form.getStyle().set("margin-left", "400px");

        Button registerButton = new Button("Log in", event -> register());
        form.addFormItem(username, "");
        form.addFormItem(password, "");

        add(form, registerButton);
        setAlignItems(Alignment.CENTER);
    }

    private void register() {
        String enteredUsername = username.getValue();
        String enteredPassword = password.getValue();

        if (enteredUsername.isEmpty() || enteredPassword.isEmpty()) {
            Notification.show("Please fill in all fields", 3000, Notification.Position.MIDDLE);
            return;
        }

        // Создайте пользователя
        User user = new User(enteredUsername, "");

        // Сохраните пользователя в сессии
        VaadinSession.getCurrent().setAttribute(User.class, user);

        Notification.show("Login successful", 3000, Notification.Position.MIDDLE);
        clearFields();
        getUI().ifPresent(ui -> ui.navigate(ImageGalleryView.class));
    }

    private void clearFields() {
        username.clear();
        password.clear();
    }
}
