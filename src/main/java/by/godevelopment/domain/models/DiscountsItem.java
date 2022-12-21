package by.godevelopment.domain.models;

public record DiscountsItem(String name, int rate) {

    public static int compare (DiscountsItem p1, DiscountsItem p2){
        if(p1.rate() > p2.rate())
            return 1;
        return -1;
    }
}
