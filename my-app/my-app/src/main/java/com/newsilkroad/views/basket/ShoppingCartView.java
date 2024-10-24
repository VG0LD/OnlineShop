package com.newsilkroad.views.basket;

import com.newsilkroad.views.checkoutform.CheckoutFormView;
import com.newsilkroad.views.productDetails.ProductDetailsView;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.newsilkroad.data.Product;
import com.newsilkroad.views.MainLayout;
import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouterLink;
import com.vaadin.flow.server.VaadinService;
import com.vaadin.flow.server.VaadinServletRequest;
import com.vaadin.flow.spring.annotation.UIScope;
import com.vaadin.flow.theme.lumo.LumoUtility;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@UIScope
@PageTitle("Login")
@Route(value = "shopping-cart", layout = MainLayout.class)
@Component
public class ShoppingCartView extends Composite<Div> {

    @Autowired
    private HttpServletRequest request;

    private Map<Product, Integer> cartItems;
    private NumberField totalAmountField;

    public ShoppingCartView() {
        this.cartItems = new HashMap<>();
        this.totalAmountField = new NumberField("Total");
        totalAmountField.getStyle().set("border", "none");
        this.totalAmountField.setReadOnly(true);
        updateView();
    }

    private void updateView() {
        VerticalLayout cartLayout = new VerticalLayout();
        cartLayout.setDefaultHorizontalComponentAlignment(FlexComponent.Alignment.STRETCH);

        HorizontalLayout headersLayout = new HorizontalLayout();
        headersLayout.setDefaultVerticalComponentAlignment(FlexComponent.Alignment.CENTER);

        H2 imageHeader = new H2(" ");
        H2 nameHeader = new H2("Name");
        H2 amountHeader = new H2("Quantity");
        H2 priceHeader = new H2("Unit price");
        H2 totalHeader = new H2("Total amount per item");

        imageHeader.getStyle().set("font-size", "15px");
        nameHeader.getStyle().set("font-size", "15px");
        amountHeader.getStyle().set("font-size", "15px");
        priceHeader.getStyle().set("font-size", "15px");
        totalHeader.getStyle().set("font-size", "15px");

        imageHeader.getStyle().set("margin-right", "280px");
        nameHeader.getStyle().set("margin-right", "725px");
        amountHeader.getStyle().set("margin-right", "59px");
        priceHeader.getStyle().set("margin-right", "10px");

        headersLayout.add(imageHeader, nameHeader, amountHeader, priceHeader, totalHeader);

        headersLayout.setSpacing(true);
        headersLayout.setMargin(true);
        headersLayout.getStyle().set("border-radius", "20%");
        headersLayout.getStyle().set("background-color", "rgb(155, 160, 254)");
        headersLayout.getStyle().set("margin-top", "10px");

        cartLayout.add(headersLayout);

        Div cartContent = new Div();

        if (cartItems.isEmpty()) {
            Image img = new Image("images/empty-plant.png", "placeholder plant");
            img.setWidth("200px");

            H2 header = new H2("This place intentionally left empty");
            header.addClassNames(LumoUtility.Margin.Top.XLARGE, LumoUtility.Margin.Bottom.MEDIUM);

            Paragraph paragraph = new Paragraph("It’s a place where you can grow your own UI 🤗");

            VerticalLayout emptyCartLayout = new VerticalLayout(img, header, paragraph);
            emptyCartLayout.setSizeFull();
            emptyCartLayout.setJustifyContentMode(FlexComponent.JustifyContentMode.CENTER);
            emptyCartLayout.setDefaultHorizontalComponentAlignment(FlexComponent.Alignment.CENTER);
            emptyCartLayout.getStyle().set("text-align", "center");

            cartContent.add(emptyCartLayout);
        } else {
            double grandTotal = 0.0;

            for (Map.Entry<Product, Integer> entry : cartItems.entrySet()) {
                Product product = entry.getKey();
                int quantity = entry.getValue();
                double totalForProduct = product.getPrice() * quantity;
                grandTotal += totalForProduct;

                VerticalLayout itemLayout = new VerticalLayout();
                itemLayout.getStyle().set("border", "1px solid #ccc");
                itemLayout.getStyle().set("padding", "10px");
                itemLayout.getStyle().set("margin-bottom", "10px");

                itemLayout.add(new Paragraph("Изображение"));
                itemLayout.add(new Paragraph("Название: " + product.getName()));
                itemLayout.add(new Paragraph("Цена за единицу: " + product.getPrice()));
                itemLayout.add(new Paragraph("Общая сумма за товар: " + totalForProduct));

                Div itemDiv = new Div(itemLayout);
                cartContent.add(itemDiv);
                itemDiv.getStyle().set("border", "2px solid black");

                Image imageSpan = new Image();
                imageSpan.getStyle().set("width", "100px");
                imageSpan.setSrc("http://localhost:8080/api/img?img=" + product.getPicture_fileName().substring(11));

                Div nameLinkDiv = new Div();
                nameLinkDiv.getStyle().set("width", "915px");
                nameLinkDiv.getStyle().set("white-space", "nowrap");
                nameLinkDiv.getStyle().set("overflow", "hidden");
                nameLinkDiv.getStyle().set("text-overflow", "ellipsis");

                RouterLink nameLink = new RouterLink(product.getName(), ProductDetailsView.class, product.getId().toString());

                nameLinkDiv.add(nameLink);


                HorizontalLayout quantityLayout = new HorizontalLayout();
                quantityLayout.setDefaultVerticalComponentAlignment(FlexComponent.Alignment.CENTER);
                quantityLayout.setAlignSelf(FlexComponent.Alignment.START);

                NumberField quantityField = new NumberField();
                quantityField.setValue((double) quantity);
                quantityField.addValueChangeListener(event -> {
                    int newQuantity = event.getValue().intValue();
                    updateCart(product, newQuantity);
                });
                quantityField.getStyle().set("width", "50px");

                Button plusButton = new Button();
                plusButton.setIcon(new Icon(VaadinIcon.PLUS));
                plusButton.getStyle().set("background-color", "transparent");
                plusButton.getStyle().set("border", "none");
                plusButton.getStyle().set("color", "black");
                plusButton.getStyle().set("font-size", "1.5em");
                plusButton.getStyle().set("border-radius", "50%");
                plusButton.getStyle().set("box-shadow", "0px 5px 10px rgba(0, 0, 0, 0.2)");
                plusButton.addClickListener(event -> {
                    int currentQuantity = quantityField.getValue().intValue();
                    quantityField.setValue((double) (currentQuantity + 1));
                    updateCart(product, currentQuantity + 1);
                });

                Button minusButton = new Button();
                minusButton.setIcon(new Icon(VaadinIcon.MINUS));
                minusButton.getStyle().set("background-color", "transparent");
                minusButton.getStyle().set("border", "none");
                minusButton.getStyle().set("color", "black");
                minusButton.getStyle().set("font-size", "1.5em");
                minusButton.getStyle().set("border-radius", "50%");
                minusButton.getStyle().set("box-shadow", "0px 5px 10px rgba(0, 0, 0, 0.2)");
                minusButton.addClickListener(event -> {
                    int currentQuantity = quantityField.getValue().intValue();
                    if (currentQuantity > 1) {
                        quantityField.setValue((double) (currentQuantity - 1));
                        updateCart(product, currentQuantity - 1);
                    }
                });

                quantityLayout.add(minusButton, quantityField, plusButton);

                HorizontalLayout priceTotalLayout = new HorizontalLayout();
                priceTotalLayout.setDefaultVerticalComponentAlignment(FlexComponent.Alignment.CENTER);
                priceTotalLayout.setWidthFull();

                Div priceDiv = new Div();
                priceDiv.getStyle().set("width", "100px");
//                priceDiv.getStyle().set("text-align", "right");
                priceDiv.setText(String.valueOf(product.getPrice()));

                Div totalDiv = new Div();
                totalDiv.getStyle().set("width", "280px");
//                totalDiv.getStyle().set("text-align", "right");
                totalDiv.setText(String.valueOf(product.getPrice() * quantity));

                HorizontalLayout itemLayoutHorizontal = new HorizontalLayout(imageSpan, nameLinkDiv, quantityLayout, priceDiv, totalDiv);
                itemLayoutHorizontal.setDefaultVerticalComponentAlignment(FlexComponent.Alignment.CENTER);

                Div itemDiv2 = new Div(itemLayoutHorizontal);
                itemDiv.getStyle().set("border", "2px solid black");
                itemDiv.getStyle().set("margin-bottom", "10px");

                cartLayout.getStyle().set("display", "flex");

                cartLayout.add(itemDiv2);
                cartContent.add(itemLayout);
            }

            totalAmountField.setValue(grandTotal);
            cartLayout.add(totalAmountField);

            Button checkoutButton = new Button("Proceed to Checkout");
            checkoutButton.getStyle().set("background-color", "#FF1493");
            checkoutButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
            Div checkoutButtonWrapper = new Div(checkoutButton);

            checkoutButtonWrapper.getStyle().set("margin-top", "20px");
            checkoutButton.getStyle().set("width", "100%");
            checkoutButton.getStyle().set("max-width", "300px");
            checkoutButton.getStyle().set("margin", "auto");

            checkoutButton.addClickListener(event -> {
                getUI().ifPresent(ui -> ui.navigate(CheckoutFormView.class));
            });

            cartLayout.add(checkoutButton);
        }

        getContent().removeAll();
        getContent().add(cartLayout);
    }


    private void updateCart(Product product, int newQuantity) {
        HttpSession session = getSession();
        Map<Product, Integer> sessionCart = (Map<Product, Integer>) session.getAttribute("cart");

        if (sessionCart != null && sessionCart.containsKey(product)) {
            sessionCart.put(product, newQuantity);
            session.setAttribute("cart", sessionCart);
            cartItems = sessionCart;

            increaseQuantity(product, newQuantity);

            updateView();

        }
    }

    private HttpSession getSession() {
        VaadinServletRequest vaadinRequest = (VaadinServletRequest) VaadinService.getCurrentRequest();
        return vaadinRequest.getHttpServletRequest().getSession();
    }

    public void addProductToSession(Product product) {
        HttpSession session = getSession();
        Map<Product, Integer> sessionCart = (Map<Product, Integer>) session.getAttribute("cart");

        if (sessionCart == null) {
            sessionCart = new HashMap<>();
        }

        sessionCart.put(product, sessionCart.getOrDefault(product, 0) + 1);
        session.setAttribute("cart", sessionCart);
        cartItems = sessionCart;

        updateView();
    }

    private double calculateTotal() {
        double total = 0.0;

        HttpSession session = getSession();
        Map<Product, Integer> sessionCart = (Map<Product, Integer>) session.getAttribute("cart");

        if (sessionCart != null) {
            for (Map.Entry<Product, Integer> entry : sessionCart.entrySet()) {
                Product product = entry.getKey();
                int quantity = entry.getValue();
                total += product.getPrice() * quantity;
            }
        }

        return total;
    }

    public void addProduct(Product product) {
        cartItems.put(product, cartItems.getOrDefault(product, 0) + 1);
        updateView();
    }

    public Map<Product, Integer> getCartItems() {
        return cartItems;
    }

    private void increaseQuantity(Product product, int newQuantity) {
        if (cartItems.containsKey(product)) {
            int currentQuantity = cartItems.get(product);
            cartItems.put(product, currentQuantity + newQuantity);
        } else {
            cartItems.put(product, newQuantity);
        }
    }
}
