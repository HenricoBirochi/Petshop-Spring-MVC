package voyager.petshop.repositories;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import voyager.petshop.models.Order;
import voyager.petshop.models.User;

@Repository
public interface OrderRepository extends JpaRepository<Order, UUID> {
    List<Order> findByUserOrderByOrderDateDesc(User user);
    Order findByOrderId(UUID orderId);
}
