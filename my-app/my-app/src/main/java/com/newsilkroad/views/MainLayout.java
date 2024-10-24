package com.newsilkroad.views;

import com.newsilkroad.views.SignUpSignIn.SignInView;
import com.newsilkroad.views.SignUpSignIn.SignUpView;
import com.newsilkroad.views.about.AboutView;
import com.newsilkroad.views.basket.ShoppingCartView;
import com.newsilkroad.views.checkoutform.CheckoutFormView;
import com.newsilkroad.views.gridwithfilters.GridwithFiltersView;
import com.newsilkroad.views.helloworld.HelloWorldView;
import com.newsilkroad.views.imagegallery.ImageGalleryView;
import com.newsilkroad.views.masterdetail.MasterDetailView;
import com.newsilkroad.views.personform.PersonFormView;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.orderedlayout.Scroller;
import com.vaadin.flow.component.sidenav.SideNav;
import com.vaadin.flow.component.sidenav.SideNavItem;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.theme.lumo.LumoUtility;
import org.vaadin.lineawesome.LineAwesomeIcon;

/**
 * The main view is a top-level placeholder for other views.
 */
public class MainLayout extends AppLayout {

    private H2 viewTitle;

    private Button loginButton;
    private Button registerButton;

    public MainLayout() {
        setPrimarySection(Section.DRAWER);
        addDrawerContent();
        addHeaderContent();
    }

    private void addHeaderContent() {
        DrawerToggle toggle = new DrawerToggle();
        toggle.setAriaLabel("Menu toggle");

        viewTitle = new H2();
        viewTitle.addClassNames(LumoUtility.FontSize.LARGE, LumoUtility.Margin.NONE);

        addToNavbar(true, toggle, viewTitle);

        loginButton = new Button("Login");
        loginButton.addClickListener(event -> {
            getUI().ifPresent(ui -> ui.navigate(SignInView.class));
        });

        registerButton = new Button("Register");
        registerButton.addClickListener(event -> {
            getUI().ifPresent(ui -> ui.navigate(SignUpView.class));
        });

        loginButton.getElement().setAttribute("style", "background-color: #4681f4; margin-left: 1300px; color: white;");
        registerButton.getElement().setAttribute("style", "background-color: #4681f4; margin-left: 10px; color: white;");

        addToNavbar(true, toggle, viewTitle, loginButton, registerButton);
    }

    private void addDrawerContent() {
        H1 appName = new H1("My App");
        appName.addClassNames(LumoUtility.FontSize.LARGE, LumoUtility.Margin.NONE);
        Header header = new Header(appName);

        Scroller scroller = new Scroller(createNavigation());

        addToDrawer(header, scroller, createFooter());
    }

    private SideNav createNavigation() {
        SideNav nav = new SideNav();

        nav.addItem(new SideNavItem("Hello World", HelloWorldView.class, LineAwesomeIcon.GLOBE_SOLID.create()));
        nav.addItem(new SideNavItem("About", AboutView.class, LineAwesomeIcon.FILE.create()));
        nav.addItem(new SideNavItem("Electronic", ImageGalleryView.class, LineAwesomeIcon.ADJUST_SOLID.create()));
        nav.addItem(new SideNavItem("Grid with Filters", GridwithFiltersView.class, LineAwesomeIcon.FILTER_SOLID.create()));
        nav.addItem(new SideNavItem("Person Form", PersonFormView.class, LineAwesomeIcon.USER.create()));
        nav.addItem(new SideNavItem("Master-Detail", MasterDetailView.class, LineAwesomeIcon.COLUMNS_SOLID.create()));
        nav.addItem(new SideNavItem("Checkout Form", CheckoutFormView.class, LineAwesomeIcon.CREDIT_CARD.create()));
        nav.addItem(new SideNavItem("Cart", ShoppingCartView.class, LineAwesomeIcon.SHOPPING_CART_SOLID.create()));

        return nav;
    }

    private Footer createFooter() {
        Footer layout = new Footer();

        return layout;
    }

    @Override
    protected void afterNavigation() {
        super.afterNavigation();
        viewTitle.setText(getCurrentPageTitle());
    }

    private String getCurrentPageTitle() {
        PageTitle title = getContent().getClass().getAnnotation(PageTitle.class);
        return title == null ? "" : title.value();
    }
}