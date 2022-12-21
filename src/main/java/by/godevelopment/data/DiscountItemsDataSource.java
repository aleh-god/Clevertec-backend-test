package by.godevelopment.data;

import by.godevelopment.domain.models.DiscountsItem;

import java.util.ArrayList;
import java.util.List;

public interface DiscountItemsDataSource {

    public List<DiscountsItem> getDiscountItems();

    public DiscountsItem getDiscountsItemByStringOrNull(String input);

    class BaseImpl implements DiscountItemsDataSource {

        List<DiscountsItem> discountItems;

        public BaseImpl() {
            discountItems = new ArrayList<DiscountsItem>();
            discountItems.add(new DiscountsItem("card-1234", 15));
            discountItems.add(new DiscountsItem("card-5678", 20));
            discountItems.add(new DiscountsItem("10% discount", 10));
        }
        @Override
        public List<DiscountsItem> getDiscountItems() {
            return new ArrayList<>(discountItems);
        }
        @Override
        public DiscountsItem getDiscountsItemByStringOrNull(String input) {
            if (input != null && !input.isEmpty()) {
                for (DiscountsItem item : discountItems) {
                    if (item.name().equals(input)) return item;
                }
            }
            return null;
        }
    }
}
