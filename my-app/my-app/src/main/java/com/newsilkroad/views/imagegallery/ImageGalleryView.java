package com.newsilkroad.views.imagegallery;


import com.newsilkroad.views.basket.ShoppingCartView;
import com.newsilkroad.views.productDetails.ProductDetailsView;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.notification.Notification;
import com.fasterxml.jackson.annotation.JsonIgnoreType;
import com.newsilkroad.data.Product;
import com.newsilkroad.data.ProductRepository;
import com.newsilkroad.views.MainLayout;
import com.vaadin.flow.component.*;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.data.provider.Query;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;
import com.vaadin.flow.theme.lumo.Lumo;
import com.vaadin.flow.theme.lumo.LumoUtility.AlignItems;
import com.vaadin.flow.theme.lumo.LumoUtility.Display;
import com.vaadin.flow.theme.lumo.LumoUtility.FontSize;
import com.vaadin.flow.theme.lumo.LumoUtility.Gap;
import com.vaadin.flow.theme.lumo.LumoUtility.JustifyContent;
import com.vaadin.flow.theme.lumo.LumoUtility.ListStyleType;
import com.vaadin.flow.theme.lumo.LumoUtility.Margin;
import com.vaadin.flow.theme.lumo.LumoUtility.MaxWidth;
import com.vaadin.flow.theme.lumo.LumoUtility.Padding;
import com.vaadin.flow.theme.lumo.LumoUtility.TextColor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import com.vaadin.flow.router.RouterLink;



import java.text.BreakIterator;
import java.util.*;
import java.util.List;

@SpringComponent
@UIScope
@PageTitle("Electronic")
@Route(value = "electronic", layout = MainLayout.class)
public class ImageGalleryView extends Main implements HasComponents, HasStyle {
    private Div chatContainer;
    private Button openChatButton;
    private Dialog chatDialog;
    private SupportChat supportChat;

    private TextArea messageInput;
    ProductRepository productRepository;
    private OrderedList imageContainer;
    private final int pageSize = 30;
    private List<Product> ListProducts;
    private int currentPage = 1;
    private int totalPages;
    private ShoppingCartView shoppingCartView;
    int startIndex;
    int endIndex;
    String sortString = null;
    HorizontalLayout container;
    HorizontalLayout horizontalLayout;
    VerticalLayout verticalLayout;
    //  Comparator<Product> priceCompare = Comparator.comparing(Product::getPrice);
    Comparator<Product> priceCompare = new Product().thenComparing(new Product());

    public ImageGalleryView( @Autowired ProductRepository productRepository,
                             @Autowired @Qualifier("myCustomList") List<String> myCustomList,
                             @Autowired ShoppingCartView shoppingCartView
    ) {
        this.shoppingCartView = shoppingCartView;
        for (String str:myCustomList) {
            System.out.println(str);
        }


//        getElement().getStyle().set("background", "url('/images/background.jpg') no-repeat center center fixed");
        getElement().getStyle().set("background", "url('/BackGround/1.jpg') no-repeat center center fixed");
        getElement().getStyle().set("background-size", "cover");
//        getElement().getStyle().set("height", "100%");
//        getElement().getStyle().set("background-size", "100px 100px");

        this.productRepository = productRepository;
        ListProducts = productRepository.findAllVideoCardsList();
        sortString = "Price ascending"; // добавили
        sortProducts(sortString);
        totalPages = (int) Math.ceil((double)ListProducts.size() / pageSize);
        navigatePage(currentPage);

        openChatButton = new Button();
        openChatButton.setIcon(new Icon(VaadinIcon.COMMENTS));
        openChatButton.getElement().getThemeList().add("circle");
        openChatButton.getStyle().set("position", "fixed");
        openChatButton.getStyle().set("bottom", "20px");
        openChatButton.getStyle().set("right", "20px");
        openChatButton.addClickListener(event -> openChatDialog());


        openChatButton = new Button(new Icon(VaadinIcon.COMMENTS));
        openChatButton.getElement().getThemeList().add("circle");
        openChatButton.getStyle().set("position", "fixed");
        openChatButton.getStyle().set("bottom", "20px");
        openChatButton.getStyle().set("right", "20px");
        openChatButton.addClickListener(event -> openChatDialog());

        chatDialog = new Dialog();
        chatDialog.setCloseOnOutsideClick(true);
        chatDialog.setCloseOnEsc(true);
        chatDialog.setWidth("400px");
        chatDialog.setHeight("500px");

        supportChat = new SupportChat();
        chatDialog.add(supportChat);

        add(openChatButton);
    }

    private void openChatDialog() {
        chatDialog.open();
    }

    private void Header() {
        addClassNames("image-gallery-view");
        addClassNames(MaxWidth.SCREEN_LARGE, Margin.Horizontal.AUTO, Padding.Bottom.LARGE, Padding.Horizontal.LARGE);
        container = new HorizontalLayout();
        horizontalLayout = new HorizontalLayout();
        container.addClassNames(AlignItems.CENTER, JustifyContent.BETWEEN);
        verticalLayout = new VerticalLayout();
        H2 header = new H2("Electronic");
        header.addClassNames(Margin.Bottom.NONE, Margin.Top.XLARGE, FontSize.XXXLARGE);
        Paragraph description = new Paragraph("\uD83D\uDE80 The best deals on electronics! \uD83D\uDE80\n" +
                "\n" +
                "Do you want to upgrade your gadgets? We have great new products for you at the best prices!\n" +
                "\n" +
                "\uD83D\uDCF1 Smartphones for everyone: from those who prefer stylish designs to those who value performance.\n" +
                "\n" +
                "\uD83D\uDCBB Laptops and computers: Powerful and reliable devices for work and entertainment.\n" +
                "\n" +
                "\uD83C\uDFA7 Headphones and Acoustics: Get the highest sound quality for your music and movies.\n" +
                "\n" +
                "\uD83D\uDCF7 Cameras and Photo Equipment: Capture every special moment of your life with our wide range of video and photo equipment.\n" +
                "\n" +
                "\uD83D\uDD0B And much more: Chargers, protective cases, game consoles and smart home appliances!\n" +
                "\n" +
                "\uD83C\uDF1F Only here you will find the latest models at the most attractive prices!\n" +
                "\n" +
                "\uD83D\uDE9A Delivery across the country! Don't miss the chance to upgrade your equipment right now!\n" +
                "\n" +
                "Visit our store or visit our website to see all the offers!");
        description.addClassNames(Margin.Bottom.XLARGE, Margin.Top.NONE, TextColor.SECONDARY);
        description.getStyle().set("color", "black");
        verticalLayout.add(header, description);
        verticalLayout.add(horizontalLayout);
        imageContainer = new OrderedList();
        imageContainer.addClassNames(Gap.MEDIUM, Display.GRID, ListStyleType.NONE, Margin.NONE, Padding.NONE);
        container.add(verticalLayout);
        add(container, imageContainer);
    }
    private void sortPage(){
        ComboBox<String> comboBox = new ComboBox<>("Sort by:");
        comboBox.setItems("Price ascending", "Descending price");
        comboBox.addValueChangeListener(event -> {
            String selectedValue = event.getValue();
            ListProducts = productRepository.findAllVideoCardsList();
            sortProducts(selectedValue);
            navigatePage(currentPage);
        });
        comboBox.setRenderer(new ComponentRenderer<>(item ->{
            Span span = new Span(item);

            if (comboBox.getValue() != null && comboBox.getValue().equals(item)) {
                span.getStyle().set("color", "gray");
            }
            return span;
        }));
        comboBox.setAllowCustomValue(false);
        verticalLayout.add(comboBox);
    }

    private void navigatePage(int offset) {
        if (offset < 1 || offset > totalPages){
            return;
        }
        else{
            removeAll();
            Header();
            sortProducts(sortString);
        }
        currentPage = offset;
        startIndex = (currentPage - 1) * pageSize;
        endIndex = startIndex + pageSize;
        List<Product> subList =null;
        try{
            subList = ListProducts.subList(startIndex, endIndex);
        }catch (Exception e){
            endIndex = ListProducts.size();
            subList = ListProducts.subList(startIndex, endIndex);
        }
        Iterator<Product> iterator = subList.iterator();

        while(iterator.hasNext()) {
            Product product = iterator.next();
            String truncatedDescription = product.getDescription().length() > 100 ?
                    product.getDescription().substring(0, 100) + "..." :
                    product.getDescription();
            ImageGalleryViewCard card = new ImageGalleryViewCard(
                    product.getName(),
                    truncatedDescription,
                    "http://localhost:8080/api/img?img=" + product.getPicture_fileName().substring(11),
                    product.getPrice() + " " + product.getCurrencyCode(),
                    product.getDiscount() + "",
                    "methodProductInfo/id=" + product.getId()
            );

            imageContainer.add(card);

            RouterLink productDetailsLink = new RouterLink("View Product", ProductDetailsView.class, product.getId().toString());
            productDetailsLink.getStyle().set("text-decoration", "none");
            productDetailsLink.getStyle().set("color", "rgb(82, 167, 255)");
            productDetailsLink.getStyle().set("margin-top", "auto");
            card.add(productDetailsLink);

            Button addToCartButton = new Button("Add to bag");
            addToCartButton.getStyle().set("margin-top", "auto");
            card.add(addToCartButton);

            addToCartButton.addClickListener(event -> {
                Product selectedProduct = product;
                shoppingCartView.addProduct(selectedProduct);
                Notification.show("Product in bag");
            });
        }
        addButtonsToPaginationLayout();

    }

    private void addButtonsToPaginationLayout(){
        Button prevButton = new Button("Previous", click -> navigatePage(currentPage - 1));
        horizontalLayout.add(prevButton);
        for (int i = minPageNumber(); i <= maxPageNumber(); i++){
            int finalI = i;
            Button pageButton = new Button(Integer.toString(i), event -> navigatePage(finalI));
            if (i == currentPage){
                pageButton.setEnabled(false);
                pageButton.addThemeVariants(ButtonVariant.LUMO_SUCCESS);
            }
            horizontalLayout.add((pageButton));
        }
        Button maxPage = new Button(Integer.toString(totalPages), event -> navigatePage(totalPages));
        if (totalPages == currentPage){
            maxPage.setEnabled(false);
            maxPage.addThemeVariants(ButtonVariant.LUMO_SUCCESS);
        }
        Button nextButton = new Button("Next", click -> navigatePage(currentPage + 1));
        horizontalLayout.add(maxPage, nextButton);
        sortPage();
    }

    private void sortProducts(String sortName){
        if (sortName.equals("Price ascending")){
            Collections.sort(ListProducts, priceCompare.reversed());
            sortString = sortName;
        }
        else if (sortName.equals("Descending price")){
            Collections.sort(ListProducts, priceCompare);
            sortString = sortName;
        }
        else{
            sortString = null;
        }
    }
    private int minPageNumber(){
        if (currentPage == totalPages || currentPage == totalPages - 1 || currentPage == totalPages - 2
                || currentPage == totalPages - 3 || currentPage == totalPages - 4)
            return totalPages - 8;
        else
            return Math.max(1, currentPage - 4);
    }
    private int maxPageNumber(){
        if (currentPage == 1 || currentPage == 2 || currentPage == 3 || currentPage == 4 || currentPage == 5)
            return 8;
        else
            return Math.min(currentPage + 3, totalPages- 1);
    }
}
