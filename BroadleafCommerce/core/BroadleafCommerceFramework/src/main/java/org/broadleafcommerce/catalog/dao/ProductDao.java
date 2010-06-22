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
package org.broadleafcommerce.catalog.dao;

import java.util.Date;
import java.util.List;

import org.broadleafcommerce.catalog.domain.Product;

public interface ProductDao {

    public Product readProductById(Long productId);

    public Product save(Product product);

    public List<Product> readProductsByName(String searchName);

    public List<Product> readActiveProductsByCategory(Long categoryId, Date currentDate);
    
    public List<Product> readProductsByCategory(Long categoryId);    

    public List<Product> readProductsBySku(Long skuId);
    
    public List<Product> readActiveProductsBySku(Long skuId, Date currentDate);
    
    public void delete(Product product);

}