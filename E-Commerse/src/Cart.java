import java.util.ArrayList;
import java.util.List;

public class Cart {
    private List<CartItem> items = new ArrayList<>();

    public void addProduct(Product product, int quantity) {
        if (quantity <= product.getQuantity()) {
            items.add(new CartItem(product, quantity));
        } else {
            System.out.println("Cannot add more than available quantity.");
        }
    }

    public void checkout(Customer customer) {
        if (items.isEmpty()) {
            System.out.println("Error: Cart is empty.");
            return;
        }

        double subtotal = 0;
        double shipping = 0;
        List<ShippableItem> toShip = new ArrayList<>();

        for (CartItem item : items) {
            Product p = item.getProduct();
            int qty = item.getQuantity();

            if (p.isExpired()) {
                System.out.println("Error: Product " + p.getName() + " is expired.");
                return;
            }

            if (qty > p.getQuantity()) {
                System.out.println("Error: Not enough quantity for product " + p.getName());
                return;
            }

            subtotal += p.getPrice() * qty;

            if (p.isShippable() && p instanceof ShippableItem) {
                for (int i = 0; i < qty; i++) {
                    toShip.add((ShippableItem) p);
                    shipping += 15;
                }
            }
        }

        double total = subtotal + shipping;

        if (!customer.deductBalance(total)) {
            System.out.println("Error: Insufficient balance.");
            return;
        }

        for (CartItem item : items) {
            item.getProduct().reduceQuantity(item.getQuantity());
        }

        if (!toShip.isEmpty()) {
            System.out.println("** Shipment notice **");
            double totalWeight = 0;
            for (ShippableItem si : toShip) {
                System.out.printf("1x %s	%.0fg%n", si.getName(), si.getWeight() * 1000);
                totalWeight += si.getWeight();
            }
            System.out.printf("Total package weight %.1fkg%n%n", totalWeight);
        }

        System.out.println("** Checkout receipt **");
        for (CartItem item : items) {
            System.out.printf("%dx %s	%.0f%n", item.getQuantity(), item.getProduct().getName(), item.getTotalPrice());
        }
        System.out.println("----------------------");
        System.out.printf("Subtotal	%.0f%n", subtotal);
        System.out.printf("Shipping	%.0f%n", shipping);
        System.out.printf("Amount		%.0f%n", total);
        customer.printBalance();
    }
}