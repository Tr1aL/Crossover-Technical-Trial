package com.dev.backend.controller;

import com.dev.backend.Utils;
import com.dev.backend.model.Customer;
import com.dev.backend.model.OrderLine;
import com.dev.backend.model.Product;
import com.dev.backend.model.SalesOrder;
import com.dev.backend.service.CustomerService;
import com.dev.backend.service.ProductService;
import com.dev.backend.service.SalesOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.*;
import java.util.stream.Collectors;

/**
 * API Rest Controller
 */
@RestController
@RequestMapping(value = "/api", produces = "application/json")
public class ApiController {

    @Autowired()
    private CustomerService customerService;

    @Autowired
    private ProductService productService;

    @Autowired
    private SalesOrderService salesOrderService;

    /**
     * Get list of all customers
     *
     * @return list of customers
     */
    @RequestMapping(value = "/customer/list", method = RequestMethod.GET)
    public List<Map<String, Object>> getCustomerList() {
        return customerService.getAll().stream().map(customer ->
                new LinkedHashMap<String, Object>() {{
                    put("code", customer.getCode());
                    put("name", customer.getName());
                    put("phone1", customer.getPhone1());
                    put("currentCredit", customer.getCurrentCredit());
                }})
                .collect(Collectors.toList());
    }

    /**
     * Get list references of all customers
     *
     * @return list of customers references
     */
    @RequestMapping(value = "/customer/refs", method = RequestMethod.GET)
    public List<Map<String, String>> getCustomerRefs() {
        return customerService.getAll().stream().map(customer ->
                new HashMap<String, String>() {{
                    put(customer.getCode(), customer.getName());
                }})
                .collect(Collectors.toList());
    }

    /**
     * Get customer by its code
     *
     * @param code customer code
     * @return customer
     */
    @RequestMapping(value = "/customer/{code}/get", method = RequestMethod.GET)
    public Customer getCustomer(@PathVariable String code) {
        return customerService.get(code);
    }

    /**
     * Save customer if exists or create if not exists
     *
     * @param customer      customer model
     * @param bindingResult check validation of customer model
     * @return customer
     */
    @RequestMapping(value = "/customer/save", method = RequestMethod.POST)
    public Customer saveCustomer(@RequestBody @Valid Customer customer,
                                 BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return null;
        }
        return customerService.save(customer);
    }

    /**
     * Delete customer by ids code
     *
     * @param code customer code
     * @return true if success of false if fail
     */
    @RequestMapping(value = "/customer/{code}/delete", method = RequestMethod.GET)
    public boolean deleteCustomer(@PathVariable String code) {
        try {
            customerService.delete(code);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Get list of all products
     *
     * @return list of products
     */
    @RequestMapping(value = "/product/list", method = RequestMethod.GET)
    public List<Product> getProductList() {
        return productService.getAll();
    }

    /**
     * Get list references of all products
     *
     * @return list of products references
     */
    @RequestMapping(value = "/product/refs", method = RequestMethod.GET)
    public List<Map<String, String>> getProductRefs() {
        return productService.getAll().stream().map(product ->
                new HashMap<String, String>() {{
                    put(product.getCode(), product.getDescription());
                }})
                .collect(Collectors.toList());
    }

    /**
     * Get product by his code
     *
     * @param code product code
     * @return product
     */
    @RequestMapping(value = "/product/{code}/get", method = RequestMethod.GET)
    public Product getProduct(@PathVariable String code) {
        return productService.get(code);
    }

    /**
     * Save product if exists or create if not exists
     *
     * @param product       customer model
     * @param bindingResult check validation of product model
     * @return product
     */
    @RequestMapping(value = "/product/save", method = RequestMethod.POST)
    public Product saveProduct(@RequestBody @Valid Product product,
                               BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return null;
        }
        return productService.save(product);
    }

    /**
     * Delete product by its code
     *
     * @param code product code
     * @return true if success of false if fail
     */
    @RequestMapping(value = "/product/{code}/delete", method = RequestMethod.GET)
    public boolean deleteProduct(@PathVariable String code) {
        try {
            productService.delete(code);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Get product price by its code
     *
     * @param code product code
     * @return product price
     */
    @RequestMapping(value = "/product/{code}/price", method = RequestMethod.GET)
    public double getProductPrice(@PathVariable String code) {
        return productService.getPrice(code);
    }

    /**
     * Get list of all salesOrder
     *
     * @return list of salesOrder
     */
    @RequestMapping(value = "/salesOrder/list", method = RequestMethod.GET)
    public List<Map<String, Object>> getSalesOrderList() {
        return salesOrderService.getAllSalesOrders().stream()
                .map(salesOrder -> {
                    Customer customer = customerService.get(salesOrder.getCustomer());
                    return new LinkedHashMap<String, Object>() {{
                        put("orderNum", salesOrder.getOrderNum());
                        put("customer", "(" + customer.getCode() + ")" + customer.getName());
                        put("totalPrice", salesOrder.getTotalPrice());
                    }};
                })
                .collect(Collectors.toList());
    }

    /**
     * Get salesOrder by its orderNum
     *
     * @param orderNum salesOrder orderNum
     * @return salesOrder
     */
    @RequestMapping(value = "/salesOrder/{orderNum}/get", method = RequestMethod.GET)
    public Map<String, Object> getSalesOrder(@PathVariable String orderNum) {
        SalesOrder salesOrder = salesOrderService.getSalesOrder(orderNum);
        List<OrderLine> orderLines = salesOrderService.getOrderLines(orderNum);
        return new HashMap<String, Object>() {{
            put("salesOrder", salesOrder);
            put("orderLines", orderLines.stream().map(ol -> {
                        double price = productService.getPrice(ol.getProduct());
                        return new HashMap<String, Object>() {{
                            put("product", ol.getProduct());
                            put("quantity", ol.getQuantity());
                            put("price", price);
                            put("totalPrice", ol.getQuantity() * price);
                        }};
                    })
                            .collect(Collectors.toList())
            );
        }};
    }

    /**
     * Save salesOrder and orderLines if exists or create if not exists
     *
     * @param params map with salesOrder and orderLines parameters
     * @return map with modified salesOrder and orderLines parameters
     */
    @RequestMapping(value = "/salesOrder/save", method = RequestMethod.POST)
    public Map<String, Object> saveSalesOrder(@RequestBody Map<String, Object> params) {
        Map<String, Object> salesOrderParams = (HashMap<String, Object>) params.get("salesOrder");
        List<Map<String, Object>> orderLinesParamsList = (List<Map<String, Object>>) params.get("orderLines");
        SalesOrder salesOrder = new SalesOrder();
        salesOrder.setOrderNum(salesOrderParams.get("orderNum") != null ? salesOrderParams.get("orderNum").toString() : null);
        salesOrder.setCustomer(salesOrderParams.get("customer") != null ? salesOrderParams.get("customer").toString() : null);
        salesOrder.setTotalPrice(0);
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        if (validator.validate(salesOrder).size() > 0) {
            return null;
        }
        List<OrderLine> orderLines = new ArrayList<>();
        for (Map<String, Object> orderLinesParams : orderLinesParamsList) {
            OrderLine orderLine = new OrderLine();
            orderLine.setOrderNum(salesOrder.getOrderNum());
            orderLine.setProduct(orderLinesParams.get("product") != null ? orderLinesParams.get("product").toString() : "");
            orderLine.setQuantity(orderLinesParams.get("quantity") != null ? Utils.parseInteger(orderLinesParams.get("quantity").toString()) : null);
            if (validator.validate(orderLine).size() > 0) {
                return null;
            }
            orderLines.add(orderLine);
        }
        boolean res = salesOrderService.save(salesOrder, orderLines);
        if (!res) {
            return null;
        }
        return getSalesOrder(salesOrder.getOrderNum());
    }

    /**
     * Delete salesOrder by its orderNum
     *
     * @param orderNum salesOrder orderNum
     * @return true if success of false if fail
     */
    @RequestMapping(value = "/salesOrder/{orderNum}/delete", method = RequestMethod.GET)
    public boolean deleteSalesOrder(@PathVariable String orderNum) {
        try {
            salesOrderService.delete(orderNum);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
