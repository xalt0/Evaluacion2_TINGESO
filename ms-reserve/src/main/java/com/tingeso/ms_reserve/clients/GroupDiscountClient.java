package com.tingeso.ms_reserve.clients;

import com.tingeso.ms_reserve.DTOs.GroupDiscountDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "ms-2", url = "http://ms-2/group-discounts")
public interface GroupDiscountClient {

    @GetMapping("/best/{groupSize}")
    Integer getBestDiscount(@PathVariable("groupSize") int groupSize);
}