package com.newsilkroad.views.productDetails;

import com.newsilkroad.data.Product;
import com.newsilkroad.data.ProductRepository;
import com.newsilkroad.views.MainLayout;
import com.newsilkroad.views.basket.ShoppingCartView;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.BeforeEvent;
import com.vaadin.flow.router.HasUrlParameter;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.WildcardParameter;
import com.vaadin.flow.spring.annotation.UIScope;
import org.springframework.beans.factory.annotation.Autowired;

@UIScope
@Route(value = "product-details", layout = MainLayout.class)
public class ProductDetailsView extends VerticalLayout implements HasUrlParameter<String> {

    @Autowired
    private ShoppingCartView shoppingCartView;
    private ProductRepository productRepository;

    private H2 productName;
    private Image productImage;
    private Paragraph productDescription;
    private Span productPrice;
    private Button addToCartButton;

    public ProductDetailsView(@Autowired ProductRepository productRepository) {
        this.productRepository = productRepository;

        this.productName = new H2();
        this.productImage = new Image();
        this.productDescription = new Paragraph();
        this.productPrice = new Span();
        this.addToCartButton = new Button("Add to bag");
        addToCartButton.getStyle().set("margin-left", "1450px");

        addToCartButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);

        addToCartButton.addClickListener(event -> {
            if (product != null) {
                shoppingCartView.addProductToSession(product);
                Notification.show("Product in bag");
            }
        });

        HorizontalLayout imageAndInfoLayout = new HorizontalLayout(productImage, createProductInfoLayout());
        imageAndInfoLayout.setSpacing(true);
        imageAndInfoLayout.setAlignItems(FlexComponent.Alignment.CENTER);

        VerticalLayout productDetailsLayout = new VerticalLayout(productName, imageAndInfoLayout, addToCartButton);
        productDetailsLayout.setSpacing(true);

        add(productDetailsLayout);
    }

    private Product product;

    @Override
    public void setParameter(BeforeEvent event, @WildcardParameter String parameter) {
        Long productId = Long.parseLong(parameter);
        product = productRepository.findById(productId).orElse(null);
        updateProductDetailsView();
    }

    private void updateProductDetailsView() {
        if (product != null) {
            productName.setText(product.getName());
            productImage.setSrc("http://localhost:8080/api/img?img=" + product.getPicture_fileName().substring(11));
            productDescription.setText(product.getDescription());
            productPrice.setText(" " + product.getPrice() + "$");
        }
    }

    private VerticalLayout createProductInfoLayout() {
        Span details = new Span("Characteristics: ");
        Span weight = new Span("Weight: ");
        Span cores = new Span("Cores: ");
        Span memory = new Span("Memory: ");
        Span clockFrequency = new Span("Clock frequency: ");
        Span memoryPerformance = new Span("Memory performance: ");
        Span recommendedPowerSupply = new Span("Recommended power supply: ");
        details.getStyle().set("font-weight", "bold");
        weight.getStyle().set("font-weight", "bold");
        cores.getStyle().set("font-weight", "bold");
        memory.getStyle().set("font-weight", "bold");
        clockFrequency.getStyle().set("font-weight", "bold");
        memoryPerformance.getStyle().set("font-weight", "bold");
        recommendedPowerSupply.getStyle().set("font-weight", "bold");

        VerticalLayout productInfoLayout = new VerticalLayout(productDescription,createPriceLayout(),
                details, weight, cores, memory, clockFrequency, memoryPerformance, recommendedPowerSupply);
        productInfoLayout.setSpacing(true);
        productInfoLayout.setAlignItems(FlexComponent.Alignment.STRETCH);
        return productInfoLayout;
    }

    private HorizontalLayout createPriceLayout() {
        Span price = new Span("Price: ");
        price.getStyle().set("font-weight", "bold");

        HorizontalLayout priceLayout = new HorizontalLayout(price, productPrice);
        priceLayout.setAlignItems(FlexComponent.Alignment.BASELINE);
        return priceLayout;
    }
}