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

package org.broadleafcommerce.core.order.dao;

import javax.annotation.Resource;

import org.broadleafcommerce.core.order.FulfillmentGroupDataProvider;
import org.broadleafcommerce.core.order.dao.FulfillmentGroupDao;
import org.broadleafcommerce.core.order.dao.OrderDao;
import org.broadleafcommerce.core.order.domain.FulfillmentGroup;
import org.broadleafcommerce.core.order.domain.Order;
import org.broadleafcommerce.core.order.service.FulfillmentGroupService;
import org.broadleafcommerce.profile.core.dao.CustomerAddressDao;
import org.broadleafcommerce.profile.core.domain.Address;
import org.broadleafcommerce.profile.core.domain.Customer;
import org.broadleafcommerce.test.CommonSetupBaseTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import org.testng.annotations.Test;

public class FulfillmentGroupDaoTest extends CommonSetupBaseTest {

    private Long orderId;
    private Long defaultFulfillmentGroupId;
    private Long fulfillmentGroupId;

    @Resource
    private FulfillmentGroupDao fulfillmentGroupDao;
    
    @Resource
    private FulfillmentGroupService fulfillmentGroupService;

    @Resource
    private CustomerAddressDao customerAddressDao;

    @Resource
    private OrderDao orderDao;

    @Test(groups = "createDefaultFulfillmentGroup", dataProvider = "basicFulfillmentGroup", dataProviderClass = FulfillmentGroupDataProvider.class)
    @Transactional
    @Rollback(false)
    public void createDefaultFulfillmentGroup(FulfillmentGroup fulfillmentGroup) {
        Customer customer = createCustomerWithBasicOrderAndAddresses();
        Address address = (customerAddressDao.readActiveCustomerAddressesByCustomerId(customer.getId())).get(0).getAddress();
        Order salesOrder = (orderDao.readOrdersForCustomer(customer.getId())).get(0);

        FulfillmentGroup newFG = fulfillmentGroupDao.createDefault();
        newFG.setAddress(address);
        newFG.setRetailShippingPrice(fulfillmentGroup.getRetailShippingPrice());
        newFG.setMethod(fulfillmentGroup.getMethod());
        newFG.setService(fulfillmentGroup.getService());
        newFG.setOrder(salesOrder);
        newFG.setReferenceNumber(fulfillmentGroup.getReferenceNumber());

        assert newFG.getId() == null;
        fulfillmentGroup = fulfillmentGroupService.save(newFG);
        assert fulfillmentGroup.getId() != null;
        orderId = salesOrder.getId();
        defaultFulfillmentGroupId = fulfillmentGroup.getId();
    }

    @Test(groups = { "readDefaultFulfillmentGroupForOrder" }, dependsOnGroups = { "createDefaultFulfillmentGroup" })
    @Transactional
    public void readDefaultFulfillmentGroupForOrder() {
        Order order = orderDao.readOrderById(orderId);
        assert order != null;
        assert order.getId() == orderId;
        FulfillmentGroup fg = fulfillmentGroupDao.readDefaultFulfillmentGroupForOrder(order);
        assert fg.getId() != null;
        assert fg.getId().equals(defaultFulfillmentGroupId);
    }

    @Test(groups = { "readDefaultFulfillmentGroupForId" }, dependsOnGroups = { "createDefaultFulfillmentGroup" })
    @Transactional
    public void readDefaultFulfillmentGroupForId() {
        FulfillmentGroup fg = fulfillmentGroupDao.readFulfillmentGroupById(defaultFulfillmentGroupId);
        assert fg != null;
        assert fg.getId() != null;
        assert fg.getId().equals(defaultFulfillmentGroupId);
    }

    @Test(groups = "createFulfillmentGroup", dataProvider = "basicFulfillmentGroup", dataProviderClass = FulfillmentGroupDataProvider.class)
    @Transactional
    @Rollback(false)
    public void createFulfillmentGroup(FulfillmentGroup fulfillmentGroup) {
        Customer customer = createCustomerWithBasicOrderAndAddresses();
        Address address = (customerAddressDao.readActiveCustomerAddressesByCustomerId(customer.getId())).get(0).getAddress();
        Order salesOrder = (orderDao.readOrdersForCustomer(customer.getId())).get(0);

        FulfillmentGroup newFG = fulfillmentGroupDao.create();
        newFG.setAddress(address);
        newFG.setRetailShippingPrice(fulfillmentGroup.getRetailShippingPrice());
        newFG.setMethod(fulfillmentGroup.getMethod());
        newFG.setService(fulfillmentGroup.getService());
        newFG.setReferenceNumber(fulfillmentGroup.getReferenceNumber());
        newFG.setOrder(salesOrder);

        assert newFG.getId() == null;
        fulfillmentGroup = fulfillmentGroupService.save(newFG);
        assert fulfillmentGroup.getId() != null;
        orderId = salesOrder.getId();
        fulfillmentGroupId = fulfillmentGroup.getId();
    }

    @Test(groups = { "readFulfillmentGroupsForId" }, dependsOnGroups = { "createFulfillmentGroup" })
    @Transactional
    public void readFulfillmentGroupsForId() {
        FulfillmentGroup fg = fulfillmentGroupDao.readFulfillmentGroupById(fulfillmentGroupId);
        assert fg != null;
        assert fg.getId() != null;
    }
}
