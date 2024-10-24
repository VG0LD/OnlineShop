package com.newsilkroad.views.imagegallery;

import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.EmailField;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;

@SpringComponent
@UIScope
public class SupportChat extends VerticalLayout {

    private TextArea chatArea;
    private TextArea messageInput;
    private Button sendButton;

    public SupportChat() {
        setDefaultHorizontalComponentAlignment(Alignment.STRETCH);

        H3 header = new H3("Contact technical support");
        header.getElement().getThemeList().add("grey");

        chatArea = new TextArea();
        chatArea.setWidth("100%");
        chatArea.setHeight("150px");
        chatArea.setReadOnly(true);

        TextField firstNameField = new TextField("First Name");
        EmailField emailField = new EmailField("Email");

        messageInput = new TextArea();
        messageInput.setWidth("100%");
        messageInput.setHeight("100px");
        messageInput.setPlaceholder("Enter your message...");
        messageInput.addKeyDownListener(Key.ENTER, event -> sendMessage());

        sendButton = new Button("Send", VaadinIcon.PAPERPLANE.create());
        sendButton.addClickListener(event -> sendMessage());

        add(header, chatArea, firstNameField, emailField, messageInput, sendButton);
    }

    private void sendMessage() {
        String message = messageInput.getValue();
        if (!message.isEmpty()) {
            chatArea.setValue(chatArea.getValue() + "\nYou: " + message);
            messageInput.clear();
        }
    }
}

