package com.protal.myApp.Service;

import com.protal.myApp.Entity.Purchase;
import com.protal.myApp.Entity.Ticket;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PurchaseService {
    void savePurchase(Purchase p);

    void deletePurchase(Purchase p);
}
