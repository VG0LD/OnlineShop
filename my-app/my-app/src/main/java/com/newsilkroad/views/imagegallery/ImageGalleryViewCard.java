package com.newsilkroad.views.imagegallery;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.spring.annotation.UIScope;
import com.vaadin.flow.theme.lumo.LumoUtility.*;
import com.vaadin.flow.theme.lumo.Lumo;

@UIScope
public class ImageGalleryViewCard extends ListItem {
    public ImageGalleryViewCard(String title, String description, String url, String price, String discount, String link) {
        addClassNames(Background.CONTRAST_5, Display.FLEX, FlexDirection.COLUMN, AlignItems.START, Padding.MEDIUM,
                BorderRadius.LARGE);
        Div div = new Div();
        div.addClassNames(Background.CONTRAST, Display.FLEX, AlignItems.CENTER, JustifyContent.CENTER,
                Margin.Bottom.MEDIUM, Overflow.HIDDEN, BorderRadius.MEDIUM, Width.FULL);
        div.setHeight("160px");
        Image image = new Image();
        image.setWidth("100%");
        image.setSrc(url);
        image.setAlt(title);
        div.add(image);
        Span header = new Span();
        header.addClassNames(FontSize.XLARGE, FontWeight.SEMIBOLD);
        header.setText(title);
        Span subtitle = new Span();
        subtitle.addClassNames(FontSize.SMALL, TextColor.SECONDARY);
        subtitle.setText("");
        Paragraph paragraph = new Paragraph(description);
        paragraph.addClassName(Margin.Vertical.MEDIUM);
        Span badge = new Span();
        badge.getElement().setAttribute("theme", "badge");

        Span priceElement = new Span();
        priceElement.addClassNames(FontSize.MEDIUM, FontWeight.NORMAL, TextColor.SECONDARY);

        if ((discount != null && !discount.isEmpty()) && !discount.equals("null")) {
            priceElement.setText("Discount: " + discount);
        } else {
            priceElement.setText("Discount: " + 0);
        }
        badge.setText("Price: " + price);

        add(div, header, subtitle, paragraph, badge, priceElement);
    }
}
