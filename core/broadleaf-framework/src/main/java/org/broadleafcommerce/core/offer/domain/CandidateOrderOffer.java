/*
 * Copyright 2008-2009 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.broadleafcommerce.core.offer.domain;

import java.io.Serializable;

import org.broadleafcommerce.core.order.domain.Order;
import org.broadleafcommerce.money.Money;

public interface CandidateOrderOffer extends Serializable {

    public Money getDiscountedPrice();
    
    public void setDiscountedPrice(Money discountedPrice);
    
    public Long getId();
    
    public void setId(Long id);

    public Order getOrder();

    public void setOrder(Order order);
    
    public void setOffer(Offer offer);

    public Offer getOffer();
    
    public int getPriority();
}
