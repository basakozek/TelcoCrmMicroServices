package com.etiya.salesservice.service.concretes;

import com.etiya.salesservice.repository.OrderRepository;
import com.etiya.salesservice.service.abstracts.OrderService;

public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;

    public OrderServiceImpl(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @Override
    public void add(String billingAccountId) {
        // TODO: Bu alanda basketservice tarafına istek atılıp sepetteki veriyi sişariş tarafına göndermek
        // => basketServiceClient.getByBillingAccoubtId(billingAccountId)

        // TODO: Sipariş onaylandıktan sonra basket service taradına sepetin boşaltılması için event fırlatılacak
        // orderRepository.save(order) dediktan sonra
        // var basketClearEvent = new BasketClearEvent(order.BillingAccountId) (basketId de olabilir. pairde konuşalım)
        // producer.send(basketClearEvent)

    }
}
