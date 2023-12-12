package hello.core.order;

public interface OrderSerivce {
    Order createOrder(Long memberId, String itemName, int itemPrice);
}
