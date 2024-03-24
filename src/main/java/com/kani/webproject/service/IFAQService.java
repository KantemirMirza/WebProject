package com.kani.webproject.service;

import com.kani.webproject.dto.FAQDto;

public interface IFAQService {
    FAQDto createFaq(Long productId, FAQDto faqDto);

}
