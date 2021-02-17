package com.protal.myApp.Service;

import com.protal.myApp.Entity.Purchase;
import com.protal.myApp.Entity.Ticket;
import com.protal.myApp.Repository.PurchaseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PurchaseserviceImpl implements PurchaseService {
    @Autowired
    PurchaseRepository purchaseRepository;

    public void savePurchase(Purchase p){
        purchaseRepository.save(p);
    }

    @Override
    public void updatePurchaseTicket(Purchase purchase, List<Ticket> tickets) {
        purchaseRepository.updatePurchaseTicket(purchase,tickets);
    }

    @Override
    public void deletePurchase(Purchase p) {
        purchaseRepository.delete(p);
    }
}
